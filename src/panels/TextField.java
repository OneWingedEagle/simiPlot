package panels;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

public class TextField extends JTextField
{
  Font tfFont = new Font("Arial", 0, 12);
  

  public TextField()
  {
    setFont(tfFont);
  }
  
  public TextField(int size) {
    setSize(new Dimension(10, size));
    setFont(tfFont);
  }
  
  public TextField(String str) {
    setText(str);
    setFont(tfFont);
  }
  
  public TextField(int size, String str) {
    setText(str);
    setSize(new Dimension(10, size));
    setFont(tfFont);
  }
}
