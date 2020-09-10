package com.prisonerprice.SpringTesseract.service;

import com.prisonerprice.SpringTesseract.model.Paper;
import com.prisonerprice.SpringTesseract.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileCompareService {

    @Autowired
    private PaperRepository paperRepository;

    public Paper getPaperById(String id) {
        Optional<Paper> retrievedPaper = paperRepository.findById(id);
        return retrievedPaper.orElse(null);
    }

    public List<Paper> getAllPapers() {
        return paperRepository.findAll();
    }
}
