package panels;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Dimension;

import java.net.URL;

import javax.swing.ImageIcon;


public class ButtonIcon extends JButton {
	public Font tfFont = new Font("Times New Roman", 1, 12);
	public ButtonIcon(){
	super();	
	setFont(tfFont);
	setFont(tfFont);
	setPreferredSize(new Dimension(30, 30));
	}
	public ButtonIcon(String st, int alignment ){
		super();	
		setText(st);
		setHorizontalAlignment(alignment);
		setFont(tfFont);
		setPreferredSize(new Dimension(100, 30));
		}
	public ButtonIcon(String str){
		super();	
		setText(str);
		setFont(tfFont);
		setPreferredSize(new Dimension(100, 30));
		}
	
public void setImageIcon(String file, String tt){
		
		URL url = panels.GUI.class.getResource(file);
		if (url != null) {
			ImageIcon icon = new ImageIcon(url);
			this.setIcon(icon);
			this.setToolTipText(tt);
		}
}
	
}
