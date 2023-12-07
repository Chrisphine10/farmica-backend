package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.WarehouseRepo;
import com.bhachu.farmica.domain.WarehouseDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepo warehouseRepo;

    public WarehouseService() {}

    public List<WarehouseDetail> findAllByLotDetail(Integer lotNo) {
        return this.warehouseRepo.findAllByLotDetail(lotNo);
    }

    public List<WarehouseDetail> findAllByStyle(Integer styleId) {
        return this.warehouseRepo.findAllByStyle(styleId);
    }

    public List<WarehouseDetail> findAllByLotId(Integer lotId) {
        return this.warehouseRepo.findAllByLotId(lotId);
    }

    // Find one by uicode
    public WarehouseDetail findOneByUicode(String uicode) {
        return this.warehouseRepo.findOneByUicode(uicode).orElseThrow();
    }
}
