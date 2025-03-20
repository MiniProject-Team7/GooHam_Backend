package com.uplus.ureka.controller.user;

import com.uplus.ureka.dto.user.mail.MailHtmlSendDto;
import com.uplus.ureka.dto.user.mail.MailTxtSendDto;
import com.uplus.ureka.service.user.mail.MailSendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("gooham/users/email")
public class MailSendController {

    private final MailSendService mailSendService;

    public MailSendController(MailSendService mailSendService) {
        this.mailSendService = mailSendService;
    }

    /**
     * 텍스트 이메일을 전송합니다.
     *
     * @param mailTxtSendDto
     * @return
     */
    @PostMapping("/txtEmail")
    public ResponseEntity<Object> sendTxtEmail(@RequestBody MailTxtSendDto mailTxtSendDto) {
        mailSendService.sendTxtEmail(mailTxtSendDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * HTML 구성 기반 이메일을 전송합니다.
     *
     * @param mailHtmlSendDto
     * @return
     */
    @PostMapping("/htmlEmail")
    public ResponseEntity<Object> sendHtmlEmail(@RequestBody MailHtmlSendDto mailHtmlSendDto) {
        mailSendService.sendHtmlEmail(mailHtmlSendDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}