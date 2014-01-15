import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.*;

/**
 * A {@link JPanel} containing a gameOver screen for users to submit high scores
 * @see HighScore
 * @author Josh Chorlton
 *
 */

public class GameOver extends JPanel{
	private JTextField nameField= new JTextField();
	private int difficulty;
	private JFrame jf;
	private Minesweeper j;
	public GameOver(final int t, int d, Minesweeper jo){
		jf= jo.getFrame();
		j= jo;
		difficulty= d;
		super.setLayout(new GridBagLayout());
		GridBagConstraints gbc= new GridBagConstraints();
		ImageIcon img= new ImageIcon();
		ImageIcon header= new ImageIcon();
		try{
			URL url= getClass().getResource("Header2.jpg");
			Image headerImage= ImageIO.read(url);
			URL ggurl= getClass().getResource("goodgame.jpg");
			Image goodGameImage= ImageIO.read(ggurl);
			header= new ImageIcon(headerImage); // it says "MINESWEEPER"
			img= new ImageIcon(goodGameImage);
		}catch(Exception e){}
		gbc.gridwidth= 3;
		gbc.gridy= 0;
		super.add(new JLabel(header), gbc);
		gbc.gridy= 1;
		super.add(new JLabel(img), gbc);
		//adds good game image to panel
		JLabel nameLabel= new JLabel("Name:");
		nameField.setPreferredSize(new Dimension(200, 20));
		String timeString= t/600+""+t/60%10+":"+t/10%6+""+t%10;
		JLabel timeLabel= new JLabel("Your time was "+timeString);
		gbc.gridy= 2;
		super.add(timeLabel, gbc);
		gbc.gridwidth= 1;
		gbc.gridy= 3;
		gbc.gridx= 0;
		gbc.weightx= 1;
		super.add(nameLabel, gbc);
		gbc.gridx= 1;
		super.add(nameField, gbc);
		JButton submitButton= new JButton("Submit");
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				HighScore hs= null;
				boolean goodName= true;
				try{
					hs= new HighScore(nameField.getText(), t, difficulty);//new highscore
				}catch(Exception InvalidNameException){
					JOptionPane jop= new JOptionPane();
					jop.showMessageDialog(jf, "Invalid Username Entered", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
					j.gameOverMenu();
					goodName= false;
				}//if they enter a bad name
				if(goodName){
					HighScore.newRecord(hs);
					jf.setVisible(false);
					Minesweeper m= new Minesweeper();//new instance of minesweeper
				}
			}
		});//adds submit button listeners
		gbc.gridx= 2;
		super.add(submitButton, gbc);
	}
}