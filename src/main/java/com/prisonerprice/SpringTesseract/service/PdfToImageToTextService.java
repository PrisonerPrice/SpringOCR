package com.prisonerprice.SpringTesseract.service;

import com.prisonerprice.SpringTesseract.repository.PaperRepository;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.ghost4j.document.PDFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfToImageToTextService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final static int RENDER_DPI = 400;

    @Autowired
    private PaperRepository paperRepository;

    public String uploadFile(MultipartFile file) throws IOException, TesseractException {
        long initTime = System.currentTimeMillis();
        String returnedText = "";
        Map<String, String> data = new HashMap<>();

        // save file in local
        File currDir = new File(".");
        Path path = Paths.get(currDir.getAbsolutePath() + file.getOriginalFilename());
        File dstDir = new File(path.toString());
        try (OutputStream os = new FileOutputStream(dstDir)) {
            os.write(file.getBytes());
        }

        List<BufferedImage> images = generateBufferedImagesFromPdf(dstDir);
        Tesseract tesseract = new Tesseract();
        tesseract.setTessVariable("user_defined_dpi", String.valueOf(RENDER_DPI));
        //tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_CHAR);
        tesseract.setDatapath(currDir.getAbsolutePath());
//        for (BufferedImage bufferedImage : images) {
//            LOG.info("Width is: " + bufferedImage.getWidth());
//            LOG.info("Height is: " + bufferedImage.getHeight());
//            returnedText += "[page " + page + "]\n";
//            returnedText += tesseract.doOCR(bufferedImage, new Rectangle(0, 0, 3399, 4400));
//            page++;
//        }

        // scan data in page 1
        returnedText += "[page " + 1 + "]\n";
        // field: 1_BusinessNameOfEmployer
        String value1 = tesseract.doOCR(images.get(0), new Rectangle(780, 1175, 1540, 100)).trim();
        returnedText += value1 + "\n";
        data.put("1_BusinessNameOfEmployer", value1);

        // field: 2_EIN
        String value2Row = tesseract.doOCR(images.get(0), new Rectangle(2370, 1175, 840, 100)).trim();
        String value2 = value2Row.replaceAll(" ", "");
        returnedText += value2 + "\n";
        data.put("2_EIN", value2);

        // field: 3_BusinessStreetAddress
        String value3 = tesseract.doOCR(images.get(0), new Rectangle(780, 1370, 1125, 100)).trim();
        returnedText += value3 + "\n";
        data.put("3_BusinessStreetAddress", value3);

        // field: 4_City
        String value4 = tesseract.doOCR(images.get(0), new Rectangle(1955, 1370, 565, 100)).trim();
        returnedText += value4 + "\n";
        data.put("4_City", value4);

        // field: 5_State
        String value5 = tesseract.doOCR(images.get(0), new Rectangle(2560, 1370, 220, 100)).trim();
        returnedText += value5 + "\n";
        data.put("5_State", value5);

        // field: 6_ZipCode
        String value6 = tesseract.doOCR(images.get(0), new Rectangle(2835, 1370, 250, 100)).trim();
        returnedText += value6 + "\n";
        data.put("6_ZipCode", value6);

        // field: 7_BusinessPhoneNumber
        String value7 = tesseract.doOCR(images.get(0), new Rectangle(780, 1535, 630, 100)).trim();
        returnedText += value7 + "\n";
        data.put("7_BusinessPhoneNumber", value7);

        // field: 8_SchwabIndividual401K
        String value8 = tesseract.doOCR(images.get(0), new Rectangle(795, 1840, 70, 70)).trim();
        returnedText += value8 + "\n";
        data.put("8_SchwabIndividual401K", value8);

        // field: 9_SchwabKeogh
        String value9 = tesseract.doOCR(images.get(0), new Rectangle(1405, 1840, 70, 70)).trim();
        returnedText += value9 + "\n";
        data.put("9_SchwabKeogh", value8);

        // field: 10_SchwabQrpMoneyPurchase
        String value10 = tesseract.doOCR(images.get(0), new Rectangle(1817, 1840, 70, 70)).trim();
        returnedText += value10 + "\n";
        data.put("10_SchwabQrpMoneyPurchase", value10);

        // field: 11_SchwabQrpProfileSharing
        String value11 = tesseract.doOCR(images.get(0), new Rectangle(2542, 1840, 70, 70)).trim();
        returnedText += value11 + "\n";
        data.put("11_SchwabQrpProfileSharing", value11);

        // field: 12_SchwabSepIra
        String value12 = tesseract.doOCR(images.get(0), new Rectangle(795, 1929, 70, 70)).trim();
        returnedText += value12 + "\n";
        data.put("12_SchwabSepIra", value12);

        // field: 13_SchwabSimpleIra
        String value13 = tesseract.doOCR(images.get(0), new Rectangle(1240, 1929, 70, 70)).trim();
        returnedText += value13 + "\n";
        data.put("13_SchwabSimpleIra", value13);

        // field: 14_GroupMasterNumber
        String value14 = tesseract.doOCR(images.get(0), new Rectangle(2405, 1929, 800, 100)).trim();
        returnedText += value14 + "\n";
        data.put("14_GroupMasterNumber", value14);

        // field: 15_CompanyRetirementAccount
        String value15 = tesseract.doOCR(images.get(0), new Rectangle(795, 2022, 70, 70)).trim();
        returnedText += value15 + "\n";
        data.put("15_CompanyRetirementAccount", value15);

        // field: 16_followingPlanYear
        String value16 = tesseract.doOCR(images.get(0), new Rectangle(2364, 2275, 300, 90)).trim();
        returnedText += value16 + "\n";
        data.put("16_followingPlanYear", value16);

        // field: 17_form_rx_cx
        int[] xValues = new int[] {550, 1178, 1645, 2150, 2528, 2873};
        int[] yValues = new int[] {2650, 2755, 2860, 2965, 3070, 3175, 3280, 3385, 3490, 3495, 3605, 3710};
        int[] widthes = new int[] {600, 415, 405, 275, 240, 300};
        for (int row = 0; row < 12; row++) {
            String line = "line " + (row + 1) + ": ";
            for (int col = 0; col < 6; col++) {
                String key = "17_form" + "_r" + row + "_c" + col;
                String value = tesseract.doOCR(
                        images.get(0),
                        new Rectangle(xValues[col], yValues[row], widthes[col], 100)
                ).trim();
                data.put(key, value);
                line += value + " ";
            }
            returnedText += line + "\n";
        }

        Files.delete(path);
        long endTime = System.currentTimeMillis();
        returnedText = "Time taken: " + (endTime - initTime) + "\n" + returnedText;

        return returnedText;
    }

    private List<BufferedImage> generateBufferedImagesFromPdf(File dstDir) throws IOException {
        List<BufferedImage> images = new ArrayList<>();

        PDDocument document = PDDocument.load(dstDir);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            images.add(pdfRenderer.renderImageWithDPI(page, RENDER_DPI, ImageType.RGB));
        }

        document.close();
        return images;
    }
}
