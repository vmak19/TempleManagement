/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mak
 */
public class PdfReport {
    
    private static final String filename = "Purchases and Costs Report.pdf";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PdfReport report = new PdfReport();
        report.createDocument();
        report.openDocumentForUser();
    }

    private void createDocument() {
        FileOutputStream output = null;
        try {
            // 1.Document
            // 2.FileOutputStream
            // 3.PdfWritter
            // 4.Put sth into document

            Document document = new Document();
            output = new FileOutputStream(filename);
            PdfWriter.getInstance(document, output);

            document.open();

            Paragraph purhcasePara = new Paragraph("Purchases: ");
            document.add(purhcasePara);

            Paragraph costPara = new Paragraph("Total Cost: ");
            document.add(costPara);
            
            document.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(PdfReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void openDocumentForUser() {
        try {
            Desktop.getDesktop().open(new File(filename));
        } catch (IOException ex) {
            Logger.getLogger(PdfReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
