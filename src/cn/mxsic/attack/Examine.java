package cn.mxsic.attack;

import java.awt.Color;



import cn.mxsic.chess.Rule;
import cn.mxsic.chess.Chess;

public class Examine {
	private Rule guize;
	private int jiang[]={4,0,3,0,5,0,
						4,1,3,1,5,1,
						4,2,3,2,5,2,};
	public Examine(){
	}
	public boolean getJianCe(Chess qizi[][],int startI,int startJ,int endI,int endJ){
		boolean safe=true;
		Chess tem=null;
		tem=qizi[endI][endJ];
		qizi[endI][endJ]=qizi[startI][startJ];
		qizi[startI][startJ]=null;
		for (int j = 0; j <9; j++) {
				for (int j2 = 0; j2 < 10; j2++) {
					if(qizi[j][j2]!=null){
						if(qizi[j][j2].getColor()==Color.yellow){//如果是人的棋子可以吃我
						String name=qizi[j][j2].getName();
						guize=new Rule(qizi);
						boolean can=false;
						can=guize.canMove(j, j2, endI, endJ, name);
						if(can){//如果能被吃掉，则返回不安全
							safe=false;
							break;
						}else{
							boolean anquan=false;
							anquan=jiangjunjianci(qizi);
							if(!anquan){
								//System.out.println(qizi[endI][endJ].getName()+"cant leave");
								safe=false;
							}
						}
					}
					}
					
				}
			}
		qizi[startI][startJ]=qizi[endI][endJ];
		qizi[endI][endJ]=tem;
		tem=null;
		return safe;
	}
	private boolean jiangjunjianci(Chess[][] qizi) {
		// TODO Auto-generated method stub
		boolean safe=true;
		int jiangI=0;int jiangJ=0;
		for (int i = 0; i < jiang.length+1/2; i++) {
			int a=jiang[i];int b=jiang[i+1];
			if(qizi[a][b]!=null&&qizi[a][b].getName().equals("帅")){
				jiangI=a;jiangJ=b;
				break;
			}
			else{
				i++;
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				if(qizi[i][j]!=null){
					if(qizi[i][j].getColor()==Color.yellow){
						boolean can=false;
						can=guize.canMove(i, j, jiangI, jiangJ, qizi[i][j].getName());
						if(can){
							safe=false;
						
							break;
						}
					}
				}
			}
		}
		return safe;
	}
}
