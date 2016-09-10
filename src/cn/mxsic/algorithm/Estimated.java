package cn.mxsic.algorithm;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import cn.mxsic.chess.Chess;

public class Estimated {
//	private QiZi qizi[][];//在每次用的时候都要进行一次初始化，否则没有意义。//评估的值是对整个书面的一次性评估。
	private Map<String, Integer> map_self=new HashMap<String, Integer>();//用来保存所有棋子本身的价值
	private int value=0;//得到所有价值总合
	private int value_self;//本身的价值
	private int value_pos;//位置价值
	private Map<String, int[][]> map_pos=new HashMap<String, int[][]>();
	private int[][] jiang;
	private int[][] che;
	private int[][] ma;
	private int[][] xiang;
	private int[][] si;
	private int[][] pao;
	private int[][] bing;
	public Estimated() {//在使用的时候初始化，并把应该棋局放入其中
	// TODO Auto-generated constructor stub
		this.initi();
	}
	
	private void initi(){//所有的可能
		
		jiang=new int[9][10];{//给将的位置价值//将只有六个位置不能界
			jiang[0][0]=0;jiang[1][0]=0;jiang[2][0]=0;jiang[0][3]=11;jiang[0][4]=15;jiang[0][5]=11;jiang[0][6]=0;jiang[0][7]=0;jiang[0][8]=0;
			jiang[0][1]=0;jiang[1][1]=0;jiang[2][1]=0;jiang[3][1]=2;jiang[4][1]=2;jiang[5][1]=2;jiang[6][1]=0;jiang[7][1]=0;jiang[8][1]=0;
			jiang[0][2]=0;jiang[1][2]=0;jiang[2][2]=0;jiang[3][2]=1;jiang[4][2]=1;jiang[5][2]=1;jiang[6][2]=0;jiang[7][2]=0;jiang[8][2]=0;
			jiang[0][3]=0;jiang[1][3]=0;jiang[2][3]=0;jiang[3][3]=0;jiang[4][3]=0;jiang[5][3]=0;jiang[6][3]=0;jiang[7][3]=0;jiang[8][3]=0;
			jiang[0][4]=0;jiang[1][4]=0;jiang[2][4]=0;jiang[3][4]=0;jiang[4][4]=0;jiang[5][4]=0;jiang[6][4]=0;jiang[7][4]=0;jiang[8][4]=0;
			jiang[0][5]=0;jiang[1][5]=0;jiang[2][5]=0;jiang[3][5]=0;jiang[4][5]=0;jiang[5][5]=0;jiang[6][5]=0;jiang[7][5]=0;jiang[8][5]=0;
			jiang[0][6]=0;jiang[1][6]=0;jiang[2][6]=0;jiang[3][6]=0;jiang[4][6]=0;jiang[5][6]=0;jiang[6][6]=0;jiang[7][6]=0;jiang[8][6]=0;
			jiang[0][7]=0;jiang[1][7]=0;jiang[2][7]=0;jiang[3][7]=0;jiang[4][7]=0;jiang[5][7]=0;jiang[6][7]=0;jiang[7][7]=0;jiang[8][7]=0;
			jiang[0][8]=0;jiang[1][8]=0;jiang[2][8]=0;jiang[3][8]=0;jiang[4][8]=0;jiang[5][8]=0;jiang[6][8]=0;jiang[7][8]=0;jiang[8][8]=0;
			jiang[0][9]=0;jiang[1][9]=0;jiang[2][9]=0;jiang[3][9]=0;jiang[4][9]=0;jiang[5][9]=0;jiang[6][9]=0;jiang[7][9]=0;jiang[8][9]=0;
		}
		che=new int[9][10];{
			che[0][0]=-15;che[1][0]=3;che[2][0]=4;che[3][0]=12;che[4][0]=0;che[5][0]=12;che[6][0]=4;che[7][0]=3;che[8][0]=-15;
			che[0][1]=0;che[1][1]=0;che[2][1]=0;che[3][1]=2;che[4][1]=0;che[5][1]=2;che[6][1]=0;che[7][1]=0;che[8][1]=0;
			che[0][2]=-2;che[1][2]=0;che[2][2]=0;che[3][2]=2;che[4][2]=2;che[5][2]=2;che[6][2]=0;che[7][2]=0;che[8][2]=-2;
			che[0][3]=0;che[1][3]=0;che[2][3]=0;che[3][3]=2;che[4][3]=4;che[5][3]=2;che[6][3]=0;che[7][3]=0;che[8][3]=0;
			che[0][4]=8;che[1][4]=12;che[2][4]=12;che[3][4]=14;che[4][4]=15;che[5][4]=14;che[6][4]=12;che[7][4]=12;che[8][4]=8;
			che[0][5]=8;che[1][5]=11;che[2][5]=11;che[3][5]=14;che[4][5]=15;che[5][5]=14;che[6][5]=11;che[7][5]=11;che[8][5]=8;
			che[0][6]=6;che[1][6]=9;che[2][6]=13;che[3][6]=16;che[4][6]=16;che[5][6]=16;che[6][6]=13;che[7][6]=9;che[8][6]=6;
			che[0][7]=6;che[1][7]=6;che[2][7]=7;che[3][7]=8;che[4][7]=16;che[5][7]=8;che[6][7]=7;che[7][7]=6;che[8][7]=6;
			che[0][8]=6;che[1][8]=8;che[2][8]=7;che[3][8]=14;che[4][8]=33;che[5][8]=14;che[6][8]=7;che[7][8]=8;che[8][8]=6;
			che[0][9]=6;che[1][9]=8;che[2][9]=7;che[3][9]=10;che[4][9]=17;che[5][9]=10;che[6][9]=7;che[7][9]=8;che[8][9]=6;
		}
		ma=new int[9][10];{
			ma[0][0]=-7;ma[1][0]=-10;ma[2][0]=-5;ma[3][0]=-5;ma[4][0]=-5;ma[5][0]=-7;ma[6][0]=-5;ma[7][0]=-10;ma[8][0]=-7;
			ma[0][1]=-10;ma[1][1]=-5;ma[2][1]=-3;ma[3][1]=-3;ma[4][1]=-17;ma[5][1]=-3;ma[6][1]=-3;ma[7][1]=-5;ma[8][1]=-10;
			ma[0][2]=-2;ma[1][2]=-3;ma[2][2]=5;ma[3][2]=0;ma[4][2]=-1;ma[5][2]=0;ma[6][2]=15;ma[7][2]=-3;ma[8][2]=-5;
			ma[0][3]=-3;ma[1][3]=-1;ma[2][3]=3;ma[3][3]=0;ma[4][3]=3;ma[5][3]=0;ma[6][3]=3;ma[7][3]=-1;ma[8][3]=-3;
			ma[0][4]=-5;ma[1][4]=3;ma[2][4]=6;ma[3][4]=7;ma[4][4]=8;ma[5][4]=7;ma[6][4]=6;ma[7][4]=3;ma[8][4]=-5;
			ma[0][5]=-5;ma[1][5]=5;ma[2][5]=4;ma[3][5]=8;ma[4][5]=9;ma[5][5]=8;ma[6][5]=4;ma[7][5]=5;ma[8][5]=-5;
			ma[0][6]=-2;ma[1][6]=13;ma[2][6]=5;ma[3][6]=12;ma[4][6]=5;ma[5][6]=12;ma[6][6]=5;ma[7][6]=13;ma[8][6]=-3;
			ma[0][7]=-3;ma[1][7]=3;ma[2][7]=4;ma[3][7]=8;ma[4][7]=5;ma[5][7]=8;ma[6][7]=4;ma[7][7]=3;ma[8][7]=-3;
			ma[0][8]=-5;ma[1][8]=1;ma[2][8]=8;ma[3][8]=2;ma[4][8]=-1;ma[5][8]=2;ma[6][8]=8;ma[7][8]=1;ma[8][8]=-5;
			ma[0][9]=-5;ma[1][9]=-5;ma[2][9]=-5;ma[3][9]=1;ma[4][9]=-5;ma[5][9]=1;ma[6][9]=-5;ma[7][9]=-5;ma[8][9]=-5;
		}
		xiang=new int[9][10];{
			xiang[0][0]=0;xiang[1][0]=0;xiang[2][0]=0;xiang[3][9]=0;xiang[4][0]=0;xiang[5][0]=0;xiang[6][0]=0;xiang[7][0]=0;xiang[8][0]=0;
			xiang[0][1]=0;xiang[1][1]=0;xiang[2][1]=0;xiang[3][1]=0;xiang[1][4]=0;xiang[5][1]=0;xiang[6][1]=0;xiang[7][1]=0;xiang[8][1]=0;
			xiang[0][2]=-1;xiang[1][2]=0;xiang[2][2]=0;xiang[3][2]=0;xiang[4][2]=2;xiang[5][2]=0;xiang[6][2]=0;xiang[7][2]=0;xiang[8][2]=-1;
			xiang[0][3]=0;xiang[1][3]=0;xiang[2][3]=0;xiang[3][3]=0;xiang[4][3]=0;xiang[5][3]=0;xiang[6][3]=0;xiang[7][3]=0;xiang[8][3]=0;
			xiang[0][4]=0;xiang[1][4]=0;xiang[2][4]=-1;xiang[3][4]=0;xiang[4][4]=0;xiang[5][4]=0;xiang[6][4]=-1;xiang[7][4]=0;xiang[8][4]=0;
			xiang[0][5]=0;xiang[1][5]=0;xiang[2][5]=0;xiang[3][5]=0;xiang[4][5]=0;xiang[5][5]=0;xiang[6][5]=0;xiang[7][5]=0;xiang[8][5]=0;
			xiang[0][6]=0;xiang[1][6]=0;xiang[2][6]=0;xiang[3][6]=0;xiang[4][6]=0;xiang[5][6]=0;xiang[6][6]=0;xiang[7][6]=0;xiang[8][6]=0;
			xiang[0][7]=0;xiang[1][7]=0;xiang[2][7]=0;xiang[3][7]=0;xiang[4][7]=0;xiang[5][7]=0;xiang[6][7]=0;xiang[7][7]=0;xiang[8][7]=0;
			xiang[0][8]=0;xiang[1][8]=0;xiang[2][8]=0;xiang[3][8]=0;xiang[4][8]=0;xiang[5][8]=0;xiang[6][8]=0;xiang[7][8]=0;xiang[8][8]=0;
			xiang[0][9]=0;xiang[1][9]=0;xiang[2][9]=0;xiang[3][9]=0;xiang[4][9]=0;xiang[5][9]=0;xiang[6][9]=0;xiang[7][9]=0;xiang[8][9]=0;
		}
		
		si=new int[9][10];{
			si[0][0]=0;si[1][0]=0;si[2][0]=0;si[3][0]=0;si[4][0]=0;si[5][0]=0;si[6][0]=0;si[7][0]=0;si[8][0]=0;
			si[0][1]=0;si[1][1]=0;si[2][1]=0;si[3][1]=0;si[4][1]=2;si[5][1]=0;si[6][1]=0;si[7][1]=0;si[8][1]=0;
			si[0][2]=0;si[1][2]=0;si[2][2]=0;si[3][2]=-2;si[4][2]=0;si[5][2]=-2;si[6][2]=0;si[7][2]=0;si[8][2]=0;
			si[0][3]=0;si[1][3]=0;si[2][3]=0;si[3][3]=0;si[4][3]=0;si[5][3]=0;si[6][3]=0;si[7][3]=0;si[8][3]=0;
			si[0][4]=0;si[1][4]=0;si[2][4]=0;si[3][4]=0;si[4][4]=0;si[5][4]=0;si[6][4]=0;si[7][4]=0;si[8][4]=0;
			si[0][5]=0;si[1][5]=0;si[2][5]=0;si[3][5]=0;si[4][5]=0;si[5][5]=0;si[6][5]=0;si[7][5]=0;si[8][5]=0;
			si[0][6]=0;si[1][6]=0;si[2][6]=0;si[3][6]=0;si[4][6]=0;si[5][6]=0;si[6][6]=0;si[7][6]=0;si[8][6]=0;
			si[0][7]=0;si[1][7]=0;si[2][7]=0;si[3][7]=0;si[4][7]=0;si[5][7]=0;si[6][7]=0;si[7][7]=0;si[8][7]=0;
			si[0][8]=0;si[1][8]=0;si[2][8]=0;si[3][8]=0;si[4][8]=0;si[5][8]=0;si[6][8]=0;si[7][8]=0;si[8][8]=0;
			si[0][9]=0;si[1][9]=0;si[2][9]=0;si[3][9]=0;si[4][9]=0;si[5][9]=0;si[6][9]=0;si[7][9]=0;si[8][9]=0;
		}
		pao=new int[9][10];{
			pao[0][0]=1;pao[1][0]=1;pao[2][0]=2;pao[3][0]=4;pao[4][0]=4;pao[5][0]=4;pao[6][0]=2;pao[7][0]=1;pao[8][0]=1;
			pao[0][1]=1;pao[1][1]=2;pao[2][1]=3;pao[3][1]=3;pao[4][1]=3;pao[5][1]=3;pao[6][1]=3;pao[7][1]=2;pao[8][1]=1;
			pao[0][2]=2;pao[1][2]=1;pao[2][2]=5;pao[3][2]=4;pao[4][2]=6;pao[5][2]=4;pao[6][2]=5;pao[7][2]=1;pao[8][2]=2;
			pao[0][3]=1;pao[1][3]=1;pao[2][3]=1;pao[3][3]=1;pao[4][3]=1;pao[5][3]=1;pao[6][3]=1;pao[7][3]=1;pao[8][3]=1;
			pao[0][4]=0;pao[1][4]=1;pao[2][4]=4;pao[3][4]=1;pao[4][4]=5;pao[5][4]=1;pao[6][4]=4;pao[7][4]=1;pao[8][4]=0;
			pao[0][5]=1;pao[1][5]=1;pao[2][5]=1;pao[3][5]=1;pao[4][5]=5;pao[5][5]=1;pao[6][5]=1;pao[7][5]=1;pao[8][5]=1;
			pao[0][6]=1;pao[1][6]=4;pao[2][6]=4;pao[3][6]=3;pao[4][6]=5;pao[5][6]=3;pao[6][6]=4;pao[7][6]=4;pao[8][6]=1;
			pao[0][7]=2;pao[1][7]=2;pao[2][7]=1;pao[3][7]=-12;pao[4][7]=-3;pao[5][7]=-12;pao[6][7]=1;pao[7][7]=2;pao[8][7]=2;
			pao[0][8]=3;pao[1][8]=3;pao[2][8]=1;pao[3][8]=-10;pao[4][8]=-6;pao[5][8]=-10;pao[6][8]=1;pao[7][8]=3;pao[8][8]=3;
			pao[0][9]=5;pao[1][9]=5;pao[2][9]=1;pao[3][9]=-4;pao[4][9]=-5;pao[5][9]=-4;pao[6][9]=1;pao[7][9]=5;pao[8][9]=5;
		}
		bing=new int[9][10];{
			bing[0][0]=0;bing[1][0]=0;bing[2][0]=0;bing[3][0]=0;bing[4][0]=0;bing[5][0]=0;bing[6][0]=0;bing[7][0]=0;bing[8][0]=0;
			bing[0][1]=0;bing[1][1]=0;bing[2][1]=0;bing[3][1]=0;bing[4][1]=0;bing[5][1]=0;bing[6][1]=0;bing[7][1]=0;bing[8][1]=0;
			bing[0][2]=0;bing[1][2]=0;bing[2][2]=0;bing[3][2]=0;bing[4][2]=0;bing[5][2]=0;bing[6][2]=0;bing[7][2]=0;bing[8][2]=0;
			bing[0][3]=-5;bing[1][3]=0;bing[2][3]=-5;bing[3][3]=0;bing[4][3]=3;bing[5][3]=0;bing[6][3]=-5;bing[7][3]=0;bing[8][3]=-5;
			bing[0][4]=-5;bing[1][4]=0;bing[2][4]=1;bing[3][4]=0;bing[4][4]=4;bing[5][4]=0;bing[6][4]=1;bing[7][4]=0;bing[8][4]=-5;
			bing[0][5]=7;bing[1][5]=15;bing[2][5]=15;bing[3][5]=24;bing[4][5]=25;bing[5][5]=24;bing[6][5]=15;bing[7][5]=15;bing[8][5]=7;
			bing[0][6]=17;bing[1][6]=24;bing[2][6]=27;bing[3][6]=37;bing[4][6]=39;bing[5][6]=37;bing[6][6]=27;bing[7][6]=24;bing[8][6]=17;
			bing[0][7]=17;bing[1][7]=27;bing[2][7]=42;bing[3][7]=52;bing[4][7]=52;bing[5][7]=52;bing[6][7]=42;bing[7][7]=27;bing[8][7]=17;
			bing[0][8]=17;bing[1][8]=27;bing[2][8]=47;bing[3][8]=62;bing[4][8]=67;bing[5][8]=62;bing[6][8]=47;bing[7][8]=27;bing[8][8]=17;
			bing[0][9]=-3;bing[1][9]=-3;bing[2][9]=-3;bing[3][9]=-1;bing[4][9]=-1;bing[5][9]=-1;bing[6][9]=-3;bing[7][9]=-3;bing[8][9]=-3;
		}
		map_self.put("帅", 9999);
		map_self.put("车", 340);
		map_self.put("马", 144);
		map_self.put("相", 26);
		map_self.put("士", 25);
		map_self.put("炮", 152);
		map_self.put("兵", 13);
		
		map_self.put("将", 9999);
		map_self.put("象", 26);
		map_self.put("仕", 25);
		map_self.put("宠", 152);
		map_self.put("卒", 13);
		
		map_pos.put("帅", jiang);
		map_pos.put("车", che);
		map_pos.put("马", ma);
		map_pos.put("相", xiang);
		map_pos.put("士", si);
		map_pos.put("炮", pao);
		map_pos.put("兵", bing);
	}
	public int getNamevalue(String name){
		int v=0;
		v=map_self.get(name);
		return v;
	}
	public int getOne(String name,Color color,int i,int j){
		int value=0;
		value=map_self.get(name);
		value=value+getPosValue(name,color, i, j);
		return value;
	}
	public int getValue(Chess  qizi[][]){//得到自己所有棋子的价值。
//		this.qizi=qizi;
		value_self=0;value_pos=0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if(qizi[i][j]!=null){//判断此处有棋子
				if(qizi[i][j].getColor()==Color.white){
					String name=qizi[i][j].getName();
					value_self=value_self+map_self.get(name);//棋子本身价值累加
					value_pos=value_pos+getPosValue(name, qizi[i][j].getColor(), i, j);//棋子位置价值累加
				}
				}
			}
		}
		value=value_self+value_pos;//棋子本身价值与位置价值之合，就是整个棋局的价值。
		return value;
	}
	private int getPosValue(String name,Color color,int i,int j){//得到 棋子位置价值
		if(color==Color.yellow){
			if(name.equals("将")){
				name="帅";
			}
			if(name.equals("车")){
				name="车";
			}
			if(name.equals("马")){
				name="马";
			}
			if(name.equals("象")){
				name="相";
			}
			if(name.equals("仕")){
				name="士";
			}
			if(name.equals("宠")){
				name="炮";
			}
			if(name.equals("卒")){
				name="兵";
			}
			i=8-i;j=9-j;
		}
		int v_p=0;
		v_p=map_pos.get(name)[i][j];
		return v_p;
	}
	
}
