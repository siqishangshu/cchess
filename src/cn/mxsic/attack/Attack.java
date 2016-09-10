package cn.mxsic.attack;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import cn.mxsic.algorithm.Estimated;
import cn.mxsic.chess.Rule;
import cn.mxsic.chess.Chess;



public class Attack {//以攻为守
	public Estimated selfvalue=new Estimated();
	public Chess qizi[][];
	public Map<Integer, int[]> gong_map=new HashMap<Integer, int[]>();//所有可以攻的走法
	public Rule guize;
	public int gongfa=0;//对方有多少种吃法
	private Examine jiance=new Examine();
	private int chijiang[]={-1,-1,-1,-1,-1};
	public Attack(Chess qizi[][]) {
		// TODO Auto-generated constructor stub
		this.qizi=qizi;
		this.guize=new Rule(qizi);
		this.initial();
	}
private void initial() {
		// TODO Auto-generated method stub
	for (int i = 0; i <9; i++) {
		for (int j = 0; j < 10; j++) {
			if(qizi[i][j]!=null){//判断此处有棋子
			if(qizi[i][j].getColor()==Color.white){//如果是电脑的棋子
				putPossibleMove(i,j,qizi[i][j].getName());
			 }
			}
		}
	}
	}
private void putPossibleMove(int i, int j, String name) {
	// TODO Auto-generated method stub
	//对每个棋子可走的位置进行遍历
	for (int k = 0; k < 9; k++) {//这种费时行为有助于其它棋子通用
		for (int k2 = 0; k2 < 10; k2++){
			if(qizi[k][k2]!=null){//这里有棋子
				if(qizi[k][k2].getColor()!=Color.white){//如果是自己的棋子
					boolean can=false;
					can=guize.canMove(i, j, k, k2, name);//判断该对方是否可以吃自己，
					if(can){//如果可以吃
						if(qizi[k][k2].getName().equals("将")){//如果可以吃掉将
							chijiang[0]=i;chijiang[1]=j;
							chijiang[2]=k;chijiang[3]=k2;chijiang[4]=-3;
							break;
						}
						boolean safe=false;
						safe=jiance.getJianCe(qizi,i, j, k, k2);
						if(safe){//如果走后下一步是安全的
						   putInMap(i, j, k, k2);//将对方可吃自己的走法放入map中
						}else{
						}
					}
				}
			
			
		}
}
}
}
private void putInMap(int startI,int startJ,int endI,int endJ) {
	// TODO Auto-generated method stub
	int tem[]=new int[4];
	tem[0]=startI;tem[1]=startJ;tem[2]=endI;tem[3]=endJ;
	gong_map.put(gongfa,tem);//将走法加入到map中？？？？？？
	//还原棋局
	gongfa++;//走法增加一种
}
public int[] bestGong(){
	int move[]=new int[5];
	if(gongfa==0){//如果没有走法
		for (int m = 0; m < move.length; m++) {
			move[m]=0;
		}
	}else{//如果有走法
		int value=0;
		int p=0;//标记位置
	for (int i = 0; i < gongfa; i++) {
		int v=0;
		int tem[]=new int[4];
		tem=gong_map.get(i);
		int a=tem[0],b=tem[1],c=tem[2],d=tem[3];
		String name=qizi[a][b].getName();
		v=selfvalue.getOne(name,qizi[a][b].getColor(), c,d)+selfvalue.getNamevalue(qizi[c][d].getName());
		if(v>value){
			v=value;
			p=i;
		}
	}
	int mov[]=new int[4];
	mov=gong_map.get(p);
	for (int i = 0; i < mov.length; i++) {
		move[i]=mov[i];
	}
	move[4]=value;
	}
	if(chijiang[0]!=-1){
		move=chijiang;
	}
	return move;
}
}
