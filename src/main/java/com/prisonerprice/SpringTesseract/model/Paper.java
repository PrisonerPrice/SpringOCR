package com.prisonerprice.SpringTesseract.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Paper {
    @Id
    private String id;
    private String data;
}
