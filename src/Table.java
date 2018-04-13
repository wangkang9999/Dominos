/*--------------------------------
cs350                         
Poject 6                      
Zhang Lu                      
--------------------------------*/
  
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;


public class Table extends JPanel
//	implements KeyListener, MouseListener, MouseMotionListener
{
	private int mx;	
	private int my;

	private int[][] nbs;
	
	
	private ArrayList<CDomino> team;	
	private CDomino Domino2moved;

	private boolean inited;
	private int[][] deck=new int[28][2] ;

	public int getmx() { return mx;}
	public int getmy() { return my;}
	
	public Table()
    {
		
		Domino2moved=null;
/*		this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);*/
        
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
	
	public void redraw(int[][] m){
		nbs=m;
		Domino2moved=null;
/*        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);*/
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
        this.repaint();
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

	
	
	public void rota(int x,int y){
		for (int i =0; i<14;i++){
			CDomino p=team.get(i);
		//	System.out.println(i);
			if (p.isInside(x, y)){
//				System.out.println("test2");	
				p.rotate(p.getX(),p.getY());
				team.remove(i);
				team.add(p);
				repaint();
				break;
			}
		}
	}
	
	public void mp (int x,int y){
		for (int i=team.size()-1; i>=0; i--) {
			CDomino p=team.get(i);
			if (p.isInside(x, y)) {			
				team.remove(i);
				team.add(p);	// move to the end, i.e. the top
				Domino2moved=p;
				mx=x;
				my=y;
				repaint();
				break;
			}
		}
	}
	
	public void mr(){
		Domino2moved=null;
	}
	
	
	public int[][] restart (){
		inited=false;
		nbs=null;
		repaint();	
		return nbs;
	}
	
	public void md(int x,int y){
		if (Domino2moved!= null) {
			Domino2moved.translate(x-mx, y-my);
			mx=x;
			my=y;
			repaint();
			}
	}
	/*
	public void mouseMoved(MouseEvent arg0) {
		
	}


	public void mouseClicked(MouseEvent e) {
		if (e.isMetaDown() ){
			rota(e.getX(),e.getY());
		}
	}


	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {

		
	}

	public void mousePressed(MouseEvent e) {
		if (e.isMetaDown()) return;
		mp(e.getX(),e.getY());
		
	}
	
	public void mouseReleased(MouseEvent e) {
		mr();
		
	}


	public void keyPressed(KeyEvent e) {		
	}

	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_R) {
			restart();	
	
		}
	}

	public void keyTyped(KeyEvent e) {
	}


	public void mouseDragged(MouseEvent e) {
	    if (e.isMetaDown()) return;	// ignore right button
		md(e.getX(),e.getY());
		}
	
	*/
	

}
