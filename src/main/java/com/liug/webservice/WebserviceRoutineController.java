package com.liug.webservice;

import com.liug.common.util.FileStruct;
import com.liug.common.util.FileUtil;
import com.liug.common.util.Result;
import com.liug.common.util.VBAUtil;
import com.liug.dao.SapScriptMapper;
import com.liug.model.entity.Document;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
            for (FileStruct struct : file_all) {
                if (struct.getName().split("\\.").length == 2) {
                    String suffix = struct.getName().split("\\.")[1];
                    if (!suffix.equals("file") && !suffix.equals("routine")) {
                        report r = new report();
                        r.setName(struct.getName());
                        Calendar cd = Calendar.getInstance();
                        cd.setTimeInMillis(new File(FileUtil.getProjectPath() + "/file/system/" + system + "/report/" + struct.getName()).lastModified());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        r.setCreateTime(sdf.format(cd.getTime()));
                        r.setSummary("未检验");
                        r.setCheck(false);
                        reports.add(r);
                    }

                }
            }
            for (String s_f : file) {
                for (String s_fr : file_routine) {
                    if (s_f.split("\\.")[0].equals(s_fr.split("\\.")[0])) {
                        report r = new report();
                        JSONObject object = JSONObject.fromObject(FileUtil.loadFile(path + "/" + s_fr, "UTF-8"));
                        r.setName(object.getString("name"));
                        r.setSummary(object.getString("summary"));
                        Calendar cd = Calendar.getInstance();

                        cd.setTimeInMillis(new File(FileUtil.getProjectPath() + "/file/system/" + system + "/report/" + s_f).lastModified());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        r.setCreateTime(sdf.format(cd.getTime()));

                        r.setCreator(object.getString("creator"));
                        r.setCheck(true);
                        r.setId(s_f.split("\\.")[0]);
                        r.setSuffix(object.getString("suffix"));
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
        String id;
        String name;
        String summary;
        String creator;
        String createTime;
        String suffix;
        boolean check;

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
    public Result setscript(@RequestParam String system, @RequestParam String sapscript) {
        JSONObject obj = new JSONObject();
        obj.put("sapscript", sapscript);
        return Result.success(FileUtil.createFileForce(FileUtil.getProjectPath() + "/file/system/" + system + "/report/script", obj.toString()));
    }


    @RequestMapping(value = "report/download", method = RequestMethod.GET)
    public Result getFile(HttpServletResponse resp, @RequestParam("filename") String filename, @RequestParam("ofilename") String ofilename) throws UnsupportedEncodingException {

        String path = FileUtil.getProjectPath() + "/file/system/ecc/report/" + filename;
        File file = new File(path);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(ofilename, "utf-8"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
            os.close();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Result.success();
        }
    }


    /**
     * check
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "report/check", method = RequestMethod.GET)
    public Result check(@RequestParam String system,
                        @RequestParam String filename,
                        @RequestParam String summary,
                        @RequestParam String name,
                        @RequestParam String suffix,
                        @RequestParam String creator,
                        @RequestParam boolean check
    ) {

        String id = String.valueOf(System.currentTimeMillis());
        File file = new File(FileUtil.getProjectPath() + "/file/system/ecc/report/" + filename);
        if (check) {
            file = new File(FileUtil.getProjectPath() + "/file/system/ecc/report/" + filename + ".file");
            File routine = new File(FileUtil.getProjectPath() + "/file/system/ecc/report/" + filename+ ".routine");
            if(routine.exists())routine.delete();
        }
        if (file.exists()) {
            //写审核信息
            JSONObject obj = new JSONObject();
            obj.put("name", name);
            obj.put("summary", summary);
            obj.put("suffix", suffix);
            obj.put("creator", creator);
            Calendar cd = Calendar.getInstance();
            cd.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            obj.put("createTime", sdf.format(cd.getTime()));
            FileUtil.createFileForce(FileUtil.getProjectPath() + "/file/system/" + system + "/report/" + id + ".routine", obj.toString());

            //更名 .file
            File newfile = new File(FileUtil.getProjectPath() + "/file/system/" + system + "/report/" + id + ".file");
            if (newfile.exists()) newfile.delete();
            file.renameTo(newfile);

            return Result.success();
        } else {
            return Result.error();
        }
    }
}
