package com.aromero.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ValidationErrorService {

    ResponseEntity<?> mapValidationError(BindingResult result);
}
