/*--------------------------------
cs350                         
Poject 6                      
Zhang Lu                      
--------------------------------*/


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import javax.swing.SwingUtilities;




public class Client extends JFrame
	implements KeyListener, MouseListener, MouseMotionListener
	{
	private ObjectOutputStream output; // output stream to server
    private ObjectInputStream input; // input stream from server
    private String chatServer; // host server for this application
    private Socket client; // socket to communicate with server
    private Table game;
    private int[][] nbs;
    private int[][]deck=new int[28][2];
    
    
    public Client(){
    	super("PLayer2------Client");
    	game=new Table();
    	add(game);
    	setSize( 600,600 );
		setVisible( true );
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        int k=0;
        for (int i=0;i<7;i++){
        	for (int j=0;j<i+1;j++ ){   
        		deck[k][0]=j;
        		deck[k][1]=i;
        		k++;        		
        	}	
        } 	
    }
 
	public void runClient() 
	   {
	      try 
	      {
	         connectToServer(); 
	         getStreams(); 
	         processConnection();
	      } 
	      
	      catch ( IOException ioException ) 
	      {
	         ioException.printStackTrace();
	      } 	     	      
	   }

		public void rad()
		{
			ArrayList<int[]> deck2 = new ArrayList<int[]>();		
			for (int i = 0;i <28;i++){
				deck2.add(deck[i]);
			}
			nbs=new int[28][2];
	
			for (int i = 0; i <14;i++){
				int ran=(int)(Math.random()*(28-i));			
				nbs[i]=deck2.get(ran);
				deck2.remove(ran);
			}
		}
		
		public int[][] flip(int[][] m){
			int[][] result =new int[14][2];
			for (int i =0;i<7;i++){
				result[2*i][0]=m[i*2+1][1];
				result[2*i][1]=m[i*2+1][0];
				result[2*i+1][0]=m[i*2][1];
				result[2*i+1][1]=m[i*2][0];
			}
			
			return result;
		}

	   private void connectToServer() throws IOException
	   {      
	      client = new Socket( InetAddress.getByName( "127.0.0.1" ), 12345 );

	   } 
  
	   private void getStreams() throws IOException
	   {
	      
	      output = new ObjectOutputStream( client.getOutputStream() );    
	      output.flush();
	      input = new ObjectInputStream( client.getInputStream() );
	      try 
	      {
	         nbs  = ( int[][] ) input.readObject();
	         int[][] fn=flip(nbs);
	    	 game.redraw(fn);
	      
	      } 
	      catch ( ClassNotFoundException classNotFoundException ) 
	      {
	     
	      } 
	   }
	   // process connection with server
	   private void processConnection() throws IOException
	   {
		   	while (true){

	         try 
	         {
	        	 Object[] msg=new Object[2];
	        	 msg = (Object[]) input.readObject();	 
	        	 
	        	 if (msg[0].equals("s")) {
	                 nbs=(int[][])msg[1];
	                 int[][] fn=flip(nbs);
	        		 game.redraw(fn);
	   

	        	 }
	        	 else if(msg[0].equals("r")){
	        		 MouseEvent e=(MouseEvent) msg[1];
	        		 game.rota(e.getX(),600- e.getY());
	        		 game.rota(e.getX(),600- e.getY());
	        		 game.rota(e.getX(),600- e.getY());
	    //    		 System.out.println( e.getX()*10000+e.getY());
	        		 
	        	 }
	        	 else if (msg[0].equals("md")){
	        		 MouseEvent e=(MouseEvent) msg[1];
	        		 game.md(e.getX(), 600-e.getY());
	        //		 System.out.print( msg[0]+"      ");
	        //		 System.out.println( e.getX()*10000+e.getY());
	        	 }
	        	 else if (msg[0].equals("mp")){
	        		 MouseEvent e=(MouseEvent) msg[1];
	        		 game.mp(e.getX(), 600-e.getY());
	       // 		 System.out.print( msg[0]+"      ");
	       // 		 System.out.println( e.getX()*10000+e.getY());
	        	 }
	        	 else if (msg[0].equals("mr")){
	        		 
	        		 game.mr();
	        //		 System.out.print( msg[0]+"      ");
	     
	        	 }
	        	 
	         } // end try
	         catch ( ClassNotFoundException classNotFoundException ) 
	         {
	        	 System.out.println( "2------------Unknown object type received" );
	         } // end catch
		   	}

	   } // end method processConnection

	   // close streams and socket
	   
	
	   private void sendData( Object m )
	   {
	      try 
	      {
	         output.writeObject(m);
	         output.flush(); 
	  //       System.out.println( "2------------CLIENT>>> g" );
	      } 
	      catch ( IOException ioException )
	      {
	    	  System.out.println( "2------------Error writing object" );
	      } 
	   } 


	  public void mouseDragged(MouseEvent e) {
			if (e.isMetaDown()) return;	// ignore right button
			game.md(e.getX(),e.getY());
			Object[] msg=new Object[2];
			msg[0]="md";
			msg[1]=e;
			sendData(msg);
			
			
			
		}
		
		public void mouseMoved(MouseEvent e) {
			
			
		}

		public void mouseClicked(MouseEvent e) {
			if (e.isMetaDown() ){
				game.rota(e.getX(),e.getY());
				Object[] msg=new Object[2];
				msg[0]="r";
				msg[1]=e;
				sendData(msg);
				
			}
			
		}
		
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void mousePressed(MouseEvent e) {
			if (e.isMetaDown()) return;
			game.mp(e.getX(),e.getY());
			Object[] msg=new Object[2];
			msg[0]="mp";
			msg[1]=e;
			sendData(msg);
			
		}
		
		public void mouseReleased(MouseEvent e) {
			game.mr();
			Object[] msg=new Object[2];
			msg[0]="mr";
			sendData(msg);
			
		}

		public void keyPressed(KeyEvent e) {

			
		}
	
		public void keyReleased( KeyEvent e) {		
			if (e.getKeyCode()==KeyEvent.VK_R) {
				rad();			
				Object[] msg=new Object[2];
				msg[0]="s";
				msg[1]=nbs;
				sendData(msg);
				game.redraw(nbs);
		//		System.out.println( nbs[0][0]);

			
			  } 
		}
			
		
	
		public void keyTyped(KeyEvent e) {
			
		}
	
		
	}
