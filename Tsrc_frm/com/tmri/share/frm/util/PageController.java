package com.tmri.share.frm.util;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.abc.fed.b.WrapHelper;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.FrmRowMapper;
import com.tmri.share.ora.SheetRowMapper;

/**
 * @author nick 2004-3-18 用来进行翻页控制
 * @edit WengYf 2007-4-9 修改提交分页信息后丢失原提交项的BUG
 */
public class PageController{
	int toPage;// 接受来自客户端的指定页面值

	int totalRowsAmount=0; // 总行数

	boolean rowsAmountSet=false; // 是否设置过totalRowsAmount

	int pageSize=20; // 每页行数

	int currentPage=1; // 初始化页码

	int nextPage; // 下一页

	int previousPage; // 上一页

	int lastPage; // 末页

	int totalPages; // 总页数

	boolean hasNext; // 是否有下一页

	boolean hasPrevious; // 是否有前一页

	String description;

	int pageStartRow; // 页开始行

	int pageEndRow; // 页结束行

	String formAction;

	String hexQueryCondition;

	String pageCtrlDesc;

	String pageScript;

	String pageFormelements;

	String elements;

	String querySql;
	
	String currentTab;	
	
	public String getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}

	public String getQuerySql(){
		return querySql;
	}

	public void setQuerySql(String querySql){
		this.querySql=querySql;
	}

	public PageController(int totalRows){
		setTotalRowsAmount(totalRows);
	}

	public PageController(HttpServletRequest req){
		ObjectCreator.CreateObjectsFromPage(req,this);
		this.setElements(req);
		setFormAction(req.getRequestURI()+getUrl(req));
	}

	public PageController(){
	}

	public void setTotalRowsAmount(int i){
		this.totalRowsAmount=i;
	}

	public void setPagespara(int i){
		if(!this.rowsAmountSet){
			totalRowsAmount=i;
			// totalPages = totalRowsAmount / pageSize + 1;
			int k=totalRowsAmount%pageSize;
			if(k==0){
				totalPages=totalRowsAmount/pageSize;

			}else{
				totalPages=totalRowsAmount/pageSize+1;

			}
			setCurrentPage(1);
			this.rowsAmountSet=true;
		}
	}
	
	public void setRowsAmountSet(boolean rowsAmountSet){
		this.rowsAmountSet = rowsAmountSet;
	}
	
	//对于一个页面使用多个TAB显示不同分页结果集时使用 sunxp 111226
	private void setPagespara(int i,String tabno){
		if(!this.rowsAmountSet){
			totalRowsAmount=i;
			// totalPages = totalRowsAmount / pageSize + 1;
			int k=totalRowsAmount%pageSize;
			if(k==0){
				totalPages=totalRowsAmount/pageSize;

			}else{
				totalPages=totalRowsAmount/pageSize+1;

			}
			setCurrentPage(1,tabno);
			this.rowsAmountSet=true;
		}
	}	

	public void setCurrentPage(int i){
		currentPage=i;
		nextPage=currentPage+1;
		previousPage=currentPage-1;
		// 计算当前页开始行和结束行
		if(currentPage*pageSize<=totalRowsAmount){
			pageEndRow=currentPage*pageSize;
			pageStartRow=pageEndRow-pageSize+1;

		}else{
			pageEndRow=totalRowsAmount;
			pageStartRow=pageSize*(totalPages-1)+1;
		}
		// 是否存在前页和后页
		if(nextPage>totalPages){
			hasNext=false;
		}else{
			hasNext=true;
		}
		if(previousPage==0){
			hasPrevious=false;
		}else{
			hasPrevious=true;
		}
		this.makingDesc();
		// System.out.println(this.pageCtrlDesc);
	}

	//对于一个页面使用多个TAB显示不同分页结果集时使用 sunxp 111226
	private void setCurrentPage(int i,String tabno){
		currentPage=i;
		nextPage=currentPage+1;
		previousPage=currentPage-1;
		// 计算当前页开始行和结束行
		if(currentPage*pageSize<=totalRowsAmount){
			pageEndRow=currentPage*pageSize;
			pageStartRow=pageEndRow-pageSize+1;

		}else{
			pageEndRow=totalRowsAmount;
			pageStartRow=pageSize*(totalPages-1)+1;
		}
		// 是否存在前页和后页
		if(nextPage>totalPages){
			hasNext=false;
		}else{
			hasNext=true;
		}
		if(previousPage==0){
			hasPrevious=false;
		}else{
			hasPrevious=true;
		}
		this.makingDesc(tabno);
		// System.out.println(this.pageCtrlDesc);
	}

	private void makingDesc(){
		StringBuffer buffer=new StringBuffer();
		StringBuffer pageFormBuffer = new StringBuffer();
		buffer.append("<script language='javascript' type='text/JavaScript'>\n");
		buffer.append("function submitController(page){\n");
        buffer
                .append("if (typeof isShowModal == 'undefined' ){document.pageController.target='_self';}\n");
		buffer.append("document.pageController.toPage.value=page;\n");
		buffer.append("document.pageController.submit();\n");
		buffer.append("}\n");
		// buffer.append("function refreshCurrentPage(){\n");
		// buffer.append("submitController("+this.currentPage+");\n");
		// buffer.append("}\n");
		buffer.append("</script>\n");

		buffer.append("<form name=\"pageController\" action=\"");
		buffer.append(this.formAction);
        buffer.append("\" method=\"POST\" target=\"dialog\">\n");
		buffer.append("<input type=\"hidden\" name=\"hexQueryCondition\" value=\""+this.getHexQueryCondition()+"\"/>\n");
		//zhoujn 20100716,增加记录总数
		buffer.append("<input type=\"hidden\" name=\"totalRowsAmount\" value=\""+this.totalRowsAmount+"\"/>\n");
		buffer.append("<input type=\"hidden\" name=\"toPage\" value=\""+this.currentPage+"\"/>\n");
		buffer.append("<input type=\"hidden\" name=\"formAction\" value=\""+this.formAction+"\"/>\n");
		buffer.append(this.getElements());
		buffer.append("</form>\n");

		pageFormBuffer.append("<form name=\"returnPagedFrom\" action=\"");
		pageFormBuffer.append(this.formAction);
		pageFormBuffer.append("\" method=\"POST\">\n");
		pageFormBuffer.append("<input type=\"hidden\" name=\"hexQueryCondition\" value=\""+this.getHexQueryCondition()+"\"/>\n");
		//zhoujn 20100716,增加记录总数
		pageFormBuffer.append("<input type=\"hidden\" name=\"totalRowsAmount\" value=\""+this.totalRowsAmount+"\"/>\n");
		pageFormBuffer.append("<input type=\"hidden\" name=\"toPage\" value=\""+this.currentPage+"\"/>\n");
		pageFormBuffer.append("<input type=\"hidden\" name=\"formAction\" value=\""+this.formAction+"\"/>\n");
		pageFormBuffer.append(this.getElements());
		pageFormBuffer.append("</form>\n");
		
		pageFormelements = StringUtil.byte2hex(pageFormBuffer.toString().getBytes());

		this.pageScript=buffer.toString();
		buffer.delete(0,buffer.length());
		buffer.append("共");
		buffer.append(this.totalRowsAmount);
		buffer.append("条&nbsp;共");
		buffer.append(this.totalPages);
		buffer.append("页&nbsp;第");
		buffer.append(this.currentPage);
		buffer.append("页 &nbsp;");
		buffer.append("<label onclick='submitController(");
		buffer.append(1);
		buffer.append(")' style='cursor:hand'>首页</label>&nbsp;");
		if(this.hasPrevious){// 存在前页
			buffer.append("<label onclick='submitController(");
			buffer.append(this.previousPage);
			buffer.append(")' style='cursor:hand'>上一页</label>&nbsp;");
		}else{
			buffer.append("<label ");
			buffer.append(">上一页</label>&nbsp;");
		}
		if(this.hasNext){// 存在后页
			buffer.append("<label onclick='submitController(");
			buffer.append(this.nextPage);
			buffer.append(")' style='cursor:hand'>下一页</label>&nbsp;");
		}else{
			buffer.append("<label ");
			buffer.append(">下一页</label>&nbsp;");
		}
		buffer.append("<label onclick='submitController(");
		buffer.append(this.totalPages);
		buffer.append(")' style='cursor:hand'>末页</label>&nbsp;");
		this.pageCtrlDesc=buffer.toString();
		buffer.delete(0,buffer.length());
	}

	//对于一个页面使用多个TAB显示不同分页结果集时使用 sunxp 111226
	private void makingDesc(String tabno){
		StringBuffer buffer=new StringBuffer();
		StringBuffer pageFormBuffer = new StringBuffer();
		buffer.append("<script language='javascript' type='text/JavaScript'>\n");
		buffer.append("function submitController"+tabno+"(page){\n");
        buffer.append("document.pageController"+tabno+".target='_self';\n");
		buffer.append("document.pageController"+tabno+".toPage.value=page;\n");
		buffer.append("document.pageController"+tabno+".submit();\n");
		buffer.append("}\n");
		// buffer.append("function refreshCurrentPage(){\n");
		// buffer.append("submitController("+this.currentPage+");\n");
		// buffer.append("}\n");
		buffer.append("</script>\n");

		buffer.append("<form name=\"pageController"+tabno+"\" action=\"");
		buffer.append(this.formAction);
        buffer.append("\" method=\"POST\" target=\"dialog\">\n");
		buffer.append("<input type=\"hidden\" name=\"hexQueryCondition\" value=\""+this.getHexQueryCondition()+"\"/>\n");
		//zhoujn 20100716,增加记录总数
		buffer.append("<input type=\"hidden\" name=\"totalRowsAmount\" value=\""+this.totalRowsAmount+"\"/>\n");
		buffer.append("<input type=\"hidden\" name=\"toPage\" value=\""+this.currentPage+"\"/>\n");
		buffer.append("<input type=\"hidden\" name=\"formAction\" value=\""+this.formAction+"\"/>\n");
		buffer.append("<input type=\"hidden\" name=\"currentTab\" value=\""+tabno+"\"/>\n");
		buffer.append(this.getElements());
		buffer.append("</form>\n");

		pageFormBuffer.append("<form name=\"returnPagedFrom"+tabno+"\" action=\"");
		pageFormBuffer.append(this.formAction);
		pageFormBuffer.append("\" method=\"POST\">\n");
		pageFormBuffer.append("<input type=\"hidden\" name=\"hexQueryCondition\" value=\""+this.getHexQueryCondition()+"\"/>\n");
		//zhoujn 20100716,增加记录总数
		pageFormBuffer.append("<input type=\"hidden\" name=\"totalRowsAmount\" value=\""+this.totalRowsAmount+"\"/>\n");
		pageFormBuffer.append("<input type=\"hidden\" name=\"toPage\" value=\""+this.currentPage+"\"/>\n");
		pageFormBuffer.append("<input type=\"hidden\" name=\"formAction\" value=\""+this.formAction+"\"/>\n");
		pageFormBuffer.append(this.getElements());
		pageFormBuffer.append("</form>\n");
		
		pageFormelements = StringUtil.byte2hex(pageFormBuffer.toString().getBytes());

		this.pageScript=buffer.toString();
		buffer.delete(0,buffer.length());
		buffer.append("共");
		buffer.append(this.totalRowsAmount);
		buffer.append("条&nbsp;共");
		buffer.append(this.totalPages);
		buffer.append("页&nbsp;第");
		buffer.append(this.currentPage);
		buffer.append("页 &nbsp;");
		buffer.append("<label onclick='submitController"+tabno+"(");
		buffer.append(1);
		buffer.append(")' style='cursor:hand'>首页</label>&nbsp;");
		if(this.hasPrevious){// 存在前页
			buffer.append("<label onclick='submitController"+tabno+"(");
			buffer.append(this.previousPage);
			buffer.append(")' style='cursor:hand'>上一页</label>&nbsp;");
		}else{
			buffer.append("<label ");
			buffer.append(">上一页</label>&nbsp;");
		}
		if(this.hasNext){// 存在后页
			buffer.append("<label onclick='submitController"+tabno+"(");
			buffer.append(this.nextPage);
			buffer.append(")' style='cursor:hand'>下一页</label>&nbsp;");
		}else{
			buffer.append("<label ");
			buffer.append(">下一页</label>&nbsp;");
		}
		buffer.append("<label onclick='submitController"+tabno+"(");
		buffer.append(this.totalPages);
		buffer.append(")' style='cursor:hand'>末页</label>&nbsp;");
		this.pageCtrlDesc=buffer.toString();
		buffer.delete(0,buffer.length());
	}

	public int getCurrentPage(){
		return currentPage;
	}

	public boolean isHasNext(){
		return hasNext;
	}

	public boolean isHasPrevious(){
		return hasPrevious;
	}

	public int getNextPage(){
		return nextPage;
	}

	public int getPageSize(){
		return pageSize;
	}

	public int getPreviousPage(){
		return previousPage;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public int getTotalRowsAmount(){
		return totalRowsAmount;
	}

	public void setHexQueryCondition(String hexQueryCondition){
		this.hexQueryCondition=hexQueryCondition;
	}

	public void setToPage(int toPage){
		this.toPage=toPage;
	}

	public void setFormAction(String formAction){
		this.formAction=formAction;
	}

	public int getPageEndRow(){
		return pageEndRow;
	}

	public int getPageStartRow(){
		return pageStartRow;
	}

	public String getDescription(){
		String description="Total:"+this.getTotalRowsAmount()+" items "+this.getTotalPages()+" pages";
		// this.currentPage+" Previous "+this.hasPrevious +
		// " Next:"+this.hasNext+
		// " start row:"+this.pageStartRow+
		// " end row:"+this.pageEndRow;
		return description;
	}

	public String getHexQueryCondition(){
		return this.hexQueryCondition;
	}

	public int getToPage(){
		return toPage;
	}

	public int getLastPage(){
		return lastPage;
	}

	public String getClientFormelements(){

		return this.pageFormelements;
	}
	
	public String getClientScript(){

		return this.pageScript;
	}
	public String getClientPageCtrlDesc(){
		return this.pageCtrlDesc;
	}

	public void setPageSize(int pageSize){
		this.pageSize=pageSize;
	}

	/**
	 * 分页信息封装主方法 使用方法：在条件输入页面植入隐常变量 形如 <input type="hidden" name="formAction"
	 * value="../ext/vehsearch.do?method=searchVeh&stype=A"/> 在列表显示页面用变量
	 * pageCtrl=controller.getClientPageCtrlDesc(); 翻页控制链接输出脚本
	 * pageScript=controller.getClientScript(); 翻译控制javascript脚本
	 * hexCondition=controller.getHexQueryCondition(); 查询条件
	 * 
	 * 
	 * @param firstQuerySQL 查询语句
	 * @param conn 数据库连接
	 * @return ResultSet 结果集
	 * @throws SQLException
	 */
	// 20091229 zhoujn
	public List getWarpedList(String firstQuerySQL,Class elementType,FrmJdbcTemplate jdbcTemplate,Object[] paraObjects){
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(StringUtil.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(StringUtil.hex2byte(this.getHexQueryCondition()));
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount=jdbcTemplate.queryForInt(buffer.toString(),paraObjects);
		buffer.delete(0,buffer.length());
		this.rowsAmountSet=false;
		this.setPagespara(rowamount);
		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(" ) pagetable");
		buffer.append(" where rownum<= "+this.pageEndRow);
		buffer.append(" ) subt WHERE subt.rowcounter>"+(this.pageStartRow-1));
		// System.out.println(buffer.toString());
		// List result=jdbcTemplate.query(buffer.toString(),mapper);
		List result=jdbcTemplate.queryForList(buffer.toString(),paraObjects,elementType);
		buffer.delete(0,buffer.length());
		return result;
	}
	/**
	 * 分页查询接口，不指定Bean，用Map返回数据
	 * @param firstQuerySQL
	 * @param jdbcTemplate
	 * @param paraObjects
	 * @return List<Map<key,value>>
	 * @author wu 增加不依赖Bean的查询
	 */
	public List getWarpedList(String firstQuerySQL,FrmJdbcTemplate jdbcTemplate,Object[] paraObjects){
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(StringUtil.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(StringUtil.hex2byte(this.getHexQueryCondition()));
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount=jdbcTemplate.queryForInt(buffer.toString(),paraObjects);
		buffer.delete(0,buffer.length());
		this.rowsAmountSet=false;
		this.setPagespara(rowamount);
		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(" ) pagetable");
		buffer.append(" where rownum<= "+this.pageEndRow);
		buffer.append(" ) subt WHERE subt.rowcounter>"+(this.pageStartRow-1));
		// System.out.println(buffer.toString());
		// List result=jdbcTemplate.query(buffer.toString(),mapper);
		List result=jdbcTemplate.queryForList(buffer.toString(),paraObjects);
		buffer.delete(0,buffer.length());
		return result;
	}
	public List getWarpedList(String firstQuerySQL,Class elementType,FrmJdbcTemplate jdbcTemplate){
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(StringUtil.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(StringUtil.hex2byte(this.getHexQueryCondition()));
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount=jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0,buffer.length());
		this.rowsAmountSet=false;
		this.setPagespara(rowamount);
		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(" ) pagetable");
		buffer.append(" where rownum<= "+this.pageEndRow);
		buffer.append(" ) subt WHERE subt.rowcounter>"+(this.pageStartRow-1));
//console.sql
//System.out.println("[sql]"+querySQL);
		List result=jdbcTemplate.queryForList(buffer.toString(),elementType);
		buffer.delete(0,buffer.length());
		return result;
	}

	//对于一个页面使用多个TAB显示不同分页结果集时使用 sunxp 111226
	//tabno:tab编号
	public List getWarpedList(String firstQuerySQL,Class elementType,FrmJdbcTemplate jdbcTemplate,String tabno){
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用
		//if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(StringUtil.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		//}else{
		//	querySQL=new String(StringUtil.hex2byte(this.getHexQueryCondition()));
		//}
		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount=jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0,buffer.length());
		this.rowsAmountSet=false;
		this.setPagespara(rowamount,tabno);
		if(this.currentTab!=null&&!this.currentTab.equals(tabno)){
			this.toPage = 1;
		}
		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage,tabno);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(" ) pagetable");
		buffer.append(" where rownum<= "+this.pageEndRow);
		buffer.append(" ) subt WHERE subt.rowcounter>"+(this.pageStartRow-1));
		List result=jdbcTemplate.queryForList(buffer.toString(),elementType);
		buffer.delete(0,buffer.length());
		return result;
	}

	public List getWarpedList(String firstQuerySQL,RowMapper mapper,FrmJdbcTemplate jdbcTemplate){
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(WrapHelper.hex2byte(this.getHexQueryCondition()));
		}

		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount=jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0,buffer.length());
		this.rowsAmountSet=false;
		this.setPagespara(rowamount);
		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable) subt WHERE subt.rowcounter>");
		buffer.append(this.pageStartRow-1); // first row
		buffer.append(" AND subt.rowcounter<=");
		buffer.append(this.pageEndRow);
		// System.out.println(buffer.toString());
		List result=jdbcTemplate.query(buffer.toString(),mapper);
		buffer.delete(0,buffer.length());
		return result;
	}

	public List getWarpedList(String firstQuerySQL,HashMap map,Class elementType,FrmJdbcTemplate jdbcTemplate){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用HashMap
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(WrapHelper.hex2byte(this.getHexQueryCondition()));
		}
		StringBuffer buffer=new StringBuffer();
		//zhoujn 增加判断记录总数
		int rowamount=this.totalRowsAmount;
		if(rowamount==0){
			buffer.append("SELECT count(*) AS rown FROM ( ");
			buffer.append(querySQL);
			buffer.append(") pagetable");
			rowamount=namedParameterJdbcTemplate.queryForInt(buffer.toString(),map);
			buffer.delete(0,buffer.length());
			this.rowsAmountSet=false;
		}
		this.setPagespara(rowamount);

		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable  ");
		buffer.append(" where rownum<="+this.pageEndRow);
		buffer.append(") subt WHERE subt.rowcounter>"+(this.pageStartRow-1));
		
		// System.out.println(buffer.toString());
		List result=namedParameterJdbcTemplate.query(buffer.toString(),map,new FrmRowMapper(elementType));
		buffer.delete(0,buffer.length());
		return result;
	}
	public List<Map<String,Object>> getWarpedList(String firstQuerySQL,Map<String,String> map,FrmJdbcTemplate jdbcTemplate){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用HashMap
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(WrapHelper.hex2byte(this.getHexQueryCondition()));
		}
		StringBuffer buffer=new StringBuffer();
		//zhoujn 增加判断记录总数
		int rowamount=this.totalRowsAmount;
		if(rowamount==0){
			buffer.append("SELECT count(*) AS rown FROM ( ");
			buffer.append(querySQL);
			buffer.append(") pagetable");
			rowamount=namedParameterJdbcTemplate.queryForInt(buffer.toString(),map);
			buffer.delete(0,buffer.length());
			this.rowsAmountSet=false;
		}
		this.setPagespara(rowamount);

		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable  ");
		buffer.append(" where rownum<="+this.pageEndRow);
		buffer.append(") subt WHERE subt.rowcounter>"+(this.pageStartRow-1));
		
		// System.out.println(buffer.toString());
		List<Map<String,Object>> result=namedParameterJdbcTemplate.query(buffer.toString(),map,new SheetRowMapper());
		buffer.delete(0,buffer.length());
		return result;
	}
	public List<Map<String,Object>> getWarpedList(String firstQuerySQL,String[] p,FrmJdbcTemplate jdbcTemplate){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用HashMap
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(WrapHelper.hex2byte(this.getHexQueryCondition()));
		}
		StringBuffer buffer=new StringBuffer();
		//zhoujn 增加判断记录总数
		int rowamount=this.totalRowsAmount;
		if(rowamount==0){
			buffer.append("SELECT count(*) AS rown FROM ( ");
			buffer.append(querySQL);
			buffer.append(") pagetable");
			rowamount=jdbcTemplate.queryForInt(buffer.toString(),p);
			buffer.delete(0,buffer.length());
			this.rowsAmountSet=false;
		}
		this.setPagespara(rowamount);

		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable  ");
		buffer.append(" where rownum<="+this.pageEndRow);
		buffer.append(") subt WHERE subt.rowcounter>"+(this.pageStartRow-1));
		
		// System.out.println(buffer.toString());
		List<Map<String,Object>> result=jdbcTemplate.query(buffer.toString(),p,new SheetRowMapper());
		buffer.delete(0,buffer.length());
		return result;
	}
	public List getWarpedList(String firstQuerySQL,FrmJdbcTemplate jdbcTemplate){
		String querySQL="";
		this.querySql=firstQuerySQL; // xuxd 将查询的Sql语句返回，可以供以后条件适用
		if(this.hexQueryCondition==null||this.hexQueryCondition.equals("")){
			this.setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL=firstQuerySQL;
		}else{
			querySQL=new String(WrapHelper.hex2byte(this.getHexQueryCondition()));
		}

		StringBuffer buffer=new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount=jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0,buffer.length());
		this.rowsAmountSet=false;
		this.setPagespara(rowamount);
		if(this.toPage==0){
			this.toPage=1;
		}
		this.setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable) subt WHERE subt.rowcounter>");
		buffer.append(this.pageStartRow-1); // first row
		buffer.append(" AND subt.rowcounter<=");
		buffer.append(this.pageEndRow);
		List result=jdbcTemplate.queryForList(buffer.toString());
		buffer.delete(0,buffer.length());
		return result;
	}

	public String getFormAction(){
		return formAction;
	}

	public String description(){
		String description="Total:"+this.getTotalRowsAmount()+" items "+this.getTotalPages()+" pages,Current page:"+this.currentPage+" Previous "+this.hasPrevious+" Next:"+this.hasNext+" start row:"+this.pageStartRow+" end row:"+this.pageEndRow;
		return description;
	}

	public static void main(String args[]){
		// PageController pc = new PageController(3);
		// System.out.println(pc.getDescription());
		// pc.setCurrentPage(2);
		// System.out.println(pc.description());
		// pc.setCurrentPage(3);
		// System.out.println(pc.description());
		// String s = "select * from cc where c='你好' and kd-=>";
		// byte[] b = s.getBytes();
		// String hex = WrapUtil.byte2hex(b);
		// System.out.println(hex);
		// String r = new String(WrapUtil.hex2byte(hex));
		// System.out.println(r);
	}

	/**
	 * Author:WengYufeng Date:Apr 9, 2007 8:43:10 AM
	 * @param request
	 * @return String
	 */
	private String getUrl(HttpServletRequest request){
		String url="";
		Enumeration param=request.getParameterNames();
		while(param.hasMoreElements()){
			String pname=param.nextElement().toString();
			if(pname.equalsIgnoreCase("method")) url+="?"+pname+"="+request.getParameter(pname);
		}
		return url;
	}

	public String getElements(){
		return elements;
	}

	public void setElements(HttpServletRequest request){
		String s="";
		Enumeration param=request.getParameterNames();
		while(param.hasMoreElements()){
			String pname=param.nextElement().toString();
			if(!pname.equalsIgnoreCase("page")&&!pname.equalsIgnoreCase("method")
					&&!pname.equalsIgnoreCase("toPage")&&!pname.equalsIgnoreCase("formAction")
					&&!pname.equalsIgnoreCase("hexQueryCondition")
					&&!pname.equalsIgnoreCase("totalRowsAmount")//wu 增加对 “zhoujn 20100716,增加记录总数” 导致分页第三页报错 的修改，过滤新增变量
					&&!pname.equalsIgnoreCase("currentTab"))//sunxp 111226 增加currentTab，用于查询结果页面中控制当前TAB页，因此在所有查询条件页面表单中input元素的名称 name不能与currentTab重复
			{
				String value = "";
				String[] values = request.getParameterValues(pname);
				if (values != null) {
					for (String v : values) {
						value += v + ",";
					}
				}
				if (!"".equals(value)) {
					value = value.substring(0, value.length() - 1);
				}
				s += "<input type=\"hidden\" name=\"" + pname + "\" value=\""
						+ value + "\">\n";
			}
		}
		this.elements=s;
	}
}