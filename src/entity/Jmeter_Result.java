package entity;

public class Jmeter_Result {
	private int status;
//	private String result;
	private StringBuilder resultBuilder;
	
	public Jmeter_Result(){
		
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResult() {
		return resultBuilder.toString();
	}
	public void setResult(String result) {
		resultBuilder=new StringBuilder();
		this.resultBuilder.append(result+"\n");
	}
	
	public void AddResult(String rs){
		if (rs!=null){
			this.resultBuilder.append(rs+"\n");
		}
	}

}
