package com.lamija.authenticationapp.common.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"enum", "value"})
public class EnumObject {
    @JsonProperty("enum")
    protected String enum_;

    protected String value;
}
