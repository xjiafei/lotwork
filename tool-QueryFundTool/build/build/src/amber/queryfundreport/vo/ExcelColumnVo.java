package amber.queryfundreport.vo;

public class ExcelColumnVo {

	private String fieldName;

	private String javaParamName;
	
	public ExcelColumnVo(){
		
	}

	public ExcelColumnVo(String fieldName,String javaParamName){
		this.fieldName = fieldName;
		this.javaParamName = javaParamName;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getJavaParamName() {
		return javaParamName;
	}

	public void setJavaParamName(String javaParamName) {
		this.javaParamName = javaParamName;
	}

}
