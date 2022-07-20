package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {

    private String toAddress;
    private String mailTitle;
    private String fromAddress;
    private BookDTO bookDTO;
}
