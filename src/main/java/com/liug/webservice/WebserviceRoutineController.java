package com.liug.webservice;

import com.liug.common.util.FileStruct;
import com.liug.common.util.FileUtil;
import com.liug.common.util.Result;
import com.liug.common.util.VBAUtil;
import com.liug.dao.SapScriptMapper;
import com.liug.model.entity.HomePage;
import com.liug.model.entity.SapScript;
import com.liug.model.entity.SysUser;
import com.liug.service.DevService;
import com.liug.service.ManagerService;
import io.swagger.annotations.Api;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "用户管理模块")
@Controller
@RequestMapping("webservice/routine")
public class WebserviceRoutineController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceRoutineController.class);
    @Autowired
    SapScriptMapper sapScriptMapper;
    @Autowired
    ManagerService managerService;

    /**
     * 巡检列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "report", method = RequestMethod.GET)
    public Result report(@RequestParam String system) {
        List<report> reports = new ArrayList<report>();
        try {
            //   扫描目录/file/system/ecc/report 后缀名为.routine
            String path = FileUtil.getProjectPath() + "/file/system/" + system + "/report";
            List<String> file = FileUtil.getFileNameList(path, "file");
            List<String> file_routine = FileUtil.getFileNameList(path, "routine");
            List<FileStruct> file_all = FileUtil.readfileList(path);

            for (String s_f : file) {
                for (String s_fr : file_routine) {
                    if (s_f.split("\\.")[0].equals(s_fr.split("\\.")[0])) {
                        report r = new report();
                        JSONObject object = JSONObject.fromObject(FileUtil.loadFile(path + "/" + s_fr, "UTF-8"));
                        r.setName(object.getString("name"));
                        r.setSummary(object.getString("summary")); Calendar cd = Calendar.getInstance();

                        cd.setTimeInMillis(new File(FileUtil.getProjectPath() + "/file/system/" + system + "/report/"+s_f).lastModified());
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        r.setCreateTime(sdf.format(cd.getTime()));

                        r.setCreator(object.getString("creator"));
                        r.setCheck(true);
                        reports.add(r);
                    }
                }
            }


            for (FileStruct struct : file_all) {
                if (struct.getName().split("\\.").length == 2) {
                    String suffix = struct.getName().split("\\.")[1];
                    if (!suffix.equals("file") && !suffix.equals("routine")) {
                        report r = new report();
                        r.setName(struct.getName());
                        Calendar cd = Calendar.getInstance();
                        cd.setTimeInMillis(new File(FileUtil.getProjectPath() + "/file/system/" + system + "/report/"+struct.getName()).lastModified());
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        r.setCreateTime(sdf.format(cd.getTime()));
                        r.setSummary("未检验");
                        r.setCheck(false);
                        reports.add(r);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(reports);
    }

    class report {
        Long id;
        String name;
        String summary;
        String creator;
        String createTime;
        boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    /**
     * sapscript
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "report/sapscript", method = RequestMethod.GET)
    public Result sapscript(@RequestParam String system) {

        String content = FileUtil.loadFile(FileUtil.getProjectPath() + "/file/system/" + system + "/report/script");
        JSONObject json = JSONObject.fromObject(content);
        String sapscriptIds = json.getString("sapscript");
        List<SapScript> sapscriptList = new ArrayList<SapScript>();
        if (sapscriptIds != null) {
            String[] sapscriptIdList = sapscriptIds.split(",");
            for (String id : sapscriptIdList) {
                sapscriptList.add(sapScriptMapper.selectById(Long.valueOf(id)));
            }
        }
        //   return null;
        return Result.success(sapscriptList);
    }


    /**
     * exec
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "report/exec", method = RequestMethod.GET)
    public Result exec(@RequestParam Long id, @RequestParam String system) {
        SapScript sapScript = sapScriptMapper.selectById(id);
        String content = (String) managerService.generator(sapScript, "\\file\\system\\" + system + "\\report\\").getData();
        String fileName = "tmp" + System.currentTimeMillis() + ".vbs";
        FileOutputStream fileOutputStream;
        File file = new File(FileUtil.getProjectPath() + "/file/script/vbs/" + fileName);
        try {
            if (file.exists()) {
                file.createNewFile();
            }

            fileOutputStream = new FileOutputStream(FileUtil.getProjectPath() + "/file/script/vbs/" + fileName);
            fileOutputStream.write(content.getBytes("GB2312"));
            fileOutputStream.flush();
            fileOutputStream.close();

            //执行
            VBAUtil.execVBS(FileUtil.getProjectPath() + "/file/script/vbs/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Result.success();
        }

    }

    /**
     * setscript
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "report/setscript", method = RequestMethod.GET)
    public Result setscript(@RequestParam String system,@RequestParam String sapscript) {
        JSONObject obj = new JSONObject();
        obj.put("sapscript", sapscript);
        return  Result.success(FileUtil.createFileForce(FileUtil.getProjectPath() + "/file/system/"+system+"/report/script",obj.toString()));
    }

}
