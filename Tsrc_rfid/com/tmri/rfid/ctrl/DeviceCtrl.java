package com.tmri.rfid.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.rfid.util.GsonHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import com.tmri.rfid.common.DeviceStatus;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.Device;
import com.tmri.rfid.bean.DeviceStation;
import com.tmri.rfid.service.DeviceService;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

/*
 *wuweihong
 *2015-10-22
 */

@Controller
@RequestMapping("/device.frm")
public class DeviceCtrl extends BaseCtrl{

	private final static Logger LOG = LoggerFactory.getLogger(DeviceCtrl.class);
	
	@Resource
	private DeviceService deviceService ;
	
    @RequestMapping(params = "method=query-device")
    public ModelAndView queryDevice(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "xh", required = false) String xh, @RequestParam(value = "sbmc", required = false) String sbmc,
    		@RequestParam(value = "dz", required = false) String dz){

    	PageList<Device> queryList = null;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();
			//String selfbmjb = dept.getBmjb();
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}
			Device device = new Device();
			if(StringUtil.checkBN(xh)){
				device.setXh(Long.valueOf(xh));
			}
			if(StringUtil.checkBN(sbmc)){
				device.setSbmc(sbmc);
			}
			if(StringUtil.checkBN(dz)){
				device.setDz(dz);
			}
			queryList = deviceService.queryList(this.pageIndex, this.pageSize, xh, sbmc, dz);
	    	Paginator page = queryList.getPaginator();
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(page, request));
//			request.setAttribute("provinces", Province.values());
            request.setAttribute("deviceStatus", DeviceStatus.values());
			request.setAttribute("command", device);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("device/deviceMain");
    }
    
    @RequestMapping(params = "method=edit-device")
    public ModelAndView editDevice(Model model, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "xh", required = false) String xh) {

		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();
			//String selfbmjb = dept.getBmjb();
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}
			if(xh != null && xh.length() > 0){
				Device device = new Device();
				device = this.deviceService.fetchDeviceByXh(Long.valueOf(xh));
				List<DeviceStation> deviceStationList = this.deviceService.queryList(Long.valueOf(xh));
				
				request.setAttribute("bean", device);
				request.setAttribute("list", deviceStationList);
			}
			model.addAttribute("deviceStatus", DeviceStatus.values());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("device/deviceEdit");
    }
    
    @RequestMapping(params = "method=del-deviceSeat")
    public ModelAndView dealDeviceSeat(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "xh", required = true) String xh) {

    	ModelAndView view;
		try {
			int code = this.deviceService.deleteByXh(Long.valueOf(xh));
			
			DbResult result = new DbResult();
			result.setCode(code);
			String msg = "删除成功";
			result.setMsg(msg);
			LOG.debug(msg);
			view = CommonResponse.JSON(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("del RFID_WORK_STATION result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		
		return view;
    }
    

	@RequestMapping(params = "method=save-device")
    public ModelAndView saveDevice(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sbmc", required = false) String sbmc, @RequestParam(value = "dz", required = false) String dz,
			@RequestParam(value = "zt", required = false) String zt, @RequestParam(value = "ip", required = false) String ip,
			@RequestParam(value = "mac", required = false) String mac, @RequestParam(value = "sbxh", required = false) String sbxh,
    		@RequestParam(value = "detailList", required = false) String detailList){
		
		ModelAndView view;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			
			if(StringUtil.isEmpty(sbxh)){
				Device device = new Device();
				device.setDz(dz);
				device.setSbmc(sbmc);
				device.setIp(ip);
				device.setMac(mac);
				device.setZt(Long.valueOf(zt));
				device.setCjrq(new Date());
				int code = deviceService.saveDevice(device);
				DbResult result = new DbResult();
				result.setCode(code);
				String msg = null;
				if(code == 1){
					if(!StringUtil.checkBN(detailList)){
					msg = "保存成功";
					} else {
					JSONArray ja = JSONArray.fromObject(detailList);
						for (int i = 0; i < ja.size(); i++) {
							JSONObject jo = (JSONObject) ja.get(i);
						DeviceStation deviceStation = new DeviceStation();
						deviceStation.setGwmc(jo.get("mc").toString());
						deviceStation.setGwxh(jo.get("xh").toString());
							deviceStation.setSbxh(device.getXh());
						deviceStation.setZt(Integer.valueOf(jo.get("zt").toString()));
						int detailCode = deviceService.saveDeviceStation(deviceStation);
							if (detailCode == 0) {
							msg = "入库单详情入库不成功";
						}
					}
					}
				}else{
					msg = "此入库单号已存在";
				}
				result.setMsg(msg);
				LOG.debug(msg);
				view = CommonResponse.JSON(result);
			}else {
				Device device = new Device();
				device.setDz(dz);
				device.setSbmc(sbmc);
				device.setIp(ip);
				device.setMac(mac);
				device.setZt(Long.valueOf(zt));
				device.setXh(Long.valueOf(sbxh));
				DbResult result = new DbResult();
				int code = deviceService.updateDevice(device);
				result.setCode(code);
				String msg = null;
				if(code == 1){
					if(!StringUtil.checkBN(detailList)){
					msg = "保存成功";
					} else {
						JSONArray ja = JSONArray.fromObject(detailList);
						for (int i = 0; i < ja.size(); i++) {
							JSONObject jo = (JSONObject) ja.get(i);
							DeviceStation deviceStation = new DeviceStation();
							deviceStation.setGwmc(jo.get("mc").toString());
							deviceStation.setSbxh(Long.valueOf(sbxh));
							deviceStation.setGwxh(jo.get("xh").toString());
							deviceStation.setZt(Integer.valueOf(jo.get("zt").toString()));
							int detailCode = deviceService.saveDeviceStation(deviceStation);
							result.setCode(detailCode);
							if (detailCode == 0) {
								msg = "入库单详情入库不成功";
							}
						}
					}
				}else{
					msg = "入库单详情更新不成功";
				}
				result.setMsg(msg);
				LOG.debug(msg);
				view = CommonResponse.JSON(result);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		
		return view;
	}
	
	  @RequestMapping(params = "method=del-device")
	    public ModelAndView delDevice(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sbxh", required = false) String sbxh) {

	    	ModelAndView view;
			try {
				DbResult result = new DbResult();
				String msg = null;
				List<DeviceStation> DeviceStationList = deviceService.queryList(Long.valueOf(sbxh));
				if(DeviceStationList.size()>0){
					int code = deviceService.deleteDeviceStation(Long.valueOf(sbxh));
					if(code==0){
						msg = "删除工位失败";
						result.setCode(code);
					}else{
						int devcode = deviceService.deleteDevice(Long.valueOf(sbxh));
						if(devcode==0){
							msg = "删除设备失败";
						}else{
							msg = "删除设备成功";
						}
						result.setCode(devcode);
					}
					
				}else{
					int devcode = deviceService.deleteDevice(Long.valueOf(sbxh));
					if(devcode==0){
						msg = "删除设备失败";
					}else{
						msg = "删除设备成功";
					}
					result.setCode(devcode);
				}
			
				result.setMsg(msg);
				LOG.debug(msg);
				view = CommonResponse.JSON(result);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("del RFID_DEVICE result fail ", e);
				e.printStackTrace();
				view = CommonResponse.JSON(e);
			}
			
			return view;
	    }

    protected final String jspPath = "jsp_core/rfid/";

    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
