package cn.mxsic.chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.mxsic.algorithm.Ai;

public class Board extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int width;//棋盘两线之间的距离
	boolean focus=false;//棋子的状态
	int jiang1_i=4;//帅的i坐标
	int jiang1_j=0;//帅的j坐标
	int jiang2_i=4;//将的i坐标
	int jiang2_j=9;//将的j坐标
	int startI=-1;int startJ=-1;//棋子开始的位置
	int endI=-1;int endJ=-1;//棋子终止的位置
	public Chess qizi[][];//棋子数组
	App xq=null;
	Rule guize;
	Ai ai;
	public Board(Chess qizi[][] ,int width,App xq){
		this.xq=xq;this.qizi=qizi;this.width=width;
		guize=new Rule(qizi);
		this.addMouseListener(this);
		this.setBounds(0,0,700,700);
		this.setLayout(null);
	}
	public void paint(Graphics g1){
		Graphics2D g=(Graphics2D)g1;//获得Grahpics2D对象
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//打开q锯齿
		Color c=g.getColor();//获得画笔颜色
		g.setColor(App.bgcolor);//将画笔设为背景颜色
		g.fill3DRect(60, 30, 580, 630, false);//绘制一个矩形棋盘
		g.setColor(Color.black);
		for (int i = 80; i <=620; i=i+60) {
			g.drawLine(110, i, 590, i);//绘制棋盘中的横线
		}
		g.drawLine(110, 80, 110, 620);//绘制左边线
		g.drawLine(590, 80, 590, 620);//绘制右边线
		for (int i =170; i <=530; i=i+60) {
			g.drawLine(i, 80, i, 320);
			g.drawLine(i, 380, i, 620);//绘制中间的竖线
		}
		g.drawLine(290, 80, 410, 200);//绘制两边的斜线
		g.drawLine(290, 200, 410, 80);
		g.drawLine(290, 620, 410, 500);//绘制两边的斜线
		g.drawLine(290, 500, 410, 620);
		//绘制 线条的部分代码，，，，
		this.smallLine(g,7,7);//绘制白炮成在的位置标示
		this.smallLine(g,1,7);
		this.smallLine(g,1,2);
		this.smallLine(g,7,2);
		
		g.setColor(Color.black);
		Font font1=new Font("宋体",Font.BOLD,50);//设置字体
		g.setFont(font1);
		g.drawString("楚  河", 150, 365);//绘制字体
		g.drawString("汉  界", 390, 365);
		Font font=new Font("宋体",Font.BOLD,30);//设置字体
		g.setFont(font);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {//绘制棋子
				if(qizi[i][j]!=null){
					if(this.qizi[i][j].getFocus()!=false){//是否被选中
						
						g.setColor(App.focusbg);//选中后的背景色
						g.fillOval((110+i*60)-25, (80+j*60)-25, 50, 50);//绘制该棋子
						g.setColor(App.focuschar);//字体颜色
					}else{
						g.fillOval(110+i*60-25,80+j*60-25, 50, 50);//绘制该棋子
						g.setColor(qizi[i][j].getColor());//设置画笔颜色
					}
					g.drawString(qizi[i][j].getName(), 110+i*60-18, 80+j*60+10);
					g.setColor(Color.black);
				}
			}
		}
		g.setColor(c);
	}
	
	private void smallLine(Graphics2D g, int i, int j) {
		// TODO Auto-generated method stub
		int x=110+60*i; int y=80+60*j;//计算坐标
		if(i>0){g.drawLine(x-3, y-3, x-20, y-3);g.drawLine(x-3, y-3, x-3, y-20);}//绘制左上方的标示
		if(i<8){g.drawLine(x+3, y-3, x+20, y-3);g.drawLine(x+3, y-3, x+3, y-20);}//绘制右上方的标示
		if(i>0){g.drawLine(x-3, y+3, x-20, y+3);g.drawLine(x-3, y+3, x-3, y+20);}//绘制左下方的标示
		if(i<8){g.drawLine(x+3, y+3, x+20, y+3);g.drawLine(x+3, y+3, x+3, y+20);}//绘制右下方的标示
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(this.xq.caiPan==true){//判断是否轮到该玩家走棋
			int i=-1,j=-1;
			int[] pos=GetPos(e);//得到事件发生坐标
			i=pos[0];j=pos[1];//xy坐标
			if(i>=0&&i<=8&&j>=0&&j<=9){//如果发生在棋盘内
				if(focus==false){//如果之前没有棋子被选中
					 //没有选 中棋子
					this.noFocus(i, j);
					
				}else{//如果之前选中过棋子
					if(qizi[i][j]!=null){//如果这里有棋子
						if(qizi[i][j].getColor()==qizi[startI][startJ].getColor()){//如果是自己的棋子
							qizi[startI][startJ].setFocus(false);//更改选中对象
							qizi[i][j].setFocus(true);
							startI=i;startJ=j;//保存修改
						}else{//如果是对方棋子  吃了对方的棋子
							endI=i;endJ=j;//保存该点
							String name=qizi[startI][startJ].getName();//得到棋子名
							boolean canMove=guize.canMove(startI, startJ, endI, endJ, name);
							if(canMove){
								try{
									//this.xq.cat.dout.writeUTF("<#MOVE#>"+this.xq.cat.tiaoZhanZhe+startI+startJ+endI+endJ);
									this.xq.caiPan=false;
									showShuYu(name,startI, startJ, endI, endJ);
								if(qizi[endI][endJ].getName().equals("帅")||qizi[endI][endJ].getName().equals("将")){
									this.success();//如果终点对方的将，
								}else{
									this.noJiang();//如果终点不是对方的将
								}
								}catch(Exception ea){ea.printStackTrace();}
							}
						}
					}else{//如果没有棋子 没吃棋子
						endI=i;endJ=j;//保存终点
						String name=qizi[startI][startJ].getName();//得到该棋子的名字
						boolean canMove=guize.canMove(startI, startJ, endI, endJ, name);
						if(canMove){
							showShuYu(name,startI, startJ, endI, endJ);
							this.noQiZi();
						}
					}
				}
				
			}
			
			this.xq.repaint();//重绘
		}
	}

	private void noJiang() {
		// TODO Auto-generated method stub
		qizi[endI][endJ]=qizi[startI][startJ];
		qizi[startI][startJ]=null;//走棋
		qizi[endI][endJ].setFocus(false);//将该棋设为非选中状态
		this.xq.repaint();//重绘
		if(qizi[endI][endJ].getName().equals("帅")){//如果移动的是帅
			jiang1_i=endI;jiang1_j=endJ;//更新帅的人位置
		}else if(qizi[endI][endJ].getName().equals("将")){
			jiang2_i=endI;jiang2_j=endJ;
		}
		if(jiang1_i==jiang2_i){//老将见面
			int count=0;
			for (int jiang_j=jiang1_j+1; jiang_j< jiang2_j; jiang_j++) {//遍历这条竖线
				if(qizi[jiang1_i][jiang_j]!=null){
					count++;break;//如果存在棋子，退出循环
				}
			}
			if(count==0){//如果等于0则照易将
				JOptionPane.showMessageDialog(this.xq, "照将!!!你失败了!!!","提示",JOptionPane.INFORMATION_MESSAGE);
					//this.xq.cat.tiaoZhanZhe=null;
					this.xq.color=0;//还原棋盘
					this.xq.caiPan=false;
					this.xq.next();//进入下一盘
					jiang1_i=4;jiang1_j=0;//将，帅的i,j的坐标
					jiang2_i=4;jiang2_j=9;
			}
		}
		int[] start_end=new int[4];//接收返回的电脑走法
		ai=new Ai(qizi);
		start_end=ai.getMove();//得到走法，
		this.move(start_end[0], start_end[1], start_end[2], start_end[3]);
		startI=-1;startJ=-1;//还原保存点
		endI=-1;endJ=-1;
		focus=false;
	}
	private void success() {
		// TODO Auto-generated method stub
		qizi[endI][endJ]=qizi[startI][startJ];//吃掉该棋子
		qizi[startI][startJ]=null;//将原来的位置设空
		this.xq.repaint();//重绘
		JOptionPane.showMessageDialog(this.xq, "恭喜您，您获胜了","提示",JOptionPane.INFORMATION_MESSAGE);
		//给出获胜消息
		//this.xq.cat.tiaoZhanZhe=null;//挑战者失败
		this.xq.color=0;
		this.xq.caiPan=false;
		this.xq.next();
		startI=-1;startJ=-1;
		endI=-1;endJ=-1;
		jiang1_i=4;jiang1_j=0;//将，帅的i,j的坐标
		jiang2_i=4;jiang2_j=9;
		focus=false;
	}
	private void noQiZi() {
		// TODO Auto-generated method stub
		try{//将移动信息发送给对方
			//this.xq.cat.dout.writeUTF("<#MOVE#>"+this.xq.cat.tiaoZhanZhe+startI+startJ+endI+endJ);
			this.xq.caiPan=false;
			qizi[endI][endJ]=qizi[startI][startJ];
			qizi[startI][startJ]=null;//走棋
			qizi[endI][endJ].setFocus(false);//将该棋设为非选中状态
			this.xq.repaint();//重绘
			if(qizi[endI][endJ].getName().equals("帅")){//如果移动的是帅
				jiang1_i=endI;jiang1_j=endJ;//更新帅的人位置
			}else if(qizi[endI][endJ].getName().equals("将")){
				jiang2_i=endI;jiang2_j=endJ;
			}
			if (jiang1_i == jiang2_i) {// 老将见面
				int count = 0;
				for (int jiang_j = jiang1_j + 1; jiang_j < jiang2_j; jiang_j++) {// 遍历这条竖线
					if (qizi[jiang1_i][jiang_j] != null) {
						count++;
						break;// 如果存在棋子，退出循环
					}
				}
				if (count == 0) {// 如果等于0则照易将
					JOptionPane.showMessageDialog(this.xq, "照将!!!你失败了!!!",
							"提示", JOptionPane.INFORMATION_MESSAGE);
					// this.xq.cat.tiaoZhanZhe=null;
					this.xq.color = 0;// 还原棋盘
					this.xq.caiPan = false;
					this.xq.next();// 进入下一盘
					jiang1_i = 4;
					jiang1_j = 0;// 将，帅的i,j的坐标
					jiang2_i = 4;
					jiang2_j = 9;

				} 
			}
			int[] start_end = new int[4];// 接收返回的电脑走法
			ai=new Ai(qizi);
			start_end = ai.getMove();// 得到走法，
			this.move(start_end[0], start_end[1], start_end[2],
					start_end[3]);
			focus=false;//取消焦点
			startI=-1;startJ=-1;//还原保存点
			endI=-1;endJ=-1;
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}
	private void noFocus(int i, int j) {
		// TODO Auto-generated method stub
		if(this.qizi[i][j]!=null){//如果该位置有棋子
			if(this.xq.color==0){//如果是红方的
				if(this.qizi[i][j].getColor().equals(this.xq.color1)){//如果棋子是红色
					this.qizi[i][j].setFocus(true);//将该棋子设为选中状态
					focus=true;//表示已经有选中的焦点
					startI=i;startJ=j;//保存该坐标点
				}
			}else{//如果是白方的
				if(this.qizi[i][j].getColor().equals(this.xq.color2)){//如果棋子是白色
					this.qizi[i][j].setFocus(true);//将该棋子设为选中状态
					focus=true;//表示已经有选中的焦点
					startI=i;startJ=j;//保存该坐标点
				}
			}
		}
	}
	private int[] GetPos(MouseEvent e) {
		// TODO Auto-generated method stub
		int[] pos=new int[2];
		pos[0]=-1;pos[1]=-1;
		Point p=e.getPoint();//获得事件发生的坐标
		double x=p.getX();
		double y=p.getY();
		if(Math.abs((x-110)/1%60)<=25){
			pos[0]=Math.round((float)(x-110))/60;//获得对应的x下标的位置
		}else if(Math.abs((x-110)/1%60)>=25){
			pos[0]=Math.round((float)(x-110))/60+1;//获得对应x下标的位置
		}
		if(Math.abs((y-80)/1%60)<=25){
			pos[1]=Math.round((float)(y-80))/60;//获得对应的y下标的位置
		}else if(Math.abs((y-80)/1%60)>=25){
			pos[1]=Math.round((float)(y-80))/60+1;//获得对应y下标的位置
		}
		return pos;
	}
	public void showShuYu(String name,int startI,int startJ,int endI,int endJ){
		int Sge,Sxian,Ege,Exian;//ge就是上下的，J，xian就是左右的I
		String mess="";
		if(this.xq.caiPan){
			Sge=10-startJ;Sxian=9-startI;Ege=10-endJ;Exian=9-endI;
			if(name.equals("将")||name.equals("�h")||name.equals("卒")||name.equals("车")){
				if(Sxian==Exian){//在同一格上走有进退
					if(Sge<Ege){
						mess="我:"+name+toWord(Sxian)+"进"+toWord(Ege-Sge);//y文字 转换成中文
					}else if(Sge>Ege){//在同线上
						mess="我:"+name+""+toWord(Sxian)+"退"+toWord(Sge-Ege);
					}
				}else
				if(Sge==Ege){//在同一条横线上左右平
					   mess="我:"+name+toWord(Sxian)+"平"+toWord(Exian);
				}
			}
			if(name.equals("象")||name.equals("仕")||name.equals("马")){
				if(Sxian<Exian){//向右进
					mess="我:"+name+toWord(Sxian)+"进"+toWord(Exian);//y文字 转换成中文
				}else
					if(Sxian>Exian){//向左退
					mess="我:"+name+toWord(Sxian)+"退"+toWord(Exian);
				}
			}
		this.xq.jtmove.setText("等待电脑走棋!!!");
		}else{
		Sge=1+startJ;Sxian=1+startI;Ege=1+endJ;Exian=1+endI;
		if(name.equals("帅")||name.equals("炮")||name.equals("兵")||name.equals("车")){
				if(Sxian==Exian){//在同一格上走有进退
					if(Sge<Ege){
						mess="电脑:"+name+Sxian+"进"+(Ege-Sge);//y文字 转换成中文
					}else if(Sge>Ege){//在同线上
						mess="电脑:"+name+""+Sxian+"退"+(Sge-Ege);
					}
				}else
				if(Sge==Ege){//在同一条横线上左右平
					   mess="电脑:"+name+Sxian+"平"+Exian;
				}
			}
			if(name.equals("相")||name.equals("士")||name.equals("马")){
				if(Sxian<Exian){//向右进
					mess="电脑:"+name+Sxian+"进"+Exian;//y文字 转换成中文
				}else
					if(Sxian>Exian){//向左退
					mess="电脑:"+name+Sxian+"退"+Exian;
				}
			}
		this.xq.jtmove.setText("等待您走棋!!!");
		}
		this.xq.vformessage.add(mess);
		this.xq.jlmessage.setListData(this.xq.vformessage);
	}
	public String toWord(int i){
		String[] str={"","一","二","三","四","五","六","七","八","九"};
		return str[i];
	}

	public void move(int startI,int startJ,int endI,int endJ){
		if(qizi[endI][endJ]!=null&&(qizi[endI][endJ].getName().equals("帅")||
				qizi[endI][endJ].getName().equals("将"))){//如果将被吃了
			qizi[endI][endJ]=qizi[startI][startJ];
			qizi[startI][startJ]=null;//走棋
			this.xq.repaint();//重绘
			JOptionPane.showMessageDialog(this.xq, "很遗憾!!您失败了!!!","提示",JOptionPane.INFORMATION_MESSAGE);
			//this.xq.cat.tiaoZhanZhe=null;
			this.xq.color=0;//还原棋盘
			this.xq.caiPan=false;
			this.xq.next();//进入下一盘
			jiang1_i=4;jiang1_j=0;//将，帅的i,j的坐标
			jiang2_i=4;jiang2_j=9;
		}else{//如果不是将
			showShuYu(qizi[startI][startJ].getName(),startI, startJ, endI, endJ);
			qizi[endI][endJ]=qizi[startI][startJ];
			qizi[startI][startJ]=null;//走棋
			this.xq.repaint();//重绘
			this.xq.caiPan=true;
			if(qizi[endI][endJ].getName().equals("帅")){//如果移动的是帅
				jiang1_i=endI;jiang1_j=endJ;//更新帅的人位置
			}else if(qizi[endI][endJ].getName().equals("将")){
				jiang2_i=endI;jiang2_j=endJ;
			}
			if(jiang1_i==jiang2_i){//老将见面
				int count=0;
				for (int jiang_j=jiang1_j+1; jiang_j< jiang2_j; jiang_j++) {//遍历这条竖线
					if(qizi[jiang1_i][jiang_j]!=null){
						count++;break;//如果存在棋子，退出循环
					}
				}if(count==0){//如果等于0则照易将
					JOptionPane.showMessageDialog(this.xq, "对方照将!!!你胜利了!!!","提示",JOptionPane.INFORMATION_MESSAGE);
						//this.xq.cat.tiaoZhanZhe=null;
						this.xq.color=0;//还原棋盘
						this.xq.caiPan=false;
						this.xq.next();//进入下一盘
						jiang1_i=4;jiang1_j=0;//将，帅的i,j的坐标
						jiang2_i=4;jiang2_j=9;
						
				}}
		}
		this.xq.repaint();//重绘
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
