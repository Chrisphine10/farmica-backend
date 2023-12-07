package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.StyleReport;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.dto.StyleReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StyleReport} and its DTO {@link StyleReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface StyleReportMapper extends EntityMapper<StyleReportDTO, StyleReport> {
    @Mapping(target = "style", source = "style", qualifiedByName = "styleId")
    StyleReportDTO toDto(StyleReport s);

    @Named("styleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StyleDTO toDtoStyleId(Style style);
}
