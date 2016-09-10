package cn.mxsic.chess;

import java.awt.Color;

public class Chess {

	private Color color;
	private String name=null;
	private int x;
	private int y;
	private boolean focus=false;
	public Chess(Color color,String name,int x,int y) {
		// TODO Auto-generated constructor stub
		this.color=color;this.name=name;
		this.x=x;this.y=y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean getFocus() {
		return focus;
	}
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	
}
