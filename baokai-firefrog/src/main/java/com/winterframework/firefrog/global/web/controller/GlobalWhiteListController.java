package com.winterframework.firefrog.global.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.global.dao.GlobalGrayListDao;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListTestVO;
import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListLogVO;
import com.winterframework.firefrog.global.entity.GlobalWhiteListIp;
import com.winterframework.firefrog.global.service.GlobalWhiteListIpService;
import com.winterframework.firefrog.global.web.dto.GlobalGrayListRequest;
import com.winterframework.firefrog.global.web.dto.GlobalGrayListResponse;
import com.winterframework.firefrog.global.web.dto.GlobalGrayListStruc;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpResponse;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpStruc;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListLogStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 *
 *@ClassName: GlobalWhiteListController 
 *@Description: 指定IP白名單管理控制类
 *@author David Wu
 *@date 2016-05-16 下午3:46:49 
 *
 */

@Controller("globalWhiteListController")
@RequestMapping("/globeAdmin")
public class GlobalWhiteListController {
	private static final Logger logger = LoggerFactory.getLogger(GlobalWhiteListController.class);

	@Resource(name = "globalWhiteListIpServiceImpl")
	private GlobalWhiteListIpService globalWhiteListIpService;
	
	@Resource(name = "globalGrayListDaoImpl")
	private GlobalGrayListDao globalGrayListDao;
	
	
	private static int  strLength = 20;//顯示長度
	
	/**
	 * 
	* @Title: deleteIp 
	* @Description: 删除IP
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteWhiteListIp")
	public @ResponseBody
	Response<Object> deleteWhiteList(@RequestBody @ValidRequestBody Request<GlobalWhiteListIpRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			globalWhiteListIpService.deleteGlobalWhiteListIp(request.getBody().getParam());
		} catch (Exception e) {
			logger.error("deleteWhiteList error.", e);
			throw e;
		}		
		return response;
	}
	
	/**
	 * 
	* @Title: queryList 
	* @Description: 查询IP列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryWhiteListIPDetail")
	public @ResponseBody
	Response<GlobalWhiteListIpStruc> queryWhiteListIPDetail(@RequestBody Request<GlobalWhiteListIpRequest> request) throws Exception {		
		Response<GlobalWhiteListIpStruc> response = new Response<GlobalWhiteListIpStruc>(request);
		GlobalWhiteListIpStruc result = globalWhiteListIpService.queryGlobalWhiteListIpById(request.getBody().getParam());
		
		
		response.setResult(result);
		return response;
	}
	
	/**
	 * 
	* @Title: queryList 
	* @Description: 查询IP列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryWhiteListIP")
	public @ResponseBody
	Response<List<GlobalWhiteListIpStruc>> queryWhiteListIP(@RequestBody Request<GlobalWhiteListIpRequest> request) throws Exception {
		Response<List<GlobalWhiteListIpStruc>> response = new Response<List<GlobalWhiteListIpStruc>>(request);
		List<GlobalWhiteListIpStruc> resultList = new ArrayList<GlobalWhiteListIpStruc>();	
		PageRequest<GlobalWhiteListIpRequest> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());		
		pageRequest.setSortColumns(" UPDATE_TIME DESC");
		try {
			String word = request.getBody().getParam().getWord();
			Page<GlobalWhiteListIp> pageGlobalWhiteListIp = globalWhiteListIpService.queryGlobalWhiteListIp(pageRequest);
			for (GlobalWhiteListIp globalWhiteListIp : pageGlobalWhiteListIp.getResult()) {
				GlobalWhiteListIpStruc result = new GlobalWhiteListIpStruc();
				result.setId(globalWhiteListIp.getId());
				result.setCountry(globalWhiteListIpService.queryCountryMapIP(globalWhiteListIp.getIpAddr()));
				result.setIpAddr(globalWhiteListIp.getIpAddr());
				String tmp = word;			
				if(word != null && !word.isEmpty() && request.getBody().getParam().getType() == 0){
					String[] rsarr = globalWhiteListIp.getUserAccunt().split(";");
					for(String acunt : rsarr){
						if(!word.equals(acunt)){	tmp += ";" + acunt; }
					}
				}else{
					tmp = globalWhiteListIp.getUserAccunt();
				}
				if(tmp.length() > strLength){
					tmp = tmp.substring(0, strLength) + "...";
				}			
				result.setUserAcunt(tmp);
				result.setOperator(globalWhiteListIp.getUpdateUser());				
				result.setOperationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(globalWhiteListIp.getUpdateTime()));
				resultList.add(result);
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(pageGlobalWhiteListIp.getThisPageLastElementNumber());
			pager.setStartNo(pageGlobalWhiteListIp.getThisPageFirstElementNumber());
			pager.setTotal(pageGlobalWhiteListIp.getTotalCount());
			response.setResult(resultList);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryWhiteList error.", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/queryWhiteListLog")
	public @ResponseBody
	Response<List<GlobalWhiteListLogStruc>> queryWhiteListLog(@RequestBody Request<GlobalWhiteListIpRequest> request) throws Exception {
		Response<List<GlobalWhiteListLogStruc>> response = new Response<List<GlobalWhiteListLogStruc>>(request);
		List<GlobalWhiteListLogVO> resultList = new ArrayList<GlobalWhiteListLogVO>();
		PageRequest<GlobalWhiteListIpRequest> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());	
		try {
			resultList = globalWhiteListIpService.queryGlobalWhiteListLog(pageRequest);
		} catch (Exception e) {
			logger.error("queryWhiteList error.", e);
			throw e;
		}
		String word = request.getBody().getParam().getWord();
		List<GlobalWhiteListLogStruc> rstList = new ArrayList<GlobalWhiteListLogStruc>();
		if(resultList != null){
			
			for(GlobalWhiteListLogVO logVO : resultList){
				GlobalWhiteListLogStruc globalWhiteListLogStruc = new GlobalWhiteListLogStruc();
				globalWhiteListLogStruc.setId(logVO.getId());
				globalWhiteListLogStruc.setWhiteListIP(logVO.getWhiteListIP());
				globalWhiteListLogStruc.setCountry(logVO.getCountry());
				String tmp = word;			
				if(word != null && !word.isEmpty() && request.getBody().getParam().getType() == 0){
					String[] rsarr = logVO.getAccunt().split(";");
					for(String acunt : rsarr){
						if(!word.equals(acunt)){	tmp += ";" + acunt; }
					}
				}else{
					tmp = logVO.getAccunt();
				}	
				if(tmp.length() > strLength){
					tmp = tmp.substring(0, strLength) + "...";
				}		
				globalWhiteListLogStruc.setAccunt(tmp);
				globalWhiteListLogStruc.setOperator(logVO.getOperator());
				globalWhiteListLogStruc.setOperationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logVO.getOperationTime()) );
				globalWhiteListLogStruc.setStatus(logVO.getStatus());	
				globalWhiteListLogStruc.setListIP(logVO.getListIP());
				rstList.add(globalWhiteListLogStruc);
			}
		}
		response.setResult(rstList);
		return response;
	}
	
	@RequestMapping(value = "/saveWhiteListIp")
	public @ResponseBody
	Response<GlobalWhiteListIpResponse> saveWhiteListIp(@RequestBody Request<GlobalWhiteListIpRequest> request) throws Exception {
		Response<GlobalWhiteListIpResponse> response = new Response<GlobalWhiteListIpResponse>();
		GlobalWhiteListIpRequest req = request.getBody().getParam();
		GlobalWhiteListIpResponse resp = new GlobalWhiteListIpResponse();
		resp.setResultType(1); //新增
		String ipAddr = req.getIpAddr();
		String ipAddr_bk = req.getIpAddr_bk();
		boolean count = false;
		GlobalWhiteListIpRequest countReqExist =new GlobalWhiteListIpRequest();
		countReqExist.setIpAddr(ipAddr);
		
		if(ipAddr_bk == null){
			countReqExist.setIpAddr_bk(ipAddr);
			req.setIpAddr_bk(ipAddr);
		}
		
		countReqExist.setStatus(1L);
		try{
			logger.info("ip="+countReqExist.getIpAddr());
			count = globalWhiteListIpService.checkUserIpExist(countReqExist);
		}catch(Exception e){
			logger.debug("saveWhiteListIp checkUserIpExist 異常" + e);
			resp.setIsSuccess(0);
			resp.setMsg("");
			response.setResult(resp);
			return response;
		}
		if(count && req.getMode()==0 ){
			logger.info("saveWhiteListIp 新增的IP 已存在");
			resp.setIsSuccess(0);
			resp.setMsg("新增的IP已存在");
			response.setResult(resp);
			return response;
		}else if(count && req.getMode() ==1 && (!ipAddr.equals(ipAddr_bk))){
			logger.info("saveWhiteListIp 修改的IP 已存在");
			resp.setIsSuccess(0);
			resp.setMsg("修改的IP已存在");
			response.setResult(resp);
			return response;
		}else if(!count && req.getMode() ==1){
			req.setMode(1);
			
			Map<String, String> result = globalWhiteListIpService.saveAndUpdateGlobalWhiteListIp(req);
			resp.setIsSuccess(Integer.parseInt(result.get("result")));
			resp.setMsg((result.get("msg") == "") ? "更新IP完成" : result.get("msg"));
			logger.info("saveWhiteListIp IP更新  已完成 : " + result.get("msg"));
			response.setResult(resp);
			return response;
		}else{
			try{
				Map<String, String> result = globalWhiteListIpService.saveAndUpdateGlobalWhiteListIp(req);
				resp.setIsSuccess(Integer.parseInt(result.get("result")));
				resp.setMsg((result.get("msg") == "") ? "新增IP完成" : result.get("msg"));
				logger.info("saveWhiteListIp IP存檔  已完成 : " + result.get("msg"));
				response.setResult(resp);
				return response;
			}catch(Exception e){
				logger.error("saveWhiteListIp 紀錄異常" + e);
				resp.setIsSuccess(0);
				resp.setMsg("");
				response.setResult(resp);
				return response;
			}
		}
	}
	
	
	
	@RequestMapping(value = "/queryGrayList")
	public @ResponseBody
	Response<GlobalGrayListResponse> queryGrayList(@RequestBody Request<GlobalGrayListRequest> request) throws Exception {
		Response<GlobalGrayListResponse> response = new Response<GlobalGrayListResponse>();
		GlobalGrayListRequest req = request.getBody().getParam();
		List<GlobalGrayListTestVO> testVo = globalGrayListDao.queryGlobalGrayListByUserAccount(req.getAccount());
		List<GlobalGrayListStruc> struc = new ArrayList<GlobalGrayListStruc>();
		for(GlobalGrayListTestVO vo :testVo){
			GlobalGrayListStruc v = new GlobalGrayListStruc();
			v.setAccount(vo.getAccount());
			v.setLevel(vo.getRiskType());
			struc.add(v);
		}
		GlobalGrayListResponse res = new GlobalGrayListResponse();
		res.setUserData(struc);
		response.setResult(res);
		
		return response;
	}
	
	@RequestMapping(value = "/queryGrayList2")
	public @ResponseBody
	Response<GlobalGrayListResponse> queryGrayList2(@RequestBody Request<GlobalGrayListRequest> request) throws Exception {
		Response<GlobalGrayListResponse> response = new Response<GlobalGrayListResponse>();
		GlobalGrayListRequest req = request.getBody().getParam();
		List<GlobalGrayListTestVO> testVo = globalGrayListDao.queryGlobalGrayListByUserAccount2(req.getAccount());
		List<GlobalGrayListStruc> struc = new ArrayList<GlobalGrayListStruc>();
		for(GlobalGrayListTestVO vo :testVo){
			GlobalGrayListStruc v = new GlobalGrayListStruc();
			v.setAccount(vo.getAccount());
			v.setLevel(vo.getRiskType());
			struc.add(v);
		}
		GlobalGrayListResponse res = new GlobalGrayListResponse();
		res.setUserData(struc);
		response.setResult(res);
		
		return response;
	}
	
	

}
