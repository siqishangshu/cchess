package cn.mxsic.chess;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class App extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Color bgcolor=new Color(245,250,160);//棋盘的背景色
	public static final Color focusbg=new Color(242, 242, 242);//棋子选中后的背景色
	public static final Color focuschar=new Color(96, 95, 91);//棋子选中后字符的颜色
	public   Color color2=Color.yellow;//黄方颜色
	public   Color color1=Color.white;//白方颜色
	JLabel jlHost=new JLabel("主机名");
	JLabel jlPort=new JLabel("端口号");
	JLabel jlNickName=new JLabel("昵   称");
	public JTextField jtfHost=new JTextField("127.0.0.1");
	public JTextField jtfPort=new JTextField("9999");
	public JTextField jtfNickName=new JTextField("");
	public JButton jbConnect=new JButton("连   接");
	public JButton jbDisconnect=new JButton("断   开");
	public JButton jbFail=new JButton("认   输");
	public JButton jbChallenge=new JButton("挑   战");
	public JButton jbYChallenge=new JButton("接受");
	public JButton jbNChallenge=new JButton("拒绝");
	public JButton jbChonglai=new JButton("重来");
	public JLabel jlvs=new JLabel("我     VS  电脑 ");//对战消息
	public JComboBox<String> jcbNickList=new JComboBox<String>();//创建存放当前用户的下拉 表
	public JTextField message=new JTextField();
	private JButton send=new JButton("发送");
	private JButton canel=new JButton("取消");
	//private  Date now;//消息时间 
	public JList<String> jlmessage=new JList<String>();
	int width=60;//设置棋子两线之间的距离
	Chess[][] qizi=new Chess[9][10];//创建棋子数组
	///如何在将qipan初始化的时候将qizi传入正确的值 
	public Board jpz=new Board(qizi,width,this);//创建棋盘
	public JTextArea jtmove=new JTextArea("提示信息");//显示走棋路子
	JPanel jpleft=new JPanel();//整体右边的面板
	Vector<String> vformessage=new Vector<String>();//消息列表
	JScrollPane jsptxt=new JScrollPane(jlmessage);//加入到消息面板
	JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jpz,jpleft);//整体左右分
	public boolean caiPan=true;//可否走棋的标志位。。。。裁判
	public int color=1;//1代表黄棋，0代表白棋
	public Socket sc;
	public static void main(String[] args) {
		new App();
	}
	
	public App(){
		this.initialComponent();
		this.addListener();
		this.initialState();
		this.initialChess();
		this.initialFrame();
		
	}
	private void initialFrame() {
		this.setTitle("中国象棋--单机脑残版");
		Image image=new ImageIcon("ico.gif").getImage();
		this.setIconImage(image);
		String s="暂时没有聊天记录!";vformessage.add(s);
		jlmessage.setListData(vformessage);
		jsp.setDividerLocation(730);//分割线位置
		jsp.setDividerSize(4);//分割线宽度
		//jsp.setEnabled(false);//设置分割线是否可动
		this.add(this.jsp);
		this.setBounds(230,0,930,730);
		this.setVisible(true);
	}

	private void initialChess() {
		// TODO Auto-generated method stub
		for (int i = 0; i <9; i++) {
			for (int j = 0; j < 10; j++) {
				qizi[i][j]=null;
			}
		}
		qizi[0][0]=new Chess(color1,"车",0,0);
		qizi[1][0]=new Chess(color1,"马",1,0);
		qizi[2][0]=new Chess(color1,"相",2,0);
		qizi[3][0]=new Chess(color1,"士",3,0);
		qizi[4][0]=new Chess(color1,"帅",4,0);
		qizi[5][0]=new Chess(color1,"士",5,0);
		qizi[6][0]=new Chess(color1,"相",6,0);
		qizi[7][0]=new Chess(color1,"马",7,0);
		qizi[8][0]=new Chess(color1,"车",8,0);
		qizi[1][2]=new Chess(color1,"炮",1,2);
		qizi[7][2]=new Chess(color1,"炮",7,2);
		qizi[0][3]=new Chess(color1,"兵",0,3);
		qizi[2][3]=new Chess(color1,"兵",2,3);
		qizi[4][3]=new Chess(color1,"兵",4,3);
		qizi[6][3]=new Chess(color1,"兵",6,3);
		qizi[8][3]=new Chess(color1,"兵",8,3);
		//摩尔
		
		qizi[0][6]=new Chess(color2,"卒",0,6);
		qizi[2][6]=new Chess(color2,"卒",2,6);
		qizi[4][6]=new Chess(color2,"卒",4,6);
		qizi[6][6]=new Chess(color2,"卒",6,6);
		qizi[8][6]=new Chess(color2,"卒",8,6);
		qizi[1][7]=new Chess(color2,"炮",1,7);
		qizi[7][7]=new Chess(color2,"炮",7,7);
		qizi[0][9]=new Chess(color2,"车",0,9);
		qizi[1][9]=new Chess(color2,"马",1,9);
		qizi[2][9]=new Chess(color2,"象",2,9);
		qizi[3][9]=new Chess(color2,"仕",3,9);
		qizi[4][9]=new Chess(color2,"将",4,9);
		qizi[5][9]=new Chess(color2,"仕",5,9);
		qizi[6][9]=new Chess(color2,"象",6,9);
		qizi[7][9]=new Chess(color2,"马",7,9);
		qizi[8][9]=new Chess(color2,"车",8,9);
	}



	private void initialState() {
		// TODO Auto-generated method stub
		this.jbDisconnect.setEnabled(false);
		this.jbChallenge.setEnabled(false);
		this.jbYChallenge.setEnabled(false);
		this.jbNChallenge.setEnabled(false);
		this.jbFail.setEnabled(true);
		this.jtmove.setEnabled(false);
		this.jbChonglai.setEnabled(true);
		this.jtfHost.setEnabled(false);
		this.jtfNickName.setEnabled(false);
		this.jtfPort.setEnabled(false);
		this.jbConnect.setEnabled(false);
		this.send.setEnabled(false);
		this.canel.setEnabled(false);
		this.message.setEnabled(false);
		this.jcbNickList.setEnabled(false);
		
	}



	private void addListener() {
		// TODO Auto-generated method stub
		this.jbConnect.addActionListener(this);
		this.jbDisconnect.addActionListener(this);
		this.jbChallenge.addActionListener(this);
		this.jbFail.addActionListener(this);
		this.jbYChallenge.addActionListener(this);
		this.jbNChallenge.addActionListener(this);
		this.jbChonglai.addActionListener(this);
		this.send.addActionListener(this);
		this.canel.addActionListener(this);
	}



	private void initialComponent() {
		// TODO Auto-generated method stub
		jpleft.setLayout(null);
		this.message.setBounds(10,580,160,20);jpleft.add(this.message);//消息框
		this.send.setBounds(10,600,60,20);jpleft.add(this.send);//发送消息
		this.canel.setBounds(100,600,60,20);jpleft.add(this.canel);//取消发送
		this.jsptxt.setBounds(10, 300, 160, 250);jpleft.add(this.jsptxt);
		this.jlvs.setBounds(20, 250, 160, 20);jpleft.add(this.jlvs);//vs对战消息
		this.jtmove.setBounds(20, 270, 140, 20);jpleft.add(this.jtmove);//提示消息
		this.jlHost.setBounds(10,10,50,20);jpleft.add(this.jlHost);//添加“主机号”标签
		this.jtfHost.setBounds(70,10,80,20);jpleft.add(this.jtfHost);
		this.jlPort.setBounds(10,40,50,20);jpleft.add(this.jlPort);
		this.jtfPort.setBounds(70,40,80,20);jpleft.add(this.jtfPort);
		this.jlNickName.setBounds(10,70,50,20);jpleft.add(this.jlNickName);
		this.jtfNickName.setBounds(70,70,80,20);jpleft.add(this.jtfNickName);
		this.jbConnect.setBounds(10,100,80,20);jpleft.add(this.jbConnect);
		this.jbDisconnect.setBounds(95,100,80,20);jpleft.add(this.jbDisconnect);
		this.jcbNickList.setBounds(20,130,130,20);jpleft.add(this.jcbNickList);
		this.jbChallenge.setBounds(5,160,80,20);jpleft.add(this.jbChallenge);
		this.jbFail.setBounds(95,160,80,20);jpleft.add(this.jbFail);
		this.jbYChallenge.setBounds(5,190,80,20);jpleft.add(this.jbYChallenge);
		this.jbNChallenge.setBounds(95,190,80,20);jpleft.add(this.jbNChallenge);
		this.jbChonglai.setBounds(50,220,80,20);jpleft.add(this.jbChonglai);
		jpleft.setBackground(new Color(99, 220, 33));
		jpz.setBounds(0,0,700,700);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jbFail){
			this.jbFail_event();
		}
		if(e.getSource()==jbChonglai){
			this.next();
		}
		
	}
	public void next(){
		//这局完一，下一局
		for (int i = 0; i <9; i++) {
			for (int j = 0; j < 10; j++) {
				qizi[i][j]=null;
			}
		}
		vformessage.removeAllElements();
		caiPan=true;
		color=1;
		this.initialState();
		this.initialChess();
		this.repaint();
	}

	private void jbFail_event() {
		// TODO Auto-generated method stub
		try{
			this.color=0;
			this.caiPan=false;
			this.next();//开始下一盘
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

}
