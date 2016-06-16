package org.funtime.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

/**
 * Created by uv on 22.01.2016 for awstest
 * common testcontroller
 */
public class TestController {

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/beans", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listOfDefinedBeans() {
        return ResponseEntity.ok(Arrays.asList(applicationContext.getBeanDefinitionNames()));
    }

}
