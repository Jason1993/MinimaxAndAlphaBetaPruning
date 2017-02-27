/**
 * Created by wenqingcao on 10/12/16.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
/**
 * Created by wenqingcao on 10/2/16.
 */
public class homework {
    public static int alpha = -65535;
    public static int beta = 65536;
    public static ArrayList<String> readFile(String file) throws IOException {
        ArrayList<String> temp = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        try {
            while((line = reader.readLine()) != null) {
                StringBuilder  stringBuilder = new StringBuilder();
                stringBuilder.append(line);
                temp.add(stringBuilder.toString());
            }
        } finally {
            reader.close();
        }
        return temp;
    }
    public static void writeFile(ArrayList<String> s) throws IOException {
        PrintWriter output = new PrintWriter("output.txt","UTF-8");
        Iterator<String> tempstring = s.iterator();
        while (tempstring.hasNext() == true)
        {
            String line = tempstring.next();
            output.println(line);
        }
        output.close();
    }

    public static String[][] buildBoard(ArrayList<String> s)
    {
        int N = Integer.valueOf(s.get(0));
        String board[][] = new String [N][N];

        int i =4+N,j = 4+2*N;
        int m = 0;
        for (; i<j; i++)
        {
            board[m] = s.get(i).split("");
            m++;
        }
        return board;
    }

    public static String[][] boardValue(ArrayList<String> s)
    {
        int N = Integer.valueOf(s.get(0));
        String value[][] = new String[N][N];

        int i = 4, j = 4+N;
        int m = 0;
        for (; i<j; i++)
        {
            value[m] = s.get(i).split(" ");
            m++;
        }
        return value;
    }

    public static int eVal(String[][] a, String[][] b,char x,int m)
    {
        String cur,opo;
        if (x == 'O')
        {
            cur = "O";
            opo = "X";
        }
        else
        {
            cur = "X";
            opo = "O";
        }
        int i,j;
        int value = 0;
        for (i = 0; i < m; i++)
        {
            for (j = 0; j < m; j++)
            {
                if (a[i][j].equals(cur))
                {
                    value+=Integer.valueOf(b[i][j]);
                }
                else {
                    if (a[i][j].equals(opo))
                    {
                        value -= Integer.valueOf(b[i][j]);
                    }
                }
            }
        }
        return value;
    }

    public static Output conductMinMax(String[][] a, String[][] b, int depth, char c,int m)
    {
        String cur = new String();
        String nextString = new String();
        char next;
        if (c == 'O') {
            cur = "O";
            next = 'X';
            nextString = "X";
        } else {
            cur = "X";
            next = 'O';
            nextString = "O";
        }

        Option gen = new Option();
        gen.computeOpts(a, c, m);
        ArrayList<String> StackOpts = gen.getStackOpts();
        ArrayList<String> RaidOpts = gen.getRaidOpts();
        int max = -65535;
        Output result = new Output();
        result.buildTable();
        if (!StackOpts.isEmpty()) {
            for (String position : StackOpts) {
                int dep = depth;
                int i;
                String [][] nextBoard = new String[m][];
                for (i = 0; i < m; i++)
                {
                    nextBoard[i] = a[i].clone();
                }
                String[] temp = position.split(" ");
                int x = Integer.valueOf(temp[0]);
                int y = Integer.valueOf(temp[1]);
                nextBoard[x][y] = cur;
                --dep;
                int res = minValue(nextBoard, b, dep, next, m);
                if (res > max) {
                    max = res;
                    result.setType("Stake");
                    result.setCordX(x);
                    result.setCordY(y);
                    result.setNextBoard(nextBoard);
                }
            }
        }
        if (!RaidOpts.isEmpty()) {
            for (String position : RaidOpts) {
                int dep = depth;
                int i;
                String [][] nextBoard = new String[m][];
                for (i = 0; i < m; i++)
                {
                    nextBoard[i] = a[i].clone();
                }
                String[] temp = position.split(" ");
                int x = Integer.valueOf(temp[0]);
                int y = Integer.valueOf(temp[1]);
                nextBoard[x][y] = cur;
                if (x != 0 && nextBoard[x - 1][y].equals(nextString)) {
                    nextBoard[x - 1][y] = cur;
                }
                if (x != m - 1 && nextBoard[x + 1][y].equals(nextString)) {
                    nextBoard[x + 1][y] = cur;
                }
                if (y != 0 && nextBoard[x][y - 1].equals(nextString)) {
                    nextBoard[x][y - 1] = cur;
                }
                if (y != m - 1 && nextBoard[x][y + 1].equals(nextString)) {
                    nextBoard[x][y + 1] = cur;
                }
                --dep;
                int res = minValue(nextBoard, b, dep, next, m);
                if (res > max) {
                    max = res;
                    result.setType("Raid");
                    result.setCordX(x);
                    result.setCordY(y);
                    result.setNextBoard(nextBoard);
                }
            }
        }
        return result;
    }

    public static int minValue(String[][] a, String[][] b, int remain, char c, int m)
    {
        String cur = new String();
        String nextString = new String();
        char next;
        if (c =='O') {
            cur = "O";
            next = 'X';
            nextString = "X";
        }
        else {
            cur = "X";
            next = 'O';
            nextString = "O";
        }
        if (remain == 0) {
            return eVal(a,b,next,m);
        }
        else
        {
            Option gen = new Option();
            gen.computeOpts(a,c,m);
            ArrayList<String> StackOpts = gen.getStackOpts();
            ArrayList<String> RaidOpts = gen.getRaidOpts();
            int min = 65536;
            String nextMove[][] = new String[m][];
            if (StackOpts.isEmpty() == true && RaidOpts.isEmpty() == true) {
                return eVal(a,b,c,m);
            }
            else {
                if (!StackOpts.isEmpty()) {
                    for (String position : StackOpts) {
                        int dep = remain;
                        int i;
                        String [][] nextBoard = new String[m][];
                        for (i = 0; i < m; i++)
                        {
                            nextBoard[i] = a[i].clone();
                        }
                        String[] temp = position.split(" ");
                        int x = Integer.valueOf(temp[0]);
                        int y = Integer.valueOf(temp[1]);
                        nextBoard[x][y] = cur;
                        --dep;
                        int res = maxValue(nextBoard, b, dep, next, m);
                        if (res < min) {
                            min = res;
                            int j;
                            for (j = 0; j < m; j++)
                            {
                                nextMove[j] = nextBoard[j].clone();
                            }
                        }
                    }
                }
                if (!RaidOpts.isEmpty()) {
                    for (String position : RaidOpts) {
                        int dep = remain;
                        int i;
                        String [][] nextBoard = new String[m][];
                        for (i = 0; i < m; i++)
                        {
                            nextBoard[i] = a[i].clone();
                        }
                        String[] temp = position.split(" ");
                        int x = Integer.valueOf(temp[0]);
                        int y = Integer.valueOf(temp[1]);
                        nextBoard[x][y] = cur;
                        if (x != 0 && nextBoard[x - 1][y].equals(nextString)) {
                            nextBoard[x - 1][y] = cur;
                        }
                        if (x != m - 1 && nextBoard[x + 1][y].equals(nextString)) {
                            nextBoard[x + 1][y] = cur;
                        }
                        if (y != 0 && nextBoard[x][y - 1].equals(nextString)) {
                            nextBoard[x][y - 1] = cur;
                        }
                        if (y != m - 1 && nextBoard[x][y + 1].equals(nextString)) {
                            nextBoard[x][y + 1] = cur;
                        }
                        --dep;
                        int res = maxValue(nextBoard, b, dep, next, m);
                        if (res < min) {
                            min = res;
                            int j;
                            for (j = 0; j < m; j++)
                            {
                                nextMove[j] = nextBoard[j].clone();
                            }
                        }
                    }
                }
                return maxValue(nextMove, b, remain - 1, next, m);
            }
        }
    }
    public static int maxValue(String[][] a, String[][] b, int remain, char c, int m) {
        String cur = new String();
        String nextString = new String();
        char next;
        if (c == 'O') {
            cur = "O";
            next = 'X';
            nextString = "X";
        } else {
            cur = "X";
            next = 'O';
            nextString = "O";
        }
        if (remain == 0) {
            return eVal(a, b, c, m);
        } else {
            Option gen = new Option();
            gen.computeOpts(a, c, m);
            ArrayList<String> StackOpts = gen.getStackOpts();
            ArrayList<String> RaidOpts = gen.getRaidOpts();
            int max = -65535;
            String nextMove[][] = new String[m][];
            if (StackOpts.isEmpty() == true && RaidOpts.isEmpty() == true) {
                return eVal(a, b, c, m);
            } else {
                if (!StackOpts.isEmpty()) {
                    for (String position : StackOpts) {
                        int dep = remain;
                        int i;
                        String[][] nextBoard = new String[m][];
                        for (i = 0; i < m; i++) {
                            nextBoard[i] = a[i].clone();
                        }
                        String[] temp = position.split(" ");
                        int x = Integer.valueOf(temp[0]);
                        int y = Integer.valueOf(temp[1]);
                        nextBoard[x][y] = cur;
                        --dep;
                        int res = minValue(nextBoard, b, dep, next, m);
                        if (res > max) {
                            max = res;
                            int j;
                            for (j = 0; j < m; j++) {
                                nextMove[j] = nextBoard[j].clone();
                            }
                        }
                    }
                    if (!RaidOpts.isEmpty()) {
                        for (String position : RaidOpts) {
                            int dep = remain;
                            int i;
                            String[][] nextBoard = new String[m][];
                            for (i = 0; i < m; i++) {
                                nextBoard[i] = a[i].clone();
                            }
                            String[] temp = position.split(" ");
                            int x = Integer.valueOf(temp[0]);
                            int y = Integer.valueOf(temp[1]);
                            nextBoard[x][y] = cur;
                            if (x != 0 && nextBoard[x - 1][y].equals(nextString)) {
                                nextBoard[x - 1][y] = cur;
                            }
                            if (x != m - 1 && nextBoard[x + 1][y].equals(nextString)) {
                                nextBoard[x + 1][y] = cur;
                            }
                            if (y != 0 && nextBoard[x][y - 1].equals(nextString)) {
                                nextBoard[x][y - 1] = cur;
                            }
                            if (y != m - 1 && nextBoard[x][y + 1].equals(nextString)) {
                                nextBoard[x][y + 1] = cur;
                            }
                            --dep;
                            int res = minValue(nextBoard, b, dep, next, m);
                            if (res > max) {
                                max = res;
                                int j;
                                for (j = 0; j < m; j++) {
                                    nextMove[j] = nextBoard[j].clone();
                                }
                            }
                        }
                    }
                }
                return minValue(nextMove, b, remain - 1, next, m);
            }
        }
    }

    public static Output conductAlphaBeta(String[][] a, String[][] b, int depth, char c,int m,int alpha,int beta)
    {
        String cur = new String();
        String nextString = new String();
        char next;
        if (c == 'O') {
            cur = "O";
            next = 'X';
            nextString = "X";
        } else {
            cur = "X";
            next = 'O';
            nextString = "O";
        }

        Option gen = new Option();
        gen.computeOpts(a, c, m);
        ArrayList<String> StackOpts = gen.getStackOpts();
        ArrayList<String> RaidOpts = gen.getRaidOpts();
        int max = -65535;
        Output result = new Output();
        result.buildTable();
        if (!StackOpts.isEmpty()) {
            for (String position : StackOpts) {
                int dep = depth;
                int i;
                String [][] nextBoard = new String[m][];
                for (i = 0; i < m; i++)
                {
                    nextBoard[i] = a[i].clone();
                }
                String[] temp = position.split(" ");
                int x = Integer.valueOf(temp[0]);
                int y = Integer.valueOf(temp[1]);
                nextBoard[x][y] = cur;
                --dep;
                int res = minAlphaBeta(nextBoard, b, dep, next, m,alpha,beta);
                if (res > max) {
                    max = res;
                    result.setType("Stake");
                    result.setCordX(x);
                    result.setCordY(y);
                    result.setNextBoard(nextBoard);
                }
            }
        }
        if (!RaidOpts.isEmpty()) {
            for (String position : RaidOpts) {
                int dep = depth;
                int i;
                String [][] nextBoard = new String[m][];
                for (i = 0; i < m; i++)
                {
                    nextBoard[i] = a[i].clone();
                }
                String[] temp = position.split(" ");
                int x = Integer.valueOf(temp[0]);
                int y = Integer.valueOf(temp[1]);
                nextBoard[x][y] = cur;
                if (x != 0 && nextBoard[x - 1][y].equals(nextString)) {
                    nextBoard[x - 1][y] = cur;
                }
                if (x != m - 1 && nextBoard[x + 1][y].equals(nextString)) {
                    nextBoard[x + 1][y] = cur;
                }
                if (y != 0 && nextBoard[x][y - 1].equals(nextString)) {
                    nextBoard[x][y - 1] = cur;
                }
                if (y != m - 1 && nextBoard[x][y + 1].equals(nextString)) {
                    nextBoard[x][y + 1] = cur;
                }
                --dep;
                int res = minAlphaBeta(nextBoard, b, dep, next, m,alpha,beta);
                if (res > max) {
                    max = res;
                    result.setType("Raid");
                    result.setCordX(x);
                    result.setCordY(y);
                    result.setNextBoard(nextBoard);
                }
            }
        }
        return result;

    }

    public static int minAlphaBeta(String[][] a, String[][] b, int remain, char c, int m, int alpha,int beta)
    {
        String cur = new String();
        String nextString = new String();
        char next;
        if (c =='O') {
            cur = "O";
            next = 'X';
            nextString = "X";
        }
        else {
            cur = "X";
            next = 'O';
            nextString = "O";
        }
        if (remain == 0) {
            return eVal(a,b,next,m);
        }
        else
        {
            Option gen = new Option();
            gen.computeOpts(a,c,m);
            ArrayList<String> StackOpts = gen.getStackOpts();
            ArrayList<String> RaidOpts = gen.getRaidOpts();
            int min = 65536;
            String nextMove[][] = new String[m][];
            if (StackOpts.isEmpty() == true && RaidOpts.isEmpty() == true) {
                return eVal(a,b,c,m);
            }
            else {
                if (!StackOpts.isEmpty()) {
                    for (String position : StackOpts) {
                        int dep = remain;
                        int i;
                        String [][] nextBoard = new String[m][];
                        for (i = 0; i < m; i++)
                        {
                            nextBoard[i] = a[i].clone();
                        }
                        String[] temp = position.split(" ");
                        int x = Integer.valueOf(temp[0]);
                        int y = Integer.valueOf(temp[1]);
                        nextBoard[x][y] = cur;
                        --dep;
                        int res = maxAlphaBeta(nextBoard, b, dep, next, m,alpha,beta);
                        if (alpha >= res) {
                            return res;
                        }
                        if (res<beta)
                        {
                            beta = res;
                        }
                        if (res < min) {
                            min = res;
                            int j;
                            for (j = 0; j < m; j++)
                            {
                                nextMove[j] = nextBoard[j].clone();
                            }
                        }
                    }
                }
                if (!RaidOpts.isEmpty()) {
                    for (String position : RaidOpts) {
                        int dep = remain;
                        int i;
                        String [][] nextBoard = new String[m][];
                        for (i = 0; i < m; i++)
                        {
                            nextBoard[i] = a[i].clone();
                        }
                        String[] temp = position.split(" ");
                        int x = Integer.valueOf(temp[0]);
                        int y = Integer.valueOf(temp[1]);
                        nextBoard[x][y] = cur;
                        if (x != 0 && nextBoard[x - 1][y].equals(nextString)) {
                            nextBoard[x - 1][y] = cur;
                        }
                        if (x != m - 1 && nextBoard[x + 1][y].equals(nextString)) {
                            nextBoard[x + 1][y] = cur;
                        }
                        if (y != 0 && nextBoard[x][y - 1].equals(nextString)) {
                            nextBoard[x][y - 1] = cur;
                        }
                        if (y != m - 1 && nextBoard[x][y + 1].equals(nextString)) {
                            nextBoard[x][y + 1] = cur;
                        }
                        --dep;
                        int res = maxAlphaBeta(nextBoard, b, dep, next, m,alpha,beta);
                        if (alpha >= res) {
                            return res;
                        }
                        if (res<beta)
                        {
                            beta = res;
                        }
                        if (res < min) {
                            min = res;
                            int j;
                            for (j = 0; j < m; j++)
                            {
                                nextMove[j] = nextBoard[j].clone();
                            }
                        }
                    }
                }
                return maxAlphaBeta(nextMove, b, remain - 1, next, m,alpha,beta);
            }
        }
    }
    public static int maxAlphaBeta(String[][] a, String[][] b, int remain, char c, int m,int alpha, int beta) {
        String cur = new String();
        String nextString = new String();
        char next;
        if (c == 'O') {
            cur = "O";
            next = 'X';
            nextString = "X";
        } else {
            cur = "X";
            next = 'O';
            nextString = "O";
        }
        if (remain == 0) {
            return eVal(a, b, c, m);
        } else {
            Option gen = new Option();
            gen.computeOpts(a, c, m);
            ArrayList<String> StackOpts = gen.getStackOpts();
            ArrayList<String> RaidOpts = gen.getRaidOpts();
            int max = -65535;
            String nextMove[][] = new String[m][];
            if (StackOpts.isEmpty() == true && RaidOpts.isEmpty() == true) {
                return eVal(a, b, c, m);
            } else {
                if (!StackOpts.isEmpty()) {
                    for (String position : StackOpts) {
                        int dep = remain;
                        int i;
                        String[][] nextBoard = new String[m][];
                        for (i = 0; i < m; i++) {
                            nextBoard[i] = a[i].clone();
                        }
                        String[] temp = position.split(" ");
                        int x = Integer.valueOf(temp[0]);
                        int y = Integer.valueOf(temp[1]);
                        nextBoard[x][y] = cur;
                        --dep;
                        int res = minAlphaBeta(nextBoard, b, dep, next, m,alpha,beta);
                        if (res > beta)
                        {
                            return res;
                        }
                        if (res > alpha)
                        {
                            alpha =res;
                        }
                        if (res > max) {
                            max = res;
                            int j;
                            for (j = 0; j < m; j++) {
                                nextMove[j] = nextBoard[j].clone();
                            }
                        }
                    }
                    if (!RaidOpts.isEmpty()) {
                        for (String position : RaidOpts) {
                            int dep = remain;
                            int i;
                            String[][] nextBoard = new String[m][];
                            for (i = 0; i < m; i++) {
                                nextBoard[i] = a[i].clone();
                            }
                            String[] temp = position.split(" ");
                            int x = Integer.valueOf(temp[0]);
                            int y = Integer.valueOf(temp[1]);
                            nextBoard[x][y] = cur;
                            if (x != 0 && nextBoard[x - 1][y].equals(nextString)) {
                                nextBoard[x - 1][y] = cur;
                            }
                            if (x != m - 1 && nextBoard[x + 1][y].equals(nextString)) {
                                nextBoard[x + 1][y] = cur;
                            }
                            if (y != 0 && nextBoard[x][y - 1].equals(nextString)) {
                                nextBoard[x][y - 1] = cur;
                            }
                            if (y != m - 1 && nextBoard[x][y + 1].equals(nextString)) {
                                nextBoard[x][y + 1] = cur;
                            }
                            --dep;
                            int res = minAlphaBeta(nextBoard, b, dep, next, m,alpha,beta);
                            if (res > beta)
                            {
                                return res;
                            }
                            if (res > alpha)
                            {
                                alpha =res;
                            }
                            if (res > max) {
                                max = res;
                                int j;
                                for (j = 0; j < m; j++) {
                                    nextMove[j] = nextBoard[j].clone();
                                }
                            }
                        }
                    }
                }
                return minAlphaBeta(nextMove, b, remain - 1, next, m,alpha,beta);
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        ArrayList<String> temp = readFile("input.txt");
        String board[][] = buildBoard(temp);
        String value[][] = boardValue(temp);
        Option test = new Option();
        char current = temp.get(2).charAt(0);
        int grid = Integer.valueOf(temp.get(0));
        int depth = Integer.valueOf(temp.get(3));
        int i,j = 0;
        //Output result = conductAlphaBeta(board,value,depth,current,grid,alpha,beta);
        //Output result = conductMinMax(board,value,depth,current,grid);
        String type = temp.get(1);
        switch (type)
        {
            case "MINIMAX":
            {
                Output result = conductMinMax(board,value,depth,current,grid);
                StringBuilder answer = new StringBuilder();
                answer.append(result.getCordY());
                answer.append(result.getCordX());
                answer.append(" ");
                answer.append(result.getType());
                ArrayList<String> outputfile = new ArrayList<>();
                outputfile.add(answer.toString());
                String[][] next = result.getNextBoard();
                for (i = 0; i < grid; i++)
                {
                    StringBuilder line = new StringBuilder();
                    for (j = 0; j < grid; j++)
                    {
                        line.append(next[i][j]);
                    }
                    outputfile.add(line.toString());
                }
                writeFile(outputfile);
                break;
            }
            case "ALPHABETA":
            {
                Output result = conductAlphaBeta(board,value,depth,current,grid,alpha,beta);
                StringBuilder answer = new StringBuilder();
                answer.append(result.getCordY());
                answer.append(result.getCordX());
                answer.append(" ");
                answer.append(result.getType());
                ArrayList<String> outputfile = new ArrayList<>();
                outputfile.add(answer.toString());
                String[][] next = result.getNextBoard();
                for (i = 0; i < grid; i++)
                {
                    StringBuilder line = new StringBuilder();
                    for (j = 0; j < grid; j++)
                    {
                        line.append(next[i][j]);
                    }
                    outputfile.add(line.toString());
                }
                writeFile(outputfile);
                break;
            }
        }
    }
}
