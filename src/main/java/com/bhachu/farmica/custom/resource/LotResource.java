package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.LotService;
import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.repository.LotDetailRepository;
import com.bhachu.farmica.service.LotDetailService;
import com.bhachu.farmica.web.rest.LotDetailResource;
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
public class LotResource extends LotDetailResource {

    public LotResource(LotDetailService lotDetailService, LotDetailRepository lotDetailRepository) {
        super(lotDetailService, lotDetailRepository);
    }

    private final Logger log = LoggerFactory.getLogger(LotResource.class);

    @Autowired
    private LotService lotService;

    @GetMapping("/batch-details/{batchId}/lot-details")
    public ResponseEntity<List<LotDetail>> findAllByBatch(@PathVariable Long batchId) {
        log.debug("REST request to get all LotDetails by Batch: {}", batchId);
        return new ResponseEntity<>(lotService.findAllByBatchDetail(batchId), HttpStatus.OK);
    }
}
