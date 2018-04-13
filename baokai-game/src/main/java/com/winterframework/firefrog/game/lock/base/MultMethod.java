package com.winterframework.firefrog.game.lock.base;

import java.util.List;

public abstract class MultMethod extends Method{
	public abstract List<MethodPackage> toSingleMethod(String betContent);
}
