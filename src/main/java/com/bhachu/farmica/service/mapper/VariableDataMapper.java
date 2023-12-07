package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.VariableData;
import com.bhachu.farmica.service.dto.VariableDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VariableData} and its DTO {@link VariableDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface VariableDataMapper extends EntityMapper<VariableDataDTO, VariableData> {}
