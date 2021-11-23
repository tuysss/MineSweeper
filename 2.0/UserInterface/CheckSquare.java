package UserInterface;

public class CheckSquare {
	
	//游戏窗口实例(大)
	private GameBoard board;
	
	//实例窗口的高度，AKA，纵向上的方块数
	private int boardHeight;
	
	//实例窗口的宽度，AKA，横向上的方块数
	private int boardWidth;

	private static final int[] distantX= {-1,0,1};
	private static final int[] distantY= {-1,0,1};

	/*
	 * 在游戏窗口中创建该类的实例
	 * */
	public CheckSquare(GameBoard board) {
		this.board=board;
		//每一个方块20*20，边框长度20
		boardHeight=(board.getHeight()-20)/20;
		boardWidth=(board.getWidth()-20)/20;
	}


	public boolean hasKickedBoundary(int x,int y) {
		return x<0||y<0||x>=boardWidth||y>=boardHeight;
	}


	public void showAllBomb(int currentX, int currentY) {
		for(int y=0;y<boardHeight;y++) {
			for(int x=0;x<boardWidth;x++) {
				if(currentX==x&&currentY==y) {}
				else if(((SmartSquare) board.getSquareAt(x, y)).getBombExist())
					board.getSquareAt(x, y).setImage(CheckSquare.class.getResource("/bomb.png"));
				else if(((SmartSquare) board.getSquareAt(x, y)).getGuessThisSquareIsBomb())
					board.getSquareAt(x, y).setImage(CheckSquare.class.getResource("/flagWrong.png"));  //wrong guess!
			}
		}
	}

	/**
	 * 计算指定方块周围的炸弹总数
	 * 如果该方块周围没有炸弹，1：把该方块描绘成白色，2：扩大检测范围until周围的炸弹总数不为0
	 * 递归
	 * @param currentX 该方块的x坐标
	 * @param currentY 该方块的y坐标
	 */
	public void countBomb(int currentX, int currentY) {
		int count=0;
		SmartSquare currentObject;
		
		if(hasKickedBoundary(currentX, currentY)) 
			return;//若当前位置出界，则无需检验
		else if(((SmartSquare)board.getSquareAt(currentX, currentY)).getTraverse())
			return;//若当前位置已检验，则无需检验
		else {
			//实例化周围的方块
			SmartSquare squareObject;
			//获取当前方块对象
			currentObject=(SmartSquare)board.getSquareAt(currentX, currentY);
			currentObject.setTraverse(true);
			
			/*
			 * 检测当前方块周围八个方块的情况：
			 * 1.若kick边界，跳出本轮循环
			 * 2.排除（0,0）是自己情况
			 * 否则，count累计周围炸弹数
			 * */
			for(int x:distantX) {
				for(int y:distantY) {
					if(hasKickedBoundary(currentX+x, currentY+y)) {}//break仅对循环！
					else if(x==0&&y==0)	{}
					else {
						squareObject=(SmartSquare)board.getSquareAt(currentX+x, currentY+y);
						if(squareObject.getBombExist())
							count++;
					}
				}
			}		
		}
		/*
		 * 如果该方块周围有炸弹，则显示周围的炸弹数（即：显示响应图标）
		 * 若count==0，则以该方块周围的方块为中心进行检测->直到kick或者count！=0
		 * */
		if(count!=0) {
			currentObject.setImage(CheckSquare.class.getResource("/"+count+".png"));
		}else {
			//将该方块渲染为空白
			currentObject.setImage(CheckSquare.class.getResource("/0.png"));
            //递归向外check
			countBomb(currentX - 1, currentY -1); // 左上
            countBomb(currentX, currentY -1); // 正上
            countBomb(currentX + 1, currentY -1); // 右上
            countBomb(currentX - 1, currentY); // 正左
            countBomb(currentX + 1, currentY); // 正右
            countBomb(currentX - 1, currentY + 1); // 左下
            countBomb(currentX, currentY + 1); // 正下
            countBomb(currentX + 1, currentY + 1); // 右下			
		}
		
	}

	public boolean isSuccess() {
		
		for(int y=0;y<boardHeight;y++) {
			for(int x=0;x<boardWidth;x++) {
				if(!((SmartSquare)board.getSquareAt(x, y)).getTraverse())
					return false;
			}
		}
		return true;
	}

}
