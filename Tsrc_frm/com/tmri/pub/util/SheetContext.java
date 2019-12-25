package com.tmri.pub.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 单例，存放表单结构缓存,缓存算法为LRU
 * @author  chiva
 * @date 2010-07-01
 */
public class SheetContext{
	
	private static SheetContext sheetContext=null;

	private static LRULinkedHashMap<String,String> detailCache;
	
	private static LRULinkedHashMap<String,String> listCache;

	private static LRULinkedHashMap<String,List<Map<String, Object>>> cellCache;
	
	private static LRULinkedHashMap<String,Map<String, Object>> queryCache;
	
	private static LRULinkedHashMap<String,Map<String, Object>> reportCache;
	private static LRULinkedHashMap<String,Map<String, Object>> statCondCache;
	
	private static LRULinkedHashMap<String,Map<String, Object>> statProcCache;
	
	private static HashMap<String,String> contrast;
	private SheetContext(){
		detailCache=new LRULinkedHashMap<String,String>(1000);
		listCache=new LRULinkedHashMap<String,String>(1000);
		cellCache=new LRULinkedHashMap<String,List<Map<String, Object>>>(1000);
		contrast=new HashMap<String,String>();
		queryCache = new LRULinkedHashMap<String, Map<String,Object>>(100);
		reportCache = new LRULinkedHashMap<String, Map<String,Object>>(100);
		statCondCache = new LRULinkedHashMap<String, Map<String,Object>>(100);
		statProcCache = new LRULinkedHashMap<String, Map<String,Object>>(100);
	}

	public static SheetContext getInstance(){
		if(sheetContext==null){
			sheetContext=new SheetContext();			
		}
		return sheetContext;
	}

	/**
	 * 需要全部重新加载
	 */
	public void refresh(){
		detailCache.clear();
		listCache.clear();
		cellCache.clear();
		contrast.clear();
		queryCache.clear();
		statCondCache.clear();
		statProcCache.clear();
	}
	public void refresh(String cachelx){
		if (cachelx.equals("detailCache"))
		{
			detailCache.clear();
		}
		if (cachelx.equals("listCache"))
		{
			listCache.clear();
		}
		if (cachelx.equals("cellCache"))
		{
			cellCache.clear();
		}
		if (cachelx.equals("contrast"))
		{
			contrast.clear();
		}
		if (cachelx.equals("queryCache"))
		{
			queryCache.clear();
		}
		if (cachelx.equals("reportCache"))
		{
			reportCache.clear();
		}
		if (cachelx.equals("statCondCache"))
		{
			statCondCache.clear();
		}
		if (cachelx.equals("statProcCache"))
		{
			statProcCache.clear();
		}
	}
	public void removeDetail(String key)
	{
		detailCache.remove(key);
	}
	public void removeList(String key)
	{
		listCache.remove(key);
	}
	public void removeCell(String key)
	{
		cellCache.remove(key);
	}
	public void removeQuery(String key){
		queryCache.remove(key);
	}
	public void removeReport(String key){
		reportCache.remove(key);
	}
	public void removeStatCond(String key){
		statCondCache.remove(key);
	}
	public void removeStatProc(String key){
		statProcCache.remove(key);
	}
	public String getValueDetail(String key){
		return detailCache.get(key);
	}
	public String getValueDetailbyName(String key){
		return detailCache.get(contrast.get(key));
	}
	public String getValueList(String key){
		return listCache.get(key);
	}
	public String getValueListbyName(String key){
		return listCache.get(contrast.get(key));
	}
	public List<Map<String,Object>> getValueCell(String key){
		return cellCache.get(key);
	}
	public List<Map<String,Object>> getValueCellbyName(String key){
		return cellCache.get(contrast.get(key));
	}
	public Map<String,Object> getQuerybyKey(String key){
		return queryCache.get(key);
	}
	public Map<String,Object> getReportbyKey(String key){
		return reportCache.get(key);
	}
	public Map<String,Object> getStatCondbyKey(String key){
		return statCondCache.get(key);
	}
	public Map<String,Object> getStatProcbyKey(String key){
		return statProcCache.get(key);
	}
	public Object putDetail(String key,String value){
		return detailCache.put(key,value);
	}
	public Object putList(String key,String value){
		return listCache.put(key,value);
	}

	public Object putCell(String key,List<Map<String, Object>> value){
		if (value!=null&&key!=null&&value.size()>0)
		{contrast.put((String)value.get(0).get("tbname"), key);}
		return cellCache.put(key,value);
	}
	public Object putContrast(String name,String id)
	{
		return contrast.put(name, id);
	}
	public Object putQueryObj(String key,Map<String, Object> value)
	{
		return queryCache.put(key, value);
	}
	public Object putReportObj(String key,Map<String, Object> value)
	{
		return reportCache.put(key, value);
	}
	public Object putStatCondObj(String key,Map<String, Object> value)
	{
		return statCondCache.put(key, value);
	}
	public Object putStatProcObj(String key,Map<String, Object> value)
	{
		return statProcCache.put(key, value);
	}
	public String getContrast(String name)
	{
		return contrast.get(name);
	}
	public boolean containsContrast(String name)
	{
		return contrast.containsKey(name);
	}
	public boolean containsDeail(String key){
		return detailCache.containsKey(key);
	}
	public boolean containsList(String key){
		return listCache.containsKey(key);
	}
	public boolean containsCell(String key){
		return cellCache.containsKey(key);
	}
	public boolean containsQuery(String key){
		return queryCache.containsKey(key);
	}
	public boolean containsReport(String key){
		return reportCache.containsKey(key);
	}
	public boolean containsStatCond(String key){
		return statCondCache.containsKey(key);
	}
	public boolean containsStatProc(String key){
		return statProcCache.containsKey(key);
	}
	public String dumpContextDetail(){
		StringBuffer buffer=new StringBuffer();
		for(Entry<String,String>entry:detailCache.entrySet())
		{buffer.append(entry.getKey());
		 buffer.append("\n  ");
		 buffer.append(entry.getValue());
		 buffer.append("\n");
		}
		return buffer.toString();
	}
	public String dumpContextList(){
		StringBuffer buffer=new StringBuffer();
		for(Entry<String,String>entry:listCache.entrySet())
		{buffer.append(entry.getKey());
		 buffer.append("\n  ");
		 buffer.append(entry.getValue());
		 buffer.append("\n");
		}
		return buffer.toString();
	}
}
