import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Generates the board on which the game is played
 * @author Josh Chorlton
 *
 */

public class Board extends JPanel{
	private int time= 0;
	private String timeString= "00:00";
	private JLabel bombsLabel= new JLabel();
	private int bombs= 0;
	private int heightSquares;
	private Date d= new Date();
	private JPanel gamePanel= new JPanel(new GridBagLayout());
	private JButton[] j;
	private Gameplay g;
	private boolean firstLeftClick= true;
	private JLabel timeLabel= new JLabel();
	private JPanel headerPanel= new JPanel(new GridBagLayout());
	private boolean singleClick= true;//boolean to detect double clicks
	private Timer t= new Timer();
	private Minesweeper menu;
	private ArrayList<MouseListener> cmls= new ArrayList<MouseListener>();
	private ImageIcon[] images;

	/**
	 * Constructs a new Board using width, height, bomb count, and the Minesweeper creating it
	 * @param width the width in cells of the Board
	 * @param height the height in cells of the Board
	 * @param nob the number of bombs to create
	 * @param m the Minesweeper that created it
	 * @see Gameplay
	 */

	public Board(int width, int height, int nob, Minesweeper m, ImageIcon[] is){
		images= is;
		menu= m;
		d= new Date();
		g= new Gameplay(height, width, this, nob, m);
		setH(height);
		super.setLayout(new GridBagLayout());
		ImageIcon header= new ImageIcon();
		j= new JButton[width*height];
		try{
			URL cellURL= getClass().getResource("cell.jpg");
			Image cellImage= ImageIO.read(cellURL);
			ImageIcon cellII= new ImageIcon(cellImage);
			for(int i= 0; i<j.length; i++){
				j[i]= new JButton(cellII);
			}
			URL headerURL= getClass().getResource("Header.jpg");
			Image headerImage= ImageIO.read(headerURL);
			header= new ImageIcon(headerImage);
		}catch(Exception e){}
		GridBagConstraints hc= new GridBagConstraints();
		hc.gridx= 1;
		hc.anchor= GridBagConstraints.CENTER;
		headerPanel.add(new JLabel(header), hc);
		headerPanel.setBackground(Color.white);//header image
		try{
			URL bombURL= getClass().getResource("Bomb.png");
			Image bombImage= ImageIO.read(bombURL);
			ImageIcon bombII= new ImageIcon(bombImage);
			bombsLabel= new JLabel(Integer.toString(bombs), bombII, SwingConstants.LEFT);
		}catch(Exception e){}
		hc.gridx= 0;
		hc.insets= new Insets(0,0,0,15);
		headerPanel.add(bombsLabel, hc);
		try{
			URL clockURL= getClass().getResource("Clock.png");
			Image clockImage= ImageIO.read(clockURL);
			ImageIcon clockII= new ImageIcon(clockImage);
			timeLabel= new JLabel(timeString, clockII, SwingConstants.LEFT);
		}catch(Exception e){}
		hc.gridx= 2;
		hc.insets= new Insets(0,15,0,0);
		headerPanel.add(timeLabel, hc);//bomb label and time label are set up
		GridBagConstraints c= new GridBagConstraints();
		c.anchor= GridBagConstraints.NORTHWEST;
		c.insets= new Insets(1,1,1,1);
		int l= 0;
		for(int i= 0; i<width; i++){
			for(int q= 0; q<height; q++){
				c.gridx= i;
				c.gridy= q;
				j[l].setBorder(null);
				CustomMouseListener cml= new CustomMouseListener();
				cml.setID(l);
				cmls.add(cml);
				j[l].addMouseListener(cml);
				gamePanel.add(j[l], c);
				l++;
			}
		}//adds a listener to every jbutton
		try{
			setBombs(nob);
		}catch(Exception e){}
		gamePanel.setBackground(Color.black);
		GridBagConstraints mc= new GridBagConstraints();
		mc.anchor= GridBagConstraints.NORTH;
		mc.gridy= 0;
		super.add(headerPanel, mc);
		mc.gridy= 1;
		super.add(gamePanel, mc);
		//adds the gamePanel to the main panel, which will be added to the main frame
		t.schedule(new TimerTask(){
			public void run(){
				Date d2= new Date();
				try {
					setTime((int)(d2.getTime()-d.getTime()));
				}catch(Exception e){}
			}
		}, 500, 500);
	}//adds timer for game timer

	/**
	 * Sets the height in squares of the Board
	 * @param h the height in squares
	 */

	private void setH(int h){
		heightSquares= h;
	}

	/**
	 * Gets the height in squares of the Board
	 * @return the height in squares
	 */

	private int getH(){
		return heightSquares;
	}

	/**
	 * Replaces a cell with a different kind of cell
	 * @param cellNumber the unique ID of the cell to replace
	 * @param type the type of cell to replace it with
	 */

	public void drawThing(int cellNumber, int type){
		gamePanel.remove(j[cellNumber]);
		if(type==0){
			try {
				j[cellNumber]= new JButton(images[0]);
				j[cellNumber].setBorder(null);
			}catch(Exception e){}
		}
		else if(type==1){
			try {
				j[cellNumber]= new JButton(images[1]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==2){
			try {
				j[cellNumber]= new JButton(images[2]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==3){
			try {
				j[cellNumber]= new JButton(images[3]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==4){
			try {
				j[cellNumber]= new JButton(images[4]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==5){
			try {
				j[cellNumber]= new JButton(images[5]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==6){
			try {
				j[cellNumber]= new JButton(images[6]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==7){
			try {
				j[cellNumber]= new JButton(images[7]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==8){
			try {
				j[cellNumber]= new JButton(images[8]);
				j[cellNumber].setBorder(null);
				TimerMouseListener tml= new TimerMouseListener();
				tml.setID(cellNumber);
				j[cellNumber].addMouseListener(tml);
				cmls.remove(cellNumber);
				cmls.add(cellNumber, tml);
			}catch(Exception e){}
		}
		else if(type==9){
			try {
				j[cellNumber]= new JButton(images[9]);
				j[cellNumber].setBorder(null);
			}catch(Exception e){}
		}
		else if(type==10){
			try {
				j[cellNumber]= new JButton(images[10]);
				j[cellNumber].setBorder(null);
			}catch(Exception e){}
		}
		else if(type==11){
			try{
				j[cellNumber]= new JButton(images[11]);
				j[cellNumber].setBorder(null);
				CustomMouseListener cml= new CustomMouseListener();
				cml.setID(cellNumber);
				j[cellNumber].addMouseListener(cml);
			}catch(Exception e){}
		}
		else if(type==12){
			try{
				j[cellNumber]= new JButton(images[12]);
				j[cellNumber].setBorder(null);
				CustomMouseListener cml= new CustomMouseListener();
				cml.setID(cellNumber);
				j[cellNumber].addMouseListener(cml);
			}catch(Exception e){}
		}
		else if(type==13){
			try{
				j[cellNumber]= new JButton(images[13]);
				j[cellNumber].setBorder(null);
			}catch(Exception e){}
		}//paint different images on cells, some add action listeners like flags and numbers that can be double clicked
		GridBagConstraints c= new GridBagConstraints();
		c.insets= new Insets(1,1,1,1);
		c.gridx= cellNumber/getH();
		c.gridy= cellNumber%getH();
		gamePanel.add(j[cellNumber], c);
	}

	/**
	 * Sets the time elapsed of the game and displays it
	 * @param t the time elapsed in milliseconds 
	 * @throws TimeException if the time is an impossible value
	 */

	public void setTime(long t) throws Exception{
		if(t<0){
			throw new TimeException();
		}
		time= Math.round(t/1000);
		timeString= time/600+""+time/60%10+":"+time/10%6+""+time%10;
		GridBagConstraints g= new GridBagConstraints();
		headerPanel.remove(timeLabel);
		URL clockURL= getClass().getResource("Clock.png");
		Image clockImage= ImageIO.read(clockURL);
		ImageIcon clockII= new ImageIcon(clockImage);
		timeLabel= new JLabel(timeString, clockII, SwingConstants.LEFT);
		g.gridx= 2;
		g.insets= new Insets(0,15,0,0);
		headerPanel.add(timeLabel, g);
		super.validate();
	}//gui related, sets the time

	/**
	 * Returns the time elapsed
	 * @return the time elapsed in milliseconds
	 */

	public int getTime(){
		return time; 
	}//returns gametime

	public JPanel getGamePanel(){
		return gamePanel;
	}

	/**
	 * Sets the number of bombs remaining and updates the display
	 * @param b the number of bombs remaining
	 * @throws BombsException if the number of remaining bombs is an impossible value
	 */

	public void setBombs(int b) throws Exception{
		if(b<0){
			throw new BombsException();
		}
		bombs= b;
		headerPanel.remove(bombsLabel);
		try{
			URL bombURL= getClass().getResource("Bomb.png");
			Image bombImage= ImageIO.read(bombURL);
			ImageIcon bombII= new ImageIcon(bombImage);
			bombsLabel= new JLabel(Integer.toString(bombs), bombII, SwingConstants.LEFT);
		}catch(Exception e){}
		GridBagConstraints hc= new GridBagConstraints();
		hc.gridx= 0;
		hc.anchor= GridBagConstraints.CENTER;
		hc.insets= new Insets(0,0,0,15);
		headerPanel.add(bombsLabel, hc);
		super.validate();//all gui related, rplaces bombs label with updated one
	}

	/**
	 * Adds an okay button to the frame so that the player can review the bomb locations and then click okay to return to the menu
	 */

	public void addOkayButton(){
		for(int i= 0; i<j.length; i++){
			j[i].setDisabledIcon(j[i].getIcon());
			j[i].setEnabled(false);
			j[i].removeMouseListener(cmls.get(i));
		}//for loop renders board useless once game is lost
		t.cancel();//stops the game timer
		JButton okayButton= new JButton("Okay");
		okayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				menu.getFrame().setVisible(false);//closes old minesweeper
				Minesweeper m= new Minesweeper();//creates new instance of minesweeper
			}
		});
		JPanel okbPanel= new JPanel(new GridBagLayout());
		GridBagConstraints gbc= new GridBagConstraints();
		okbPanel.add(okayButton, gbc);
		gbc.gridy= 2;
		super.add(okbPanel, gbc);
	}

	public Timer getTimer(){
		return t;
	}

	/**
	 * A custom mouse listener that reacts based on the type of click. Inside it stores the ID of the cell for which it is listening.
	 * @author Josh Chorlton
	 *
	 */

	class CustomMouseListener implements MouseListener{
		private int l;
		private int x;
		private int y;

		/**
		 * Sets the ID of the cell
		 * @param h the ID of the cell
		 */

		public void setID(int h){
			l= h;
		}

		/**
		 * If there is a click
		 */

		public void mouseClicked(MouseEvent e){
			if(e.getButton()==MouseEvent.BUTTON3){
				g.buttonClicked(l, 1, false);
			}//if right click...
			else{
				g.buttonClicked(l, 0, firstLeftClick);
				firstLeftClick= false;
			}
		}
		public void mouseEntered(MouseEvent e){
		}
		public void mouseExited(MouseEvent e){
		}
		public void mousePressed(MouseEvent e){
			x= e.getX();
			y= e.getY();
		}

		/**
		 * if the user clicks and drags but releases on the same tile
		 */

		public void mouseReleased(MouseEvent e){
			if(e.getX()>=0&&e.getX()<=x&&e.getY()>=0&&e.getY()<=y){
				mouseClicked(e);
			}
			else if(e.getX()<=22&&e.getX()>=x&&e.getY()>=0&&e.getY()<=y){
				mouseClicked(e);
			}
			else if(e.getX()>=0&&e.getX()<=x&&e.getY()<=22&&e.getY()>=y){
				mouseClicked(e);
			}
			else if(e.getX()<=22&&e.getX()>=x&&e.getY()<=22&&e.getY()>=y){
				mouseClicked(e);
			}//if click and drag but the mouse is released on the same button
		}
	}//custom mouse listener

	/**
	 * Another custom mouse listener for numbered cells listening for a double click
	 * @author Josh Chorlton
	 *
	 */

	class TimerMouseListener implements MouseListener{
		private int l;
		private int x;
		private int y;
		public void setID(int h){
			l= h;
		}
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount()==2){
				g.buttonClicked(l, 2, false);
			}
		}
		public void mouseEntered(MouseEvent e){
		}
		public void mouseExited(MouseEvent e){
		}
		public void mousePressed(MouseEvent e){
			x= e.getX();
			y= e.getY();
		}
		public void mouseReleased(MouseEvent e){
			if(e.getX()>=0&&e.getX()<=x&&e.getY()>=0&&e.getY()<=y){
				mouseClicked(e);
			}
			else if(e.getX()<=22&&e.getX()>=x&&e.getY()>=0&&e.getY()<=y){
				mouseClicked(e);
			}
			else if(e.getX()>=0&&e.getX()<=x&&e.getY()<=22&&e.getY()>=y){
				mouseClicked(e);
			}
			else if(e.getX()<=22&&e.getX()>=x&&e.getY()<=22&&e.getY()>=y){
				mouseClicked(e);
			}//if click and drag but the mouse is released on the same button
		}
	}//mouseListener on buttons that have been pressed, timer giving a time limit to double click
}
