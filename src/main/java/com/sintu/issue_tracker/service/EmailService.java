package com.sintu.issue_tracker.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // We REMOVED JavaMailSender so it won't crash!

    public void sendStatusUpdate(String toEmail, String ticketTitle, String status, String staffName) {
        // Just print to console instead of sending real email
        System.out.println("------------------------------------------------");
        System.out.println(">>> [EMAIL SIMULATION] To: " + toEmail);
        System.out.println(">>> Ticket: " + ticketTitle);
        System.out.println(">>> Status: " + status);
        System.out.println("------------------------------------------------");
    }
    
    // If you have other methods like sendSimpleEmail, add a dummy version here too:
    public void sendSimpleEmail(String toEmail, String subject, String body) {
        System.out.println(">>> [EMAIL SIMULATION] Simple Email to: " + toEmail);
    }
}