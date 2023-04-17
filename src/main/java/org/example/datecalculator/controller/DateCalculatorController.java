package org.example.datecalculator.controller;

import org.example.datecalculator.email.EmailService;
import org.example.datecalculator.model.*;
import org.example.datecalculator.service.DateCalculatorService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/swagger-ui.html
@Slf4j
@RestController
@RequestMapping("/api/v1/date")
public class DateCalculatorController {
    private DateCalculatorService dateCalculatorService;
    private EmailService emailService;
    public DateCalculatorController(DateCalculatorService dateCalculatorService, EmailService emailService) {
        this.dateCalculatorService = dateCalculatorService;
        this.emailService = emailService;
    }

    @PostMapping("/sendfile")
    ResponseEntity<String> sendEMailWithAttachment() {
        File path = new File("/Users/chuck/code/DateCalculator/");
        emailService.sendEmail("chuckhutchinson2@icloud.com",
                "chuckhutchinson2@icloud.com",
                "pom.xml",
                "email body", new File(path,"pom.xml"));

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/sendemailviatemplate")
    ResponseEntity<String> sendEMailViaTemplate() {

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", "bob");
        templateModel.put("text", "take a look at the attached pom.xml. It has the stuff we need to send emails");
        templateModel.put("senderName", "jack");

        File path = new File("/Users/chuck/code/DateCalculator/pom.xml");
        emailService.sendMessage("chuckhutchinson2@icloud.com",
                "chuckhutchinson2@icloud.com",
                "pom.xml", "template-thymeleaf.html",
                templateModel, path);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /*

     */

    @PostMapping("/send")
    ResponseEntity<String> sendEMail() {
        emailService.sendTextEmail("chuckhutchinson2@icloud.com", "chuckhutchinson2@icloud.com", "subject", "email body");

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/daysbetween")
    ResponseEntity<Long> daysBetween(@RequestBody DaysBetweenRequest daysBetweenRequest) {

        Long daysBetween = dateCalculatorService.daysBetween(daysBetweenRequest);

        return new ResponseEntity<>(daysBetween, HttpStatus.OK);
    }

    @PostMapping("/workingdaysbetween")
    ResponseEntity<Long> workingDaysBetween(@RequestBody DaysBetweenRequest daysBetweenRequest) {

        Long daysBetween = dateCalculatorService.workingDaysBetween(daysBetweenRequest);

        return new ResponseEntity<>(daysBetween, HttpStatus.OK);
    }

    @PostMapping("/calculate")
    ResponseEntity<LocalDate> calculate(@RequestBody CalculateDateRequest calculateDateRequest) {

        LocalDate dateCalculated = dateCalculatorService.calculate(calculateDateRequest);

        return new ResponseEntity<>(dateCalculated, HttpStatus.OK);
    }
}
