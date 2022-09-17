package com.charles.zodrac.model.dto;

import com.charles.zodrac.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class CharacterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private GenderEnum gender;

    @NotNull
    private LocalDate birthDate;
}
