package GameMain;
import java.sql.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.awt.Point;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ButtonEvent extends JPanel implements ActionListener {
	private int row;
	private int col;
	private int bound = 2;
	private int size = 50; //Kích thước panel
	private JButton[][] btn;
	private GamePanel gpanel;
	private Color bgColor = Color.lightGray;
	private GameFrame gframe;
	
	
	private Point p1 = null;
	private Point p2 = null;      
	private Line line;
	private int score = 0;
	//Tổng cặp 10*10/2 =50 cặp icon
	private int pair;
	
	private sql sql1 = new sql();
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/sql1";
	static final String USER = "root";
	static final String PASS = "";
	
	
	public ButtonEvent(GameFrame gframe, int row, int col) {
		this.gframe = gframe;
		this.row = row + 2;
		this.col = col + 2;
		pair = row * col /2;
		
		setLayout(new GridLayout(row, col, bound, bound));
		setBackground(bgColor);
		setPreferredSize(new Dimension((size +bound) * col, (size + bound)*row));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setAlignmentY(JPanel.CENTER_ALIGNMENT);
		
		newGame();
	}
	public void newGame() {
		gpanel= new	GamePanel(this.gframe,this.row, this.col);
		addArrayButton();

		
	}
	
	

	private JButton createButton(String action) {
		JButton btn = new JButton();
		btn.setActionCommand(action);
		btn.setBorder(null);
		btn.addActionListener(this);
		return btn;
	}
	
	
	
	 
	

	private void addArrayButton() {
		btn = new JButton[row][col];
		for(int i = 1;i < row - 1; i++) {
			for(int j = 1;j < col - 1;j++) {
				//tao nut (i,j) cho icon o vi tri (i,j)
				btn[i][j] = createButton(i + "," + j);
				Icon icon = getIcon(gpanel.getMatrix()[i][j]);
				btn[i][j].setIcon(icon);
				add(btn[i][j]);
			}
		}
	}
	
	private Icon getIcon(int index){
		int width = 48, height = 48; 
		Image image = new ImageIcon("src/res/" +index + ".png").getImage();
	//	URL url = getClass().getResource(
		//		"src/res/" + index + ".png");
		//ImageIcon icon = new ImageIcon(url);
		
		Icon icon = new ImageIcon(image.getScaledInstance(width, height,
				Image.SCALE_SMOOTH));
		return icon;
	}
	
	public void execute(Point p1, Point p2) {
		System.out.println("delete");
		setDisable(btn[p1.x][p1.y]);
		setDisable(btn[p2.x][p2.y]);
	}
	private void setDisable(JButton btn) {
		btn.setIcon(null);
		btn.setBackground(bgColor);
		btn.setEnabled(false);
	}
	
	 @Override
	    public void actionPerformed(ActionEvent e) {
		 String btnIndex = e.getActionCommand();
		 int indexDot = btnIndex.lastIndexOf(",");
		 int x = Integer.parseInt(btnIndex.substring(0, indexDot));
		 int y = Integer.parseInt(btnIndex.substring(indexDot + 1,
		 		btnIndex.length()));
	 if (p1 == null) {
		//hieu ung chon icon
		 	p1 = new Point(x, y);
			btn[p1.x][p1.y].setBorder(new LineBorder(CBLACKBLACK));
		}else {
			p2 = new Point(x, y);
			line = gpanel.check2Icon(p1, p2);
			if (line != null) {
				System.out.println("line != null");
				gpanel.getMatrix()[p1.x][p1.y] = 0;
				gpanel.getMatrix()[p2.x][p2.y] = 0;
				gpanel.showMatrix();
				execute(p1, p2);
				line = null;
				score += 10;
				pair--;
				gframe.time++;
				gframe.lbScore.setText(score + "");
			}
			//xoa hieu ung chon icon
			btn[p1.x][p1.y].setBorder(null);
			p1 = null;
			p2 = null;
			System.out.println("done");
			if (pair == 0) {
				String name = JOptionPane.showInputDialog("Enter your name:");
				try {
				    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				    sql.addToDatabase(conn, name, score);
				   
				} catch (SQLException e1) {
				    e1.printStackTrace();
				}
				 JOptionPane.showMessageDialog(gframe, "Điểm của bạn là: " + score);
			    gframe.NewGameWindow("Thắng rùi! Muốn chơi lại khum?", "Win",1);
			}
			    
				}
			    
			}
		}


	 

