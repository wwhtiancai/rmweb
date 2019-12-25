package com.tmri.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
@Service

public class FrmManagerImpl {
	@Autowired
	protected FrmDataObjDaoJdbc frmDataObjDao;

//	public FrmDataObjDaoJdbc getFrmDataObjDao() {
//		return frmDataObjDao;
//	}
//
//	public void setFrmDataObjDao(FrmDataObjDaoJdbc frmDataObjDao) {
//		this.frmDataObjDao = frmDataObjDao;
//	}
}
