package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.SalesService;
import com.bhachu.farmica.domain.SalesDetail;
import com.bhachu.farmica.repository.SalesDetailRepository;
import com.bhachu.farmica.service.SalesDetailService;
import com.bhachu.farmica.web.rest.SalesDetailResource;
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
public class SalesResource extends SalesDetailResource {

    public SalesResource(SalesDetailService salesDetailService, SalesDetailRepository salesDetailRepository) {
        super(salesDetailService, salesDetailRepository);
    }

    private static final Logger log = LoggerFactory.getLogger(SalesResource.class);

    @Autowired
    private SalesService salesService;

    @GetMapping("/lot-details-no/{lotNo}/sales")
    public ResponseEntity<List<SalesDetail>> findAllByLotDetail(@PathVariable Integer lotNo) {
        log.info("Request to get all sales by lot detail: {}", lotNo);
        List<SalesDetail> salesDetails = this.salesService.findAllByLotDetail(lotNo);
        return new ResponseEntity<>(salesDetails, HttpStatus.OK);
    }

    @GetMapping("style/{styleId}/sales")
    public ResponseEntity<List<SalesDetail>> findAllByStyle(@PathVariable Integer styleId) {
        log.info("Request to get all sales by style: {}", styleId);
        List<SalesDetail> salesDetails = this.salesService.findAllByStyle(styleId);
        return new ResponseEntity<>(salesDetails, HttpStatus.OK);
    }

    @GetMapping("/lot-details/{lotId}/sales")
    public ResponseEntity<List<SalesDetail>> findAllByLotId(@PathVariable Integer lotId) {
        log.info("Request to get all sales by lot id: {}", lotId);
        List<SalesDetail> salesDetails = this.salesService.findAllByLotId(lotId);
        return new ResponseEntity<>(salesDetails, HttpStatus.OK);
    }

    @GetMapping("/sales/uicode/{uicode}/")
    public ResponseEntity<SalesDetail> findOneByUicode(@PathVariable String uicode) {
        log.info("Request to get sales by uicode: {}", uicode);
        SalesDetail salesDetail = this.salesService.findOneByUicode(uicode);
        return new ResponseEntity<>(salesDetail, HttpStatus.OK);
    }
}
