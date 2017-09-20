//break breaker game 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Brick_Breaker extends JPanel implements Runnable,KeyListener,ActionListener{
	
	static int frameWidth=480;
	static int frameHeight=600;
    public static int Score=0;
    public static int Remaining=200;
    


    //declare position of x axis and y axis of the ball
	int ballx=217;
	int bally=378;

    //declare the position of the x axis and y axis of the bat
	int batx=190;
	int baty=392;
	

    //for making bricks
	int brickx=28;
	int bricky=30;

//bat moving demonstration
	 boolean left=false;
	 boolean right=false;
	 boolean gameOvermsg=false;


    //declare rectangle for the ball and also bat
	Rectangle ball=new Rectangle(ballx, bally, 10, 10);
    Rectangle bat=new Rectangle(batx, baty, 60, 7);
    Rectangle bricks[]=new Rectangle[20];
    
    
	public Brick_Breaker() {
		//constructor
	}
	// main method starting here
public static void main(String[] args){
		JFrame gameFrame=new JFrame("Brick Breaker");
		JButton gameButton=new JButton("Restart");
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(gameButton,BorderLayout.SOUTH);
	    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.setSize(frameWidth,frameHeight);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setResizable(true);
		gameFrame.setFocusable(true);
		Brick_Breaker mygame=new Brick_Breaker();
		gameButton.addActionListener(mygame);
		mygame.setFocusable(true);
		gameFrame.add(mygame);
		mygame.addKeyListener(mygame);
		Thread thread=new Thread(mygame);
		thread.start();
		}
	 
public void paint(Graphics g){
	    g.setColor(Color.white);
		g.fillRect(0, 0,480 ,400);
		g.setColor(Color.RED);
		g.fillOval(ball.x, ball.y, ball.width, ball.height);
		g.setColor(Color.BLUE);
		g.fill3DRect(bat.x, bat.y, bat.width, bat.height, true);
		g.setColor(Color.lightGray);
		g.fillRect(0, 400, 480, 150);
		String remainingScore=Integer.toString(Remaining);
		g.setColor(Color.blue);
		g.setFont(new Font("Arial",Font.PLAIN, 20));
		g.drawString("RemainingScore :: "+remainingScore, 8, 440);
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Target :: 200 ",340 , 430);
		
		g.setColor(Color.blue);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
	    String a=Integer.toString(Score);
        g.drawString("Score :: "+a, 8, 420);
		for(int i=0;i<bricks.length;i++){
			if(bricks[i]!=null){
			g.setColor(Color.blue);
	    	g.fill3DRect(bricks[i].x,bricks[i].y,bricks[i].width,bricks[i].height,true);
			}
	    }
		
		if(ballfallsdown==true || brickisOver==true){
			Font font=new Font("Arial",Font.PLAIN, 18);
			g.setFont(font);
			g.drawString(status, 100, 200);
			ballfallsdown=false;
			brickisOver=false;
		}
		
}
	  
    boolean brickisOver=false;
	boolean ballfallsdown=false;
	int movex=1;
	int movey=-1;
	int count=0;
	String status;
	
public void run() {
		//making brick
		for(int i=0;i<bricks.length;i++){
	    	bricks[i]=new Rectangle(brickx, bricky, 50, 12);
	    	//making brick position
	    	if(i==7){
	    		brickx=28;
	    		bricky=44;
	    	}
	    	if(i==13){
	    		brickx=81;
	    		bricky=58;
	    	}
	    	if(i==17){
	    		brickx=134;
	    		bricky=72;
	    	}
    	brickx+=53;
	    }
		
		while(true){
	//move y axis when ball intersect to bat
		 if(ball.intersects(bat)){
				movey=-movey;
			}
	//set collision of x intersect
			if(ball.x>=473 || ball.x<=0){
				movex=-movex;
			}
	//set collision of y intersect
			if(ball.y<=0){
				movey=-movey;
			}
			if(ball.y>=400){
				ballfallsdown=true;
				status="::You Failed :( :("+"   ::score ::"+Score;
				repaint();
			}
		    repaint();
			ball.x+=movex;
			ball.y+=movey;
			
		 requestFocus(true);
            if(left==true){
            	bat.x -= 4;
              right=false;
            	
            }
            if(right==true){
            	bat.x += 4;
            	left=false;
            }
            
            //set bat movement limitation
            if(bat.x<=1){
            	bat.x=1;
            }
            else if(bat.x>=418){
            	bat.x=418;
            }
			
           
			//bricks and ball colliosion
            for(int i=0;i<bricks.length;i++){
            	if(bricks[i]!=null){
            		if(bricks[i].intersects(ball)){
            			movey=-movey;
            			movex=-movex;
            			bricks[i]=null;
            			count++;Score+=10;
            			Remaining-=10;
            			}
            		
            		
            	}
            }
            if(count==bricks.length){
            	brickisOver=true;
            	status=":: Target Successful :):)"+"  :: score ::"+Score;
            	repaint(); 
            	
            }
            
		
        try {
				Thread.sleep(8);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR 404");
			}
			
		}	
		
	}
	
	
public void keyTyped(KeyEvent e) {
		
		}

public void keyPressed(KeyEvent e) {
		int pressKey=e.getKeyCode();
		
		if(pressKey==KeyEvent.VK_RIGHT){
			right=true;
		}
		if(pressKey==KeyEvent.VK_LEFT){
			left=true;
		}
		
	}

public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String string=e.getActionCommand();
		if(string.equals("Restart")){
			System.out.println("restart");
			this.restart();
		}
	}
	
 public void keyReleased(KeyEvent e) {
        int pressKey=e.getKeyCode();
		
		if(pressKey==KeyEvent.VK_RIGHT){
			right=false;
		}
		if(pressKey==KeyEvent.VK_LEFT){
			left=false;
		}
		
	}

    //restart method
      public void restart(){
	   requestFocus(true);
	 //declare position of x axis and y axis of the ball
		 ballx=217;
		 bally=378;
		//declare the position of the x axis and y axis of the bat
		 batx=190;
		 baty=392;
		
		//for making bricks
		 brickx=28;
		 bricky=30;
		
        //declare rectangle for the ball and also bat
		 ball=new Rectangle(ballx, bally, 10, 10);
	     bat=new Rectangle(batx, baty, 60, 5);
	     bricks=new Rectangle[20];
	    
	  //bat moving demnostration
	     movex=-1;
		 movey=1;
		 count=0;
		 ballfallsdown=false;
		 brickisOver=false;
			
		 status=null;
	    
	    for(int i=0;i<bricks.length;i++){
	    	bricks[i]=new Rectangle(brickx, bricky, 50, 12);
	    	//constrate our brick position
	    	if(i==7){
	    		brickx=28;
	    		bricky=44;
	    	}
	    	if(i==13){
	    		brickx=81;
	    		bricky=58;
	    	}
	    	if(i==17){
	    		brickx=134;
	    		bricky=72;
	    	}
    	brickx+=53;
	    }
	    Score=0;
	    repaint();
		
     }
}

