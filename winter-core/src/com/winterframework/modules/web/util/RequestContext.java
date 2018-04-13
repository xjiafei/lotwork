package com.winterframework.modules.web.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartRequest;

import com.winterframework.modules.security.CryptUtils;

/** 
 * 请求上下文 
 * @author abba
 * @date 2010-6-8 下午04:18:00 
 */
@SuppressWarnings("unused")
public class RequestContext {

	private final static Logger log = LoggerFactory.getLogger(RequestContext.class);
	private final static int MAX_FILE_SIZE = 10 * 1024 * 1024;
	private final static String UTF_8 = "UTF-8";
	private final static ThreadLocal<RequestContext> contexts = new ThreadLocal<RequestContext>() {
		{
		}
	};
	private final static String upload_tmp_path;
	private final static String TEMP_UPLOAD_PATH_ATTR_NAME = "$htREADER_TEMP_UPLOAD_PATH$";
	private static String webroot = null;
	private ServletContext context;
	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String mainCookieName;
	private Map<String, Cookie> cookies;
	public IUser iuser;
	static {
		webroot = getWebrootPath();
		//上传的临时目录  
		upload_tmp_path = webroot + "WEB-INF" + File.separator + "tmp" + File.separator;
		try {
			FileUtils.forceMkdir(new File(upload_tmp_path));
		} catch (IOException excp) {
		}

	}

	private final static String getWebrootPath() {
		String root = RequestContext.class.getResource("/").getFile();
		try {
			root = new File(root).getParentFile().getParentFile().getCanonicalPath();
			root += File.separator;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return root;
	}

	/** 
	 * 初始化请求上下文 
	 * @param ctx 
	 * @param req 
	 * @param res 
	 */
	public static RequestContext begin(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) {
		return begin(ctx, req, res, DEFAULT_COOKIE_LOGIN);
	}
	public static RequestContext begin(ServletContext ctx, HttpServletRequest req, HttpServletResponse res,
			String mainCookieName) {
		RequestContext rc = new RequestContext();
		rc.setMainCookieName(mainCookieName);
		rc.context = ctx;
		rc.request = req;
		rc.response = res;
		rc.response.setCharacterEncoding(UTF_8);
		rc.session = req.getSession(false);
		rc.cookies = new HashMap<String, Cookie>();
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie ck : cookies) {
				rc.cookies.put(ck.getName(), ck);
			}
		contexts.set(rc);
		return rc;
	}

	/** 
	 * 返回Web应用的路径 
	 * @return 
	 */
	public static String root() {
		return webroot;
	}

	/** 
	 * 获取当前请求的上下文 
	 * @return 
	 */
	public static RequestContext get() {
		return contexts.get();
	}

	public static IUser getCurrUser() {

		IUser user = null;
		if (contexts.get() != null) {
			if(contexts.get().iuser!=null){
				user = contexts.get().iuser;
			}else{
				user = contexts.get().getUserFromCookie();
			}
		}
		if (user == null) {
			return new IUser() {
				@Override
				public boolean IsBlocked() {
					return false;
				}

				@Override
				public Long getId() {
					return 0L;
				}

				@Override
				public String getUserName() {
					return "passenger";
				}

				public byte getRole() {
					return IUser.ROLE_GENERAL;
				}
			};
		} else {
			return user;
		}
	}

	public void end() {
		String tmpPath = (String) request.getAttribute(TEMP_UPLOAD_PATH_ATTR_NAME);
		if (tmpPath != null) {
			try {
				FileUtils.deleteDirectory(new File(tmpPath));
			} catch (IOException e) {
				log.error("Failed to cleanup upload directory: " + tmpPath, e);
			}
		}
		this.context = null;
		this.request = null;
		this.response = null;
		this.session = null;
		this.cookies = null;
		contexts.remove();
	}

	public Locale locale() {
		return request.getLocale();
	}

	public void closeCache() {
		header("Pragma", "No-cache");
		header("Cache-Control", "no-cache");
		header("Expires", 0L);
	}

	public long id() {
		return param("id", 0L);
	}

	public String ip() {
		return RequestUtils.getRemoteAddr(request);
	}

	@SuppressWarnings("unchecked")
	public Enumeration<String> params() {
		return request.getParameterNames();
	}

	public String param(String name, String... def_value) {
		String v = request.getParameter(name);
		return (v != null) ? v : ((def_value.length > 0) ? def_value[0] : null);
	}

	public long param(String name, long def_value) {
		return NumberUtils.toLong(param(name), def_value);
	}

	public int param(String name, int def_value) {
		return NumberUtils.toInt(param(name), def_value);
	}

	public byte param(String name, byte def_value) {
		return (byte) NumberUtils.toInt(param(name), def_value);
	}

	public String[] params(String name) {
		return request.getParameterValues(name);
	}

	public long[] lparams(String name) {
		String[] values = params(name);
		if (values == null)
			return null;
		return (long[]) ConvertUtils.convert(values, long.class);
	}

	public String uri() {
		return request.getRequestURI();
	}

	public String contextPath() {
		return request.getContextPath();
	}

	public void redirect(String uri) throws IOException {
		response.sendRedirect(uri);
	}

	public void forward(String uri) throws ServletException, IOException {
		RequestDispatcher rd = context.getRequestDispatcher(uri);
		rd.forward(request, response);
	}

	public void include(String uri) throws ServletException, IOException {
		RequestDispatcher rd = context.getRequestDispatcher(uri);
		rd.include(request, response);
	}

	public boolean isUpload() {
		return (request instanceof MultipartRequest);
	}

	public boolean isRobot() {
		return RequestUtils.isRobot(request);
	}

	/** 
	 * 输出信息到浏览器 
	 * @param msg 
	 * @throws IOException 
	 */
	public void print(Object msg) throws IOException {
		if (!UTF_8.equalsIgnoreCase(response.getCharacterEncoding()))
			response.setCharacterEncoding(UTF_8);
		response.getWriter().print(msg);
	}

	public void output_json(String[] key, Object[] value) throws IOException {
		StringBuilder json = new StringBuilder("{");
		for (int i = 0; i < key.length; i++) {
			if (i > 0)
				json.append(',');
			boolean isNum = value[i] instanceof Number;
			json.append("\"");
			json.append(key[i]);
			json.append("\":");
			if (!isNum)
				json.append("\"");
			json.append(value[i]);
			if (!isNum)
				json.append("\"");
		}
		json.append("}");
		print(json.toString());
	}

	public void output_json(String key, Object value) throws IOException {
		output_json(new String[] { key }, new Object[] { value });
	}

	public void error(int code, String... msg) throws IOException {
		if (msg.length > 0)
			response.sendError(code, msg[0]);
		else
			response.sendError(code);
	}

	public void forbidden() throws IOException {
		error(HttpServletResponse.SC_FORBIDDEN);
	}

	public void not_found() throws IOException {
		error(HttpServletResponse.SC_NOT_FOUND);
	}

	public ServletContext context() {
		return context;
	}

	public HttpSession session() {
		return session;
	}

	public HttpSession session(boolean create) {
		return (session == null && create) ? (session = request.getSession()) : session;
	}

	public Object sessionAttr(String attr) {
		HttpSession ssn = session();
		return (ssn != null) ? ssn.getAttribute(attr) : null;
	}

	public HttpServletRequest request() {
		return request;
	}

	public HttpServletResponse response() {
		return response;
	}

	public Cookie cookie(String name) {
		return cookies.get(name);
	}

	public void cookie(String name, String value, int max_age, boolean all_sub_domain) {
		RequestUtils.setCookie(request, response, name, value, max_age, all_sub_domain);
	}

	public static void main(String[] args) throws Exception {
		String sb = "22|user_test_1|-147789545|1389331690839";
		String keyss = Base64.encodeBase64String(sb.toString().getBytes("UTF-8"));
		Cookie cookie = new Cookie("a", keyss);
		System.out.println("a");
		//String cookie="MjJ8dXNlcl90ZXN0XzF8LTIwNDQwNjc4MzZ8MTM4OTMyMTUxNjQ4OQ==";
		//RequestContext rc=new RequestContext();
		//src.cookie("aaa", cookie, 1, true);
	}

	public void deleteCookie(String name, boolean all_domain) {
		RequestUtils.deleteCookie(request, response, name, all_domain);
	}

	public String header(String name) {
		return request.getHeader(name);
	}

	public void header(String name, String value) {
		response.setHeader(name, value);
	}

	public void header(String name, int value) {
		response.setIntHeader(name, value);
	}

	public void header(String name, long value) {
		response.setDateHeader(name, value);
	}

	/** 
	 * 返回当前登录的用户资料 
	 * @return 
	 */
	public IUser user() {
		return null;
	}

	/** 
	 * 保存登录信息 
	 * @param req 
	 * @param res 
	 * @param user 
	 * @param save 
	 */
	public void saveUserInCookie(IUser user, boolean save) throws Exception {
		String new_value = _GenLoginKey(user, ip(), header("user-agent"));
		int max_age = save ? MAX_AGE : -1;
		deleteCookie(getCookieName(), true);
		cookie(getCookieName(), new_value, max_age, true);
	}

	public void deleteUserInCookie() {
		deleteCookie(getCookieName(), true);
	}

	/** 
	 * 生成用户登录标识字符串 
	 * @param user 
	 * @param ip 
	 * @param user_agent 
	 * @return 
	 */
	public static String _GenLoginKey(IUser user, String ip, String user_agent) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(user.getId());
		sb.append('|');
		sb.append(user.getUserName());
		//sb.append('|');
		//sb.append(ip);
		sb.append('|');
		sb.append((user_agent == null) ? 0 : user_agent.hashCode());
		sb.append('|');
		sb.append(System.currentTimeMillis());
		return Base64.encodeBase64String(sb.toString().getBytes("UTF-8"));
	}

	/** 
	 * 加密 
	 * @param value 
	 * @return  
	 * @throws Exception  
	 */
	private static String _Encrypt(String value) {
		String data = CryptUtils.desEncryptToBase64(value, E_KEY);
		try {
			return URLEncoder.encode(data, UTF_8);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/** 
	 * 解密 
	 * @param value 
	 * @return 
	 * @throws Exception  
	 */
	private static String _Decrypt(String value) {
		try {
			value = URLDecoder.decode(value, UTF_8);
			if (StringUtils.isBlank(value))
				return null;
			return new String(CryptUtils.desDecryptFromBase64(value, E_KEY));
		} catch (UnsupportedEncodingException excp) {
			return null;
		}
	}

	/** 
	 * 从cookie中读取保存的用户信息 
	 * @param req 
	 * @return 
	 */
	public IUser getUserFromCookie() {
		try {
			String cookieName = this.getMainCookieName();
			final Cookie cookie = cookie(this.getCookieName());
			return userFromUUID(cookie.getValue());

		} catch (Exception e) {
		}
		return null;
	}

	/** 
	 * 从cookie中读取保存的用户信息 
	 * @param req 
	 * @return 
	 */
	public IUser getUserFromCookie(String cookie_ID) {
		try {
			final Cookie cookie = cookie(cookie_ID);
			return userFromUUID(cookie.getValue());

		} catch (Exception e) {
		}
		return null;
	}

	/** 
	 * 从cookie中读取保存的用户信息 
	 * @param req 
	 * @return 
	 */
	public IUser userFromUUID(String uuid) throws Exception {
		if (StringUtils.isBlank(uuid))
			return null;
		uuid = URLDecoder.decode(uuid, "UTF-8");
		//String ck = _Decrypt(uuid);
		String ck = new String(org.apache.commons.codec.binary.Base64.decodeBase64(uuid), "UTF-8");
		final String[] items = StringUtils.split(ck, '|');
		if (items.length >= 2) {
			String ua = header("user-agent");
			int ua_code = (ua == null) ? 0 : ua.hashCode();
			int old_ua_code = (items[3].hashCode());
			if (true || ua_code == old_ua_code) {
				return new IUser() {
					@Override
					public boolean IsBlocked() {
						return false;
					}

					@Override
					public Long getId() {
						return NumberUtils.toLong(items[0], -1L);
					}

					@Override
					public String getUserName() {
						return items[1];
					}

					public byte getRole() {
						return IUser.ROLE_GENERAL;
					}
				};
			}
		}
		return null;
	}

	public String getMainCookieName() {
		return mainCookieName;
	}

	public void setMainCookieName(String mainCookieName) {
		this.mainCookieName = mainCookieName;
	}

	public String getCookieName() {
		return mainCookieName == null ? DEFAULT_COOKIE_LOGIN : mainCookieName;
	}
	


	public static void setIUer(IUser iuser) {
		if(contexts.get()!=null){
			contexts.get().iuser = iuser;
		}
	}

	public final static String DEFAULT_COOKIE_LOGIN = "_frcid";
	private final static int MAX_AGE = 86400 * 365;
	private final static byte[] E_KEY = new byte[] { '1', '2', '3', '4', '5', '6', '7', '8' };
}