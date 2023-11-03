package com.example.spring.CafeManagerApplication.Utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;


@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender emailSender;

    public void sendInvoiceEmail(String recipientEmail, byte[] pdfData) {
        try {
            // Create a MimeMessage
            MimeMessage message = emailSender.createMimeMessage();

            // Enable the multipart flag to attach the PDF
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the recipient's email address
            helper.setTo(recipientEmail);

            // Set the email subject
            helper.setSubject("Thank you for using our services");

            // Set the email body
            helper.setText("Please find the invoice attached.");

            // Attach the PDF invoice
            ByteArrayResource invoiceAttachment = new ByteArrayResource(pdfData);
            helper.addAttachment("invoice.pdf", invoiceAttachment);

            // Send the email
            emailSender.send(message);

            System.out.println("Email sent with invoice attachment successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
