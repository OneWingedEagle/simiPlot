package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import panels.Label;




public class ColorBar
{
  private Color[] color;
  private Color[][] clrs;
  private double min;
  private double max;
  public TextField[] tfc = new TextField[2];
  
  public ColorBar() {
    set();
  }
  
  public ColorBar(double min, double max)
  {
    this.min = min;
    this.max = max;
    set();
  }
  

  public ColorBar(int min, int max)
  {
    this.min = min;
    this.max = max;
    set();
  }
  

  public ColorBar(double[][] vals, double min, double max)
  {
    this.min = min;
    this.max = max;
    
    clrs = new Color[vals.length][vals.length];
    
    Vect v = new Vect(vals.length * vals.length + vals.length);
    int[][] map = new int[vals.length][vals.length];
    int ix = 0;
    for (int m = 0; m < vals.length; m++) {
      for (int n = m; n < vals[0].length; n++)
      {
        v.el[ix] = (ix + vals[m][n]);
        map[m][n] = ix;
        ix++;
      }
    }
    




    for (int m = 0; m < vals.length; m++) {
      for (int n = m; n < vals[0].length; n++) {
        double cx = (v.el[map[m][n]] - min) / (max - min);
        

        double a = 0.0D;double b = 0.75D;
        float x = (float)(b - cx * b);
        
        clrs[m][n] = Color.getHSBColor(x, 1.0F, 1.0F);
      }
    }
  }
  
  public void setEnds(double min, double max)
  {
    this.min = min;
    this.max = max;
  }
  
  public double[] getEnds() { double[] ends = new double[2];
    ends[0] = min;
    ends[1] = max;
    return ends;
  }
  

  public JPanel getColorBarPn(double min, double max, double pw, String name, int N)
  {
    JPanel pn = new JPanel(new GridLayout(N + 4, 1, 5, 0));
    DecimalFormat formatter = new DecimalFormat("0.00E0");
    
    if (pw != 0.0) pw = 1.0;
    double rpw = 1.0D / pw;
    double maxpw = Math.signum(max) * Math.pow(Math.abs(max), pw);
    double minpw = Math.signum(min) * Math.pow(Math.abs(min), pw);
    this.min = minpw;
    this.max = maxpw;
    
    set();
    JPanel[] p1 = new JPanel[N];
    for (int j = 0; j < N; j++) {
      p1[j] = new JPanel(new GridLayout(1, 2, 5, 5));
    }
    tfc[0] = new TextField(formatter.format(min));
    tfc[1] = new TextField(formatter.format(max));
    tfc[0].setPreferredSize(new Dimension(40, 20));
    tfc[1].setPreferredSize(new Dimension(40, 20));
    



    Label[][] lb = new Label[N + 2][2];
    
    lb[0][0] = new Label(name, 2);
    lb[1][0] = new Label();
    


    pn.add(lb[0][0]);
    pn.add(lb[1][0]);
    pn.add(tfc[1]);
    
    lb[2][0] = new Label(formatter.format(max));
    lb[2][0].setFont(new Font("Arial", 1, 12));
    lb[(N + 1)][0] = new Label(formatter.format(min));
    lb[(N + 1)][0].setFont(new Font("Arial", 1, 12));
    

    for (int j = 2; j < N + 2; j++)
    {
      if ((j > 2) && (j < N + 1))
      {
        if ((j > 3) && (j < N) && (j % 2 == 0))
        {
          double value = Math.pow(maxpw - j * (maxpw - minpw) / N, rpw);
          lb[j][0] = new Label(formatter.format(value));
          lb[j][0].setFont(new Font("Arial", 1, 12));
        }
        else
        {
          lb[j][0] = new Label("  ");
        }
      }
      
      lb[j][1] = new Label("  ");
      double value = maxpw - j * (maxpw - minpw) / N;
      lb[j][1].setBackground(getColor(value));
      lb[j][1].setOpaque(true);
    }
    



    for (int j = 0; j < N; j++) {
      p1[j].add(lb[(j + 2)][0]);
      p1[j].add(lb[(j + 2)][1]);
      pn.add(p1[j]);
    }
    
    pn.add(tfc[0]);
    
    return pn;
  }
  




  public JPanel getColorBarPnLog(double min, double max, String name, int N)
  {
    double maxlg = Math.log10(max + 1.0D);
    double minlg = Math.log10(min + 1.0D);
    this.min = minlg;
    this.max = maxlg;
    
    set();
    JPanel[] p1 = new JPanel[N];
    for (int j = 0; j < N; j++) {
      p1[j] = new JPanel(new GridLayout(1, 2, 5, 5));
    }
    JPanel pn = new JPanel(new GridLayout(N + 2, 1, 5, 0));
    DecimalFormat formatter = new DecimalFormat("0.00E0");
    

    Label[][] lb = new Label[N + 2][2];
    
    lb[0][0] = new Label(name, 2);
    lb[1][0] = new Label();
    


    pn.add(lb[0][0]);
    pn.add(lb[1][0]);
    
    lb[2][0] = new Label(formatter.format(max));
    lb[2][0].setFont(new Font("Arial", 1, 14));
    lb[(N + 1)][0] = new Label(formatter.format(min));
    lb[(N + 1)][0].setFont(new Font("Arial", 1, 14));
    

    for (int j = 2; j < N + 2; j++)
    {
      if ((j > 2) && (j < N + 1))
      {
        if ((j > 3) && (j < N) && (j % 2 == 0))
        {
          double value = Math.exp(maxlg - j * (maxlg - minlg) / N);
          lb[j][0] = new Label(formatter.format(value));
          lb[j][0].setFont(new Font("Arial", 1, 14));
        }
        else
        {
          lb[j][0] = new Label("  ");
        }
      }
      
      lb[j][1] = new Label("  ");
      double value = maxlg - j * (maxlg - minlg) / N;
      lb[j][1].setBackground(getColor(value));
      lb[j][1].setOpaque(true);
    }
    



    for (int j = 0; j < N + 1; j++) {
      p1[j].add(lb[(j + 2)][0]);
      p1[j].add(lb[(j + 2)][1]);
      pn.add(p1[j]);
    }
    

    return pn;
  }
  
  public Color getColor(double b)
  {
    return getColor(b, min, max);
  }
  
  public Color getColor(double b, double bmin, double bmax)
  {
    int mode = Math.max(0, Math.min(color.length - 1, (int)((b - bmin) / (bmax - bmin) * (color.length - 1))));
    return color[mode];
  }
  
  private void set()
  {
    color = new Color[160];
    for (int c = 0; c < 20; c++)
    {
      int red = 0;
      int green = 0;
      int blue = 100 + c * 155 / 20;
      color[c] = new Color(red, green, blue);
    }
    
    for (int c = 20; c < 25; c++)
    {
      int red = 0;
      int green = 0;
      int blue = 255;
      color[c] = new Color(red, green, blue);
    }
    
    for (int c = 25; c < 48; c++)
    {
      int red = 0;
      int green = (c - 25) * 255 / 23;
      int blue = 255;
      color[c] = new Color(red, green, blue);
    }
    
    for (int c = 48; c < 73; c++)
    {
      int red = 0;
      int green = 190 + (80 - c) * 65 / 32;
      int blue = (73 - c) * 255 / 25;
      color[c] = new Color(red, green, blue);
    }
    
    for (int c = 73; c < 80; c++)
    {
      int red = 0;
      int green = 190 + (80 - c) * 65 / 32;
      int blue = 0;
      color[c] = new Color(red, green, blue);
    }
    
    for (int c = 80; c < 87; c++)
    {
      int red = 0;
      int green = 190 + (c - 80) * 65 / 32;
      int blue = 0;
      color[c] = new Color(red, green, blue);
    }
    
    for (int c = 87; c < 112; c++)
    {
      int red = (c - 87) * 255 / 25;
      int green = 190 + (c - 80) * 65 / 32;
      int blue = 0;
      color[c] = new Color(red, green, blue);
    }
    
    for (int c = 112; c < 160; c++)
    {
      int red = 255;
      int green = (160 - c) * (160 - c) * 255 / 48 / 48;
      int blue = 0;
      color[c] = new Color(red, green, blue);
    }
  }
  

  private void set2()
  {
    color = new Color[160];
    double a = 0.0D;double b = 0.75D;
    for (int i = 0; i < color.length; i++)
    {
      double cx = i * 1.0D / color.length;
      float x = (float)(b - cx * b);
      float y = (float)(0.8D + i * 0.2D / color.length);
      float z = (float)(0.8D + i * 0.2D / color.length);
      
      color[i] = Color.getHSBColor(x, y, z);
    }
  }
  
  private void set3(double[][] vals)
  {
    color = new Color[vals.length * vals.length / 2 + vals.length];
    Vect v = new Vect(color.length);
    int ix = 0;
    for (int i = 0; i < vals.length; i++) {
      for (int j = i; j < vals[0].length; j++)
      {
    	  v.el[ix] = vals[i][j];
        






        double cx = (vals[i][j] - min) / (max - min);
        

        double a = 0.0D;double b = 0.75D;
        float x = (float)(b - cx * b);
        float y = (float)(0.8D + i * 0.2D / color.length);
        float z = (float)(0.8D + i * 0.2D / color.length);
        
        color[ix] = Color.getHSBColor(x, y, z);
        ix++;
      }
    }
  }
  


  public Color getColor(int i, int j)
  {
    return clrs[i][j];
  }
}
