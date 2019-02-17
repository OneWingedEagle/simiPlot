package panels;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.StyleConstants;

public class FontChooser extends javax.swing.JDialog implements java.awt.event.ActionListener
{
  javax.swing.JColorChooser colorChooser;
  JComboBox fontName;
  JComboBox labelChooser;
  JCheckBox fontBold;
  JCheckBox fontItalic;
  javax.swing.JTextField fontSize;
  JLabel previewLabel;
  javax.swing.text.SimpleAttributeSet attributes;
  java.awt.Font newFont;
  java.awt.Color newColor;
  java.awt.Font dfFont = new java.awt.Font("Arial", 1, 11);
  int selIndex;
  
  public FontChooser(java.awt.Frame parent, String[] label) {
    super(parent, "Font Chooser", true);
    setSize(550, 400);
    attributes = new javax.swing.text.SimpleAttributeSet();
    

    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
        closeAndCancel();
      }
      

    });
    java.awt.Container c = getContentPane();
    
    JPanel fontPanel = new JPanel();
    fontName = new JComboBox(new String[] { "Arial", "Times New Roman", 
      "Helvetica", "Courier" });
    
    int L = label.length;
    String[] label2 = new String[L + 1];
    label2[0] = "-All-";
    for (int i = 1; i < L + 1; i++)
      label2[i] = label[(i - 1)];
    labelChooser = new JComboBox(label2);
    labelChooser.setBackground(new java.awt.Color(215, 255, 225));
    
    fontName.setSelectedIndex(0);
    fontName.addActionListener(this);
    fontSize = new javax.swing.JTextField("11", 4);
    fontSize.setHorizontalAlignment(4);
    fontSize.addActionListener(this);
    fontBold = new JCheckBox("Bold");
    fontBold.setSelected(true);
    fontBold.addActionListener(this);
    fontItalic = new JCheckBox("Italic");
    fontItalic.addActionListener(this);
    
    fontPanel.add(labelChooser);
    fontPanel.add(fontName);
    fontPanel.add(new JLabel(" Size: "));
    fontPanel.add(fontSize);
    fontPanel.add(fontBold);
    fontPanel.add(fontItalic);
    
    c.add(fontPanel, "North");
    


    colorChooser = new javax.swing.JColorChooser(java.awt.Color.black);
    colorChooser.getSelectionModel()
      .addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent e) {
          updatePreviewColor();
        }
      });
    c.add(colorChooser, "Center");
    
    JPanel previewPanel = new JPanel(new java.awt.BorderLayout());
    previewLabel = new JLabel("Here's a sample of this font.");
    previewLabel.setForeground(colorChooser.getColor());
    previewLabel.setFont(dfFont);
    
    previewPanel.add(previewLabel, "Center");
    

    javax.swing.JButton okButton = new javax.swing.JButton("Ok");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent ae) {
        updatePreviewFont();
        closeAndSave();
      }
    });
    javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent ae) {
        closeAndCancel();
      }
      
    });
    JPanel controlPanel = new JPanel();
    controlPanel.add(okButton);
    controlPanel.add(cancelButton);
    previewPanel.add(controlPanel, "South");
    

    previewPanel.setMinimumSize(new java.awt.Dimension(100, 100));
    previewPanel.setPreferredSize(new java.awt.Dimension(100, 100));
    
    c.add(previewPanel, "South");
  }
  


  public void actionPerformed(java.awt.event.ActionEvent ae)
  {
    if (!StyleConstants.getFontFamily(attributes).equals(fontName.getSelectedItem())) {
      StyleConstants.setFontFamily(attributes, 
        (String)fontName.getSelectedItem());
    }
    
    if (StyleConstants.getFontSize(attributes) != 
      Integer.parseInt(fontSize.getText())) {
      StyleConstants.setFontSize(attributes, 
        Integer.parseInt(fontSize.getText()));
    }
    
    if (StyleConstants.isBold(attributes) != fontBold.isSelected()) {
      StyleConstants.setBold(attributes, fontBold.isSelected());
    }
    
    if (StyleConstants.isItalic(attributes) != fontItalic.isSelected()) {
      StyleConstants.setItalic(attributes, fontItalic.isSelected());
    }
    
    updatePreviewFont();
  }
  

  protected void updatePreviewFont()
  {
    String name = StyleConstants.getFontFamily(attributes);
    
    boolean bold = fontBold.isSelected();
    boolean ital = fontItalic.isSelected();
    int size = Integer.parseInt(fontSize.getText());
    name = fontName.getSelectedItem().toString();
    
    java.awt.Font f = new java.awt.Font(name, (bold ? 1 : 0) + (
      ital ? 2 : 0), size);
    previewLabel.setFont(f);
  }
  

  protected void updatePreviewColor()
  {
    previewLabel.setForeground(colorChooser.getColor());
    
    previewLabel.repaint(); }
  
  public java.awt.Font getNewFont() { return newFont; }
  public java.awt.Color getNewColor() { return newColor; }
  public javax.swing.text.AttributeSet getAttributes() { return attributes; }
  
  public void closeAndSave()
  {
    newFont = previewLabel.getFont();
    newColor = previewLabel.getForeground();
    
    setVisible(false);
  }
  
  public void closeAndCancel()
  {
    newFont = dfFont;
    
    setVisible(false);
  }
}
