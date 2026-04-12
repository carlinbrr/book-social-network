package com.bsn.api.adapters.input;

import com.bsn.api.adapters.input.dto.UserRequest;
import com.bsn.api.adapters.input.mapper.UserMapper;
import com.bsn.api.core.port.input.SaveUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@Tag(name = "Users", description = "API For Users")
public class UserRestController {

    private final SaveUserUseCase saveUserUseCase;

    public UserRestController(SaveUserUseCase saveUserUseCase) {
        this.saveUserUseCase = saveUserUseCase;
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest userRequest) {
        saveUserUseCase.save(UserMapper.toSaveUserCommand(userRequest));
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

}
