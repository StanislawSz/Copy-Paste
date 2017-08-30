/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cphelper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author Stas
 */
public class OknoPopUpTest extends JFrame
{
    
    private JFrame frame;
    private JLabel messageLabel1;
    private JLabel messageLabel2;
    private JLabel messageLabel3;
    private CopiedStrings copiedStrings;
    private String lastCopiedText = "";
    private String textToSetClip = "";

    public OknoPopUpTest()
    {
        frame = new JFrame();
        messageLabel1 = new JLabel();
        messageLabel2 = new JLabel("test2");
        messageLabel3 = new JLabel("test3");
        copiedStrings = new CopiedStrings();
        
        frame = setFrameConfig(frame);
    }
    
    private JFrame setFrameConfig(JFrame frame)
    {
        frame.setSize(400,135);
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());// height of the task bar
        frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height - toolHeight.bottom - frame.getHeight());
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setBackground(Color.yellow);
//                frame.setOpacity(0.9f);
        frame.setAutoRequestFocus(false);
        frame.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 0f;
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.fill = GridBagConstraints.BOTH;

//        messageLabel1.setBorder(BorderFactory.createLineBorder(Color.black));
        messageLabel1.setFont(new Font("Serif", Font.PLAIN, 18));
        messageLabel1.setOpaque(false);
        frame.add(messageLabel1, constraints);
        
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 0;
        constraints.gridy++; // oddzielenie elementow
        constraints.weightx = 1.0f;
        constraints.weighty = 0f;
        constraints.insets = new Insets(5, 10, 5, 10); //wstawka
        constraints.fill = GridBagConstraints.BOTH; // rozciaga na cala szerokosc
        
        messageLabel2.setBorder(BorderFactory.createLineBorder(Color.black));
        messageLabel2.setFont(new Font("Serif", Font.BOLD, 18));
        messageLabel2.setOpaque(false);
        frame.add(messageLabel2, constraints);
        
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 0;
        constraints.gridy++; // oddzielenie elementow
        constraints.weightx = 1.0f;
        constraints.weighty = 0f;
        constraints.insets = new Insets(5, 10, 10, 10); //wstawka
        constraints.fill = GridBagConstraints.BOTH; // rozciaga na cala szerokosc
        
//        messageLabel3.setBorder(BorderFactory.createLineBorder(Color.black));
        messageLabel3.setFont(new Font("Serif", Font.PLAIN, 18));
        messageLabel3.setOpaque(false);
        frame.add(messageLabel3, constraints);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        return frame;
    }
    
    public void showme(boolean flag)
    {
        if (flag == false & frame.isVisible())
        {
            pasteText();
                
        }
        
        frame.setVisible(flag);
        this.toFront();

    }
    
    private void pasteText()
    {
        Robot r;
        copiedStrings.setTextInClip(textToSetClip);
        
        try {
            r = new Robot();
            Thread.sleep(10);
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(10);

            copiedStrings.setTextInClip(lastCopiedText);
                    
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean getFocus()
    {
        return frame.isFocused();
    }
    
    public void setLabelToText(String msg)
    {
        messageLabel1.setText(msg);
    }
    
    public void setLabelToList(Map<Integer, String> list, int middleElement )
    {
        if (list.containsKey(middleElement))
        {
            String text = list.get(middleElement);
            text = cutText(text);
            
            lastCopiedText = list.get(list.size());
            textToSetClip = list.get(middleElement);
            
            messageLabel2.setText(middleElement + ". " + list.get(middleElement));
        }
        else
        {
            messageLabel2.setText(" ");
        }
        
        if (list.containsKey(middleElement-1))
        {
            messageLabel1.setText(middleElement-1 + ". " + list.get(middleElement-1));
        }
        else
        {
            messageLabel1.setText(" ");
        }
        
        if (list.containsKey(middleElement+1))
        {
            messageLabel3.setText(middleElement+1 + ". " + list.get(middleElement+1));
        }
        else
        {
            messageLabel3.setText(" ");
        }
        
        if (messageLabel2.getText().equals(" "))
        {
            textToSetClip = "";
        }
        
    }
    
    private String cutText(String text)
    {
        if (text.length() > 25)
        {
            return text.substring(0, 25) + "...";
        }
        else
        {
            return text;
        }
    }
    
}
