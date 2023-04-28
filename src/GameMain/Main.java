package GameMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;



public class Main {
	GameFrame gframe;
	GamePanel gpanel;

	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/sql1";
	static final String USER = "root";
	static final String PASS = "";
	private int score;
	public Main() {
		gframe = new GameFrame();
		GameTime gtime = new GameTime();
		gtime.start();
		new Thread(gframe).start();
	}
	
	public static void main(String[] args) {
		new Main();
		
	}
	
	class GameTime extends Thread{
		public void run() {
            while (true) {
                try {
                	//1s
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gframe.isPause()) {
                    if (gframe.isResume()) {
                        gframe.time--;
                    }
                } else {
                    gframe.time--;
                   // gframe.time = gframe.time - 100;
                }
                if (gframe.time == 0) {
                	String name = JOptionPane.showInputDialog("Enter your name:");
                	if(name != null || JOptionPane.CANCEL_OPTION != 0) {
    				try {
    				    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    				    sql.addToDatabase(conn, name, score);
    				
    				} catch (SQLException e1) {
    				    e1.printStackTrace();
    				}finally {
    				JOptionPane.showMessageDialog(gframe, "Điểm của bạn là: " + score);
                	gframe.NewGameWindow("Het thoi gian", "You lose!",1);
    				}
    				}
                }
            
            }
		}
	}
}
	
	
