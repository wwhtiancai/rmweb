package com.tmri.rfid.bean;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * ÿ�ε����ⲿ�ӿڲ�ѯ�����ݾͽ��и���
 * ÿ�ν��и��Ի��󣬾ͽ���Ϣ���浽RFID_VEHICLE_LOG��
 */
public class Vehicle extends VehicleInfo implements RowMapper {

    private Long id; //���

    private String xh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setFzjg(rs.getString("fzjg"));
        vehicle.setYxqz(rs.getDate("yxqz"));
        vehicle.setQzbfqz(rs.getDate("qzbfqz"));
        vehicle.setBxzzrq(rs.getDate("bxzzrq"));
        vehicle.setBz(rs.getString("bz"));
        vehicle.setCcrq(rs.getDate("ccrq"));
        vehicle.setCllx(rs.getString("cllx"));
        vehicle.setCsys(rs.getString("csys"));
        vehicle.setHdzk(rs.getInt("hdzk"));
        vehicle.setHdzzl(rs.getInt("hdzzl"));
        vehicle.setPl(rs.getInt("pl"));
        vehicle.setHphm(rs.getString("hphm"));
        vehicle.setHpzl(rs.getString("hpzl"));
        vehicle.setSyr(rs.getString("syr"));
        vehicle.setSyxz(rs.getString("syxz"));
        vehicle.setId(rs.getLong("id"));
        //vehicle.setClpp(rs.getString("clpp"));
        //vehicle.setClsbdh(rs.getString("clsbdh"));
        vehicle.setZzl(rs.getInt("zzl"));
        return vehicle;
    }
}
