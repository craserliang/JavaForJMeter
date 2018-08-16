package test;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import entity.Jmeter_Result;
import test.DoSendSms;
import constant.ResultState;


public class Sms_Test extends AbstractJavaSamplerClient {

	private String host;
    private String port;
    private String username;
    private String password;
    private String phonehead;
    private String msgCount;
    private String content;
    private String isMass;
    private String batchname;
    private int biztype;
    private boolean distinctFlag;
    
    /** Holds the result data (shown as Response Data in the Tree display). */
    private Jmeter_Result resultData;

    // 这个方法是用来自定义java方法入参的。
    // params.addArgument("p1","");表示入参名字叫num1，默认值为空。
    //设置可用参数及的默认值；
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("host", "");
        params.addArgument("port", "");
        params.addArgument("username", "");
        params.addArgument("password", "");
        params.addArgument("phonehead", "");
        params.addArgument("msgCount", "");
        params.addArgument("content", "");
        params.addArgument("isMass", "true");
        params.addArgument("batchname", "");
        params.addArgument("biztype", "0");
        params.addArgument("distinctFlag", "false");
        return params;
    }

    //每个线程测试前执行一次，做一些初始化工作；
    public void setupTest(JavaSamplerContext arg0) {
    }
	
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		host=arg0.getParameter("host");
//		System.out.println("host:"+host);
		port=arg0.getParameter("port");
		username=arg0.getParameter("username");
		password=arg0.getParameter("password");
		phonehead=arg0.getParameter("phonehead");
		msgCount=arg0.getParameter("msgCount");
		content=arg0.getParameter("content");
		isMass=arg0.getParameter("isMass");
		batchname=arg0.getParameter("batchname");
		biztype=Integer.parseInt(arg0.getParameter("biztype"));
		if (arg0.getParameter("distinctFlag").toLowerCase().equals("true")){
			distinctFlag=true;
		}else{
			distinctFlag=false;
		}
		
		SampleResult sr = new SampleResult();
		sr.setSampleLabel( "Java请求发送sms");
		try {
            sr.sampleStart();// jmeter 开始统计响应时间标记
            DoSendSms test = new DoSendSms(host,port,username,password,phonehead,msgCount,content,isMass,batchname,biztype,distinctFlag);
            // 通过下面的操作就可以将被测方法的响应输出到Jmeter的察看结果树中的响应数据里面了。
            test.test();
            resultData = test.getResult();
            if (resultData != null && resultData.getStatus() == 200 && resultData.getResult()!=null) {
                sr.setResponseData("响应结果：\n"+resultData.getResult(), null);
                sr.setResponseCode(resultData.getStatus()+"");
                sr.setDataType(SampleResult.TEXT);
                sr.setSuccessful(true);
            }else{
            	sr.setResponseData("响应结果：\n"+resultData.getResult(), null);
                sr.setResponseCode(resultData.getStatus()+"");
                sr.setDataType(SampleResult.TEXT);
            	sr.setSuccessful(false);
            }

        } catch (Throwable e) {
        	sr.setResponseData("报错！报错信息如下：\n"+e.getMessage(), null);
        	sr.setResponseCode(ResultState.SERVER_ERROR+"");
            sr.setSuccessful(false);
            e.printStackTrace();
        } finally {
            sr.sampleEnd();// jmeter 结束统计响应时间标记
        }
        return sr;
	}
	
	
	//测试结束时调用；
    public void teardownTest(JavaSamplerContext arg0) {
        // System.out.println(end);
        // System.out.println("The cost is"+(end-start)/1000);
    }
    
    
 // main只是为了调试用，最后打jar包的时候注释掉。
    
//      public static void main(String[] args) 
//      { // TODO Auto-generated method stub
//    	  Arguments params = new Arguments(); 
//          params.addArgument("host", "172.16.0.78");//设置参数
//          params.addArgument("port", "8090");//设置参数
//          params.addArgument("username", "admin");//设置参数
//          params.addArgument("password", "123456");//设置参数
//          params.addArgument("phonehead", "189");//设置参数
//			params.addArgument("msgCount", "1");//设置参数
//          params.addArgument("content", "辨认");//设置参数
//          params.addArgument("isMass", "false");//设置参数
//          params.addArgument("batchname", "");//设置参数
//          params.addArgument("biztype", "0");//设置参数
//          params.addArgument("distinctFlag", "false");//设置参数
//          JavaSamplerContext arg0 = new JavaSamplerContext(params); 
//          Sms_Test a=new Sms_Test();
//          a.setupTest(arg0);
//          a.runTest(arg0);
//          a.teardownTest(arg0);
//      }
     

}
