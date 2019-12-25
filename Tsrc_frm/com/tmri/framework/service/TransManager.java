package com.tmri.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tmri.framework.bean.FrmLogTask;
import com.tmri.framework.bean.FrmTaskTj;
import com.tmri.share.frm.util.PageController;

public interface TransManager {
	public List getLogtask(FrmLogTask frmlogtask, PageController controller) ;
	/**
	 * ��ȡlogrow��־
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getLogrow(FrmLogTask frmlogtask, PageController controller) ;
	/**
	 * ��ȡupqueue��־
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getUpqueue(FrmLogTask frmlogtask, PageController controller) ;
	/**
	 * ��ȡupqueue��־
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getTransTj(FrmLogTask frmlogtask);
}
