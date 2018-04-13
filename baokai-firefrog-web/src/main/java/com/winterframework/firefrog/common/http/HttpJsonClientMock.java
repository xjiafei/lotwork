package com.winterframework.firefrog.common.http;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.help.web.dto.CateStruc;
import com.winterframework.firefrog.help.web.dto.HelpDetailRequest;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.firefrog.help.web.dto.KeywordStruc;
import com.winterframework.firefrog.help.web.dto.LotteryCateStruc;
import com.winterframework.firefrog.help.web.dto.LotteryContentStruc;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryAddResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;

@Service("httpJsonClientImpl1")
public class HttpJsonClientMock implements IHttpJsonClient {

	@PropertyConfig(value = "url.help.queryCategory")
	private String queryCategoryUrl;

	@PropertyConfig(value = "url.help.queryHotWord")
	private String queryHotWordUrl;

	@PropertyConfig(value = "url.help.queryHelp")
	private String queryHelpUrl;
	@PropertyConfig(value = "url.help.queryHelp")
	private String queryHelp;

	@PropertyConfig(value = "url.help.queryHelpDetail")
	private String queryHelpDetail;

	@PropertyConfig(value = "url.help.createHelpFeedback")
	private String createHelpFeedback;

	@PropertyConfig(value = "url.help.queryLotteryKnowledgeCategory")
	private String queryLotteryKnowledgeCategoryUrl;

	@PropertyConfig(value = "url.help.createLotteryKnowledgeCategory")
	private String createLotteryKnowledgeCategory;

	@PropertyConfig(value = "url.help.queryLotteryKnowledgeCategory")
	private String queryLotteryKnowledgeCategory;

	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, long userId, String userAccount, Class... dataClass) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, Class... dataClass) throws Exception {
		T t = null;
		if (url.equals(queryHelp) && ((HelpQueryRequest) requestData).getTitle() != null
				&& !((HelpQueryRequest) requestData).getTitle().equals("")) {
			List<HelpStruc> list = new ArrayList<HelpStruc>();
			HelpStruc h1 = new HelpStruc();
			h1.setContent("content");
			h1.setIsRec(1);
			h1.setCateId(1l);
			h1.setCateId2(2l);
			h1.setPreface("");
			h1.setTitle("如何绑定银行卡111");
			h1.setId(2l);
			h1.setNo(1L);
			HelpStruc h2 = new HelpStruc();
			h2.setContent("");
			h2.setIsRec(0);
			h2.setCateId(1l);
			h2.setCateId2(2l);
			h2.setPreface("");
			h2.setTitle("如何绑定银行卡222");
			h2.setId(3l);
			h2.setNo(2L);

			HelpStruc h3 = new HelpStruc();
			h3.setIsRec(0);
			h3.setCateId(4l);
			h3.setCateId2(2l);
			h3.setTitle("彩种");
			h3.setId(3l);
			h3.setNo(2L);

			list.add(h1);
			list.add(h2);
			list.add(h3);

			Page<HelpStruc> page = new Page<HelpStruc>(12, 10, 123, list);
			page.setPageNo(3);
			t = (T) page;
		} else if (url.equals(queryHelp)) {
			Page<HelpStruc> page = new Page<HelpStruc>( pager.getStartNo() / 10 + 1,10,100);
			List<HelpStruc> list = new ArrayList<HelpStruc>();
			HelpStruc h1 = new HelpStruc();
			h1.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h1.setIsRec(1);
			h1.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h1.setTitle("如何注册111");
			h1.setId(2l);
			HelpStruc h2 = new HelpStruc();
			h2.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h2.setIsRec(0);
			h2.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h2.setTitle("如何注册222");
			h2.setId(3l);
			list.add(h1);
			list.add(h2);
			page.setResult(list);
			t = (T) page;
		}
		Response<T> resp = new Response<T>();
		resp.setResult(t);
		return resp;
	}

	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, long userId, String userAccount, Class... dataClass)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Class... dataClass) throws Exception {
		T t = null;
		if (url.equals(queryHelp) && requestData instanceof HelpQueryRequest
				&& ((HelpQueryRequest) requestData).getType() == 0l) {
			List<HelpStruc> hotQuestion = new ArrayList<HelpStruc>();
			HelpStruc q1 = new HelpStruc();
			q1.setTitle("问题1");
			HelpStruc q2 = new HelpStruc();
			q2.setTitle("问题2");
			HelpStruc q3 = new HelpStruc();
			q3.setTitle("问题3");
			HelpStruc q4 = new HelpStruc();
			q4.setTitle("问题4");
			hotQuestion.add(q1);
			hotQuestion.add(q2);
			hotQuestion.add(q3);
			hotQuestion.add(q4);
			t = (T) hotQuestion;
		} else if (url.equals(queryHelp) && requestData instanceof HelpQueryRequest
				&& ((HelpQueryRequest) requestData).getType() == 1l) {
			List<HelpStruc> hotLottery = new ArrayList<HelpStruc>();
			HelpStruc l1 = new HelpStruc();
			l1.setTitle("彩种1");
			l1.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			l1.setIsRec(1);
			l1.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			l1.setTitle("自定义彩票1");
			l1.setLotteryAdvert("自定义彩票1");
			HelpStruc l2 = new HelpStruc();
			l2.setTitle("彩种2");
			l2.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			l2.setIsRec(0);
			l2.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			l2.setTitle("自定义彩票2");
			l2.setLotteryAdvert("自定义彩票2");
			HelpStruc l3 = new HelpStruc();
			l3.setTitle("彩种3");
			HelpStruc l4 = new HelpStruc();
			l4.setTitle("彩种4");
			l1.setNo(4l);
			l2.setNo(3l);
			l3.setNo(2l);
			l4.setNo(1l);
			l1.setId(1l);
			l2.setId(2l);
			l3.setId(3l);
			l4.setId(4l);
			hotLottery.add(l1);
			hotLottery.add(l2);
			hotLottery.add(l3);
			hotLottery.add(l4);
			t = (T) hotLottery;
		} else if (url.equals(queryHelpDetail) && requestData instanceof HelpDetailRequest
				&& ((HelpDetailRequest) requestData).getHelpId() == 3l) {
			HelpStruc helpStruc = new HelpStruc();
			helpStruc.setId(1L);
			helpStruc.setTitle("this is lottery title");
			helpStruc.setContent("this is lottery detail content");
			helpStruc.setCateId(4l);
			helpStruc.setCateId2(2L);
			helpStruc.setLotteryAdvert("adv");
			helpStruc.setLotteryLogo("test.jpg");
			helpStruc.setLotteryLink("www.xx.com");
			helpStruc
					.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			LotteryContentStruc l1 = new LotteryContentStruc();
			l1.setName("this is test1");
			l1.setContent("his is test1");
			LotteryContentStruc l2 = new LotteryContentStruc();
			l2.setName("this is test2");
			l2.setContent("his is test2");
			LotteryContentStruc l3 = new LotteryContentStruc();
			l3.setName("this is test3");
			l3.setContent("his is test3");
			LotteryContentStruc l4 = new LotteryContentStruc();
			l4.setName("this is test4");
			l4.setContent("his is test4");
			List<LotteryContentStruc> list = new ArrayList<LotteryContentStruc>();
			list.add(l1);
			list.add(l2);
			list.add(l3);
			list.add(l4);
			helpStruc.setLotteryContentStruc(list);
			t = (T) helpStruc;
		} else if (url.equals(queryHelpDetail)) {
			HelpStruc helpStruc = new HelpStruc();
			helpStruc.setId(1L);
			helpStruc.setTitle("this is help title");
			helpStruc.setContent("this is help detail content");
			helpStruc.setCateId(1l);
			helpStruc.setCateId2(2L);
			helpStruc
					.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			LotteryContentStruc l1 = new LotteryContentStruc();
			l1.setName("this is test1");
			l1.setContent("his is test1");
			LotteryContentStruc l2 = new LotteryContentStruc();
			l2.setName("this is test2");
			l2.setContent("his is test2");
			LotteryContentStruc l3 = new LotteryContentStruc();
			l3.setName("this is test3");
			l3.setContent("his is test3");
			LotteryContentStruc l4 = new LotteryContentStruc();
			l4.setName("this is test4");
			l4.setContent("his is test4");
			List<LotteryContentStruc> list = new ArrayList<LotteryContentStruc>();
			list.add(l1);
			list.add(l2);
			list.add(l3);
			list.add(l4);
			helpStruc.setLotteryContentStruc(list);
			t = (T) helpStruc;
		} else if (url.equals(queryHelp)) {
			List<HelpStruc> list = new ArrayList<HelpStruc>();
			HelpStruc h1 = new HelpStruc();
			h1.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h1.setIsRec(1);
			h1.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h1.setTitle("自定义彩票1");
			h1.setLotteryAdvert("自定义彩票1");
			HelpStruc h2 = new HelpStruc();
			h2.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h2.setIsRec(0);
			h2.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h2.setTitle("自定义彩票2");
			h2.setLotteryAdvert("自定义彩票2");
			list.add(h1);
			list.add(h2);
			t = (T) list;
		} else if (url.equals(queryCategoryUrl)) {
			List<CateStruc> cateStrucs = new ArrayList<CateStruc>();
			CateStruc c1 = new CateStruc();
			c1.setId(1l);
			c1.setLevel(1l);
			c1.setName("<input id='1'/>id=32323");
			c1.setNumber(1l);

			CateStruc s1 = new CateStruc();
			s1.setId(1l);
			s1.setLevel(2l);
			s1.setName("subtest111");
			s1.setNumber(1l);
			CateStruc s2 = new CateStruc();
			s2.setId(2l);
			s2.setLevel(2l);
			s2.setName("subtest222");
			s2.setNumber(2l);
			CateStruc s3 = new CateStruc();
			s3.setId(3l);
			s3.setLevel(2l);
			s3.setName("subtest333");
			s3.setNumber(3l);
			List<CateStruc> subStruc1 = new ArrayList<CateStruc>();
			subStruc1.add(s1);
			subStruc1.add(s2);
			subStruc1.add(s3);
			c1.setSubCate(subStruc1);
			cateStrucs.add(c1);

			CateStruc c2 = new CateStruc();
			c2.setId(2l);
			c2.setLevel(1l);
			c2.setName("test222");
			c2.setNumber(2l);

			CateStruc s21 = new CateStruc();
			s21.setId(1l);
			s21.setLevel(2l);
			s21.setName("subtest111");
			s21.setNumber(1l);
			CateStruc s22 = new CateStruc();
			s22.setId(2l);
			s22.setLevel(2l);
			s22.setName("subtest222");
			s22.setNumber(2l);
			CateStruc s23 = new CateStruc();
			s23.setId(3l);
			s23.setLevel(2l);
			s23.setName("subtest333");
			s23.setNumber(3l);
			List<CateStruc> subStruc2 = new ArrayList<CateStruc>();
			subStruc2.add(s21);
			subStruc2.add(s22);
			subStruc2.add(s23);
			c2.setSubCate(subStruc2);
			cateStrucs.add(c2);
			t = (T) cateStrucs;
		} else if (url.equals(createLotteryKnowledgeCategory)) {
			LotteryKnowledgeCategoryAddResponse lotteryKnowledge = new LotteryKnowledgeCategoryAddResponse();
			lotteryKnowledge.setId(5l);
			lotteryKnowledge.setName("22ss");
			lotteryKnowledge.setNo(5l);
			t = (T) lotteryKnowledge;
		}
		Response<T> resp = new Response<T>();
		resp.setResult(t);
		return resp;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, Class... dataClass) throws Exception {
		T t = null;
		if (url.equals(queryCategoryUrl)) {
			List<CateStruc> cateList = new ArrayList<CateStruc>();
			CateStruc cate = new CateStruc();
			cate.setId(1l);
			cate.setLevel(1l);
			cate.setName("注册");
			cate.setNumber(1l);
			List<CateStruc> subCate = new ArrayList<CateStruc>();
			CateStruc cate1 = new CateStruc();
			cate1.setId(2l);
			cate1.setLevel(2l);
			cate1.setName("如何注册1");
			cate1.setNumber(2l);
			subCate.add(cate1);
			CateStruc cate2 = new CateStruc();
			cate2.setId(3l);
			cate2.setLevel(2l);
			cate2.setName("如何注册2");
			cate2.setNumber(3l);
			subCate.add(cate2);
			CateStruc cate3 = new CateStruc();
			cate3.setId(4l);
			cate3.setLevel(2l);
			cate3.setName("如何注册3");
			cate3.setNumber(4l);
			subCate.add(cate3);
			cate.setSubCate(subCate);
			cate3.setSubCate(subCate);
			cateList.add(cate);
			cateList.add(cate1);
			cateList.add(cate2);
			cateList.add(cate3);
			t = (T) cateList;
		}

		else if (url.equals(queryHelpDetail)) {
			HelpStruc helpStruc = new HelpStruc();
			helpStruc.setId(1L);
			helpStruc.setTitle("this is help title");
			helpStruc.setContent("this is help detail content");
			helpStruc.setTitle("this is help title");
			helpStruc.setContent("this is help detail content");
			helpStruc
					.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			LotteryContentStruc l1 = new LotteryContentStruc();
			l1.setName("this is test1");
			l1.setContent("his is test1");
			LotteryContentStruc l2 = new LotteryContentStruc();
			l2.setName("this is test2");
			l2.setContent("his is test2");
			LotteryContentStruc l3 = new LotteryContentStruc();
			l3.setName("this is test3");
			l3.setContent("his is test3");
			LotteryContentStruc l4 = new LotteryContentStruc();
			l4.setName("this is test4");
			l4.setContent("his is test4");
			List<LotteryContentStruc> list = new ArrayList<LotteryContentStruc>();
			list.add(l1);
			list.add(l2);
			list.add(l3);
			list.add(l4);
			helpStruc.setLotteryContentStruc(list);
			t = (T) helpStruc;
		} else if (url.equals(queryHotWordUrl)) {
			List<KeywordStruc> keywordList = new ArrayList<KeywordStruc>();
			KeywordStruc keyword1 = new KeywordStruc();
			keyword1.setKeyword("keyword1");
			keyword1.setId(1l);
			keyword1.setNo(3l);
			KeywordStruc keyword2 = new KeywordStruc();
			keyword2.setKeyword("keyword2");
			keyword2.setId(2l);
			keyword2.setNo(2l);
			KeywordStruc keyword3 = new KeywordStruc();
			keyword3.setKeyword("keyword3");
			keyword3.setId(3l);
			keyword3.setNo(1l);
			keywordList.add(keyword1);
			keywordList.add(keyword2);
			keywordList.add(keyword3);
			t = (T) keywordList;
		} else if (url.equals(queryHelp)) {
			List<HelpStruc> list = new ArrayList<HelpStruc>();
			HelpStruc h1 = new HelpStruc();
			h1.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h1.setIsRec(1);
			h1.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h1.setTitle("自定义彩票1");
			h1.setLotteryAdvert("自定义彩票1");
			HelpStruc h2 = new HelpStruc();
			h2.setContent("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h2.setIsRec(0);
			h2.setPreface("用户进入首页(www.firefrog.com),在首页顶部点击“免费注册”,进入用户注册页面； 在并同意电话投注服务协议； 在注册页面填写相关信息并提交； 注册成功,系统返回注册成功信息");
			h2.setTitle("自定义彩票2");
			h2.setLotteryAdvert("自定义彩票2");
			list.add(h1);
			list.add(h2);
			t = (T) list;
		} else if (url.equals(queryLotteryKnowledgeCategory)) {
			List<LotteryCateStruc> lotCateList = new ArrayList<LotteryCateStruc>();
			LotteryCateStruc cate1 = new LotteryCateStruc();
			cate1.setId(1L);
			cate1.setName("玩法介绍");
			cate1.setNo(1L);
			LotteryCateStruc cate2 = new LotteryCateStruc();
			cate2.setId(2L);
			cate2.setName("投注方式");
			cate2.setNo(2L);
			LotteryCateStruc cate3 = new LotteryCateStruc();
			cate3.setId(3L);
			cate3.setName("奖项规则");
			cate3.setNo(3L);
			LotteryCateStruc cate4 = new LotteryCateStruc();
			cate4.setId(4L);
			cate4.setName("购买方式");
			cate4.setNo(1L);
			lotCateList.add(cate1);
			lotCateList.add(cate2);
			lotCateList.add(cate3);
			lotCateList.add(cate4);
			t = (T) lotCateList;
		}

		Response<T> resp = new Response<T>();
		resp.setResult(t);
		return resp;

	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, TypeReference typeReference) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, TypeReference typeReference) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param userId
	* @param userAccount
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, long, java.lang.String, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, long userId, String userAccount,
			TypeReference typeReference) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, TypeReference typeReference)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @param userId
	* @param userAccount
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager, long, java.lang.String, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, long userId, String userAccount,
			TypeReference typeReference) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String) 
	*/
	@Override
	public Response invokeHttpWithoutResultType(String url) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param userId
	* @param userAccount
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, long, java.lang.String) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, long userId, String userAccount) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, Pager pager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @param userId
	* @param userAccount
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager, long, java.lang.String) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, Pager pager, long userId, String userAccount)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <R> String invokeHttpWithStringResult(String url, R requestData) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
