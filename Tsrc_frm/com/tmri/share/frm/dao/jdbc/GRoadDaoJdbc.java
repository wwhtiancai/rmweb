package com.tmri.share.frm.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Roaditem;
import com.tmri.share.frm.bean.Roadsegitem;
import com.tmri.share.frm.dao.GRoadDao;
import com.tmri.share.frm.util.Constants;

@Repository
public class GRoadDaoJdbc extends FrmDaoJdbc implements GRoadDao {

	public List<Roaditem> getRoads() throws Exception {
		return (List<Roaditem>) SystemCache.getInstance().getValue(Constants.MEM_ROAD);
	}

	public Roaditem getRoad(String dldm, String glbm) throws Exception {
		Roaditem roaditem = new Roaditem();
		roaditem.setDldm(dldm);
		roaditem.setDlmc(dldm);
		List<Roaditem> list = (List<Roaditem>) SystemCache.getInstance().getValue(Constants.MEM_ROAD);
		if (list != null && list.size() > 0) {
			for (Roaditem road : list) {
				if (road.getDldm().equals(dldm) && road.getGlbm().equals(glbm)) {
					roaditem = road;
					break;
				}
			}
		}
		return roaditem;
	}

	public String getRoadValue(String dldm, String glbm) throws Exception {
		return this.getRoad(dldm, glbm).getDlmc();
	}

	public String getRoadSegName(String glbm, String dldm, String lddm) throws Exception {
		String res = lddm;
        List<Roadsegitem> slist = null;
		try {
			Roadsegitem s = SystemCache.getInstance().getRoadSeg(glbm, dldm, lddm);
			if (null == s) {
                String sql = "select * from frm_roadsegitem where lddm='" + lddm + "' and glbm='"
                        + glbm + "' and dldm='" + dldm + "' and rownum<=1";
                // System.out.println("road===" + sql);
                // s = jdbcTemplate.queryForObject(sql, Roadsegitem.class);
                slist = jdbcTemplate.queryForList(sql, Roadsegitem.class);
                if (slist != null && !slist.isEmpty()) {
                    s = slist.get(0);
                    SystemCache.getInstance().addRoadSeg(s);
                } else {
                    System.out.println("未获取到路段名称, lddm = " + lddm + ", glbm = " + glbm + ", dldm="
                            + dldm);
                }
			}
			if (null != s) {
				res = s.getLdmc();
			}
		} catch (DataAccessException e) {
            e.printStackTrace();
			System.out.println("获取路段名称失败, lddm = " + lddm + ", glbm = " + glbm + ", dldm=" + dldm);
		}
		return res;
	}

	public List getRoadsegList(String glbm, String dldm) throws Exception {
		List queryList = null;
		HashMap map = new HashMap();
		String sql = "select * from FRM_ROADSEGITEM where glbm=:glbm and dldm=:dldm ";
		map.put("glbm", glbm);
		map.put("dldm", dldm);
		queryList = jdbcTemplate.queryForList(sql, map, Roadsegitem.class);
		return queryList;
	}

	public Roaditem getRoaditem(String dldm, String glbm) throws Exception {
		String sql = "select * from frm_roaditem where dldm = '" + dldm + "' and glbm = '" + glbm + "' and rownum = 1";
		return (Roaditem) this.jdbcTemplate.queryForBean(sql, Roaditem.class);
	}

	public List<Roaditem> getRoaditemList(String dldm) throws Exception {
		List<Roaditem> result = new ArrayList<Roaditem>();
		List<Roaditem> list = (List<Roaditem>) SystemCache.getInstance().getValue(Constants.MEM_ROAD);
		if (list != null && list.size() > 0) {
			for (Roaditem road : list) {
				if (road.getDldm().equals(dldm)) {
					result.add(road);
				}
			}
		}
		return result;
	}
}
