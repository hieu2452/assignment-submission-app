package com.example.spring.CafeManagerApplication.Utils;

import com.example.spring.CafeManagerApplication.dto.BillDetailDto;
import com.example.spring.CafeManagerApplication.dto.ProductDto;
import com.example.spring.CafeManagerApplication.entity.BillDetail;
import com.example.spring.CafeManagerApplication.entity.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CafeUtils {
    public static byte[] createPdf(BillDetailDto billDetailDto) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A5);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Define text and positions
            String title = "Invoice";
            String customerName = "Customer Name: " + billDetailDto.getName();
            String email = "Email: " + billDetailDto.getEmail();
            String contactNumber = "Contact Number: " + billDetailDto.getContactNumber();
            String paymentMethod = "Payment Method: " + billDetailDto.getPaymentMethod();
            String createdDate = "Created Date: " + billDetailDto.getCreatedDate();

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

            // Write the title (centered)
            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 16;
            float titleX = (PDRectangle.A5.getWidth() - titleWidth) / 2;
            contentStream.beginText();
            contentStream.newLineAtOffset(titleX, PDRectangle.A5.getHeight() - 20);
            contentStream.showText(title);
            contentStream.endText();

            // Set font and font size for the rest of the content
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Write customer information
            contentStream.beginText();
            contentStream.newLineAtOffset(50, PDRectangle.A5.getHeight() - 40);
            contentStream.showText(createdDate);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(customerName);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(email);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(contactNumber);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(paymentMethod);
            contentStream.endText();

            // Draw a table-like structure for products
            float tableX = 50;
            float tableY = PDRectangle.A5.getHeight() - 150;
            float cellWidth = 130;
            float cellHeight = 20;
            float cellMargin = 5;

            // Draw headers
            contentStream.beginText();
            contentStream.newLineAtOffset(tableX + cellMargin, tableY - cellMargin);
            contentStream.showText("Product");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(tableX + cellWidth + cellMargin, tableY - cellMargin);
            contentStream.showText("Quantity");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(tableX + 2 * cellWidth + cellMargin, tableY - cellMargin);
            contentStream.showText("Price");
            contentStream.endText();

            // Draw product data
            for (int i = 0; i < billDetailDto.getProducts().size(); i++) {
                ProductDto product = billDetailDto.getProducts().get(i);
                float y = tableY - cellHeight * (i + 1);

                contentStream.beginText();
                contentStream.newLineAtOffset(tableX + cellMargin, y - cellHeight / 2 - cellMargin);
                contentStream.showText(product.getName());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(tableX + cellWidth + cellMargin, y - cellHeight / 2 - cellMargin);
                contentStream.showText(String.valueOf(product.getQuantity()));
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(tableX + 2 * cellWidth + cellMargin, y - cellHeight / 2 - cellMargin);
                contentStream.showText(String.valueOf(product.getPrice()));
                contentStream.endText();
            }

            double totalPrice = billDetailDto.getProducts().stream().mapToDouble(p -> p.getQuantity() * p.getPrice()).sum();
            String totalPriceText = "Total Price: " + totalPrice;
            float totalPriceWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(totalPriceText) / 1000 * 12;
            float totalPriceX = PDRectangle.A5.getWidth() - 70 - totalPriceWidth;
            contentStream.beginText();
            contentStream.newLineAtOffset(totalPriceX, tableY - 90);
            contentStream.showText(totalPriceText);
            contentStream.endText();

            contentStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();

            System.out.println("Invoice generated successfully.");

            // Return the generated PDF content as a byte array
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
