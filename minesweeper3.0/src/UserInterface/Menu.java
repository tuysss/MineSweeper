package UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;


public class Menu extends JFrame implements ActionListener{
	private JButton start;
	private JRadioButton beginner,intermediate,advanced,custom;
	private JTextField width,height,mine_number;
	
	public Menu(String title) {
		//设置菜单标题
		setTitle(title);
		
		//设置子菜单标题
		JLabel subtitle=new JLabel("Difficulty");
		subtitle.setBounds(100, 10, 100, 20);
		add(subtitle);
		
		//创建“初级”选择按钮及其响应
		beginner=new JRadioButton("Beginner");
		beginner.setBounds(40, 40, 150, 20);
		add(beginner);
		
		//创建“初级”选项的描述
		JLabel bDecsFirstLine=new JLabel("10 mines.");
		bDecsFirstLine.setBounds(70,60,100,20);
		JLabel bDecsSecondLine=new JLabel("10*10 grids.");
		bDecsSecondLine.setBounds(70,80,100,20);
		add(bDecsFirstLine);
		add(bDecsSecondLine);
		
		//创建“中级”选择按钮及其响应
		intermediate=new JRadioButton("intermediate");
		intermediate.setBounds(40, 100, 150, 20);
		add(intermediate);
		
		//创建“中级”选项的描述
		JLabel iDecsFirstLine=new JLabel("40 mines.");
		iDecsFirstLine.setBounds(70,120,100,20);
		JLabel iDecsSecondLine=new JLabel("16x16 grids.");
		iDecsSecondLine.setBounds(70,140,100,20);
		add(iDecsFirstLine);
		add(iDecsSecondLine);
		
		//创建“高级”选择按钮及其响应
		advanced=new JRadioButton("advanced");
		advanced.setBounds(40, 160, 160, 20);
		add(advanced);
		
		//创建“高级”选项的描述
		JLabel aDecsFirstLine=new JLabel("100 mines.");
		aDecsFirstLine.setBounds(70,180,100,20);
		JLabel aDecsSecondLine=new JLabel("30*25 grids.");
		aDecsSecondLine.setBounds(70,200,100,20);
		add(aDecsFirstLine);
		add(aDecsSecondLine);
		
		//创建"自定义"选择按钮
		custom=new JRadioButton("custom");
		custom.setBounds(40, 220, 100, 20);
		add(custom);
		
		//创建“自定义”选项的描述
		//TODO: modify label size
		JLabel widthLabel=new JLabel("Width(10-30):");
		widthLabel.setBounds(70,240,140,20);
		add(widthLabel);
		
		width=new JTextField();
		width.setBounds(200,240,40,20);
		add(width);
		
		JLabel heightLabel=new JLabel("Height(10-25):");
		heightLabel.setBounds(70,260,140,20);
		add(heightLabel);
		
		height=new JTextField();
		height.setBounds(200,260,40,20);
		add(height);
		
		JLabel mine_numberLabel=new JLabel("Mine number(10-100):");
		mine_numberLabel.setBounds(70,280,140,20);
		add(mine_numberLabel);
		
		mine_number=new JTextField();
		mine_number.setBounds(200,280,40,20);
		add(mine_number);
		
		//创建“开始游戏”按钮及其响应
		start=new JButton("New Game");
		start.setBounds(80,320,100,20);
		add(start);

		// 在每个按键上添加监听事件。
        custom.addActionListener(this);
        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        start.addActionListener(this);
		
		//初始化每个文本框的编辑状态
		width.setEditable(false);
		height.setEditable(false);
		mine_number.setEditable(false);
		
		//确保单选
		ButtonGroup group=new ButtonGroup();
		group.add(beginner);
		group.add(intermediate);
		group.add(advanced);
		group.add(custom);
		
		//初始化菜单实例
		start.setSelected(true);
		setLayout(null);
		setSize(300,400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
		
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean flag=true;
		if(e.getSource()==custom) {
			//如果用户选择“自定义”，则设置文本框为可编辑状态
			width.setEditable(true);
			height.setEditable(true);
			mine_number.setEditable(true);
			
		}else if (e.getSource()==start) {
			//如果用户点击“start”按钮，则显示本轮游戏面板的长、宽、炸弹数量
			int boardWidth=0;
			int boardHeight=0;
			int boardMineNumber=0;		
		
			if(beginner.isSelected()) {
				boardWidth=10;
				boardHeight=10;
				boardMineNumber=10;			
			}else if(intermediate.isSelected()) {
				boardWidth=16;
				boardHeight=16;
				boardMineNumber=40;			
			}else if(advanced.isSelected()) {
				boardWidth=30;
				boardHeight=25;
				boardMineNumber=100;			
			}else {
				//TODO: set a limit
				flag=valid(width.getText(),height.getText(),mine_number.getText());
				if(flag==true){
					boardWidth=Integer.parseInt(width.getText());
					boardHeight=Integer.parseInt(height.getText());
					boardMineNumber=Integer.parseInt(mine_number.getText());
				}else{
					JOptionPane.showMessageDialog(null,"please input correct information:");
				}
			}

			if(flag==true){
				//结束步骤：关闭当前的菜单窗口，弹出相应的游戏窗口
				this.dispose();
				GameBoard b=new GameBoard(getTitle(), boardHeight, boardWidth);
				new produceBombs(b, boardMineNumber);
				((SmartSquare)b.getSquareAt(0, 0)).setStartTime(System.currentTimeMillis());
			}
		}
	}

	private boolean valid(String inWidth,String inHeight,String mines){

		Pattern pattern=Pattern.compile("[0-9]*");
		if(inWidth==null||inHeight==null||mines==null)
			return false;
		else if(inWidth.isEmpty()||inHeight.isEmpty()||mines.isEmpty())
			return false;
		else if(!pattern.matcher(inWidth).matches()||!pattern.matcher(inHeight).matches()||!pattern.matcher(mines).matches())
			return false;
		else if(Integer.parseInt(inWidth)<10||Integer.parseInt(inWidth)>30||Integer.parseInt(inHeight)<10
			||Integer.parseInt(inHeight)>25||Integer.parseInt(mines)<10||Integer.parseInt(mines)>100)
			return false;
		else if(Integer.parseInt(inWidth)*Integer.parseInt(inHeight)<=Integer.parseInt(mines))
			return false;
		else
			return true;
	}
}
