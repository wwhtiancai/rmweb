package com.tmri.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.framework.bean.FrmLogTask;
import com.tmri.framework.bean.FrmTaskTj;
import com.tmri.framework.dao.TransDao;
import com.tmri.framework.service.TransManager;
import com.tmri.share.frm.util.PageController;
@Service

public class TransManagerImpl implements TransManager{
	@Autowired
	private TransDao transDao;

//	public void setTransDao(TransDao transDao) {
//		this.transDao = transDao;
//	}
	public List getLogtask(FrmLogTask frmlogtask, PageController controller) {
		return this.transDao.getLogtask(frmlogtask, controller);
	}
	/**
	 * ��ȡlogrow��־
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getLogrow(FrmLogTask frmlogtask, PageController controller) {
		return this.transDao.getLogrow(frmlogtask, controller);
	}
	/**
	 * ��ȡupqueue��־
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getUpqueue(FrmLogTask frmlogtask, PageController controller) {
		return this.transDao.getUpqueue(frmlogtask, controller);
	}
	/**
	 * ��ȡupqueue��־
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getTransTj(FrmLogTask frmlogtask){
		HashMap hm=new HashMap();
		FrmTaskTj tj;
		List list=this.transDao.getTransTj(frmlogtask);
		if (list!=null)
		{
			for(int i=0;i<list.size();i++)
			{
				hm.put(((FrmTaskTj)list.get(i)).getBzid(), list.get(i));
			}
		}
		List rtn=new ArrayList();
		tj=(FrmTaskTj)hm.get("Z01");
		if (tj!=null){
			tj.setSjmc("�����¼����");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z01");
			tj.setSjmc("�����¼����");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z02");
		if (tj!=null){
			tj.setSjmc("�����¼�ɹ���");
			rtn.add(tj);
		}else
		{
			tj.setBzid("Z02");
			tj=new FrmTaskTj();
			tj.setSjmc("�����¼�ɹ���");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z03");
		if (tj!=null){
			tj.setSjmc("�����¼ʧ����");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z03");
			tj.setSjmc("�����¼ʧ����");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z04");
		if (tj!=null){
			tj.setSjmc("������������");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z04");
			tj.setSjmc("������������");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z05");
		if (tj!=null){
			tj.setSjmc("��������ɹ���");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z05");
			tj.setSjmc("��������ɹ���");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z06");
		if (tj!=null){
			tj.setSjmc("��������ʧ����");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z06");
			tj.setSjmc("��������ʧ����");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z07");
		if (tj!=null){
			tj.setSjmc("���б��ѹ��������");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z07");
			tj.setSjmc("���б��ѹ��������");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z08");
		if (tj!=null){
			tj.setSjmc("���б�δ��������");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z08");
			tj.setSjmc("���б�δ��������");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z09");
		if (tj!=null){
			tj.setSjmc("���б���ʧ��������");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z09");
			tj.setSjmc("���б���ʧ��������");
			rtn.add(tj);
		}
		return rtn;
	}


}
