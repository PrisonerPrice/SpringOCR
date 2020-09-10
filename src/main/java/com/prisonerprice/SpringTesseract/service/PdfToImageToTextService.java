package com.prisonerprice.SpringTesseract.service;

import com.prisonerprice.SpringTesseract.repository.PaperRepository;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.ghost4j.document.PDFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfToImageToTextService {
    private final static int RENDER_DPI = 300;

    @Autowired
    private PaperRepository paperRepository;

    public String uploadFile(MultipartFile file) throws IOException, TesseractException {
        long initTime = System.currentTimeMillis();
        String returnedText = "";

        // save file in local
        File currDir = new File(".");
        Path path = Paths.get(currDir.getAbsolutePath() + file.getOriginalFilename());
        File dstDir = new File(path.toString());
        try (OutputStream os = new FileOutputStream(dstDir)) {
            os.write(file.getBytes());
        }

        // transform pdf into images
        List<String> pages = generateImageFromPdf(dstDir);
        for (String page : pages) {
            String pageDir = currDir.getAbsolutePath() + "/" + page;
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(currDir.getAbsolutePath());
            returnedText += page + "\n";
            returnedText += tesseract.doOCR(new File(pageDir));
            Files.delete(Paths.get(pageDir));
        }

        Files.delete(path);
        long endTime = System.currentTimeMillis();
        returnedText = "Time taken: " + (endTime - initTime) + "\n" + returnedText;

        return returnedText;
    }

    private List<String> generateImageFromPdf(File dstDir) throws IOException {
        List<String> pages = new ArrayList<>();

        PDDocument document = PDDocument.load(dstDir);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, RENDER_DPI, ImageType.RGB);
            ImageIOUtil.writeImage(bufferedImage, "pic_" + (page + 1) + ".jpg", RENDER_DPI);
            pages.add("pic_" + (page + 1) + ".jpg");
        }
        document.close();
        return pages;
    }
}
