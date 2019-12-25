package com.tmri.rfid.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.EriScrapApp;
import com.tmri.rfid.bean.EriScrapDetail;
import com.tmri.rfid.ctrl.view.EriScrapView;

/*
 *wuweihong
 *2015-11-12
 */
public interface EriScrapService {
	PageList<EriScrapApp> queryScrapList(int pageIndex, int pageSize, String bfdh,String bfyy) throws Exception;
	List<EriScrapApp> fetchByCondition(Map<Object, Object> condition, int page, int pageSize) throws Exception;
	EriScrapApp queryList(String bfdh) throws Exception;
	List<EriScrapDetail> queryDetailList(String bfdh) throws Exception;
	int creatEriScrapApp(EriScrapApp eriScrapApp)  throws Exception;
	int creatEriScrapDetail(EriScrapDetail eriScrapDetail)  throws Exception;
	int updateEriScrapApp(EriScrapApp eriScrapApp)  throws Exception;
	int updateEriScrapDetail(EriScrapDetail eriScrapDetail)  throws Exception;
	int deleteEriScrapDetail(String xh)  throws Exception;
	int deleteEriScrapApp(String xh)  throws Exception;
	int deleteEriScrapAppByBfdh(String bfdh)  throws Exception;
	boolean finish(String bfdh) throws Exception;
}
