package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.LotRepo;
import com.bhachu.farmica.domain.LotDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotService {

    @Autowired
    private LotRepo lotRepo;

    public LotService() {}

    public List<LotDetail> findAllByBatchDetail(Long batchId) {
        return lotRepo.findAllByBatchDetail(batchId);
    }
}
