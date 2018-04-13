package amber.queryfundreport;

public interface QueryStatusListener {

	public void updateProgress(int total,int done);
	
	public void doLog(String logStr);
}
