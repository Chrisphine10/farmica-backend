package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.domain.SalesDetail;
import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.domain.WarehouseDetail;
import com.bhachu.farmica.service.dto.LotDetailDTO;
import com.bhachu.farmica.service.dto.SalesDetailDTO;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import com.bhachu.farmica.service.dto.WarehouseDetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalesDetail} and its DTO {@link SalesDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalesDetailMapper extends EntityMapper<SalesDetailDTO, SalesDetail> {
    @Mapping(target = "warehouseDetail", source = "warehouseDetail", qualifiedByName = "warehouseDetailId")
    @Mapping(target = "lotDetail", source = "lotDetail", qualifiedByName = "lotDetailId")
    @Mapping(target = "style", source = "style", qualifiedByName = "styleId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    SalesDetailDTO toDto(SalesDetail s);

    @Named("warehouseDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarehouseDetailDTO toDtoWarehouseDetailId(WarehouseDetail warehouseDetail);

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
