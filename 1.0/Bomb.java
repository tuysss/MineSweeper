import java.util.Random;

public class Bomb {
    private int width;
    private int height;
    private GameBoard board;
    private int bombs;

    public Bomb(GameBoard board,int bombs) {
        //TODO
        this.board=board;
        this.bombs=bombs;
        this.width=(board.getWidth()-20)/20;
        this.height=(board.getHeight()-20)/20;

        int count=0;
        do{
            count++;
            produceBomb();
        }while(count<bombs);
    }

    private void produceBomb(){
        Random r=new Random();

        int xLocation=r.nextInt(width);
        int yLocation=r.nextInt(height);

        GameSquare square=board.getSquareAt(xLocation,yLocation);

        if(!square.getBombExist()){
            square.setBombExist(true);
            square.setTraverse(true);
        }else{
            produceBomb();
        }
    }


}
