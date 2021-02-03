package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.stereotype.Service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;


@DataJpaTest(includeFilters=@ComponentScan.Filter(Service.class))
class TipoCitaServiceTest {
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Test
	void shouldExistTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotEquals(listTiposCita, new ArrayList<>());
	}
	
	@Test
	void shouldBeNotNullTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotNull(listTiposCita);
	}
	
	@Test
	void shouldFindTipoCitaNotNull() {
		TipoCita t=tipoCitaService.findById(3).get();
		assertNotNull(t);
	}
	
	@Test
	void shouldFindTipoCita() {
		String t=tipoCitaService.findById(3).get().getTipo();
		assertEquals(t, "AIRE ACONDICIONADO");
	}
	
	@Test
	void e() throws FileNotFoundException, IOException {
		String src = "./src/main/resources/static/resources/f.pdf";
		String dest = "./src/main/resources/static/resources/f1.pdf";
<<<<<<< Upstream, based on origin/master
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        
        form.setGenerateAppearance(true);
        form.getField("direccion").setValue("uwu1");
        form.flattenFields();
=======
		PdfWriter writer = new PdfWriter(dest);
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        
        form.setGenerateAppearance(true);
        form.getField("direccion").setValue("uwu");
>>>>>>> cd2ac2d probando generar factura
        pdfDoc.close();
	}

}
package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.stereotype.Service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;


@DataJpaTest(includeFilters=@ComponentScan.Filter(Service.class))
class TipoCitaServiceTest {
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Test
	void shouldExistTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotEquals(listTiposCita, new ArrayList<>());
	}
	
	@Test
	void shouldBeNotNullTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotNull(listTiposCita);
	}
	
	@Test
	void shouldFindTipoCitaNotNull() {
		TipoCita t=tipoCitaService.findById(3).get();
		assertNotNull(t);
	}
	
	@Test
	void shouldFindTipoCita() {
		String t=tipoCitaService.findById(3).get().getTipo();
		assertEquals(t, "AIRE ACONDICIONADO");
	}
	
	@Test
	void e() throws FileNotFoundException, IOException {
		String src = "./src/main/resources/static/resources/f.pdf";
		String dest = "./src/main/resources/static/resources/f1.pdf";
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        
        form.setGenerateAppearance(true);
        form.getField("direccion").setValue("uwu1");
        form.flattenFields();
        pdfDoc.close();
	}

}
package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters=@ComponentScan.Filter(Service.class))
class TipoCitaServiceTest {
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Test
	void shouldExistTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotEquals(listTiposCita, new ArrayList<>());
	}
	
	@Test
	void shouldBeNotNullTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotNull(listTiposCita);
	}
	
	@Test
	void shouldFindTipoCitaNotNull() {
		TipoCita t=tipoCitaService.findById(3).get();
		assertNotNull(t);
	}
	
	@Test
	void shouldFindTipoCita() {
		String t=tipoCitaService.findById(3).get().getTipo();
		assertEquals(t, "AIRE ACONDICIONADO");
	}

}
