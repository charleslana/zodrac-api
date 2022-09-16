package com.charles.zodrac.model.dto;

import com.charles.zodrac.utils.LocaleUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;

    public ResponseDTO(String message, MessageSource ms) {
        this.message = ms.getMessage(message, null, LocaleUtils.currentLocale());
    }
}
