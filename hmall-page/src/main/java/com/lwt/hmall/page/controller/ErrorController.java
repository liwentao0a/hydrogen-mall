package com.lwt.hmall.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @Author lwt
 * @Date 2020/4/8 12:57
 * @Description
 */
@Controller
public class ErrorController {

    @RequestMapping("/err")
    public String err(@RequestParam("status") Integer status, HttpServletRequest request){
        if (status!=null){
            String url="/error/"+status+".html";
            String path = getClass().getClassLoader().getResource("static").getPath();
            File file = new File(path + url);
            if (!file.exists()){
                String s = (status / 100) + "xx";
                url="/error/"+s+".html";
            }
            return url;
        }else {
            throw new RuntimeException();
        }
    }
}
