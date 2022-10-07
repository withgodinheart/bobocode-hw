package com.bobocode.nasafeign.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "nasa-service-records", url = "${nasa.api.url}")
public interface NasaClientRecords {

    @GetMapping("${nasa.api.path}")
    Photos find(@RequestParam(name = "api_key") String apiKey, @RequestParam Integer sol, @RequestParam(required = false) String camera);

    record Photos(List<Photo> photos) {
    }

    record Photo(@JsonProperty("img_src") String ImgSrc) {
    }
}