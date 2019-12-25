package com.tmri.share.frm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.dao.GRoadDao;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.dao.GSystemDao;

@Service

public class BaseServiceImpl {

	@Autowired
	protected GSysparaCodeDao gSysparaCodeDao;
	@Autowired
	protected GDepartmentDao gDepartmentDao;
	@Autowired
	protected GSystemDao gSystemDao;
	@Autowired
	protected GRoadDao gRoadDao;
}
