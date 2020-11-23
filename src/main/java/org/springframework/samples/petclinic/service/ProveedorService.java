package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.repository.ProveedorRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedProveedorNifException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorService {

	private ProveedorRepository proveedorRepository;
	
	@Autowired
	public ProveedorService(ProveedorRepository proveedorRepository) {
		this.proveedorRepository = proveedorRepository;
	}
	
	@Transactional(rollbackFor = DuplicatedProveedorNifException.class)
	public void saveProveedor(Proveedor proveedor) throws DataAccessException, DuplicatedProveedorNifException  {
		Proveedor p = proveedorRepository.findByNif(proveedor.getNif());
		if(p!=null) {
			throw new DuplicatedProveedorNifException();
		} else {
			proveedorRepository.save(proveedor);
		}	
	}
	
	@Transactional(readOnly = true)
	public Iterable<Proveedor> findAll() throws DataAccessException {
		return proveedorRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Proveedor> findProveedorById(int id) throws DataAccessException {
		return proveedorRepository.findById(id);
	}
	
	
	@Transactional
	public void delete(Proveedor proveedor) {
		proveedorRepository.delete(proveedor);
	}
	
	
	
	
}
