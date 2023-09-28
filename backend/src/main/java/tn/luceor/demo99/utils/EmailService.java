package tn.luceor.demo99.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> listOfEmailsToSend){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("connect4Aid@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(listOfEmailsToSend != null && listOfEmailsToSend.size()>0)
            //creation methode convertListOfEmailsToArray car setCc te9bel ken array me te9belch list
             message.setCc(convertListOfEmailsToArray(listOfEmailsToSend));
        emailSender.send(message);
    }

    private String[] convertListOfEmailsToArray(List<String> emailList){
        String[] tab = new String[emailList.size()];
        for(int i = 0 ; i< emailList.size();i++){
            tab[i] = emailList.get(i);
        }
        return tab;
    }

    public void forgotPasswordMail(String to,String subject,String password) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("connect4Aid@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMessage = "<p><b>Your Login details for Connect4Aid Account</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/home\">Click here to login</a></p>";
        message.setContent(htmlMessage,"text/html");
        emailSender.send(message);
    }
}
