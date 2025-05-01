package com.pymes.inventario.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AlertaCorreoService {

    private final JavaMailSender mailSender;

    public AlertaCorreoService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarAlerta(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(destinatario);
        mail.setSubject(asunto);
        mail.setText(mensaje);
        mailSender.send(mail);
    }
}
