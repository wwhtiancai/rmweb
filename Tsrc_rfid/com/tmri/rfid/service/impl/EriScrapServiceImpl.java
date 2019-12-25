package com.tmri.rfid.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.common.EriScrapDetailStatus;
import com.tmri.rfid.common.EriScrapStatus;
import com.tmri.rfid.common.EriStatus;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.util.MapUtilities;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.EriScrapApp;
import com.tmri.rfid.bean.EriScrapDetail;
import com.tmri.rfid.mapper.EriScrapAppMapper;
import com.tmri.rfid.mapper.EriScrapDetailMapper;
import com.tmri.rfid.property.ConfigProperty;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.EriScrapService;
import com.tmri.share.frm.util.StringUtil;

/*
 *wuweihong
 *2015-11-12
 */
@Service
public class EriScrapServiceImpl extends BaseServiceImpl implements EriScrapService{

	private final static Logger LOG = LoggerFactory.getLogger(EriScrapServiceImpl.class);
	
	@Autowired
    private EriScrapDetailMapper eriScrapDetailMapper;
	
	@Autowired
    private EriScrapAppMapper eriScrapAppMapper;

	@Resource
	private EriService eriService;
    
	@Override
	public EriScrapApp queryList(String bfdh) throws Exception {
		// TODO Auto-generated method stub
		return eriScrapAppMapper.queryByBfdh(bfdh);
	}

	@Override
	public List<EriScrapDetail> queryDetailList(String bfdh) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("bfdh", bfdh);
		condition.put("zt", EriScrapDetailStatus.SUBMIT.getStatus());
		return eriScrapDetailMapper.queryByCondition(condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<EriScrapApp> queryScrapList(int pageIndex, int pageSize, String bfdh,String bfyy) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		if (StringUtil.checkBN(bfdh)) {
			map.put("bfdh", bfdh);
		}
		if (StringUtil.checkBN(bfyy)) {
			map.put("bfyy", bfyy);
		}
		return (PageList<EriScrapApp>) getPageList(EriScrapAppMapper.class, "queryByCondition",
	    		map, pageIndex, pageSize);
	}

	@Override
	public int creatEriScrapApp(EriScrapApp eriScrapApp) throws Exception {
		// TODO Auto-generated method stub
		eriScrapApp.setQqrq(new Date());
		return eriScrapAppMapper.create(eriScrapApp);
	}

	@Override
	public int updateEriScrapApp(EriScrapApp eriScrapApp) throws Exception {
		// TODO Auto-generated method stub
		return eriScrapAppMapper.update(eriScrapApp);
	}

	@Override
	public int creatEriScrapDetail(EriScrapDetail eriScrapDetail) throws Exception {
		// TODO Auto-generated method stub
		return eriScrapDetailMapper.create(eriScrapDetail);
	}

	@Override
	public int updateEriScrapDetail(EriScrapDetail eriScrapDetail) throws Exception {
		// TODO Auto-generated method stub
		return eriScrapDetailMapper.update(eriScrapDetail);
	}

	@Override
	public int deleteEriScrapApp(String bfdh) throws Exception {
		// TODO Auto-generated method stub
		return eriScrapAppMapper.deleteById(bfdh);
	}

	@Override
	public int deleteEriScrapDetail(String xh) throws Exception {
		// TODO Auto-generated method stub
		return eriScrapDetailMapper.deleteById(xh);
	}

	@Override
	public int deleteEriScrapAppByBfdh(String bfdh) throws Exception {
		// TODO Auto-generated method stub
		return eriScrapDetailMapper.deleteByBfdh(bfdh);
	}
	
	public static  boolean uploadFile(String bfdh, String myPath,MultipartFile mFile) throws Exception {
		File file=new File(myPath);
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    file .mkdir();    
		}  
		 File uploadFile = new File(myPath, bfdh + "." + FilenameUtils.getExtension(mFile.getOriginalFilename()));
         OutputStream outputStream = new FileOutputStream(uploadFile);
         BufferedInputStream bufferedInputStream = null;
         BufferedOutputStream bufferedOutputStream = null;
         try {
             bufferedInputStream = new BufferedInputStream(mFile.getInputStream());
             bufferedOutputStream = new BufferedOutputStream(outputStream);
  
             final byte temp[] = new byte[8192];
             int readBytes = 0;
             while ((readBytes = bufferedInputStream.read(temp)) != -1) {
                 bufferedOutputStream.write(temp, 0, readBytes);
             }
             bufferedOutputStream.flush();
  
         } finally {
             if (bufferedOutputStream != null) {
                 bufferedOutputStream.close();
             }
             if (bufferedInputStream != null) {
                 bufferedInputStream.close();
             }
         }
     	return true;
	 }

	@Override
	public List<EriScrapApp> fetchByCondition(Map<Object, Object> condition, int page, int pageSize) throws Exception {
		return eriScrapAppMapper.queryByCondition(MapUtilities.buildMap("zt", 1), new PageBounds(page, pageSize));
	}

	public int updateByCondition(Map<Object, Object> condition) throws Exception {
		return eriScrapAppMapper.updateByCondition(condition);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public boolean finish(String bfdh) throws Exception {
		int result = updateByCondition(MapUtilities.buildMap("cond_bfdh", bfdh, "zt", EriScrapStatus.DONE.getStatus()));
		if (result > 0) {
			List<EriScrapDetail> eriScrapDetailList = queryDetailList(bfdh);
			if (!eriScrapDetailList.isEmpty()) {
				for (int i = 0; i < eriScrapDetailList.size(); i++) {
					Map condition = new HashMap();
					String tid = eriScrapDetailList.get(i).getTid();
					String kh = eriScrapDetailList.get(i).getKh();
					if (StringUtils.isNotEmpty(tid)) {
						condition.put("cond_tid", tid);
					} else if (StringUtils.isNotEmpty(kh)) {
						condition.put("cond_kh", kh);
						condition.put("cond_zt", EriStatus.AVAILABLE.getStatus());
					}

					condition.put("zt", EriStatus.DISABLE.getStatus());
					condition.put("bz", "±¨·Ï£¬±¨·Ïµ¥ºÅ:" + bfdh);
					eriService.updateByCondition(condition);
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
