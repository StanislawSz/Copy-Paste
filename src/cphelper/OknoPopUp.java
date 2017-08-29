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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author Stas
 */
public class OknoPopUp extends JFrame
{
    
    private JFrame frame;
    private JLabel messageLabel;
    private CopiedStrings copiedStrings;
    private String lastCopiedText = "";

    public OknoPopUp()
    {
        frame = new JFrame();
        messageLabel = new JLabel();
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
//        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
//        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        
        JButton cloesButton = new JButton(new AbstractAction("x") {
                @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        frame.dispose();
                    }
                });
        
        cloesButton.setMargin(new Insets(1, 4, 1, 4));
        cloesButton.setFocusable(false);
        frame.add(cloesButton, constraints);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;
        messageLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        messageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        messageLabel.setVerticalAlignment(JLabel.BOTTOM);
        frame.add(messageLabel, constraints);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setVisible(true);

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
        messageLabel.setText(msg);
    }
    
    public void setLabelToList(Map<Integer, String> list, int offset)
    {
        String messageInFrame = "<HtMl>";
        
        for (int i = 3; i>=0; i--)
        {
            if (i < list.size())
            {
                int mapKey = list.size()-i-offset;
                
                if (mapKey > 0)
                {
                    String text = list.get(mapKey);

                    lastCopiedText = list.get(list.size());

                    if (text.length()>30)
                    {
                        text = cutText(text);
                    }

                    if (i==0)
                    {
                        messageInFrame += boldText(mapKey, text);
                        copiedStrings.setTextInClip(list.get(mapKey));
                    }
                    else
                    {
                        messageInFrame += mapKey + ". " + text + "<br>";
                    }
                }
            }
            
        }
        
        messageLabel.setText(messageInFrame);
        
    }
    
    private String cutText(String text)
    {
        return text.substring(0, 30) + "...";
    }
    
    private String boldText(int mapKey, String text)
    {
        return "<b>" + mapKey + ". " + text + "</b><br>";
    }
    
}
