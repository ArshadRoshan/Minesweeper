import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.text.*;


public class Minesweeper extends JPanel implements ActionListener, MouseListener
{
    JFrame frame;
    JMenuBar menuBar;
    JMenu game;
    JMenu controls;
    JMenuItem beginner;
    JMenuItem intermediate;
    JMenuItem expert;
    JMenuItem item1;
    JMenuItem item2;
    JMenuItem item3;
    JPanel panel;
    JPanel scoreboard;
    JToggleButton[][] togglers;
    int[][] grid;
    JTextField mineCounter;
    JTextField timeCounter;
    int minecount = 10;
    int timecount = 0;
    int diffLevel = 1;
    JButton reset;
    ImageIcon smile;
    Font f;
    ImageIcon mine;
    ImageIcon flag; 
    int dimR = 9, dimC = 9; 
    int duborl = 0;
    int nullClick = 0;
	Timer timer; 
	String pattern = "000";

    public Minesweeper()
    {
        timer = new Timer();

        frame = new JFrame("Minesweeper");
        frame.add(this);
        frame.setSize(1000,800);

        grid = new int[dimR][dimC];
        for(int x = 0; x < dimR; x++)
            for(int y = 0; y < dimC; y++)
                grid[x][y] = 0;
        for(int x = 0; x < minecount; x++)
        {
            int randrow = (int)(Math.random()*9);
            int randcol = (int)(Math.random()*9);

            if(grid[randrow][randcol] != 1)
                grid[randrow][randcol] = 1;
            else
                x--; 
        }

        mine = new ImageIcon("bomb.png");
        mine = new ImageIcon(mine.getImage().getScaledInstance(frame.getWidth()/dimC, frame.getHeight()/dimR, Image.SCALE_SMOOTH));
        flag = new ImageIcon("flag.jpg");
        flag = new ImageIcon(flag.getImage().getScaledInstance(frame.getWidth()/dimC, frame.getHeight()/dimR, Image.SCALE_SMOOTH));

        menuBar = new JMenuBar();
        game = new JMenu("Game");
        controls = new JMenu("Controls");
        beginner = new JMenuItem("Beginner 9x9");
        beginner.addActionListener(this);
        intermediate = new JMenuItem("Intermediate 16x16");
        intermediate.addActionListener(this);
        expert = new JMenuItem("Expert 16x30");
        expert.addActionListener(this);
        item1 = new JMenuItem("Left-click an empty square to reveal it.");
        item2 = new JMenuItem("Right-click (or Ctrl+click) an empty square to flag it.");
        item3 = new JMenuItem("Left-click the smiley face to reset.");
        
        togglers = new JToggleButton[dimR][dimC];
        panel = new JPanel();
        panel.setLayout(new GridLayout(togglers.length,togglers[0].length));
        for(int x = 0; x < togglers.length; x++)
            for(int y = 0; y < togglers[0].length; y++)
            {
                togglers[x][y] = new JToggleButton();
                togglers[x][y].addMouseListener(this);
                panel.add(togglers[x][y]);
            }
        
        panel.setPreferredSize(new Dimension(1000, 600));

        scoreboard = new JPanel();
        smile = new ImageIcon("smile.png");
        reset = new JButton(smile);
        reset.addActionListener(this);
        f = new Font("GB18030 Bitmap",Font.BOLD,30);
        mineCounter = new JTextField(6);
        mineCounter.setFont(f);
        mineCounter.setForeground(Color.red);
		mineCounter.setBackground(Color.BLACK);
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String mout = myFormatter.format(minecount);
		String tout = myFormatter.format(timecount);
        mineCounter.setText(mout+"");
        mineCounter.setHorizontalAlignment(JTextField.CENTER);
        timeCounter = new JTextField(6);
        timeCounter.setFont(f);
        timeCounter.setForeground(Color.red);
        timeCounter.setBackground(Color.BLACK);
        timeCounter.setText(tout+"");
        timeCounter.setHorizontalAlignment(JTextField.CENTER);
        scoreboard.add(mineCounter,BorderLayout.WEST);
        scoreboard.add(reset,BorderLayout.CENTER);
        scoreboard.add(timeCounter,BorderLayout.EAST);

        game.add(beginner);
        game.add(intermediate);
        game.add(expert);
        controls.add(item1);
        controls.add(item2);
        controls.add(item3);
        menuBar.add(game);
        menuBar.add(controls);

        frame.add(menuBar,BorderLayout.NORTH);
        frame.add(scoreboard,BorderLayout.CENTER);
        frame.add(panel,BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e)
	{
        duborl = 0;
        nullClick = 0;
        timecount = 0;
		if(e.getSource()==beginner)
		{
            dimR = 9;
            dimC = 9;
            minecount = 10;
            diffLevel = 1;
        }
        if(e.getSource()==intermediate)
        {
            dimR = 16;
            dimC = 16;
            minecount = 40;
            diffLevel = 2;
        }
        if(e.getSource()==expert)
        {
            dimR = 16;
            dimC = 30; 
            minecount = 99;
            diffLevel = 3; 
        }
        if(e.getSource()==reset)
        {
            if(diffLevel==1)
            {
                minecount = 10;
            }
            if(diffLevel==2)
            {
                minecount = 40;
            }
            if(diffLevel==3)
            {
                minecount = 99;
            }
            duborl = 0;
        }
        grid = new int[dimR][dimC];
        for(int x = 0; x < dimR; x++)
            for(int y = 0; y < dimC; y++)
                grid[x][y] = 0;
        for(int x = 0; x < minecount; x++)
        {
            int randrow = (int)(Math.random()*dimR);
            int randcol = (int)(Math.random()*dimC);

            if(grid[randrow][randcol] != 1)
                grid[randrow][randcol] = 1;
            else
                x--; 
        }
        mine = new ImageIcon(mine.getImage().getScaledInstance(frame.getWidth()/dimC, frame.getHeight()/dimR, Image.SCALE_SMOOTH));
        flag = new ImageIcon(flag.getImage().getScaledInstance(frame.getWidth()/dimC, frame.getHeight()/dimR, Image.SCALE_SMOOTH));
        frame.remove(scoreboard);
        frame.remove(panel);
        togglers = new JToggleButton[dimR][dimC];
        panel = new JPanel();
        panel.setLayout(new GridLayout(togglers.length,togglers[0].length));
        for(int x = 0; x < togglers.length; x++)
            for(int y = 0; y < togglers[0].length; y++)
            {
                togglers[x][y] = new JToggleButton();
                togglers[x][y].addMouseListener(this);
                panel.add(togglers[x][y]);
            }
        panel.setPreferredSize(new Dimension(1000, 600));

        scoreboard = new JPanel();
        smile = new ImageIcon("smile.png");
        reset = new JButton(smile);
        reset.addActionListener(this);
        f = new Font("GB18030 Bitmap",Font.BOLD,30);
        mineCounter = new JTextField(6);
        mineCounter.setFont(f);
        mineCounter.setForeground(Color.red);
		mineCounter.setBackground(Color.BLACK);
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String mout = myFormatter.format(minecount);
		String tout = myFormatter.format(timecount);
        mineCounter.setText(mout+"");
        mineCounter.setHorizontalAlignment(JTextField.CENTER);
        timeCounter = new JTextField(6);
        timeCounter.setFont(f);
        timeCounter.setForeground(Color.red);
        timeCounter.setBackground(Color.BLACK);
        timeCounter.setText(tout+"");
        timeCounter.setHorizontalAlignment(JTextField.CENTER);
        scoreboard.add(mineCounter,BorderLayout.WEST);
        scoreboard.add(reset,BorderLayout.CENTER);
        scoreboard.add(timeCounter,BorderLayout.EAST);

        frame.add(scoreboard,BorderLayout.CENTER);
        frame.add(panel,BorderLayout.SOUTH);
        frame.revalidate();
	}

    @Override
    public void mouseClicked(MouseEvent e) 
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if((e.getButton() == MouseEvent.BUTTON1)&&(duborl==0)) 
        {
            for(int x = 0; x < togglers.length; x++)
            {
                for(int y = 0; y < togglers[0].length; y++)
                {
                    if(e.getSource()==togglers[x][y])
                    {
                        if(nullClick==0)
                        {
                            MyTimer();
                            if(grid[x][y]==1)
                            {
                                grid[x][y] = 0;
                                int xval = x;
                                int yval = y;
                                int loop = 0;
                                do 
                                {
                                    int rowSet = (int)(Math.random()*dimR);
                                    int colSet = (int)(Math.random()*dimC);
                                    if((rowSet!=xval)||(colSet!=yval))
                                    {
                                        if(grid[rowSet][colSet]==0)
                                        {
                                            grid[rowSet][colSet] = 1;
                                            loop = 1; 
                                        }
                                    }
                                }while(loop==0);
                            }
                            
                            for(int i=x-1;i<=x+1;i++)
                            {
                                for(int j=y-1;j<=y+1;j++)
                                {
                                    try
                                    {
                                        if(grid[i][j]==1)
                                        {
                                            grid[i][j] = 0;
                                            int loop = 0;
                                            do 
                                            {
                                                int rowSet = (int)(Math.random()*dimR);
                                                int colSet = (int)(Math.random()*dimC);
                                                if(((rowSet!=x-1)&&(colSet!=y-1))||((rowSet!=x)&&(colSet!=y-1))||((rowSet!=x+1)&&(colSet!=y-1))||((rowSet!=x-1)&&(colSet!=y))||((rowSet!=x+1)&&(colSet!=y))||((rowSet!=x-1)&&(colSet!=y+1))||((rowSet!=x)&&(colSet!=y+1))||((rowSet!=x+1)&&(colSet!=y+1)))
                                                {
                                                    if(grid[rowSet][colSet]==0)
                                                    {
                                                        grid[rowSet][colSet] = 1;
                                                        loop = 1; 
                                                    }
                                                }
                                            }while(loop==0);
                                        }
                                    }
                                    catch(ArrayIndexOutOfBoundsException exception)
                                    {
                                    }
                                }
                            }
                            nullClick = 1; 
                        }
                        if((!togglers[x][y].isSelected())&&(grid[x][y]==1))
                        {
                            togglers[x][y].setSelected(true);
                            togglers[x][y].setIcon(mine);
                            for(int a = 0; a < dimR; a++)
                            {
                                for(int b = 0; b < dimC; b++)
                                {
                                    if(grid[a][b]==1)
                                    {
                                        togglers[a][b].setSelected(true);
                                        togglers[a][b].setIcon(mine); 
                                    }
                                }
                            }
                            duborl = 2;
                            gameOver();
                        }
                        else if((grid[x][y]==2)||(grid[x][y]==3))
                        {
                            togglers[x][y].setSelected(false);
                        }
                        else if(grid[x][y]==0)
                        {
                            expand(x,y);
                            togglers[x][y].setSelected(false);
                        }
                    }
                }
            }
        }
        if((e.getButton() == MouseEvent.BUTTON3)&&(duborl==0))
        {
            for(int x = 0; x < togglers.length; x++)
            {
                for(int y = 0; y < togglers[0].length; y++)
                {
                    if(e.getSource()==togglers[x][y])
                    {
                        if((!togglers[x][y].isSelected())&&(grid[x][y]!=1))
                        {
                            grid[x][y] = 2;
                            togglers[x][y].setSelected(true);
                            togglers[x][y].setIcon(flag);
                            minecount--;
                        }
                        else if((!togglers[x][y].isSelected())&&(grid[x][y]==1))
                        {
                            grid[x][y] = 3;
                            togglers[x][y].setSelected(true);
                            togglers[x][y].setIcon(flag);
                            minecount--;
                        }
                        else if(grid[x][y]==2)
                        {
                            grid[x][y] = 0;
                            togglers[x][y].setSelected(false);
                            togglers[x][y].setIcon(null);
                            minecount++;
                        }
                        else if(grid[x][y]==3)
                        {
                            grid[x][y] = 1;
                            togglers[x][y].setSelected(false);
                            togglers[x][y].setIcon(null);
                            minecount++;
                        }
                    }
                }
            }
            scoreboardUpdate();
        }
        int numFlags = 0;
        for(int a = 0; a < grid.length; a++)
        {
            for(int b = 0; b < grid[0].length; b++)
            {
                if(grid[a][b]==3)
                {
                    numFlags++;
                }
            }
        }
        if((diffLevel==1)&&(numFlags==10))
        {
            duborl = 1;
            gameOver();
        }
        if((diffLevel==2)&&(numFlags==40))
        {
            duborl = 1;
            gameOver();
        }
        if((diffLevel==3)&&(numFlags==99))
        {
            duborl = 1; 
            gameOver(); 
        }
        frame.revalidate();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }
   
    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    public void scoreboardUpdate()
    {
        frame.remove(scoreboard);

        scoreboard = new JPanel();
        smile = new ImageIcon("smile.png");
        reset = new JButton(smile);
        reset.addActionListener(this);
        f = new Font("GB18030 Bitmap",Font.BOLD,30);
        mineCounter = new JTextField(6);
        mineCounter.setFont(f);
        mineCounter.setForeground(Color.red);
		mineCounter.setBackground(Color.BLACK);
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String mout = myFormatter.format(minecount);
		String tout = myFormatter.format(timecount);
        mineCounter.setText(mout+"");
        mineCounter.setHorizontalAlignment(JTextField.CENTER);
        timeCounter = new JTextField(6);
        timeCounter.setFont(f);
        timeCounter.setForeground(Color.red);
        timeCounter.setBackground(Color.BLACK);
        timeCounter.setText(tout+"");
        timeCounter.setHorizontalAlignment(JTextField.CENTER);
        scoreboard.add(mineCounter,BorderLayout.WEST);
        scoreboard.add(reset,BorderLayout.CENTER);
        scoreboard.add(timeCounter,BorderLayout.EAST);

        frame.add(scoreboard,BorderLayout.CENTER);
    }

    public void gameOver()
    {
        frame.remove(scoreboard);
        nullClick = 0; 

        scoreboard = new JPanel();
        if(duborl==2)
            smile = new ImageIcon("dead.png");
        else
            smile = new ImageIcon("win.jpeg");
        reset = new JButton(smile);
		reset.addActionListener(this);
		
        f = new Font("GB18030 Bitmap",Font.BOLD,30);
        mineCounter = new JTextField(6);
        mineCounter.setFont(f);
        mineCounter.setForeground(Color.red);
		mineCounter.setBackground(Color.BLACK);
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String mout = myFormatter.format(minecount);
		String tout = myFormatter.format(timecount);
        mineCounter.setText(mout+"");
        mineCounter.setHorizontalAlignment(JTextField.CENTER);
        timeCounter = new JTextField(6);
        timeCounter.setFont(f);
        timeCounter.setForeground(Color.red);
        timeCounter.setBackground(Color.BLACK);
        timeCounter.setText(tout+"");
        timeCounter.setHorizontalAlignment(JTextField.CENTER);
        scoreboard.add(mineCounter,BorderLayout.WEST);
        scoreboard.add(reset,BorderLayout.CENTER);
        scoreboard.add(timeCounter,BorderLayout.EAST);
        frame.add(scoreboard,BorderLayout.CENTER);
    }

    public void expand(int row, int col)
    {
        togglers[row][col].setSelected(true);
        int count = 0;
        if((row-1 < dimR)&&(row-1 > -1)&&(col-1 < dimC)&&(col-1 > -1))
        {
            if((grid[row-1][col-1]==1)||(grid[row-1][col-1]==3))
            {
                count++;
            }
        }
        if((col-1 < dimC)&&(col-1 > -1))
        {
            if((grid[row][col-1]==1)||(grid[row][col-1]==3))
            {
                count++;
            }
        }
        if((row+1 < dimR)&&(row+1 > -1)&&(col-1 < dimC)&&(col-1 > -1))
        {
            if((grid[row+1][col-1]==1)||(grid[row+1][col-1]==3))
            {
                count++;
            }
        }
        if((row-1 < dimR)&&(row-1 > -1)&&(col+1 < dimC)&&(col+1 > -1))
        {
            if((grid[row-1][col+1]==1)||(grid[row-1][col+1]==3))
            {
                count++;
            }
        }
        if((col+1 < dimC)&&(col+1 > -1))
        {
            if((grid[row][col+1]==1)||(grid[row][col+1]==3))
            {
                count++;
            }
        }
        if((row+1 < dimR)&&(row+1 > -1)&&(col+1 < dimC)&&(col+1 > -1))
        {
            if((grid[row+1][col+1]==1)||(grid[row+1][col+1]==3))
            {
                count++;
            }
        }
        if((row-1 < dimR)&&(row-1 > -1))
        {
            if((grid[row-1][col]==1)||(grid[row-1][col]==3))
            {
                count++;
            }
        }
        if((row+1 < dimR)&&(row+1 > -1))
        {
            if((grid[row+1][col]==1)||(grid[row+1][col]==3))
            {
                count++;
            }
        }
        
        if(count > 0)
        {
            togglers[row][col].setText(count+"");
        }
        else
        {
            for(int x=row-1;x<=row+1;x++)
            {
                for(int y=col-1;y<=col+1;y++)
                {
                    try
                    {
                        if(!togglers[x][y].isSelected())
                        {
                            expand(x,y);
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException exception)
                    {
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Minesweeper app = new Minesweeper();
    }

    public void MyTimer()
    {
        TimerTask task; 
    
        task = new TimerTask() 
        {

        @Override
        public void run() 
        { 
            if((duborl==0)&&(nullClick!=0)) 
            {
				timecount++;
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String tout = myFormatter.format(timecount);
                timeCounter.setText(tout+"");
            } else 
            {
                // stop the timer
                cancel();
                timecount = 0;
            }
        }
        };
        timer.schedule(task, 0, 1000);
    }
}
