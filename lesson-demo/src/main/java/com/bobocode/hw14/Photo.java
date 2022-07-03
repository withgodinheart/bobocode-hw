package com.bobocode.hw14;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class Photo {
  
    @JsonProperty("img_src")
    private String imgSrc;

    private Long size;

    @Override
    public String toString() {
        return  "imgSrc: " + imgSrc + '\n' +
                "size: " + size + '\n';
    }
}
