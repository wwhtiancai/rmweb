package com.tmri.rm.dao.jdbc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.tmri.rm.bean.Vehicle;
import com.tmri.rm.dao.VehQryDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.StringUtil;

@Repository
public class VehQryDaoJdbc extends FrmDaoJdbc implements VehQryDao {
	private static final Set<String> ipList = new HashSet<String>();

	public int getStreamMachineCount(String requestIp) throws Exception {
		// TODO ²âÊÔÓï¾ä ¿ÉÉ¾³ý
		if ("10.2.41.223".equals(requestIp)) {
			return 1;
		}
		
		if (!StringUtil.checkBN(requestIp)) {
			return 0;
		}
		
		if (!ipList.contains(requestIp)) {
			int count = jdbcTemplate.queryForInt("select count(1) from rm_stream_machine where jqlx = '2' and ip = '" + requestIp + "'");
			if (count > 0) {
				ipList.add(requestIp);
			}
			return count;
		}
		return 1;
	}
	
	
	public List<Vehicle> getVehList() throws Exception {
		String sql = "select distinct hpzl,hphm from rmbu_tj.uncheck_kycl_pass_0211 where syxz is null ";
		return this.jdbcTemplate.queryForList(sql, Vehicle.class);
	}
	
	public void updateVeh(Vehicle vehicle) throws Exception {
		String sql = "update rmbu_tj.uncheck_kycl_pass_0211 " +
				" set yxqz=to_date('"+vehicle.getYxqz().substring(0, 10)+"','yyyy-mm-dd') , syxz='"+ vehicle.getSyxz()+"' " +
				" where hpzl='"+vehicle.getHpzl()+"' and hphm='"+vehicle.getHphm()+"' ";
		this.jdbcTemplate.execute(sql);
	}
	

}
