package com.charles.zodrac.model.dto;

import com.charles.zodrac.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class CharacterBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String image;
    private GenderEnum gender;
    private LocalDate birthDate;
    private Integer level;
}