package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.BatchDetail;
import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.service.dto.BatchDetailDTO;
import com.bhachu.farmica.service.dto.LotDetailDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LotDetail} and its DTO {@link LotDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface LotDetailMapper extends EntityMapper<LotDetailDTO, LotDetail> {
    @Mapping(target = "batchDetail", source = "batchDetail", qualifiedByName = "batchDetailId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    LotDetailDTO toDto(LotDetail s);

    @Named("batchDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BatchDetailDTO toDtoBatchDetailId(BatchDetail batchDetail);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
