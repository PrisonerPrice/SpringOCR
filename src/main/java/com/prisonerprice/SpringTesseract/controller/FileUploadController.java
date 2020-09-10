package com.prisonerprice.SpringTesseract.controller;

import com.prisonerprice.SpringTesseract.service.PdfToImageToTextService;
import com.prisonerprice.SpringTesseract.service.PdfToTextService;
import io.swagger.annotations.Api;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Upload Controller")
@RestController
@RequestMapping(value = {"/rest"})
public class FileUploadController {

    @Autowired
    private PdfToImageToTextService pdfToImageToTextService;

    @Autowired
    private PdfToTextService pdfToTextService;

    @GetMapping("/")
    public String index() {
        return "uploadbnfnbfbn";
    }

    @PostMapping(value = "/upload_v1")
    public ResponseEntity<String> uploadFile_v1(@RequestParam("file") MultipartFile file) throws IOException, TesseractException {
        return new ResponseEntity<>(pdfToImageToTextService.uploadFile(file), HttpStatus.OK);
    }

    @PostMapping(value = "/upload_v2")
    public ResponseEntity<String> uploadFile_v2(@RequestParam("file") MultipartFile file) throws IOException, TesseractException {
        return new ResponseEntity<>(pdfToTextService.uploadFile(file), HttpStatus.OK);
    }
}

