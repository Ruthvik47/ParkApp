package com.abcexample.parkapp;

import android.content.Context;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailSSL {

    Context c;
    String tomail;
    String frommail;
    String frompass;
    String Bodyofthemail;

    public SendMailSSL() {
    }

    public SendMailSSL(Context c, String tomail, String frommail, String frompass, String bodyofthemail) {
        this.c = c;
        this.tomail = tomail;
        this.frommail = frommail;
        this.frompass = frompass;
        Bodyofthemail = bodyofthemail;
    }


    void sendmail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(frommail, frompass);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(frommail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(tomail));
            message.setSubject("ParkApp");
            message.setText(Bodyofthemail);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
