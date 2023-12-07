package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.ZoneDetailService;
import com.bhachu.farmica.domain.PackingZoneDetail;
import com.bhachu.farmica.repository.PackingZoneDetailRepository;
import com.bhachu.farmica.service.PackingZoneDetailService;
import com.bhachu.farmica.web.rest.PackingZoneDetailResource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ZoneDetailResource extends PackingZoneDetailResource {

    public ZoneDetailResource(PackingZoneDetailService packingZoneDetailService, PackingZoneDetailRepository packingZoneDetailRepository) {
        super(packingZoneDetailService, packingZoneDetailRepository);
    }

    private final Logger log = LoggerFactory.getLogger(ZoneDetailResource.class);

    @Autowired
    private ZoneDetailService zoneDetailService;

    @GetMapping("/lot-details-no/{lotNo}/parking-zone-details")
    public ResponseEntity<List<PackingZoneDetail>> findAllByLot(@PathVariable Integer lotNo) {
        log.debug("REST request to get all ZoneDetails by Lot: {}", lotNo);
        return ResponseEntity.ok(zoneDetailService.findAllByLotDetail(lotNo));
    }

    @GetMapping("/style-details/{styleId}/parking-zone-details")
    public ResponseEntity<List<PackingZoneDetail>> findAllByStyle(@PathVariable Integer styleId) {
        log.debug("REST request to get all ZoneDetails by Style: {}", styleId);
        return ResponseEntity.ok(zoneDetailService.findAllByStyle(styleId));
    }

    @GetMapping("/lot-details/{lotId}/parking-zone-details")
    public ResponseEntity<List<PackingZoneDetail>> findAllByLotId(@PathVariable Integer lotId) {
        log.debug("REST request to get all ZoneDetails by Lot: {}", lotId);
        return ResponseEntity.ok(zoneDetailService.findAllByLotId(lotId));
    }

    @GetMapping("/parking-zone-details/uicode/{uicode}/")
    public ResponseEntity<PackingZoneDetail> findOneByUicode(@PathVariable String uicode) {
        log.debug("REST request to get ZoneDetail by Uicode: {}", uicode);
        return ResponseEntity.ok(zoneDetailService.findOneByUicode(uicode));
    }
}
