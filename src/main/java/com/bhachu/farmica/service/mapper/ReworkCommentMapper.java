package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.ReworkComment;
import com.bhachu.farmica.domain.ReworkDetail;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.service.dto.ReworkCommentDTO;
import com.bhachu.farmica.service.dto.ReworkDetailDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReworkComment} and its DTO {@link ReworkCommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReworkCommentMapper extends EntityMapper<ReworkCommentDTO, ReworkComment> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "reworkDetail", source = "reworkDetail", qualifiedByName = "reworkDetailId")
    ReworkCommentDTO toDto(ReworkComment s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("reworkDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReworkDetailDTO toDtoReworkDetailId(ReworkDetail reworkDetail);
}
