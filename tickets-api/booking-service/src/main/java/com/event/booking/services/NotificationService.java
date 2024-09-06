package com.event.booking.services;

import com.event.booking.dtos.EmailDetails;
import com.event.booking.dtos.EventDto;
import com.event.booking.entities.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {


  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String emailSender;

  @Async
  public void sendBookingConfirmationEmail(EventDto eventDto, Booking booking) {

    try {
      String message = "Dear " + booking.getUserEmail() + ",\n\n" +
          "Your booking has been confirmed.\n\n" +
          "Booking Details:\n" +
          "Event Name: " + eventDto.getName() + "\n" +
          "Event Date: " + eventDto.getDate().toString() + "\n" +
          "Event Location: " + eventDto.getLocation() + "\n" +
          "User Name: " + booking.getUserEmail() + "\n" +
          "Ticket Type: " + booking.getTicketType() + "\n" +
          "Number of Tickets: " + booking.getNoOfTickets() + "\n\n" +
          "Payment Amount: Rs " + String.format("%.2f", booking.getTotalPrice()) + "\n\n";

      sendEmail(EmailDetails.builder().recipient(booking.getUserEmail())
          .subject("event booking confirmation").messageBody(message).build());

    } catch (Exception exception) {

      log.error("error sending email :" + exception.getMessage());
    }
  }



  private void sendEmail(EmailDetails emailDetails){

      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom(emailSender);
      mailMessage.setTo(emailDetails.getRecipient());
      mailMessage.setText(emailDetails.getMessageBody());
      mailMessage.setSubject(emailDetails.getSubject());
      javaMailSender.send(mailMessage);
      log.info("Mail sent successfully");

  }
}
