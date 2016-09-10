package cn.mxsic.attack;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import cn.mxsic.algorithm.Estimated;
import cn.mxsic.chess.Rule;
import cn.mxsic.chess.Chess;

public class Maintain {//以守为攻
	public Estimated selfvalue=new Estimated();
	public Chess qizi[][];
	public Map<Integer, int[]> shou_map=new HashMap<Integer, int[]>();//所有不安全棋子
	private  Map<Integer, int[]> pao_map=new HashMap<Integer, int[]>();//所有可以逃跑的走法
	private Map<Integer, int[]> hujiang_map=new HashMap<Integer, int[]>();//护将大法
	private int hujiangfa=0;
	public Rule guize;
	public int shoufa=0;//对方有多少种守法
	public int paofa=0;
	private Examine jiance=new Examine();
	private int jiangI,jiangJ;
	private boolean jiang=false;
	public Maintain(Chess qizi[][]) {
		// TODO Auto-generated constructor stub
		this.qizi=qizi;
		this.guize=new Rule(qizi);
		this.initial();
		this.taopiao();
	}
	private void initial(){//得到所有可行性走法
		for (int i = 0; i <9; i++) {
			for (int j = 0; j < 10; j++) {
				if(qizi[i][j]!=null){//判断此处有棋子
				if(qizi[i][j].getColor()==Color.yellow){//如果不是电脑的棋子
					putPossibleMove(i,j,qizi[i][j].getName());
				 }
				}
			}
		}
	}

	private void putPossibleMove(int i,int j,String name){
		//对每个棋子可走的位置进行遍历
			for (int k = 0; k < 9; k++) {//这种费时行为有助于其它棋子通用
				for (int k2 = 0; k2 < 10; k2++){
					if(qizi[k][k2]!=null){//这里有棋子
						if(qizi[k][k2].getColor()==Color.white){//如果是电脑的棋子
							boolean can=false;
							can=guize.canMove(i, j, k, k2, name);//判断该对方是否可以吃自己，
							if(can){//如果可以吃
								if(qizi[k][k2].getName().equals("帅")){//被将军
									jiangI=k;jiangJ=k2;
									jiang=true;
									break;
								}else{
									putInMap(i,j,k, k2);//将对方可吃自己的走法放入map中
								}
							}
						}
					
					
				}
		}
	}
	}
	private void putInMap(int startI,int startJ,int endI,int endJ){//用临时走法走到一下种棋局
		int tem[]=new int[4];
		tem[0]=startI;tem[1]=startI;tem[2]=endI;tem[3]=endJ;
		shou_map.put(shoufa,tem);//将走法加入到map中？？？？？？
		//还原棋局
		shoufa++;//走法增加一种
	}
	private void taopiao(){
		if(shoufa==0){//如果有走法
		}else{
		for (int i = 0; i < shoufa; i++) {
			int tem[]=new int[4];
			int t[]=new int[4];
			t=shou_map.get(i);
			int a=t[2],b=t[3];
			for (int j = 0; j < 9; j++) {
				for (int j2 = 0; j2 < 10; j2++) {
						if(qizi[j][j2]==null||qizi[j][j2].getColor()==Color.yellow){//如果逃走的地方有棋子，但不是自己的
						boolean can=false;
						can=guize.canMove(a, b, j, j2, qizi[a][b].getName());
						if(can){
							boolean safe=false;
							safe=jiance.getJianCe(qizi, a, b, j, j2);
							if(safe){
								tem[0]=a;tem[1]=b;tem[2]=j;tem[3]=j2;
								pao_map.put(paofa, tem);
								paofa++;
							}
						}
					}
			}
		}
		}
		}
	}
	public int[] hujiang(){//不能单纯为攻为解决，而应该有用所有可能的走法，解除将寰
		int tem[]=new int[5];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++){
				if(qizi[i][j]!=null){
					if(qizi[i][j].getColor()==Color.white){//如果有电脑的棋子可以吃hj wbn
						for (int j2 = 0; j2 < 9; j2++) {
							for (int k = 0; k < 10; k++) {
								if((qizi[j2][k]!=null&&qizi[j2][k].getColor()!=Color.white)||qizi[j2][k]==null){//if therer is no self qizi
								boolean can=false;
								can=guize.canMove(i, j, j2, k, qizi[i][j].getName());
								if(can){//如果电脑此棋可行
									//System.out.println();
									boolean st=true;
									Chess temq=null;
									temq=qizi[j2][k];
									qizi[j2][k]=qizi[i][j];qizi[i][j]=null;//行棋度法
									for (int l = 0; l <9; l++) {
										for (int l2 = 0; l2 < 10; l2++) {//二次将军检测
											if(qizi[l][l2]!=null&&qizi[l][l2].getColor()==Color.yellow){
												boolean still=false;
												still=guize.canMove(l, l2, jiangI, jiangJ, qizi[l][l2].getName());
												if(still){
													st=false;
												}
											}
										}
									}
									if(!st){//存在二次将军
										qizi[i][j]=qizi[j2][k];qizi[j2][k]=temq;//恢复
										continue;
									}else{//解除将军
										qizi[i][j]=qizi[j2][k];qizi[j2][k]=temq;//恢复
										tem[0]=i;tem[1]=j;tem[2]=j2;tem[3]=k;tem[4]=-1;
										hujiang_map.put(hujiangfa, tem);
										hujiangfa++;
									break;
									}
								}
							}
						}
						}
					}
				}
			}
		}
		Attack gong=new Attack(qizi);
		for (int i = 0; i < hujiangfa; i++) {
			int temf[]=new int[5];
			temf=hujiang_map.get(i);
			for (int j = 0; j < gong.gongfa; j++) {
				int te[]=new int[5];
				te=gong.gong_map.get(j);
				if(temf[0]==te[0]&&temf[1]==te[1]&&temf[2]==te[2]&&temf[3]==te[3]){//找到公法
					tem=temf;
				break;
				}
			}
		}
		return tem;
	}

	public int[] bestShou(){
		int move[]=new int[5];
		if(jiang){//如果被将军，采用特殊算法
			move=hujiang();
			jiang=false;
		}else{
		if(paofa==0){//如果没有走法
			for (int i = 0; i < move.length; i++) {
				move[i]=0;
			}
		}else{
		int value=0;
		int p=0;//标记位置
		for (int i = 0; i < paofa; i++) {
			int v=0;
			int tem[]=new int[4];
			tem=pao_map.get(i);
			int a=tem[0],b=tem[1],c=tem[2],d=tem[3];
			String name=qizi[a][b].getName();
			boolean can=false;
			can=guize.canMove(a,b, c, d, name);
			if(can){
				v=selfvalue.getOne(name,Color.white, c, d);
				if(v>value){
					v=value;
					p=i;
				}
			}
			
		}
		int mov[]=new int[4];
		mov=pao_map.get(p);
		for (int i = 0; i < mov.length; i++) {
			move[i]=mov[i];
		}
		move[4]=value;
		}
		move=yigongweishou(move);//进行可攻的
		}
		
		return move;
	}
	private int[] yigongweishou(int mo[]){
		Attack gong=new Attack(qizi);
		int move[]=mo;
		int mov[]=new int[4];
		for (int k = 0; k <paofa; k++) {//对所有逃跑棋子进行
			mov=pao_map.get(k);
		int weixian[]={-1,-1};
		for (int i = 0; i < shoufa; i++) {
			int te[]=shou_map.get(i);
			if(mov[0]==te[2]&&mov[1]==te[3]){//找到所受的威胁
				weixian[0]=te[0];weixian[1]=te[1];
				break;
			}
		}
		if(weixian[0]!=-1){
			for (int i = 0; i < gong.gongfa; i++) {
				int tem[]=gong.gong_map.get(i);
					if(weixian[0]==tem[2]&&weixian[1]==tem[3]){//找到以攻为守的方法
					move[0]=tem[0];	move[1]=tem[1];	
					move[2]=tem[2];	move[3]=tem[3];	
					move[4]=-2;	
					}
			}
		}
		}
		return move;
	}
}
