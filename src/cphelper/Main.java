package cphelper;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Stas
 */
public class Main implements NativeKeyListener
{
    private boolean ctrlKey = false;
    private boolean downKey = false;
    private int offset = 0;
    private OknoPopUpTest frame = new OknoPopUpTest();
    private CopiedStrings copiedStrings = new CopiedStrings();
    private TrayClass icon = new TrayClass(copiedStrings);
    

    /**
     * @param args the command line arguments
     */
    public Main()
    {
        copiedStrings.getTextFromClip();
        
    }
    
    public static void main(String[] args)
    {
                
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        Main main = new Main();
        GlobalScreen.addNativeKeyListener(main);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e)
    {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) 
        {
            ctrlKey = true;
            
        } 
        
        else if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE)
        {
            System.exit(0);
        }
        
        else if (e.getKeyCode() == NativeKeyEvent.VC_C)
        {
            try
            {
                Thread.sleep(200);
                if (ctrlKey)
                {
                    copiedStrings.getTextFromClip();
                }
                
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        else if (e.getKeyCode() == NativeKeyEvent.VC_UP)
        {
            Map list = copiedStrings.getList();
            
             if (--offset <= 0)
                {
                    offset = 0;
                }   
            
            frame.setLabelToList(list, offset);
        }
        
        
        else if (e.getKeyCode() == NativeKeyEvent.VC_DOWN) 
        {
            downKey = true;
            Map list = copiedStrings.getList();
            int listSize = list.size();
            
            if (ctrlKey) 
            {
                
                if(++offset > listSize+1)
                {
                    offset = listSize+1;
                }
                
                frame.showme(true);
                frame.setLabelToList(copiedStrings.getList(), offset);
                
            }
        }
        
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e)
    {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) 
        {
            ctrlKey = false;
            downKey = false;
            offset = copiedStrings.getList().size()-1;
            frame.showme(false);

        }
    }
    
}
