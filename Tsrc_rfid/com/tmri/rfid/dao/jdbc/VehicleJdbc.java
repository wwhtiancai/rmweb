package com.tmri.rfid.dao.jdbc;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.dao.VehicleDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import org.apache.commons.lang.StringUtils;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 * Created by Joey on 2015/9/6.
 */
@Repository
public class VehicleJdbc extends FrmDaoJdbc implements VehicleDao {

    @Override
    public Vehicle queryById(Long id) {
    	String sql =
                String.format("select * from rfid_vehicle where id = '%s'", id);
    	 try {
    		 Object result = this.jdbcTemplate.queryForObject(sql, new Vehicle());
             return (Vehicle) result;
         } catch (EmptyResultDataAccessException erdae) {
             return null;
         }
        //return new Vehicle();
    }

    @Override
    public Vehicle queryByHPHMAndHPZL(String hphm, String hpzl ,String fzjg ) {
        String sql =
                String.format("select * from rfid_vehicle where hphm = '%s'", hphm);
        if (StringUtils.isNotEmpty(hpzl)) {
            sql = sql + " and hpzl = '" + hpzl + "'";
        }
        if (StringUtils.isNotEmpty(fzjg)) {
            sql = sql + " and fzjg = '" + fzjg + "'";
        }
        try {
   		 Object result = this.jdbcTemplate.queryForObject(sql, new Vehicle());
            return (Vehicle) result;
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    @Override
	public List<Vehicle> getVehicleList(Vehicle command,PageController controller) throws Exception {
		String sql = " where 1 = 1";
		if(command.getId() != null){
			sql=sql+" and id ='"+command.getId()+"'";
		}
		if(StringUtil.checkBN(command.getHpzl())){
			sql=sql+" and hpzl = '"+command.getHpzl()+"'";
		}
		if(StringUtil.checkBN(command.getHphm())){
			sql=sql+" and hphm = '"+command.getHphm()+"'";
		}
		
		sql=" select * from rfid_vehicle "+sql+" order by xh desc";
		return controller.getWarpedList(sql,new Vehicle(),jdbcTemplate);
	}
    
    @Override
    public Vehicle queryVehicle(Map<String ,Object> condition) {
    	 String sql = null;
         if(condition.containsKey("hphm")&&condition.containsKey("hpzl")){
         	sql = String.format("select * from RFID_vehicle where hphm='%s' and hpzl = '%s'",condition.get("hphm"),condition.get("hpzl"));
         }
         if(condition.containsKey("id")){
        	 sql = String.format("select * from rfid_vehicle where id = '%s'", condition.get("id"));
         }
    	 try {
    		 Object result = this.jdbcTemplate.queryForObject(sql, new Vehicle());
             return (Vehicle) result;
         } catch (EmptyResultDataAccessException erdae) {
             return null;
         }
        //return new Vehicle();
    }
}
