package test;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import constant.ResultState;
import entity.Jmeter_Result;
import test.DoSendSms_group;


public class Sms_Test_group extends AbstractJavaSamplerClient {

	private String host;
    private String port;
    private String username;
    private String password;
    private String phonehead;
    private String path;
    private String TemplateNo;
    private String batchname;
    private int biztype;
    private boolean distinctFlag;
    
    /** Holds the result data (shown as Response Data in the Tree display). */
    private Jmeter_Result resultData;

    // ��������������Զ���java������εġ�
    // params.addArgument("p1","");��ʾ������ֽ�num1��Ĭ��ֵΪ�ա�
    //���ÿ��ò�������Ĭ��ֵ��
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("host", "");
        params.addArgument("port", "");
        params.addArgument("username", "");
        params.addArgument("password", "");
        params.addArgument("phonehead", "");
        params.addArgument("path", "");
        params.addArgument("TemplateNo", "");
        params.addArgument("batchname", "");
        params.addArgument("biztype", "0");
        params.addArgument("distinctFlag", "false");
        return params;
    }

    //ÿ���̲߳���ǰִ��һ�Σ���һЩ��ʼ��������
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
		path=arg0.getParameter("path");
		TemplateNo=arg0.getParameter("TemplateNo");
		batchname=arg0.getParameter("batchname");
		biztype=Integer.parseInt(arg0.getParameter("biztype"));
		if (arg0.getParameter("distinctFlag").toLowerCase().equals("true")){
			distinctFlag=true;
		}else{
			distinctFlag=false;
		}
		
		SampleResult sr = new SampleResult();
		sr.setSampleLabel( "Java������sms");
		try {
            sr.sampleStart();// jmeter ��ʼͳ����Ӧʱ����
            DoSendSms_group test = new DoSendSms_group(host,port,username,password,phonehead,path,TemplateNo,batchname,biztype,distinctFlag);
            // ͨ������Ĳ����Ϳ��Խ����ⷽ������Ӧ�����Jmeter�Ĳ쿴������е���Ӧ���������ˡ�
            test.test();
            resultData = test.getResult();
            if (resultData != null && resultData.getStatus() == 200) {
                sr.setResponseData("��Ӧ�����\n"+resultData.getResult(), null);
                sr.setResponseCode(resultData.getStatus()+"");
                sr.setDataType(SampleResult.TEXT);
                sr.setSuccessful(true);
            }else{
            	sr.setResponseData("��Ӧ�����\n"+resultData.getResult(), null);
                sr.setResponseCode(resultData.getStatus()+"");
                sr.setDataType(SampleResult.TEXT);
            	sr.setSuccessful(false);
            }
        } catch (Throwable e) {
        	sr.setResponseData("����������Ϣ���£�\n"+e.getMessage(), null);
        	sr.setResponseCode(ResultState.SERVER_ERROR+"");
            sr.setSuccessful(false);
            e.printStackTrace();
        } finally {
            sr.sampleEnd();// jmeter ����ͳ����Ӧʱ����
        }
        return sr;
	}
	
	
	//���Խ���ʱ���ã�
    public void teardownTest(JavaSamplerContext arg0) {
        // System.out.println(end);
        // System.out.println("The cost is"+(end-start)/1000);
    }
    
    
 // mainֻ��Ϊ�˵����ã�����jar����ʱ��ע�͵���
    
//      public static void main(String[] args) 
//      { // TODO Auto-generated method stub
//    	  Arguments params = new Arguments(); 
//          params.addArgument("host", "172.16.0.78");//���ò���
//          params.addArgument("port", "8090");//���ò���
//          params.addArgument("username", "craser");//���ò���
//          params.addArgument("password", "123456");//���ò���
//          params.addArgument("phonehead", "159");//���ò���
//          params.addArgument("path", "F:\\��ʱ�ļ�\\��־��¼�ļ�\\�鷢ģ��.csv");//���ò���
//          params.addArgument("TemplateNo", "craser1");//���ò���
//          params.addArgument("batchname", "��������2");//���ò���
//          params.addArgument("biztype", "0");//���ò���
//          params.addArgument("distinctFlag", "false");//���ò���
//          JavaSamplerContext arg0 = new JavaSamplerContext(params); 
//          Sms_Test_group a=new Sms_Test_group();
//          a.setupTest(arg0);
//          a.runTest(arg0);
//          a.teardownTest(arg0);
//      }
//     

}
