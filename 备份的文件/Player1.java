//cs350
//poject 2
//Zhang Lu


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;



public class Player1 extends JPanel
	implements KeyListener, MouseListener, MouseMotionListener
{
	private int mx;	
	private int my;	
	private int[][] nbs;	
	private ArrayList<CDomino> team;	
	private CDomino Domino2moved;
	private boolean inited;
	private int[][] deck=new int[28][2] ;
	private int k=0;
	
	 private ObjectOutputStream output;
	 private ObjectInputStream input; 
	 private ServerSocket server; 
	 private Socket connection; 
	
	public Player1()
  {
		
		Domino2moved=null;
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.addKeyListener(this);
      this.setFocusable(true);
      inited = false;
      
      
      //put all the dominos in deck
      int k=0;
      for (int i=0;i<7;i++){
      	for (int j=0;j<i+1;j++ ){   
      		deck[k][0]=j;
      		deck[k][1]=i;
      		k++;        		
      	}
      }
   //   init();

  }
	
	public void redraw(int[][] nbss){
		nbs=nbss;
		Domino2moved=null;
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.addKeyListener(this);
      this.setFocusable(true);
      inited = false;
      
      
      //put all the dominos in deck
      int k=0;
      for (int i=0;i<7;i++){
      	for (int j=0;j<i+1;j++ ){   
      		deck[k][0]=j;
      		deck[k][1]=i;
      		k++;        		
      	}
      }
	}
	
	

	public int[][] getnbs(){
		
			return nbs;
		
		
	}
	public void aa()  {
		if (nbs!=null){

			for (int i =0;i<14;i++){
				System.out.println(nbs[i][0]*100+nbs[i][1]);				
			}
		}
	}
	
	public void init() {  
		int gx=getSize().width;
		int gy=getSize().height;
		int dy=gy/6;
		k=1;
		if (nbs==null) {
		
		//random  dominos
				
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
		
				
		
		//Allocation domino from deck
		team = new ArrayList<CDomino>();		
		int t=0;
		int t1=0;
		int t2=0;
		for (int i= 0; i <7;i++){
			//player1
			t1=nbs[t][0];
	        t2=nbs[t][1];
			team.add(new CDomino(t1,t2,(int)(dy+i*(gx-dy*2-dy/3)/6),(int)(dy/6),(int)(dy/3),(int)(dy/3*2)));
			//player2			
			t1=nbs[t+1][0];
		    t2=nbs[t+1][1];			
			team.add(new CDomino(t1,t2,(int)(dy+i*(gx-dy*2-dy/3)/6),(int)(gy/6*5+dy/6),(int)(dy/3),(int)(dy/3*2)));
			t=t+2;
		}
		inited = true;
	}

	//paint  graph
	public void paintComponent( Graphics g ){
		
		
		if (!inited) 	init();
		
		// State Presentation, using double buffers
		// create the back buffer
		Image backBuffer=createImage(getSize().width, getSize().height);
		Graphics gBackBuffer=backBuffer.getGraphics();
		// clear the back buffer
		gBackBuffer.setColor(Color.white);
		gBackBuffer.clearRect(0, 0, getSize().width, getSize().height);
		// draw the pieces to back buffer
		
		for (int i=0; i<team.size(); i++) {
			team.get(i).draw(gBackBuffer);
		}
		
		// copy from back buffer to front
		g.drawImage(backBuffer, 0, 0, null);
		
		//draw line
		g.setColor(Color.blue);
		g.drawLine(0,(int)(getSize().height/6),getSize().width, (int)(getSize().height/6));
		g.drawLine(0,(int)(getSize().height*5/6),getSize().width, (int)(getSize().height*5/6));
	}

	
	
	public void mouseMoved(MouseEvent arg0) {
		
	}


	public void mouseClicked(MouseEvent e) {
		if (e.isMetaDown() ){
		//	System.out.println("1111")	;	
			
			for (int i =0; i<14;i++){
				CDomino p=team.get(i);
			//	System.out.println(i);
				if (p.isInside(e.getX(), e.getY())){
	//				System.out.println("test2");	
					p.rotate(p.getX(),p.getY());
					team.remove(i);
					team.add(p);
					repaint();
					break;
				}
			}
		}
	}


	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {

		
	}

	public void mousePressed(MouseEvent e) {
		if (e.isMetaDown()) return;
		
		for (int i=team.size()-1; i>=0; i--) {
			CDomino p=team.get(i);
			if (p.isInside(e.getX(), e.getY())) {			
				team.remove(i);
				team.add(p);	// move to the end, i.e. the top
				Domino2moved=p;
				mx=e.getX();
				my=e.getY();
				repaint();
				break;
			}
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		Domino2moved=null;
		
	}


	public void keyPressed(KeyEvent e) {		
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_R) {
			inited=false;
			nbs=null;
			repaint();	
			aa();
		}
		else if (e.getKeyCode()==KeyEvent.VK_S) {
		//	this.runServer();
			this.aa();
		}
	}

	public void keyTyped(KeyEvent e) {
	}


	public void mouseDragged(MouseEvent e) {
	    if (e.isMetaDown()) return;	// ignore right button
		if (Domino2moved!= null) {
			Domino2moved.translate(e.getX()-mx, e.getY()-my);
			mx=e.getX();
			my=e.getY();
			repaint();
			}
	
	}

   
	public void runServer()
	{
      try // set up server to receive connections; process connections
      {
         server = new ServerSocket( 12345, 100 ); // create ServerSocket
         
         while ( true ) 
         {
            try 
            {
               waitForConnection(); // wait for a connection
               getStreams(); // get input & output streams
               processConnection(); // process connection
            } // end try
            catch ( EOFException eofException ) 
            {
            	System.out.println( "1-------------Server terminated connection" );
            } // end catch
            finally 
            {
               closeConnection(); //  close connection
              
            } // end finally
         } // end while
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method runServer

   // wait for connection to arrive, then display connection info
   private void waitForConnection() throws IOException
   {
      System.out.println( "1--------------Waiting for connection\n" );
      connection = server.accept(); // allow server to accept connection            
      System.out.println( "1-----------Connection " );
      
      
   } // end method waitForConnection

   // get streams to send and receive data
   private void getStreams() throws IOException
   {
      // set up output stream for objects
      output = new ObjectOutputStream( connection.getOutputStream() );     
      output.writeObject(nbs);     
      output.flush();
      // set up input stream for objects
      input = new ObjectInputStream( connection.getInputStream() );
      
      System.out.println( "1-------------Got I/O streams\n" );
   } // end method getStreams

   // process connection with client
   private void processConnection() throws IOException
   {
      
    //  sendData( nbs ); // send connection successful message

      // enable enterField so server user can send messages
      


         try // read message and display it
         {
            nbs = ( int[][] ) input.readObject(); // read new message
          //  displayMessage( "\n" + message ); // display message
         } // end try
         catch ( ClassNotFoundException classNotFoundException ) 
         {
         //   displayMessage( "\nUnknown object type received" );
         } // end catch


   } // end method processConnection

   // close streams and socket
   private void closeConnection() 
   {
	  System.out.println( "1-------------Terminating connection\n" );


      try 
      {
         output.close(); // close output stream
         input.close(); // close input stream
         connection.close(); // close socket
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method closeConnection

   // send message to client
   private void sendData( Table g )
   {
      try // send object to client
      {
         output.writeObject( g);
         output.flush(); // flush output to client
         System.out.println( "1-------------SERVER>>> g"  );
      } // end try
      catch ( IOException ioException ) 
      {
    	  System.out.println( "1------------Error writing object" );
      } // end catch
   } // end method sendData

   // manipulates displayArea in the event-dispatch thread
   
   
 
} // end class Server


	      
	      
      
	  
