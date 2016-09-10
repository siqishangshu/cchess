package cn.mxsic.algorithm;


import cn.mxsic.chess.Chess;

public class Ai {
	private int[] move_way={0,0,0,0};//生成电脑的走法0,1起点，2,3是止点
	private Chess qizi[][];//人走后的棋面
	private Walkable moves;
	public Ai(Chess qizi[][]) {
		// TODO Auto-generated constructor stub
		this.qizi=qizi;
	}
	public int[] getMove(){//用原来棋面，和生成的最佳棋面的差别得出最佳走法
		moves=new Walkable(this.qizi);
		move_way=moves.getBestMove();//得到最佳走法下的棋面
		return move_way;
	}

}
