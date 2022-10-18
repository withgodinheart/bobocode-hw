package com.bobocode.redisproxyfeign.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "picture-service", url = "${service.picture.url}")
public interface ProxyClient {

    @GetMapping(path = "${service.picture.path}")
    @Cacheable("redis")
    byte[] getPicture(@RequestParam Integer sol,
                      @RequestParam(required = false) String camera);
}
