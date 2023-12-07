package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.BatchRepo;
import com.bhachu.farmica.domain.BatchDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    @Autowired
    private BatchRepo batchRepo;

    public List<BatchDetail> findAllByRegion(Long regionId) {
        return batchRepo.findAllByRegion(regionId);
    }
}
