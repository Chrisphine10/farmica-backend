package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Style} and its DTO {@link StyleDTO}.
 */
@Mapper(componentModel = "spring")
public interface StyleMapper extends EntityMapper<StyleDTO, Style> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    StyleDTO toDto(Style s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
