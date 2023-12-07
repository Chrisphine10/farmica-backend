package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.ReworkService;
import com.bhachu.farmica.domain.ReworkDetail;
import com.bhachu.farmica.repository.ReworkDetailRepository;
import com.bhachu.farmica.service.ReworkDetailService;
import com.bhachu.farmica.web.rest.ReworkDetailResource;
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
public class ReworkResource extends ReworkDetailResource {

    public ReworkResource(ReworkDetailService reworkDetailService, ReworkDetailRepository reworkDetailRepository) {
        super(reworkDetailService, reworkDetailRepository);
    }

    private final Logger log = LoggerFactory.getLogger(ReworkResource.class);

    @Autowired
    private ReworkService reworkService;

    @GetMapping("/lot-details/{lotId}/rework-details")
    public ResponseEntity<List<ReworkDetail>> findAllByLot(@PathVariable Long lotId) {
        log.debug("REST request to get all ReworkDetails by Lot: {}", lotId);
        return new ResponseEntity<>(reworkService.findAllByLotDetail(lotId), HttpStatus.OK);
    }

    @GetMapping("/rework-details/uicode/{uicode}/")
    public ResponseEntity<ReworkDetail> findOneByUicode(@PathVariable String uicode) {
        log.debug("REST request to get ReworkDetail by Uicode: {}", uicode);
        return new ResponseEntity<>(reworkService.findOneByUicode(uicode), HttpStatus.OK);
    }
}
