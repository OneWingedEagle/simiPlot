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
///import javax.media.j3d.ImageComponent2D;
import javax.rmi.CORBA.Util;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

//import com.sun.j3d.utils.image.TextureLoader;

public class GUI extends javax.swing.JFrame implements java.awt.event.ActionListener, java.awt.event.MouseListener
{
  public JTextArea progressArea = new JTextArea(); public JTextArea paramArea = new JTextArea();
  



  public boolean simi = true;
  

  public JTabbedPane tbPanel = new JTabbedPane();
  public TextField tfInputFile;
  public TextField tfOutPutFile;
  public JScrollPane scrollPane;
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
    else if (s.startsWith("RSCU")) {
        mode = 2;
      }
    
    board = new Board2D();
    board.mode = mode;
    

    board.addMouseListener(this);
    

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    height = ((int)(0.9 * screenSize.height));
    width = ((int)(0.9 * screenSize.width));
    setTitle(" Similiarity Plot");
    setPreferredSize(new Dimension(width, height));
    setLocation(0, 0);
   /// setDefaultCloseOperation(3);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
    tbPanel = new JTabbedPane();
    tbPanel.setFont(new Font("Arial", 1, 12));
 
    
/*	scrollPane = new JScrollPane(board,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(500, 5));
	scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 15,10));
	
	tbPanel.add("Plot",scrollPane);*/
    
   // JFrame frm = new JFrame(" Analysis Progress");
  //  frm.setLocation(50, 550);
  //  frm.setPreferredSize(new Dimension(600, 400));
   // frm.add(this.messageScrollPane);
	
    tbPanel.addTab("Plot", board);
    
    getContentPane().add(tbPanel, "Center");
    
    JPanel panel = new JPanel(new GridLayout(20, 1, 5, 5));
    panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    getContentPane().add(panel, "East");
    
    bSimi = new Button("sim/diff");
    bSimi.addActionListener(this);
    


    bLines = new Button("Show Lines");
    bLines.addActionListener(this);
    bCurve = new Button("Show Curve");
    bCurve.addActionListener(this);
    bcBar = new Button("Show ColorBar");
    bcBar.addActionListener(this);
    
    bLables = new Button("Show Lables");
    bLables.addActionListener(this);
    
    bPlot = new Button("Show Plot");
    bPlot.addActionListener(this);
    
    bFont = new Button("rowLabel Font");
    bFont.addActionListener(this);
    
    tfYuser=new TextField("0");
    
    bShot = new ButtonIcon();

    bShot.setPreferredSize(new Dimension(30, 30));
    bShot.setImageIcon("capture.jpg","shot");

    bShot.setPreferredSize(new Dimension(30, 30));
    
    bShot.addActionListener(this);
    


    if (mode == 1) { bSimi.setEnabled(false);
    }
    
    panel.add(bSimi);
    panel.add(bPlot);
    panel.add(bLables);
    panel.add(bLines);
    panel.add(bCurve);
    panel.add(bcBar);
    panel.add(tfYuser);
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
      
      tt = br.readLine();
      String[] sp =tt.split(regex);

      numRows=Integer.parseInt(sp[0]);
 
      if(mode==2)
    	  numCols=Integer.parseInt(sp[1]);
      else
    	   numCols=numRows;
      

      
      board.crn = new int[numRows][numCols][4];
      vals = new double[numRows][numCols];
      board.vals = new double[numRows][numCols];
      board.rowLabel = new String[numRows];
      board.colLabel = new String[numCols];

      board.rowlbFont = new Font[numRows];
      board.rowlbColor = new java.awt.Color[numRows];
      for (int i = 0; i < numRows; i++) {
        board.rowlbFont[i] = new Font("Arial", 1, 11);
      }
      
      board.collbFont = new Font[numCols];
      board.collbColor = new java.awt.Color[numCols];
      for (int i = 0; i < numCols; i++) {
        board.collbFont[i] = new Font("Arial", 1, 11);
      }
      
      int[] crn0 = new int[4];
      int dx0 = (int)(height * 2*0.85D / numRows);
      int x0 = 200;
      x0Label = 200;
      y0Label = 50;
      crn0[0] = x0;
      crn0[2] = dx0;
      crn0[3] = dx0;
      
      int dx = (int)(1.0D * dx0);
      int dy = dx;
      dyLabel = dy;
      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
          board.crn[i][j][0] = (crn0[0] + j * dx);
          board.crn[i][j][1] = (crn0[1] + i * dy);
          board.crn[i][j][2] = crn0[2];
          board.crn[i][j][3] = crn0[3];
        }
      }



      String line = "";
  

      line = br.readLine();

      if(mode==2){
      String[] sp1 = line.split(regex);
      for (int j = 0; j < numCols; j++) {
        

    	  board.colLabel[j] = sp1[j];
     
        }
      }
       
      for (int i = 0; i < numRows; i++) {
        line = br.readLine();

        String[] st = line.split(regex);
        /// System.out.println( line);
        int j0=i;
        if(mode==2) j0=0;
        for (int j = j0; j < numCols; j++) {
      //  	  System.out.println( j+"   "+st[j]);
          vals[i][j] = Double.parseDouble(st[j]);
     
        }
        String name="";
        try{
        	double vv=Double.parseDouble(st[(st.length - 1)]);
        }catch(NumberFormatException e){
        	name=board.rowLabel[i] = st[(st.length - 1)];

      }
        board.rowLabel[i] =  name;
      }
      

      if(mode!=2){
      String[] sp1 = line.split(regex);
      for (int j = 0; j < numCols; j++) {
    	  board.colLabel[j] =    board.rowLabel[j];
     
        }
      }


      if (mode == 0) {
        for (int i = 0; i <numRows; i++) {
         // vals[i][i] = 100.0D;
          vals[i][i] = 1.;
        }
        for (int i = 0; i < numRows; i++) {
          for (int j = 0; j < i; j++){
            vals[i][j] = board.vals[j][i];
          }

        }
        

      } else if (mode == 1)
      {
        for (int i = 0; i < numRows; i++) {
          for (int j = 0; j <= i; j++){
            vals[i][j] = board.vals[j][i];
          ///  System.out.println(  board.vals[i][j]);

                 }
        }

      }

      
      for (int i = 0; i < numRows; i++) {
          for (int j = 0; j < numCols; j++)
          {
    //      	 System.out.println(i+" "+j+" ----   "+  vals[i][j]);

          }
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

    if(mode==2){
    	  for (int i = 0; i < numRows; i++) 
    	      for (int j = 0; j < numCols; j++) 
    	    	  board.vals[i][j] = vals[i][j];

    }else
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {

        if (simi || j<=i+1000) {
          board.vals[i][j] = vals[i][j];
        ///  System.out.println(i+" "+j+"  ---------  "+  vals[i][j]);

        //  System.out.println(  board.vals[i][j]);

        } else {
          board.vals[i][j] = (1. - vals[i][j]);
        }
      }
    }
    

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++)
      {
        if (board.vals[i][j] > vmax) vmax = board.vals[i][j];
        if (board.vals[i][j] < vmin) vmin = board.vals[i][j];
  

      }
    }

    int div=100;
    double[][] ranges=new double[div][2];
    double dx=(vmax-vmin)/ranges.length;
    for (int i = 0; i < div; i++){
  	  ranges[i][0]=i*dx;
  	 ranges[i][1]=(i+1)*dx;
    }
    int[]counts=new int[div];
    
    double[] vv=new double[numRows*numCols];
    int kx=0;
    for (int i = 0; i < numRows; i++) 
        for (int j = 0; j < numCols; j++)
        	vv[kx++]=vals[i][j];
    
	for(int k=0;k<vv.length;k++){
		   
	//	System.out.println(k+"  vv   "+  vv[k]);

	}
    
    for (int i = 0; i < vv.length; i++) {
    	double val= vv[i];
        	for(int k=0;k<div;k++){
        		if(val>=ranges[k][0] && val<=ranges[k][1]){
        			counts[k]++;
        			break;
        		}
        	}
       // 	 System.out.println(i+" "+j+" ----   "+  vals[i][j]);

        }

    int totals=0;
	for(int k=0;k<div;k++)
		totals+= counts[k];
//	System.out.println(numRows*numCols+"  --totals --   "+  totals);
   double[]density=new double[div];
	for(int k=0;k<div;k++){
		density[k]= 	 counts[k]*1./totals;	   
	//	System.out.println((ranges[k][0]+ranges[k][1])/2+"   "+  density[k]);

	}


    board.cb = new graphic.ColorBar(vmin, vmax);
    board.cb_below1 = new graphic.ColorBar(vmin, 2.-vmin);
    board.cb_above1 = new graphic.ColorBar( 2-vmax,vmax);
    
    if (mode == 0) {
      if (simi) {
    //    board.title = " Similarity Plot ( % ) ";
        board.title = " Similarity Plot ";
      } else {
       // board.title = " Difference Plot ( % ) ";
        board.title = " Difference Plot ";
      }
    }
    else  if (mode == 1){
      board.title = " Distance Plot ( nucletoid) ";
    }
    else  if (mode == 2){
        board.title = "       RSCU Plot ";
      }
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == bSimi) {
      simi = (!simi);
      setBoardData();
      board.repaint();
    }
    else if (e.getSource() == bPlot) {
        board.showPlot = (!board.showPlot);
      //  if(!board.showPlot)  board.showLables=false;
        board.repaint();
      }
    else if (e.getSource() == bLines) {
      board.showLines = (!board.showLines);
      board.repaint();
    }
    else if (e.getSource() == bLables) {
        board.showLables = (!board.showLables);
        if(!board.showLables)  board.showLines=false;
        board.repaint();
      }
    else if (e.getSource() == bCurve) {
        board.showCurve = (!board.showCurve);
        board.repaint();
      }
    else if (e.getSource() == bcBar) {
        board.showColorBar = (!board.showColorBar);
        board.showCurve = board.showColorBar ;
        if( board.showColorBar) board.yuser=Integer.parseInt(tfYuser.getText());
        board.repaint();
      }
    else if (e.getSource() == bFont) {
      Frame f = new Frame();
      fch = new FontChooser(f, board.rowLabel);
      fch.setVisible(true);
      int k = fch.labelChooser.getSelectedIndex();
      if (k > 0) {
        board.rowlbFont[(k - 1)] = fch.newFont;
        board.rowlbColor[(k - 1)] = fch.newColor;
      }
      else {
        for (int p = 0; p < board.rowlbFont.length; p++) {
          board.rowlbFont[p] = fch.newFont;
          board.rowlbColor[p] = fch.newColor;
        } }
      board.repaint();
      fch.dispose();
      f.dispose();

    }
    else if (e.getSource() == bShot) {
      takeShot();
    } }
  
  public Button bLines,bCurve,bLables,bPlot,bcBar;
  public  TextField tfYuser;
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
      fch = new FontChooser(f, board.rowLabel);
      
      int k = 1 + selectedLabel;
      
      fch.labelChooser.setSelectedIndex(k);
      
      fch.setVisible(true);
      board.rowlbFont[(k - 1)] = fch.newFont;
      board.rowlbColor[(k - 1)] = fch.newColor;
      
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
  public int numRows,numCols;
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
