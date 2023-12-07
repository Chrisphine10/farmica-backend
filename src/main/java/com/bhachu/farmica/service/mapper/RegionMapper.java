package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.Region;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.service.dto.RegionDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    RegionDTO toDto(Region s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
