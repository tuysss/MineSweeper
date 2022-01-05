package UserInterface;

import ToolLibrary.Bomb;
import java.util.Random;

/*
 *该类用于在游戏窗口分配炸弹
 */

public class produceBombs extends Bomb {

	/**
	 * 继承Bomb，重写构造器，在给定游戏窗口创建该类的实例
	 * 使用递归函数避免炸弹位置重叠
	 * @param board 用户点击的游戏窗口
	 * @param number 炸弹总数
	 */
	public produceBombs(GameBoard board, int number) {
		super(board);
		
		int count=0;
		do {
			reproduceBomb();
			count++;
		}while(count<number);
	}

	/*
	 * 该类用于在游戏窗口随机生成炸弹的位置。
	 * 如果该位置已被占用（已点击并无炸弹存在、或者显示红旗->均会被HasTraversed标记），则通过调用自己重新生成位置
	 * */
	@Override
	public void reproduceBomb() {
		Random r=new Random();

		//xLocation定位，AKA，第横向xLocation，纵向yLocation个方块
		int xLocation=r.nextInt(boardWidth);
		int yLocation=r.nextInt(boardHeight);
		
		SmartSquare square=(SmartSquare)board.getSquareAt(xLocation, yLocation);
		
		if(!square.getBombExist()) {
			//标记该方块含有炸弹，并被遍历过
			square.setBombExist(true);
			square.setTraverse(true);
		}else {
			reproduceBomb();
		}
	}
}
