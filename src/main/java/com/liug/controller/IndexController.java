package com.liug.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by liugang on 2017/6/3.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String easyuiIndex(Map<String,Object> map){
        try {
            //File file = ResourceUtils.getFile("classpath:banner.txt");
            InputStream is = IndexController.class.getClassLoader().getResourceAsStream("dev_log.txt");
            String result = IOUtils.toString(is,"UTF-8");
            map.put("banner", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "system/index";
    }
}
