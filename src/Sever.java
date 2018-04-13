/*--------------------------------
cs350                         
Poject 6                      
Zhang Lu                      
--------------------------------*/

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import java.awt.event.*;
import java.util.ArrayList;


public class Sever extends JFrame
	implements KeyListener, MouseListener, MouseMotionListener
	{
	private ObjectOutputStream output; // output stream to client
	private ObjectInputStream input; // input stream from client
	private ServerSocket server; // server socket
	private Socket connection; // connection to client
	private int[][] nbs;
	private Table game;
	

	private int[][]deck=new int[28][2];
	
	
	public Sever(){
		super("PLayer1------Server");
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
		
		JFrame P1 =new JFrame();
	    game=new Table();
		add(game);
		setSize( 600,600 );
		setVisible( true );	
		
		int k=0;
        for (int i=0;i<7;i++){
        	for (int j=0;j<i+1;j++ ){   
        		deck[k][0]=j;
        		deck[k][1]=i;
        		k++;        		
        	}
        }
	
	}
	public void runServer()
	   {
	      try // set up server to receive connections; process connections
	      {
	         server = new ServerSocket( 12345, 100 ); // create ServerSocket
	         
	         
	            try 
	            {
	               waitForConnection(); 
	               getStreams(); 
	               processConnection();
	  	      	
	            } 
	            catch ( EOFException eofException ) 
	            {	              
	            }
	         
	      } 
	      catch ( IOException ioException ) 
	      {
	         ioException.printStackTrace();
	      } 
	   } 
	 private void waitForConnection() throws IOException
	   {
	//      System.out.println("1----------------Waiting for connection\n" );
	      connection = server.accept(); // allow server to accept connection            
	 //     System.out.println("1----------------Connection ");
	
	   } // end method waitForConnection
	 private void getStreams() throws IOException
	   {
	      // set up output stream for objects
	      output = new ObjectOutputStream( connection.getOutputStream() );
	      nbs=game.getnbs();
	      output.writeObject(nbs);
	      output.flush(); // flush output buffer to send header information

	      // set up input stream for objects
	      input = new ObjectInputStream( connection.getInputStream() );

	  //    System.out.println("1----------------Got I/O streams\n" );
	   } // end method getStreams

	   // process connection with client
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
	        		 game.rota(e.getX(), 600-e.getY());
	        		 game.rota(e.getX(), 600-e.getY());
	        		 game.rota(e.getX(), 600-e.getY());
	//        		 System.out.println( e.getX()*10000+e.getY());
	        		 
	        	 }
	        	 else if (msg[0].equals("md")){
	        		 MouseEvent e=(MouseEvent) msg[1];
	        		 game.md(e.getX(),600- e.getY());
	 //       		 System.out.print( msg[0]+"      ");
	 //       		 System.out.println( e.getX()*10000+e.getY());
	        	 }
	        	 else if (msg[0].equals("mp")){
	        		 MouseEvent e=(MouseEvent) msg[1];
	        		 game.mp(e.getX(), 600-e.getY());
	//        		 System.out.print( msg[0]+"      ");
	//        		 System.out.println( e.getX()*10000+e.getY());
	        	 }
	        	 else if (msg[0].equals("mr")){
	        		 
	        		 game.mr();
	 //       		 System.out.print( msg[0]+"      ");
	     
	        	 }
	        	 
	         } // end try
	         catch ( ClassNotFoundException classNotFoundException ) 
	         {
	        	 System.out.println( "1------------Unknown object type received" );
	         } // end catch
		   	}

	   } // end method processConnection

	   // close streams and socket
	   
	   private void sendData( Object[]  m )
	   {
	      try // send object to client
	      {
	    	 
	         output.writeObject( m);
	         output.flush(); // flush output to client
	         
	      } // end try
	      catch ( IOException ioException ) 
	      {
	    	  System.out.println( "1------------Error writing object" );
	      } // end catch
	   } // end method sendData

	   // manipulates displayArea in the event-dispatch thread
	   
	   public void rad()
		  {
		   ArrayList<int[]> deck2 = new ArrayList<int[]>();		
			for (int i = 0;i <28;i++){
				deck2.add(deck[i]);
			}
			nbs=new int[28][2];
			int[] ddd=new int[14];
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
	
	public void mouseDragged(MouseEvent e) {
		if (e.isMetaDown()) return;	
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
	//		System.out.println( e.getX()*10000+e.getY());
			
		}
		
	}
	
	public void mouseEntered(MouseEvent e) {

		
	}
	
	public void mouseExited(MouseEvent e) {

		
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



