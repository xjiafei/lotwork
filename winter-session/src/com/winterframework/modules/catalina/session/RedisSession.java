package com.winterframework.modules.catalina.session;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Manager;
import org.apache.catalina.session.StandardSession;

public class RedisSession extends StandardSession {
	private SessionpPhpHead zf;
	protected static Boolean manualDirtyTrackingSupportEnabled = false;

	public static void setManualDirtyTrackingSupportEnabled(Boolean enabled) {
		manualDirtyTrackingSupportEnabled = enabled;
	}

	protected static String manualDirtyTrackingAttributeKey = "__changed__";

	public static void setManualDirtyTrackingAttributeKey(String key) {
		manualDirtyTrackingAttributeKey = key;
	}

	public SessionpPhpHead getZf() {
		return zf;
	}

	public void setZf(SessionpPhpHead zf) {
		this.zf = zf;
	}

	protected HashMap<String, Object> changedAttributes;
	protected Boolean dirty;

	public RedisSession(Manager manager) {
		super(manager);
		resetDirtyTracking();
	}

	public Boolean isDirty() {
		return dirty || !changedAttributes.isEmpty();
	}

	public HashMap<String, Object> getChangedAttributes() {
		return changedAttributes;
	}

	public Map<String, Object> getAttribute() {
		return this.attributes;
	}

	public void resetDirtyTracking() {
		changedAttributes = new HashMap<String, Object>();
		dirty = false;
	}

	@Override
	public void setAttribute(String key, Object value) {
		if (manualDirtyTrackingSupportEnabled && manualDirtyTrackingAttributeKey.equals(key)) {
			dirty = true;
			return;
		}
		Object oldValue = getAttribute(key);
		if ((value != null || oldValue != null)
				&& (value == null && oldValue != null || oldValue == null && value != null
						|| !value.getClass().isInstance(oldValue) || !value.equals(oldValue))) {
			changedAttributes.put(key, value);

		}

		//如果是对象，则json化
		super.setAttribute(key, (value));

	}

	@Override
	public void removeAttribute(String name) {
		dirty = true;
		super.removeAttribute(name);
	}

	@Override
	public void setId(String id) {
		// Specifically do not call super(): it's implementation does unexpected things
		// like calling manager.remove(session.id) and manager.add(session).

		this.id = id;
	}

	@Override
	public void setPrincipal(Principal principal) {
		dirty = true;
		super.setPrincipal(principal);
	}

	public void setLastAccessedTime(Long time) {
		this.lastAccessedTime = time;
	};

	public void setPhpEnt(){
		if(getZf()==null){
			this.setZf(new SessionpPhpHead());
		}
		getZf().getDatas().put("ENT", System.currentTimeMillis() / 1000 + getMaxInactiveInterval());
		this.dirty=true;
	};

	public void setThisAccessedTime(Long time) {
		this.thisAccessedTime = time;
	};

}
