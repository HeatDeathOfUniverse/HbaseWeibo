package com.weibo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: Hbase_web
 * @description:
 * @author: renxuw
 * @create: 2020-06-21 12:09
 **/
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello(Model model) {
        model.addAttribute("msg", "hello,hbase");
        return "hello";
    }
}
