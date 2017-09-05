package com.liug.controller;

import com.liug.common.config.WebSecurityConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by liugang on 2017/6/3.
 */
@Controller
public class IndexController {

    @Value("${project.name}")
    String projectName;

    @RequestMapping("/")
    public String easyuiIndex(Map<String,Object> map){
        try {
            //File file = ResourceUtils.getFile("classpath:banner.txt");
            InputStream is = IndexController.class.getClassLoader().getResourceAsStream("dev_log.txt");
            String result = IOUtils.toString(is,"UTF-8");
            map.put("banner", result);
            map.put("name", projectName);
            System.out.println(projectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "system/index";
    }




}
