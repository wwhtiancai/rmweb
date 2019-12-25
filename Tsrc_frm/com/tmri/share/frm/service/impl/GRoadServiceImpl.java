package com.tmri.share.frm.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Roaditem;
import com.tmri.share.frm.dao.GRoadDao;
import com.tmri.share.frm.dao.GSystemDao;
import com.tmri.share.frm.service.GRoadService;

@Service

public class GRoadServiceImpl implements GRoadService {
	@Autowired
	private GRoadDao gRoadDao; 
	
	@Autowired
	private GSystemDao gSystemDao;
	
	public List<Roaditem> getRoadsByDistrict(Department department) throws Exception{
		List<Roaditem> list=new Vector<Roaditem>();
		List<Roaditem> road=this.gRoadDao.getRoads();
		if("1".equals(department.getBmjb())){
			Set<Roaditem> ary = new TreeSet<Roaditem>(new Comparator<Roaditem>() {  
        public int compare(Roaditem o1,Roaditem o2){  
        	return (o1.getGlbm()+o1.getDldm()).compareTo(o2.getGlbm()+o2.getDldm());  
        }
			}); 
			for(Roaditem r:road){
				if(!list.contains(r)&&(r.getGlxzdj().equals("1")||r.getGlxzdj().equals("2"))){
					Roaditem rr=new Roaditem(r.getGlbm(),r.getDldm(),r.getDlmc(),r.getXzqh(),r.getGlxzdj());
					if(rr.getXzqh().indexOf(",")!=-1){
						String t1=rr.getXzqh();
						String t2="";
						String tmpLast = "";
						String [] t1Ary = t1.split(",");
						if(t1Ary.length > 0){
							for(String tmpT1 : t1Ary){
								String curStr = tmpT1.substring(0,2)+"0000";
								if(!curStr.equals(tmpLast)){
									t2 += "," + curStr;
									tmpLast = curStr;
								}
							}
						}
						rr.setXzqh(t2);
					}else{
						rr.setXzqh(rr.getXzqh().substring(0,2)+"0000");
					}
					ary.add(rr);
				}
			}
			for (Roaditem ri:ary){  
        list.add(ri); 
			} 
			return list;
		}else if("2".equals(department.getBmjb())){
			String s=department.getGlbm().substring(0,2);
			Set<Roaditem> ary = new TreeSet<Roaditem>(new Comparator<Roaditem>() {  
        public int compare(Roaditem o1,Roaditem o2){  
        	return (o1.getGlbm()+o1.getDldm()).compareTo(o2.getGlbm()+o2.getDldm());  
        }
	    });
			for(Roaditem r:road){
				String[] tmp=r.getXzqh().split(",");
				for(String ss:tmp){
					if(!list.contains(r)&&ss.substring(0,2).equals(s)&&(s.equals("31") || s.equals("11") || s.equals("12") || s.equals("50") || r.getGlxzdj().equals("1")||r.getGlxzdj().equals("2")||r.getGlxzdj().equals("3"))){
						Roaditem rr=new Roaditem(r.getGlbm(),r.getDldm(),r.getDlmc(),r.getXzqh(),r.getGlxzdj());
						if(rr.getXzqh().indexOf(",")!=-1){
							String t1=rr.getXzqh();
							String t2="";
							String tmpLast = "";
							String [] t1Ary = t1.split(",");
							if(t1Ary.length > 0){
								for(String tmpT1 : t1Ary){
									String curStr = tmpT1.substring(0,4)+"00";
									if(!curStr.equals(tmpLast)){
										t2 += "," + curStr;
										tmpLast = curStr;
									}
								}
							}
							rr.setXzqh(t2.substring(1));
						}else{
							rr.setXzqh(rr.getXzqh().substring(0,4)+"00");
						}
						ary.add(rr);
					}
				}
			}
			for (Roaditem ri:ary){  
        list.add(ri); 
			} 
			return list;
		}else if("3".equals(department.getBmjb())){
			String xzqh=this.gSystemDao.getDistrictByDepartment(department.getGlbm());
			if(xzqh==null||xzqh.length()<1){
				return null;
			}
			String s=xzqh.substring(0,4);
			for(Roaditem r:road){
				String[] tmp=r.getXzqh().split(",");
				for(String ss:tmp){
					if(!list.contains(r)&&ss.substring(0,4).equals(s))
						list.add(r);
				}
			}
			return list;
		}else if("4".equals(department.getBmjb())||"5".equals(department.getBmjb())){
			String xzqh=this.gSystemDao.getDistrictByDepartment(department.getGlbm());
			if(xzqh==null||xzqh.length()<1){
				return null;
			}
			String[] tmp=xzqh.split(",");
			for(String s:tmp){
				for(Roaditem r:road){
					if((","+r.getXzqh()+",").indexOf(","+s+",")!=-1){
						if(!list.contains(r))
							list.add(r);
					}
				}
			}
			return list;
		}else{
			return null;
		}
	}
	
	
	public List<Roaditem> getRoadsByDistrict(Department department,String glxzqh) throws Exception{
		List<Roaditem> list=new Vector<Roaditem>();
		List<Roaditem> road=this.gRoadDao.getRoads();
		if("1".equals(department.getBmjb())){
			Set<Roaditem> ary = new TreeSet<Roaditem>(new Comparator<Roaditem>() {  
        public int compare(Roaditem o1,Roaditem o2){  
        	return (o1.getGlbm()+o1.getDldm()).compareTo(o2.getGlbm()+o2.getDldm());  
        }
			}); 
			for(Roaditem r:road){
				if(!list.contains(r)&&(r.getGlxzdj().equals("1")||r.getGlxzdj().equals("2"))){
					Roaditem rr=new Roaditem(r.getGlbm(),r.getDldm(),r.getDlmc(),r.getXzqh(),r.getGlxzdj());
					if(rr.getXzqh().indexOf(",")!=-1){
						String t1=rr.getXzqh();
						String t2="";
						String tmpLast = "";
						String [] t1Ary = t1.split(",");
						if(t1Ary.length > 0){
							for(String tmpT1 : t1Ary){
								String curStr = tmpT1.substring(0,2)+"0000";
								if(!curStr.equals(tmpLast)){
									t2 += "," + curStr;
									tmpLast = curStr;
								}
							}
						}
						rr.setXzqh(t2);
					}else{
						rr.setXzqh(rr.getXzqh().substring(0,2)+"0000");
					}
					ary.add(rr);
				}
			}
			for (Roaditem ri:ary){  
        list.add(ri); 
			} 
			return list;
		}else if("2".equals(department.getBmjb())){
			String s=department.getGlbm().substring(0,2);
			Set<Roaditem> ary = new TreeSet<Roaditem>(new Comparator<Roaditem>() {  
        public int compare(Roaditem o1,Roaditem o2){  
        	return (o1.getGlbm()+o1.getDldm()).compareTo(o2.getGlbm()+o2.getDldm());  
        }
	    });
			for(Roaditem r:road){
				String[] tmp=r.getXzqh().split(",");
				for(String ss:tmp){
					if(!list.contains(r)&&ss.substring(0,2).equals(s)&&(s.equals("31") || s.equals("11") || s.equals("12") || s.equals("50") || r.getGlxzdj().equals("1")||r.getGlxzdj().equals("2")||r.getGlxzdj().equals("3"))){
						Roaditem rr=new Roaditem(r.getGlbm(),r.getDldm(),r.getDlmc(),r.getXzqh(),r.getGlxzdj());
						if(rr.getXzqh().indexOf(",")!=-1){
							String t1=rr.getXzqh();
							String t2="";
							String tmpLast = "";
							String [] t1Ary = t1.split(",");
							if(t1Ary.length > 0){
								for(String tmpT1 : t1Ary){
									String curStr = tmpT1.substring(0,4)+"00";
									if(!curStr.equals(tmpLast)){
										t2 += "," + curStr;
										tmpLast = curStr;
									}
								}
							}
							rr.setXzqh(t2.substring(1));
						}else{
							rr.setXzqh(rr.getXzqh().substring(0,4)+"00");
						}
						ary.add(rr);
					}
				}
			}
			for (Roaditem ri:ary){  
        list.add(ri); 
			} 
			return list;
		}else if("3".equals(department.getBmjb())){
			String xzqh=glxzqh;
			if(xzqh==null||xzqh.length()<1){
				return null;
			}
			String s=xzqh.substring(0,4);
			for(Roaditem r:road){
				String[] tmp=r.getXzqh().split(",");
				for(String ss:tmp){
					if(!list.contains(r)&&ss.substring(0,4).equals(s))
						list.add(r);
				}
			}
			return list;
		}else if("4".equals(department.getBmjb())||"5".equals(department.getBmjb())){
			String xzqh=glxzqh;
			if(xzqh==null||xzqh.length()<1){
				return null;
			}
			String[] tmp=xzqh.split(",");
			for(String s:tmp){
				for(Roaditem r:road){
					if((","+r.getXzqh()+",").indexOf(","+s+",")!=-1){
						if(!list.contains(r))
							list.add(r);
					}
				}
			}
			return list;
		}else{
			return null;
		}
	}
	
	
	public List<Roaditem> getRoadsByManagement(Department department) throws Exception{
		List<Roaditem> list=new Vector<Roaditem>();
		String key="";
		List<Roaditem> road=this.gRoadDao.getRoads();
		if("1".equals(department.getBmjb())){
			for(Roaditem r:road){
				if(r.getGlbm().startsWith(key)&&(r.getGlxzdj().equals("1")||r.getGlxzdj().equals("2")))
					list.add(r);
			}
		}else if("2".equals(department.getBmjb())){
			key=department.getGlbm().substring(0,2);
			for(Roaditem r:road){
				if(r.getGlbm().startsWith(key)&&(r.getGlxzdj().equals("1")||r.getGlxzdj().equals("2")||r.getGlxzdj().equals("3")))
					list.add(r);
			}
		}else if("3".equals(department.getBmjb())){
			key=department.getGlbm().substring(0,4);
			for(Roaditem r:road){
				if(r.getGlbm().startsWith(key))
					list.add(r);
			}
		}else if("4".equals(department.getBmjb())||"5".equals(department.getBmjb())){
			key=department.getGlbm().substring(0,8)+"0000";
			for(Roaditem r:road){
				if(key.equals(r.getGlbm()))
					list.add(r);
			}
		}else{
			return null;
		}
		
		
		return list;
	}
	
	public List<Roaditem> getRoads() throws Exception{
		return this.gRoadDao.getRoads();
	}
	public Roaditem getRoad(String dldm,String glbm) throws Exception{
		return this.gRoadDao.getRoad(dldm,glbm);
	}
	public String getRoadValue(String dldm,String glbm) throws Exception{
		return this.gRoadDao.getRoadValue(dldm,glbm);
	}
	
	public List getRoadsegList(String glbm,String dldm) throws Exception{
		return this.gRoadDao.getRoadsegList(glbm, dldm);
	}
	
	public String getRoaditemValue(String dldm, String glbm) throws Exception {
		String dlmc = dldm;
		Roaditem bean = this.gRoadDao.getRoaditem(dldm, glbm);
		if (null != bean) {
			dlmc = bean.getDlmc();
		}
		return dlmc;
	}
	
	public List<Roaditem> getRoaditemList(String dldm) throws Exception{
		return this.gRoadDao.getRoaditemList(dldm);
	}
}
