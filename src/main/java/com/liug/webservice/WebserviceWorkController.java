package com.liug.webservice;

import com.liug.common.util.*;
import com.liug.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Controller
@RequestMapping("webservice/work")
public class WebserviceWorkController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceWorkController.class);
    @Autowired
    MantisProjectSummaryMapper mantisProjectSummaryMapper;

    @RequestMapping(value = "/mantis/summary")
    @ResponseBody
    public Result getSapsystemHostList() {
        return Result.success(mantisProjectSummaryMapper.selectAll());
    }
}
