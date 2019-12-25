package com.tmri.framework.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.bean.FrmGispara;
import com.tmri.framework.dao.FrmGisparaDao;
import com.tmri.framework.service.FrmGisparaService;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.service.impl.BaseServiceImpl;
import com.tmri.share.ora.bean.DbResult;

@Service
public class FrmGisparaServiceImpl extends BaseServiceImpl implements FrmGisparaService {
    @Autowired
    private FrmGisparaDao gisparaDao;

    @Autowired
    private SLogDao sLogDao;

    @Autowired
    protected GSysparaCodeService gSysparaCodeService;

    // �г����йؼ���
    public static final String[] GISPARAS = { "GISLX", "GISJB", "GISDTFWDZ1", "GISDTFWDZ2",
            "GISDTFWDZ3", "GISKJSJYQ", "GISSJFWGXDZ", "GISFWYH", "GISXZQHB", "GISDLSLXXB",
            "GISDLTPGXB" };

    public static final String[] CSMC = { "GIS��ͼ����", "GIS��ͼ����", "GISʸ����Ƭ�����ַ", "GISӰ����Ƭ�����ַ",
            "GISʸ��Ӱ�������Ƭ�����ַ", "GIS�ռ�������������", "GIS���ݷ��ʸ��µ�ַ", "GIS���ݷ��ʸ����û�", "GIS�����������ֶ�����",
            "GIS��·ʸ����Ϣ���ֶ�����", "GIS��·���˹�ϵ���ֶ�����" };

    public static final String[] DMLB = { "5002", "5003", "", "", "", "5004", "", "", "", "", "" };

    public static final String[] CSZ = { "1-PGISդ���ͼ���񣨷����ͼ��׼��;2-PGISդ���ͼ�������ͼ��׼��;3-���ͼWMTS����;4-WMTS����;5-ArcGIS REST����;6-ArcGIS ����ͼƬ;7-SUPERMAP REST����;8-WMS����;9-����",
            "0-������;1-ʡ��(�ܶ�);2-������(֧��)", "", "", "", "1-SDE;2-SDX+;3-ORACLESPATIAL", "", "", "",
            "", "" };

    public static final String[] XSXS = { "2", "2", "1", "1", "1", "2", "1", "1", "1", "1", "1" };

    @SuppressWarnings("unchecked")
    public List<FrmGispara> getGisParaList(FrmGispara para, RmLog log) throws Exception {
        List<FrmGispara> list = this.gisparaDao.getGisParaList(para);
        int size = list.size();
        // String glbm = para.getGlbm();
        // String glbmmc = gDepartmentDao.getDepartmentBmmc(glbm);

        for (int j = 0, i = 0; j < GISPARAS.length; j++) {
            FrmGispara bean = null;
            if (size > 0) {
                bean = list.get(i);
            }
            String n = Integer.toString(j + 1);
            if (size == 0 || !n.equals(bean.getXssx())) {
                FrmGispara temp = new FrmGispara();
                // temp.setGlbm(glbm);
                // temp.setGlbmmc(glbmmc);
                temp.setGjz(GISPARAS[j]);
                temp.setCsmc(CSMC[j]);
                temp.setCssm(CSMC[j]);
                temp.setCsz(CSZ[j]);
                temp.setXsxs(XSXS[j]);
                temp.setDmlb(DMLB[j]);
                temp.setXssx(n);
                temp.setSflr("0");
                temp.setSfxs("1");
                temp.setFzmc("GIS");
                list.add(temp);
            } else {
                if (i < size - 1) {
                    i++;
                }
                bean.setCsz(CSZ[j]);
                bean.setSflr("1");
                if ("2".equals(bean.getXsxs())) {
                    String cszStr = CSZ[j];
                    String[] cszs = cszStr.split(";");
                    for (String csz : cszs) {
                        String[] cs = csz.split("-");
                        if (bean.getMrz().equals(cs[0])) {
                            bean.setMrzmc(cs[1]);
                        }
                    }
                }
                // bean.setGlbmmc(glbmmc);
                bean.setFzmc("GIS");
            }
        }
        Collections.sort(list, new Comparator<FrmGispara>() {
            public int compare(FrmGispara o1, FrmGispara o2) {
                int xssx1 = Integer.valueOf(o1.getXssx());
                int xssx2 = Integer.valueOf(o2.getXssx());
                return (xssx1 - xssx2);
            }
        });
        return (List<FrmGispara>) sLogDao.saveRmLogForQuery(list, log);
    }

    public DbResult saveBean(FrmGispara bean, RmLog log) throws Exception {
        DbResult result = null;
            result = gisparaDao.setOracleData("Rm_Setdata_Pkg", "Setfrm_Gispara", bean);
            if (result != null && result.getCode() == 1) {
                this.sLogDao.saveRmLog(log);
                result = gisparaDao.save("Rm_Pkg.Savefrm_Gispara");
            }
        return result;
    }

    public FrmGispara getGisParaByPk(String gjz) throws Exception {
        FrmGispara bean = gisparaDao.getGisParaByPk(gjz);
        // bean.setGlbmmc(gDepartmentDao.getDepartmentBmmc(bean.getGlbm()));
        return bean;
    }
}
