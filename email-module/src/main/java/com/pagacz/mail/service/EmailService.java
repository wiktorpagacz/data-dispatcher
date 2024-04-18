package com.pagacz.mail.service;

import com.pagacz.database.dto.BaseDto;
import com.pagacz.mail.config.EmailConfig;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmailService<T extends BaseDto> {

    private final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    EmailConfig emailConfig;

    @Value("${EMAIL_RECIPIENTS}")
    private String emailRecipients;

    @Value("${EMAIL_NAME}")
    private String emailName;

    public void sendEmailNew(String subject, String mail, String recipients, String sender) {
        log.info("Preparing message and sending email");
        Message message = emailConfig.prepareMessage(mail, subject, sender, recipients);
        sendMail(message);
        log.info("Email sent.");
    }

    public void sendEmailWithOffers(String email, String emailRecipients, String emailSender) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        sendEmailNew("Nowe oferty z mieszkaniami " + dtf.format(now), email, emailRecipients, emailSender);
    }

    private void sendMail(Message message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error occurred at sending message stage", e);
        }
    }

    public void prepareAndSendMailWithOffers(List<T> offersToSend) {
        MailCreatorService<T> mailCreatorService = new MailCreatorService<>();
        String mailMessage = mailCreatorService.createMailFromOffers(offersToSend);
        sendEmailWithOffers(mailMessage, emailRecipients, emailName);
    }
}
