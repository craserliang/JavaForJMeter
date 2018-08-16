package test;

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

import constant.ResultState;
import entity.Jmeter_Result;

public class DoSendSms {
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String phonehead;
	private int msgCount;
	private String content;
	private String isMass;
	private String batchname;
	private int biztype;
	private boolean distinctFlag;
	private PostMsg pm = null;
	private GsmsResponse resp=null;
	private Jmeter_Result JR=new Jmeter_Result();
	
	
	public DoSendSms(String host,String port,String username,String password,String phonehead,String msgCount,String content
			,String isMass,String batchname,int biztype,boolean distinctFlag) {
		// TODO Auto-generated constructor stub
		this.host=host;
		this.port=Integer.parseInt(port);
		this.username=username;
		this.password=password;
		this.phonehead=phonehead;
		this.msgCount=Integer.parseInt(msgCount);
		this.content=content;
		this.isMass=isMass;
		this.batchname=batchname;
		this.biztype=biztype;
		this.distinctFlag=distinctFlag;
		this.pm=new PostMsg(this.host, this.port);
		
	}
	
		
//	private static PostMsg pm = new PostMsg("172.16.0.78", 8090);// 发送器 建议单例配置
	
	
	@Test
	public void test() {
		try {
			extend(); // 扩展接口范例
		} catch (Exception e) {
			String errorStr="错误信息如下：";
			JR.setResult(errorStr);
			JR.setStatus(ResultState.SERVER_ERROR);
			e.printStackTrace();
			JR.AddResult(e.getMessage());
		}
		if (msgCount>1&&isMass.toLowerCase().equals("false")){
			String result="请求成功！状态报告如上：";
			JR.AddResult(result);
			JR.setStatus(ResultState.SUCCESS);
		}else{
			String result="请求成功！状态报告如下：";
			JR.setResult(result);
			JR.setStatus(ResultState.SUCCESS);
			JR.AddResult(resp.toString());
		}
	}
	
	// 扩展接口范例
	public void extend() throws Exception {
		doSendSms(username, password, phonehead, content,msgCount,isMass); // 短信下行

	}
		
		
	/**
	 * 短信下发范例
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param phone
	 *            接收方手机号
	 * @param content
	 *            短信内容
	 */
	public void doSendSms(String account, String password, String phonehead, String content,int msgCount,String isMass) throws Exception {
//		pack.addMsg(MessageData.getInstance(phone, content));
		Account ac = new Account(account, password);
		if(isMass.toLowerCase().equals("true")==true){
		MTPack pack = buildDefaultSMSPack();
		ArrayList<MessageData> msgs = new ArrayList<MessageData>();
		double base=Integer.parseInt(phonehead)*100000000.00;
		for(int i = 0; i < msgCount; i ++){
			msgs.add(new MessageData(new BigDecimal(base+i)+"", ((msgCount==1) ? content : (content+i))));
		}
		pack.setMsgs(msgs);
		resp = pm.post(ac, pack);
		}else{
			double base=Integer.parseInt(phonehead)*100000000.00;
			JR.setResult("单发测试报告如下：");
			for (int i = 0; i < msgCount; i++) {
				MTPack pack = null;
				pack = buildMtPack(((msgCount==1) ? batchname : (batchname+i)), MsgType.SMS, SendType.MASS, biztype, distinctFlag);
				pack.addMsg(MessageData.getInstance(new BigDecimal(base+i)+"", content));
//				pack.addMsg(MessageData.getInstance("15914494647", "BUYIYANG"));
				resp = pm.post(ac, pack);
				JR.AddResult(resp.toString()+"");
			}
		}
		
		/**
		 * 设置模板编号(静态模板将以模板内容发送;
		 * 动态模板需要发送变量值，JSON格式：[{"key":"变量名1","value":"变量值1"},{"key":"变量名2","
		 * value":"变量值2"}])
		 */
		
//		System.out.println(resp);
	}	
	
	
	/**
	 * 创建默认下行批次 消息类型：短信； 发送方式；群发； 业务类型：0； 是否去重：否
	 */
	private MTPack buildDefaultSMSPack() {
		return buildMtPack(batchname, MsgType.SMS, SendType.MASS, biztype, distinctFlag);
	}
	
	
	/**
	 * @param batchName
	 *            批次名称
	 * @param msgType
	 *            消息类型
	 * @param sendType
	 *            发送方式
	 * @param biztype
	 *            业务类型根据自己的业务类型编号配置
	 * @param distinctFlag
	 *            是否去重
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
