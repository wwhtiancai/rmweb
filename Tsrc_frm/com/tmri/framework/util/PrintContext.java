package com.tmri.framework.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * �������ṩһ��context����ӡ����ʹ��
 * @author ����
 *
 */
public class PrintContext{
	private static PrintContext applicationContext=null;
	private static Hashtable tab;
    private PrintContext(){
    	tab=new Hashtable();
    }
    public static PrintContext getInstance(){
    	if(applicationContext==null){
    		applicationContext= new PrintContext();
    	}
    	return applicationContext;
    }
  
    /**
     * ��Ҫȫ�����¼���
     */
    public void refresh(){
    	tab.clear();
    }
    public void remove(Object key){
    	tab.remove(key);
    }
	public Object getValue(Object key) {
		return tab.get(key);
	}
    public boolean reg(Object key,Object value){
    	if(tab.containsKey(key)){
    		return false;
    	}else{
    		
    		tab.put(key, value);
    		return true;
    	}
    	

    }
    public boolean contains(Object key){
    	return tab.containsKey(key);
    }
	public String dumpContext(){
    	StringBuffer buffer=new StringBuffer();
    	 
    	 for (Enumeration e = tab.keys() ; e.hasMoreElements() ;) {
             Object key=e.nextElement();
             buffer.append(key);
             buffer.append("\n");
             Map map=(Map)tab.get(key);
             Set set=map.keySet();
             for(Iterator i=set.iterator();i.hasNext();){
            	 Object dmz=i.next();
            	 Object   object=map.get(dmz);
            	 buffer.append(dmz);
                 buffer.append(object);
                 buffer.append("\n");
             }
         }
    	return buffer.toString();
    }        
}
