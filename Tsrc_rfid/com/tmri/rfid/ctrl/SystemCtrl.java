package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.cache.service.WebCacheService;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.service.SysParaManager;
import com.tmri.framework.web.support.UserState;
import com.tmri.pub.service.SysService;
import com.tmri.rfid.bean.DBOperation;
import com.tmri.rfid.bean.OperationLog;
import com.tmri.rfid.bean.ProductApply;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.service.DBOperationService;
import com.tmri.rfid.service.OperationLogService;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.LogFileTailer;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.cache.bean.RmWebCache;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/11/10.
 */
@Controller
@RequestMapping("system.frm")
public class SystemCtrl extends BaseCtrl{

    private final static Logger LOG = LoggerFactory.getLogger(SystemCtrl.class);

    @Resource
    private DBOperationService dbOperationService;

    @Resource
    private SysService sysService;

    @Resource
    private SysParaManager sysParaManager;

    @Resource
    private GSysparaCodeService gSysparaCodeService;

    @Resource
    private OperationLogService operationLogService;

    @Autowired
    private WebCacheService rmWebCacheService;

    @Resource
    private RuntimeProperty runtimeProperty;

    private Map<String, LogFileTailer> tailerList = new HashMap<String, LogFileTailer>();

    public ModelAndView redirectMav(String mav) {
        return redirectMav("jsp_core/rfid/system", mav);
    }

    @RequestMapping(params = "method=database-manager")
    public ModelAndView databaseManager(Model model) {
        LOG.info("------> display /system/database-manager");
        List<DBOperation> executable = dbOperationService.fetchAll();
        model.addAttribute("operationList", executable);
        return redirectMav("databaseManager");
    }

    @RequestMapping(params = "method=upload-scripts", method = RequestMethod.POST)
    public ModelAndView uploadScripts(HttpServletRequest request) {
        LOG.info("------> post /system.frm?method=upload-scripts");
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 得到上传的文件
            MultipartFile mFile = multipartRequest.getFile("file_upload");
            // 得到上传的文件的文件名
            String filename = mFile.getOriginalFilename();
            if (!filename.toLowerCase().endsWith(".sql")) {
                CommonResponse.setAlerts("只支持SQL文件", request);
                return redirectMav("databaseManager");
            }

            File f = new File(runtimeProperty.getDbScriptFolder() + filename);
            if (f.exists()) return redirectMav("operationLog");
            if(mFile.getSize() == 0){
                CommonResponse.setAlerts("您上传的文件为空", request);
            }
            mFile.transferTo(f);

            CommonResponse.setAlerts("完成上传", request);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return new ModelAndView(new RedirectView("system.frm?method=database-manager"));
    }

    @RequestMapping(params = "method=database-manager", method = RequestMethod.POST)
    @ResponseBody
    public String executeScripts(Model model) {
        LOG.info("------> post /system/database-manager");
        try {
            dbOperationService.executeScripts();
            rmWebCacheService.initSysParas();
            rmWebCacheService.initSysParaValue();
            rmWebCacheService.initFrmCode();
            rmWebCacheService.initProgramRelation();
            rmWebCacheService.initFuntions();
            rmWebCacheService.initDepartments();
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "00"));
        } catch (Exception e) {
            LOG.error("execute scripts fail", e);
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "99", "resultMsg", e.getMessage()));
        }
    }

    @RequestMapping(params = "method=show-operation-log")
    public ModelAndView showOperationLogs(@RequestParam(value = "czmc", required=false) String czmc,
                                          @RequestParam(value = "gjz", required = false) String gjz,
                                          @RequestParam(value = "xxnr", required = false) String xxnr,
                                          @RequestParam(value = "jg", required = false) String jg,
                                          @RequestParam(value = "czr", required = false) String czr,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                          Model model, HttpServletRequest request) {
        try {
            Map<Object, Object> condition = new HashMap<Object, Object>();
            if (StringUtils.isNotEmpty(czmc)) {
                condition.put("czmc", czmc);
            }
            if (StringUtils.isNotEmpty(gjz)) {
                condition.put("gjz", gjz);
            }
            if (StringUtils.isNotEmpty(xxnr)) {
                condition.put("xxnr", xxnr);
            }
            if (StringUtils.isNotEmpty(jg)) {
                condition.put("jg", jg);
            }
            if (StringUtils.isNotEmpty(czr)) {
                condition.put("czr", czr);
            }
            List<OperationLog> logs = operationLogService.list(condition, page, pageSize);
            PageList<OperationLog> pageList = (PageList<OperationLog>) logs;
            Paginator paginator = pageList.getPaginator();
            model.addAttribute("controller", getPageInfo(paginator, request));
            model.addAttribute("queryList", pageList);
        } catch (Exception e) {
            LOG.error("------> call system.frm?method=show-operation-log fail", e);
        }
        return redirectMav("operationLog");
    }

    @RequestMapping(params = "method=show-log")
    public ModelAndView showLog(Model model) {
        model.addAttribute("status", 0);
        return redirectMav("log");
    }

    @RequestMapping(params = "method=start-tail")
    public ModelAndView startTail(@RequestParam(value = "file", required = true) String file,
                                  @RequestParam(value = "lines", defaultValue = "0") int lines,
                                  @RequestParam(value = "reverse", required = true)  int reverse,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  HttpServletRequest request, Model model) {
        try {
            HttpSession session = request.getSession();
            Object tailerObj = request.getSession().getAttribute("tailer");
            LogFileTailer tailer;
            if (tailerObj == null) {
                tailer = LogFileTailer.getInstance(new File(file), 1000, reverse == 0, keyword, lines, UserState.getUser().getYhdh());
                if (tailer == null) {
                    model.addAttribute("resultId", "01");
                    model.addAttribute("resultMsg", "当前有用户（" + tailer.getOwner() + "）正在操作，请等待");
                } else {
                    tailer.start();
                }
            } else {
                tailer = (LogFileTailer) tailerObj;
            }
            if (tailer != null) {
                model.addAttribute("resultId", "00");
                session.setAttribute("tailer", tailer);
                model.addAttribute("status", 1);
            }
            model.addAttribute("file", file);
            model.addAttribute("lines", lines);
            model.addAttribute("reverse", reverse);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            LOG.error("start tail fail", e);
        }
        return redirectMav("log");
    }

    @RequestMapping(params = "method=stop-tail")
    public ModelAndView startTail(HttpServletRequest request, Model model) {
        try {
            HttpSession session = request.getSession();
            Object tailerObj = request.getSession().getAttribute("tailer");
            LogFileTailer tailer;
            if (tailerObj != null) {
                tailer = (LogFileTailer) tailerObj;
                tailer.stopTailing();
            }
            session.removeAttribute("tailer");
            model.addAttribute("status", 0);
        } catch (Exception e) {
            LOG.error("stop tail fail", e);
        }
        return redirectMav("log");
    }

    @RequestMapping(params = "method=tail-log")
    @ResponseBody
    public String tailLog(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            LogFileTailer tailer = (LogFileTailer) request.getSession().getAttribute("tailer");
            List<String> lines = tailer.fetchLog();
            resultMap.put("resultId", "00");
            resultMap.put("lines", lines);
        } catch (Exception e) {
            LOG.error("start tail fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        return GsonHelper.getGson().toJson(resultMap);
    }

    @RequestMapping(params = "method=upload-cert")
    public ModelAndView uploadCert(Model model) {
        LOG.info("------> display /system.frm?method=upload-cert");
        SysPara sysPara = gSysparaCodeService.getSyspara("00", "2", "QGGZS");
        Map<String, String> map = new HashMap<String, String>();
        if (sysPara != null) {
            model.addAttribute("cert1", sysPara.getMrz());
            map.put("QGGZS", sysPara.getCsmc());
        }
        sysPara = gSysparaCodeService.getSyspara("00", "2", "DSJZS");
        if (sysPara != null) {
            model.addAttribute("cert2", sysPara.getMrz());
            map.put("DSJZS", sysPara.getCsmc());
        }
        model.addAttribute("typeMap", map);
        return redirectMav("uploadCert");
    }

    @RequestMapping(params = "method=upload-cert", method = RequestMethod.POST)
    public ModelAndView upload(Model model, HttpServletRequest request, HttpServletResponse response) {
        LOG.info("------> submit /system.frm?method=upload-cert");

        response.setContentType("text/html; charset=GBK");
        HttpSession session=request.getSession();
        UserSession userSession=gSystemService.getSessionUserInfo(session);
        try{

            SysUser sysUser=userSession.getSysuser();

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                // 得到上传的文件
            MultipartFile mFile = multipartRequest.getFile("file_upload");
            String type = multipartRequest.getParameter("type");
            SysPara sysPara = gSysparaCodeService.getSyspara("00", "2", type);
            byte[] certContent = mFile.getBytes();
            String certStr = EriUtil.bytesToHex(certContent);
            sysPara.setMrz(certStr);

            String sysdatetime=gSysDateService.getSysDate(0, 3);
            String cznr="修改系统参数,关键值:"+sysPara.getGjz()+"参数值:"+sysPara.getMrz();
            Log log=new Log(sysUser.getGlbm(),sysUser.getYhdh(),sysdatetime,Constants.MENU_XTCS,"",cznr,FuncUtil.getRemoteIpdz(request),"");
            SysResult result=this.sysParaManager.saveSyspara(sysPara,log);
            this.rmWebCacheService.initSysParas();
            this.rmWebCacheService.initSysParaValue();

            if(result.getFlag()==1){
                model.addAttribute("resultId", "00");
                sysPara = gSysparaCodeService.getSyspara("00", "2", "QGGZS");
                Map<String, String> map = new HashMap<String, String>();
                if (sysPara != null) {
                    model.addAttribute("cert1", sysPara.getMrz());
                    map.put("QGGZS", sysPara.getCsmc());
                }
                sysPara = gSysparaCodeService.getSyspara("00", "2", "DSJZS");
                if (sysPara != null) {
                    model.addAttribute("cert2", sysPara.getMrz());
                    map.put("DSJZS", sysPara.getCsmc());
                }
                model.addAttribute("typeMap", map);
            }else{
                model.addAttribute("resultId", "01");
                model.addAttribute("resultMsg", "上传证书失败");
            }

        }catch(Exception e){
            LOG.error("上传证书失败", e);
            model.addAttribute("resultId", "99");
            model.addAttribute("resultMsg", "上传证书失败");
        }
        return redirectMav("uploadCert");
    }

    @RequestMapping(params = "method=test")
    @ResponseBody
    public String test(Model model) {
        LOG.info("------> post /system/test");
        try {
            LOG.info("user.home=" + System.getProperty("user.home"));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "00"));
        } catch (Exception e) {
            LOG.error("execute scripts fail", e);
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "99", "resultMsg", e.getMessage()));
        }
    }

    @RequestMapping(params = "method=show-plugin")
    public ModelAndView showPlugin(Model model) {
        model.addAttribute("eriVersion", gSysparaCodeService.getParaValue("00", "QZCXBB"));
        return redirectMav("plugin");

    }

    @RequestMapping(params = "method=download")
    public void download(@RequestParam(value = "fileName", required = true) String fileName,
                         HttpServletResponse response) {
        try {
            File file = new File(runtimeProperty.getFileFolder() + "/plugins/" + fileName);
            if (!file.exists()) {
                response.sendError(404, "File not found!");
                return;
            }
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            ServletOutputStream out;

            out = response.getOutputStream();

            while ((len = br.read(buf)) > 0)
                out.write(buf, 0, len);
            br.close();
            out.close();
            out.flush();

        } catch (Exception e) {
            LOG.error("下载失败", e);
        }
    }

    @RequestMapping(params = "method=download-plugin")
    public void downloadPlugin(@RequestParam(value = "id", required = true) String id,
                               @RequestParam(value = "version", required = true) String version,
                               HttpServletResponse response) {

        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName="+ id + "_" + version + ".exe");
        ServletOutputStream out;
        //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
        File file = new File(runtimeProperty.getFileFolder() + "/plugins/" + id + "_" + version + ".exe");

        try {
            FileInputStream inputStream = new FileInputStream(file);

            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1){
                b = inputStream.read(buffer);
                //4.写到输出流(out)中
                out.write(buffer,0,b);
            }
            inputStream.close();
            out.close();
            out.flush();

        } catch (IOException e) {
            LOG.error("下载插件失败", e);
        }

    }

    @RequestMapping(params="method=upgrade", method=RequestMethod.POST)
    public ModelAndView upgrade(HttpServletRequest request) {
        LOG.info("------> post /system.frm?method=upgrade");
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 得到上传的文件
            MultipartFile mFile = multipartRequest.getFile("file_upload");
            // 得到上传的文件的文件名
            String filename = mFile.getOriginalFilename();
            if (!filename.toLowerCase().endsWith(".war")) {
                CommonResponse.setAlerts("只支持war文件", request);
                return redirectMav("upgrade");
            }

            File f = new File(runtimeProperty.getFileFolder() + "/tmp/" + filename);
            if (f.exists()) return redirectMav("operationLog");
            if(mFile.getSize() == 0){
                CommonResponse.setAlerts("您上传的文件为空", request);
            }
            mFile.transferTo(f);

            CommonResponse.setAlerts("完成上传", request);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("upgrade");
    }
}
