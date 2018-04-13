package com.winterframework.firefrog.global.entity;

public class SensitiveWord {

	private Long id;

	private Type type;

	private String word;

	public enum Type {
		register(0L, "注册"), adv(1L, "广告"), help(2L, "帮助"), message(3L, "消息"),
        comment(4L, "评论"), service(5L, "客服"), all(null, "全部"), any(null, "选择全部");
        private Long value;
        private String description;
        Type(Long value, String description) {
            this.value = value;
            this.description = description;
        }
        public Long getVal() {
            return this.value;
        }
        public String getDescription() {
            return this.description;
        }
        public static Type getType(Long type){
            for (Type ty : Type.values()) {
                if (ty.getVal() == type) {
                    return ty;
                }
            }
            throw new IllegalArgumentException("Bad type: " + type);
        }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
