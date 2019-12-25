package com.tmri.framework.bean;
import java.util.*;
import java.io.Serializable;

/**
 * <p>Title:FRM_TASK_LOG的持久类 </p>
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
    private String zxxh;	// 执行序号
    private String xtlb;	//系统类别
    private String rwid;	//任务ID
    private String zxsj;	//执行时间，指任务结束时间 
    private String jgbj;	//结果标记 0-失败 1-成功 
    private String fhxx;	//返回信息 
    private String bz;		//备注  
    private String rwmc;	
    private String ipdz;	// 任务执行的IP地址
    private String Kssj;	// 任务开始时间   

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
