package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.BatchService;
import com.bhachu.farmica.domain.BatchDetail;
import com.bhachu.farmica.repository.BatchDetailRepository;
import com.bhachu.farmica.service.BatchDetailService;
import com.bhachu.farmica.web.rest.BatchDetailResource;
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
public class BatchResource extends BatchDetailResource {

    public BatchResource(BatchDetailService batchDetailService, BatchDetailRepository batchDetailRepository) {
        super(batchDetailService, batchDetailRepository);
    }

    private final Logger log = LoggerFactory.getLogger(BatchResource.class);

    @Autowired
    private BatchService batchService;

    @GetMapping("/regions/{regionId}/batch-details")
    public ResponseEntity<List<BatchDetail>> findAllByRegion(@PathVariable Long regionId) {
        log.debug("REST request to get all BatchDetails by Region: {}", regionId);
        return new ResponseEntity<>(batchService.findAllByRegion(regionId), HttpStatus.OK);
    }
}
