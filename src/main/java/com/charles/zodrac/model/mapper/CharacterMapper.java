package com.charles.zodrac.model.mapper;

import com.charles.zodrac.model.dto.CharacterBasicDTO;
import com.charles.zodrac.model.dto.CharacterDTO;
import com.charles.zodrac.model.entity.CharacterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    CharacterDTO toDto(CharacterEntity entity);

    CharacterEntity toEntity(CharacterDTO dto);

    CharacterBasicDTO toBasicDto(CharacterEntity entity);

    CharacterEntity toEntity(CharacterBasicDTO dto);
}
