/**   
*/
package com.winterframework.firefrog.common.util;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/** 
* @ClassName: PageTag 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-9-26 下午5:39:04 
*  
*/
public class PermissionTag extends TagSupport {

	private static final long serialVersionUID = -480471960045959932L;

	private String moduleName;

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		ServletRequest request = this.pageContext.getRequest();
		List<String> aclList = (List<String>) request.getAttribute("aclList");
		if (aclList != null && (!aclList.isEmpty())) {
			if (aclList.contains(this.moduleName)) {
				return EVAL_BODY_INCLUDE;
			}
		}
		return EVAL_BODY_INCLUDE;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
