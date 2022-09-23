package com.charles.zodrac.service;

import com.charles.zodrac.enums.BannedEnum;
import com.charles.zodrac.exception.BusinessRuleException;
import com.charles.zodrac.model.dto.CharacterBasicDTO;
import com.charles.zodrac.model.dto.CharacterDTO;
import com.charles.zodrac.model.dto.ResponseDTO;
import com.charles.zodrac.model.entity.CharacterEntity;
import com.charles.zodrac.model.entity.UserEntity;
import com.charles.zodrac.model.mapper.CharacterMapper;
import com.charles.zodrac.repository.CharacterRepository;
import com.charles.zodrac.security.SecurityUtils;
import com.charles.zodrac.utils.FunctionUtils;
import com.charles.zodrac.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Slf4j
@Transactional(readOnly = true)
public class CharacterService {

    @Lazy
    private final UserService userService;
    private final CharacterRepository repository;
    private final CharacterMapper mapper;
    private final MessageSource ms;

    @Transactional
    public ResponseDTO save(CharacterDTO dto) {
        validateCountExceeded();
        validateExistsName(dto);
        UserEntity userEntity = userService.getAuthAccount();
        CharacterEntity entity = mapper.toEntity(dto);
        entity.setUser(userEntity);
        repository.save(entity);
        return new ResponseDTO(MessageUtils.CHARACTER_SUCCESS, "character.created", ms);
    }

    public CharacterBasicDTO get() {
        return repository.findById(getAuthCharacter().getId()).map(mapper::toBasicDto).orElseThrow(() -> new BusinessRuleException(MessageUtils.CHARACTER_EXCEPTION, "character.not.found"));
    }

    public List<CharacterBasicDTO> getAll() {
        return  repository.findAllByUserIdOrderById(userService.getAuthAccount().getId()).stream().map(mapper::toBasicDto).toList();
    }

    public Page<CharacterBasicDTO> search(String searchTerm, Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return repository.search(searchTerm.toLowerCase(), pageRequest).map(mapper::toBasicDto);
    }

    public void select(Long id) {
        SecurityUtils.setCharacterId(getCharacterId(id).getId());
    }

    public void logout() {
        existsCharacterId();
        SecurityUtils.removeCharacterId();
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        CharacterEntity entity = getCharacterId(id);
        repository.delete(entity);
        return new ResponseDTO(MessageUtils.CHARACTER_SUCCESS, "character.delete", ms);
    }

    public CharacterEntity getAuthCharacter() {
        existsCharacterId();
        return getCharacterId(SecurityUtils.getUserDetails().getCharacterId());
    }

    private void existsCharacterId() {
        if (Boolean.FALSE.equals(SecurityUtils.existsCharacterId())) {
            throw new BusinessRuleException(MessageUtils.CHARACTER_EXCEPTION, "character.not.found");
        }
    }

    private CharacterEntity getCharacterId(Long id) {
        return repository.findByIdAndUserIdAndBanned(id, userService.getAuthAccount().getId(), BannedEnum.NO).orElseThrow(() -> new BusinessRuleException(MessageUtils.CHARACTER_EXCEPTION, "character.not.found"));
    }

    private void validateCountExceeded() {
        if (repository.countByUserId(userService.getAuthAccount().getId()) >= 4) {
            throw new BusinessRuleException(MessageUtils.CHARACTER_EXCEPTION, "character.count.exceeded");
        }
    }

    private void validateExistsName(CharacterDTO dto) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName());
        if (existsByName) {
            throw new BusinessRuleException(MessageUtils.CHARACTER_EXCEPTION, "character.exists.name");
        }
    }
}
