package com.tmri.rfid.endpoints;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.EriCustomizeContent;
import com.tmri.rfid.bean.EriCustomizeData;
import com.tmri.rfid.bean.EriCustomizeRequest;
import com.tmri.rfid.bean.InstallHistory;
import com.tmri.rfid.common.EriCustomizeRequestStatus;
import com.tmri.rfid.service.CustomizeRequestService;
import com.tmri.rfid.service.EriCustomizeDataService;
import com.tmri.rfid.service.InstallHistoryService;
import com.tmri.rfid.service.OperationLogService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.SysUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-23.
 */
@Controller
@RequestMapping("/be/customize-request.rfid")
public class CustomizeRequestEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(CustomizeRequestEndPoint.class);

    @Resource
    private CustomizeRequestService customizeRequestService;

    @Resource
    private EriCustomizeDataService customizeDataService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private InstallHistoryService installHistoryService;

    @RequestMapping(params = "method=create")
    @ResponseBody
    public String create(@RequestParam(value = "lsh", required = false) String lsh,
                         @RequestParam(value = "hphm", required = false) String hphm,
                         @RequestParam(value = "hpzl", required = false) String hpzl,
                         @RequestParam(value = "fzjg", required = false) String fzjg,
                         @RequestParam(value = "tid", required = true) String tid,
                         @RequestParam(value = "kh", required = false) String kh,
                         @RequestParam(value = "ywlx", required = false) String ywlx) {
        LOG.info(String.format("------> calling /be/customize-request?method=create lsh=%s,hphm=%s,hpzl=%s,fzjg=%s,tid=%s, kh=%s, ywlx = %s",
                lsh, hphm, hpzl, fzjg, tid, kh, ywlx));
        int _ywlx = 1;
        if (!StringUtils.isEmpty(ywlx)) {
            _ywlx = Integer.valueOf(ywlx);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            EriCustomizeRequest request = customizeRequestService.fetchActiveByTid(tid);
            if (request != null) {
                if (((StringUtils.isNotEmpty(lsh) && lsh.equalsIgnoreCase(request.getLsh()))
                        || (StringUtils.isNotEmpty(hphm) && StringUtils.isNotEmpty(hpzl) && StringUtils.isNotEmpty(fzjg) &&
                hphm.equalsIgnoreCase(request.getHphm()) && hpzl.equalsIgnoreCase(request.getHpzl()) && fzjg.equalsIgnoreCase(request.getFzjg()))
                        || System.currentTimeMillis() - request.getCjsj().getTime() <= 300000 )
                        && System.currentTimeMillis() - request.getCjsj().getTime() <= 43200000) {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "您已经发送过一次请求，若要再次请求，请先取消之前的请求");
                    resultMap.put("xh", request.getXh());
                } else {
                    customizeRequestService.updateCustomizeResult(request.getXh(), null, 3, "系统自动取消之前的请求");
                    request = customizeRequestService.create(lsh, hphm, hpzl, fzjg, tid, kh, _ywlx);
                    if (request != null) {
                        resultMap.put("resultId", "00");
                        resultMap.put("resultMsg", "创建成功,请耐心等待，信息更新后进行数据写入");
                        resultMap.put("xh", request.getXh());
                    } else {
                        resultMap.put("resultId", "01");
                        resultMap.put("resultMsg", "创建失败");
                    }
                }
            } else {
                request = customizeRequestService.create(lsh, hphm, hpzl, fzjg, tid, kh, _ywlx);
                if (request != null) {
                    resultMap.put("resultId", "00");
                    resultMap.put("resultMsg", "创建成功,请耐心等待，信息更新后进行数据写入");
                    resultMap.put("xh", request.getXh());
                } else {
                    resultMap.put("resultId", "01");
                    resultMap.put("resultMsg", "创建失败");
                }
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/customize-request?method=create fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info(String.format("------> calling /be/customize-request?method=create result=%s", result));
        return result;
    }

    @RequestMapping(params = "method=customize")
    @ResponseBody
    public String customize(@RequestParam(value = "tid", required = true) String tid,
                            @RequestParam(value = "aqmkxh", required = true) String aqmkxh) {
        LOG.info(String.format("------> calling /be/customize-request.rfid?method=customize, tid = %s, aqmkxh = %s", tid, aqmkxh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long qqxh = null;
        try {
            SysUser sysUser = UserState.getUser();
            List<EriCustomizeRequest> customizeRequests = customizeRequestService
                    .fetchByCondition(MapUtilities.buildMap("glbm", sysUser.getGlbm(),
                            "tid", tid, "zts", new int[] {
                                    EriCustomizeRequestStatus.NEW.getStatus(),
                                    EriCustomizeRequestStatus.FETCHED.getStatus(),
                                    EriCustomizeRequestStatus.FETCHING.getStatus()}));
            if (customizeRequests == null || customizeRequests.isEmpty()) {
                EriCustomizeRequest lastRequest = customizeRequestService.fetchLast(tid);
                if (lastRequest.getZt() == EriCustomizeRequestStatus.FAIL.getStatus()) {
                    resultMap.put("resultMsg", "之前的请求处理失败，原因：" + lastRequest.getSbyy());
                } else {
                    resultMap.put("resultMsg", "请求不存在，请先发起请求");
                }
                resultMap.put("resultId", "01");
            } else {
                EriCustomizeData customizeData = customizeDataService.fetchByTidAndAqmkxh(tid, aqmkxh);
                if (customizeData != null) {
                    EriCustomizeContent customizeContent =
                            GsonHelper.getGson().fromJson(customizeData.getData(), EriCustomizeContent.class);
                    resultMap.put("resultId", "00");
                    resultMap.put("info", customizeContent);
                    resultMap.put("xh", customizeData.getQqxh());
                    qqxh = customizeData.getQqxh();
                } else {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "未获取到数据");
                }
            }
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        operationLogService.log("FETCH_CUSTOMIZE_DATA", qqxh, result);
        LOG.info("------> calling /be/customize-request.rfid?method=customize result = " + result);
        return result;
    }

    /**
     *
     * @param qqxh
     * @param aqmkxh
     * @param result <= 0 ：写入失败；1 ：成功；>1：取消
     * @param sbyy
     * @return
     */
    @RequestMapping(params = "method=customize-result")
    @ResponseBody
    public String customizeResult(@RequestParam(value = "qqxh", required = true) long qqxh,
                                  @RequestParam(value = "aqmkxh", required = true) String aqmkxh,
                                  @RequestParam(value = "result", required = true) int result,
                                  @RequestParam(value = "sbyy", required = false) String sbyy) {
        LOG.info(String.format("------> calling /customize-request.rfid?method=customize-result, qqxh = %s, aqmkxh = %s, result=%s, sbyy = %s",
                qqxh, aqmkxh, result, sbyy));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (customizeRequestService.checkRequestStatus(qqxh)) {
                customizeRequestService.updateCustomizeResult(qqxh, aqmkxh, result, sbyy);
                resultMap.put("resultId", "00");
                resultMap.put("resultMsg", "更新成功");
            } else {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "请求状态不正确");
            }
        } catch (Exception e) {
            LOG.error("save customize result fail ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultStr = GsonHelper.getGson().toJson(resultMap);
        operationLogService.log("UPDATE_CUSTOMIZE_RESULT", qqxh, resultStr);
        LOG.info("------> calling /customize-request.rfid?method=customize-result result = " + resultStr);
        return resultStr;
    }

    @RequestMapping(params = "method=check-status")
    @ResponseBody
    public String checkStatus(@RequestParam(value = "qqxh", required = true) long qqxh) {
        LOG.info(String.format("------> calling /customize-request.rfid?method=check-status, qqxh = %s", qqxh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            EriCustomizeRequest request = customizeRequestService.fetchByXh(qqxh);
            resultMap.put("resultId", "00");
            resultMap.put("zt", request.getZt());
        } catch (Exception e) {
            LOG.error("save customize result fail ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultStr = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /customize-request.rfid?method=check-status result = " + resultStr);
        return resultStr;
    }

    @RequestMapping(params = "method=upload-install-info", method = RequestMethod.POST)
    @ResponseBody
    public String uploadInstallInfo(@RequestParam(value = "tid") String tid,
                                    @RequestParam(value = "kh") String kh,
                                    @RequestParam(value = "hphm") String hphm,
                                    @RequestParam(value = "hpzl") String hpzl) {
        LOG.info(String.format("------> calling /customize-request.rfid?method=upload-install-info tid=%s,kh=%s,hphm=%s,hpzl=%s",
                tid, kh, hphm, hpzl));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (installHistoryService.create(tid, kh, hphm, hpzl, UserState.getUser().getYhdh())) {
                resultMap.put("resultId", "00");
            } else {
                resultMap.put("resultId", "01");
            }
        } catch (Exception e) {
            LOG.error("save customize result fail ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultStr = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /customize-request.rfid?method=upload-install-info result = " + resultStr);
        return resultStr;
    }

    @RequestMapping(params = "method=list")
    @ResponseBody
    public String list(@RequestParam(value = "tid", required = false) String tid,
                       @RequestParam(value = "kh", required = false) String kh,
                       @RequestParam(value = "cjr", required = false) String cjr,
                       @RequestParam(value = "hpzl", required = false) String hpzl,
                       @RequestParam(value = "hphm", required = false) String hphm,
                       @RequestParam(value = "fzjg", required = false) String fzjg,
                       @RequestParam(value = "lsh", required = false) String lsh,
                       @RequestParam(value = "zt", required = false) String zt,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        LOG.info(String.format("------> calling /be/customize-request.rfid?method=list, tid = %s, kh = %s, cjr = %s, hpzl = %s, hphm = %s, fzjg = %s, lsh = %s, zt = %s",
                tid, kh, cjr, hpzl, hphm, fzjg, lsh, zt));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(tid)) {
                condition.put("tid", tid);
            }
            if (StringUtils.isNotEmpty(kh)) {
                condition.put("kh", kh);
            }
            if (StringUtils.isNotEmpty(cjr)) {
                condition.put("cjr", cjr);
            }
            if (StringUtils.isNotEmpty(hpzl)) {
                condition.put("hpzl", hpzl);
            }
            if (StringUtils.isNotEmpty(hphm)) {
                condition.put("hphm", hphm);
            }
            if (StringUtils.isNotEmpty(lsh)) {
                condition.put("lsh", lsh);
            }
            if (StringUtils.isNotEmpty(zt)) {
                condition.put("zt", Integer.valueOf(zt));
            } else {
                condition.put("zts", new int[] {0, 1, 2});
            }
            condition.put("glbm", UserState.getUser().getGlbm());
            List<EriCustomizeRequest> customizeRequests = customizeRequestService.fetchByCondition(condition, page, pageSize);
            PageList<EriCustomizeRequest> pageList = (PageList<EriCustomizeRequest>) customizeRequests;
            Paginator paginator = pageList.getPaginator();
            resultMap.put("resultId", "00");
            resultMap.put("list", customizeRequests);
            resultMap.put("page", paginator.getPage());
            resultMap.put("totalPages", paginator.getTotalPages());
            resultMap.put("totalCount", paginator.getTotalCount());
        } catch (Exception e) {
            LOG.error("list all request fail ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String resultStr = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/customize-request.rfid?method=list result = " + resultStr);
        return resultStr;
    }

}
