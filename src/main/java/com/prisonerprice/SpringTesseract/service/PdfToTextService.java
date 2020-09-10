package com.prisonerprice.SpringTesseract.service;

import com.prisonerprice.SpringTesseract.repository.PaperRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfToTextService {

    @Autowired
    private PaperRepository paperRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        long initTime = System.currentTimeMillis();
        String returnedText = "";

        // save file in local
        File currDir = new File(".");
        Path path = Paths.get(currDir.getAbsolutePath() + file.getOriginalFilename());
        File dstDir = new File(path.toString());
        try (OutputStream os = new FileOutputStream(dstDir)) {
            os.write(file.getBytes());
        }

        PDDocument document = PDDocument.load(dstDir);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        Files.delete(path);

        long endTime = System.currentTimeMillis();
        returnedText = "Time taken: " + (endTime - initTime) + "\n" + text;

        return returnedText;
    }
}
