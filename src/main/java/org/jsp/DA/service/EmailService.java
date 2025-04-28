package org.jsp.DA.service;


import java.util.List;

import org.jsp.DA.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender jms;

	public void sendEmail(User u) {
		MimeMessage message = jms.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setTo(u.getEmail());
	        helper.setSubject("✨ Welcome to DRE – Where Fairy Tales Begin! ✨");
	        helper.setText(
	            "<html>" +
	            "<body style='background-color: #fff0f5; font-family: \"Comic Sans MS\", cursive, sans-serif; color: #6a1b9a; padding: 30px;'>" +
	            "<div style='background-color: #ffffff; border-radius: 15px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); padding: 30px;'>" +
	            "<h1 style='text-align: center; color: #ba68c8;'>💕 Welcome, " + u.getName() + "! ❤️</h1>" +
	            "<p style='text-align: center; font-size: 18px;'>A magical journey awaits you at <strong>DRE</strong>... 💖</p>" +
	            "<p style='text-align: center;'>Where every moment sparkles ✨ and every heartbeat tells a story 💕.</p>" +
	            "<p style='margin-top: 30px; text-align: center;'>Close your eyes, make a wish, and let your fairy tale unfold...</p>" +
	            "<p style='margin-top: 20px; text-align: center; font-size: 16px;'><em>With all our love,<br>The DRE Team 🌟</em></p>" +
	            "</div>" +
	            "</body>" +
	            "</html>",
	            true
	        );
	        jms.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
	
	public void matchUsers(User u, List<User> users) {
	    MimeMessage message = jms.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        helper.setTo(u.getEmail());
	        helper.setSubject("💖 Your Perfect Matches Are Waiting! 💖");

	        StringBuilder emailContent = new StringBuilder();
	        emailContent.append("<html>");
	        emailContent.append("<head>");
	        emailContent.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
	        emailContent.append("<style>");
	        emailContent.append("@media only screen and (max-width: 600px) {");
	        emailContent.append("  table { width: 100% !important; }");
	        emailContent.append("  .mobile-stack { display: block !important; width: 100% !important; }");
	        emailContent.append("  .mobile-padding { padding: 8px 5px !important; }");
	        emailContent.append("  .button { width: 90% !important; display: block !important; margin: 0 auto !important; }");
	        emailContent.append("}");
	        emailContent.append("</style>");
	        emailContent.append("</head>");
	        emailContent.append("<body style='background: linear-gradient(135deg, #ffe6f0 0%, #fff0f5 100%); font-family: \"Poppins\", sans-serif; padding: 15px; color: #5c2a9d; margin: 0;'>");

	        emailContent.append("<div style='background: #ffffff; padding: 20px; border-radius: 15px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); max-width: 600px; margin: 0 auto;'>");
	        
	        emailContent.append("<h1 style='text-align: center; color: #d63384; font-size: 24px; margin-bottom: 15px;'>Hey " + u.getName() + "! ⭐(●'◡'●)</h1>");
	        emailContent.append("<p style='text-align: center; font-size: 16px; margin-bottom: 20px;'>Your love story is just about to get exciting! We've found some wonderful people for you 💖✨</p>");
	        
	        emailContent.append("<div style='overflow-x: auto;'>");
	        emailContent.append("<table style='width: 100%; margin-top: 20px; font-size: 14px; border-collapse: collapse; min-width: 300px;'>");
	        emailContent.append("<thead><tr style='background-color: #f8bbd0;'><th style='padding: 10px; text-align: left;'>Name</th><th style='padding: 10px; text-align: left;'>Age</th><th style='padding: 10px; text-align: left;'>Email</th></tr></thead>");
	        emailContent.append("<tbody>");

	        for (User matchedUser : users) {
	            emailContent.append("<tr style='border-bottom: 1px solid #f0f0f0;'>");
	            emailContent.append("<td style='padding: 10px;' class='mobile-padding'>" + matchedUser.getName() + "</td>");
	            emailContent.append("<td style='padding: 10px;' class='mobile-padding'>" + matchedUser.getAge() + "</td>");
	            emailContent.append("<td style='padding: 10px;' class='mobile-padding'>" + matchedUser.getEmail() + "</td>");
	            emailContent.append("</tr>");
	        }

	        emailContent.append("</tbody>");
	        emailContent.append("</table>");
	        emailContent.append("</div>");

	        emailContent.append("<p style='text-align: center; margin-top: 25px; font-size: 16px;'>💌 Ready to meet them? Log in now and start something beautiful! 💌</p>");
	        emailContent.append("<div style='text-align: center; margin-top: 20px; margin-bottom: 10px;'>");
	        emailContent.append("<a href='https://your-dating-app-link.com/login' style='background-color: #e91e63; color: white; padding: 12px 24px; text-decoration: none; border-radius: 25px; font-weight: bold; font-size: 14px;' class='button'>Meet Your Matches</a>");
	        emailContent.append("</div>");

	        emailContent.append("</div>");
	        emailContent.append("</body>");
	        emailContent.append("</html>");

	        helper.setText(emailContent.toString(), true);
	        jms.send(message);

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}




}
