package panels;

import java.awt.Font;

public class Label extends javax.swing.JLabel
{
  Font tfFont = new Font("Arial", 0, 13);
  
  public Label() {
    setFont(tfFont);
  }
  
  public Label(String st, int alignment) {
    setText(st);
    setHorizontalAlignment(alignment);
    setFont(tfFont);
  }
  
  public Label(String str)
  {
    setText(str);
    setFont(tfFont);
    setHorizontalAlignment(4);
  }
}
