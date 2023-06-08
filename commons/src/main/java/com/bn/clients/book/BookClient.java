package com.bn.clients.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "book",
        url = "http://${api.gateway.network}:${api.gateway.book.port}",
        path = "api/v1/book")
public interface BookClient {
    @GetMapping("/test")
    String test();
}
