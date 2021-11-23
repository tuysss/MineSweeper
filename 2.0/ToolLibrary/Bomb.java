package ToolLibrary;

import UserInterface.GameBoard;

/*
 * 这个抽象类的抽象方法会在被继承时实现
 */

public abstract class Bomb {
	
	protected GameBoard board;
	
	protected int boardHeight;
	
	protected int boardWidth;
	
	/*
	 * 创建炸弹，将被放置在Gameboard上
	 * @param board 用户将点击的游戏界面
	 */
	public Bomb(GameBoard board) {
		this.board=board;
		boardHeight=(board.getHeight()-20)/20;
		boardWidth=(board.getWidth()-20)/20;
	}
	
	protected abstract void reproduceBomb();
	

}
