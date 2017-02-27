import java.util.ArrayList;

/**
 * Created by wenqingcao on 10/8/16.
 */
public class Option {
    private ArrayList<String> StackOpts = new ArrayList<>();
    private ArrayList<String> RaidOpts = new ArrayList<>();
    public void computeOpts(String[][] a, char x, int m)
    {
        int i,j;
        for (i = 0; i < m; i++)
        {
            for (j = 0; j < m; j++)
            {
                if (a[i][j].equals("."))
                {
                    StringBuilder temp = new StringBuilder();
                    temp.append(i);
                    temp.append(" ");
                    temp.append(j);
                    StackOpts.add(temp.toString());
                }
                if (a[i][j].equals(String.valueOf(x)))
                {
                    if (i != 0 && a[i-1][j].equals("."))
                    {
                        if (i-1 != 0 && !a[i-2][j].equals(String.valueOf(x)) && !a[i-2][j].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i-1);
                            temp.append(" ");
                            temp.append(j);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (j != 0 && !a[i-1][j-1].equals(String.valueOf(x)) && !a[i-1][j-1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i-1);
                            temp.append(" ");
                            temp.append(j);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (j != m-1 && !a[i-1][j+1].equals(String.valueOf(x)) && !a[i-1][j+1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i-1);
                            temp.append(" ");
                            temp.append(j);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                    }

                    if (i != m-1 && a[i+1][j].equals("."))
                    {
                        if (i+1 != m-1 && !a[i+2][j].equals(String.valueOf(x)) && !a[i+2][j].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i+1);
                            temp.append(" ");
                            temp.append(j);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (j != 0 && !a[i+1][j-1].equals(String.valueOf(x)) && !a[i+1][j-1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i+1);
                            temp.append(" ");
                            temp.append(j);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (j != m-1 && !a[i+1][j+1].equals(String.valueOf(x)) && !a[i+1][j+1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i+1);
                            temp.append(" ");
                            temp.append(j);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                    }

                    if (j != 0 && a[i][j-1].equals("."))
                    {
                        if (i != 0 && !a[i-1][j-1].equals(String.valueOf(x)) && !a[i-1][j-1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i);
                            temp.append(" ");
                            temp.append(j-1);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (i != m-1 && !a[i+1][j-1].equals(String.valueOf(x)) && !a[i+1][j-1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i);
                            temp.append(" ");
                            temp.append(j-1);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (j-1 != 0 && !a[i][j-2].equals(String.valueOf(x)) && !a[i][j-2].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i);
                            temp.append(" ");
                            temp.append(j-1);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                    }
                    if (j != m-1 && a[i][j+1].equals("."))
                    {
                        if (i != 0 && !a[i-1][j+1].equals(String.valueOf(x)) && !a[i-1][j+1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i);
                            temp.append(" ");
                            temp.append(j+1);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (i != m-1 && !a[i+1][j+1].equals(String.valueOf(x)) && !a[i+1][j+1].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i);
                            temp.append(" ");
                            temp.append(j+1);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                        if (j+1 != m-1 && !a[i][j+2].equals(String.valueOf(x)) && !a[i][j+2].equals("."))
                        {
                            StringBuilder temp = new StringBuilder();
                            temp.append(i);
                            temp.append(" ");
                            temp.append(j+1);
                            if (!RaidOpts.contains(temp.toString())) {
                                RaidOpts.add(temp.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<String> getStackOpts()
    {
        return this.StackOpts;
    }

    public ArrayList<String> getRaidOpts()
    {
        return this.RaidOpts;
    }
}
