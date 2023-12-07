package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.FarmicaReport;
import com.bhachu.farmica.service.dto.FarmicaReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FarmicaReport} and its DTO {@link FarmicaReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface FarmicaReportMapper extends EntityMapper<FarmicaReportDTO, FarmicaReport> {}
