package com.charles.zodrac.service;

import com.charles.zodrac.enums.RoleEnum;
import com.charles.zodrac.enums.StatusEnum;
import com.charles.zodrac.exception.CustomException;
import com.charles.zodrac.model.dto.ResponseDTO;
import com.charles.zodrac.model.dto.UserBasicDTO;
import com.charles.zodrac.model.dto.UserDTO;
import com.charles.zodrac.model.dto.UserDetailsDTO;
import com.charles.zodrac.model.entity.UserEntity;
import com.charles.zodrac.model.mapper.UserMapper;
import com.charles.zodrac.repository.UserRepository;
import com.charles.zodrac.security.SecurityUtils;
import com.charles.zodrac.utils.FunctionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository repository;
    private final UserMapper mapper;
    private final MessageSource ms;

    @Transactional
    public ResponseDTO save(UserDTO dto) {
        validateExistsEmail(dto);
        UserEntity entity = mapper.toEntity(dto);
        entity.setPassword(encoder.encode(dto.getPassword()));
        repository.save(entity);
        return new ResponseDTO("user.created", ms);
    }

    public UserBasicDTO get(Long id) {
        return repository.findById(id).map(mapper::toBasicDto).orElseThrow(() -> new CustomException("user.not.found"));
    }

    public UserBasicDTO getDetail() {
        return get(getAuthAccount().getId());
    }

    public Page<UserBasicDTO> getAll(Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return repository.findAll(pageRequest).map(mapper::toBasicDto);
    }

    public UserEntity getAuthAccount() {
        String email = SecurityUtils.getAuthEmail();
        return getAccountByEmail(email);
    }

    public UserEntity getAccountByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new CustomException("user.not.exists.email"));
    }

    public List<GrantedAuthority> getRoles(String email) {
        UserEntity entity = getAccountByEmail(email);
        return Collections.singletonList(new SimpleGrantedAuthority(RoleEnum.ADMIN.equals(entity.getRole()) ? "ROLE_ADMIN" : "ROLE_USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username: {}", username);
        UserEntity entity = repository.findByEmailAndStatusNot(username, StatusEnum.INACTIVE).orElseThrow(() -> new UsernameNotFoundException(String.format("email %s does not exists or account not active", username)));
        List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(RoleEnum.ADMIN.equals(entity.getRole()) ? "ROLE_ADMIN" : "ROLE_USER"));
        return new UserDetailsDTO(roles, entity.getPassword(), entity.getEmail());
    }

    private void validateExistsEmail(UserDTO dto) {
        boolean existsByEmail = repository.existsByEmail(dto.getEmail());
        if (existsByEmail) {
            throw new CustomException("user.exists.email");
        }
    }
}
