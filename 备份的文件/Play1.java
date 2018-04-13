//cs350
//poject 2
//Zhang Lu

import javax.swing.JFrame;

public class Play1
{
   public static void main( String[] args )
   {
	   JFrame application = new JFrame( "Player1" );
	   Player1 d = new Player1();	  
	   application.add(d);
	   d.runServer();
	   application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   application.setSize( 600,600 ); // set frame size
	   application.setVisible( true ); // display frame	   
	   
   } // end main
} // end class ServerTes