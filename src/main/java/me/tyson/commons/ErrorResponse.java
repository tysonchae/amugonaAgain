package me.tyson.commons;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by oolong on 2017-12-28.
 */
@Data
public class ErrorResponse {

    private String message;
    private String code;

    private List<FieldError> errors;

    //TODO
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }
}
