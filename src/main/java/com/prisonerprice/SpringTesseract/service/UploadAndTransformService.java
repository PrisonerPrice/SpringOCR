package com.prisonerprice.SpringTesseract.service;

import com.prisonerprice.SpringTesseract.model.Paper;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UploadAndTransformService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final static int RENDER_DPI = 400;
    private final File currDir = new File(".");
    private final static int FINISH_COUNT = 92;
    private final static int TASK_PER_THREAD = 10;

    @Autowired
    private PaperRepository paperRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        long initTime = System.currentTimeMillis();
        ConcurrentMap<String, String> data = new ConcurrentHashMap<>();
        List<String> keyArray = new ArrayList<>();

        // save file in local
        Path path = Paths.get(currDir.getAbsolutePath() + file.getOriginalFilename());
        File dstDir = new File(path.toString());
        try (OutputStream os = new FileOutputStream(dstDir)) {
            os.write(file.getBytes());
        }

        List<BufferedImage> images = generateBufferedImagesFromPdf(dstDir);
        List<OcrTask> ocrTasks = new ArrayList<>();

        // field: 1_BusinessNameOfEmployer
        ocrTasks.add(new OcrTask("1_BusinessNameOfEmployer", new Rectangle(780, 1175, 1540, 100), -1, images.get(0)));
        keyArray.add("1_BusinessNameOfEmployer");

        // field: 2_EIN
        ocrTasks.add(new OcrTask("2_EIN", new Rectangle(2370, 1175, 840, 100), -1, images.get(0)));
        keyArray.add("2_EIN");

        // field: 3_BusinessStreetAddress
        ocrTasks.add(new OcrTask("3_BusinessStreetAddress", new Rectangle(780, 1365, 1125, 100), -1, images.get(0)));
        keyArray.add("3_BusinessStreetAddress");

        // field: 4_City
        ocrTasks.add(new OcrTask("4_City", new Rectangle(1955, 1365, 565, 100), -1, images.get(0)));
        keyArray.add("4_City");

        // field: 5_State
        ocrTasks.add(new OcrTask("5_State", new Rectangle(2560, 1365, 220, 100), -1, images.get(0)));
        keyArray.add("5_State");

        // field: 6_ZipCode
        ocrTasks.add(new OcrTask("6_ZipCode", new Rectangle(2835, 1365, 250, 100), -1, images.get(0)));
        keyArray.add("6_ZipCode");

        // field: 7_BusinessPhoneNumber
        ocrTasks.add(new OcrTask("7_BusinessPhoneNumber", new Rectangle(780, 1535, 630, 100), -1, images.get(0)));
        keyArray.add("7_BusinessPhoneNumber");

        // field: 8_SchwabIndividual401K
        ocrTasks.add(new OcrTask("8_SchwabIndividual401K", new Rectangle(795, 1840, 70, 70), -1, images.get(0)));
        keyArray.add("8_SchwabIndividual401K");

        // field: 9_SchwabKeogh
        ocrTasks.add(new OcrTask("9_SchwabKeogh", new Rectangle(1405, 1840, 70, 70), -1, images.get(0)));
        keyArray.add("9_SchwabKeogh");

        // field: 10_SchwabQrpMoneyPurchase
        ocrTasks.add(new OcrTask("10_SchwabQrpMoneyPurchase", new Rectangle(1817, 1840, 70, 70), -1, images.get(0)));
        keyArray.add("10_SchwabQrpMoneyPurchase");

        // field: 11_SchwabQrpProfileSharing
        ocrTasks.add(new OcrTask("11_SchwabQrpProfileSharing", new Rectangle(2542, 1840, 70, 70), -1, images.get(0)));
        keyArray.add("11_SchwabQrpProfileSharing");

        // field: 12_SchwabSepIra
        ocrTasks.add(new OcrTask("12_SchwabSepIra", new Rectangle(795, 1929, 70, 70), -1, images.get(0)));
        keyArray.add("12_SchwabSepIra");

        // field: 13_SchwabSimpleIra
        ocrTasks.add(new OcrTask("13_SchwabSimpleIra", new Rectangle(1240, 1929, 70, 70), -1, images.get(0)));
        keyArray.add("13_SchwabSimpleIra");

        // field: 14_GroupMasterNumber
        ocrTasks.add(new OcrTask("14_GroupMasterNumber", new Rectangle(2405, 1929, 800, 100), -1, images.get(0)));
        keyArray.add("14_GroupMasterNumber");

        // field: 15_CompanyRetirementAccount
        ocrTasks.add(new OcrTask("15_CompanyRetirementAccount", new Rectangle(795, 2022, 70, 70), -1, images.get(0)));
        keyArray.add("15_CompanyRetirementAccount");

        // field: 16_followingPlanYear
        ocrTasks.add(new OcrTask("16_followingPlanYear", new Rectangle(2364, 2275, 300, 90), -1, images.get(0)));
        keyArray.add("16_followingPlanYear");

        // field: 17_form_rx_cx
        int[] xValues = new int[] {550, 1178, 1645, 2150, 2528, 2873};
        int[] yValues = new int[] {2650, 2755, 2860, 2965, 3070, 3175, 3280, 3385, 3490, 3495, 3605, 3710};
        int[] widthes = new int[] {600, 415, 405, 275, 240, 300};
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 6; col++) {
                String key = "17_form" + "_r" + row + "_c" + col;
                ocrTasks.add(new OcrTask(key, new Rectangle(xValues[col], yValues[row], widthes[col], 100), -1, images.get(0)));
                keyArray.add(key);
            }
        }

        // field: 18_Signature
        ocrTasks.add(new OcrTask("18_Signature", new Rectangle(805, 846, 1650, 167), -1, images.get(1)));
        keyArray.add("18_Signature");
        BufferedImage signatureImage = images.get(1).getSubimage(805, 846, 1650, 167);
        ByteArrayOutputStream signatureBos = new ByteArrayOutputStream();
        ImageIO.write(signatureImage, "jpg", signatureBos );
        byte [] signatureBytes = signatureBos.toByteArray();
        signatureBos.close();

        // field: 19_Date
        ocrTasks.add(new OcrTask("19_Date", new Rectangle(2515, 842, 690, 170), -1, images.get(1)));
        keyArray.add("19_Date");
        BufferedImage dateImage = images.get(1).getSubimage(2515, 842, 690, 170);
        ByteArrayOutputStream dateBos = new ByteArrayOutputStream();
        ImageIO.write(dateImage, "jpg", dateBos );
        byte [] dateBytes = dateBos.toByteArray();
        dateBos.close();

        // field: 20_PrintName
        ocrTasks.add(new OcrTask("20_PrintName", new Rectangle(800, 1100, 1290, 90), -1, images.get(1)));
        keyArray.add("20_PrintName");

        // field: 21_Title
        ocrTasks.add(new OcrTask("21_Title", new Rectangle(2125, 1100, 1060, 90), -1, images.get(1)));
        keyArray.add("21_Title");

        LOG.info("Finish creating tasks, time taken: " + (System.currentTimeMillis() - initTime));

        long initProcessingTime = System.currentTimeMillis();
        List<OcrTask> readyToGoTasks = new ArrayList<>();
        for (OcrTask task : ocrTasks) {
            readyToGoTasks.add(task);
            if (readyToGoTasks.size() == TASK_PER_THREAD) {
                runTasks(data, new ArrayList<>(readyToGoTasks));
                readyToGoTasks.clear();
            }
        }
        runTasks(data, new ArrayList<>(readyToGoTasks));

        // empty looper, used for waiting all tasks finish
        int count = 0;
        while (data.size() < FINISH_COUNT) {
            if (data.size() > count) {
                LOG.info("- In processing - " + count + " tasks finished");
                LOG.info("- In processing - " + (System.currentTimeMillis() - initProcessingTime) / 1000 + " seconds eclipsed");
                count = data.size();
            }
        }

        // Log info for signature and date
        LOG.info("Signature: {}; Date: {}", data.get(keyArray.get(88)), data.get(keyArray.get(89)));

        Files.delete(path);
        long endTime = System.currentTimeMillis();

        // generate output
        StringBuffer sb = new StringBuffer();
        sb.append("Time taken: " + (endTime - initTime) + " Finished at: " + new Date(endTime) + "\n");
        int i;
        for (i = 0; i < 16; i++) {
            sb.append(keyArray.get(i) + ": " + data.get(keyArray.get(i)) + "\n");
        }
        i = 16;
        for (int row = 0; row < 12; row++) {
            String line = "17_form_line" + (row + 1) + ":";
            for (int j = 0; j < 6; j++, i++) {
                line += " " + data.get(keyArray.get(i));
            }
            sb.append(line + "\n");
        }
        for (i = 88; i < 92; i++) {
            sb.append(keyArray.get(i) + ": " + data.get(keyArray.get(i)) + "\n");
        }

        saveToDatabase(data, signatureBytes, dateBytes, keyArray);
        return sb.toString();
    }

    private void saveToDatabase(Map<String, String> data, byte[] signatureBytes, byte[] dateBytes, List<String> keyList) {
        Paper paper = new Paper();
        paper.setField1_BusinessNameOfEmployer(data.get(keyList.get(0)));
        paper.setField2_EIN(data.get(keyList.get(1)));
        paper.setField3_BusinessStreetAddress(data.get(keyList.get(2)));
        paper.setField4_City(data.get(keyList.get(3)));
        paper.setField5_State(data.get(keyList.get(4)));
        paper.setField6_ZipCode(data.get(keyList.get(5)));
        paper.setField7_BusinessPhoneNumber(data.get(keyList.get(6)));
        paper.setField8_SchwabIndividual401K(data.get(keyList.get(7)).equals("[m]"));
        paper.setField9_SchwabKeogh(data.get(keyList.get(8)).endsWith("[m]"));
        paper.setField10_SchwabQrpMoneyPurchase(data.get(keyList.get(9)).equals("[m]"));
        paper.setField11_SchwabQrpProfileSharing(data.get(keyList.get(10)).equals("[m]"));
        paper.setField12_SchwabSepIra(data.get(keyList.get(11)).equals("[m]"));
        paper.setField13_SchwabSimpleIra(data.get(keyList.get(12)).endsWith("[m]"));
        paper.setField14_GroupMasterNumber(data.get(keyList.get(13)));
        paper.setField15_CompanyRetirementAccount(data.get(keyList.get(14)).equals("[m]"));
        paper.setField16_followingPlanYear(data.get(keyList.get(15)));
        String[][] form = new String[12][6];
        int index = 16;
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 6; col++, index++) {
                form[row][col] = data.get(keyList.get(index));
            }
        }
        paper.setField17_forms(form);
        paper.setField18_Signature(signatureBytes);
        paper.setField19_Date(dateBytes);
        paper.setField20_PrintName(data.get(keyList.get(90)));
        paper.setField21_Title(data.get(keyList.get(91)));
        paperRepository.save(paper);
        LOG.info("Successfully save to db");
    }

    private List<BufferedImage> generateBufferedImagesFromPdf(File dstDir) throws IOException {
        List<BufferedImage> images = new ArrayList<>();

        PDDocument document = PDDocument.load(dstDir);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        for (int page = 0; page < 2; page++) {
            images.add(pdfRenderer.renderImageWithDPI(page, RENDER_DPI, ImageType.RGB));
        }

        document.close();
        return images;
    }

    private void runTasks(Map<String, String> data, List<OcrTask> tasks) {
        Tesseract tesseract = new Tesseract();
        tesseract.setTessVariable("user_defined_dpi", String.valueOf(RENDER_DPI));
        tesseract.setDatapath("/app");
        //tesseract.setDatapath(".");
        Runnable runnable = () -> {
            LOG.info("New thread started!");
            for (OcrTask task : tasks) {
                if (task.mode != -1) {
                    tesseract.setPageSegMode(task.mode);
                }
                String value = null;
                try {
                    value = tesseract.doOCR(task.image, task.rectangle).trim();
                } catch (TesseractException e) {
                    e.printStackTrace();
                }
                data.put(task.key, value);
                LOG.info("Finish one task! Task key is: " + task.key);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public class OcrTask {
        String key;
        Rectangle rectangle;
        int mode;
        BufferedImage image;

        public OcrTask(String key, Rectangle rectangle, int mode, BufferedImage image) {
            this.key = key;
            this.rectangle = rectangle;
            this.mode = mode;
            this.image = image;
        }
    }
}


//tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_CHAR);
