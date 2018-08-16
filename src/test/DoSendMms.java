package test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;
import com.esms.common.util.MediaUtil;

import constant.ResultState;
import entity.Jmeter_Result;

public class DoSendMms {
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String phonehead;
	private int msgCount;
	private String content;
	private String Mediapath;
	private String batchname;
	private int biztype;
	private boolean distinctFlag;
	private PostMsg pm = null;
	private GsmsResponse resp=null;
	private Jmeter_Result JR=new Jmeter_Result();
	
	public DoSendMms(String host,String port,String username,String password,String phonehead,String msgCount,String content
			,String Mediapath,String batchname,int biztype,boolean distinctFlag) {
		// TODO Auto-generated constructor stub
		this.host=host;
		this.port=Integer.parseInt(port);
		this.username=username;
		this.password=password;
		this.phonehead=phonehead;
		this.msgCount=Integer.parseInt(msgCount);
		this.content=content;
		this.Mediapath=Mediapath;
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
	}
	
	// ��չ�ӿڷ���
	public void extend() throws Exception {
		doSendMms(username, password, phonehead, content,msgCount,Mediapath); // ��������

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
	public void doSendMms(String account, String password, String phonehead, String content,int msgCount,String Mediapath) throws Exception {
		
		
		MTPack pack = buildDefaultSMSPack();

		// ���ù���������Դ
//		System.out.println(Mediapath);
		try{
		pack.setMedias(MediaUtil.getMediasFromFolder(Mediapath));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		ArrayList<MessageData> mms = new ArrayList<MessageData>();
		double base=Integer.parseInt(phonehead)*100000000.00;
		for(int i = 0; i < msgCount; i ++){
			mms.add(new MessageData(new BigDecimal(base+i)+"", ((msgCount>1) ? content : content + i)));
		}
		/** Ⱥ���������һ���� */
		pack.setMsgs(mms);
		
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
		return buildMtPack(batchname, MsgType.MMS, SendType.MASS, biztype, distinctFlag);
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
