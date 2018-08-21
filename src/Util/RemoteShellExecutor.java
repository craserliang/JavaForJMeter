package Util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import entity.Jmeter_Result;

public class RemoteShellExecutor {
     
     private Connection conn;
     /** 远程机器IP */
     private String ip;
     /** 用户名 */
     private String osUsername;
     /** 密码 */
     private String password;
     private String charset = Charset.defaultCharset().toString();
     private Jmeter_Result resultData=new Jmeter_Result();

     private static final int TIME_OUT = 1000 * 5;
     

     /**
      * 构造函数
      * @param ip
      * @param usr
      * @param pasword
      */
     public RemoteShellExecutor(String ip, String usr, String pasword) {
          this.ip = ip;
         this.osUsername = usr;
         this.password = pasword;
     }


     /**
     * 登录
     * @return
     * @throws IOException
     */
     private boolean login() throws IOException {
         conn = new Connection(ip);
         conn.connect();
         return conn.authenticateWithPassword(osUsername, password);
     }

     /**
     * 执行脚本
     * 
     * @param cmds
     * @return
     * @throws Exception
     */
     public int exec(String cmds) throws Exception {
         InputStream stdOut = null;
         InputStream stdErr = null;
         String outStr = "";
         String outErr = "";
         int ret = -1;
         try {
         if (login()) {
             // Open a new {@link Session} on this connection
             Session session = conn.openSession();
             session.requestPTY("vt100", 80, 24, 640, 480, null);
             Thread.sleep(5000);
             StringBuilder sb = new StringBuilder();
             // Execute a command on the remote machine.
             session.execCommand(cmds);
             
             stdOut = new StreamGobbler(session.getStdout());
             BufferedReader br = new BufferedReader(new InputStreamReader(stdOut));
             char[] arr = new char[512];
             int read;
             int i = 0;
             long start = System.currentTimeMillis();
             while(true){
            	 read = br.read(arr, 0, arr.length);
            	 if (read < 0 ||(System.currentTimeMillis() - start) > TIME_OUT) {
                     break;
                 }
            	 sb.append(new String(arr, 0, read));
            	 i++;
             }
             outStr=sb.toString();
             
             stdErr = new StreamGobbler(session.getStderr());
             outErr = processStream(stdErr, charset);
                     
             
             session.waitForCondition(ChannelCondition.EXIT_STATUS, (TIME_OUT+1000));

             System.out.println("outStr=" + outStr);
             System.out.println("outErr=" + outErr);
             resultData.setResult(outStr);
             
             ret = session.getExitStatus();
         } else {
             throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
         }
         } finally {
             if (conn != null) {
                 conn.close();
             }
             IOUtils.closeQuietly(stdOut);
             IOUtils.closeQuietly(stdErr);
         }
         return ret;
     }

     /**
     * @param in
     * @param charset
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
     private String processStream(InputStream in, String charset) throws Exception {
         byte[] buf = new byte[1024];
         StringBuilder sb = new StringBuilder();
         while (in.read(buf) != -1) {
             sb.append(new String(buf, charset));
         }
         return sb.toString();
     }


	public Jmeter_Result getResult() {
		// TODO Auto-generated method stub
		return resultData;
	}
	

    public static void main(String args[]) throws Exception {
      RemoteShellExecutor executor = new RemoteShellExecutor("172.16.0.78", "root", "Qcd0809@xw.");
      // 执行myTest.sh 参数为java Know dummy
      System.out.println(executor.exec("/home/ump5.3/simulateGate2/simgate.sh start"));
    }
}