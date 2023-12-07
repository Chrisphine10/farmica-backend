package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.domain.PackingZoneDetail;
import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.service.dto.LotDetailDTO;
import com.bhachu.farmica.service.dto.PackingZoneDetailDTO;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PackingZoneDetail} and its DTO {@link PackingZoneDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface PackingZoneDetailMapper extends EntityMapper<PackingZoneDetailDTO, PackingZoneDetail> {
    @Mapping(target = "lotDetail", source = "lotDetail", qualifiedByName = "lotDetailId")
    @Mapping(target = "style", source = "style", qualifiedByName = "styleId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    PackingZoneDetailDTO toDto(PackingZoneDetail s);

    @Named("lotDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LotDetailDTO toDtoLotDetailId(LotDetail lotDetail);

    @Named("styleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StyleDTO toDtoStyleId(Style style);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
