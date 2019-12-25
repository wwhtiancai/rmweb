package com.tmri.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
@Service

public class FrmService{
	@Autowired
	FrmDataObjDaoJdbc frmDataObjDao;

//	public FrmDataObjDaoJdbc getDataObjDao(){
//		return frmDataObjDao;
//	}
//
//	public void setFrmDataObjDao(FrmDataObjDaoJdbc dataObjDao){
//		this.frmDataObjDao=dataObjDao;
//	}

}
