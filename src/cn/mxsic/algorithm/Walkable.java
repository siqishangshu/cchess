package cn.mxsic.algorithm;


import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.mxsic.attack.Attack;
import cn.mxsic.attack.Maintain;
import cn.mxsic.chess.Rule;
import cn.mxsic.chess.Chess;
public class Walkable {
	private Chess qizi[][];//用来接收开始棋局
	private Map<Integer, int[]> possible_map=new HashMap<Integer, int[]>();//用来保存可行的起始位置
	private Rule guize;//对每个可行性进行判断时再实例化
	private int zoufa=0;
	private int[] top_five_value=new int[5];
	private int[] five=new int[5];//保存索引值
	private Map<Integer, int[]> top_five_map=new HashMap<Integer, int[]>();//用来保存可行性的的路径
	private Estimated value;
	private Attack gong;
	private Maintain shou;
//	private static int chongfu=4;
	public Walkable(Chess qizi[][]) {
		// TODO Auto-generated constructor stub
		this.qizi=qizi;
		this.guize=new Rule(qizi);
		this.initial();
	}
	private void initial(){//得到所有可行性走法
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
	private void putPossibleMove(int i,int j,String name){
		//对每个棋子可走的位置进行遍历
		
			for (int k = 0; k < 9; k++) {//这种费时行为有助于其它棋子通用
				for (int k2 = 0; k2 < 10; k2++){
					if(qizi[k][k2]==null){//这里没有棋子时
					boolean can=false;
					can=guize.canMove(i, j, k, k2, name);//判断该位置是否可行
					if(can){
						putInMap(i, j, k, k2);
					}
					}else{//这里有棋子
						if(qizi[k][k2].getColor()!=Color.white){//棋子不能放在自己的棋子上面
							boolean can=false;
							can=guize.canMove(i, j, k, k2, name);//判断该位置是否可行
							if(can){
								putInMap(i, j, k, k2);
							}
						}
					}
					
				}
		}
	}
	private void putInMap(int startI,int startJ,int endI,int endJ){//用临时走法走到一下种棋局
		int tem[]=new int[4];
		tem[0]=startI;tem[1]=startJ;tem[2]=endI;tem[3]=endJ;
		possible_map.put(zoufa,tem);//将走法加入到map中？？？？？？
		//还原棋局
		zoufa++;//走法增加一种
	
	}
	public int[] getBestMove(){//得到最佳的走法
	
		value=new Estimated();
		for (int i = 0; i < zoufa; i++) {
			int temp[];//用临时的棋局来保存本次取出的走法后的棋局
			temp=possible_map.get(i);
			int v_tem=0;//临时�
			Chess temqizi=null;
			temqizi=qizi[temp[2]][temp[3]];//走一步
			qizi[temp[2]][temp[3]]=qizi[temp[0]][temp[1]];
			qizi[temp[0]][temp[1]]=null;
			v_tem=value.getValue(qizi);//得到该棋面的价值
			qizi[temp[0]][temp[1]]=qizi[temp[2]][temp[3]];//退一步
			qizi[temp[2]][temp[3]]=temqizi;
			for (int j = 0; j < 5; j++) {
				if(v_tem>top_five_value[j]){//得到前五的棋面值的集合
					int k=4;
					while(k>j){
						top_five_value[k]=top_five_value[k-1];
						five[k]=five[k-1];//保存下前五个棋面的map的key值
						k--;
						
					};
					top_five_value[j]=v_tem;
					five[j]=i;
					break;
				}
			}
		}
		for (int i = 0; i < five.length; i++) {//得到前五的棋面集合
			int temp[];
			temp=possible_map.get(five[i]);
			top_five_map.put(i,temp);
		}
		possible_map.clear(); //把空间释放。
		int move[]=new int[4];
		move=gongORshou();
		if(move[0]==0&&move[1]==0&&move[2]==0&&move[3]==0){
			Random rand=new Random();
			int i=rand.nextInt(4);
			move=top_five_map.get(i);
		}
		return move;
	}
	private int[] gongORshou(){
		gong=new Attack(qizi);
		shou=new Maintain(qizi);
		int fial[]=new int[4];
		int gongfa[]=gong.bestGong();
		int shoufa[]=shou.bestShou();
		
		if(shoufa[4]==-1){//护将
			//System.out.println("护将棋!");
			fial[0]=shoufa[0];fial[1]=shoufa[1];fial[2]=shoufa[2];fial[3]=shoufa[3];
			return fial;
		}
		if(shoufa[4]==-2){//
			//System.out.println("杀棋而守!");
			fial[0]=shoufa[0];fial[1]=shoufa[1];fial[2]=shoufa[2];fial[3]=shoufa[3];
			return fial;
		}
		if(gongfa[4]==-3){//杀将棋
			//System.out.println("杀将棋!");
			fial[0]=gongfa[0];fial[1]=gongfa[1];fial[2]=gongfa[2];fial[3]=gongfa[3];
			return fial;
		}
		if(gong.gongfa==0&&shou.paofa==0){
			fial[0]=0;fial[1]=0;fial[2]=0;fial[3]=0;
			//System.out.println("无攻无守");
		}
		if(gong.gongfa==0&&shou.paofa!=0){
			fial[0]=shoufa[0];fial[1]=shoufa[1];fial[2]=shoufa[2];fial[3]=shoufa[3];
			//System.out.println("无攻而守");
		}
		if(gong.gongfa!=0&&shou.paofa==0){
			fial[0]=gongfa[0];fial[1]=gongfa[1];fial[2]=gongfa[2];fial[3]=gongfa[3];
			//System.out.println("无守而攻");
		}
		if(gong.gongfa!=0&&shou.paofa!=0){
		if(50+gongfa[4]<shoufa[4]){
			fial[0]=shoufa[0];fial[1]=shoufa[1];fial[2]=shoufa[2];fial[3]=shoufa[3];
			//System.out.println("守比攻大");
		}else{
			fial[0]=gongfa[0];fial[1]=gongfa[1];fial[2]=gongfa[2];fial[3]=gongfa[3];
			//System.out.println("攻比守大");
		}
		}
		return fial;
	}
}