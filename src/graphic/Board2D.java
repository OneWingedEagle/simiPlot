package graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.JPanel;

public class Board2D extends JPanel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public java.awt.Image img;
  public int[][][] crn;
  public int[][] cbn;
  public double[][] vals;
  public String[] rowLabel;
  public String[] colLabel;
  public ColorBar cb,cb_below1,cb_above1;
  public boolean showPlot = true;
  public boolean showLables = true;
  public boolean showLines = true;
  public boolean showCurve= true;
  public String title = " Similarity Plot ";
  
  public Font[] rowlbFont,collbFont;
  
  public Color[] rowlbColor,collbColor;
  
  public int mode;
  

  public Board2D()
  {
    setBackground(Color.WHITE);
  }
  
  public void actionPerformed(ActionEvent e) {
    repaint();
  }
  
  public void paint(java.awt.Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
      RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
    
    double a = 1.0;
    int I = crn.length;
    int J = crn[0].length;

    double drange=cb.getEnds()[1]-cb.getEnds()[0];
  //  System.out.println("   "+  drange);
    int nCounts=I*J/50;
    int[] counts=new int[nCounts];
    double interval=drange/nCounts;
    
    double cmin= cb.getEnds()[0];
    double cmax= cb.getEnds()[1];
    double c_mean=(cmin + cmax)/2;
    

    g2d.setFont(new Font("Times New Roman", 1, 20));
    g2d.drawString(title, crn[(I / 2)][(J / 2)][0] - 30, crn[0][0][1]-20);
    
    
    double pih=Math.PI/2;
    //******************************* initial rotate
    g2d.rotate(pih, 0,0);
    g2d.translate(-50, -crn[I-1][0][1]-100);

    //********************************
    
    
    for (int i = 0; i < I; i++) {
    	int j0=i;
    	if(mode==2) 
    		j0=0;
      for (int j = j0; j < J; j++) {
      // System.out.println(i+" "+j+"    "+  vals[i][j]);

    	  double val=vals[i][j];
    	  for(int k=0;k<nCounts;k++){
    		  if(val>=k*interval && val<=(k+1)*interval){
    		  counts[k]++;
    		  break;
    		  }
    	  }
    	 
    	
    		Color color=null;

        	double f=1;
        	if(val>=1){
        		f=1-Math.pow((val-1)/(cmax-1),4);
        		color=this.cb_above1.getColor(val);
        	}
        	else{
        		color=this.cb_below1.getColor(val);
        	}


          int rx = (int)(color.getRed() *a*f);
          int gx = (int)(color.getGreen() * a*f);
          int bx = (int)(color.getBlue() * a*f);
   
        color = new Color(rx, gx, bx);
        if(showPlot){
        g2d.setColor(color);
        g2d.fill3DRect(crn[i][j][0], crn[i][j][1], crn[i][j][2], crn[i][j][3], true);
    	  }
      }
    }


    g2d.setColor(Color.black);
    g2d.setFont(new Font("Aria", 1, 16));
    DecimalFormat df = new DecimalFormat("0.0");
    


    
 
    	

        int dm = 0;
        for (int i = 0; i < I; i++)
        {
          int x = rowLabel[i].getBytes().length;
          if (x > dm) { dm = x;
          }
        }
        
        int d0 =dm *6+6;
    for (int i = 0; i < I; i++) {
      g2d.setColor(rowlbColor[i]);
      g2d.setFont(rowlbFont[i]);
    //  g2d.drawString(rowLabel[i], crn[0][0][0] - d0 , crn[0][i][1] + crn[0][i][3] - h0);
      
      if(showLables){
      g2d.drawString(rowLabel[i], crn[0][0][0] - d0 , crn[i][0][1] + crn[i][0][3] );
      }

      if (showLines) {
        g2d.setColor(Color.gray.brighter());
      //  g2d.drawLine(crn[0][i][0] - d0 - 3, crn[0][i][1] + crn[0][i][3], crn[0][i][0], crn[0][i][1] + crn[0][i][3]);
        g2d.drawLine(crn[i][0][0] - d0 - 3, crn[i][0][1] + crn[i][0][3], crn[i][0][0], crn[i][0][1] + crn[i][0][3]);
        g2d.setColor(Color.black);
      }
    }

    int dy=crn[0][0][3];
    int xc0=crn[0][0][0]+dy;
    int yc0=crn[I-1][0][1] + crn[I-1][0][3]+3*dy;

    g2d.rotate(-pih, xc0, yc0);
    
    if (showLines && mode!=2) {
      g2d.setColor(Color.gray.brighter());
      g2d.drawLine(crn[0][0][0] - d0 - 3, crn[0][0][1], crn[0][0][0], crn[0][0][1]);
      g2d.setColor(Color.black);
    }
    



    for (int i = 0; i < J; i++) {
      g2d.setColor(collbColor[i]);
      g2d.setFont(collbFont[i]);

      if(showLables){
      g2d.drawString(colLabel[i],xc0 , yc0+i*dy);
      }
      if (showLines /*&& mode!=2*/) {
        g2d.setColor(Color.gray.brighter());
        g2d.drawLine(xc0, yc0+i*dy, xc0+30, yc0+i*dy);
    //    g2d.drawLine(crn[0][i][0] - h0-13, crn[0][0][1] + crn[0][i][3], crn[0][i][0]-148, crn[0][i][1] + crn[0][i][3]);
        g2d.setColor(Color.black);
      }
    }

  
    
    
    
    g2d.translate(100,700);
    g2d.setFont(new Font("Aria", 1, 16));
    //  density graph
    int numberInts=(int)(drange)+1;
     
    int Ld = 5;
    int Lc = Ld * 20;
    cbn = new int[Lc][4];
    
    int dc =2;//(int)(crn[0][0][2] * I * 0.35 / Lc);
  
    int x0=40;
    int y0=40;
    if(mode==2){
    	x0+=100;
    	y0+=100;
    }
    int Hd=Lc*dc;
    int xcb=crn[(I - 1)][(J- 1)][0]+x0;
    int ycb=crn[(I / 2)][(J / 2)][1]+y0;
    // vertical bar
    for (int i = 0; i < Lc; i++) {
        cbn[i][0] =xcb+dc * i;
        cbn[i][1] =ycb;
        cbn[i][2] = dc;
        cbn[i][3] = Hd;
      }

    
     for (int i = 0; i < Lc; i++) {
     	
     	double    val=cmin + drange * i * 1.0 / (Lc - 1);
      
     	Color color=null;

     	double f=1;
     	if(val>=1){
     		f=1-Math.pow((val-1)/(cmax-1),4);
     		color=this.cb_above1.getColor(val);
     	}
     	else{
     		color=this.cb_below1.getColor(val);
     	}


       int rx = (int)(color.getRed() *a*f);
       int gx = (int)(color.getGreen() * a*f);
       int bx = (int)(color.getBlue() * a*f);
       color = new Color(rx, gx, bx);
       
       g2d.setColor(color);
       g2d.fillRect(cbn[i][0], cbn[i][1], cbn[i][2], cbn[i][3]);
     }
     



    // double drange=cb.getEnds()[1]-cb.getEnds()[0];

     int numberLevel=Math.min(numberInts,10);
     int skip=numberInts/numberLevel;
     double[] vals=new double[numberLevel];
     int[] lable_x=new int[numberLevel];
     int Wd=Lc*dc;
     for (int i = 0; i < numberLevel; i ++)
     {
     	vals[i]=i*skip;
     	lable_x[i]=	xcb+(int)((vals[i]-cb.getEnds()[0])*Wd/drange);
      //   System.out.println(" lable_x[i]    "+  lable_x[i]);
     }
     for (int i = 0; i < numberLevel; i++) {
         int dd = 0;
         double val = vals[i];
         if (val < 10.0) { dd += 2;
         } else if (val < 100.0) { dd -= 2;
         }
        g2d.drawLine(lable_x[i], cbn[i][1] + cbn[i][3]+10, lable_x[i], cbn[i][1] + cbn[i][3]);
        g2d.drawString(df.format(val), lable_x[i]-dd, cbn[i][1] + cbn[i][3]+40);
       }
     
     int sumCounts=0;
     for (int i = 0; i < nCounts; i ++)
     	sumCounts+=counts[i];
     double[] dens_distrib=new double[nCounts];
     
     double max_den=0;
     for (int i = 0; i < nCounts; i ++){
     	dens_distrib[i]=counts[i]*1.0/sumCounts/interval;
    // 	System.out.println(" dens_distrib[i]    "+i+"    "+ (dens_distrib[i]));
     	if(dens_distrib[i]>max_den) max_den=dens_distrib[i];
     }
     ///System.out.println(" counts[i]    "+ (counts[i]*1.0/sumCounts/interval));
     
     int densityLevel=6;
     double[] dens_vals=new double[densityLevel];
     double dlevel=max_den/(densityLevel-1);
     int[] lable_y=new int[densityLevel];

  //   System.out.println(" Wd    "+ Wd);
     for (int i = 0; i < densityLevel; i ++)
     {
     	dens_vals[i]=i*dlevel;
     	lable_y[i]=	ycb+(int)(i*1./(densityLevel-1)*Hd);
        // System.out.println(" lable_y[i]    "+  lable_y[i]);
     }
     
     
     int dx=Wd/nCounts;
     


     double [] dens=new double[nCounts];
     for (int i = 0; i < counts.length; i ++)
     	dens[i]=(counts[i]/sumCounts/interval);
     //******************************* initial rotate
   //  g2d.translate(0, crn[I-1][0][1]+200);
   
    

     if (showCurve) {
     g2d.setColor(Color.CYAN);
     g2d.setStroke(new BasicStroke(2));
     
     for (int i = 0; i < counts.length-1; i ++)
     {
     	int yy1=	ycb+Hd-(int)(dens_distrib[i]/max_den*Hd);
     	int yy2=	ycb+Hd-(int)(dens_distrib[i+1]/max_den*Hd);
     	g2d.drawLine(xcb+(int)((i)*dx), yy1, xcb+(int)((i+1)*dx), yy2);
     }
     
     g2d.setColor(Color.black);
     g2d.setStroke(new BasicStroke(1)); 
     }
     
     for (int i = 0; i < densityLevel; i++) {
         int dd = 40;
         double val = dens_vals[densityLevel-1-i];

        g2d.drawLine(xcb-10, lable_y[i], xcb-2, lable_y[i]);
        g2d.drawString(df.format(val), xcb-dd, lable_y[i]);
       }
     
     g2d.setFont(new Font("Aria", 1, 20));
     g2d.drawString("Value", xcb+Wd/2-30, ycb+Hd+70); 
     g2d.rotate(-Math.PI/2, xcb-50,ycb+Hd/2);
     g2d.drawString("Density", xcb-80, ycb+Hd/2); 
     g2d.rotate(Math.PI/2,xcb-50,ycb+Hd/2);
     
     g2d.setFont(new Font("Aria", 1, 16));
     if (mode == 0) {
      // g2d.drawString(" ( % ) ", cbn[(Lc - 1)][0] - 8, cbn[(Lc - 1)][1] - 10);
       g2d.drawString(" ", cbn[(Lc - 1)][0] - 8, cbn[(Lc - 1)][1] - 10);
     } else if (mode == 1) {
       g2d.drawString(" ( nuc. ) ", cbn[(Lc - 1)][0] - 8, cbn[(Lc - 1)][1] - 10);
     }
     
   ////g2d.rotate(-Math.PI/2, xcb+300/2, ycb+dc/2);
  }
}
