package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.ReportRepo;
import com.bhachu.farmica.custom.repository.ReworkRepo;
import com.bhachu.farmica.custom.repository.SalesRepo;
import com.bhachu.farmica.custom.repository.StylesReportRepo;
import com.bhachu.farmica.custom.repository.WarehouseRepo;
import com.bhachu.farmica.custom.repository.ZoneDetailRepo;
import com.bhachu.farmica.domain.FarmicaReport;
import com.bhachu.farmica.domain.PackingZoneDetail;
import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.StyleReport;
import com.bhachu.farmica.domain.WarehouseDetail;
import com.bhachu.farmica.service.FarmicaReportService;
import com.bhachu.farmica.service.PackingZoneDetailService;
import com.bhachu.farmica.service.StyleService;
import com.bhachu.farmica.service.dto.FarmicaReportDTO;
import com.bhachu.farmica.service.dto.PackingZoneDetailDTO;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.mapper.FarmicaReportMapper;
import com.bhachu.farmica.service.mapper.PackingZoneDetailMapper;
import com.bhachu.farmica.service.mapper.StyleMapper;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private PackingZoneDetailMapper packingZoneDetailMapper;

    @Autowired
    private PackingZoneDetailService packingZoneDetailService;

    public ReportService() {}

    // private Boolean checkIfOneMonthHasPassed(ZonedDateTime lastReportTime) {
    // ZonedDateTime currentTime = ZonedDateTime.now();
    // ZonedDateTime oneMonthAgo = currentTime.minusMonths(1);
    // return lastReportTime.isBefore(oneMonthAgo);
    // }

    private Boolean checkIfOneDayHasPassed(ZonedDateTime lastReportTime) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime oneDayAgo = currentTime.minusDays(1);
        return lastReportTime.isBefore(oneDayAgo);
    }

    private Integer getSalesCount(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        try {
            return salesRepo.findTotalSalesCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer getWarehouseCount(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        try {
            return warehouseRepo.findTotalWarehouseCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer getReworkCount(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        try {
            return reworkRepo.findTotalReworkCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer getZoneCount(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        try {
            return zoneDetailRepo.findTotalZoneCountByStartDateAndEndDate(startOfMonthDateTime, endOfMonthDateTime);
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer getTotalStyleCount(Long styleId, ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
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

    private Integer getStyleCountForWarehouse(Long styleId, ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
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

    private Integer getStyleCountForSales(Long styleId, ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
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

    private Integer getStyleCountForRework(Long styleId, ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
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

    private Integer getStyleCountForPacking(Long styleId, ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
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
        Pageable wholePage = Pageable.unpaged();
        List<StyleDTO> styles = styleService.findAll(wholePage).toList();

        for (StyleDTO style : styles) {
            StyleReport styleReport = stylesReportRepo.findFirstByStyleOrderByCreatedAtDesc(styleMapper.toEntity(style)).orElseThrow();
            styleReports.add(styleReport);
        }

        return styleReports;
    }

    public Boolean createNewReport(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        System.err.println("Creating new report");
        try {
            Optional<FarmicaReport> lastReport = reportRepo.findFirstByOrderByCreatedAtDesc();
            if (lastReport.isPresent()) {
                ZonedDateTime lastReportTime = lastReport.orElseThrow().getCreatedAt();
                if (checkIfOneDayHasPassed(lastReportTime)) {
                    FarmicaReportDTO farmicaReportDTO = new FarmicaReportDTO();
                    farmicaReportDTO.setTotalItemsInSales(getSalesCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItemsInWarehouse(getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItemsInRework(getReworkCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItemsInPacking(getZoneCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItems(
                        getSalesCount(startOfMonthDateTime, endOfMonthDateTime) +
                        getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime) +
                        getReworkCount(startOfMonthDateTime, endOfMonthDateTime) +
                        getZoneCount(startOfMonthDateTime, endOfMonthDateTime)
                    );
                    farmicaReportDTO.setCreatedAt(ZonedDateTime.now());
                    farmicaReportService.save(farmicaReportDTO);
                } else {
                    // update the last report
                    lastReport.orElseThrow().setCreatedAt(ZonedDateTime.now());
                    FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(lastReport.orElseThrow());
                    farmicaReportDTO.setTotalItemsInSales(getSalesCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItemsInWarehouse(getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItemsInRework(getReworkCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItemsInPacking(getZoneCount(startOfMonthDateTime, endOfMonthDateTime));
                    farmicaReportDTO.setTotalItems(
                        getSalesCount(startOfMonthDateTime, endOfMonthDateTime) +
                        getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime) +
                        getReworkCount(startOfMonthDateTime, endOfMonthDateTime) +
                        getZoneCount(startOfMonthDateTime, endOfMonthDateTime)
                    );
                    farmicaReportService.save(farmicaReportDTO);
                }
            } else {
                FarmicaReportDTO farmicaReportDTO = new FarmicaReportDTO();
                farmicaReportDTO.setTotalItemsInSales(getSalesCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItemsInWarehouse(getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItemsInRework(getReworkCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItemsInPacking(getZoneCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItems(
                    getSalesCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getReworkCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getZoneCount(startOfMonthDateTime, endOfMonthDateTime)
                );
                farmicaReportDTO.setCreatedAt(ZonedDateTime.now());
                farmicaReportService.save(farmicaReportDTO);
            }
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public Boolean createNewReportAuto(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        System.err.println("Creating new report");
        try {
            Optional<FarmicaReport> lastReport = reportRepo.findFirstByOrderByCreatedAtDesc();
            if (lastReport.isPresent()) {
                FarmicaReportDTO farmicaReportDTO = new FarmicaReportDTO();
                farmicaReportDTO.setTotalItemsInSales(
                    getSalesCount(startOfMonthDateTime, endOfMonthDateTime) + lastReport.orElseThrow().getTotalItemsInSales()
                );
                farmicaReportDTO.setTotalItemsInWarehouse(
                    getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime) + lastReport.orElseThrow().getTotalItemsInWarehouse()
                );
                farmicaReportDTO.setTotalItemsInRework(
                    getReworkCount(startOfMonthDateTime, endOfMonthDateTime) + lastReport.orElseThrow().getTotalItemsInRework()
                );
                farmicaReportDTO.setTotalItemsInPacking(
                    getZoneCount(startOfMonthDateTime, endOfMonthDateTime) + lastReport.orElseThrow().getTotalItemsInPacking()
                );
                farmicaReportDTO.setTotalItems(
                    getSalesCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getReworkCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getZoneCount(startOfMonthDateTime, endOfMonthDateTime) +
                    lastReport.orElseThrow().getTotalItemsInSales() +
                    lastReport.orElseThrow().getTotalItemsInWarehouse() +
                    lastReport.orElseThrow().getTotalItemsInRework() +
                    lastReport.orElseThrow().getTotalItemsInPacking()
                );
                farmicaReportDTO.setCreatedAt(startOfMonthDateTime);
                farmicaReportService.save(farmicaReportDTO);
            } else {
                FarmicaReportDTO farmicaReportDTO = new FarmicaReportDTO();
                farmicaReportDTO.setTotalItemsInSales(getSalesCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItemsInWarehouse(getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItemsInRework(getReworkCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItemsInPacking(getZoneCount(startOfMonthDateTime, endOfMonthDateTime));
                farmicaReportDTO.setTotalItems(
                    getSalesCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getWarehouseCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getReworkCount(startOfMonthDateTime, endOfMonthDateTime) +
                    getZoneCount(startOfMonthDateTime, endOfMonthDateTime)
                );
                farmicaReportDTO.setCreatedAt(startOfMonthDateTime);
                // if (farmicaReportDTO.getTotalItemsInSales() < 1 &&
                // farmicaReportDTO.getTotalItemsInWarehouse() < 1
                // && farmicaReportDTO.getTotalItemsInRework() < 1
                // && farmicaReportDTO.getTotalItemsInPacking() < 1) {
                // return false;
                // } else {
                farmicaReportService.save(farmicaReportDTO);
                // }
            }

            return true;
        } catch (Exception e) {
            System.err.println(e);
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
        Pageable wholePage = Pageable.unpaged();
        List<StyleDTO> styles = styleService.findAll(wholePage).toList();

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

    // ! Find all the number of CTNs in warehouse belonging to a packing zone and
    // ! update the packing zone details
    public Boolean updatePackingZoneDetails() {
        try {
            List<PackingZoneDetail> zoneDetails = zoneDetailRepo.findAll();
            for (PackingZoneDetail zoneDetail : zoneDetails) {
                // zone DTO
                PackingZoneDetailDTO zoneDetailDTO = packingZoneDetailMapper.toDto(zoneDetail);
                // find all the warehouse data of this zone
                Integer totalNumberOfCTNsInWarehouse = 0;
                totalNumberOfCTNsInWarehouse = warehouseRepo.findTotalWarehouseCountByZoneDetailId(zoneDetail.getId());
                List<WarehouseDetail> warehouseDetails = warehouseRepo.findAllByZoneDetailId(zoneDetail.getId());
                Integer totalNumberOfCTNsInRework = 0;
                Integer totalNumberOfCTNsInSales = 0;
                for (WarehouseDetail warehouseDetail : warehouseDetails) {
                    // find all the rework data of this zone
                    totalNumberOfCTNsInRework =
                        totalNumberOfCTNsInRework + reworkRepo.findTotalReworkCountByWarehouseDetailId(warehouseDetail.getId());
                    // find all the sales data of this zone
                    totalNumberOfCTNsInSales =
                        totalNumberOfCTNsInRework + salesRepo.findTotalSalesCountByWarehouseDetailId(warehouseDetail.getId());
                }

                System.err.println("totalNumberOfCTNsInWarehouse " + totalNumberOfCTNsInWarehouse);
                System.err.println("totalNumberOfCTNsInRework " + totalNumberOfCTNsInRework);
                System.err.println("totalNumberOfCTNsInSales " + totalNumberOfCTNsInSales);

                zoneDetailDTO.setNumberOfCTNsInWarehouse(totalNumberOfCTNsInWarehouse);
                zoneDetailDTO.setNumberOfCTNsReworked(totalNumberOfCTNsInRework);
                zoneDetailDTO.setNumberOfCTNsSold(totalNumberOfCTNsInSales);
                zoneDetailDTO.setNumberOfCTNsPacked(
                    zoneDetailDTO.getReceivedCTNs() - (totalNumberOfCTNsInWarehouse + totalNumberOfCTNsInRework + totalNumberOfCTNsInSales)
                );
                packingZoneDetailService.update(zoneDetailDTO);
                System.err.println("Updated zone detail " + zoneDetail.getId());
            }
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public List<StyleReport> getLiveStyleReports() {
        List<StyleReport> styleReports = new ArrayList<StyleReport>();
        // find total number of styles
        Pageable wholePage = Pageable.unpaged();
        List<StyleDTO> styles = styleService.findAll(wholePage).toList();
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

    public Boolean createStyleReport(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        System.err.println("Creating new style report");
        try {
            Optional<StyleReport> lastStyleReport = stylesReportRepo.findFirstByOrderByCreatedAtDesc();
            Pageable wholePage = Pageable.unpaged();
            List<StyleDTO> styles = styleService.findAll(wholePage).toList();
            if (lastStyleReport.isPresent()) {
                ZonedDateTime lastReportTime = lastStyleReport.orElseThrow().getCreatedAt();
                if (checkIfOneDayHasPassed(lastReportTime)) {
                    for (StyleDTO style : styles) {
                        StyleReport styleReport = new StyleReport();
                        styleReport.setStyle(styleMapper.toEntity(style));
                        styleReport.setTotalStyleInSales(getStyleCountForSales(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                        styleReport.setTotalStyleInWarehouse(
                            getStyleCountForWarehouse(style.getId(), startOfMonthDateTime, endOfMonthDateTime)
                        );
                        styleReport.setTotalStyleInRework(getStyleCountForRework(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                        styleReport.setTotalStyleInPacking(
                            getStyleCountForPacking(style.getId(), startOfMonthDateTime, endOfMonthDateTime)
                        );
                        styleReport.setTotalStyle(getTotalStyleCount(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
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
                        styleReport.setTotalStyleInSales(getStyleCountForSales(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                        styleReport.setTotalStyleInWarehouse(
                            getStyleCountForWarehouse(style.getId(), startOfMonthDateTime, endOfMonthDateTime)
                        );
                        styleReport.setTotalStyleInRework(getStyleCountForRework(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                        styleReport.setTotalStyleInPacking(
                            getStyleCountForPacking(style.getId(), startOfMonthDateTime, endOfMonthDateTime)
                        );
                        styleReport.setTotalStyle(getTotalStyleCount(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                        styleReport.setCreatedAt(ZonedDateTime.now());
                        stylesReportRepo.save(styleReport);
                    }
                }
            } else {
                for (StyleDTO style : styles) {
                    StyleReport styleReport = new StyleReport();
                    styleReport.setStyle(styleMapper.toEntity(style));
                    styleReport.setTotalStyleInSales(getStyleCountForSales(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                    styleReport.setTotalStyleInWarehouse(
                        getStyleCountForWarehouse(style.getId(), startOfMonthDateTime, endOfMonthDateTime)
                    );
                    styleReport.setTotalStyleInRework(getStyleCountForRework(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                    styleReport.setTotalStyleInPacking(getStyleCountForPacking(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                    styleReport.setTotalStyle(getTotalStyleCount(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
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

    public Boolean createStyleReportAuto(ZonedDateTime startOfMonthDateTime, ZonedDateTime endOfMonthDateTime) {
        System.err.println("Creating new style report");
        try {
            Pageable wholePage = Pageable.unpaged();
            List<StyleDTO> styles = styleService.findAll(wholePage).toList();

            for (StyleDTO style : styles) {
                StyleReport styleReport = new StyleReport();
                styleReport.setStyle(styleMapper.toEntity(style));
                styleReport.setTotalStyleInSales(getStyleCountForSales(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                styleReport.setTotalStyleInWarehouse(getStyleCountForWarehouse(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                styleReport.setTotalStyleInRework(getStyleCountForRework(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                styleReport.setTotalStyleInPacking(getStyleCountForPacking(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                styleReport.setTotalStyle(getTotalStyleCount(style.getId(), startOfMonthDateTime, endOfMonthDateTime));
                styleReport.setCreatedAt(startOfMonthDateTime);
                // if (styleReport.getTotalStyleInPacking() < 1 &&
                // styleReport.getTotalStyleInSales() < 1 &&
                // styleReport.getTotalStyleInWarehouse() < 1 &&
                // styleReport.getTotalStyleInRework() < 1) {
                // } else {
                stylesReportRepo.save(styleReport);
                // }
            }

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean generateOldReports(ZonedDateTime startOfFirstData, ZonedDateTime endOfLastData) {
        try {
            // Check if data exists
            if ((startOfFirstData == null || endOfLastData == null)) {
                System.out.println("No data found for generating daily report.");
                return false;
            }

            if (startOfFirstData.isAfter(endOfLastData)) {
                System.out.println("Date error");
                return false;
            }

            // Loop through all days in the range
            ZonedDateTime currentDate = startOfFirstData.with(LocalTime.MIN);
            while (!currentDate.isAfter(endOfLastData)) {
                ZonedDateTime startOfDay = currentDate.with(LocalTime.MIN);
                ZonedDateTime endOfDay = currentDate.with(LocalTime.MAX);

                // Generate reports for the current day
                createNewReportAuto(startOfDay, endOfDay);
                createStyleReportAuto(startOfDay, endOfDay);

                // Move to the next day
                currentDate = currentDate.plusDays(1);
            }
            System.err.print(currentDate);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public FarmicaReport getReportByDates(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<FarmicaReport> reports = reportRepo.findReportByDates(startDate, endDate);
        FarmicaReport farmicaReport = new FarmicaReport();
        if (reports.size() == 0) {
            return null;
        }
        for (FarmicaReport report : reports) {
            farmicaReport.setTotalItemsInSales(
                farmicaReport.getTotalItemsInSales() != null
                    ? (farmicaReport.getTotalItemsInSales() + report.getTotalItemsInSales())
                    : report.getTotalItemsInSales()
            );
            farmicaReport.setTotalItemsInWarehouse(
                farmicaReport.getTotalItemsInWarehouse() != null
                    ? (farmicaReport.getTotalItemsInWarehouse() + report.getTotalItemsInWarehouse())
                    : report.getTotalItemsInWarehouse()
            );
            farmicaReport.setTotalItemsInRework(
                farmicaReport.getTotalItemsInRework() != null
                    ? (farmicaReport.getTotalItemsInRework() + report.getTotalItemsInRework())
                    : report.getTotalItemsInRework()
            );
            farmicaReport.setTotalItemsInPacking(
                farmicaReport.getTotalItemsInPacking() != null
                    ? (farmicaReport.getTotalItemsInPacking() + report.getTotalItemsInPacking())
                    : report.getTotalItemsInPacking()
            );
            farmicaReport.setTotalItems(
                farmicaReport.getTotalItems() != null ? (farmicaReport.getTotalItems() + report.getTotalItems()) : report.getTotalItems()
            );

            farmicaReport.setCreatedAt(report.getCreatedAt());
        }

        return farmicaReport;
    }

    public List<StyleReport> getStyleReportByDates(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<StyleReport> styleReports = new ArrayList<StyleReport>();
        // find total number of styles
        Pageable wholePage = Pageable.unpaged();
        List<StyleDTO> styles = styleService.findAll(wholePage).toList();

        for (StyleDTO style : styles) {
            List<StyleReport> styleReportList = stylesReportRepo.findStyleReportByDates(startDate, endDate, style.getId());
            StyleReport styleReport = new StyleReport();
            if (styleReportList.size() == 0) {
                continue;
            }
            for (StyleReport styleReportItem : styleReportList) {
                System.err.println(styleReportItem);
                styleReport.setTotalStyleInSales(
                    styleReport.getTotalStyleInSales() != null
                        ? (styleReportItem.getTotalStyleInSales() + styleReport.getTotalStyleInSales())
                        : styleReportItem.getTotalStyleInSales()
                );
                styleReport.setTotalStyleInWarehouse(
                    styleReport.getTotalStyleInWarehouse() != null
                        ? (styleReportItem.getTotalStyleInWarehouse() + styleReport.getTotalStyleInWarehouse())
                        : styleReportItem.getTotalStyleInWarehouse()
                );
                styleReport.setTotalStyleInRework(
                    styleReport.getTotalStyleInRework() != null
                        ? (styleReportItem.getTotalStyleInRework() + styleReport.getTotalStyleInRework())
                        : styleReportItem.getTotalStyleInRework()
                );
                styleReport.setTotalStyleInPacking(
                    styleReport.getTotalStyleInPacking() != null
                        ? (styleReportItem.getTotalStyleInPacking() + styleReport.getTotalStyleInPacking())
                        : styleReportItem.getTotalStyleInPacking()
                );
                styleReport.setTotalStyle(
                    styleReport.getTotalStyle() != null
                        ? (styleReportItem.getTotalStyle() + styleReport.getTotalStyle())
                        : styleReportItem.getTotalStyle()
                );
                styleReport.setStyle(styleReportItem.getStyle());
                styleReport.setCreatedAt(styleReportItem.getCreatedAt());
            }
            styleReports.add(styleReport);
        }

        return styleReports;
    }
}
