package com.winterframework.firefrog.sample.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.winterframework.firefrog.common.exception.ServerException;
import com.winterframework.firefrog.common.exception.ValidExcetion;
import com.winterframework.firefrog.sample.entity.Task;
import com.winterframework.firefrog.sample.service.TaskManager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /task/ Create page : GET /task/create Create action : POST
 * /task/save Update page : GET /task/update/{id} Update action : POST
 * /task/save/{id} Delete action : POST /task/delete/{id}
 * 
 * @author abba
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

	@Resource(name = "taskService")
	private TaskManager taskService;
	public String execute(Model model) throws ServerException {	
		return "monitor";
	}

	@RequestMapping(value = { "/testValid", "" })
	public String testValidException(Model model) throws ValidExcetion {
		if (true) {
			throw new ValidExcetion();
		}
		return "taskList";
	}

	@RequestMapping(value = { "/testServer", "" })
	public String testVServerException(Model model) throws ServerException {
		if (true) {
			throw new ServerException();
		}
		return "taskList";
	}

	@RequestMapping(value = "create")
	public String createForm(Model model) {
		model.addAttribute("task", new Task());
		return "taskForm";
	}

	@RequestMapping(value = "save")
	public String create(@ModelAttribute("newTask") Task newTask, RedirectAttributes redirectAttributes) {
		taskService.saveOrUpdate(newTask);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/task/";
	}

	@RequestMapping(value = "update/{id}")
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", taskService.getById(id));
		return "taskForm";
	}

	@RequestMapping(value = "/test")
	@ResponseBody
	public Object test(@RequestBody Request<User> request) {
		Response<User> response = new Response<User>(request);
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (request.getBody() != null) {
			User u = new User();
			u.setPasswd(request.getBody().getParam().getPasswd());
			u.setUserName(request.getBody().getParam().getUserName());
			response.setResult(u);
		}
		return response;
	}

	/**
	 * 含分页逻辑在里面，我们的分页是最简单的需求，请求的时候提供开始行和结束行，结果的时候给出总记录数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/test2")
	@ResponseBody
	public Object test2(@RequestBody Request<User> request) {
		Response<User> response = new Response<User>(request);
		if (request.getBody() != null) {
			//u.setPasswd(request.getBody().getPasswd());
			//u.setUserName(request.getBody().getUserName());
			response.setResult(request.getBody().getParam());
			ResultPager resultPage = new ResultPager();
			resultPage.setTotal(request.getBody().getPager().getStartNo() - request.getBody().getPager().getEndNo());
			response.setResultPage(resultPage);
		}
		return response;
	}

	@RequestMapping(value = "save/{id}")
	public String update(@ModelAttribute("task") @Valid Task task, RedirectAttributes redirectAttributes) {
		taskService.saveOrUpdate(task);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/task/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		taskService.removeById(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/task/";
	}

	/**
	 * 使用@ModelAttribute, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("task")
	private Task getTask(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			return taskService.getById(id);
		}
		return null;
	}

	/**
	 * 不要绑定对象中的id属性.
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id");
	}
}
