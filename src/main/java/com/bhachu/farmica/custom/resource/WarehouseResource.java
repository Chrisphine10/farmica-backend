package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.WarehouseService;
import com.bhachu.farmica.domain.WarehouseDetail;
import com.bhachu.farmica.repository.WarehouseDetailRepository;
import com.bhachu.farmica.service.WarehouseDetailService;
import com.bhachu.farmica.web.rest.WarehouseDetailResource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WarehouseResource extends WarehouseDetailResource {

    public WarehouseResource(WarehouseDetailService warehouseDetailService, WarehouseDetailRepository warehouseDetailRepository) {
        super(warehouseDetailService, warehouseDetailRepository);
    }

    private static final Logger log = LoggerFactory.getLogger(WarehouseResource.class);

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/lot-details-no/{lotNo}/warehouse-details")
    public ResponseEntity<List<WarehouseDetail>> findAllByLotDetail(@PathVariable Integer lotNo) {
        log.info("Request to get all warehouse by lot detail: {}", lotNo);
        List<WarehouseDetail> warehouseDetails = this.warehouseService.findAllByLotDetail(lotNo);
        return new ResponseEntity<>(warehouseDetails, HttpStatus.OK);
    }

    @GetMapping("style/{styleId}/warehouse-details")
    public ResponseEntity<List<WarehouseDetail>> findAllByStyle(@PathVariable Integer styleId) {
        log.info("Request to get all warehouse by style: {}", styleId);
        List<WarehouseDetail> warehouseDetails = this.warehouseService.findAllByStyle(styleId);
        return new ResponseEntity<>(warehouseDetails, HttpStatus.OK);
    }

    @GetMapping("/lot-details/{lotId}/warehouse-details")
    public ResponseEntity<List<WarehouseDetail>> findAllByLotId(@PathVariable Integer lotId) {
        log.info("Request to get all warehouse by lot id: {}", lotId);
        List<WarehouseDetail> warehouseDetails = this.warehouseService.findAllByLotId(lotId);
        return new ResponseEntity<>(warehouseDetails, HttpStatus.OK);
    }

    @GetMapping("/warehouse-details/uicode/{uicode}/")
    public ResponseEntity<WarehouseDetail> findOneByUicode(@PathVariable String uicode) {
        log.info("Request to get warehouse by uicode: {}", uicode);
        WarehouseDetail warehouseDetail = this.warehouseService.findOneByUicode(uicode);
        return new ResponseEntity<>(warehouseDetail, HttpStatus.OK);
    }
}
