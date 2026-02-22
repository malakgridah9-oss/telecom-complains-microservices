package org.example.telecomcomplainstestservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiController {

    @GetMapping("/employees/{name}")
    String one(@PathVariable String name) {

        return "Hello "+ name;
    }
}
