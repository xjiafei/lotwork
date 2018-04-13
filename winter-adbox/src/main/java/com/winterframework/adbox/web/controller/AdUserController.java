package com.winterframework.adbox.web.controller;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.dto.ResResult;
import com.winterframework.adbox.entity.AdUser;
import com.winterframework.adbox.service.IAdUserService;
import com.winterframework.adbox.utils.PageResult;


 
/**
 * @author 登陆ip分发
 *
 */
@Controller("adUserController")
@RequestMapping("/user")
public class AdUserController {
	
	
	private static final Logger log = LoggerFactory.getLogger(DeviceInterfaceController.class);
	@Resource(name = "adUserServiceImpl")
	private IAdUserService adUserServiceImpl;

	@Resource(name = "adDeviceController")
	private AdDeviceController adDeviceController;
	
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("login");
		return view;
	}
	
	@RequestMapping("/loginError")
	public ModelAndView loginError(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("loginError");
		view.addObject("message", "请重新登录系统！");
		return view;
	}
	
	
	@RequestMapping("/lg")
	public ModelAndView loginDo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = null;
		try{
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		AdUser adUser = adUserServiceImpl.getUserByUserPass(userName, password);
		
		if(adUser != null){
			view = new ModelAndView("index");
			request.getSession().setAttribute("loginUser",adUser);
			request.getSession().setAttribute("userList", this.getUserList(request));
			view.addObject("userType", adUser.getType());
			view.addObject("loginUser",adUser);
		}else{
			view = new ModelAndView("login");
			view.addObject("message", "登录失败，请用正确的用户名密码！");
			request.getSession().removeAttribute("loginUser");
		}
		}catch(Exception e){
			log.error("login error", e);
			throw new Exception();
		}
		return view;
	}
	
	@RequestMapping("/unLogin")
	public ModelAndView unLogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("login");;
		try{
			request.getSession().removeAttribute("loginUser");
		}catch(Exception e){
			log.error("unlogin error", e);
			throw new Exception();
		}
		return view;
	}
	
	
	
	
	@RequestMapping("/password")
	public ModelAndView password(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("password.jsp");
		return view;
	}
	
	@RequestMapping("/setPassword")
	@ResponseBody
	public Object setPassword(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResResult<Long> res = new ResResult<Long>();
		try{
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		AdUser loginUser = (AdUser)request.getSession().getAttribute("loginUser");
		if(!userId.equals(loginUser.getId()+"")){//不是修改自己密码
			if(loginUser.getType() ==3){
				res.setMessage("非管理员不能修改用户密码！");
				res.setCode(0);
				return res;
			}
		}
		AdUser adUser = adUserServiceImpl.get(Long.valueOf(userId));
		Context ctx = new Context();
		ctx.set("userId", loginUser.getId());
		adUser.setPassword(password);
		adUserServiceImpl.save(ctx, adUser);
		res.setCode(1);
		}catch(Exception e){
			log.error("register error", e);
			res.setMessage("修改密码失败！");
			res.setCode(0);
		}
		return res;
	}	
	
	@RequestMapping("/registerDo")
	@ResponseBody
	public Object registerDo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResResult<Long> res = new ResResult<Long>();
		try{
		AdUser loginUser = (AdUser)request.getSession().getAttribute("loginUser");
		if(loginUser.getType() ==3){
			res.setCode(0);
			res.setMessage("非管理员不能添加用户！");
			return res;
		}
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		AdUser adUser = adUserServiceImpl.getUserByUserPass(userName, null);
		if(adUser!=null){
			res.setCode(0);
			res.setMessage("用户名不能重复！");
			return res;
		}
		
		AdUser newUser = new AdUser();
		newUser.setName(userName);
		newUser.setPassword(password);
		newUser.setType(loginUser.getType()==1?2:3);
		newUser.setParentId(loginUser.getId());
		Context ctx = new Context();
		ctx.set("userId", loginUser.getId());
		adUserServiceImpl.save(ctx, newUser);
		res.setCode(1);
		}catch(Exception e){
			res.setCode(0);
			res.setMessage("添加用户失败！");
			return res;
		}
		return res;
	}
	
	
	@RequestMapping("/toUserList")
	public ModelAndView toUserList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("userList");
		return view;
	}
	
	
	private List<AdUser> getUserList(HttpServletRequest request) throws Exception{
		List<AdUser> adList = new ArrayList<AdUser>();
		AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
		if(adUser.getType().intValue()==1){
			adList = adUserServiceImpl.getUserList(2,null,null);
		}else{
			adList.add(adUser);
			if(adUser.getType().intValue()==2){
				adList.addAll(adUserServiceImpl.getUserList(3,null,adUser.getId()));
			}
		}
		return adList;
	}
	
	
	@RequestMapping("/userList")
	@ResponseBody
	public Object userList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userName =  request.getParameter("userName");
		
		AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
		List<AdUser> adList = new ArrayList<AdUser>();
		try{
		if(adUser.getType().intValue()==1){
			adList = adUserServiceImpl.getUserList(2,userName,null);
		}else{
			if(adUser.getType().intValue()==2){
				adList = adUserServiceImpl.getUserList(3,null,adUser.getId());
			}
		}
		for(AdUser u:adList){
			u.setUpdateName(u.getModifier() == null?null:adUserServiceImpl.get(u.getModifier()).getName());
			u.setCreatorName(u.getCreator()==null?null:adUserServiceImpl.get(u.getCreator()).getName());
		}}catch(Exception e){
			log.error("userList error", e);
		}
		PageResult<AdUser> pg = new PageResult<AdUser>();
		pg.setRows(adList);
		return pg;
	}
}
