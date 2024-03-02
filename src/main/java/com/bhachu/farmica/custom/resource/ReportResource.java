package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.ReportService;
import com.bhachu.farmica.domain.FarmicaReport;
import com.bhachu.farmica.domain.StyleReport;
import com.bhachu.farmica.repository.FarmicaReportRepository;
import com.bhachu.farmica.service.FarmicaReportService;
import com.bhachu.farmica.service.dto.FarmicaReportDTO;
import com.bhachu.farmica.web.rest.FarmicaReportResource;
import java.time.ZonedDateTime;
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
public class ReportResource extends FarmicaReportResource {

    public ReportResource(FarmicaReportService farmicaReportService, FarmicaReportRepository farmicaReportRepository) {
        super(farmicaReportService, farmicaReportRepository);
    }

    @Autowired
    private ReportService reportService;

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    @GetMapping("/generate-report")
    public ResponseEntity<String> generateReport() {
        log.debug("REST request to generate report");
        ZonedDateTime startTime = ZonedDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        ZonedDateTime endTime = ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0);
        Boolean reportGenerated = reportService.createNewReport(startTime, endTime);
        Boolean generateStyleReport = reportService.createStyleReport(startTime, endTime);
        if (reportGenerated && generateStyleReport) {
            return new ResponseEntity<>("Report generated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/last-reports")
    public ResponseEntity<FarmicaReport> getLastReports() {
        log.debug("REST request to get last reports");
        return new ResponseEntity<>(reportService.getLastReports(), HttpStatus.OK);
    }

    @GetMapping("/report-by-month-year/{month}/{year}")
    public ResponseEntity<FarmicaReport> getReportByMonthYear(@PathVariable String month, @PathVariable String year) {
        log.debug("REST request to get last report by month and year");
        return new ResponseEntity<>(reportService.getReportByMonthYear(month, year), HttpStatus.OK);
    }

    @GetMapping("/style-report-by-month-year/{month}/{year}")
    public ResponseEntity<List<StyleReport>> getStyleReportByMonthYear(@PathVariable String month, @PathVariable String year) {
        log.debug("REST request to get last report by month and year");
        return new ResponseEntity<>(reportService.getStyleReportByMonthYear(month, year), HttpStatus.OK);
    }

    @GetMapping("/report-by-dates/{startDate}/{endDate}")
    public ResponseEntity<FarmicaReport> getReportByDates(@PathVariable ZonedDateTime startDate, @PathVariable ZonedDateTime endDate) {
        log.debug("REST request to get last report by startDate and endDate");
        return new ResponseEntity<>(reportService.getReportByDates(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/style-report-by-dates/{startDate}/{endDate}")
    public ResponseEntity<List<StyleReport>> getStyleReportByDates(
        @PathVariable ZonedDateTime startDate,
        @PathVariable ZonedDateTime endDate
    ) {
        log.debug("REST request to get last report by month and year");
        return new ResponseEntity<>(reportService.getStyleReportByDates(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/old-report-generation/{startDate}/{endDate}")
    public ResponseEntity<Boolean> generateOldReports(@PathVariable ZonedDateTime startDate, @PathVariable ZonedDateTime endDate) {
        log.debug("REST request to get live reports");
        return new ResponseEntity<>(reportService.generateOldReports(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/live-reports")
    public ResponseEntity<FarmicaReportDTO> getLiveReports() {
        log.debug("REST request to get live reports");
        return new ResponseEntity<>(reportService.getLiveReports(), HttpStatus.OK);
    }

    @GetMapping("/live-style-reports")
    public ResponseEntity<List<StyleReport>> getLiveStyleReports() {
        log.debug("REST request to get live style reports");
        return new ResponseEntity<>(reportService.getLiveStyleReports(), HttpStatus.OK);
    }

    @GetMapping("/update-zone-data")
    public ResponseEntity<Boolean> updateZoneData() {
        log.debug("REST request to update zone data");
        return new ResponseEntity<>(reportService.updatePackingZoneDetails(), HttpStatus.OK);
    }
}
