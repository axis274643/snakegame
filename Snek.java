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
	
	public static void main(String[] args) throws InterruptedException {
		new Snek();
	}
	
	final int boardResolution = 40;
	final int boardWidth = 20;
	final int boardHeight = 15;
	final int boardPosX = 350;
	final int boardPosY = 100;
	
	static String catDirection = null;
	static String lastCatDirection = null;
	int catLocationX = boardPosX + (int)(boardWidth/2)*boardResolution;
	int catLocationY = boardPosY + (int)(boardHeight/2)*boardResolution;
	int catLastX = 0;
	int catLastY = 0;
	int catLength = 1;
	
	int red;
	int green;
	int blue;
	
	static int foodPosX = 0;
	static int foodPosY = 0;
	
	List<Integer> catAllX = new ArrayList();
	List<Integer> catAllY = new ArrayList();
	
	
	public Snek() throws InterruptedException {
		
		catAllX.add(catLocationX);
		catAllY.add(catLocationY);
		
		JFrame frame = new JFrame();
        frame.add(new TestPane());
        frame.setSize(1500,800);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
		
		//set up board
		
		
		JPanel pa = new JPanel();
		frame.add(pa);
		pa.setVisible(true);
		pa.setLayout(null);
		
		
		
		JLabel cat = new JLabel(Integer.toString(catLength));
		cat.setOpaque(true);
		cat.setBounds(boardPosX + (int)(boardWidth/2)*boardResolution,boardPosY + (int)(boardHeight/2)*boardResolution, boardResolution,boardResolution);
		cat.setBackground(new Color(255, 50, 50));
		cat.setForeground(new Color(255,255,255));
		pa.add(cat);
		cat.setVisible(true);
		
		for(int v = 0; v < (int) boardWidth; v++) {
			for(int h = 0; h < (int) boardHeight; h++) {
				
				JLabel square = new JLabel();
				square.setOpaque(true);
				square.setBounds(boardPosX + v*(boardResolution),boardPosY + h*(boardResolution),boardResolution,boardResolution);
				if(v%2 == h%2) {
					square.setBackground(new Color(100,200,100));
				}else {
					square.setBackground(new Color(90,190,90));
				}
				pa.add(square);
				square.setVisible(true);
				
			}
			
		}
		
		frame.setVisible(true);
		
		//add thing
		
		int foodCount = 0;
		
		
		
		JLabel food = new JLabel();
		pa.add(food);
		
		
		while(catLength < 20 && catLocationX < boardPosX + boardWidth*boardResolution - boardResolution + 1 && catLocationY < boardPosY + boardHeight*boardResolution - boardResolution + 1 && catLocationX > boardPosX - 1 && catLocationY > boardPosY - 1) {
			
			if(foodCount == 0) {
				foodPosX = (int) (Math.random()*(boardWidth))*boardResolution + boardPosX;
				foodPosY = (int)(Math.random()*(boardHeight))*boardResolution + boardPosY;
				red = (int) (Math.random()*200+55);
				green = (int) (Math.random()*200+55);
				blue = (int) (Math.random()*200+55);
				regenerateFood(pa,food,foodPosX,foodPosY,red,green,blue);
				foodCount = 1;
			}
			
			if(touchingFood(catLocationX,catLocationY)==true){
				foodCount = 0;
				catLength += 1;
				
				addSegment(pa,catLength-1);
				
			}
			
			pa.setComponentZOrder(cat,0);
			cat.setText(Integer.toString(catLength));
			
			if((double)(catLocationX-boardPosX)/boardResolution == (int) (catLocationX-boardPosX)/boardResolution && (double)(catLocationY-boardPosY)/boardResolution == (int) (catLocationY-boardPosY)/boardResolution) {
				lastCatDirection = catDirection;
			}
			
			moveInDirection(lastCatDirection, cat);
			
		}
		JFrame f = new JFrame("results...");
		f.setSize(boardWidth*boardResolution,boardHeight*boardResolution);
		f.setLocation(boardPosX,boardPosY);
		f.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { 
	        	System.out.println("lol");
	        }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { 
	        	System.exit(0);
	        }
	        
	    });
		
		f.setVisible(true);
		System.out.println(catLength);
		if(catLength==20) {
			JLabel YAY = new JLabel("YOU WON WOW!!!!!!!!!!!!!");
			YAY.setSize(500,500);
			YAY.setLocation(boardPosX,boardPosY);
			YAY.setOpaque(true);
			YAY.setBackground(new Color(255, 255, 255));
			cat.setHorizontalTextPosition(getWidth()/2);
			f.add(YAY);
			f.setComponentZOrder(YAY,0);
			YAY.setVisible(true);
		}else {
			JLabel YAY = new JLabel("YOU LOST HAHA");
			YAY.setSize(500,500);
			YAY.setLocation(boardPosX,boardPosY);
			YAY.setOpaque(true);
			YAY.setBackground(new Color(255, 255, 255));
			cat.setHorizontalTextPosition(getWidth()/2);
			f.add(YAY);
			f.setComponentZOrder(YAY,0);
			YAY.setVisible(true);
		}
	}
	
	
	public class TestPane extends JPanel {

        public TestPane() {
            InputMap im = getInputMap();
            ActionMap am = getActionMap();

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "Right");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "Left");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "Up");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "Down");

            am.put("Left", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(catLocationY/boardResolution == (int)catLocationY/boardResolution) {
                        Snek.catDirection = "Left";
                	}
                }
            });

            am.put("Right", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Snek.catDirection = "Right";
                }
            });
            
            am.put("Up", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Snek.catDirection = "Up";
                }
            });
            
            am.put("Down", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Snek.catDirection = "Down";
                }
            });

            setFocusable(true);
            requestFocusInWindow();        
        }

    }
	
	public void regenerateFood(JPanel p, JLabel l, int x, int y, int r, int g, int b) {
        l.setSize(boardResolution,boardResolution);
        l.setOpaque(true);
        l.setBackground(new Color(r,g,b));
        l.setLocation(x,y);
        p.setComponentZOrder(l,catLength+2);
        l.setVisible(true);
	}
	
	public boolean touchingFood(int catx, int caty) {
		if(catx == foodPosX && caty == foodPosY) {
			return true;
		}else {
			return false;
		}
	}
	
	public void addSegment(JPanel p,int len) throws InterruptedException {
		JLabel clone = new JLabel();
		clone.setSize(boardResolution,boardResolution);
		clone.setOpaque(true);
		clone.setBackground(new Color(red,green,blue));
		p.add(clone);
		p.setComponentZOrder(clone,len);
		
		clone.setLocation(catLocationX,catLocationY);
		
		clone.setVisible(true);
		
		Thread newClone = new Thread() {
			public void run() {
				try {
					Thread.sleep(boardResolution*(len+1)*(boardWidth/10));
					while(true) {
						clone.setLocation(catAllX.get(catAllX.size()-boardResolution*len-1),catAllY.get(catAllY.size()-boardResolution*len-1));
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		
		
		newClone.start();
		
		
		
		//when i start as a clone... oh yeah mhm
	}
	
	public void moveInDirection(String d, JLabel c) throws InterruptedException {
		if(d == "Left") {
			catLocationX = catLocationX - 1;
		}else if(d == "Right") {
			catLocationX = catLocationX + 1;
		}else if(d == "Up") {
			catLocationY = catLocationY - 1;
		}else if(d == "Down") {
			catLocationY = catLocationY + 1;
		}
		c.setLocation(catLocationX,catLocationY);
		catAllX.add(catLocationX);
		catAllY.add(catLocationY);
		Thread.sleep((int) boardWidth/10);
	}
	


}
