package com.tmri.framework.ctrl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.tmri.framework.bean.FrmGispara;
import com.tmri.framework.service.FrmGisparaService;
import com.tmri.share.frm.service.LogService;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.MenuConstant;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

/**
 * GIS参数配置
 * @author shiyl 2014-10-24
 */
@Controller
@RequestMapping("/gispara.frm")
public class FrmGisparaCtrl extends FrmCtrl {
    private final String XTLB = "66";

    private final String CDBH = "M101";

    @Autowired
    private FrmGisparaService gisparaService;

    @Autowired
    private LogService logService;

    @RequestMapping(params = "method=queryGisparaList")
    public ModelAndView queryGisparaList(HttpServletRequest request, HttpServletResponse response) {
        try {
            gSystemService.doBeginCall(request, getClass().getName(), "");
            List<FrmGispara> queryList = null;
            // 只展示可见参数
            FrmGispara cmd = new FrmGispara();
            cmd.setSfxs("1");
            queryList = gisparaService.getGisParaList(cmd,
                    logService.getRmLog(request,
                    getClass().getName(), "", null, null));
            FrmGispara para = gisparaService.getGisParaByPk("GISLX");
            request.setAttribute("queryList", queryList);
            request.setAttribute("gislx", para.getMrz());

            // 程序名称
            request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH));
            request.setAttribute("xtyxms", gSysparaCodeService.getParaValue("00", "XTYXMS"));
            gSystemService.doEndCall(request, getClass().getName(), "", MenuConstant.P_open);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("jsp/gisparaList");
    }

    /**
     * 编辑GIS参数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=editGispara")
    public ModelAndView editGispara(HttpServletRequest request, HttpServletResponse response) {
        try {
            gSystemService.doBeginCall(request, getClass().getName(), "");

            // 程序名称
            request.setAttribute("cxmc", gSystemService.getProgramName(XTLB, CDBH));

            gSystemService.doEndCall(request, getClass().getName(), "", MenuConstant.P_open);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("jsp/gisparaEdit");
    }

    @RequestMapping(params = "method=saveGispara")
    public ModelAndView saveGispara(HttpServletRequest request, HttpServletResponse response,
            FrmGispara bean) {
        ModelAndView view = null;
        response.setContentType("text/html;charset=GB2312");
        try {
            gSystemService.doBeginCall(request, getClass().getName(), "");
            String glbm = request.getParameter("glbm");
            String gjz = request.getParameter("gjz");
            String mrz = request.getParameter("mrz");
            String logs = "";
            if (StringUtil.checkBN(mrz)) {
                logs = "修改" + glbm;
                logs += gjz + "的参数值:" + mrz;
            } else {
                logs = "新增" + glbm;
                logs += gjz + "的参数值:" + mrz;
            }

            HashMap<String, String> keyMap = new HashMap<String, String>();
            keyMap.put("glbm", glbm);
            DbResult result = this.gisparaService.saveBean(bean, this.logService.getRmLog(request,
                    getClass().getName(), "", logs, keyMap));
            view = CommonResponse.JSON(result);
            gSystemService.doEndCall(request, getClass().getName(), "", MenuConstant.P_save);
        } catch (Exception e) {
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
            view = CommonResponse.JSON(e);
        }
        return view;
    }

    @RequestMapping(params = "method=testActiveWebIp")
    public ModelAndView testActiveWebIp(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = null;
        response.setContentType("text/html;charset=GB2312");
        DbResult result = new DbResult();
        // String responsIp="";
        // responsIp="http://"+webip+"/"+path+"/loading.html";
        // 全国升级后下一步调整为该地址，可测试数据库
        // responsIp="http://"+webip+"/"+path+"/testchannel.jsp";

        String responsIp = request.getParameter("fwdz");
        try {
            if (StringUtil.checkBN(responsIp)) {
                URL url = new URL(responsIp.trim());
                HttpURLConnection con = null;
                OutputStream outs = null;
                InputStream in = null;
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(6000);
                con.setReadTimeout(50000);
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setUseCaches(false);
                outs = con.getOutputStream();
                in = con.getInputStream();
                result.setCode(11);
                result.setMsg("连接正常!");
            } else {
                result.setCode(-1);
                result.setMsg("远程访问地址为空!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            result.setCode(-1);
            result.setMsg("设定的接受端应用服务地址:" + responsIp.trim() + ":" + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(-1);
            result.setMsg("访问时发生错误:" + e.getMessage());
        } finally {
            view = CommonResponse.JSON(result);
        }
        return view;
    }

}
