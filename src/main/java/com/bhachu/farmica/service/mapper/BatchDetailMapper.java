package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.BatchDetail;
import com.bhachu.farmica.domain.Region;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.service.dto.BatchDetailDTO;
import com.bhachu.farmica.service.dto.RegionDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BatchDetail} and its DTO {@link BatchDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface BatchDetailMapper extends EntityMapper<BatchDetailDTO, BatchDetail> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    BatchDetailDTO toDto(BatchDetail s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
