package com.charles.zodrac.service.mock;

import com.charles.zodrac.enums.GenderEnum;
import com.charles.zodrac.model.dto.CharacterDTO;
import com.charles.zodrac.model.entity.CharacterEntity;
import com.charles.zodrac.model.entity.UserEntity;
import com.charles.zodrac.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class CharacterMock {

    @Autowired
    private CharacterRepository repository;

    public void createCharacter(CharacterDTO dto, Long userId) {
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setName(dto.getName());
        characterEntity.setGender(dto.getGender());
        characterEntity.setBirthDate(dto.getBirthDate());
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        characterEntity.setUser(userEntity);
        repository.save(characterEntity);
    }

    public CharacterDTO getCharacterMock(String name) {
        CharacterDTO dto = new CharacterDTO();
        dto.setName(name);
        dto.setGender(GenderEnum.MALE);
        dto.setBirthDate(LocalDate.now());
        return dto;
    }

    public void deleteAllCharacter() {
        repository.deleteAll();
    }

    public Long getCharacter() {
        Optional<CharacterEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
