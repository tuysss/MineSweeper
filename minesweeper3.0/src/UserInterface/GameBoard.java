package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * 该类为游戏窗口提供图形模型
 * 该类创建了可点击的矩形模板
 * 如果玩家点击了小方块，会在相应的SmartSquare实例中调用回调函数
 */

public class GameBoard extends JFrame implements ActionListener{
	//创建游戏面板（大、背景），用以放置小方块
	private JPanel boardPanel =new JPanel();
	
	private int boardHeight;
	private int boardWidth;
	private GameSquare[][] board;
	
	/*
	 *  创建给定大小的游戏窗口。
	 * 一旦该类实例被创建，窗口将可视化。
	 *
	 * @param title 窗口栏的标题。
	 * @param width 以方块作单位的窗口的宽,AKA,窗口横向有width个方块
	 * @param height 以方块作单位的窗口的高，AKA,窗口纵向有height个方块
	 * 
	 * */
	public GameBoard(String title,int height,int width) {
		super();  //JFrame
		
		this.boardHeight=height;
		this.boardWidth=width;
		
		//创建游戏初始化方块
		this.board=new GameSquare[width][height];
		
		//新建窗口
		setTitle(title);
		setSize(width*20+20, height*20+20);
		setContentPane(boardPanel);
		//TODO: resizable
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		boardPanel.setLayout(new GridLayout(height,width)); //rows=height  cols=width

		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				board[x][y]=new SmartSquare(x, y, this);
				board[x][y].addActionListener(this);
				
				boardPanel.add(board[x][y]);
			}
		}
		setVisible(true);	
	}

	/*
	 * 如果 x 和 y 的位置都在边界范围内，则给出响应的方块对象，否则返回 null
	 * @param x 给定方块的 x 的坐标
	 * @param y 给定方块的 y 的坐标
	 * @return 返回给定位置的方块
	 * */

	public GameSquare getSquareAt(int x,int y) {
		if(x<0||x>=boardWidth||y<0||y>=boardHeight)
			return null;
		return board[x][y];
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//被点击的方块，需要处理点击情况
	
		GameSquare b=(GameSquare) e.getSource();
		b.clicked();
		
	}
	
	

}
