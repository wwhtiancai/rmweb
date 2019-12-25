package com.tmri.share.frm.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.service.GHtmlService;
import com.tmri.share.frm.service.GRoadService;
import com.tmri.share.frm.service.GSysDateService;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.service.GSystemService;
import com.tmri.share.frm.service.LogService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseCtrl extends MultiActionController{

	@Autowired
	protected GSysparaCodeService gSysparaCodeService;
	@Autowired
	protected GDepartmentService gDepartmentService;
	@Autowired
	protected GSystemService gSystemService;
	@Autowired
	protected GRoadService gRoadService;
	@Autowired
	protected GHtmlService gHtmlService;
	@Autowired
	protected GSysDateService gSysDateService;
	@Autowired
	protected LogService logService;
    @Autowired
    protected GBasService gBasService;
	
	
	public ModelAndView redirectMav(String path, String mav) {
		return new ModelAndView(path + "/" + mav);
	}

	protected int pageIndex = 1;
	protected int pageSize = 20;
	
	protected void setPageInfo(HttpServletRequest request){
		if(request.getParameter("toPage") != null){
			this.pageIndex = Integer.parseInt(request.getParameter("toPage"));
		}
	}
	
    /**
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

    /**
     * 得到页面显示的分页html
     * @return
     */
    public Map getPageInfo(Paginator paginator, HttpServletRequest request){
        String formAction = request.getRequestURI()+getUrl(request);

        StringBuffer buffer=new StringBuffer();
        StringBuffer pageFormBuffer = new StringBuffer();
        buffer.append("<script language='javascript' type='text/JavaScript'>\n");
        buffer.append("function submitController(page){\n");
        buffer.append("if(page){document.myform.page.value=page;}");
        buffer.append("query_cmd();");
//        buffer.append("if (typeof isShowModal == 'undefined' ){document.pageController.target='_self';}\n");
//        buffer.append("document.pageController.toPage.value=page;\n");
//        buffer.append("document.pageController.submit();\n");
        buffer.append("}\n");
        // buffer.append("function refreshCurrentPage(){\n");
        // buffer.append("submitController("+this.currentPage+");\n");
        // buffer.append("}\n");
        buffer.append("</script>\n");

        buffer.append("<form name=\"pageController\" action=\"");
        buffer.append(formAction);
        buffer.append("\" method=\"POST\" target=\"dialog\">\n");
        //buffer.append("<input type=\"hidden\" name=\"hexQueryCondition\" value=\""+this.getHexQueryCondition()+"\"/>\n");
        //zhoujn 20100716,增加记录总数
        buffer.append("<input type=\"hidden\" name=\"totalRowsAmount\" value=\""+paginator.getTotalCount()+"\"/>\n");
        buffer.append("<input type=\"hidden\" name=\"page\" value=\""+paginator.getPage()+"\"/>\n");
        buffer.append("<input type=\"hidden\" name=\"formAction\" value=\""+formAction+"\"/>\n");
        //buffer.append(this.getElements());
        buffer.append("</form>\n");

        pageFormBuffer.append("<form name=\"returnPagedFrom\" action=\"");
        pageFormBuffer.append(formAction);
        pageFormBuffer.append("\" method=\"POST\">\n");
        //pageFormBuffer.append("<input type=\"hidden\" name=\"hexQueryCondition\" value=\""+this.getHexQueryCondition()+"\"/>\n");
        //zhoujn 20100716,增加记录总数
        pageFormBuffer.append("<input type=\"hidden\" name=\"totalRowsAmount\" value=\""+paginator.getTotalCount()+"\"/>\n");
        pageFormBuffer.append("<input type=\"hidden\" name=\"page\" value=\""+paginator.getPage()+"\"/>\n");
        pageFormBuffer.append("<input type=\"hidden\" name=\"formAction\" value=\""+formAction+"\"/>\n");
        //pageFormBuffer.append(this.getElements());
        pageFormBuffer.append("</form>\n");

        //pageFormelements = StringUtil.byte2hex(pageFormBuffer.toString().getBytes());

        int totalPages = paginator.getTotalPages();
        int previousPage = paginator.getPrePage();
        int nextPage = paginator.getNextPage();

        String pageScript=buffer.toString();
        buffer.delete(0, buffer.length());
        buffer.append("共");
        buffer.append(paginator.getTotalCount());
        buffer.append("条&nbsp;共");
        buffer.append(totalPages);
        buffer.append("页&nbsp;第");
        buffer.append(paginator.getPage());
        buffer.append("页 &nbsp;");
        buffer.append("<label onclick='submitController(");
        buffer.append(1);
        buffer.append(")' style='cursor:hand'>首页</label>&nbsp;");
        if(paginator.isHasPrePage()){// 存在前页
            buffer.append("<label onclick='submitController(");
            buffer.append(previousPage);
            buffer.append(")' style='cursor:hand'>上一页</label>&nbsp;");
        }else{
            buffer.append("<label ");
            buffer.append(">上一页</label>&nbsp;");
        }
        if(paginator.isHasNextPage()){// 存在后页
            buffer.append("<label onclick='submitController(");
            buffer.append(nextPage);
            buffer.append(")' style='cursor:hand'>下一页</label>&nbsp;");
        }else{
            buffer.append("<label ");
            buffer.append(">下一页</label>&nbsp;");
        }
        buffer.append("<label onclick='submitController(");
        buffer.append(totalPages);
        buffer.append(")' style='cursor:hand'>末页</label>&nbsp;");
        String pageCtrlDesc=buffer.toString();
        buffer.delete(0,buffer.length());

        Map<String, Object> map = new HashMap<String, Object>();

		map.put("clientScript", pageScript);
		map.put("clientPageCtrlDesc", pageCtrlDesc);
        return map;
    }
}
