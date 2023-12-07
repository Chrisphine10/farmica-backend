package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.SalesRepo;
import com.bhachu.farmica.domain.SalesDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesService {

    @Autowired
    private SalesRepo salesRepo;

    public SalesService() {}

    public List<SalesDetail> findAllByLotDetail(Integer lotNo) {
        return this.salesRepo.findAllByLotDetail(lotNo);
    }

    public List<SalesDetail> findAllByStyle(Integer styleId) {
        return this.salesRepo.findAllByStyle(styleId);
    }

    public List<SalesDetail> findAllByLotId(Integer lotId) {
        return this.salesRepo.findAllByLotId(lotId);
    }

    // Find one by uicode
    public SalesDetail findOneByUicode(String uicode) {
        return this.salesRepo.findOneByUicode(uicode).orElseThrow();
    }
}
