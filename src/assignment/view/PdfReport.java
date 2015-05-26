/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.database.BookingQueries;
import assignment.database.ProvidesQueries;
import assignment.model.BookingInfo;
import assignment.model.RoomType;
import assignment.model.ServiceInfo;
import assignment.util.DateUtil;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mak
 */
public class PdfReport {

    BookingQueries bookingQueries = new BookingQueries();
    ProvidesQueries providesQueries = new ProvidesQueries();
    private static final String filename = "Purchases and Costs Report.pdf";

    /**
     * @param args the command line arguments
     */
    public static void main(BookingInfo toInsert) {
        PdfReport report = new PdfReport();
        report.createDocument(toInsert);
        report.openDocumentForUser();
    }

    private void createDocument(BookingInfo toInsert) {
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
            //LOGO
            //Image logo = Image.getInstance("logo.png");
           // logo.setAbsolutePosition(500f, 650f);
           // document.add(logo);

            //FOOTER           
            //Create grey font
            FontSelector selector1 = new FontSelector();
            Font f1 = FontFactory.getFont(FontFactory.HELVETICA, 12);
            f1.setColor(BaseColor.GRAY);
            selector1.addFont(f1);
            //Create size 12 bold font
            FontSelector selector2 = new FontSelector();
            Font f2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            selector2.addFont(f2);

            //Changes phrases to grey colour
            Phrase ph2 = selector1.process("Address: 14 Coogee St");
            Phrase ph3 = selector1.process("Tel: +61 2 99999, Fax: +61 999 9999");
            Phrase ph4 = selector1.process("Email: enquries@whitecoogeehotel.com");
            Phrase ph5 = selector1.process("Website: www.whitecoogeehotel.com.au");
            //Add phrases to paragraphs
            Paragraph footer2 = new Paragraph(ph2);
            Paragraph footer3 = new Paragraph(ph3);
            Paragraph footer4 = new Paragraph(ph4);
            Paragraph footer5 = new Paragraph(ph5);
            Paragraph footer6 = new Paragraph("------------------------------------------------------------------------------------------------");
            //Formats layout of footer            
            footer2.setAlignment(Element.ALIGN_RIGHT);
            footer3.setAlignment(Element.ALIGN_RIGHT);
            footer4.setAlignment(Element.ALIGN_RIGHT);
            footer5.setAlignment(Element.ALIGN_RIGHT);
            footer6.setAlignment(Element.ALIGN_CENTER);
            footer6.setSpacingAfter(15);
            //Add footer to document
            document.add(footer2);
            document.add(footer3);
            document.add(footer4);
            document.add(footer5);
            document.add(footer6);

            //CONTENT
            //Paragraph invoice = new Paragraph("INVOICE");
            Phrase invoice1 = selector2.process("INVOICE");
            Paragraph invoice = new Paragraph(invoice1);
            invoice.setSpacingAfter(20);
            document.add(new Paragraph(invoice));
            document.add(new Paragraph("Arrival:            " + (DateUtil.format(toInsert.getCheckIn()))));
            document.add(new Paragraph("Departure:      " + (DateUtil.format(toInsert.getCheckOut()))));
            document.add(new Paragraph("Room#:            " + (Integer.toString(toInsert.getRefCode()))));  //getRoomID()))));

            //TABLE OF PURCHASES
            //Create 5 number of columns
            PdfPTable table = new PdfPTable(5); // 5 columns.
            table.setWidthPercentage(100);
            //float[] columnWidths = {1, 1, 1, 1, 1};
            //table.setWidths(columnWidths);

            //Create header columns
            PdfPCell cell1 = new PdfPCell(new Paragraph("Date"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Description"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Charges"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Credit"));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Balance"));
            //Add header columns to table
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);

            //Charging for room
            List<RoomType> myCost = new ArrayList<RoomType>();
            myCost = bookingQueries.getRoomCost(toInsert.getRefCode());
            double thisCost = myCost.get(0).getBaseRate();

            double balance = thisCost - toInsert.getAmountPaid();

            PdfPCell valueCell1 = new PdfPCell(new Paragraph((DateUtil.format(toInsert.getCheckIn()))));
            PdfPCell valueCell2 = new PdfPCell(new Paragraph("Room Charge"));
            PdfPCell valueCell3 = new PdfPCell(new Paragraph(Double.toString(thisCost)));
            PdfPCell valueCell4 = new PdfPCell(new Paragraph(Double.toString(toInsert.getAmountPaid())));
            PdfPCell valueCell5 = new PdfPCell(new Paragraph(Double.toString(balance)));
            //Add header columns to table
            table.addCell(valueCell1);
            table.addCell(valueCell2);
            table.addCell(valueCell3);
            table.addCell(valueCell4);
            table.addCell(valueCell5);

            //Charging for service
            //Get services provided for this booking
            List<ServiceInfo> servicesProvided = new ArrayList<ServiceInfo>();
            servicesProvided = providesQueries.getServicesProvided(toInsert.getRefCode());

            int size = servicesProvided.size();
            //Loops through array containing service provided for this booking and adds them to table
            if (size >= 0) {
                for (int i = 0; i < size; i++) {
                    double serviceCost = servicesProvided.get(i).getCost();
                    String serviceDescript = servicesProvided.get(i).getServiceDesc();
                    double serviceBalance = serviceCost + balance;

                    PdfPCell serviceCell1 = new PdfPCell(new Paragraph((DateUtil.format(toInsert.getCheckIn()))));
                    PdfPCell serviceCell2 = new PdfPCell(new Paragraph("Service Charge: " + serviceDescript));
                    PdfPCell serviceCell3 = new PdfPCell(new Paragraph(Double.toString(serviceCost)));
                    PdfPCell serviceCell4 = new PdfPCell(new Paragraph("0"));
                    PdfPCell serviceCell5 = new PdfPCell(new Paragraph(Double.toString(serviceBalance)));
                    //Add header columns to table
                    table.addCell(serviceCell1);
                    table.addCell(serviceCell2);
                    table.addCell(serviceCell3);
                    table.addCell(serviceCell4);
                    table.addCell(serviceCell5);
                }
            }

            //Format spacing
            table.setSpacingBefore(20);
            table.setSpacingAfter(20);
            //Add table to document
            document.add(table);

            //Summation of invoice
            //Total Charge
            Paragraph totalCost = new Paragraph("Total Charge:         " + "$" + toInsert.getAmountDue());
            totalCost.setAlignment(Element.ALIGN_RIGHT);
            document.add(new Paragraph(totalCost));
            //Total Paid
            Paragraph totalPaid = new Paragraph("Total Paid:         " + "$" + toInsert.getAmountPaid());
            totalPaid.setAlignment(Element.ALIGN_RIGHT);
            document.add(new Paragraph(totalPaid));
            //Balance
            double paid = toInsert.getAmountPaid();
            double due = toInsert.getAmountDue();
            double endBalance = due - paid;
            Paragraph endBalancePara = new Paragraph("Balance:         " + "$" + endBalance);
            endBalancePara.setSpacingAfter(50);
            endBalancePara.setAlignment(Element.ALIGN_RIGHT);
            document.add(new Paragraph(endBalancePara));

            //Signature
            Paragraph signatureLine = new Paragraph("-----------------                                                                          --------------------");
            invoice.setSpacingAfter(10);
            document.add(new Paragraph(signatureLine));
            Paragraph signature = new Paragraph("Cashier                                                                                Guest Signature");
            signature.setSpacingAfter(50);
            document.add(new Paragraph(signature));

            //End notice
            Paragraph notice = new Paragraph("PLEASE RETURN YOUR ROOM KEY CARD TO THE FRONT DESK.");
            notice.setSpacingAfter(50);
            document.add(new Paragraph(notice));

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
