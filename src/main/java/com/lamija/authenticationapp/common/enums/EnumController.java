package com.lamija.authenticationapp.common.enums;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/enums", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
public class EnumController {

    @GetMapping(path = "/role")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get role enum values",
            description = "Gets all acceptable values for role enum.")
    public List<EnumObject> getRoleEnum() {
        return RolesEnum.items();
    }
}
