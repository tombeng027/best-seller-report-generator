package com.svi.get_barcode_page.marufuku_report_generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
 
public class Test {
 
    public static final String TEXT
        = "C:\\Users\\darimbuyutan\\Desktop\\marufukuReportGenerator\\output\\samp\\samp.txt";
    public static final String DEST
        = "C:\\Users\\darimbuyutan\\Desktop\\marufukuReportGenerator\\output\\samp\\samp.pdf";
 
    public static void main(String[] args)
    	throws DocumentException, IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    	new Test().createPdf(DEST);
    }
 
    @SuppressWarnings("resource")
	public void createPdf(String dest)
	throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        ColumnText ct = new ColumnText(writer.getDirectContent());
        BufferedReader br = new BufferedReader(new FileReader(TEXT));
        String line;
        Paragraph p;
        Font normal = new Font(FontFamily.TIMES_ROMAN, 12);
        Font bold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        boolean title = true;
        while ((line = br.readLine()) != null) {
            p = new Paragraph(line, title ? bold : normal);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            title = line.isEmpty();
            ct.addElement(p);
        }
 
        int c = 0;
        int status = ColumnText.START_COLUMN;
        while (ColumnText.hasMoreText(status)) {
        	if(c==0)
        	ct.setSimpleColumn(36, 36, 290, 806);
        	else
        	ct.setSimpleColumn(305, 36, 559, 806);
            status = ct.go();
            System.out.println(c);
            if (++c == 2) {
                document.newPage();
                c = 0;
            }
            
        }
        document.close();
    }
}
