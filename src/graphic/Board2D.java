package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.JPanel;

public class Board2D extends JPanel
{
  public java.awt.Image img;
  public int[][][] crn;
  public int[][] cbn;
  public double[][] vals;
  public String[] label;
  public ColorBar cb;
  public boolean showLines = true;
  public String title = " Similarity Plot ";
  
  public Font[] lbFont;
  
  public Color[] lbColor;
  
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
    
    double a = 0.8D;
    int I = crn.length;
    int ix = 0;
    for (int i = 0; i < I; i++) {
      for (int j = i; j < I; j++) {
        Color color = cb.getColor(vals[i][j]);
        
        int rx = (int)(color.getRed() * a);
        int gx = (int)(color.getGreen() * a);
        
        int bx = (int)(color.getBlue() * a);
        
        color = new Color(rx, gx, bx);
        g2d.setColor(color);
        g2d.fill3DRect(crn[i][j][0], crn[i][j][1], crn[i][j][2], crn[i][j][3], true);
      }
    }
    int Ld = 5;
    int Lc = Ld * 6;
    cbn = new int[Lc][4];
    
    int dc = (int)(crn[0][0][2] * I * 0.5D / Lc);
    
    for (int i = 0; i < Lc; i++) {
      cbn[i][0] = crn[(I - 1)][(I - 1)][0];
      cbn[i][1] = (crn[(I / 2)][(I / 2)][1] - dc * i);
      cbn[i][2] = 20;
      cbn[i][3] = dc;
    }
    
    for (int i = 0; i < Lc; i++) {
      Color color = cb.getColor(cb.getEnds()[0] + (cb.getEnds()[1] - cb.getEnds()[0]) * i * 1.0D / (Lc - 1));
      
      int rx = (int)(color.getRed() * a);
      int gx = (int)(color.getGreen() * a);
      int bx = (int)(color.getBlue() * a);
      color = new Color(rx, gx, bx);
      g2d.setColor(color);
      g2d.fillRect(cbn[i][0], cbn[i][1], cbn[i][2], cbn[i][3]);
    }
    



    g2d.setColor(Color.black);
    g2d.setFont(new Font("Aria", 1, 16));
    DecimalFormat df = new DecimalFormat("0.000");
    for (int i = 0; i < Lc; i += 5) {
      int dd = 50;
      double val = cb.getEnds()[0] + (cb.getEnds()[1] - cb.getEnds()[0]) * i * 1.0D / (Lc - 1);
      if (val < 10.0D) { dd += 2;
      } else if (val < 100.0D) { dd -= 2;
      }
      g2d.drawLine(cbn[i][0] - 5, cbn[i][1] + cbn[i][3], cbn[i][0], cbn[i][1] + cbn[i][3]);
      g2d.drawString(df.format(val), cbn[i][0] - dd, cbn[i][1] + cbn[i][3]);
    }
    int dd = 50;
    g2d.drawLine(cbn[(Lc - 1)][0] - 5, cbn[(Lc - 1)][1], cbn[(Lc - 1)][0], cbn[(Lc - 1)][1]);
    g2d.drawString(df.format(cb.getEnds()[1]), cbn[(Lc - 1)][0] - dd, cbn[(Lc - 1)][1]);
    
    g2d.setFont(new Font("Aria", 1, 16));
    if (mode == 0) {
     // g2d.drawString(" ( % ) ", cbn[(Lc - 1)][0] - 8, cbn[(Lc - 1)][1] - 10);
      g2d.drawString(" ", cbn[(Lc - 1)][0] - 8, cbn[(Lc - 1)][1] - 10);
    } else if (mode == 1) {
      g2d.drawString(" ( nuc. ) ", cbn[(Lc - 1)][0] - 8, cbn[(Lc - 1)][1] - 10);
    }
    


    g2d.setFont(new Font("Times New Roman", 1, 20));
    g2d.drawString(title, crn[(I / 2)][(I / 2)][0] - 60, crn[0][0][1]-20);
    

    int dm = 0;
    for (int i = 0; i < I; i++)
    {
      int x = label[i].getBytes().length;
      if (x > dm) { dm = x;
      }
    }
    
    int d0 = dm * 6;
    

    if (showLines) {
      g2d.setColor(Color.gray.brighter());
      g2d.drawLine(crn[0][0][0] - d0 - 3, crn[0][0][1], crn[0][0][0], crn[0][0][1]);
      g2d.setColor(Color.black);
    }
    

    for (int i = 0; i < I; i++) {
      g2d.setColor(lbColor[i]);
      g2d.setFont(lbFont[i]);
      g2d.drawString(label[i], crn[0][0][0] - d0 - 3, crn[0][i][1] + crn[0][i][3] - 2);
      if (showLines) {
        g2d.setColor(Color.gray.brighter());
        g2d.drawLine(crn[0][i][0] - d0 - 3, crn[0][i][1] + crn[0][i][3], crn[0][i][0], crn[0][i][1] + crn[0][i][3]);
        g2d.setColor(Color.black);
      }
    }
    



    double xm = (crn[0][0][0] + crn[(I - 1)][(I - 1)][0] + crn[(I - 1)][(I - 1)][2]) / 2;
    double ym = (crn[0][0][1] + crn[(I - 1)][(I - 1)][1] + crn[(I - 1)][(I - 1)][3]) / 2;
    
    g2d.rotate(-1.5707963267948966D, xm, ym);
    
    if (showLines) {
      g2d.setColor(Color.gray.brighter());
      g2d.drawLine(crn[0][0][0] - d0 - 3, crn[0][0][1], crn[0][0][0], crn[0][0][1]);
      g2d.setColor(Color.black);
    }
    

    for (int i = 0; i < I; i++) {
      g2d.setColor(lbColor[i]);
      g2d.setFont(lbFont[i]);
      g2d.drawString(label[i], crn[0][0][0] - d0 - 3, crn[0][i][1] + crn[0][i][3] - 2);
      if (showLines) {
        g2d.setColor(Color.gray.brighter());
        g2d.drawLine(crn[0][i][0] - d0 - 3, crn[0][i][1] + crn[0][i][3], crn[0][i][0], crn[0][i][1] + crn[0][i][3]);
        g2d.setColor(Color.black);
      }
    }
  }
}
