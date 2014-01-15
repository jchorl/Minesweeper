/*
 * Modified: April 5, 2012 by Aaron Goldberg. Added restrictions for customization, fixed accompanying bug from the automated settings
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Settings extends JPanel{
 private JSlider widthSlider= new JSlider(0, 30, 30);
 private JSlider heightSlider= new JSlider(0, 16, 16);
 private JSlider bombsSlider= new JSlider(0, 99, 99);
 private JRadioButton easyButton= new JRadioButton("Easy", false);
 private JRadioButton moderateButton= new JRadioButton("Moderate", false);
 private JRadioButton hardButton= new JRadioButton("Difficult", true);
 private JRadioButton customButton= new JRadioButton("Custom", false);
 private int height;
 private int width;
 private int bombs;
 private int difficulty;
 
 /**
  * Constructs a new Settings panel with sliders and slider restrictions
  */
 
 public Settings(){
  super.setLayout(new GridBagLayout());
  JPanel settingsPanel= new JPanel(new GridBagLayout());
  GridBagConstraints sc= new GridBagConstraints();
  JPanel radioPanel= new JPanel(new GridBagLayout());
  ButtonGroup bg= new ButtonGroup();
  bg.add(easyButton);
  bg.add(moderateButton);
  bg.add(hardButton);
  bg.add(customButton);
  GridBagConstraints rc= new GridBagConstraints();
  rc.gridx= 0;
  radioPanel.add(easyButton, rc);
  rc.gridx++;
  radioPanel.add(moderateButton, rc);
  rc.gridx++;
  difficulty= 2;
  radioPanel.add(hardButton, rc);
  rc.gridx++;
  radioPanel.add(customButton, rc);
  //^^just put the buttons all in a row

  JLabel widthLabel= new JLabel("Width");
  sc.gridy= 0;
  sc.insets= new Insets(30, 76,0,0);
  sc.anchor= GridBagConstraints.SOUTH;
  settingsPanel.add(widthLabel, sc);
  widthSlider.setMajorTickSpacing(5);
  widthSlider.setMinorTickSpacing(1);
  widthSlider.setPaintTicks(true);
  widthSlider.setPaintLabels(true);
  widthSlider.setSnapToTicks(true);
  sc.insets= new Insets(0,0,0,0);
  sc.anchor= GridBagConstraints.NORTH;
  sc.gridwidth= 2;
  sc.gridx= 0;
  sc.gridy= 1;
  settingsPanel.add(widthSlider, sc);
  heightSlider.setMajorTickSpacing(5);
  heightSlider.setMinorTickSpacing(1);
  heightSlider.setOrientation(JSlider.VERTICAL);
  heightSlider.setPaintTicks(true);
  heightSlider.setPaintLabels(true);
  heightSlider.setSnapToTicks(true);
  sc.gridwidth= 1;
  sc.gridx= 2;
  sc.gridy= 1;
  sc.gridheight= 3;
  settingsPanel.add(heightSlider, sc);
  JLabel heightLabel= new JLabel("Height");
  sc.insets= new Insets(30,0,0,0);
  sc.gridheight= 1;
  sc.gridy= 0;
  sc.gridx= 2;
  settingsPanel.add(heightLabel, sc);
  //width and height sliders done

  JLabel bombsLabel= new JLabel("Bombs");
  sc.gridy= 1;
  sc.gridx= 0;
  sc.insets= new Insets(100,76,0,0);
  sc.anchor= GridBagConstraints.NORTH;
  settingsPanel.add(bombsLabel, sc);
  bombsSlider.setMajorTickSpacing(20);
  bombsSlider.setMinorTickSpacing(5);
  bombsSlider.setPaintTicks(true);
  bombsSlider.setPaintLabels(true);
  bombsSlider.setSnapToTicks(true);
  sc.insets= new Insets(0,0,0,0);
  sc.anchor= GridBagConstraints.NORTH;
  sc.gridwidth= 2;
  sc.gridx= 0;
  sc.gridy= 2;
  settingsPanel.add(bombsSlider, sc);
  //bombs slider done
  width= widthSlider.getValue();
  height= heightSlider.getValue();
  bombs= bombsSlider.getValue();
  easyButton.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    width= 9;
    height= 9;
    bombs= 10;
    widthSlider.setValue(9);
    heightSlider.setValue(9);
    bombsSlider.setValue(10);
    easyButton.setSelected(true);
    difficulty= 0;
   }
  }); //if clicked on easy button
  moderateButton.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    width= 16;
    height= 16;
    bombs= 40;
    widthSlider.setValue(16);
    heightSlider.setValue(16);
    bombsSlider.setValue(40);
    moderateButton.setSelected(true);
    difficulty= 1;
   }
  }); //if clicked on moderate button
  hardButton.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    width= 30;
    height= 16;
    bombs= 99;
    widthSlider.setValue(30);
    heightSlider.setValue(16);
    bombsSlider.setValue(99);
    hardButton.setSelected(true);
    difficulty= 2;
   }
  }); //if clicked on hard button
  widthSlider.addChangeListener(new ChangeListener(){
   public void stateChanged(ChangeEvent c){
    width= widthSlider.getValue();
    customButton.setSelected(true);
    while (checkIfAllowable() != 0){
     if (checkIfAllowable() == 1)
      width++;//if there are too many bombs, increase the width
     else
      width--;//if there are too few bombs, decrease the width
     widthSlider.setValue(width);
    }//ensures that the bombs to squares ratio is acceptable
    if(widthSlider.getValue()==30 && heightSlider.getValue()==16 && bombsSlider.getValue()==99){
     hardButton.setSelected(true);
    }
    else if (widthSlider.getValue()==16 && heightSlider.getValue()==16 && bombsSlider.getValue()==40){
     moderateButton.setSelected(true);
    }
    else if(widthSlider.getValue()==9 && heightSlider.getValue()==9 && bombsSlider.getValue()==10){
     easyButton.setSelected(true);
    }

   }
  });//if moved width slider
  heightSlider.addChangeListener(new ChangeListener(){
   public void stateChanged(ChangeEvent c){
    customButton.setSelected(true);
    height= heightSlider.getValue();
    while (checkIfAllowable() != 0){
     if (checkIfAllowable() == 1)
      height++;//if there are too many bombs, increase the height
     else
      height--;//if there are too few bombs, decrease the height
     heightSlider.setValue(height);
    }//ensures that the bombs to squares ratio is acceptable
    if(widthSlider.getValue()==30 && heightSlider.getValue()==16 && bombsSlider.getValue()==99){
     hardButton.setSelected(true);
    }
    else if (widthSlider.getValue()==16 && heightSlider.getValue()==16 && bombsSlider.getValue()==40){
     moderateButton.setSelected(true);
    }
    else if(widthSlider.getValue()==9 && heightSlider.getValue()==9 && bombsSlider.getValue()==10){
     easyButton.setSelected(true);
    }

   }
  });//if moved height slider
  bombsSlider.addChangeListener(new ChangeListener(){
   public void stateChanged(ChangeEvent c){
    customButton.setSelected(true);
    bombs= bombsSlider.getValue();
    while (checkIfAllowable() != 0){
     if (checkIfAllowable() == 1)
      bombs= bombs - 5;//if there are too many bombs, increase the height
     else
      bombs= bombs + 5;//if there are too few bombs, decrease the height
     bombsSlider.setValue(bombs);
    }//ensures that the bombs to squares ratio is acceptable
    if(widthSlider.getValue()==30 && heightSlider.getValue()==16 && bombsSlider.getValue()==99){
     hardButton.setSelected(true);
    }
    else if (widthSlider.getValue()==16 && heightSlider.getValue()==16 && bombsSlider.getValue()==40){
     moderateButton.setSelected(true);
    }
    else if(widthSlider.getValue()==9 && heightSlider.getValue()==9 && bombsSlider.getValue()==10){
     easyButton.setSelected(true);
    }

   }
  });//if moved bombs slider
  GridBagConstraints mc= new GridBagConstraints();
  mc.anchor= GridBagConstraints.NORTH;
  mc.gridy= 0;
  super.add(radioPanel, mc);
  mc.gridy= 1;
  super.add(settingsPanel, mc);
 }
 
 /**
  * Gets the set width for the board in squares
  * @return the width of the board in squares
  */
 
 public int getWidthSquares(){
  setWidthSquares(widthSlider.getValue());
  return width;
 }
 
 /**
  * Sets the width in squares
  * @param i the width in squares
  */
 
 public void setWidthSquares(int i){
  width= i;
 }
 
 /**
  * Gets the height for the board in squares
  * @return the height in squares
  */
 
 public int getHeightSquares(){
  setHeightSquares(heightSlider.getValue());
  return height;
 }
 
 /**
  * Sets the height in squares
  * @param i the height in squares
  */
 
 public void setHeightSquares(int i){
  height= i;
 }
 
 /**
  * Get the amount of bombs to place on the board
  * @return the amount of bombs
  */
 
 public int getBombs(){
  setBombs(bombsSlider.getValue());
  return bombs;
 }
 
 /**
  * Sets the amount of bombs to place on the board
  * @param i the amount of bombs
  */
 
 public void setBombs(int i){
  bombs= i;
 }
 
 /**
  * Gets the difficulty level of the game
  * @return the difficulty as an int
  */
 
 public int getDifficulty(){
  return difficulty;
 }
 
 /**
  * Checks to see if the ratio of bombs:cells is allowable
  * @return 1 for allowable and 0 for not allowable
  */
 
 public byte checkIfAllowable(){
  int totalSquares= width * height;
  if (totalSquares < 100 && bombs > totalSquares * 0.6)
   return 1;
  else if (totalSquares < 320 && bombs > totalSquares * 0.4)
   return 1;
  else if (totalSquares >= 320 && bombs > totalSquares * 0.3)
   return 1;
  else if (totalSquares < 100 && bombs < totalSquares * 0.05)
   return -1;
  else if (totalSquares < 320 && bombs < totalSquares * 0.1)
   return -1;
  else if (totalSquares >= 320 && bombs < totalSquares * 0.2)
   return -1;
  return 0;
 }//returns 1 if there are too many bombs, -1 if there are too few, 0 if # is acceptable
}
