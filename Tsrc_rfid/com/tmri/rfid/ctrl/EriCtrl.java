package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.common.CodeTableDefinition;
import com.tmri.rfid.common.EriCustomizeStatus;
import com.tmri.rfid.common.EriStatus;
import com.tmri.rfid.service.EriOperationService;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.impl.EriPastedServiceImpl;
import com.tmri.rfid.util.ExcelOperator;
import com.tmri.rfid.util.FileUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.rfid.util.ReadExcel;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by Joey on 2015/9/1.
 */
@Controller
@RequestMapping("/eri.frm")
public class EriCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(EriCtrl.class);

    @Resource
    private EriService eriService;
    @Resource
    private EriOperationService eriOperationService;
    @Resource
    private InventoryService inventoryService;

    @Resource
    private EriPastedServiceImpl eriPastedServiceImpl;

    @RequestMapping(params = "method=init-card")
    @ResponseBody
    public String initCard(@RequestParam(value = "tid", required = true) String tid,
                           @RequestParam(value = "province", required = false) String province,
                           @RequestParam(value = "key", required = true) String key) {
        LOG.info(String.format("------> calling /eri.frm?method=init-card, tid = %s, province = %s", tid, province));
        try {
            EriInitializeContent eriInitializeContent = eriService.initialize(tid, StringUtils.isEmpty(province) ? "32" : province, key);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return gson.toJson(eriInitializeContent).replaceAll("\"", "'");
        } catch (Exception e) {
            LOG.error("��ʼ��ʧ��", e);
            return "Exception: " + e.getMessage();
        }
    }

    /**
     * ��ʼ�����ӱ�ʶ�ӿ�
     * @param tid ���ӱ�ʶTID
     * @param kh ���ӱ�ʶ���ţ�ͨ����������Ԥ����
     * @param sbxh �豸���
     * @param gwh ��λ��
     * @param czr ������
     * @param rwh ���������
     * @return
     */
    @RequestMapping(params = "method=factory-init-card", method= RequestMethod.POST)
    @ResponseBody
    public String factoryInitCard(@RequestParam(value = "tid", required = true) String tid,
                                  @RequestParam(value = "kh", required = true) String kh,
                                  @RequestParam(value = "bzhh", required = true) String bzhh,
                                  @RequestParam(value = "sbxh", required = true) String sbxh,
                                  @RequestParam(value = "gwh", required = false) String gwh,
                                  @RequestParam(value = "czr", required = true) String czr,
                                  @RequestParam(value = "rwh", required = true) String rwh,
                                  @RequestParam(value = "cert", required = true) String cert) {
        LOG.info(String.format("------> calling /eri.frm?method=factory-init-card, tid=%s, kh=%s, bzhh=%s, sbxh=%s, gwh=%s, czr=%s, rwh=%s",
                tid, kh, bzhh, sbxh, gwh, czr, rwh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            EriInitializeContent eriInitializeContent =
                    eriService.factoryInitialize(tid, kh, bzhh, rwh, sbxh, gwh, czr, cert);
            resultMap.put("resultId", "00");
            resultMap.put("content", eriInitializeContent);
        } catch (Exception e) {
            LOG.error("------> calling /eri.frm?method=factory-init-card fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(resultMap).replaceAll("\"", "'");
    }

    @RequestMapping(params = "method=check-status")
    @ResponseBody
    public String checkStatus(@RequestParam(value = "tid", required = true) String tid) {
        LOG.info(String.format("------> calling /eri.frm?method=check-status, tid = %s", tid));
        try {
            Eri eri = eriService.fetchByTid(tid);
            for (EriStatus status : EriStatus.values()) {
                if (eri.getZt() == status.getStatus()) {
                    return status.name();
                }
            }
            return "Exception:δ֪״̬��" + eri.getZt() + "��" ;
        } catch (Exception e) {
            LOG.error("check card status fail", e);
            return "Exception:" + e.getMessage();
        }
    }


    @RequestMapping(params = "method=init-card-result")
    @ResponseBody
    public String initCardResult(@RequestParam(value = "tid", required = true) String tid,
                                 @RequestParam(value = "result", required = true) int result,
                                 @RequestParam(value = "sbyy", required = false) String sbyy) {
        LOG.info(String.format("------> calling /eri.frm?method=init-card-result, tid = %s, result = %s, sbyy = %s", tid, result, sbyy));
        try {
            eriService.saveInitializeResult(tid, result, sbyy);
        } catch (Exception e) {
            LOG.error("update init card result fail", e);
            return "Exception:" + e.getMessage();
        }
        LOG.info("------> calling /eri.frm?method=init-card-result result = 1");
        return "1";
    }
    
    protected final String jspPath = "jsp_core/rfid/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
    
    @RequestMapping(params = "method=query-eri")
    public ModelAndView queryEri(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "tid", required = false) String tid,
			@RequestParam(value = "kh", required = false) String kh,
			@RequestParam(value = "ph", required = false) String ph,
			@RequestParam(value = "sf", required = false) String sf,
			@RequestParam(value = "zt", required = false) Integer zt,
			@RequestParam(value = "cshrqks", required = false) String cshrqks,
			@RequestParam(value = "cshrqjs", required = false) String cshrqjs,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize){

		try {
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();
			//String selfbmjb = dept.getBmjb();
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}
	    	PageController controller = new PageController(request);
			controller.setPageSize(20);

            Map<String, Object> condition = new HashMap<String, Object>();
			if(StringUtil.checkBN(tid)){
                condition.put("tid", tid);
			}
			if(StringUtil.checkBN(kh)){
                condition.put("kh", kh);
			}
			if(StringUtil.checkBN(ph)){
                condition.put("ph", ph);
			}
			if(StringUtil.checkBN(sf)){
                condition.put("sf", sf);
			}
			if(zt != null){
                condition.put("zt", zt);
			}
			if(StringUtil.checkBN(cshrqks)){
                condition.put("cshrqks", cshrqks);
			}
			if(StringUtil.checkBN(cshrqjs)){
                condition.put("cshrqjs", cshrqjs);
			}

            List<Eri> queryList = eriService.fetchByCondition(condition, new PageBounds(page, pageSize));
            PageList<Eri> pageList = (PageList<Eri>)queryList;
	    	
			request.setAttribute("queryList", queryList);
			//request.setAttribute("controller", controller);
			request.setAttribute("controller", getPageInfo(((PageList<Eri>) queryList).getPaginator(), request));
			request.setAttribute("provinces", gSysparaCodeService.getCodes("00", "0032"));
            request.setAttribute("eriStatus", EriStatus.values());
			request.setAttribute("command", condition);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		return redirectMav("query/eriMain");
    }

    @RequestMapping(params = "method=query-eri-detail")
    public ModelAndView queryEriDetail(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "tid", required = false) String tid){
		try {
			if (tid != null && tid.length() > 0) {
				Eri bean = this.eriService.fetchByTid(tid);
				List<EriOperation> eriCustomizes = this.eriOperationService.getCustomizeRecordByTid(tid);
				
				request.setAttribute("bean", bean);
				if(eriCustomizes.size() > 0){
					request.setAttribute("eriCustomizeStatus", EriCustomizeStatus.values());
					request.setAttribute("licenceTypes", gSysparaCodeService.getCodes(CodeTableDefinition.VEHICLE_LICENCE_TYPE));
				}
				request.setAttribute("provinces", gSysparaCodeService.getCodes("00", "0032"));
				request.setAttribute("eriStatus", EriStatus.values());

				EriOperation initInfo = new EriOperation();//��ʼ����Ϣ
				initInfo.setRq(bean.getCshrq());
				initInfo.setType(2);
				List<EriOperation> warehouses = eriOperationService.queryRkByTid(tid);//�����������Ϣ
				List<EriOperation> exWarehouses = eriOperationService.queryCkByTid(tid);//������������Ϣ
				List<EriOperation> corpsWarehouses = eriOperationService.queryZdrkByTid(tid);//�ܶ������Ϣ
				List<EriOperation> productApplys = eriOperationService.queryLyByTid(tid);//֧��������Ϣ
				List<EriOperation> scrapDetails = eriOperationService.queryBfByTid(tid);//������Ϣ
				
				List<EriOperation> list = new ArrayList<EriOperation>();
				list.add(initInfo);
				list.addAll(warehouses);
				list.addAll(exWarehouses);
				list.addAll(corpsWarehouses);
				list.addAll(eriCustomizes);
				list.addAll(productApplys);
				list.addAll(scrapDetails);
				
				Collections.sort(list, new Comparator<EriOperation>() {
		            public int compare(EriOperation arg0, EriOperation arg1) {
		                return arg0.getRq().compareTo(arg1.getRq());
		            }
		        });
				request.setAttribute("eriOperations", list);
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
			CommonResponse.setErrors(e, request);
		}
    	
    	return redirectMav("query/eriDetail");
    }
    
    @RequestMapping(params = "method=fetch-eri-by-bzhh")
    @ResponseBody
    public String fetchEriByBzhh(@RequestParam(value = "bzhh", required = true) String bzhh,
                                 HttpServletRequest request) {
        LOG.info(String.format("------> call /eri.frm?method=fetch-eri-by-bzhh(bzhh=%s", bzhh));
        try {
            Inventory inventory = inventoryService.fetchByBzhh(bzhh);
            List<Eri> eriList = eriService.fetchByRegion(inventory.getQskh(), inventory.getZzkh());
            String result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "00", "eriList", eriList));
            LOG.info("------> call inventory.frm?method=query-by-bzhh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("���ݰ�װ�кŲ�ѯ�������ӱ�ʶ�쳣", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }

    @RequestMapping(params = "method=generate-url-by-kh")
    @ResponseBody
    public String generateUrlByKh(@RequestParam(value = "kh", required = true) String kh) {
        LOG.info(String.format("------> call /eri.frm?method=generate-url-by-kh kh = %s", kh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String qrcode = eriService.generateUrlByKh(kh);
            resultMap.put("resultId", "00");
            resultMap.put("url", qrcode);
        } catch (Exception e) {
            LOG.error("���ɶ�ά��ʧ��", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", "���ɶ�ά��ʧ��");
            resultMap.put("exception", e.getMessage());
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String result =gson.toJson(resultMap);
        LOG.info("------> call /eri.frm?method=generate-url-by-kh result = " + result);
        return result;
    }
    
    /**
     * �ϴ���ճ���ı�ǩ�б����
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=upload-pastedEri")
    public ModelAndView uploadPastedEri(HttpServletRequest request,HttpServletResponse response){
        LOG.info(String.format("------> call /eri.frm?method=upload-pastedEri"));
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
			CommonResponse.setErrors(e, request);
		}
    	
    	return redirectMav("eri/uploadPastedEri");
    }
    
    /**
     * �ϴ���ճ���ı�ǩ�б�
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=upload", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam(value = "lyrq", required = true) String lyrq,
    		@RequestParam(value = "uploadType", required = true) String uploadType,
    		HttpServletRequest request,HttpServletResponse response) {
        LOG.info("------> display /eri.frm?method=upload");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // �õ��ϴ����ļ�
            MultipartFile mFile = multipartRequest.getFile("file_upload");
            
            // �õ��ϴ���������·��
            String myPath = request.getSession().getServletContext().getRealPath("/");
            // �õ��ϴ����ļ����ļ���
            String fileName = mFile.getOriginalFilename();
            
            String folderPath = myPath+"upload/";
            FileUtil.createFolder(folderPath);
            String filePathAndName = folderPath+fileName;
            File f = new File(filePathAndName);
    		mFile.transferTo(f);
    		
    		if(mFile.getSize() == 0){
    			request.setAttribute("errMsg", "�ļ�Ϊ��");
    			return redirectMav("eri/uploadPastedEri");
    		}else{
    			if(uploadType.equals("1")){
        			eriPastedServiceImpl.updatePastedEris(f,lyrq);
    			}else{
    				eriPastedServiceImpl.updatePastedErisDirect(f,lyrq);
    			}
    			CommonResponse.setAlerts("������ǩ�ϴ��ɹ�", request);
        		FileUtil.delFile(filePathAndName);
    		}
        }catch (Exception e) {
            LOG.error("upload pasted eri fail:", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        
        return redirectMav("eri/uploadPastedEri");
    }

	
	@RequestMapping(params = "method=export-eri-page")
    public ModelAndView exportEriPage(HttpServletRequest request,HttpServletResponse response){
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}
			
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("query/eriExport");
	}

	@RequestMapping(params = "method=export-eri", method = RequestMethod.GET)
    public void exportEri(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "qskh", required = true) String qskh,
    		@RequestParam(value = "zzkh", required = true) String zzkh){
		try {
			String myPath = request.getSession().getServletContext().getRealPath("/");
	        String folderPath = myPath+"download/";
	        FileUtil.createFolder(folderPath);
	        
	        ArrayList data = new ArrayList();
			ArrayList header = new ArrayList();
			header.add("TID");
			header.add("����");
			header.add("ʡ��");
			header.add("״̬");
			header.add("����");
			header.add("��ʼ������");
			header.add("�ϴθ��Ի�����");
			header.add("������");
			header.add("������");
			header.add("��װ�к�");
			header.add("������Ϣ����ID");
			header.add("��ע");
			data.add(header);
			
			Map<String, Object> condition = new HashMap<String, Object>();
			if(StringUtil.checkBN(qskh)){
                condition.put("qskh", qskh);
			}
			if(StringUtil.checkBN(zzkh)){
                condition.put("zzkh", zzkh);
			}
			List<Eri> eris = eriService.fetchByCondition(condition);
			for (int i = 0; i < eris.size(); i++) {
				Eri eri = eris.get(i);
				ArrayList data1 = new ArrayList();
				
				data1.add(eri.getTid());
				data1.add(eri.getKh());
				data1.add(eri.getSf());
				data1.add(eri.getZt());
				data1.add(eri.getPh());
				data1.add(DateUtil.formatDate(eri.getCshrq()));
				data1.add(DateUtil.formatDate(eri.getScgxhrq()));
				data1.add(eri.getGlbm());
				data1.add(eri.getKlx());
				data1.add(eri.getBzhh());
				data1.add(eri.getClxxbfid());
				data1.add(eri.getBz());
				data.add(data1);
			}
			HashMap mapData = new HashMap();
			mapData.put("eris", data);
			
			ExcelModel model = new ExcelModel();
			String path = folderPath + "ExportEri.xls";
			model.setPath(path);
			model.setDataMap(mapData);
			ExcelOperator eo = new ExcelOperator();
			eo.WriteExcel(model);
			
			response.setContentType("application/vnd.ms-excel");
			
			//��ü��������ļ�����չ����
			response.setHeader("Content-disposition","attachment; filename=ExportEri.xls");
	  
			BufferedInputStream buffInput=new BufferedInputStream(new FileInputStream(path));
			BufferedOutputStream buffout=new BufferedOutputStream(response.getOutputStream());
			int length=-1;
			byte[] buff=new byte[1024];
			while((length=buffInput.read(buff))!=-1)
			{
				buffout.write(buff, 0, length);
			}
			buffout.flush();
			buffInput.close();
	      	buffout.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory_out result fail ", e);
			e.printStackTrace();
		}
		
	}
}
