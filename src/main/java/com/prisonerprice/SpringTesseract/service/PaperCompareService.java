package com.prisonerprice.SpringTesseract.service;

import com.prisonerprice.SpringTesseract.dto.PaperDto;
import com.prisonerprice.SpringTesseract.model.Paper;
import com.prisonerprice.SpringTesseract.repository.PaperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaperCompareService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final static int TOTAL_COUNTS = 90 + 20;

    @Autowired
    private PaperRepository paperRepository;

    public Paper getPaperById(String id) {
        Optional<Paper> retrievedPaper = paperRepository.findById(id);
        return retrievedPaper.orElse(null);
    }

    public List<PaperDto> getAllPapers() {
        return paperRepository.findAll().stream().map(PaperDto::new).collect(Collectors.toList());
    }

    public String compareTwoPapers(String id1, String id2) {
        Paper paper1 = getPaperById(id1);
        Paper paper2 = getPaperById(id2);
        if (paper1 == null && paper2 == null) {
            return "Papers not found, both ids are not existed!";
        }
        if (paper1 == null) {
            return "id1 not found!";
        }
        if (paper2 == null) {
            return "id2 not found!";
        }

        int count = 0;
        StringBuilder sb = new StringBuilder();
        count = paperComparator("Field1_BusinessNameOfEmployer", paper1.getField1_BusinessNameOfEmployer(), paper2.getField1_BusinessNameOfEmployer(), count, sb);
        count = paperComparator("Field2_EIN", paper1.getField2_EIN(), paper2.getField2_EIN(), count, sb);
        count = paperComparator("Field3_BusinessStreetAddress", paper1.getField3_BusinessStreetAddress(), paper2.getField3_BusinessStreetAddress(), count, sb);
        count = paperComparator("Field4_City", paper1.getField4_City(), paper2.getField4_City(), count, sb);
        count = paperComparator("Field5_State", paper1.getField5_State(), paper2.getField5_State(), count, sb);
        count = paperComparator("Field6_ZipCode", paper1.getField6_ZipCode(), paper2.getField6_ZipCode(), count, sb);
        count = paperComparator("Field7_BusinessPhoneNumber", paper1.getField7_BusinessPhoneNumber(), paper2.getField7_BusinessPhoneNumber(), count, sb);
        count = paperComparator("Field8_SchwabIndividual401K", paper1.isField8_SchwabIndividual401K(), paper2.isField8_SchwabIndividual401K(), count, sb);
        count = paperComparator("Field9_SchwabKeogh", paper1.isField9_SchwabKeogh(), paper2.isField9_SchwabKeogh(), count, sb);
        count = paperComparator("Field10_SchwabQrpMoneyPurchase", paper1.isField10_SchwabQrpMoneyPurchase(), paper2.isField10_SchwabQrpMoneyPurchase(), count, sb);
        count = paperComparator("Field11_SchwabQrpProfileSharing", paper1.isField11_SchwabQrpProfileSharing(), paper2.isField11_SchwabQrpProfileSharing(), count, sb);
        count = paperComparator("Field12_SchwabSepIra", paper1.isField12_SchwabSepIra(), paper2.isField12_SchwabSepIra(), count, sb);
        count = paperComparator("Field13_SchwabSimpleIra", paper1.isField13_SchwabSimpleIra(), paper2.isField13_SchwabSimpleIra(), count, sb);
        count = paperComparator("Field14_GroupMasterNumber", paper1.getField14_GroupMasterNumber(), paper2.getField14_GroupMasterNumber(), count, sb);
        count = paperComparator("Field15_CompanyRetirementAccount", paper1.isField15_CompanyRetirementAccount(), paper2.isField15_CompanyRetirementAccount(), count, sb);
        count = paperComparator("Field16_followingPlanYear", paper1.getField16_followingPlanYear(), paper2.getField16_followingPlanYear(), count, sb);
        count = paperComparator("Field17_forms", paper1.getField17_forms(), paper2.getField17_forms(), count, sb);
        count = paperComparator("Field18_Signature", paper1.getField18_Signature(), paper2.getField18_Signature(), count, sb);
        count = paperComparator("Field19_Date", paper1.getField19_Date(), paper2.getField19_Date(), count, sb);
        count = paperComparator("Field20_PrintName", paper1.getField20_PrintName(), paper2.getField20_PrintName(), count, sb);
        count = paperComparator("Field21_Title", paper1.getField21_Title(), paper2.getField21_Title(), count, sb);

        return "The percentage of similarity is: " + ((double) count / TOTAL_COUNTS) * 100 + "%\n" + sb.toString();
    }

    private int paperComparator(String fieldName, String str1, String str2, int count, StringBuilder sb) {
        if (str1.equals(str2)) {
            count++;
        } else {
            sb.append(fieldName + " not match; Paper 1: " + str1 + " Paper 2: " + str2 + "\n");
        }
        return count;
    }

    private int paperComparator(String fieldName, boolean b1, boolean b2, int count, StringBuilder sb) {
        if (b1 == b2) {
            count++;
        } else {
            sb.append(fieldName + " not match; Paper 1: " + b1 + " Paper 2: " + b2 + "\n");
        }
        return count;
    }

    private int paperComparator(String fieldName, String[][] form1, String[][] form2, int count, StringBuilder sb) {
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 6; col++) {
                if (form1[row][col].equals(form2[row][col])) {
                    count++;
                } else {
                    sb.append(fieldName + " not match at " + "row: " + (row + 1) + " col: " + (col + 1) + "; Paper 1: " + form1[row][col] + " Paper 2: " + form2[row][col] + "\n");
                }
            }
        }
        return count;
    }

    // max adding 10 points
    private int paperComparator(String fieldName, byte[] bytes1, byte[] bytes2, int count, StringBuilder sb) {
        BufferedImage image1;
        BufferedImage image2;
        try {
            InputStream in1 = new ByteArrayInputStream(bytes1);
            image1 = ImageIO.read(in1);
            in1.close();
            InputStream in2 = new ByteArrayInputStream(bytes2);
            image2 = ImageIO.read(in2);
            in2.close();
        } catch (IOException e) {
            e.printStackTrace();
            sb.append(fieldName + " comparing error occurs \n");
            return count;
        }

        int height = image1.getHeight();
        int width = image1.getWidth();

        long difference = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);
                int red1 = (rgb1 >> 16) & 0xff;
                int green1 = (rgb1 >> 8) & 0xff;
                int blue1 = (rgb1) & 0xff;
                int red2 = (rgb2 >> 16) & 0xff;
                int green2 = (rgb2 >> 8) & 0xff;
                int blue2 = (rgb2) & 0xff;
                difference += Math.abs(red1 - red2);
                difference += Math.abs(green1 - green2);
                difference += Math.abs(blue1 - blue2);
            }
        }

        // So total number of pixels = width * height * 3 (because we have r, g, b)
        double total_pixels = height * width * 3;

        // Normalizing the value of different pixels for accuracy(average pixels per color component)
        double avg_different_pixels = difference / total_pixels;

        // There are 255 values of pixels in total
        double percentage = (avg_different_pixels / 255);

        LOG.info(">>>>>>>>>> percentage of difference is: " + percentage);

        if (percentage > 0.1) {
            sb.append(fieldName + " not match \n");
        }

        count += (1 - percentage) * 10;

        return count;
    }

    public String truncateDB() {
        try {
            paperRepository.deleteAll();
            return "Successfully delete the database!";
        } catch (Exception e) {
            return "Delete the database failed! Please try again later";
        }
    }
}
