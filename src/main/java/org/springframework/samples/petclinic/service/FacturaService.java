package org.springframework.samples.petclinic.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.HorasTrabajadas;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;

	@Transactional
	public void saveFactura(Factura factura) {
		this.facturaRepository.save(factura);
		log.info("Factura creada");
	}

	@Transactional(readOnly = true)
	public Iterable<Factura> findAll() throws DataAccessException {
		return facturaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Factura> findFacturaById(int id) throws DataAccessException {
		return facturaRepository.findById(id);
	}

	@Transactional
	public void delete(Factura factura) {
		facturaRepository.delete(factura);
		log.info("Factura con id " + factura.getId() + " borrado");
	}
	
	public String generarPDF(Factura factura) throws FileNotFoundException, IOException {
		String src = "./src/main/resources/static/resources/factura_base.pdf";
		String dest = "./src/main/resources/static/resources/factura_no_" + factura.getId() + ".pdf";
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        
        form.setGenerateAppearance(true);
        Cliente cliente = factura.getLineaFactura().get(0).getReparacion().getCita().getVehiculo().getCliente();
        form.getField("nombre").setValue(cliente.getNombre() + " " + cliente.getApellidos());
        form.getField("telefono").setValue(cliente.getTelefono());
        form.getField("correo").setValue(cliente.getEmail());
        form.getField("factura_id").setValue(factura.getId().toString());
        form.getField("fecha").setValue(factura.getFechaPago().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        form.flattenFields();
        
        Table table = new Table(UnitValue.createPercentArray(5));
        table.setFixedPosition((float)0, (float)0, (float)520);
        table.setRelativePosition((float)2, (float)281, (float)0, (float)0);
        table.addCell(getTitleCell("NOMBRE", 1f));
        table.addCell(getTitleCell("CANTIDAD", 1f));
        table.addCell(getTitleCell("PRECIO POR UNIDAD", 1f));
        table.addCell(getTitleCell("DESCUENTO", 1f));
        table.addCell(getTitleCell("TOTAL DE LÍNEA", 1f));
        
        for(LineaFactura LFactura : factura.getLineaFactura()) {
        	String nombre = LFactura.getRecambio().getName();
        	String cantidad = LFactura.getCantidad().toString();
        	String precioUnidad = LFactura.getPrecioBase().toString() + "€";
        	String descuento = LFactura.getDescuento().toString() + "%";
        	String total = LFactura.getPrecio().toString() + "€";
        	table.addCell(getBodyCell(nombre));
        	table.addCell(getBodyCell(cantidad));
        	table.addCell(getBodyCell(precioUnidad));
        	table.addCell(getBodyCell(descuento));
        	table.addCell(getBodyCell(total));
        }
        table.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
        add4EmptyCells(table);
        table.addCell(getTitleCell("DESCRIPCIÓN", 0.5f));
        table.addCell(getTitleCell("HORAS", 0.5f));
        table.addCell(getTitleCell("PRECIO HORA", 0.5f));
        table.addCell(getTitleCell("EMPLEADO", 0.5f));
        table.addCell(getTitleCell("TOTAL", 0.5f));
        
        for(HorasTrabajadas horas: factura.getLineaFactura().get(0).getReparacion().getHorasTrabajadas()) {
        	table.addCell(getBodyCell(horas.getTrabajoRealizado()));
        	table.addCell(getBodyCell(horas.getHorasTrabajadas().toString()));
        	table.addCell(getBodyCell(horas.getHorasTrabajadas().toString() + "€"));
        	table.addCell(getBodyCell(horas.getEmpleado().getNombre() + " " + horas.getEmpleado().getApellidos()));
        	table.addCell(getBodyCell(horas.getPrecioTotal().toString() + "€"));
        }
        
        add4EmptyCells(table);      
        table.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
        add4EmptyCells(table);
        table.addCell(new Cell().add(new Paragraph("Suma: " + factura.getPrecioTotal().toString() + "€")).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER)
        		.setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(0.5f)));
        add4EmptyCells(table);
        table.addCell(new Cell().add(new Paragraph("Descuento: " + factura.getDescuento().toString() + "%")).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER)
        		.setTextAlignment(TextAlignment.CENTER));
        add4EmptyCells(table);
        table.addCell(new Cell().add(new Paragraph("Total: " + factura.getPrecioConDescuento().toString() + "€")).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER)
        		.setTextAlignment(TextAlignment.CENTER));
        Document doc = new Document(pdfDoc);
        doc.add(table);
        doc.close();
        pdfDoc.close();
        log.info("PDF generado para factura con id " + factura.getId());
		return dest;
	}
	
	public Cell getTitleCell(String title, Float width) {
        Color color = new DeviceRgb(35, 106, 70);
		return new Cell().add(new Paragraph(title)).setTextAlignment(TextAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.MIDDLE)
				.setFontColor(color).setBold().setFontSize(11.04f).setBorder(Border.NO_BORDER)
				.setBorderBottom(new SolidBorder(width));
	}
	
	public Cell getBodyCell(String value) {
		return new Cell().add(new Paragraph(value)).setTextAlignment(TextAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.MIDDLE)
				.setBorder(Border.NO_BORDER);
	}
	
	public void add4EmptyCells(Table table) {
        table.addCell(new Cell().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().setBorder(Border.NO_BORDER));
	}

}
