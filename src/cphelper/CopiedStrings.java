
package cphelper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stas
 */
public class CopiedStrings
{
    private Map<Integer, String> lista;
    
    public CopiedStrings()
    {
        lista = new HashMap<>();
//        lista.put(1, "test1");
//        lista.put(2, "test2");
//        lista.put(3, "test3");
//        lista.put(4, "test4");
    }
    
    public Map getList()
    {
        return lista;
    }
    
    public void getTextFromClip()
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         
        Transferable transferable = clipboard.getContents(null);
         
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
          // Get clipboard contents and cast to String
            String data;
            int listaSize = lista.size();
            
            try {
                data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                
                if (listaSize == 0)
                {
                    lista.put(listaSize+1, data);
                }
                else
                {
                    if (lista.get(listaSize).equals(data) == false)
                    {
                        lista.put(listaSize+1, data);
                    }
                }
                 
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(CopiedStrings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setTextInClip(String text)
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
        
    }
    
    public void clearList()
    {
        lista.clear();
    }
}
