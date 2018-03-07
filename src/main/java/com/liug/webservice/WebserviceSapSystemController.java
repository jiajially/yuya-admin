package com.liug.webservice;

import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SelectContnet;
import com.liug.common.ssh.SshResult;
import com.liug.common.util.*;
import com.liug.dao.*;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.*;
import com.liug.model.json.Sid;
import com.liug.service.ManagerService;
import com.liug.service.SshHostService;
import com.liug.service.SshScriptService;
import com.liug.service.SshTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "管理模块")
@Controller
@RequestMapping("webservice/sapsystem")
public class WebserviceSapSystemController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceSapSystemController.class);
    @Autowired
    private SshHostService sshHostService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private SapSystemMapper sapSystemMapper;

    @Autowired
    private SapSystemHostMapper sapSystemHostMapper;

    @Autowired
    private SapSystemHardwareMapper sapSystemHardwareMapper;

    @Autowired
    private SapSystemLogMapper sapSystemLogMapper;



    @ResponseBody
    @RequestMapping(value = "/sid")
    public Result sapsystemSidList(@RequestParam String group) {
        List<Sid> sidList = new ArrayList<Sid>();
        List<SapSystem> sapSystemList = sapSystemMapper.selectAll();
        for (SapSystem sapSystem : sapSystemList) {
            sidList.add(new Sid(sapSystem));
        }
        return Result.success(sidList);
    }

    @ResponseBody
    @RequestMapping(value = "/host")
    public Result sapsystemHostList(@RequestParam long sapsystemId) {
        List<SapSystemHost> sapSystemHostList = sapSystemHostMapper.selectAll();
        List<SapSystemHost> sapSystemHostById = new ArrayList<SapSystemHost>();
        if (sapsystemId == 0) sapSystemHostById = sapSystemHostList;
        else {
            for (SapSystemHost sapSystemHost : sapSystemHostList) {
                if (sapSystemHost.getSapsystemId().equals(sapsystemId)) sapSystemHostById.add(sapSystemHost);
            }
        }
        return Result.success(sapSystemHostById);
    }

    @ResponseBody
    @RequestMapping(value = "/host/add")
    public Result addSapsystemHost(@RequestParam int type,
                                   @RequestParam long hostId,
                                   @RequestParam long hardwareId,
                                   @RequestParam long sapsystemId,
                                   @RequestParam String hostname,
                                   @RequestParam(required = false) String cpu,
                                   @RequestParam(required = false) String mem,
                                   @RequestParam(required = false) String disk,
                                   @RequestParam(required = false) String dbinfo,
                                   @RequestParam(required = false) String dbsize,
                                   @RequestParam(required = false) String sapproduct,
                                   @RequestParam(required = false) String kernel,
                                   @RequestParam(required = false) String sapcomponents,
                                   @RequestParam(required = false) Date installTime,
                                   @RequestParam(required = false) String contact,
                                   @RequestParam long creator) {
        SapSystemHost sapSystemHost = new SapSystemHost();
        sapSystemHost.setHostname(hostname);
        SapSystem sapSystem = sapSystemMapper.selectById(sapsystemId);
        sapSystemHost.setSapsystem(sapSystem.getGrouping() + sapSystem.getSid());
        sapSystemHost.setType(type);
        sapSystemHost.setHardwareId(hardwareId);
        sapSystemHost.setHostId(hostId);
        sapSystemHost.setSapsystemId(sapsystemId);
        sapSystemHost.setCreator(creator);
        if (contact != null) sapSystemHost.setContact(contact);
        if (cpu != null) sapSystemHost.setCpu(cpu);
        if (dbinfo != null) sapSystemHost.setDbinfo(dbinfo);
        if (dbsize != null) sapSystemHost.setDbsize(dbsize);
        if (disk != null) sapSystemHost.setDisk(disk);
        if (mem != null) sapSystemHost.setMem(mem);
        if (sapproduct != null) sapSystemHost.setSapproduct(sapproduct);
        if (kernel != null) sapSystemHost.setKernel(kernel);
        if (sapcomponents != null) sapSystemHost.setSapcomponents(sapcomponents);
        if (installTime != null) sapSystemHost.setInstallTime(installTime);
        return managerService.addSapsystemHost(sapSystemHost);
    }

    @ResponseBody
    @RequestMapping(value = "/sid/add")
    public Result addSapsystemSid(@RequestParam String sid,
                                  @RequestParam String group,
                                  @RequestParam(required = false) String company,
                                  @RequestParam(required = false) String address) {

        try {
            if (sid.trim().length() != 3) return Result.error("sid长度错误");
            List<SapSystem> sapSystemList = sapSystemMapper.selectAll();
            for (SapSystem sapSystem : sapSystemList) {
                if (sapSystem.getSid().trim().equals(sid.trim()) && sapSystem.getGrouping().trim().equals(group.trim()))
                    return Result.error("sid已存在");
            }
            SapSystem newSystem = new SapSystem();
            newSystem.setAddress(address);
            newSystem.setGrouping(group);
            newSystem.setSid(sid);
            newSystem.setCompany(company);
            sapSystemMapper.insert(newSystem);
            return Result.success(sapSystemMapper.selectAll());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/hardware")
    public Result sapsystemHardwareList(@RequestParam String group) {
        return Result.success(sapSystemHardwareMapper.selectAll());
    }


    @ResponseBody
    @RequestMapping(value = "/hardware/add")
    public Result addHardware(@RequestParam String version,
                              @RequestParam String group,
                              @RequestParam(required = false) String name) {
        SapSystemHardware hardware = new SapSystemHardware();
        hardware.setBrand(group);
        hardware.setModel(version);
        hardware.setSummary(name);
        return Result.success(sapSystemHardwareMapper.insert(hardware));
    }

    @RequestMapping(value = "/host/hostname")
    @ResponseBody
    public Result getHostname(@RequestParam long hostId) {
        SshHost host = sshHostService.selectById(hostId);
        String hostname = null;
        SshResult sshResult = Commond.execute(host, "hostname");
        return Result.transform(sshResult);
    }

    @RequestMapping(value = "/host/list")
    @ResponseBody
    public Result getSapsystemHostList(@RequestParam long sapsystemId) {
        return Result.success(sapSystemHostMapper.selectBySapsystemId(sapsystemId));
    }

    @RequestMapping(value = "/host/delete")
    @ResponseBody
    public Result deleteSapsystemHost(@RequestParam long id) {
        return Result.success(sapSystemHostMapper.deleteById(id));
    }


    @RequestMapping(value = "/updatelog")
    @ResponseBody
    public Result updatelog(@RequestParam long sapsystemId) {
        return Result.success(sapSystemLogMapper.selectBySapsystemId(sapsystemId));
    }






}
