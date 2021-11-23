import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
该类提供游戏面板的显示实例
 */
public class GameBoard extends JFrame implements ActionListener {
    private JPanel boardPanel=new JPanel();
    private int width;
    private int height;
    private GameSquare[][] board;

    public GameBoard(int width, int height, int bombs) {
        //创建panel
        super();

        this.width=width;
        this.height=height;

        this.board=new GameSquare[width][height];

        setTitle("MineSweeper");
        setSize(20+20*width,20+20*height);
        setResizable(false);
        setContentPane(boardPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        //创建panel上的方格（和panel同层次）,以grid为单位监听时间
        boardPanel.setLayout(new GridLayout(height,width)); //rows.cols

        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                board[x][y] = new GameSquare(x, y, GameSquare.class.getResource("/block.png"),this);
                board[x][y].addActionListener(this);

                boardPanel.add(board[x][y]);
            }
        }

       new Bomb(this,bombs);

       setVisible(true);
    }


    public GameSquare getSquareAt(int x,int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return null;

        return board[x][y];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //点击按钮
        GameSquare b=(GameSquare)e.getSource();
        b.clickCheck();
    }
}
