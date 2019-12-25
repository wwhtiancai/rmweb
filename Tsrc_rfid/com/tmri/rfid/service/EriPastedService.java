package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.EriCustomizeContent;
import com.tmri.rfid.bean.EriPastedLog;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.bean.MaterialInventory;
import com.tmri.rfid.bean.MaterialWarehouse;
import com.tmri.rfid.bean.PackageCase;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author stone
 * @date 2016-3-31 ÏÂÎç1:46:15
 */
public interface EriPastedService {

	void updatePastedEris(File f, String lyrq) throws Exception;

	void savePasted(EriPastedLog eriPastedLog) throws Exception;

	void saveUnStandard(EriPastedLog eriPastedLog) throws Exception;

	void saveUnStandards(File f) throws Exception;

	void checkPastedEri(EriPastedLog eriPastedLog) throws Exception;

}
