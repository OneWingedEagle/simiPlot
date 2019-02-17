package panels;

import graphic.Board2D;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.media.j3d.ImageComponent2D;
import javax.rmi.CORBA.Util;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.sun.j3d.utils.image.TextureLoader;

public class GUI extends javax.swing.JFrame implements java.awt.event.ActionListener, java.awt.event.MouseListener
{
  public JTextArea progressArea = new JTextArea(); public JTextArea paramArea = new JTextArea();
  



  public boolean simi = true;
  

  public JTabbedPane tbPanel = new JTabbedPane();
  public TextField tfInputFile;
  public TextField tfOutPutFile;
  
  public GUI()
  {
    mode = 0;
    String s = "";
    String file = getFile(0);
    try
    {
      Scanner scr = new Scanner(new FileReader(file));
      if (scr.hasNext())
        s = scr.next();
      scr.close();
    }
    catch (IOException ee) {
      ee.printStackTrace();
    }
    if (s.startsWith("dist")) {
      mode = 1;
    }
    System.out.println(s);
    
    board = new Board2D();
    board.mode = mode;
    

    board.addMouseListener(this);
    

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    height = ((int)(0.9 * screenSize.height));
    width = ((int)(0.7 * screenSize.width));
    setTitle(" Similiarity Plot");
    setPreferredSize(new Dimension(width, height));
    setLocation(10, 10);
    setDefaultCloseOperation(3);
    

    tbPanel = new JTabbedPane();
    tbPanel.setFont(new Font("Arial", 1, 12));
    tbPanel.addTab("Plot", board);
    
    getContentPane().add(tbPanel, "Center");
    
    JPanel panel = new JPanel(new GridLayout(20, 1, 5, 5));
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    getContentPane().add(panel, "East");
    
    bSimi = new Button("sim/diff");
    bSimi.addActionListener(this);
    


    bLines = new Button("Show Lines");
    bLines.addActionListener(this);
    
    bFont = new Button("Label Font");
    bFont.addActionListener(this);
    

    bShot = new ButtonIcon();

    bShot.setPreferredSize(new Dimension(30, 30));
    bShot.setImageIcon("capture.jpg","shot");

    bShot.setPreferredSize(new Dimension(30, 30));
    
    bShot.addActionListener(this);
    


    if (mode == 1) { bSimi.setEnabled(false);
    }
    
    panel.add(bSimi);
    panel.add(bLines);
    panel.add(bFont);
    Panel pc = new Panel(new GridLayout(1, 3, 5, 5));
    pc.add(new Label());
    pc.add(bShot);
    pc.add(new Label());
    panel.add(pc);
    



    loadData(file);
    
    setBoardData();
    
    pack();
  }
  
  public Button Browse1;
  public Button Browse2;
  public Button bSimi;
  private void loadData(String data) { String regex = "[: ,=\\t]+";
    



    try
    {
      BufferedReader br = new BufferedReader(new FileReader(data));
      String tt = br.readLine();
      

      int I = Integer.parseInt(br.readLine());
      

      int J = I;
      
      board.crn = new int[I][I][4];
      vals = new double[I][I];
      board.vals = new double[I][I];
      board.label = new String[I];
      

      board.lbFont = new Font[I];
      board.lbColor = new java.awt.Color[I];
      for (int i = 0; i < I; i++) {
        board.lbFont[i] = new Font("Arial", 1, 11);
      }
      
      int[] crn0 = new int[4];
      int dx0 = (int)(height * 0.65D / I);
      int x0 = 200;
      x0Label = 200;
      y0Label = 50;
      crn0[0] = x0;
      crn0[1] = y0Label;
      crn0[2] = dx0;
      crn0[3] = dx0;
      
      int dx = (int)(1.0D * dx0);
      int dy = dx;
      dyLabel = dy;
      for (int i = 0; i < I; i++) {
        for (int j = 0; j < I; j++) {
          board.crn[i][j][0] = (crn0[0] + i * dx);
          board.crn[i][j][1] = (crn0[1] + j * dy);
          board.crn[i][j][2] = crn0[2];
          board.crn[i][j][3] = crn0[3];
        }
      }



      String line = "";
      line = br.readLine();
      
      for (int i = 0; i < I; i++) {
        line = br.readLine();
        
        String[] st = line.split(regex);
        for (int j = i; j < I; j++) {
          vals[i][j] = Double.parseDouble(st[j]);
        }
        board.label[i] = st[(st.length - 1)];

      }
      



      if (mode == 0) {
        for (int i = 0; i < I; i++) {
         // vals[i][i] = 100.0D;
          vals[i][i] = 1.;
        }
        for (int i = 0; i < I; i++) {
          for (int j = 0; j < i; j++){
            vals[i][j] = board.vals[j][i];
          }

        }
        

      } else if (mode == 1)
      {
        for (int i = 0; i < I; i++) {
          for (int j = 0; j <= i; j++){
            vals[i][j] = board.vals[j][i];

                 }
        }

      }
      for (int i = 0; i < I; i++) {
        for (int j = 0; j < I; j++) {
         // System.out.print(vals[i][j] + " ");

        }
      //  System.out.println();
      }

      br.close();
     
      
    }
    catch (IOException e)
    {
      System.out.println("loading data file failed.");System.exit(0);
    }
  }
  
  public void setBoardData()
  {
    double vmin = 1000000.0;double vmax = 0.0;
    int I = vals.length;
    for (int i = 0; i < I; i++) {
      for (int j = i; j < I; j++) {
        if (simi) {
          board.vals[i][j] = vals[i][j];
        } else {
          board.vals[i][j] = (1. - vals[i][j]);
        }
      }
    }
    for (int i = 0; i < I; i++) {
      for (int j = i; j < I; j++)
      {
        if (board.vals[i][j] > vmax) vmax = board.vals[i][j];
        if (board.vals[i][j] < vmin) vmin = board.vals[i][j];
      }
    }


    board.cb = new graphic.ColorBar(vmin, vmax);
    

    if (mode == 0) {
      if (simi) {
    //    board.title = " Similarity Plot ( % ) ";
        board.title = " Similarity Plot ";
      } else {
       // board.title = " Difference Plot ( % ) ";
        board.title = " Difference Plot ";
      }
    }
    else {
      board.title = " Distance Plot ( nucletoid) ";
    }
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == bSimi) {
      simi = (!simi);
      setBoardData();
      board.repaint();
    }
    else if (e.getSource() == bLines) {
      board.showLines = (!board.showLines);
      board.repaint();
    }
    else if (e.getSource() == bFont) {
      Frame f = new Frame();
      fch = new FontChooser(f, board.label);
      fch.setVisible(true);
      int k = fch.labelChooser.getSelectedIndex();
      if (k > 0) {
        board.lbFont[(k - 1)] = fch.newFont;
        board.lbColor[(k - 1)] = fch.newColor;
      }
      else {
        for (int p = 0; p < board.lbFont.length; p++) {
          board.lbFont[p] = fch.newFont;
          board.lbColor[p] = fch.newColor;
        } }
      board.repaint();
      fch.dispose();
      f.dispose();

    }
    else if (e.getSource() == bShot) {
      takeShot();
    } }
  
  public Button bLines;
  public Button bFont;
  public ButtonIcon bShot;
  public FontChooser fch;
  public String inputFile;
  public String outputFile;
  public void mouseClicked(MouseEvent me) { if (me.getClickCount() == 2) {
      mouseX = me.getX();
      mouseY = me.getY();
      pr(mouseX + " " + mouseY);
      if (mouseX < x0Label) selectedLabel = ((mouseY - y0Label) / dyLabel);
      pr(selectedLabel);
      
      Frame f = new Frame();
      fch = new FontChooser(f, board.label);
      
      int k = 1 + selectedLabel;
      
      fch.labelChooser.setSelectedIndex(k);
      
      fch.setVisible(true);
      board.lbFont[(k - 1)] = fch.newFont;
      board.lbColor[(k - 1)] = fch.newColor;
      
      board.repaint();
      fch.dispose();
      f.dispose();
    }
  }
  

  public double[][] vals;
  
  public int mouseX;
  
  public int mouseY;
  
  public int y0Label;
  
  public int x0Label;
  public int dyLabel;
  public int selectedLabel;
  public int mode;
  public Board2D board;
  private int width;
  private int height;
  public void mouseEntered(MouseEvent me) {}
  
  public void mouseExited(MouseEvent me) {}
  
  public void mousePressed(MouseEvent me) {}
  
  public void mouseReleased(MouseEvent me) {}
  
  public static void main(String[] args)
  {
    GUI gui = new GUI();
    gui.setVisible(true);
  }
  
  public static void show(int[][] A)
  {
    for (int i = 0; i < A.length; i++) {
      for (int j = 0; j < A[0].length; j++)
        System.out.format("%d\t", new Object[] { Integer.valueOf(A[i][j]) });
      System.out.println();
    }
    System.out.println();
  }
  
  public static void show(int[] v) { for (int i = 0; i < v.length; i++)
      System.out.format("%d\n", new Object[] { Integer.valueOf(v[i]) });
    System.out.println();
  }
  
  public static void hshow(int[] v) { for (int i = 0; i < v.length; i++)
      System.out.format("%d\t", new Object[] { Integer.valueOf(v[i]) });
    System.out.println();
  }
  
  public static void show(byte[] v) {
    for (int i = 0; i < v.length; i++)
      System.out.format("%d\t", new Object[] { Byte.valueOf(v[i]) });
    System.out.println();
  }
  
  public static void show(boolean[] v) {
    for (int i = 0; i < v.length; i++)
      System.out.format("%s\t", new Object[] { Boolean.valueOf(v[i]) });
    System.out.println();
  }
  
  public static void show(boolean[][] A) {
    for (int i = 0; i < A.length; i++) {
      for (int j = 0; j < A[0].length; j++)
        System.out.format("%s\t", new Object[] { Boolean.valueOf(A[i][j]) });
      System.out.println();
    }
  }
  
  public static void pr(double a)
  {
    System.out.println(a);
  }
  

  public static void pr(String a)
  {
    System.out.println(a);
  }
  
  public static void pr(int a)
  {
    System.out.println(a);
  }
  
  public static void pr(boolean b)
  {
    System.out.println(b);
  }
  
  public static String getFile(int mode)
  {
    String filePath = "";
    
    Frame f = new Frame();
    FileDialog fd; FileDialog fd1; if (mode == 0) {
      fd1 = new FileDialog(f, "Select  file", 0);
    } else
      fd1 = new FileDialog(f, "Select  file", 1);
    fd1.setVisible(true);
    fd1.toFront();
    String Folder = fd1.getDirectory();
    String File = fd1.getFile();
    if ((Folder != null) && (File != null))
    {

      filePath = Folder + "\\" + File;
    }
    
    f.dispose();
    fd1.dispose();
    
    return filePath;
  }
  

  public void takeShot()
  {
    DateFormat dateFormat = new java.text.SimpleDateFormat("mm.ss");
    java.util.Date date = new java.util.Date();
    String suff = dateFormat.format(date);
    
    String root = System.getProperty("user.dir") + "\\Plot Images";
    File folder = new File(root);
    if (!folder.exists()) {
      folder.mkdir();
    }
    try
    {
      Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
      rec = new Rectangle(board.getBounds());
      java.awt.image.BufferedImage bi = new Robot().createScreenCapture(rec);
      File file = new File(root + "\\shot" + suff + ".PNG");
      ImageIO.write(bi, "PNG", file);
    }
    catch (HeadlessException e3)
    {
      e3.printStackTrace();
    }
    catch (AWTException e3) {
      e3.printStackTrace();
    }
    catch (IOException e3) {
      e3.printStackTrace();
    }
  }
}
