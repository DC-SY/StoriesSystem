package cn.dcsy.stsy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DC_DC
 * Date: 2024/3/21/23:54
 */
@RestController
public class Demo {

    @GetMapping("/index")
    public String domain(){
        System.out.println("demo");
        return "DC_DC";
    }
}
