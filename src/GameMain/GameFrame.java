package GameMain;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import javax.swing.WindowConstants;
import javax.swing.border.*;


import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class GameFrame extends JFrame implements ActionListener, Runnable {
	
	private sql sql1 = new sql();
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/sql1";
	static final String USER = "root";
	static final String PASS = "";
	private int score;
	private String name;
	
 	private JFrame jframe;
	private JPanel jpanel;
	private ButtonEvent IconButton; 
	private int row = 6;
	private int col = 6;
	private int width = 700;
	private int height = 500;
	
	
	private int maxTime = 300;
	public int time = maxTime;
	public JLabel lbScore;
	private JProgressBar progressTime;
	private JButton btnNewGame;
	
	
	private boolean pause = false;
	private boolean resume = false;
	
	public GameFrame()  {
		jframe = new JFrame();
		
		
		jframe.add(jpanel = createMainPanel());
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		//(width, height)
		jframe.setSize(width, height);
		
		jframe.setResizable(true);
		jframe.setTitle("Pikachu");
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
	}
	
	//ham tao va sap xep cac components
	private JPanel createMainPanel() {
			JPanel jpanel = new JPanel(new BorderLayout());
			jpanel.add(createIconButton(), BorderLayout.CENTER);
			jpanel.add(createControlPanel(), BorderLayout.WEST);
			return jpanel;
	}
	
	
	private JPanel createControlPanel() {
	
		lbScore = new JLabel("0");
		progressTime = new JProgressBar(0, 100);
		progressTime.setValue(100);

	

		JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
		panelLeft.add(new JLabel("Score:"));
		panelLeft.add(new JLabel("Time:"));

		JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
		panelCenter.add(lbScore);
		panelCenter.add(progressTime);

		JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
		panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
		panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

	
		JPanel panelControl = new JPanel(new BorderLayout(10, 10));
		panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
		panelControl.add(panelScoreAndTime, BorderLayout.PAGE_START);
		panelControl.add(btnNewGame = createButton("New Game"),BorderLayout.CENTER);


		
	
		JPanel jpanel = new JPanel(new BorderLayout());
		jpanel.add(panelControl, BorderLayout.PAGE_START);
		
		
		return jpanel;
		
		
		
		
	}
	

		private JButton createButton(String ButtonName) {
			JButton btn = new JButton(ButtonName);
			btn.addActionListener(this);
			return btn;
			
		}
	

	private JPanel createIconButton() {
		IconButton = new ButtonEvent(this, row, col);
		//resize in Button event so no need for gridbagconstraints
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.DARK_GRAY);
		panel.add(IconButton);
		return panel;
	}
	

	
	
	
	//reset het icon,score va time ve trang thai ban dau
	public void newGame() {
		time = maxTime;
		IconButton.removeAll();
		jpanel.add(createIconButton(), BorderLayout.CENTER);
		jpanel.validate();
		jpanel.setVisible(true);
		lbScore.setText("0");	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {  
    	if(e.getSource() == btnNewGame) {
    		NewGameWindow("Game chưa xong bạn ei!. Muốn chơi lại hử?" , "Warning!",0);
    	}
	}


	@Override
	public void run() {
		while(true) {
			try {
				//1s
				Thread.sleep(1000);
				
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			progressTime.setValue((int) ((double) time / maxTime * 100));
		}
		
	}
	
	public JProgressBar getProgressTime() {
        return progressTime;
    }

    public void setProgressTime(JProgressBar progressTime) {
        this.progressTime = progressTime;
    }
	
	
	public boolean isResume() {
        return resume;
	}

	public void setResume(boolean resume) {
        this.resume = resume;
	}
        
	public boolean isPause() {
        return pause;
	}

	public void setPause(boolean pause) {
        this.pause = pause;	
	} 
	
	
	
	public boolean NewGameWindow(String text, String title, int t) {
		
		pause = true;
		resume = false;
		int select = JOptionPane.showOptionDialog(null, text, title,
				     JOptionPane.YES_NO_OPTION, 
				     JOptionPane.QUESTION_MESSAGE, null,null, null);
		if(select == 0) {
			pause = false;
			newGame();
			return true;
			
		}	else {
			if(t==1) {
				System.exit(0);
				return false;
			}else {
				resume = true;
				return true;
			}
			
		}
	}

}	
