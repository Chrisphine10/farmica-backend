package com.bhachu.farmica.service.mapper;

import com.bhachu.farmica.domain.Comment;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.service.dto.CommentDTO;
import com.bhachu.farmica.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    CommentDTO toDto(Comment s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
