package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_TASK_LOG�ĳ־��� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class FrmTaskLog implements Serializable{
    private String zxxh;	// ִ�����
    private String xtlb;	//ϵͳ���
    private String rwid;	//����ID
    private String zxsj;	//ִ��ʱ�䣬ָ�������ʱ�� 
    private String jgbj;	//������ 0-ʧ�� 1-�ɹ� 
    private String fhxx;	//������Ϣ 
    private String bz;		//��ע  
    private String rwmc;	
    private String ipdz;	// ����ִ�е�IP��ַ
    private String Kssj;	// ����ʼʱ��   

    public String getXtlb(){
        return this.xtlb;
    }
    public void setXtlb(String xtlb1) {
        this.xtlb =xtlb1;
    }
    public String getRwid(){
        return this.rwid;
    }
    public void setRwid(String rwid1) {
        this.rwid =rwid1;
    }
    public String getZxsj(){
        return this.zxsj;
    }
    public void setZxsj(String zxsj1) {
        this.zxsj =zxsj1;
    }
    public String getFhxx(){
        return this.fhxx;
    }
    public void setFhxx(String fhxx1) {
        this.fhxx =fhxx1;
    }
    public String getBz(){
        return this.bz;
    }
    public void setBz(String bz1) {
        this.bz =bz1;
    }
		public String getJgbj(){
			return jgbj;
		}
		public void setJgbj(String jgbj){
			this.jgbj=jgbj;
		}
		public String getRwmc(){
			return rwmc;
		}
		public void setRwmc(String rwmc){
			this.rwmc=rwmc;
		}
		public String getZxxh() {
			return zxxh;
		}
		public void setZxxh(String zxxh) {
			this.zxxh = zxxh;
		}
		public void setIpdz(String ipdz) {
			this.ipdz = ipdz;
		}
		public String getIpdz() {
			return ipdz;
		}
		public void setKssj(String kssj) {
			Kssj = kssj;
		}
		public String getKssj() {
			return Kssj;
		}

}
