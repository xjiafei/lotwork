package com.winterframework.modules.catalina.session;

import java.util.Calendar;
import java.util.Map;

public class RedisSessionTool {
	public static JsonRedisSession getJsonRedisSesson(RedisSession js) {
		JsonRedisSession rs = new JsonRedisSession();	
		if (js.getZf() == null) {
			Calendar cd = Calendar.getInstance();
			cd.add(Calendar.HOUR, 1);
			SessionpPhpHead zf = new SessionpPhpHead();
			zf.getDatas().put("ENT", cd.getTime().getTime());
			rs.set__ZF(zf);
		} else{
			rs.set__ZF(js.getZf());
		}
		SessionJavaHead java = new SessionJavaHead();
		java.setAuthType(null);
		java.setCreationTime(js.getCreationTime());
		java.setId(js.getId());
		java.setLastAccessedTime(js.getLastAccessedTime());
		java.setMaxInactiveInterval(js.getMaxInactiveInterval());
		java.setNew(js.isNew());
		java.setPrincipal(null);
		java.setThisAccessedTime(js.getThisAccessedTime());
		java.setValid(js.isValid());
		//rs.set__JAVA(java);
		js.setAttribute("__JAVA", java);
		rs.setDatas(js.getAttribute());
		return rs;
	};

	public static RedisSession toJsonRedisSesson(JsonRedisSession jss, RedisSession rs) {

		rs.setZf(jss.get__ZF());
		rs.setValid(true);
		if (jss.getDatas() != null) {
			for (String str : jss.getDatas().keySet()) {
				if (!"__JAVA".equals(str)) {
					//
					rs.setAttribute(str, jss.getDatas().get(str));
				} else {
					SessionJavaHead js = JsonMapper.nonEmptyMapper().fromMapToObject((Map)jss.getDatas().get("__JAVA"),SessionJavaHead.class);					
					if (js != null) {
						rs.setNew(js.isNew());
						rs.setAuthType(null);
						rs.setCreationTime(js.getCreationTime());
						rs.setId(js.getId());
						rs.setMaxInactiveInterval(js.getMaxInactiveInterval());
						rs.setPrincipal(null);
					}
				}
			}

		}
		return rs;

	}
	public static void main(String[] args) throws Exception{
		String ss="{\"__ZF\":{\"datas\":{\"ENT\":1417076079}},\"datas\":{\"__JAVA\":{\"id\":\"buimnpcg0hlgclva6vs7jhg000\",\"creationTime\":1411892032790,\"lastAccessedTime\":1411892032790,\"maxInactiveInterval\":5184000,\"thisAccessedTime\":1411892079082,\"valid\":true,\"new\":false},\"code\":\"DRWC\",\"info\":{\"account\":\"henry13\",\"id\":794,\"userLvl\":1,\"vipLvl\":0,\"userChain\":\"/admin/henry13/\",\"serialNumber\":null,\"passwd\":\"96e79218965eb72c92a549dd5a330112\",\"passwdLvl\":1,\"isFreeze\":0,\"withdrawPasswd\":\"e10adc3949ba59abbe56e057f20f883e\",\"cipher\":null,\"freezer\":null,\"freezeDate\":0,\"freezeMemo\":null,\"sex\":0,\"email\":\"1714312029@qq.com\",\"emailActived\":1,\"cellphone\":\"13245678901\",\"birthday\":0,\"qqStruc\":null,\"quStruc\":[{\"qu\":1,\"ans\":\"125be71452b657e5f812e335f94263a5\"},{\"qu\":3,\"ans\":\"125be71452b657e5f812e335f94263a5\"},{\"qu\":5,\"ans\":\"125be71452b657e5f812e335f94263a5\"}],\"parentId\":31,\"registerDate\":1402561578904,\"registerIp\":3232261230,\"lastLoginDate\":1411892047348,\"teamACount\":2,\"teamUCount\":0,\"vipCellphone\":null,\"freezeMethod\":0,\"agentlimit\":null,\"freezeAccount\":null,\"registerAddress\":\"xe5xb1x80xe5x9fx9fxe7xbdx91 xe5xafxb9xe6x96xb9xe5x92x8cxe6x82xa8xe5x9cxa8xe5x90x8cxe4xb8x80xe5x86x85xe9x83xa8xe7xbdx91\",\"questionStructureActiveDate\":0,\"loginGameGroups\":null,\"loginCtrl\":{\"reregister\":3,\"overTime\":1440,\"multLogin\":1},\"subUsers\":null,\"availBal\":169384819796,\"canWithdrawBal\":169384819796,\"freezeBal\":384883710000,\"teamBal\":null,\"parentAccount\":\"admin\"},\"isLogin\":1,\"loginIp\":168166142,\"initialized\":true}}}";
		new FirefrogSerializer().deserializeInto(ss.getBytes(),null);
	}
}
