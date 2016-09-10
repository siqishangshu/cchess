package cn.mxsic.chess;

public class Rule {
	Chess[][] qizi;//棋子的数组
	boolean  canMove=false;//是否可以移动
	int i;//棋子的x的坐标
	int j;//棋子的y的坐标
	public Rule(Chess[][] qizi){
		this.qizi=qizi;
	}
	public boolean canMove(int startI,int startJ,int endI,int endJ,String name){
		int maxI;int minI;int maxJ;int minJ;//辅助量
		canMove=true;
		if(startI>=endI){maxI=startI;minI=endI;}//起始坐标的大小 关系
		else{
			maxI=endI;minI=startI;
		}
		if(startJ>=endJ){maxJ=startJ;minJ=endJ;}//起始坐标的大小 关系
		else{
			maxJ=endJ;minJ=startJ;
		}
		if(name.equals("车")){this.ju(maxI,minI,maxJ,minJ);}//判断不同的棋子对应不同的移动规则
		else if(name.equals("马")){this.ma(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		else if(name.equals("相")){this.xiang1(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		else if(name.equals("象")){this.xiang2(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		else if(name.equals("士")||name.equals("仕")){this.shi(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		else if(name.equals("帅")||name.equals("将")){this.jiang(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		else if(name.equals("炮")||name.equals("宠")){this.pao(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		else if(name.equals("兵")){this.bing(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		else if(name.equals("卒")){this.zu(maxI,minI,maxJ,minJ,startI,startJ,endI,endJ);}
		return canMove;
	}
	private void zu(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		if(startJ>4){//还没有过河
			if(startI!=endI){//如果不是向前走
				canMove=false;
			}
			if(endJ-startJ!=-1){//如果不是走一格
				canMove=false;
			}
		}else{//如果已经过河了
			if(startI==endI){//如果是向前走
				if(endJ-startJ!=-1){//如果不是走一格
					canMove=false;
				}
			}else if(startJ==endJ){//如果 是走横线
				if(maxI-minI!=1){//如果不是走一格
					canMove=false;
				}
			}else if(startI!=endI&&startJ!=endJ){//如果走的不是竖线也不是横线
				canMove=false;
			}
			
		}
	}
	private void bing(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		 if(startI!=endI&&startJ!=endJ){//如果走的不是竖线也不是横线
				canMove=false;
			}
		if(startJ<5){//还没有过河
			if(startI!=endI){//如果不是向前走
				canMove=false;
			}
			if(endJ-startJ!=1){//如果不是走一格
				canMove=false;
			}
		}else{//如果已经过河了
			if(startI==endI){//如果是向前走
				if(endJ-startJ!=1){//如果不是走一格
					canMove=false;
				}
			}else if(startJ==endJ){//如果 是走横线
				if(maxI-minI!=1){//如果不是走一格
					canMove=false;
				}
			}
			
		}
	}
	private void pao(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		if(maxJ!=minJ&&maxI!=minI){//如果不在同一条线上
			canMove=false;
		}
		if(maxI==minI){//如果在一条竖线上
			if(qizi[endI][endJ]!=null){
				int count=0;//统计此线上的棋子数
				for (int j=minJ+1; j < maxJ; j++) {//遍历这条线
					if(qizi[minI][j]!=null){
						count++;
					}
				}
				if(count!=1){
					canMove=false;
				}
			}else 
				if(qizi[endI][endJ]==null){//如果终点没有棋子
				for (int j = minJ+1; j < maxJ; j++) {
					if(qizi[minI][j]!=null){
						canMove=false;
						break;
					}
				}
			}
		}else if(maxJ==minJ){//如果在一条横线上
			if(qizi[endI][endJ]!=null){
				int count=0;//统计此线上的棋子数
				for (int i =minI+1; i < maxI; i++) {//遍历这条线
					if(qizi[i][minJ]!=null){
						count++;
					}
				}
				if(count!=1){
					canMove=false;
				}
			}else if(qizi[endI][endJ]==null){//如果终点没有棋子
				for (int i = minI+1; i < maxI; i++) {
					if(qizi[i][minJ]!=null){//如果起止点有棋子
						canMove=false;
						break;
					}
				}
			}
		}
		
	}
	private void jiang(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		int a=maxI-minI;int b=maxJ-minJ;//得到两点的坐标差
		if((a==1&&b==0)||(a==0&&b==1)){//如果是田字
			if(startJ>4){//如果是下方的将
				if(endJ<7){
					canMove=false;//下方的越界
				}
			}else{//如果是上方的将
				if(endJ>2){
					canMove=false;
				}
			}
			if(endI>5||endI<3){//如果左右越界
			canMove=false;
			}
		}else{//如果不是小斜线
			canMove=false;
		}
	}
	private void shi(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		int a=maxI-minI;int b=maxJ-minJ;//得到两点的坐标差
		if(a==1&&b==1){//如果是田字
			if(startJ>4){//如果是下方的
				if(endJ<7){
					canMove=false;//下方的越界
				}
			}else{//如果是上方的
				if(endJ>2){
					canMove=false;
				}
			}
			if(endI>5||endI<3){//如果左右越界
			canMove=false;
			}
		}else{//如果不是小斜线
			canMove=false;
		}
	}
	private void xiang2(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		int a=maxI-minI;int b=maxJ-minJ;//得到两点的坐标差
		if(a==2&&b==2){//如果是田字
			if(endJ<5){//如果过河了
				canMove=false;
			}
			if(qizi[(maxI+minI)/2][(maxJ+minJ)/2]!=null){//如果田字中间有棋子
				canMove=false;
			}
		}else{//如果不是田字，不可走
			canMove=false;
		}
	}
	private void xiang1(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		int a=maxI-minI;int b=maxJ-minJ;//得到两点的坐标差
		if(a==2&&b==2){//如果是田字
			if(endJ>4){//如果过河了
				canMove=false;
			}
			if(qizi[(maxI+minI)/2][(maxJ+minJ)/2]!=null){//如果田字中间有棋子
				canMove=false;
			}
		}else{//如果不是田字，不可走
			canMove=false;
		}
	}
	private void ma(int maxI, int minI, int maxJ, int minJ, int startI,
			int startJ, int endI, int endJ) {
		// TODO Auto-generated method stub
		int a=maxI-minI;int b=maxJ-minJ;//获得两位置间的差
		if(!((a==2&&b==1)||(a==1&&b==2))){//如果不是日字
			canMove=false;
		}
		if(a==1&&b==2){//如果是竖着的日字
			if(startJ>endJ){//如果是向上走
				if(qizi[startI][startJ-1]!=null){
					canMove=false;
				}
			}else{//如果晌下走
				if(qizi[startI][startJ+1]!=null){
					canMove=false;
				}
			}
		}else if(a==2&&b==1){//如果横着的日字
			if(startI>endI){//如果是从向左走
			if(qizi[startI-1][startJ]!=null){
				canMove=false;
			}}
			else {//如果是向右走
				if(qizi[startI+1][startJ]!=null){
					canMove=false;
				}
			}
		}
	}
	private void ju(int maxI, int minI, int maxJ, int minJ) {
		// TODO Auto-generated method stub
		if(maxI!=minI&&maxJ!=minJ){
			canMove=false;
		}
		if(maxI==minI){//在同一种条竖线上
			for (int j = minJ+1; j <maxJ; j++) {
				if(qizi[maxI][j]!=null){
				canMove=false;
				break;
				}
			}
		}
		if(maxJ==minJ){//在同一条横线上
			for (int i = minI+1; i<maxI; i++) {
				if(qizi[i][maxJ]!=null){
				canMove=false;
				break;
				}
			}
		}
	}

}
