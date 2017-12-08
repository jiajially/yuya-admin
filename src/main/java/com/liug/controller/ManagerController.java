package com.liug.controller;

import com.liug.common.util.Result;
import com.liug.dao.SapScriptStaticMapper;
import com.liug.model.dto.PageInfo;
import com.liug.model.entity.ManagerProblem;
import com.liug.model.entity.ManagerWork;
import com.liug.model.entity.SapScriptStatic;
import com.liug.service.ManagerService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author liug
 * @Date 2016/10/8/13:37
 * @Description
 */
@Api(value = "管理工作")
@Controller
@RequestMapping("manager")
public class ManagerController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
    @Autowired
    private ManagerService managerService;
    @Autowired
    private SapScriptStaticMapper sapScriptStaticMapper;


    @ApiOperation(value = "problem", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "problem", method = RequestMethod.GET)
    public String problem() {
        return "manager/problem";
    }
    @ApiOperation(value = "work", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "work", method = RequestMethod.GET)
    public String work() {
        return "manager/work";
    }


    /**
     * 查询问题列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param begin     开始
     * @param end       结束
     * @return
     */
    @ApiOperation(value = "查询问题列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "problem/list", method = RequestMethod.GET)
    public PageInfo problemList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "30") int rows,
                                @RequestParam(defaultValue = "id") String sort,
                                @RequestParam(defaultValue = "asc") String order,
                                @RequestParam(required = false) String begin,
                                @RequestParam(required = false) String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_begin, date_end;
        try {
            date_begin = sdf.parse(begin);
        } catch (ParseException e) {
            date_begin = new Date();
            date_begin.setTime(0);
        }
        try {
            date_end = sdf.parse(end);
        } catch (ParseException e) {
            date_end = new Date(System.currentTimeMillis());
        }

        PageInfo pageInfo = managerService.selectProblemPage(page,rows,sort,order,date_begin,date_end);
        return pageInfo;
    }
    /**
     * 根据id查problem
     *
     * @param id      id
     *
     * @return
     */
    @ApiOperation(value = "查询问题列表", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "problem/detail", method = RequestMethod.GET)
    public Result problemList(@RequestParam(required = true) int id) {
        return Result.success(managerService.getProblemById(id));
    }
    /**
     * 添加问题
     *
     * @param summary      起始页码
     * @param channel      分页大小
     * @param informer     排序字段
     * @return
     */
    @ApiOperation(value = "添加问题", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "problem/insert", method = RequestMethod.POST)
    public Result addProblem(
                            @RequestParam(required = true) String summary,
                            @RequestParam(required = true) String channel,
                            @RequestParam(required = true) String informer
                                ) {
        long id = managerService.addProblem(summary,channel,informer);
        if (id>0)return  Result.success(id);
        else return Result.error();
    }

    /**
     * 处理问题
     *
     * @param id      起始页码
     * @return
     */
    @ApiOperation(value = "处理问题", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "problem/deal", method = RequestMethod.POST)
    public Result dealProblem(
            @RequestParam(required = true) long id,
            @RequestParam(required = false) String detail,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String system,
            @RequestParam(required = false) String needtime,
            @RequestParam(required = false) String needresources,
            @RequestParam(required = false) String result,
            @RequestParam(required = false) String dealer,
            @RequestParam(required = false) String recorder
    ) {
        ManagerProblem problem = new ManagerProblem();
        problem.setId(id);
        problem.setDetail(detail);
        problem.setType(type);
        problem.setLevel(level);
        problem.setSystem(system);
        problem.setNeedtime(needtime);
        problem.setNeedresources(needresources);
        problem.setResult(result);
        problem.setDealer(dealer);
        problem.setRecorder(recorder);
        if (managerService.dealProblem(problem)>0)return  Result.success();
        else return Result.error();
    }



    /**
     * 查询工作记录列表
     *
     * @param page      起始页码
     * @param rows      分页大小
     * @param sort      排序字段
     * @param order     排序规则
     * @param begin     开始
     * @param end       结束
     * @return
     */
    @ApiOperation(value = "查询工作记录列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequestMapping(value = "work/list", method = RequestMethod.GET)
    public PageInfo workList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "30") int rows,
                                @RequestParam(defaultValue = "id") String sort,
                                @RequestParam(defaultValue = "asc") String order,
                                @RequestParam(required = false) String begin,
                                @RequestParam(required = false) String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_begin, date_end;
        try {
            date_begin = sdf.parse(begin);
        } catch (ParseException e) {
            date_begin = new Date();
            date_begin.setTime(0);
        }
        try {
            date_end = sdf.parse(end);
        } catch (ParseException e) {
            date_end = new Date(System.currentTimeMillis());
        }

        PageInfo pageInfo = managerService.selectWorkPage(page,rows,sort,order,date_begin,date_end);
        return pageInfo;
    }

    /**
     * 添加工作记录
     *
     * @param summary      起始页码
     * @param type         类型
     * @param level        优先级
     * @return
     */
    @ApiOperation(value = "添加工作记录", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "work/insert", method = RequestMethod.POST)
    public Result addWork(
            @RequestParam(required = true) String summary,
            @RequestParam(required = true) String type,
            @RequestParam(required = true) String level
    ) {
        long id = managerService.addWork(summary,level,type);
        if (id>0)return  Result.success(id);
        else return Result.error();
    }


    /**
     * 处理工作
     *
     * @param id      id
     * @return
     */
    @ApiOperation(value = "处理工作", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "work/deal", method = RequestMethod.POST)
    public Result dealWork(
            @RequestParam(required = true) long id
    ) {
        ManagerWork managerWork = new ManagerWork();
        managerWork.setId(id);
        managerWork.setFinishdate(new Date(System.currentTimeMillis()));
        if (managerService.dealWork(managerWork)>0)return  Result.success("处理完成");
        else return Result.error();
    }
    /**
     * 删除工作
     *
     * @param id      id
     * @return
     */
    @ApiOperation(value = "删除工作", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "work/delete", method = RequestMethod.POST)
    public Result deleteWork(
            @RequestParam(required = true) long id
    ) {
        if (managerService.deleteWork(id)>0)return  Result.success("处理完成");
        else return Result.error();
    }
    /**
     * 查
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sapscript", method = RequestMethod.GET )
    public Result sapscript() {

        return managerService.selectSapScript();
    }
    /**
     * 查
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sapscriptstatic", method = RequestMethod.GET )
    public Result sapscriptstatic() {

/*        SapScriptStatic sapScriptStatic = new SapScriptStatic();
        String t_code = "sm12";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm12\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]/usr/ctxtSEQG3-GCLIENT\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/txtSEQG3-GUNAME\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/txtSEQG3-GUNAME\").setFocus\n" +
                "session.findById(\"wnd[0]/usr/txtSEQG3-GUNAME\").caretPosition = 3\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm13";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm13\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/txtSEL_CLIENT\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/txtSEL_USER\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/txtFROM_DATE\").text = yesterday_format\n" +
                "session.findById(\"wnd[0]/usr/txtFROM_DATE\").setFocus\n" +
                "session.findById(\"wnd[0]/usr/txtFROM_DATE\").caretPosition = 10\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm20";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm20\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-LOGIN\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-RFCLOGIN\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-RFCSTART\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-TASTART\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-REPOSTART\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-USERSTAMM\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-SONST\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/txtDY_START_DATE\").text = yesterday_format\n" +
                "session.findById(\"wnd[0]/usr/txtDY_START_TIME\").text = \"00:00:00\"\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP/tabpAUDI/ssubTABAREA:SAPMSM20:0310/chkRSAUINFO-SONST\").setFocus\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "\n" +
                "'session.findById(\"wnd[1]/tbar[0]/btn[0]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)\n");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm21";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm21\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/chkRB1\").selected = true\n" +
                "session.findById(\"wnd[0]/usr/ctxtDATE_FR\").text = yesterday_format\n" +
                "session.findById(\"wnd[0]/usr/ctxtTIME_FR\").text = \"00:00:00\"\n" +
                "session.findById(\"wnd[0]/usr/chkRB1\").setFocus\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "session.findById(\"wnd[0]/usr/cntlCONTAINER_0100/shellcont/shell/shellcont[0]/shell\").setCurrentCell -1,\"ICON\"\n" +
                "session.findById(\"wnd[0]/usr/cntlCONTAINER_0100/shellcont/shell/shellcont[0]/shell\").selectColumn \"ICON\"\n" +
                "session.findById(\"wnd[0]/usr/cntlCONTAINER_0100/shellcont/shell/shellcont[0]/shell\").pressToolbarButton \"&SORT_ASC\"\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)\n");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm37";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm37\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/chkBTCH2170-SCHEDUL\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/chkBTCH2170-READY\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/chkBTCH2170-RUNNING\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/chkBTCH2170-FINISHED\").selected = false\n" +
                "session.findById(\"wnd[0]/usr/txtBTCH2170-USERNAME\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/ctxtBTCH2170-FROM_DATE\").text = yesterday_format\n" +
                "session.findById(\"wnd[0]/usr/ctxtBTCH2170-FROM_DATE\").setFocus\n" +
                "session.findById(\"wnd[0]/usr/ctxtBTCH2170-FROM_DATE\").caretPosition = 10\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)\n");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm50";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm50\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell\").setCurrentCell -1,\"FAILURES_DISP\"\n" +
                "session.findById(\"wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell\").selectColumn \"FAILURES_DISP\"\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[41]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)\n");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm51";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm51\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)\n");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm58";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm58\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/ctxtZEITRAUM-LOW\").text = yesterday_format\n" +
                "session.findById(\"wnd[0]/usr/txtBENUTZER-LOW\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/txtBENUTZER-LOW\").setFocus\n" +
                "session.findById(\"wnd[0]/usr/txtBENUTZER-LOW\").caretPosition = 1\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)\n");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sm66";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sm66\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/cntlGRID1/shellcont/shell\").setCurrentCell -1,\"CPU\"\n" +
                "session.findById(\"wnd[0]/usr/cntlGRID1/shellcont/shell\").selectColumn \"CPU\"\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[40]\").press\n" +
                "\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "smq2";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"smq2\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/txtCLIENT\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/txtQERROR\").text = \"X\"\n" +
                "session.findById(\"wnd[0]/usr/txtQERROR\").setFocus\n" +
                "session.findById(\"wnd[0]/usr/txtQERROR\").caretPosition = 1\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "st22";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"st22\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/ctxtS_DATUM-LOW\").text = yesterday_format\n" +
                "session.findById(\"wnd[0]/usr/txtS_UNAME-LOW\").text = \"*\"\n" +
                "session.findById(\"wnd[0]/usr/txtS_UNAME-LOW\").setFocus\n" +
                "session.findById(\"wnd[0]/usr/txtS_UNAME-LOW\").caretPosition = 1\n" +
                "session.findById(\"wnd[0]/usr/btnSTARTSEL\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "sxmb_moni";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"sxmb_moni\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/usr/cntlSPLITTER_CONTAINER_TR/shellcont/shell\").doubleClickNode \"MONI_SA\"\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode & \"_1\")\n" +
                "CALL writeToWord(tcode & \"_1\")\n" +
                "session.findById(\"wnd[0]/tbar[0]/btn[3]\").press\n" +
                "session.findById(\"wnd[0]/usr/cntlSPLITTER_CONTAINER_TR/shellcont/shell\").selectedNode = \"MONI_LOG\"\n" +
                "session.findById(\"wnd[0]/usr/cntlSPLITTER_CONTAINER_TR/shellcont/shell\").doubleClickNode \"MONI_LOG\"\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP_TABSTRIP/tabp'STRD'/ssub%_SUBSCREEN_TABSTRIP:RSXMB_SELECT_MESSAGES:0500/ctxtEXETIME\").text = \"00:00:00\"\n" +
                "session.findById(\"wnd[0]/usr/cmbSTATTYPE\").setFocus\n" +
                "session.findById(\"wnd[0]/usr/cmbSTATTYPE\").key = \"4\"\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP_TABSTRIP/tabp'STRD'/ssub%_SUBSCREEN_TABSTRIP:RSXMB_SELECT_MESSAGES:0500/ctxtEXEDATE\").text = yesterday_format\n" +
                "session.findById(\"wnd[0]/usr/tabsTABSTRIP_TABSTRIP/tabp'STRD'/ssub%_SUBSCREEN_TABSTRIP:RSXMB_SELECT_MESSAGES:0500/ctxtEXEDATE\").caretPosition = 10\n" +
                "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode & \"_2\")\n" +
                "CALL writeToWord(tcode  & \"_2\")");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "db02";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"db02\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode & \"_1\")\n" +
                "CALL writeToWord(tcode & \"_1\")\n" +
                "session.findById(\"wnd[0]\").resizeWorkingPane 156,33,false\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").expandNode \"       1008\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").topNode = \"       1009-\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").selectItem \"         47\",\"Task\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").ensureVisibleHorizontalItem \"         47\",\"Task\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").doubleClickItem \"         47\",\"Task\"\n" +
                "\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode & \"_2\")\n" +
                "CALL writeToWord(tcode & \"_2\")\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").expandNode \"       1005-\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").topNode = \"       1009-\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").selectItem \"         74\",\"Task\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").ensureVisibleHorizontalItem \"         74\",\"Task\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").doubleClickItem \"         74\",\"Task\"\n" +
                "\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode & \"_3\")\n" +
                "CALL writeToWord(tcode & \"_3\")\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").selectItem \"         71\",\"Task\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").ensureVisibleHorizontalItem \"         71\",\"Task\"\n" +
                "session.findById(\"wnd[0]/shellcont[1]/shell/shellcont[1]/shell\").doubleClickItem \"         71\",\"Task\"\n" +
                "session.findById(\"wnd[0]/usr/btn%_AUTOTEXT002\").press\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode & \"_4\")\n" +
                "CALL writeToWord(tcode & \"_4\")\n" +
                "session.findById(\"wnd[0]/tbar[0]/btn[3]\").press\n" +
                "session.findById(\"wnd[0]/usr/btn%_AUTOTEXT003\").press\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode & \"_5\")\n" +
                "CALL writeToWord(tcode & \"_5\")\n" +
                "session.findById(\"wnd[0]/tbar[0]/btn[3]\").press\n" +
                "''定制''");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "st03n";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"st03n\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]/shellcont/shell/shellcont[1]/shell\").selectedNode = \"B.999\"\n" +
                "session.findById(\"wnd[0]/shellcont/shell/shellcont[1]/shell\").doubleClickNode \"B.999\"\n" +
                "session.findById(\"wnd[1]/usr/btnBUTTON_1\").press\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode)\n" +
                "CALL writeToWord(tcode)");
        sapScriptStatic.setType("1");

        sapScriptStaticMapper.insert(sapScriptStatic);
        t_code = "st06";
        sapScriptStatic.setCode(t_code);
        sapScriptStatic.setSummary(t_code);
        sapScriptStatic.setContent("\n" +
                "tcode = \"st06\"\n" +
                "CALL exec(tcode)\n" +
                "''定制''\n" +
                "session.findById(\"wnd[0]/shellcont/shellcont/shell/shellcont[0]/shellcont/shell/shellcont[0]/shell\").topNode = \"          1\"\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode& \"_1\")\n" +
                "CALL writeToWord(tcode& \"_1\")\n" +
                "session.findById(\"wnd[0]/shellcont/shellcont/shell/shellcont[0]/shellcont/shell/shellcont[1]/shell\").selectedNode = \"          7\"\n" +
                "''定制''\n" +
                "CALL wait()\n" +
                "CALL saveToBmp(tcode& \"_2\")\n" +
                "CALL writeToWord(tcode& \"_2\")\n");
        sapScriptStatic.setType("1");
*/

        return managerService.selectSapScriptStatic();
    }
}
