package com.skorobagatiy.ClientDB.controllers;

import com.skorobagatiy.ClientDB.services.UserDownloadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(UserDownloadController.USERS_DOWNLOAD_URL)
public class UserDownloadController {
    private static final Logger logger = LogManager.getLogger(UserDownloadController.class);
    public static final String USERS_DOWNLOAD_URL = "/users/download";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");

    private UserDownloadService userDownloadService;

    @Autowired
    public UserDownloadController(UserDownloadService userDownloadService) {
        this.userDownloadService = userDownloadService;
    }

    @GetMapping
    public ResponseEntity<Resource> createReportForAllUsers(
            @RequestParam(value = "format", defaultValue = "excel") String format) throws IOException {

        String dateNow = DATE_TIME_FORMATTER.format(LocalDateTime.now());
        String exportFileName = "";
        String mediaType = "";

        InputStreamResource file = null;
        if (format.equals("csv")) {
            logger.info("Export data to CSV format Started");

            file = new InputStreamResource(userDownloadService.loadCsv());
            exportFileName = "overall-users-for_{date}.csv".replace("{date}", dateNow);
            mediaType = "application/csv";
        } else {
            logger.info("Export data to Excel Started");

            file = new InputStreamResource(userDownloadService.loadExcel());

            exportFileName = "overall-users-_{date}.xlsx".replace("{date}", dateNow);
            mediaType = "application/vnd.ms-excel";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + exportFileName)
                .contentType(MediaType.parseMediaType(mediaType))
                .body(file);
    }
}
