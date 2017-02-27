import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wenqingcao on 10/10/16.
 */
public class Output {
    private int cordX;
    private char cordY;
    private String type;
    private String nextBoard[][];
    private HashMap<Integer,Character> convert = new HashMap<>();

    public void setType(String s)
    {
        this.type = s;
    }
    public void buildTable()
    {
        this.convert.put(0,'A');
        this.convert.put(1,'B');
        this.convert.put(2,'C');
        this.convert.put(3,'D');
        this.convert.put(4,'E');
        this.convert.put(5,'F');
        this.convert.put(6,'G');
        this.convert.put(7,'H');
        this.convert.put(8,'I');
        this.convert.put(9,'J');
        this.convert.put(10,'K');
        this.convert.put(11,'L');
        this.convert.put(12,'M');
        this.convert.put(13,'N');
        this.convert.put(14,'O');
        this.convert.put(15,'P');
        this.convert.put(16,'Q');
        this.convert.put(17,'R');
        this.convert.put(18,'S');
        this.convert.put(19,'T');
        this.convert.put(20,'U');
        this.convert.put(21,'V');
        this.convert.put(22,'W');
        this.convert.put(23,'X');
        this.convert.put(24,'Y');
        this.convert.put(25,'Z');
    }

    public void setCordY(int x)
    {
        this.cordY = this.convert.get(x);
    }

    public void setCordX(int y)
    {
        this.cordX = y+1;
    }

    public void setNextBoard(String a[][])
    {
        this.nextBoard = a;
    }

    public int getCordX()
    {
        return this.cordX;
    }

    public char getCordY()
    {
        return this.cordY;
    }

    public String getType()
    {
        return this.type;
    }

    public String[][] getNextBoard()
    {
        return this.nextBoard;
    }
}
