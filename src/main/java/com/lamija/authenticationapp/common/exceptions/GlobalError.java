package com.lamija.authenticationapp.common.exceptions;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GlobalError {

    private List<String> errorList;

    public GlobalError(List<String> errorList) {
        this.errorList = errorList;
    }

    public GlobalError(String message) {
        this.errorList = Collections.singletonList(message);
    }
}
