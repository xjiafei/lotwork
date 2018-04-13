package amber.genbatis.vo;

public class TableColumnVO {

	private String name;
	private String targetName;
	private String type;
	private Integer size;
	private Integer scale;
	private boolean isDate;

	public TableColumnVO() {

	}

	public TableColumnVO(String name, String type, Integer size, Integer scale) {
		super();
		this.name = name;
		this.type = type;
		this.size = size;
		this.scale = scale;
		isDate = "DATE".equals(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public boolean isDate() {
		return isDate;
	}

}
