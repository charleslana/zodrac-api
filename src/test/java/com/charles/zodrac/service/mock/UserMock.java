package com.charles.zodrac.service.mock;

import com.charles.zodrac.model.dto.UserDTO;
import com.charles.zodrac.model.entity.UserEntity;
import com.charles.zodrac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMock {

    @Autowired
    private UserRepository repository;

    public void createUser(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        repository.save(entity);
    }

    public UserDTO getUserMock() {
        return getUserMock("user@user.com", "123456");
    }

    public UserDTO getUserMock(String email, String password) {
        UserDTO dto = new UserDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    public void deleteAllUser() {
        repository.deleteAll();
    }

    public Long getUser() {
        Optional<UserEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
