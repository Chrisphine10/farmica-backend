package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.ZoneDetailRepo;
import com.bhachu.farmica.domain.PackingZoneDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneDetailService {

    @Autowired
    private ZoneDetailRepo zoneDetailRepo;

    public ZoneDetailService() {}

    public List<PackingZoneDetail> findAllByLotDetail(Integer lotNo) {
        return this.zoneDetailRepo.findAllByLotDetail(lotNo);
    }

    public List<PackingZoneDetail> findAllByStyle(Integer styleId) {
        return this.zoneDetailRepo.findAllByStyle(styleId);
    }

    public List<PackingZoneDetail> findAllByLotId(Integer lotId) {
        return this.zoneDetailRepo.findAllByLotId(lotId);
    }

    // Find one by uicode
    public PackingZoneDetail findOneByUicode(String uicode) {
        return this.zoneDetailRepo.findOneByUicode(uicode).orElseThrow();
    }
}
