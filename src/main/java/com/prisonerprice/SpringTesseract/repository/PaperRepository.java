package com.prisonerprice.SpringTesseract.repository;

import com.prisonerprice.SpringTesseract.model.Paper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends MongoRepository<Paper, String> {
}
