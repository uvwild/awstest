package org.funtime.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uv on 22.01.2016 for awstest
 */
@RestController
public class HomeController {

    @RequestMapping("/home")
    public String index() {
        return "Welcome to the home page!";
    }
}
