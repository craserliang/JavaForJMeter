package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.xml.soap.SOAPElement;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;

import test.ShellSample;
import test.Sms_Test;

public class ShellSampleGui extends AbstractSamplerGui{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabeledTextField IPTextField = new JLabeledTextField("IP");
	private JLabeledTextField UserTextField = new JLabeledTextField("USER");
	private JLabeledTextField PasswordTextField = new JLabeledTextField("PASSWORD");
	private JLabeledTextField ComandTextField = new JLabeledTextField("Comand"); 

	public ShellSampleGui() {
		super();
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout()); // ���ò���ΪJMeter���õ�BorderLayout
		setBorder(makeBorder()); // ���ñ߿�ΪĬ�ϱ߿�
		add(makeTitlePanel(),BorderLayout.NORTH); // ���JMeter���õĵ�һ�����ơ�ע�ⲿ��
		JPanel ShellConfigurePanel = new JPanel(new BorderLayout()); // ����һ��Panel
		ShellConfigurePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Configure")); // ����Panel�ı߿�Ϊ����ʴ�̷��ı߿��ߣ����Ҵ�������
		VerticalPanel configureVerticalPanel = new VerticalPanel(5, VerticalPanel.LEFT_ALIGNMENT); // ������ֱ������������panel
		JPanel configurePanel = new HorizontalPanel(); // ����ˮƽ�����Panel
		configurePanel.add(IPTextField);
		configurePanel.add(UserTextField);
		configurePanel.add(PasswordTextField);
		configureVerticalPanel.add(configurePanel); // ��"xml file"�������ӵ�configureVerticalPanel��
		configureVerticalPanel.add(ComandTextField); // ��"xml file"�������ӵ�configureVerticalPanel��
		ShellConfigurePanel.add(configureVerticalPanel); // ��configureVerticalPanel��ӵ�soapConfigurePanel��
		add(ShellConfigurePanel,BorderLayout.CENTER); // ��soapConfigurePanel��ӵ�������
	}

	@Override
	public TestElement createTestElement() {
		// TODO Auto-generated method stub
		ShellSample sampler=new ShellSample();
		modifyTestElement(sampler);
		return sampler;
	}

	@Override
	public String getLabelResource() {
		// TODO Auto-generated method stub
		return "Shell Sample";
	}
	
	@Override
	public String getStaticLabel() {
		return "Shell Sample";
	}
	
	@Override
	public void modifyTestElement(TestElement arg0) {
		// TODO Auto-generated method stub
		super.configureTestElement(arg0);
		ShellSample sampler=(ShellSample) arg0;
		sampler.setIp(IPTextField.getText());
		sampler.setUsername(UserTextField.getText());
		sampler.setPassword(PasswordTextField.getText());
		sampler.setCommand(ComandTextField.getText());
	}
	
	@Override
	public void configure(TestElement te) {
		super.configure(te); // ����ȱ�٣����򽫻�������ֶ�ʧ
		ShellSample sampler = (ShellSample)te;
		IPTextField.setText(sampler.getIp());
		UserTextField.setText(sampler.getUsername());
		PasswordTextField.setText(sampler.getPassword());
		ComandTextField.setText(sampler.getCommand());
	}
	
	@Override
	public void clearGui() {
		super.clearGui();
		IPTextField.setText("");
		UserTextField.setText("");
		PasswordTextField.setText("");
		ComandTextField.setText("");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
