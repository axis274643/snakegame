package snekGame;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import javax.swing.*;

import java.util.*;
import java.util.List;

public class Snek extends JPanel {
	
	JLabel suffer = new JLabel();
	
	static int level = 1;
	
	static boolean levelWon = false;
	
	static boolean gameStarted = false;
	
	static boolean restart = false;
	
	static boolean catAlive = true;
	
	static int boardResolution = 40;
	static int boardWidthPixels = 800;
	static int boardHeightPixels = 600;
	static int boardWidth = boardWidthPixels/boardResolution;
	static int boardHeight = boardHeightPixels/boardResolution;
	static int boardPosX = 350;
	static int boardPosY = 100;
	
	
	int foodCount = 0;
	
	static String deathMessage = "death";
	
	double speed = (int) (100/boardResolution);
	
	
	List<JLabel> naughtyList = new ArrayList<JLabel>();
	List<JLabel> foodList = new ArrayList<JLabel>();
	List<JLabel> allObjectsList = new ArrayList<JLabel>();
	
	List<JLabel> backgroundSquaresList = new ArrayList<JLabel>();
	
	int squareColorRed = 0;
	int squareColorGreen = 0;
	int squareColorBlue = 0;
	int squareTintRed = 0;
	int squareTintGreen = 0;
	int squareTintBlue = 0;
	
	//cat
	String catDirection = null;
	String lastCatDirection = null;
	
	int catLocationX = boardPosX + (int)(boardWidth/2)*boardResolution;
	int catLocationY = boardPosY + (int)(boardHeight/2)*boardResolution;
	int catLength = 1;
	
	List<Integer> catAllX = new ArrayList<Integer>();
	List<Integer> catAllY = new ArrayList<Integer>();
	
	
	
	public Snek() throws InterruptedException {
		
		JFrame f = new JFrame();
		f.add(new detectKeyPressCat());
		
		JPanel pa = new JPanel();
		pa.setLayout(null);
		pa.setEnabled(false);
		
		JLabel cat = new JLabel("' O '", SwingConstants.CENTER);
		player(cat,pa);
		
		for(int v = 0; v < (int) boardWidth; v++) {
			
			for(int h = 0; h < (int) boardHeight; h++) {
				JLabel square = new JLabel();
				square.setOpaque(true);
				square.setBounds(boardPosX + v*(boardResolution),boardPosY + h*(boardResolution),boardResolution,boardResolution);
				
				if(v%2 == h%2) {
					if(level == 5) {
						square.setBackground(new Color(100,200,100));
					}else if(level == 10) {
						square.setBackground(new Color(150,130,200));
					}else if(level == 15) {
						square.setBackground(new Color(230, 210, 100));
					}else {
						square.setBackground(new Color(squareColorRed,squareColorGreen,squareColorBlue));
					}
				}else {
					if(level==10) {
						square.setBackground(new Color(90,190,90));
					}else if(level == 20) {
						square.setBackground(new Color(140,120,190));
					}else if(level == 30) {
						square.setBackground(new Color(230,180,100));
					}else {
						square.setBackground(new Color(squareColorRed-squareTintRed,squareColorGreen-squareTintGreen,squareColorBlue-squareTintBlue));
					}
				}
				
				pa.add(square);
				backgroundSquaresList.add(square);
				square.setVisible(true);
			}
			
			
		}
		
		
		
		JButton nextButton = new JButton("next level thing?");
		nextButton.setBounds(boardWidthPixels/2 + boardPosX - 50, boardHeightPixels/2 - 25 + boardPosY,100,50);
		
		
		while(true) {
			
			gameStarted = false;
			levelWon = false;
			
			if(restart == false) {
				squareColorRed = (int) (Math.random()*70+140);
				squareColorGreen = (int) (Math.random()*70+140);
				squareColorBlue = (int) (Math.random()*70+140);
				squareTintRed = (int) (Math.random()*20+10);
				squareTintGreen = (int) (Math.random()*20+10);
				squareTintBlue = (int) (Math.random()*20+10);
				
				pa.removeAll();
				pa.revalidate();
				pa.repaint();
				
				naughtyList.clear();
				foodList.clear();
				allObjectsList.clear();
				
				cat.setText("' O '");
				cat.setOpaque(true);
				cat.setFont(new Font("Arial", Font.BOLD, 20));
				cat.setBounds(boardPosX + (int)(boardWidth/2)*boardResolution,boardPosY + (int)(boardHeight/2)*boardResolution,boardResolution+1,boardResolution+1);
				cat.setBackground(new Color(255, 50, 50));
				cat.setForeground(new Color(255,255,255));
				allObjectsList.add(cat);
				pa.add(cat);
				cat.setVisible(true);
				
				//make stuff
				
				changeBackgroundColor(pa);
				
				
				pa.repaint();
				f.add(pa);
				f.setSize(1500,1000);
				f.getContentPane().setBackground(new Color(255, 255, 255));
				f.setComponentZOrder(pa,0);
				f.setVisible(true);
				
				if(level > 1) {
					for(int i = 0; i < level * level; i++) {
						generateEvilFood(pa,cat,1);
						Thread.sleep(1);
					}
				}
				foodCount = 0;
				//start game
				
				pa.setEnabled(true);
			}else {
				cat.setText("' O '");
				cat.setOpaque(true);
				cat.setFont(new Font("Arial", Font.BOLD, 20));
				cat.setBounds(boardPosX + (int)(boardWidth/2)*boardResolution,boardPosY + (int)(boardHeight/2)*boardResolution,boardResolution+1,boardResolution+1);
				cat.setBackground(new Color(255, 50, 50));
				cat.setForeground(new Color(255,255,255));
				
			}
			
			catAlive = true;
			restart = false;
			
			speed = (int) (100/boardResolution);
			
			catDirection = null;
			lastCatDirection = null;
			
			catLocationX = boardPosX + (int)(boardWidth/2)*boardResolution;
			catLocationY = boardPosY + (int)(boardHeight/2)*boardResolution;
			
			catLength = 1;
			
			
			catAllX.add(catLocationX);
			catAllY.add(catLocationY);
			
			
			
			//set up board
			
//			for(int i = 0; i < pa.getComponentCount() - 1; i++) {
//				List<JLabel> nullify = new ArrayList<JLabel>();
//				nullify.add((JLabel) pa.getComponent(i));
//				nullify.get(i) = null;
//			}
			
			
			
//			for(int i = 0; i < naughtyList.size(); i++) {
//				System.out.println(naughtyList.get(i).getText());
//			}
			System.out.println("Level " + level);
			System.out.println("Number of threads: " + Thread.activeCount());
			System.out.println("Number of components: " + pa.getComponentCount());
			System.out.println("Number of supposedly all components: " + allObjectsList.size());
			System.out.println("Number of evil things: " + naughtyList.size());
			System.out.println("Number of food things: " + foodList.size());
			
			gameStarted = true; 
			
			while(catLength < level*2 && catAlive == true && (catLocationX < boardPosX + boardWidth*boardResolution - boardResolution + 1 && catLocationY < boardPosY + boardHeight*boardResolution - boardResolution + 1 && catLocationX > boardPosX - 1 && catLocationY > boardPosY - 1)) {
				
				
				
				if(foodCount < level) {
					regenerateFood(pa,catLength,cat);
					foodCount += 1;
				}
				
				pa.setComponentZOrder(cat,0);
				
				if((double)(catLocationX-boardPosX)/boardResolution == (int) (catLocationX-boardPosX)/boardResolution && (double)(catLocationY-boardPosY)/boardResolution == (int) (catLocationY-boardPosY)/boardResolution) {
					lastCatDirection = catDirection;
				}
				
				moveInDirection(lastCatDirection, cat);
				
				if((catLocationX < boardPosX + boardWidth*boardResolution - boardResolution + 1 && catLocationY < boardPosY + boardHeight*boardResolution - boardResolution + 1 && catLocationX > boardPosX - 1 && catLocationY > boardPosY - 1) == false) {
					catAlive = false;
					System.out.println("you died");
				}
				
				f.repaint();
			}
			
			
			pa.add(nextButton);
			pa.setComponentZOrder(nextButton,0);
			nextButton.setVisible(false);
//			nextButton.setVisible(true);
			
			
			if(catAlive == true) {
				levelWon = true;
				
				JLabel yay = new JLabel("nice",SwingConstants.CENTER);
				yay.setFont(new Font("Arial", Font.BOLD, 34));
				yay.setOpaque(true);
				yay.setBounds(500,0,500,100);
				yay.setBackground(new Color(0,0,0));
				yay.setBackground(new Color(255,255,255));
				pa.add(yay);
				pa.setComponentZOrder(yay,catLength);
				yay.setVisible(true);
				f.repaint();
//				for(int i = 0; i < catLength; i++) {
//					regenerateFood(pa,catLength);
//					foodCount += 1;
//					f.repaint();
//				}
				
				while(foodCount != 0) {
					
					pa.setComponentZOrder(cat,0);
//					cat.setText(Integer.toString(catLength));
					
					if((double)(catLocationX-boardPosX)/boardResolution == (int) (catLocationX-boardPosX)/boardResolution && (double)(catLocationY-boardPosY)/boardResolution == (int) (catLocationY-boardPosY)/boardResolution) {
						lastCatDirection = catDirection;
					}
					
					moveInDirection(lastCatDirection, cat);
					
					
					f.repaint();
				}
				
				nextButton.setVisible(false);
				level += 1;
			}else {
				levelWon = false; 
				cat.setBackground(new Color(100,100,100));
				cat.setText("x  x");
				
				if((catLocationX < boardPosX + boardWidth*boardResolution - boardResolution + 1 && catLocationY < boardPosY + boardHeight*boardResolution - boardResolution + 1 && catLocationX > boardPosX - 1 && catLocationY > boardPosY - 1)==false) {
					deathMessage = "went to space";
				}
				
				JLabel yay = new JLabel(deathMessage,SwingConstants.CENTER);
				
				yay.setFont(new Font("Arial", Font.BOLD, 20));
				yay.setOpaque(true);
				yay.setBounds(500,0,500,100);
				yay.setBackground(new Color(0,0,0));
				yay.setBackground(new Color(255,255,255));
				pa.add(yay);
				pa.setComponentZOrder(yay,catLength);
				yay.setVisible(true);
				f.repaint();
				while(restart == false) {
					cat.setBackground(new Color(100,100,100));
					Thread.sleep(1);
					cat.setBackground(new Color(0,0,0));
					Thread.sleep(1);
				}
				yay = null;
				allObjectsList.remove(cat);
			}
		}
			
	}
	
	public void player(JLabel character, JPanel p) {
		Thread playerThread = new Thread() {
			public void run() {
				while(true) {
					while(gameStarted == true) {
						for(int i = 0; i < naughtyList.size(); i++) {
							if(catLocationX == naughtyList.get(i).getX() && catLocationY == naughtyList.get(i).getX()) {
								catAlive = false;
							}
						}
					}
				}
			}
		};
		playerThread.start();
	}
		
	public void regenerateFood(JPanel p, int len, JLabel caat) throws InterruptedException {
        Thread touchingCat = new Thread() {
			public void run() {
				int currentLevel = level;
				int currentLength;
				int foodPosX = 0;
				int foodPosY = 0;
				
				boolean needXY = true;
				
				while(needXY) {
					foodPosX = (int) (Math.random()*(boardWidth))*boardResolution + boardPosX;
					foodPosY = (int) (Math.random()*(boardHeight))*boardResolution + boardPosY;
					needXY = false;
					for(int i = 0; i < allObjectsList.size(); i++) {
						if(foodPosX == allObjectsList.get(i).getX() && foodPosY == allObjectsList.get(i).getY()) {
							needXY = true;
							break;
						}
					}
				}
				
				int red = (int) (Math.random()*200+55);
				int green = (int) (Math.random()*200+55);
				int blue = (int) (Math.random()*200+55);
				
				JLabel food = new JLabel();
				
				food.setOpaque(true);
				food.setBackground(new Color(red,green,blue));
				food.setBounds(foodPosX,foodPosY,boardResolution+1,boardResolution+1);
				p.add(food);
		        p.setComponentZOrder(food,len);
		        food.setVisible(true);
		        foodList.add(food);
		        allObjectsList.add(food);
				
				while((catLocationX != foodPosX || catLocationY != foodPosY) && food.getParent()==p) {
					suffer.addNotify();
				}
				if(food.getParent()==p) {
					caat.setText("^-^");
					currentLength = catLength;
					foodCount -= 1;
					foodList.remove(food);
					allObjectsList.remove(food);
					catLength += 1;
					p.setComponentZOrder(food,currentLength);
					try {
						Thread.sleep((long) ((boardResolution + currentLength/2)*(currentLength)*speed + boardResolution/currentLength + boardResolution*currentLength));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(food.getParent()==p) {
						caat.setText("' O '");
						while(catAlive == true && currentLevel==level && food.getParent()==p) {
							while(catDirection != null && restart == false && catAlive == true) {
								int foodX = (int) catAllX.get(catAllX.size()-boardResolution*(currentLength)-1);
								int foodY = (int) catAllY.get(catAllY.size()-boardResolution*(currentLength)-1);
								food.setLocation(foodX,foodY);
								
								if(catLocationX == foodX && catLocationY == foodY && levelWon == false) {
									catAlive = false;
									levelWon = false;
									deathMessage = "ran over self";
									food.setBackground(new Color(100,100,100));
								}
							}
							try {
								this.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
				while(catAlive == false) {
					food.setBackground(new Color(100,100,100));
				}
				food.setVisible(false);
				p.remove(food);
				food = null;
				suffer.removeNotify();
				
				//System.out.println("Thread ended: food");
				
			}
		};
		
		touchingCat.start();
		Thread.sleep(10);
	}
	
	public void generateEvilFood(JPanel p, JLabel deadCat, int len) throws InterruptedException {
        Thread touchingCat = new Thread() {
			public void run() {
				int currentLevel = level;
				int foodPosX = 0;
				int foodPosY = 0;
				
				boolean needXY = true;
				
				while(needXY) {
					foodPosX = (int) (Math.random()*(boardWidth))*boardResolution + boardPosX;
					foodPosY = (int) (Math.random()*(boardHeight))*boardResolution + boardPosY;
					needXY = false;
					for(int i = 0; i < allObjectsList.size(); i++) {
						if(foodPosX == allObjectsList.get(i).getX() && foodPosY == allObjectsList.get(i).getY()) {
							needXY = true;
							break;
						}
					}
				}
				
				JLabel evil = new JLabel(">:D",SwingConstants.CENTER);
				
				evil.setFont(new Font("Arial", Font.BOLD, 17));
				evil.setOpaque(true);
				evil.setBackground(new Color(0,0,0));
				evil.setForeground(new Color(255,255,255));
				evil.setBounds(foodPosX,foodPosY,boardResolution,boardResolution);
				p.add(evil);
		        p.setComponentZOrder(evil,0);
		        evil.setVisible(true);
		        naughtyList.add(evil);
		        allObjectsList.add(evil);
				
				while(evil.getParent()==p && levelWon == false) {
					if(restart==true) {
						evil.setText(">:D");
					}
//					while((catLocationX != foodPosX || catLocationY != foodPosY) && evil.getParent()==p && levelWon == false) {
//						suffer.addNotify();
//					}
					if(levelWon == true && catAlive == true && restart == false) {
						evil.setText(">:(");
					}else if(restart == false){
						evil.setText(">:)");
						if((catLocationX == foodPosX && catLocationY == foodPosY)){
							deathMessage = "ate evil food";
							catAlive = false;
							levelWon = false;
						}
					}
					
				}
				while(evil.getParent()==p) {
					try {
						this.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				evil.setVisible(false);
				naughtyList.remove(evil);
				allObjectsList.remove(evil);
				evil = null;
				suffer.removeNotify();
				//System.out.println("Thread ended: evil");
				
			}
			
		};
		
		touchingCat.start();
		Thread.sleep(10);
	}
	
	public void changeBackgroundColor(JPanel p) {
		
		for(int i = 0; i < backgroundSquaresList.size();i++) {
			JLabel square = backgroundSquaresList.get(i);
			square.setOpaque(true);
			square.setBounds(backgroundSquaresList.get(i).getX(),backgroundSquaresList.get(i).getY(),boardResolution,boardResolution);
			
			if(i%2==0) {
				if(level == 5) {
					square.setBackground(new Color(100,200,100));
				}else if(level == 10) {
					square.setBackground(new Color(150,130,200));
				}else if(level == 15) {
					square.setBackground(new Color(230, 210, 100));
				}else {
					square.setBackground(new Color(squareColorRed,squareColorGreen,squareColorBlue));
				}
			}else {
				if(level==5) {
					square.setBackground(new Color(90,190,90));
				}else if(level == 10) {
					square.setBackground(new Color(140,120,190));
				}else if(level == 15) {
					square.setBackground(new Color(230,180,100));
				}else {
					square.setBackground(new Color(squareColorRed-squareTintRed,squareColorGreen-squareTintGreen,squareColorBlue-squareTintBlue));
				}
			}
			
			p.add(square);
			square.setVisible(true);
		}
	}
	
	public void moveInDirection(String d, JLabel l) throws InterruptedException {
		if(catAlive == true) {
			if(d == "Left") {
				catLocationX = catLocationX - 1;
			}else if(d == "Right") {
				catLocationX = catLocationX + 1;
			}else if(d == "Up") {
				catLocationY = catLocationY - 1;
			}else if(d == "Down") {
				catLocationY = catLocationY + 1;
			}
			l.setLocation(catLocationX,catLocationY);
			catAllX.add(catLocationX);
			catAllY.add(catLocationY);
			Thread.sleep((long) speed);
		}
	}
	
	public class detectKeyPressCat extends JPanel {

        public detectKeyPressCat() {
            InputMap im = getInputMap();
            ActionMap am = getActionMap();

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "Right");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "Left");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "Up");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "Down");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Restart");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, 0, false), "Pause");
            
            
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "Right");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "Left");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Up");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "Down");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, false), "Restart");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "Pause");
            
            

            am.put("Left", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(gameStarted==true) {
                		catDirection = "Left";
                	}
                }
            });
            am.put("Right", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(gameStarted==true) {
                		catDirection = "Right";
                	}
                }
            });
            am.put("Up", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(gameStarted==true) {
                		catDirection = "Up";
                	}
                }
            });
            am.put("Down", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(gameStarted==true) {
                		catDirection = "Down";
                	}
                }
            });
            am.put("Restart", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    restart = true;
                    System.out.println("restart");
                }
            });
            am.put("Pause", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    catDirection = null;
                    System.out.println("Pause");
                }
            });
            

            setFocusable(true);
            requestFocusInWindow();        
        }

    }
	
	public class mouseClicked extends JPanel {

        public mouseClicked() {
            InputMap im = getInputMap();
            ActionMap am = getActionMap();

            im.put(KeyStroke.getKeyStroke(1,1,false), "Right");

            am.put("Left", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	catDirection = "Left";
                }
            });

            setFocusable(true);
            requestFocusInWindow();        
        }

    }
	
	public static void main(String[] args) throws InterruptedException {
		
		new Snek();
		
	}
	
}
