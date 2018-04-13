// Fig. 27.7: Client.java
// Client portion of a stream-socket connection between client and server.
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Player2 extends JFrame 
{
   private JTextField enterField; // enters information from user
   private JTextArea displayArea; // display information to user
   private ObjectOutputStream output; // output stream to server
   private ObjectInputStream input; // input stream from server
  // private Object[] message ; // message from server
   private String chatServer; // host server for this application
   private Socket client; // socket to communicate with server

   // initialize chatServer and set up GUI
   public Player2( String host )
   {
      super( "Client" );

      chatServer = host; // set server to which this client connects

      enterField = new JTextField(); // create enterField
      enterField.setEditable( false );
      enterField.addActionListener(
         new ActionListener() 
         {
            // send message to server
            public void actionPerformed( ActionEvent event )
            {
               
               enterField.setText( "" );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      add( enterField, BorderLayout.NORTH );

      displayArea = new JTextArea(); // create displayArea
      add( new JScrollPane( displayArea ), BorderLayout.CENTER );

      setSize( 300, 150 ); // set size of window
      setVisible( true ); // show window
   } // end Client constructor

   // connect to server and process messages from server
   public void runClient() 
   {
      try // connect to server, get streams, process connection
      {
         connectToServer(); // create a Socket to make connection
         getStreams(); // get the input and output streams
         processConnection(); // process connection
      } // end try
      catch ( EOFException eofException ) 
      {
         displayMessage( "\nClient terminated connection" );
      } // end catch
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
      finally 
      {
         closeConnection(); // close connection
      } // end finally
   } // end method runClient

   // connect to server
   private void connectToServer() throws IOException
   {      
      displayMessage( "Attempting connection\n" );

      // create Socket to make connection to server
      client = new Socket( InetAddress.getByName( chatServer ), 12345 );

      // display connection information
      displayMessage( "Connected to: " + 
         client.getInetAddress().getHostName() );
   } // end method connectToServer

   // get streams to send and receive data
   private void getStreams() throws IOException
   {
      
      output = new ObjectOutputStream( client.getOutputStream() );      
      output.flush();
      
      
      input = new ObjectInputStream( client.getInputStream() );
      try 
      {
         int [][] kk  = ( int[][] ) input.readObject();
         String k2=String.valueOf(kk[0][0]);

    	 displayMessage( "\n"+k2+"\n" );
        
        
      } 
      catch ( ClassNotFoundException classNotFoundException ) 
      {
     
      } 
      displayMessage( "\nGot I/O streams\n" );
   } 

   // process connection with server
   private void processConnection() throws IOException
   {
      // enable enterField so client user can send messages
      setTextFieldEditable( true );

      do // process messages sent from server
      { 
         try // read message and display it
         {
        	 Object[] message=new Object[2];
        	 message = ( Object[] ) input.readObject(); 
        	 if (message[0].equals("s")){
        	 Object a= message[1];
        	 int[][] b=(int[][]) a;
        	 int c= b[0][0];
        	 String d=String.valueOf(c);
        	 displayMessage( "\n" + message[0]+"      "+d); 
        	 }
        	 else if (message[0].equals("mr")){
        		displayMessage( "\n" + message[0]);
        	 }
        	 else {
        		 MouseEvent e=(MouseEvent) message[1];
        		 int a=e.getX();
        		 int b=e.getY();
        		 String a1=String.valueOf(a);
        		 String b1=String.valueOf(b);
        		 displayMessage( "\n" + message[0]+"      "+a+"           "+b);
        	 }
            
            
         } // end try
         catch ( ClassNotFoundException classNotFoundException ) 
         {
            displayMessage( "\nUnknown object type received" );
         } // end catch

      } while ( true  );
   } // end method processConnection

   // close streams and socket
   private void closeConnection() 
   {
      displayMessage( "\nClosing connection" );
      setTextFieldEditable( false ); // disable enterField

      try 
      {
         output.close(); // close output stream
         input.close(); // close input stream
         client.close(); // close socket
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method closeConnection

   // send message to server
   
   private void displayMessage( final String messageToDisplay )
   {
      SwingUtilities.invokeLater(
         new Runnable()
         {
            public void run() // updates displayArea
            {
               displayArea.append( messageToDisplay );
            } // end method run
         }  // end anonymous inner class
      ); // end call to SwingUtilities.invokeLater
   } // end method displayMessage

   // manipulates enterField in the event-dispatch thread
   private void setTextFieldEditable( final boolean editable )
   {
      SwingUtilities.invokeLater(
         new Runnable() 
         {
            public void run() // sets enterField's editability
            {
               enterField.setEditable( editable );
            } // end method run
         } // end anonymous inner class
      ); // end call to SwingUtilities.invokeLater
   } // end method setTextFieldEditable
} // end class Client

/**************************************************************************
 * (C) Copyright 1992-2010 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
