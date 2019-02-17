package panels;

import java.awt.Font;
import javax.swing.JButton;






public class Button
  extends JButton
{
  public Font tfFont = new Font("Times New Roman", 1, 13);
  
  public Button()
  {
    setFont(tfFont);
  }
  
  public Button(String st, int alignment) {
    setText(st);
    setHorizontalAlignment(alignment);
    setFont(tfFont);
  }
  
  public Button(String str) {
    setText(str);
    setFont(tfFont);
  }
}
