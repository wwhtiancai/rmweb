package com.tmri.framework.dao.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import com.tmri.framework.bean.FrmLogRow;
import com.tmri.framework.bean.FrmLogTask;
import com.tmri.framework.bean.FrmTaskTj;
import com.tmri.framework.bean.FrmUpQueue;
import com.tmri.framework.dao.TransDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.FrmJdbcTemplate;
@Repository
public class TransDaoJdbc extends FrmDaoJdbc implements TransDao {

	/**
	 * 获取logtask日志
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getLogtask(FrmLogTask frmlogtask, PageController controller) {
		String sql = "";
		String glbm = "";
		if (frmlogtask.getFlag() != null && !frmlogtask.getFlag().equals("")) {
			sql += " and flag = '" + frmlogtask.getFlag() + "' ";
		}
		if (frmlogtask.getQssj() != null && !frmlogtask.getQssj().equals("")) {
			sql += " and zxsj >= to_date('" + frmlogtask.getQssj()
					+ "','yyyy-mm-dd') ";
		}
		if (frmlogtask.getJssj() != null && !frmlogtask.getJssj().equals("")) {
			sql += " and zxsj <= to_date('" + frmlogtask.getJssj()
					+ "','yyyy-mm-dd') ";
		}
		if (frmlogtask.getRwid() != null && !frmlogtask.getRwid().equals("")) {
			sql += " and rwid = '" + frmlogtask.getRwid() + "' ";
		}
		sql = "select * from tmri_trans_logtask where  1=1 " + sql
				+ " Order By zxsj Desc";
		return controller.getWarpedList(sql, FrmLogTask.class, jdbcTemplate);
	}
	/**
	 * 获取logrow日志
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getLogrow(FrmLogTask frmlogtask, PageController controller) {
		String sql = "";
		String glbm = "";
		if (frmlogtask.getFlag() != null && !frmlogtask.getFlag().equals("")) {
			sql += " and t2.flag = '" + frmlogtask.getFlag() + "' ";
		}
		if (frmlogtask.getQssj() != null && !frmlogtask.getQssj().equals("")) {
			sql += " and t2.zxsj >= to_date('" + frmlogtask.getQssj()
					+ "','yyyy-mm-dd') ";
		}
		if (frmlogtask.getJssj() != null && !frmlogtask.getJssj().equals("")) {
			sql += " and t2.zxsj <= to_date('" + frmlogtask.getJssj()
					+ "','yyyy-mm-dd') ";
		}
		if (frmlogtask.getRwid() != null && !frmlogtask.getRwid().equals("")) {
			sql += " and t1.rwid = '" + frmlogtask.getRwid() + "' ";
		}
		if (frmlogtask.getZjz() != null && !frmlogtask.getZjz().equals("")) {
			sql += " and t2.zjz like '" + frmlogtask.getZjz() + "%' ";
		}
		sql = "select t2.* from tmri_trans_logtask t1,tmri_trans_logrow t2 where  t1.zxxh=t2.zxxh " + sql
				+ " Order By t2.zxsj Desc";
		return controller.getWarpedList(sql, FrmLogRow.class, jdbcTemplate);
	}
	/**
	 * 获取upqueue日志
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getUpqueue(FrmLogTask frmlogtask, PageController controller) {
		String sql = "";
		String glbm = "";
		if (frmlogtask.getFlag() != null && !frmlogtask.getFlag().equals("")) {
			sql += " and flag = '" + frmlogtask.getFlag() + "' ";
		}
		if (frmlogtask.getQssj() != null && !frmlogtask.getQssj().equals("")) {
			sql += " and gxsj >= to_date('" + frmlogtask.getQssj()
					+ "','yyyy-mm-dd') ";
		}
		if (frmlogtask.getJssj() != null && !frmlogtask.getJssj().equals("")) {
			sql += " and gxsj <= to_date('" + frmlogtask.getJssj()
					+ "','yyyy-mm-dd') ";
		}
		if (frmlogtask.getZjz() != null && !frmlogtask.getZjz().equals("")) {
			sql += " and cdtcols = '" + frmlogtask.getZjz() + "' ";
		}

		sql = "select * from tmri_trans_up_queue where  1=1 " + sql
				+ " Order By gxsj Desc";
		return controller.getWarpedList(sql, FrmUpQueue.class, jdbcTemplate);
	}
	/**
	 * 获取upqueue日志
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getTransTj(FrmLogTask frmlogtask) {
		String sql = "";
		String glbm = "";
		if (frmlogtask.getQssj() != null && !frmlogtask.getQssj().equals("")) {
			sql = " and zxxh >= '" + frmlogtask.getQssj().substring(0,4)+frmlogtask.getQssj().substring(5,7)+frmlogtask.getQssj().substring(8,10)
					+ "' ";
		}
		if (frmlogtask.getJssj() != null && !frmlogtask.getJssj().equals("")) {
			sql = " and zxxh <= '" + frmlogtask.getJssj().substring(0,4)+frmlogtask.getJssj().substring(5,7)+frmlogtask.getJssj().substring(8,10)
			+ "' ";
		}
		sql = "select bzid,sum(tjsl) zl,sum(decode(sjlx,'01',1,0)*tjsl) sl1,sum(decode(sjlx,'02',1,0)*tjsl) sl2,sum(decode(sjlx,'03',1,0)*tjsl) sl3,sum(decode(sjlx,'04',1,0)*tjsl) sl4,sum(decode(sjlx,'05',1,0)*tjsl) sl5 from tmri_trans_logrow_tj where  1=1 " + sql
				+ " group by bzid Order By bzid";
		return this.jdbcTemplate.queryForList(sql, FrmTaskTj.class);
	}
	
	
	
}
