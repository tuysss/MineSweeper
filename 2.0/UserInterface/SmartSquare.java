package UserInterface;

import ToolLibrary.TimeChecker;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 该类继承GameSquare类，重写了clicked()方法，该方法需要实现以下功能：
 * 1.玩家左键单击board会显示该方块周围的炸弹数量
 * 2.玩家右键单击board会显示小红旗imageIcon
 * 3.玩家右键双击board会显示问号imageIcon
 */
public class SmartSquare extends GameSquare implements TimeChecker,MouseListener {

	//炸弹是否在这个方块上
	private boolean thisSquareHasBomb;
	
	//玩家是否在这个方块上放置红旗
	private boolean guessThisSquareIsBomb;
	
	//该方块是否被遍历过
	private boolean thisSquareHasTraversed;
	
	private int xLocation;
	private int yLocation;
	
	private long startTime;
	
	/*
	 * 创建该类的实例，把该实例放在游戏窗口上
	 * @param x 该方块相对于游戏窗口的x坐标
	 * @param y 该方块相对于游戏窗口的y坐标
	 * @param board 该方块所在的游戏窗口
	 * */
	public SmartSquare(int x, int y, GameBoard board) {
		//Implicit super constructor GameSquare() is undefined. Must explicitly invoke another constructor
		//初始化时将方块设置成灰色
		//！！！需要灰色图标
		super(x, y, SmartSquare.class.getResource("/block.png"), board);
		
		xLocation=x;
		yLocation=y;
		
		//初始化属性
		thisSquareHasBomb=false;
		thisSquareHasTraversed=false;
		guessThisSquareIsBomb=false;
		startTime=0;
		
		//右键添加监听按钮
		addMouseListener(this);	
		
	}
	

	protected void setBombExist(boolean result) {
		thisSquareHasBomb=result;
	}

	protected boolean getBombExist() {
		return thisSquareHasBomb;
	}

	protected boolean getGuessThisSquareIsBomb(){
		return guessThisSquareIsBomb;
	}

	protected void setTraverse(boolean result) {
		thisSquareHasTraversed=result;
	}

	protected boolean getTraverse() {
		return thisSquareHasTraversed;
	}

	protected void setStartTime(long time) {
		startTime=time;
	}

	protected long getStartTime() {
		return startTime;
	}



	/*
	 * @Override clicked() from extended abstract class:GameSquare
	 * 
	 * */
	public void clicked() {
		CheckSquare check=new CheckSquare(board);
		
		guessThisSquareIsBomb=false;
		
		if(thisSquareHasBomb) {
			/*
			 * 如果该方块包含炸弹:
			 * 1.显示炸弹图标
			 * 2.本轮游戏结束，弹出失败窗口
			 */
			setImage(SmartSquare.class.getResource("/bombReveal.png"));
			long costTime=System.currentTimeMillis()-((SmartSquare)board.getSquareAt(0, 0)).getStartTime();
			check.showAllBomb(xLocation,yLocation);
			showWindow("Game Over.","You used "+TimeChecker.calculateTime(costTime)+". Do you want to try again?",
					new ImageIcon(SmartSquare.class.getResource("/failFace.png")));
		}else {
			thisSquareHasTraversed=false;
			/*
			 * 如果该方块不包含炸弹，计算它周围八个格子的炸弹总数
			 * 如果周围没有炸弹，则扩大空白区域直到检测到炸弹或者越界
			 * 如果该次点击后成功，弹出窗口。
			 * */
			check.countBomb(xLocation,yLocation);
			
			if(check.isSuccess()) {
				long costTime=System.currentTimeMillis()-((SmartSquare)board.getSquareAt(0, 0)).getStartTime();
				check.showAllBomb(xLocation, yLocation);//???
				showWindow("Congratulations","You win this game in " + TimeChecker.calculateTime(costTime) +
						"! Do you want to try again?",
						new ImageIcon(SmartSquare.class.getResource("/passFace.jpg")));
				
			}
		}
	}


	/*
	 * @param msg :message to show on this window
	 * @param title:
	 * @param img:
	 * */
	public void showWindow(String title,String msg,Icon img) {
		int choose=JOptionPane.showConfirmDialog(board, msg,title,JOptionPane.YES_NO_OPTION,JOptionPane.CANCEL_OPTION,img);
		if(choose==JOptionPane.YES_OPTION) {
			new Menu("Mine Sweeper");
		}
		board.dispose();
	}

	/*
	 * 实现对鼠标右键时间的响应
	 * @param e 鼠标右键事件
	 * */
	@Override
	public void mouseClicked(MouseEvent e) {
		//用户右击
		if(e.getButton()==MouseEvent.BUTTON3) {
			int clickCount=e.getClickCount();
			//右击一下，显示小红旗
			if(clickCount==1) {
				setImage(SmartSquare.class.getResource("/redFlag.png"));
				guessThisSquareIsBomb=true;
			}
			//右击两下，显示问号
			if(clickCount==2) {
				setImage(SmartSquare.class.getResource("/questionMark.png"));
				guessThisSquareIsBomb=false;
			}		
		}		
	}


	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	

}
