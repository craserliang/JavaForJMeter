package test;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import constant.ResultState;
import entity.Jmeter_Result;
import gui.ShellSampleGui;
import Util.RemoteShellExecutor;

public class ShellSample extends AbstractSampler{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(ShellSample.class);
	private static final String Ip="172.16.0.78";
    private static final String Username="root";
    private static final String Password="123456";
    private static final String Command="ifconfig";
    private Jmeter_Result resultData;
	
	
	public void setIp(String Ip) {
		setProperty(ShellSample.Ip, Ip);
	}
	
	public void setUsername(String Username) {
		setProperty(ShellSample.Username, Username);
	}
	
	public void setPassword(String Password) {
		setProperty(ShellSample.Password, Password);
	}
	
	public void setCommand(String Command) {
		setProperty(ShellSample.Command, Command);
	}


	public String getIp() {
		return getPropertyAsString(ShellSample.Ip);
	}
	
	public String getUsername() {
		return getPropertyAsString(ShellSample.Username);
	}
	
	public String getPassword() {
		return getPropertyAsString(ShellSample.Password);
	}
	
	public String getCommand() {
		return getPropertyAsString(ShellSample.Command);
	}
	
	//每个线程测试前执行一次，做一些初始化工作；
    public void setupTest(JavaSamplerContext arg0) {
    }
	
	@Override
	public SampleResult sample(Entry arg0) {
		// TODO Auto-generated method stub
		SampleResult sr = new SampleResult();
		sr.setSampleLabel(getName());
		sr.sampleStart();
		sr.setContentType("text/xml; charset=utf-8");
		sr.setDataType(SampleResult.TEXT);
		sr.setDataEncoding("utf-8");
		String Ip = getIp();
		String Username = getUsername();
		String Password = getPassword();
		String Command = getCommand();
		if(Ip==null||Ip==""){
			log.error("Ip is null");
			sr.setSuccessful(false);
			return sr;
		}else if(Username==null||Username==""){
			log.error("Ip is null");
			sr.setSuccessful(false);
			return sr;
		}else if(Password==null||Password==""){
			log.error("Ip is null");
			sr.setSuccessful(false);
			return sr;
		}else if(Command==null||Command==""){
			log.error("Ip is null");
			sr.setSuccessful(false);
			return sr;
		}
		
		try{
			RemoteShellExecutor executor = new RemoteShellExecutor(Ip, Username, Password);
			int ResultStatus=executor.exec(Command);
			resultData=executor.getResult();
			System.out.println(ResultStatus+"$"+resultData.getResult());
			if(ResultStatus==0){
				sr.setResponseCodeOK();
				sr.setResponseMessageOK();
				sr.setResponseData("命令运行结果如下！\n"+resultData.getResult(), "");
				sr.setSuccessful(true);
			}else if(ResultStatus==126){
				sr.setResponseCode(ResultState.NO_PERMISSION+"");
				sr.setResponseMessage("shell脚本无执行权限！");
				sr.setResponseData("shell脚本无执行权限！\n"+resultData.getResult(), "");
				sr.setSuccessful(false);
				
			}else{
				sr.setResponseCode(ResultState.PARAMETER_ERROR+"");
				sr.setResponseMessage("其他错误!");
				sr.setResponseData("其他错误!\n"+resultData.getResult(), "");
				sr.setSuccessful(false);
			}
				
	        
		}catch(Exception e){
			sr.setResponseCode("500");
			sr.setResponseMessage("internal error");
			log.error(e.getMessage());
			sr.setSuccessful(false);
			e.printStackTrace();
		}finally{
			sr.sampleEnd();
		}
		
		return sr;
	}
	
	//测试结束时调用；
    public void teardownTest(JavaSamplerContext arg0) {
        
    }
    

}
