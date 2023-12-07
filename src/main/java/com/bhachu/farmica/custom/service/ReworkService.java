package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.ReworkRepo;
import com.bhachu.farmica.domain.ReworkDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReworkService {

    @Autowired
    private ReworkRepo reworkRepo;

    public ReworkService() {}

    public List<ReworkDetail> findAllByLotDetail(Long lotId) {
        return reworkRepo.findAllByLotDetail(lotId);
    }

    // Find one by uicode
    public ReworkDetail findOneByUicode(String uicode) {
        return reworkRepo.findOneByUicode(uicode).orElseThrow();
    }
}
