import javax.swing.*;
import java.awt.event.*;
import java.net.URL;


public class GameSquare extends JButton implements MouseListener {

    private boolean thisSquareHasBomb;
    private boolean guessThisSquareIsBomb;
    private boolean thisSquareHasTraversed;
    private int xLocation;
    private int yLocation;
    protected GameBoard board;
    private static final int[] distantX = {-1, 0, 1};
    private static final int[] distantY = {-1, 0, 1};

    public GameSquare(int x, int y, URL filename,GameBoard board){
        //TODO
        super(new ImageIcon(filename));

        this.board = board;
        xLocation = x;
        yLocation = y;

        thisSquareHasBomb = false;
        thisSquareHasTraversed = false;
        guessThisSquareIsBomb = false;

        addMouseListener(this);
    }


    public void setImage(URL filename) {
        this.setIcon(new ImageIcon(filename));
    }

    protected void setBombExist(boolean result) {
        thisSquareHasBomb = result;
    }

    protected boolean getBombExist() {
        return thisSquareHasBomb;
    }

    protected boolean getTraverse() {
        return thisSquareHasTraversed;
    }

    protected void setTraverse(boolean result) {
        thisSquareHasTraversed = result;
    }

    protected boolean getGuessThisSquareIsBomb() {
        return guessThisSquareIsBomb;
    }

    public void clickCheck() {
        guessThisSquareIsBomb=false;

        //TODO:递归查找周围八个grid炸弹数
        if(thisSquareHasBomb){
            setImage(GameSquare.class.getResource("/bombReveal.png"));
            JOptionPane.showMessageDialog(board,"failed.");
        }else{
             bombCount(xLocation,yLocation);
             thisSquareHasTraversed=true;
             if(isSuccess()){
                 JOptionPane.showMessageDialog(board,"Winned.");
             }
        }
    }

    private void bombCount(int currX,int currY){
        int count=0;
        GameSquare currSquare;
        if(isKickedBoundary(currX,currY)){
            return;
        }
        else if(board.getSquareAt(currX,currY).getTraverse()){
            return;
        }
        else{
            GameSquare neighbourSquare;
            currSquare= board.getSquareAt(currX,currY);
            currSquare.setTraverse(true);

            for(int x:distantX){
                for(int y:distantY){
                    if(isKickedBoundary(currX+x,currY+y)){}
                    else if(x==0&&y==0){}
                    else{
                        neighbourSquare= board.getSquareAt(currX+x,currY+y);
                        count=neighbourSquare.getBombExist() ? count+1 : count;
                    }
                }
            }
        }
        if(count!=0)
            currSquare.setImage(GameSquare.class.getResource( "/" + count + ".png"));
        else{
            currSquare.setImage(GameSquare.class.getResource( "/0.png"));
            bombCount(currX-1, currY-1);
            bombCount(currX, currY-1);
            bombCount(currX+1, currY-1);
            bombCount(currX-1, currY);
            bombCount(currX+1, currY);
            bombCount(currX-1, currY+1);
            bombCount(currX, currY+1);
            bombCount(currX+1, currY+1);
        }
    }

    private boolean isKickedBoundary(int x,int y){
        return x < 0 || x >= (board.getWidth()-20)/20 || y < 0 || y >= (board.getHeight()-20)/20;
    }


    private boolean isSuccess(){
        int count = 0;
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 20; x++) {
                if (board.getSquareAt(x, y).getTraverse())
                    count++;
            }
        }
        return count == 20 * 15;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            int clickCount = e.getClickCount();

            // Show red flag.
            if (clickCount == 1)
            {
                setImage(GameSquare.class.getResource("/redFlag.png"));
                guessThisSquareIsBomb = true;
            }

            // Show question mark.
            if (clickCount == 2)
            {
                setImage(GameSquare.class.getResource("/questionMark.png"));
                guessThisSquareIsBomb = false;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {    }

    @Override
    public void mouseReleased(MouseEvent e) {    }

    @Override
    public void mouseEntered(MouseEvent e) {    }

    @Override
    public void mouseExited(MouseEvent e) {    }
}
