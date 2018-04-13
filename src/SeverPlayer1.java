/*--------------------------------
cs350                         
Poject 6                      
Zhang Lu                      
--------------------------------*/


import javax.swing.JFrame;

public class SeverPlayer1
{
   public static void main( String[] args )
   {
	   Sever application = new Sever(); // create server
      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      application.runServer(); // run server application
   } // end main
} // end class ServerTest
