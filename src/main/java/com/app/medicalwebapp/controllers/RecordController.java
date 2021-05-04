package com.app.medicalwebapp.controllers;

import com.app.medicalwebapp.controllers.requestbody.RecordsPageResponse;
import com.app.medicalwebapp.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/records")
public class RecordController {


    @Autowired
    RecordService recordService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecords(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            RecordsPageResponse responseBody;
            if (title != null) {
                responseBody = recordService.getRecordsPage(page, size, title);
            } else {
                responseBody = recordService.getRecordsPageByTopic(page, size, topicId);
            }
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
