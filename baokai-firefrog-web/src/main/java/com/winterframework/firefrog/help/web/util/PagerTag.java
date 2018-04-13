/**   
* @Title: PageTag.java 
* @Package com.winterframework.firefrog.help.web.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-9-26 下午5:39:04 
* @version V1.0   
*/
package com.winterframework.firefrog.help.web.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/** 
* @ClassName: PageTag 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-9-26 下午5:39:04 
*  
*/
public class PagerTag extends TagSupport {

	private static final long serialVersionUID = -2324354546456561L;
	private int pageSize = 10; //每页要显示的记录数   
	private int pageNo = 1; //当前页号 
	private int totalCount; //总记录数
	private String doPre = "doPre";
	private String doNext = "doNext";
	private String doForward = "doForward";
	private String doCurrent = "doCurrent";

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}
	
	private int totalPages() {
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
	}


	@Override
	public int doStartTag() throws JspException {
		int totalPages = this.totalPages();
		JspWriter out = this.pageContext.getOut();
		StringBuilder sb = new StringBuilder();
		sb.append("<input id=\"pageNo\" type=\"hidden\" name=\"pageNo\" value=\""+pageNo+"\">");
		sb.append("<input id=\"pageSize\" type=\"hidden\" name=\"pageSize\" value=\""+pageSize+"\">");
		sb.append("<div class=\"page-wrapper\">");
		sb.append("<span class=\"page-text\">");
		sb.append("<span class=\"lower\">共" + totalCount + "条记录</span>");
		sb.append("</span>");
		sb.append("<div class=\"page page-right\">");
		if (pageNo != 1) {
			sb.append("<a href=\"javascript:void(0)\" onclick=\"" + doPre + "()\" class=\"prev\">上一步</a>");
		}
		if (totalPages <= 10) {
			for (int i = 1; i <= totalPages; i++) {
				sb.append("<a href=\"javascript:void(0)\" onclick=\"" + doCurrent + "(" + i + ")" + "\" ");
				if (pageNo == i) {
					sb.append("class=\"current\" ");
				}
				sb.append(">" + i + "</a>");
			}
		}
		if (totalPages > 10 && pageNo >= 5) {
			int forwardCount = pageNo +5;
			boolean noForward = false;
			for (int i = pageNo - 4; i <= pageNo + 4; i++) {
				if(i<=totalPages){
					sb.append("<a href=\"javascript:void(0)\" onclick=\"" + doCurrent + "(" + i + ")" + "\" ");
					if (pageNo == i) {
						sb.append("class=\"current\" ");
					}
					sb.append(">" + i + "</a>");
				}
				if(i<totalPages){
					forwardCount = i+1;
				}
				if(i == totalPages){
					noForward = true;
				}
			}
			if(!noForward){
				sb.append("<a href=\"javascript:void(0)\" onclick=\"" + doForward + "(" + forwardCount + ")" + "\" >...</a>");
			}
		}
		if (totalPages > 10 && pageNo < 5) {
			for(int i=1;i<=10;i++){
				sb.append("<a href=\"javascript:void(0)\" onclick=\"" + doCurrent + "(" + i + ")" + "\" ");
				if (pageNo == i) {
					sb.append("class=\"current\" ");
				}
				sb.append(">" + i + "</a>");
			}
			sb.append("<a href=\"javascript:void(0)\" onclick=\"" + doForward + "(" + 11 + ")" + "\" >...</a>");
		}
		if(pageNo != totalPages && totalPages !=0){
			sb.append("<a href=\"javascript:void(0)\" onclick=\"" + doNext +"()\"" + " class=\"next\">下一步</a>");
		}
		if(totalPages != 0){
		sb.append("<span class=\"page-few\">到第 <input id=\"forwardPage\" type=\"text\" value=\"\" class=\"input\"> /"+totalPages+"页</span>");
		sb.append("<input type=\"button\" value=\"确 认\" class=\"page-btn\" onclick=\"" + doForward +"("+-1+")\" >");
		}
		sb.append("</div>");
		sb.append("</div>");
		try {
			out.print(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		 return EVAL_BODY_INCLUDE;

	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
