package com.bobocode.rabbitnasa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDto {

    private Integer sol;
    private String camera;

//    @JsonProperty
//    public Optional<String> getCamera() {
//        return Optional.of(camera);
//    }
}
