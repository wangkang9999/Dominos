/*--------------------------------
cs350                         
Poject 6                      
Zhang Lu                      
--------------------------------*/


import javax.swing.JFrame;

public class ClientPlayer2
{
   public static void main( String[] args )
   {
	  
	   Client application = new Client(); // create server
      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      application.runClient(); // run server application
   } // end main
} // end class ServerTest
