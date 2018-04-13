/*--------------------------------
cs350                         
Poject 6                      
Zhang Lu                      
--------------------------------*/

import java.awt.Color;
import java.awt.Image;

import javax.swing.JApplet;
import javax.swing.JPanel;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;




public class CDomino {

	private int Nb1;    //number 1
	private int Nb2;    //number 2
	private int[] Xs;   
	private int[] Ys;
	private int Width;
	private int Height;
	private boolean isshow;
	
	
	public boolean isshow(){
		if (Ys[0]>100){
			return true;		
		}
		else{
			return false;
		}
		
		
	}
			
	public CDomino(int n1,int n2 , int x1,int y1,int w, int h  ) {
		Nb1= n1; 
		Nb2= n2;
		Xs=new int[20];
		Ys=new int[20];
		Width=w;
		Height=h;
		//rect
		Xs[0]=x1;
		Ys[0]=y1;
		Xs[1]=x1+Width;
		Ys[1]=y1;
		Xs[2]=x1+Width;
		Ys[2]=y1+Height;
		Xs[3]=x1;
		Ys[3]=y1+Height;
		
		//line
		Xs[4]=x1;
		Ys[4]=y1+Width;
		Xs[5]=x1+Width;
		Ys[5]=y1+Width;
		
		//number1
		int r=(int)(Width/10);
		
		Xs[6]=x1+r+r;Ys[6]=y1+r+r;
		Xs[7]=x1+r+r;Ys[7]=y1+4*r+r;
		Xs[8]=x1+r+r;Ys[8]=y1+7*r+r;
		Xs[9]=x1+7*r+r;Ys[9]=y1+r+r;
		Xs[10]=x1+7*r+r;Ys[10]=y1+4*r+r;
		Xs[11]=x1+7*r+r;Ys[11]=y1+7*r+r;
		Xs[12]=x1+4*r+r;Ys[12]=y1+4*r+r;
		
	    //number2
		Xs[13]=x1+r+r;Ys[13]=Width+y1+r+r;
		Xs[14]=x1+r+r;Ys[14]=Width+y1+4*r+r;
		Xs[15]=x1+r+r;Ys[15]=Width+y1+7*r+r;
		Xs[16]=x1+7*r+r;Ys[16]=Width+y1+r+r;
		Xs[17]=x1+7*r+r;Ys[17]=Width+y1+4*r+r;
		Xs[18]=x1+7*r+r;Ys[18]=Width+y1+7*r+r;
		Xs[19]=x1+4*r+r;Ys[19]=Width+y1+4*r+r;

	}
	
	
	public CDomino( CDomino dmn){
		Nb1= dmn.Nb1; 
		Nb2= dmn.Nb2;
		Xs=dmn.Xs;
		Ys=dmn.Ys;
		Width=dmn.Width;
		Height=dmn.Height;

	}
	//find   pivot poin
	public int getX() { return (int)(Xs[0]+Xs[2])/2; }
//	public void setX(int x) { X=x; }
	public int getY() { return (int)(Ys[0]+Ys[2])/2; }
//	public void setY(int y) { Y=y; }
	
	
	
	public boolean isInside(int x, int y) {		
			return (x-5>=Xs[0] && y-25>=Ys[0] && x-5<Xs[2] && y-25<Ys[2]);		
	}

	
	
	public void draw(Graphics g){
		
		
		//draw rect
		Polygon p =new Polygon();
		for (int i=0;i<4;i++){
			p.addPoint((int)Xs[i], (int)0.5+Ys[i]);
		}
		g.setColor(Color.lightGray);
		g.fillPolygon(p);
		g.setColor(Color.blue);
		g.drawPolygon(p);	
		
		if (isshow()){
			//draw line
			g.drawLine((int)Xs[4], (int)Ys[4], (int)Xs[5],(int)Ys[5]);
		
		
		
			//draw point of number
			int r=(int)(Width/10);
			g.setColor(Color.red);
		
//   	   point number
//		©°©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©´
//		|  1         4  |
//		|               |
//		|               |
//		|  2    7    5  |
//		|               |
//		|               |
//		|  3         6  |
//      ©À©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©È
//		|  1         4  |
//		|               |
//		|               |
//		|  2    7    5  |
//		|               |
//		|               |
//		|  3         6  |
//      ©¸©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¼   
		
		
		//player1
			if (Nb1==2 ||Nb1==3 ||Nb1==4||Nb1==5||Nb1==6 ) g.fillOval(Xs[6]-r,Ys[6]-r,(int)(5*r/2),(int)(5*r/2));            //1
			if (Nb1==6                                   ) g.fillOval(Xs[7]-r,Ys[7]-r,(int)(5*r/2),(int)(5*r/2));            //2
			if (Nb1==4 ||Nb1==5 ||Nb1==6                 ) g.fillOval(Xs[8]-r,Ys[8]-r,(int)(5*r/2),(int)(5*r/2));            //3
			if (Nb1==4 ||Nb1==5 ||Nb1==6                 ) g.fillOval(Xs[9]-r,Ys[9]-r,(int)(5*r/2),(int)(5*r/2));            //4
			if (Nb1==6                                   ) g.fillOval(Xs[10]-r,Ys[10]-r,(int)(5*r/2),(int)(5*r/2));          //5
			if (Nb1==2 ||Nb1==3 ||Nb1==4||Nb1==5||Nb1==6 ) g.fillOval(Xs[11]-r,Ys[11]-r,(int)(5*r/2),(int)(5*r/2));          //6	
			if (Nb1==1 ||Nb1==3 ||Nb1==5                 ) g.fillOval(Xs[12]-r,Ys[12]-r,(int)(5*r/2),(int)(5*r/2));          //7
		
		//player2
		
			if (Nb2==2 ||Nb2==3 ||Nb2==4||Nb2==5||Nb2==6 ) g.fillOval(Xs[13]-r,Ys[13]-r,(int)(5*r/2),(int)(5*r/2));          //1
			if (Nb2==6                                   ) g.fillOval(Xs[14]-r,Ys[14]-r,(int)(5*r/2),(int)(5*r/2));          //2
			if (Nb2==4 ||Nb2==5 ||Nb2==6                 ) g.fillOval(Xs[15]-r,Ys[15]-r,(int)(5*r/2),(int)(5*r/2));          //3
			if (Nb2==4 ||Nb2==5 ||Nb2==6                 ) g.fillOval(Xs[16]-r,Ys[16]-r,(int)(5*r/2),(int)(5*r/2));          //4
			if (Nb2==6                                   ) g.fillOval(Xs[17]-r,Ys[17]-r,(int)(5*r/2),(int)(5*r/2));          //5
			if (Nb2==2 ||Nb2==3 ||Nb2==4||Nb2==5||Nb2==6 ) g.fillOval(Xs[18]-r,Ys[18]-r,(int)(5*r/2),(int)(5*r/2));          //6	
			if (Nb2==1 ||Nb2==3 ||Nb2==5                 ) g.fillOval(Xs[19]-r,Ys[19]-r,(int)(5*r/2),(int)(5*r/2));          //7
		}	
	}
	
	
	//for rotate
	public void rotate(int xp,int yp) {	
		
		for (int i =0;i<20;i++){
			int temp;
			temp=xp+(Ys[i]-yp);
			Ys[i]=yp-(Xs[i]-xp);
			Xs[i]=temp;
		}		
		//change items
		int tempx=Xs[0];
		int tempy=Ys[0];
		for (int i=0;i<3;i++){
			Xs[i]=Xs[i+1];
			Ys[i]=Ys[i+1];
		}
		Xs[3]=tempx;
		Ys[3]=tempy;
	}
	//for move
	public void translate(int dx, int dy) {
		for (int i=0; i<20; i++) {
			Xs[i] += dx;
			Ys[i] += dy;
		}
	}


	
		
	
}	

	
		
	
