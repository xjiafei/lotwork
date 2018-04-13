package com.winterframework.modules.catalina.session;

import java.io.IOException;

import javax.servlet.http.HttpSession;

public class FirefrogSerializer implements Serializer {
	private JsonMapper jm = JsonMapper.nonEmptyMapper();

	@Override
	public void setClassLoader(ClassLoader loader) {
	}

	@Override
	public byte[] serializeFrom(HttpSession session) throws IOException {
		RedisSession redisSession = (RedisSession) session;

		String bos = jm.toJson(RedisSessionTool.getJsonRedisSesson(redisSession));
		return bos.getBytes("UTF-8");
	}
	

	@Override
	public HttpSession deserializeInto(byte[] data, HttpSession session) throws IOException, ClassNotFoundException {
		JsonRedisSession rs = jm.fromJson(new String(data, "UTF-8"), JsonRedisSession.class);
		RedisSession redisSession = (RedisSession) session;
		
		session=RedisSessionTool.toJsonRedisSesson(rs,redisSession);
		return session;
	}
}
