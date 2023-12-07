package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.ReportRepo;
import com.bhachu.farmica.custom.repository.ReworkRepo;
import com.bhachu.farmica.custom.repository.SalesRepo;
import com.bhachu.farmica.custom.repository.StylesReportRepo;
import com.bhachu.farmica.custom.repository.WarehouseRepo;
import com.bhachu.farmica.custom.repository.ZoneDetailRepo;
import com.bhachu.farmica.domain.FarmicaReport;
import com.bhachu.farmica.domain.PackingZoneDetail;
import com.bhachu.farmica.domain.StyleReport;
import com.bhachu.farmica.service.FarmicaReportService;
import com.bhachu.farmica.service.StyleService;
import com.bhachu.farmica.service.dto.FarmicaReportDTO;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.mapper.FarmicaReportMapper;
import com.bhachu.farmica.service.mapper.StyleMapper;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private FarmicaReportService farmicaReportService;

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private StylesReportRepo stylesReportRepo;

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private WarehouseRepo warehouseRepo;

    @Autowired
    private ReworkRepo reworkRepo;

    @Autowired
    private ZoneDetailRepo zoneDetailRepo;

    @Autowired
    private FarmicaReportMapper farmicaReportMapper;

    @Autowired
    private StyleService styleService;

    @Autowired
    private StyleMapper styleMapper;

    public ReportService() {}

    private ZonedDateTime startOfMonthDateTime = ZonedDateTime.now(ZoneId.of("Africa/Nairobi")).with(LocalTime.MIN).withDayOfMonth(1);

    private ZonedDateTime endOfMonthDateTime = ZonedDateTime
        .now(ZoneId.of("Africa/Nairobi"))
        .with(LocalTime.MAX)
        .withDayOfMonth(ZonedDateTime.now(ZoneId.of("Africa/Nairobi")).toLocalDate().lengthOfMonth());

    private Boolean checkIfOneMonthHasPassed(ZonedDateTime lastReportTime) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime oneMonthAgo = currentTime.minusMonths(1);
        return lastReportTime.isBefore(oneMonthAgo);
    }

    private Integer getSalesCount() {
        return salesRepo.findTotalSalesCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
    }

    private Integer getWarehouseCount() {
        return warehouseRepo.findTotalWarehouseCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
    }

    private Integer getReworkCount() {
        return reworkRepo.findTotalReworkCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
    }

    private Integer getZoneCount() {
        return zoneDetailRepo.findTotalZoneCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
    }

    private Integer getTotalStyleCount(Long styleId) {
        List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAllByStartDateAndEndDateAndStyle(
            startOfMonthDateTime,
            endOfMonthDateTime,
            styleId
        );
        Integer totalZoneCount = 0;
        for (PackingZoneDetail zoneDetail : zoneDetails) {
            totalZoneCount += zoneDetail.getNumberOfCTNs();
        }
        return totalZoneCount;
    }

    private Integer getStyleCountForWarehouse(Long styleId) {
        List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAllByStartDateAndEndDateAndStyle(
            startOfMonthDateTime,
            endOfMonthDateTime,
            styleId
        );
        Integer totalZoneCount = 0;
        for (PackingZoneDetail zoneDetail : zoneDetails) {
            totalZoneCount += zoneDetail.getNumberOfCTNsInWarehouse();
        }
        return totalZoneCount;
    }

    private Integer getStyleCountForSales(Long styleId) {
        List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAllByStartDateAndEndDateAndStyle(
            startOfMonthDateTime,
            endOfMonthDateTime,
            styleId
        );
        Integer totalZoneCount = 0;
        for (PackingZoneDetail zoneDetail : zoneDetails) {
            totalZoneCount += zoneDetail.getNumberOfCTNsSold();
        }
        return totalZoneCount;
    }

    private Integer getStyleCountForRework(Long styleId) {
        List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAllByStartDateAndEndDateAndStyle(
            startOfMonthDateTime,
            endOfMonthDateTime,
            styleId
        );
        Integer totalZoneCount = 0;
        for (PackingZoneDetail zoneDetail : zoneDetails) {
            totalZoneCount += zoneDetail.getNumberOfCTNsReworked();
        }
        return totalZoneCount;
    }

    private Integer getStyleCountForPacking(Long styleId) {
        List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAllByStartDateAndEndDateAndStyle(
            startOfMonthDateTime,
            endOfMonthDateTime,
            styleId
        );
        Integer totalZoneCount = 0;
        for (PackingZoneDetail zoneDetail : zoneDetails) {
            totalZoneCount += zoneDetail.getNumberOfCTNsPacked();
        }
        return totalZoneCount;
    }

    public List<StyleReport> getStyleReports() {
        List<StyleReport> styleReports = new ArrayList<StyleReport>();
        // find total number of styles
        List<StyleDTO> styles = styleService.findAll();

        for (StyleDTO style : styles) {
            StyleReport styleReport = stylesReportRepo.findFirstByStyleOrderByCreatedAtDesc(styleMapper.toEntity(style)).orElseThrow();
            styleReports.add(styleReport);
        }

        return styleReports;
    }

    public Boolean createNewReport() {
        System.err.println("Creating new report");
        try {
            Optional<FarmicaReport> lastReport = reportRepo.findFirstByOrderByCreatedAtDesc();
            if (lastReport.isPresent()) {
                ZonedDateTime lastReportTime = lastReport.orElseThrow().getCreatedAt();
                if (checkIfOneMonthHasPassed(lastReportTime)) {
                    FarmicaReportDTO farmicaReportDTO = new FarmicaReportDTO();
                    farmicaReportDTO.setTotalItemsInSales(getSalesCount());
                    farmicaReportDTO.setTotalItemsInWarehouse(getWarehouseCount());
                    farmicaReportDTO.setTotalItemsInRework(getReworkCount());
                    farmicaReportDTO.setTotalItemsInPacking(getZoneCount());
                    farmicaReportDTO.setTotalItems(getSalesCount() + getWarehouseCount() + getReworkCount() + getZoneCount());
                    farmicaReportDTO.setCreatedAt(ZonedDateTime.now());
                    farmicaReportService.save(farmicaReportDTO);
                } else {
                    // update the last report
                    lastReport.orElseThrow().setCreatedAt(ZonedDateTime.now());
                    FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(lastReport.orElseThrow());
                    farmicaReportDTO.setTotalItemsInSales(getSalesCount());
                    farmicaReportDTO.setTotalItemsInWarehouse(getWarehouseCount());
                    farmicaReportDTO.setTotalItemsInRework(getReworkCount());
                    farmicaReportDTO.setTotalItemsInPacking(getZoneCount());
                    farmicaReportDTO.setTotalItems(getSalesCount() + getWarehouseCount() + getReworkCount() + getZoneCount());
                    farmicaReportService.save(farmicaReportDTO);
                }
            } else {
                FarmicaReportDTO farmicaReportDTO = new FarmicaReportDTO();
                farmicaReportDTO.setTotalItemsInSales(getSalesCount());
                farmicaReportDTO.setTotalItemsInWarehouse(getWarehouseCount());
                farmicaReportDTO.setTotalItemsInRework(getReworkCount());
                farmicaReportDTO.setTotalItemsInPacking(getZoneCount());
                farmicaReportDTO.setTotalItems(getSalesCount() + getWarehouseCount() + getReworkCount() + getZoneCount());
                farmicaReportDTO.setCreatedAt(ZonedDateTime.now());
                farmicaReportService.save(new FarmicaReportDTO());
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public FarmicaReport getLastReports() {
        return reportRepo.findFirstByOrderByCreatedAtDesc().orElseThrow();
    }

    public FarmicaReport getReportByMonthYear(String month, String year) {
        return reportRepo.findReportByMonthAndYear(month, year).orElseThrow();
    }

    public List<StyleReport> getStyleReportByMonthYear(String month, String year) {
        List<StyleReport> styleReports = new ArrayList<StyleReport>();
        // find total number of styles
        List<StyleDTO> styles = styleService.findAll();

        for (StyleDTO style : styles) {
            StyleReport styleReport = stylesReportRepo.findStyleReportByMonthAndYear(month, year, style.getId()).orElseThrow();
            styleReports.add(styleReport);
        }

        return styleReports;
    }

    // a live report is a report that is generated to check the current products in
    // zone

    public FarmicaReportDTO getLiveReports() {
        FarmicaReportDTO farmicaReportDTO = new FarmicaReportDTO();
        // find all the products in packing zone where number of ctns > 0
        List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAllByNumberOfCTNsGreaterThanZero();
        Integer totalNumberOfCTNsInZone = 0;
        Integer totalNumberOfCTNsInSales = 0;
        Integer totalNumberOfCTNsInWarehouse = 0;
        Integer totalNumberOfCTNsInRework = 0;
        for (PackingZoneDetail zoneDetail : zoneDetails) {
            totalNumberOfCTNsInZone += zoneDetail.getNumberOfCTNs();
            totalNumberOfCTNsInSales += zoneDetail.getNumberOfCTNsSold();
            totalNumberOfCTNsInWarehouse += zoneDetail.getNumberOfCTNsInWarehouse();
            totalNumberOfCTNsInRework += zoneDetail.getNumberOfCTNsReworked();
        }
        farmicaReportDTO.setTotalItemsInSales(totalNumberOfCTNsInSales);
        farmicaReportDTO.setTotalItemsInWarehouse(totalNumberOfCTNsInWarehouse);
        farmicaReportDTO.setTotalItemsInRework(totalNumberOfCTNsInRework);
        farmicaReportDTO.setTotalItemsInPacking(totalNumberOfCTNsInZone);
        farmicaReportDTO.setTotalItems(
            totalNumberOfCTNsInSales + totalNumberOfCTNsInWarehouse + totalNumberOfCTNsInRework + totalNumberOfCTNsInZone
        );

        return farmicaReportDTO;
    }

    public List<StyleReport> getLiveStyleReports() {
        List<StyleReport> styleReports = new ArrayList<StyleReport>();
        // find total number of styles
        List<StyleDTO> styles = styleService.findAll();
        // find all the products in packing zone where number of ctns > 0
        List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAllByNumberOfCTNsGreaterThanZero();
        for (StyleDTO style : styles) {
            StyleReport styleReport = new StyleReport();
            styleReport.setStyle(styleMapper.toEntity(style));
            Integer totalNumberOfCTNsInZone = 0;
            Integer totalNumberOfCTNsInSales = 0;
            Integer totalNumberOfCTNsInWarehouse = 0;
            Integer totalNumberOfCTNsInRework = 0;
            for (PackingZoneDetail zoneDetail : zoneDetails) {
                if (zoneDetail.getStyle().getId() == style.getId()) {
                    totalNumberOfCTNsInZone += zoneDetail.getNumberOfCTNs();
                    totalNumberOfCTNsInSales += zoneDetail.getNumberOfCTNsSold();
                    totalNumberOfCTNsInWarehouse += zoneDetail.getNumberOfCTNsInWarehouse();
                    totalNumberOfCTNsInRework += zoneDetail.getNumberOfCTNsReworked();
                }
            }
            styleReport.setTotalStyleInSales(totalNumberOfCTNsInSales);
            styleReport.setTotalStyleInWarehouse(totalNumberOfCTNsInWarehouse);
            styleReport.setTotalStyleInRework(totalNumberOfCTNsInRework);
            styleReport.setTotalStyleInPacking(totalNumberOfCTNsInZone);
            styleReport.setTotalStyle(
                totalNumberOfCTNsInSales + totalNumberOfCTNsInWarehouse + totalNumberOfCTNsInRework + totalNumberOfCTNsInZone
            );
            styleReports.add(styleReport);
        }

        return styleReports;
    }

    public Boolean createStyleReport() {
        System.err.println("Creating new style report");
        try {
            Optional<StyleReport> lastStyleReport = stylesReportRepo.findFirstByOrderByCreatedAtDesc();
            List<StyleDTO> styles = styleService.findAll();
            if (lastStyleReport.isPresent()) {
                ZonedDateTime lastReportTime = lastStyleReport.orElseThrow().getCreatedAt();
                if (checkIfOneMonthHasPassed(lastReportTime)) {
                    for (StyleDTO style : styles) {
                        StyleReport styleReport = new StyleReport();
                        styleReport.setStyle(styleMapper.toEntity(style));
                        styleReport.setTotalStyleInSales(getStyleCountForSales(style.getId()));
                        styleReport.setTotalStyleInWarehouse(getStyleCountForWarehouse(style.getId()));
                        styleReport.setTotalStyleInRework(getStyleCountForRework(style.getId()));
                        styleReport.setTotalStyleInPacking(getStyleCountForPacking(style.getId()));
                        styleReport.setTotalStyle(getTotalStyleCount(style.getId()));
                        styleReport.setCreatedAt(ZonedDateTime.now());
                        stylesReportRepo.save(styleReport);
                    }
                } else {
                    // update the last report
                    lastStyleReport.orElseThrow().setCreatedAt(ZonedDateTime.now());
                    for (StyleDTO style : styles) {
                        StyleReport styleReport = stylesReportRepo
                            .findFirstByStyleOrderByCreatedAtDesc(styleMapper.toEntity(style))
                            .orElseThrow();
                        styleReport.setStyle(styleMapper.toEntity(style));
                        styleReport.setTotalStyleInSales(getStyleCountForSales(style.getId()));
                        styleReport.setTotalStyleInWarehouse(getStyleCountForWarehouse(style.getId()));
                        styleReport.setTotalStyleInRework(getStyleCountForRework(style.getId()));
                        styleReport.setTotalStyleInPacking(getStyleCountForPacking(style.getId()));
                        styleReport.setTotalStyle(getTotalStyleCount(style.getId()));
                        styleReport.setCreatedAt(ZonedDateTime.now());
                        stylesReportRepo.save(styleReport);
                    }
                }
            } else {
                for (StyleDTO style : styles) {
                    StyleReport styleReport = new StyleReport();
                    styleReport.setStyle(styleMapper.toEntity(style));
                    styleReport.setTotalStyleInSales(getStyleCountForSales(style.getId()));
                    styleReport.setTotalStyleInWarehouse(getStyleCountForWarehouse(style.getId()));
                    styleReport.setTotalStyleInRework(getStyleCountForRework(style.getId()));
                    styleReport.setTotalStyleInPacking(getStyleCountForPacking(style.getId()));
                    styleReport.setTotalStyle(getTotalStyleCount(style.getId()));
                    styleReport.setCreatedAt(ZonedDateTime.now());
                    stylesReportRepo.save(styleReport);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
