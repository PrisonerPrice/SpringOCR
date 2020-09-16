package com.prisonerprice.SpringTesseract.controller;

import com.prisonerprice.SpringTesseract.dto.PaperDto;
import com.prisonerprice.SpringTesseract.service.PaperCompareService;
import com.prisonerprice.SpringTesseract.service.PdfToTextService;
import com.prisonerprice.SpringTesseract.service.UploadAndTransformService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "Upload Controller")
@RestController
@RequestMapping(value = {"/rest"})
@CrossOrigin
public class FileUploadController {

    @Autowired
    private UploadAndTransformService uploadAndTransformService;

    @Autowired
    private PaperCompareService paperCompareService;

    @Autowired
    private PdfToTextService pdfToTextService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<PaperDto>> getAllPapers() {
        return new ResponseEntity<>(paperCompareService.getAllPapers(), HttpStatus.OK);
    }

    @PostMapping(value = "/upload_v1")
    public ResponseEntity<String> uploadFile_v1(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(uploadAndTransformService.uploadFile(file), HttpStatus.OK);
    }

    @PostMapping(value = "/upload_v2")
    public ResponseEntity<String> uploadFile_v2(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(pdfToTextService.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping(value = "/compare/{id1}/{id2}")
    public ResponseEntity<String> compareTwoFiles(@PathVariable String id1, @PathVariable String id2) {
        return new ResponseEntity<>(paperCompareService.compareTwoPapers(id1, id2), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> truncateDatabase() {
        return new ResponseEntity<>(paperCompareService.truncateDB(), HttpStatus.OK);
    }
}

