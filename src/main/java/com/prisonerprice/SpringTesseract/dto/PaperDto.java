package com.prisonerprice.SpringTesseract.dto;

import com.prisonerprice.SpringTesseract.model.Paper;

import java.util.Arrays;

public class PaperDto {
    private String id;
    private String field1_BusinessNameOfEmployer;
    private String field2_EIN;
    private String field3_BusinessStreetAddress;
    private String field4_City;
    private String field5_State;
    private String field6_ZipCode;
    private String field7_BusinessPhoneNumber;
    private boolean field8_SchwabIndividual401K;
    private boolean field9_SchwabKeogh;
    private boolean field10_SchwabQrpMoneyPurchase;
    private boolean field11_SchwabQrpProfileSharing;
    private boolean field12_SchwabSepIra;
    private boolean field13_SchwabSimpleIra;
    private String field14_GroupMasterNumber;
    private boolean field15_CompanyRetirementAccount;
    private String field16_followingPlanYear;
    private String field17_Line1;
    private String field17_Line2;
    private String field17_Line3;
    private String field17_Line4;
    private String field17_Line5;
    private String field17_Line6;
    private String field18_Signature;
    private String field19_Date;
    private String field20_PrintName;
    private String field21_Title;

    public PaperDto() {
    }

    public PaperDto(Paper paper) {
        this.id = paper.getId();
        this.field1_BusinessNameOfEmployer = paper.getField1_BusinessNameOfEmployer();
        this.field2_EIN = paper.getField2_EIN();
        this.field3_BusinessStreetAddress = paper.getField3_BusinessStreetAddress();
        this.field4_City = paper.getField4_City();
        this.field5_State = paper.getField5_State();
        this.field6_ZipCode = paper.getField6_ZipCode();
        this.field7_BusinessPhoneNumber = paper.getField7_BusinessPhoneNumber();
        this.field8_SchwabIndividual401K = paper.isField8_SchwabIndividual401K();
        this.field9_SchwabKeogh = paper.isField9_SchwabKeogh();
        this.field10_SchwabQrpMoneyPurchase = paper.isField10_SchwabQrpMoneyPurchase();
        this.field11_SchwabQrpProfileSharing = paper.isField11_SchwabQrpProfileSharing();
        this.field12_SchwabSepIra = paper.isField12_SchwabSepIra();
        this.field13_SchwabSimpleIra = paper.isField13_SchwabSimpleIra();
        this.field14_GroupMasterNumber = paper.getField14_GroupMasterNumber();
        this.field15_CompanyRetirementAccount = paper.isField15_CompanyRetirementAccount();
        this.field16_followingPlanYear = paper.getField16_followingPlanYear();

        final StringBuilder sb = new StringBuilder();
        Arrays.stream(paper.getField17_forms()[0]).forEach(s -> sb.append(s + " "));
        this.field17_Line1 = sb.toString();

        sb.delete(0, sb.length());
        Arrays.stream(paper.getField17_forms()[1]).forEach(s -> sb.append(s + " "));
        this.field17_Line2 = sb.toString();

        sb.delete(0, sb.length());
        Arrays.stream(paper.getField17_forms()[2]).forEach(s -> sb.append(s + " "));
        this.field17_Line3 = sb.toString();

        sb.delete(0, sb.length());
        Arrays.stream(paper.getField17_forms()[3]).forEach(s -> sb.append(s + " "));
        this.field17_Line4 = sb.toString();

        sb.delete(0, sb.length());
        Arrays.stream(paper.getField17_forms()[4]).forEach(s -> sb.append(s + " "));
        this.field17_Line5 = sb.toString();

        sb.delete(0, sb.length());
        Arrays.stream(paper.getField17_forms()[5]).forEach(s -> sb.append(s + " "));
        this.field17_Line6 = sb.toString();

        this.field18_Signature = paper.getField18_Signature().toString();
        this.field19_Date = paper.getField19_Date().toString();
        this.field20_PrintName = paper.getField20_PrintName();
        this.field21_Title = paper.getField21_Title();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField1_BusinessNameOfEmployer() {
        return field1_BusinessNameOfEmployer;
    }

    public void setField1_BusinessNameOfEmployer(String field1_BusinessNameOfEmployer) {
        this.field1_BusinessNameOfEmployer = field1_BusinessNameOfEmployer;
    }

    public String getField2_EIN() {
        return field2_EIN;
    }

    public void setField2_EIN(String field2_EIN) {
        this.field2_EIN = field2_EIN;
    }

    public String getField3_BusinessStreetAddress() {
        return field3_BusinessStreetAddress;
    }

    public void setField3_BusinessStreetAddress(String field3_BusinessStreetAddress) {
        this.field3_BusinessStreetAddress = field3_BusinessStreetAddress;
    }

    public String getField4_City() {
        return field4_City;
    }

    public void setField4_City(String field4_City) {
        this.field4_City = field4_City;
    }

    public String getField5_State() {
        return field5_State;
    }

    public void setField5_State(String field5_State) {
        this.field5_State = field5_State;
    }

    public String getField6_ZipCode() {
        return field6_ZipCode;
    }

    public void setField6_ZipCode(String field6_ZipCode) {
        this.field6_ZipCode = field6_ZipCode;
    }

    public String getField7_BusinessPhoneNumber() {
        return field7_BusinessPhoneNumber;
    }

    public void setField7_BusinessPhoneNumber(String field7_BusinessPhoneNumber) {
        this.field7_BusinessPhoneNumber = field7_BusinessPhoneNumber;
    }

    public boolean isField8_SchwabIndividual401K() {
        return field8_SchwabIndividual401K;
    }

    public void setField8_SchwabIndividual401K(boolean field8_SchwabIndividual401K) {
        this.field8_SchwabIndividual401K = field8_SchwabIndividual401K;
    }

    public boolean isField9_SchwabKeogh() {
        return field9_SchwabKeogh;
    }

    public void setField9_SchwabKeogh(boolean field9_SchwabKeogh) {
        this.field9_SchwabKeogh = field9_SchwabKeogh;
    }

    public boolean isField10_SchwabQrpMoneyPurchase() {
        return field10_SchwabQrpMoneyPurchase;
    }

    public void setField10_SchwabQrpMoneyPurchase(boolean field10_SchwabQrpMoneyPurchase) {
        this.field10_SchwabQrpMoneyPurchase = field10_SchwabQrpMoneyPurchase;
    }

    public boolean isField11_SchwabQrpProfileSharing() {
        return field11_SchwabQrpProfileSharing;
    }

    public void setField11_SchwabQrpProfileSharing(boolean field11_SchwabQrpProfileSharing) {
        this.field11_SchwabQrpProfileSharing = field11_SchwabQrpProfileSharing;
    }

    public boolean isField12_SchwabSepIra() {
        return field12_SchwabSepIra;
    }

    public void setField12_SchwabSepIra(boolean field12_SchwabSepIra) {
        this.field12_SchwabSepIra = field12_SchwabSepIra;
    }

    public boolean isField13_SchwabSimpleIra() {
        return field13_SchwabSimpleIra;
    }

    public void setField13_SchwabSimpleIra(boolean field13_SchwabSimpleIra) {
        this.field13_SchwabSimpleIra = field13_SchwabSimpleIra;
    }

    public String getField14_GroupMasterNumber() {
        return field14_GroupMasterNumber;
    }

    public void setField14_GroupMasterNumber(String field14_GroupMasterNumber) {
        this.field14_GroupMasterNumber = field14_GroupMasterNumber;
    }

    public boolean isField15_CompanyRetirementAccount() {
        return field15_CompanyRetirementAccount;
    }

    public void setField15_CompanyRetirementAccount(boolean field15_CompanyRetirementAccount) {
        this.field15_CompanyRetirementAccount = field15_CompanyRetirementAccount;
    }

    public String getField16_followingPlanYear() {
        return field16_followingPlanYear;
    }

    public void setField16_followingPlanYear(String field16_followingPlanYear) {
        this.field16_followingPlanYear = field16_followingPlanYear;
    }

    public String getField17_Line1() {
        return field17_Line1;
    }

    public void setField17_Line1(String field17_Line1) {
        this.field17_Line1 = field17_Line1;
    }

    public String getField17_Line2() {
        return field17_Line2;
    }

    public void setField17_Line2(String field17_Line2) {
        this.field17_Line2 = field17_Line2;
    }

    public String getField17_Line3() {
        return field17_Line3;
    }

    public void setField17_Line3(String field17_Line3) {
        this.field17_Line3 = field17_Line3;
    }

    public String getField17_Line4() {
        return field17_Line4;
    }

    public void setField17_Line4(String field17_Line4) {
        this.field17_Line4 = field17_Line4;
    }

    public String getField17_Line5() {
        return field17_Line5;
    }

    public void setField17_Line5(String field17_Line5) {
        this.field17_Line5 = field17_Line5;
    }

    public String getField17_Line6() {
        return field17_Line6;
    }

    public void setField17_Line6(String field17_Line6) {
        this.field17_Line6 = field17_Line6;
    }

    public String getField18_Signature() {
        return field18_Signature;
    }

    public void setField18_Signature(String field18_Signature) {
        this.field18_Signature = field18_Signature;
    }

    public String getField19_Date() {
        return field19_Date;
    }

    public void setField19_Date(String field19_Date) {
        this.field19_Date = field19_Date;
    }

    public String getField20_PrintName() {
        return field20_PrintName;
    }

    public void setField20_PrintName(String field20_PrintName) {
        this.field20_PrintName = field20_PrintName;
    }

    public String getField21_Title() {
        return field21_Title;
    }

    public void setField21_Title(String field21_Title) {
        this.field21_Title = field21_Title;
    }
}
