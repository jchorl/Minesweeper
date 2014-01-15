import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;

public class Minesweeper{
	private Settings s= new Settings();
	private HighScorePanel h= new HighScorePanel();
	private Board b;
	private GameOver go;
	private JPanel mainPanel= new JPanel(new GridBagLayout());
	private JFrame frame= new JFrame("Minesweeper");
	private int difficulty;
	private GridBagConstraints mc= new GridBagConstraints();
	public static void main(String[] args){
		Minesweeper m= new Minesweeper();
	}

	/**
	 * Sets up the frame and controls which panels to display and when. Includes (at different times) the {@link HighScorePanel}, {@link Board} Panel, {@link Settings} Panel, and {@link GameOver} Panel.
	 */

	public Minesweeper(){
		frame.setSize(720,454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel headerPanel= new JPanel(new GridBagLayout());
		ImageIcon header= new ImageIcon();
		try{
			URL url= getClass().getResource("Header.jpg");
			header= new ImageIcon(ImageIO.read(url)); // it says "MINESWEEPER"
		}catch(Exception e){}
		GridBagConstraints hc= new GridBagConstraints();
		hc.anchor= GridBagConstraints.NORTH;
		headerPanel.add(new JLabel(header), hc);
		mc.gridy= 0;
		mc.gridwidth= 2;
		mc.anchor= GridBagConstraints.NORTH;
		mainPanel.add(headerPanel, mc);//header panel set up and added
		mc.gridwidth= 1;
		mc.gridy= 1;
		mc.gridwidth= 2;
		mainPanel.add(s, mc);//adds settings panel
		mc.gridx= 2;
		mc.gridwidth=1;
		mc.insets= new Insets(40,0,0,0);
		mainPanel.add(h, mc);//adds high score panel
		JButton start= new JButton("Start");
		final ImageIcon[] images= setImages();
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				difficulty= s.getDifficulty();//gets difficulty from settings panel
				b= new Board(s.getWidthSquares(), s.getHeightSquares(), s.getBombs(), Minesweeper.this, images);//new instance of board
				mainPanel.removeAll();
				if(24*s.getWidthSquares()>=480){
					frame.setSize(24*s.getWidthSquares(), 50+s.getHeightSquares()*24+frame.getInsets().top);
				}
				else{
					frame.setSize(480, 50+s.getHeightSquares()*24+frame.getInsets().top);
				}//sets window size, with a minimum size
				mc.insets= new Insets(0,0,0,0);
				mainPanel.add(b, mc);
				mainPanel.repaint();
				frame.validate();
			}
		});//start button listeners are added
		mc.weighty++;
		mc.gridy++;
		mc.gridx= 1;
		mc.gridwidth= 1;
		mc.insets= new Insets(0,0,0,175);
		mainPanel.add(start, mc);//adds start button
		JButton quit= new JButton("Quit");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		mc.insets= new Insets(0,0,0,75);
		mc.gridx--;
		mainPanel.add(quit, mc);//adds quit button
		JButton reset= new JButton("Reset High Scores");
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					HighScore.newRecord(new HighScore("-", 0, 0));
					HighScore.newRecord(new HighScore("-", 0, 1));
					HighScore.newRecord(new HighScore("-", 0, 2));//writes blank highscores to file
				}catch(Exception f){}
				mainPanel.remove(h);
				h= new HighScorePanel();
				GridBagConstraints mc1= new GridBagConstraints();
				mc1.gridwidth= 1;
				mc1.gridy= 1;
				mc1.gridx= 2;
				mc1.insets= new Insets(40,0,0,0);//new constraints mirroring those from before so it looks the same
				mainPanel.add(h, mc1);
				mainPanel.repaint();
				frame.validate();
			}
		});
		mc.gridx= mc.gridx+2;
		mc.insets= new Insets(0,10,0,0);
		mainPanel.add(reset, mc);
		frame.add(mainPanel);//add main panel to frame
		frame.setVisible(true);
	}

	public ImageIcon[] setImages(){
		ImageIcon[] images= new ImageIcon[14];
		URL[] url= new URL[14];
		url[0]= getClass().getResource("0cell.jpg");
		url[1]= getClass().getResource("1cell.jpg");
		url[2]= getClass().getResource("2cell.jpg");
		url[3]= getClass().getResource("3cell.jpg");
		url[4]= getClass().getResource("4cell.jpg");
		url[5]= getClass().getResource("5cell.jpg");
		url[6]= getClass().getResource("6cell.jpg");
		url[7]= getClass().getResource("7cell.jpg");
		url[8]= getClass().getResource("8cell.jpg");
		url[9]= getClass().getResource("obombedcells.jpg");
		url[10]= getClass().getResource("bombedcell.jpg");
		url[11]= getClass().getResource("flaggedcell.jpg");
		url[12]= getClass().getResource("cell.jpg");
		url[13]= getClass().getResource("incorrectflag.jpg");
		try{
			for(int i= 0; i<14; i++){
				images[i]= new ImageIcon(ImageIO.read(url[i]));
			}
		}catch(Exception e){}
		return images;
	}

	/**
	 * Get the main JFrame that everything is diplayed in
	 * @return main {@link JFrame}
	 */

	public JFrame getFrame(){
		return frame;
	}

	/**
	 * Called when the game is over, does different actions depending on if the user was victorious
	 * @param win Whether the player won or not
	 * @see Gameplay
	 */

	public void gameOver(boolean win){
		if(win){
			gameOverMenu();//they won
		}
		else{
			frame.setSize(frame.getWidth(), frame.getHeight()+30);
			b.addOkayButton();//adds okay button so people can see the bombs and then when they are ready, move on to the menu
		}
	}

	/**
	 * If the user won and broke a high score, this menu allows them to submit their name and be added to the record book.
	 * @see HighScore
	 */

	public void gameOverMenu(){
		mainPanel.removeAll();
		HighScore[] hsdb= HighScore.readFromFile();
		if(b.getTime()<hsdb[difficulty].getScore()||hsdb[difficulty].getScore()==0){//if they set a new highscore
			go= new GameOver(b.getTime(), difficulty, Minesweeper.this);
			mainPanel.add(go, mc);
			mainPanel.repaint();
			frame.validate();
		}
		else{//otherwise, new game
			frame.setVisible(false);
			Minesweeper m= new Minesweeper();
		}
	}

	/**
	 * Returns the Board of this instance of Minesweeper
	 * @return the Board
	 */

	public Board getBoard(){
		return b;
	}
}
