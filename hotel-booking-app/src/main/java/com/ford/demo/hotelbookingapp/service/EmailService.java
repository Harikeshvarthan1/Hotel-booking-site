package com.ford.demo.hotelbookingapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Send a confirmation email for a booking.
     *
     * @param toEmail     The recipient's email address.
     * @param userName    The name of the user.
     * @param hotelName   The name of the hotel booked.
     * @param checkInDate The check-in date for the booking.
     * @param checkOutDate The check-out date for the booking.
     * @throws MessagingException If there is an error with the email sending process.
     */
    public void sendBookingConfirmationEmail(String toEmail, String userName, String hotelName, String checkInDate, String checkOutDate) throws MessagingException {
        try {
            // Log the start of email sending process
            System.out.println("Preparing to send booking confirmation email to: " + toEmail);

            String subject = "✅ Your Booking is Confirmed! - " + hotelName;
            String htmlContent = constructPremiumEmailContent(userName, hotelName, checkInDate, checkOutDate);

            sendEmail(toEmail, subject, htmlContent);

            // Log successful email preparation
            System.out.println("Email content prepared successfully for: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending booking confirmation email to " + toEmail + ": " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to let the controller handle it
        } catch (Exception e) {
            System.err.println("Unexpected error during email preparation: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to prepare email: " + e.getMessage(), e);
        }
    }

    /**
     * Construct premium HTML content for the confirmation email.
     *
     * @param userName    The user's name.
     * @param hotelName   The hotel name.
     * @param checkInDate The check-in date.
     * @param checkOutDate The check-out date.
     * @return The constructed HTML content as a String.
     */
    private String constructPremiumEmailContent(String userName, String hotelName, String checkInDate, String checkOutDate) {
        // Get current year for copyright
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());

        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Your Booking is Confirmed!</title>" +
                "    <style>" +
                "        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');" +
                "        * {" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            box-sizing: border-box;" +
                "        }" +
                "        body {" +
                "            font-family: 'Poppins', sans-serif;" +
                "            line-height: 1.6;" +
                "            color: #333;" +
                "            background-color: #f5f7fa;" +
                "        }" +
                "        .email-container {" +
                "            max-width: 650px;" +
                "            margin: 0 auto;" +
                "            background-color: #ffffff;" +
                "            border-radius: 15px;" +
                "            overflow: hidden;" +
                "            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        .email-header {" +
                "            background: linear-gradient(135deg, #0a2540 0%, #1a4b8c 100%);" +
                "            color: white;" +
                "            padding: 30px 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .logo {" +
                "            margin-bottom: 15px;" +
                "            font-size: 28px;" +
                "            font-weight: 700;" +
                "            letter-spacing: 1px;" +
                "        }" +
                "        .logo span {" +
                "            color: #ffc107;" +
                "        }" +
                "        .confirmation-badge {" +
                "            display: inline-block;" +
                "            background-color: #e7f4e4;" +
                "            color: #28a745;" +
                "            font-size: 14px;" +
                "            font-weight: 600;" +
                "            padding: 6px 15px;" +
                "            border-radius: 50px;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        .email-title {" +
                "            font-size: 24px;" +
                "            font-weight: 700;" +
                "            margin-bottom: 10px;" +
                "            color: #ffffff;" +
                "        }" +
                "        .email-subtitle {" +
                "            font-size: 16px;" +
                "            font-weight: 400;" +
                "            opacity: 0.9;" +
                "        }" +
                "        .email-content {" +
                "            padding: 40px 30px;" +
                "        }" +
                "        .greeting {" +
                "            font-size: 18px;" +
                "            font-weight: 600;" +
                "            margin-bottom: 20px;" +
                "            color: #0a2540;" +
                "        }" +
                "        .message {" +
                "            margin-bottom: 30px;" +
                "            font-size: 16px;" +
                "            color: #4a5568;" +
                "        }" +
                "        .booking-details {" +
                "            background-color: #f8fafc;" +
                "            border-radius: 12px;" +
                "            padding: 25px;" +
                "            margin-bottom: 30px;" +
                "            border-left: 4px solid #1a4b8c;" +
                "        }" +
                "        .booking-details h3 {" +
                "            font-size: 18px;" +
                "            font-weight: 600;" +
                "            margin-bottom: 20px;" +
                "            color: #0a2540;" +
                "        }" +
                "        .detail-row {" +
                "            display: flex;" +
                "            margin-bottom: 15px;" +
                "        }" +
                "        .detail-icon {" +
                "            flex: 0 0 40px;" +
                "            height: 40px;" +
                "            background-color: rgba(26, 75, 140, 0.1);" +
                "            border-radius: 50%;" +
                "            display: flex;" +
                "            align-items: center;" +
                "            justify-content: center;" +
                "            margin-right: 15px;" +
                "            color: #1a4b8c;" +
                "            font-size: 18px;" +
                "        }" +
                "        .detail-content {" +
                "            flex: 1;" +
                "        }" +
                "        .detail-label {" +
                "            font-size: 14px;" +
                "            color: #718096;" +
                "            margin-bottom: 4px;" +
                "        }" +
                "        .detail-value {" +
                "            font-size: 16px;" +
                "            font-weight: 600;" +
                "            color: #2d3748;" +
                "        }" +
                "        .hotel-info {" +
                "            background-color: #ebf5ff;" +
                "            border-radius: 12px;" +
                "            padding: 25px;" +
                "            margin-bottom: 30px;" +
                "        }" +
                "        .hotel-info h3 {" +
                "            font-size: 18px;" +
                "            font-weight: 600;" +
                "            margin-bottom: 15px;" +
                "            color: #1a4b8c;" +
                "        }" +
                "        .hotel-info p {" +
                "            color: #4a5568;" +
                "            font-size: 15px;" +
                "            margin-bottom: 15px;" +
                "        }" +
                "        .amenities {" +
                "            display: flex;" +
                "            flex-wrap: wrap;" +
                "            margin-top: 20px;" +
                "        }" +
                "        .amenity {" +
                "            flex: 0 0 50%;" +
                "            display: flex;" +
                "            align-items: center;" +
                "            margin-bottom: 15px;" +
                "        }" +
                "        .amenity-icon {" +
                "            margin-right: 10px;" +
                "            color: #1a4b8c;" +
                "        }" +
                "        .cta-section {" +
                "            text-align: center;" +
                "            margin-bottom: 30px;" +
                "        }" +
                "        .cta-button {" +
                "            display: inline-block;" +
                "            background-color: #1a4b8c;" +
                "            color: white;" +
                "            text-decoration: none;" +
                "            padding: 14px 30px;" +
                "            border-radius: 6px;" +
                "            font-weight: 600;" +
                "            font-size: 16px;" +
                "            transition: background-color 0.3s;" +
                "        }" +
                "        .cta-button:hover {" +
                "            background-color: #0f366d;" +
                "        }" +
                "        .contact-info {" +
                "            text-align: center;" +
                "            font-size: 15px;" +
                "            color: #4a5568;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        .email-footer {" +
                "            background-color: #0a2540;" +
                "            color: #e2e8f0;" +
                "            padding: 30px;" +
                "            text-align: center;" +
                "        }" +
                "        .social-links {" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        .social-link {" +
                "            display: inline-block;" +
                "            margin: 0 10px;" +
                "            color: white;" +
                "            text-decoration: none;" +
                "            font-size: 20px;" +
                "        }" +
                "        .footer-text {" +
                "            font-size: 14px;" +
                "            color: #a0aec0;" +
                "            margin-bottom: 10px;" +
                "        }" +
                "        .footer-address {" +
                "            font-size: 13px;" +
                "            color: #718096;" +
                "        }" +
                "        .footer-link {" +
                "            color: #a0aec0;" +
                "            text-decoration: underline;" +
                "        }" +
                "        @media only screen and (max-width: 600px) {" +
                "            .email-container {" +
                "                width: 100%;" +
                "                border-radius: 0;" +
                "            }" +
                "            .email-content {" +
                "                padding: 30px 20px;" +
                "            }" +
                "            .detail-row {" +
                "                flex-direction: column;" +
                "            }" +
                "            .detail-icon {" +
                "                margin-bottom: 10px;" +
                "            }" +
                "            .amenity {" +
                "                flex: 0 0 100%;" +
                "            }" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"email-container\">" +
                "        <div class=\"email-header\">" +
                "            <div class=\"logo\">ITC <span>HOTELS</span></div>" +
                "            <div class=\"confirmation-badge\">✓ Booking Confirmed</div>" +
                "            <h1 class=\"email-title\">Your Reservation is Confirmed!</h1>" +
                "            <p class=\"email-subtitle\">Thank you for choosing " + hotelName + "</p>" +
                "        </div>" +
                "        <div class=\"email-content\">" +
                "            <h2 class=\"greeting\">Hello, " + userName + "</h2>" +
                "            <p class=\"message\">Your reservation at <strong>" + hotelName + "</strong> has been successfully confirmed. We're excited to welcome you for your upcoming stay and are committed to making your experience exceptional.</p>" +
                "            <div class=\"booking-details\">" +
                "                <h3>Booking Details</h3>" +
                "                <div class=\"detail-row\">" +
                "                    <div class=\"detail-icon\">🏨</div>" +
                "                    <div class=\"detail-content\">" +
                "                        <div class=\"detail-label\">Hotel</div>" +
                "                        <div class=\"detail-value\">" + hotelName + "</div>" +
                "                    </div>" +
                "                </div>" +
                "                <div class=\"detail-row\">" +
                "                    <div class=\"detail-icon\">📅</div>" +
                "                    <div class=\"detail-content\">" +
                "                        <div class=\"detail-label\">Check-in Date</div>" +
                "                        <div class=\"detail-value\">" + checkInDate + " (after 3:00 PM)</div>" +
                "                    </div>" +
                "                </div>" +
                "                <div class=\"detail-row\">" +
                "                    <div class=\"detail-icon\">📆</div>" +
                "                    <div class=\"detail-content\">" +
                "                        <div class=\"detail-label\">Check-out Date</div>" +
                "                        <div class=\"detail-value\">" + checkOutDate + " (before 11:00 AM)</div>" +
                "                    </div>" +
                "                </div>" +
                "                <div class=\"detail-row\">" +
                "                    <div class=\"detail-icon\">👤</div>" +
                "                    <div class=\"detail-content\">" +
                "                        <div class=\"detail-label\">Guest Name</div>" +
                "                        <div class=\"detail-value\">" + userName + "</div>" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "            <div class=\"hotel-info\">" +
                "                <h3>" + hotelName + " Information</h3>" +
                "                <p>We're looking forward to welcoming you. Our staff is ready to make your stay comfortable and memorable.</p>" +
                "                <p>Please bring your ID and the credit card used for booking with you at check-in time.</p>" +
                "                <h4 style=\"margin-top: 20px; color: #1a4b8c; font-size: 16px;\">Hotel Amenities</h4>" +
                "                <div class=\"amenities\">" +
                "                    <div class=\"amenity\">" +
                "                        <span class=\"amenity-icon\">🏊</span> Swimming Pool" +
                "                    </div>" +
                "                    <div class=\"amenity\">" +
                "                        <span class=\"amenity-icon\">🍳</span> Breakfast Included" +
                "                    </div>" +
                "                    <div class=\"amenity\">" +
                "                        <span class=\"amenity-icon\">🔌</span> Free WiFi" +
                "                    </div>" +
                "                    <div class=\"amenity\">" +
                "                        <span class=\"amenity-icon\">🅿️</span> Free Parking" +
                "                    </div>" +
                "                    <div class=\"amenity\">" +
                "                        <span class=\"amenity-icon\">🏋️</span> Fitness Center" +
                "                    </div>" +
                "                    <div class=\"amenity\">" +
                "                        <span class=\"amenity-icon\">🍽️</span> Restaurant" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "            <div class=\"cta-section\">" +
                "                <p style=\"margin-bottom: 20px;\">Need to modify your reservation or have questions?</p>" +
                "                <a href=\"#\" class=\"cta-button\">Contact Us</a>" +
                "            </div>" +
                "            <div class=\"contact-info\">" +
                "                <p>If you have any questions, please contact our customer service:</p>" +
                "                <p><strong>Phone:</strong> +1 (555) 123-4567 | <strong>Email:</strong> support@itchotels.com</p>" +
                "            </div>" +
                "        </div>" +
                "        <div class=\"email-footer\">" +
                "            <div class=\"social-links\">" +
                "                <a href=\"#\" class=\"social-link\">📱</a>" +
                "                <a href=\"#\" class=\"social-link\">📘</a>" +
                "                <a href=\"#\" class=\"social-link\">📸</a>" +
                "                <a href=\"#\" class=\"social-link\">🐦</a>" +
                "            </div>" +
                "            <p class=\"footer-text\">© " + currentYear + " ITC Hotels. All rights reserved.</p>" +
                "            <p class=\"footer-address\">123 Luxury Avenue, Mumbai, India | <a href=\"https://www.itchotels.com\" class=\"footer-link\">www.itchotels.com</a></p>" +
                "            <p class=\"footer-text\" style=\"margin-top: 20px; font-size: 12px;\">This email was sent to you because you made a reservation at " + hotelName + ".</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Send an email using the specified parameters.
     *
     * @param toEmail     The recipient's email address.
     * @param subject     The subject of the email.
     * @param htmlContent The HTML content to send.
     * @throws MessagingException If there is an error with the email sending process.
     */
    private void sendEmail(String toEmail, String subject, String htmlContent) throws MessagingException {
        try {
            System.out.println("Configuring email message...");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom(fromEmail); // Using the email from application.properties

            System.out.println("Sending email from: " + fromEmail + " to: " + toEmail);

            // Attempt to send the email
            mailSender.send(mimeMessage);

            System.out.println("Email sent successfully to: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            // Log more details about the error
            if (e.getCause() != null) {
                System.err.println("Cause: " + e.getCause().getMessage());
                e.getCause().printStackTrace();
            }
            throw e;
        }
    }
}
