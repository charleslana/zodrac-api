package com.charles.zodrac.model.mapper;

import com.charles.zodrac.model.dto.UserBasicDTO;
import com.charles.zodrac.model.dto.UserDTO;
import com.charles.zodrac.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserEntity entity);

    UserEntity toEntity(UserDTO dto);

    UserBasicDTO toBasicDto(UserEntity entity);

    UserEntity toEntity(UserBasicDTO dto);
}