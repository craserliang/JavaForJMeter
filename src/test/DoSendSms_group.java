package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.junit.Test;

import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;
import com.esms.common.entity.TemplateData;

import constant.ResultState;
import entity.Jmeter_Result;
import Util.CsvReader;

public class DoSendSms_group {
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String phonehead;
	private int msgCount;
//	private static String content;
	private String path;
	private String TemplateNo;
	private String batchname;
	private int biztype;
	private boolean distinctFlag;
	private PostMsg pm = null;
	private GsmsResponse resp=null;
	private Jmeter_Result JR=new Jmeter_Result();
	
	public DoSendSms_group(String host,String port,String username,String password,String phonehead,String path,
			String TemplateNo,String batchname,int biztype,boolean distinctFlag) {
		// TODO Auto-generated constructor stub
		this.host=host;
		this.port=Integer.parseInt(port);
		this.username=username;
		this.password=password;
		this.phonehead=phonehead;
		this.path=path;
//		this.msgCount=Integer.parseInt(msgCount);
//		this.content=content;
		this.TemplateNo=TemplateNo;
		this.batchname=batchname;
		this.biztype=biztype;
		this.distinctFlag=distinctFlag;
		this.pm=new PostMsg(this.host, this.port);
		
	}
	
		
//	private static PostMsg pm = new PostMsg("172.16.0.78", 8090);// ������ ���鵥������
	
	
	@Test
	public void test() {
		try {
			extend(); // ��չ�ӿڷ���
		} catch (Exception e) {
			String errorStr="������Ϣ���£�";
			JR.setResult(errorStr);
			JR.setStatus(ResultState.SERVER_ERROR);
			e.printStackTrace();
			JR.AddResult(e.getMessage());
		}
		String result="����ɹ���״̬�������£�";
		JR.setResult(result);
		JR.setStatus(ResultState.SUCCESS);
		JR.AddResult(resp.toString());
		JR.AddResult("�鷢����������"+msgCount+"��");
	}
	
	// ��չ�ӿڷ���
	public void extend() throws Exception {
		doSendSms(username, password, phonehead, path,TemplateNo); // ��������

	}
		
		
	/**
	 * �����·�����
	 * 
	 * @param account
	 *            �����˺�
	 * @param password
	 *            ��������
	 * @param phone
	 *            ���շ��ֻ���
	 * @param content
	 *            ��������
	 */
	public void doSendSms(String account, String password, String phonehead, String path,String TemplateNo) throws Exception {
		MTPack pack = buildDefaultSMSPack();
//		pack.addMsg(MessageData.getInstance(phone, content));

		
		
		//�鷢
		CsvReader CR=new CsvReader(path);
		CR.DoRaedcsv();
		double base=Integer.parseInt(phonehead)*100000000.00;
		//ѭ����ȡcsv�ļ�����������ȥ1Ϊ���ݵ�������������0��ʼ���ټ�ȥ1
		try{
		for (int i = 0; i < (CR.getLineCount()-2); i++) {
			Map<String, TemplateData> templateValue=null;
			templateValue = new HashMap<String, TemplateData>();
			//��ȡÿһ�еı���
			for (int j = 0; j < CR.getTitle().length; j++) {
				templateValue.put(CR.getTitle()[j], new TemplateData(CR.getContent().get(i)[j]));
//				System.out.println("tile:"+CR.getTitle()[j]);
//				System.out.println(CR.getContent().get(i)[j]);
			}
			pack.addMsg(MessageData.getTemplateInstance(new BigDecimal(base+i)+"", templateValue));
		}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		this.msgCount=CR.getLineCount()-2;
		pack.setTemplateNo(TemplateNo);
		//�鷢
		
		/**
		 * ����ģ����(��̬ģ�彫��ģ�����ݷ���;
		 * ��̬ģ����Ҫ���ͱ���ֵ��JSON��ʽ��[{"key":"������1","value":"����ֵ1"},{"key":"������2","
		 * value":"����ֵ2"}])
		 */
		Account ac = new Account(account, password);
		resp = pm.post(ac, pack);
//		System.out.println(resp);
	}	
	
	
	/**
	 * ����Ĭ���������� ��Ϣ���ͣ����ţ� ���ͷ�ʽ��Ⱥ���� ҵ�����ͣ�0�� �Ƿ�ȥ�أ���
	 */
	private MTPack buildDefaultSMSPack() {
		return buildMtPack(batchname, MsgType.SMS, SendType.GROUP, biztype, distinctFlag);
	}
	
	
	/**
	 * @param batchName
	 *            ��������
	 * @param msgType
	 *            ��Ϣ����
	 * @param sendType
	 *            ���ͷ�ʽ
	 * @param biztype
	 *            ҵ�����͸����Լ���ҵ�����ͱ������
	 * @param distinctFlag
	 *            �Ƿ�ȥ��
	 */
	private MTPack buildMtPack(String batchName, MsgType msgType, SendType sendType, int biztype,
			boolean distinctFlag) {
		MTPack pack = new MTPack();
		pack.setBatchID(UUID.randomUUID());
		pack.setBatchName(batchName);
		pack.setMsgType(msgType);
		pack.setBizType(biztype);
		pack.setDistinctFlag(distinctFlag);
		pack.setSendType(sendType);
		List<MessageData> msgs = new ArrayList<>();
		pack.setMsgs(msgs);
		return pack;
	}
	
	public Jmeter_Result getResult(){
		return JR;
	}
	

		
}
