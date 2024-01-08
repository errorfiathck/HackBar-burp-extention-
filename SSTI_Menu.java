
package burp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;

/**
 *
 * @author errorfiathck
 */
public class SSTI_Menu extends JMenu {
    public BurpExtender myburp;
    public String[] SSTI_MenuItems = {"{{7*7}}", "${7*7}", "<%= 7*7 %>","${{7*7}}", "#{7*7}", "*{7*7}"};
    
    SSTI_Menu(BurpExtender burp){
        this.setText("SSTI");
        this.myburp = burp;
        Methods.add_MenuItem_and_listener(this, SSTI_MenuItems, new WebShellItemListener(myburp));
    }
}


class WebShellItemListener implements ActionListener {

    BurpExtender myburp;
    WebShellItemListener(BurpExtender burp) {
        myburp = burp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedIndex = myburp.context.getSelectionBounds();
        IHttpRequestResponse req = myburp.context.getSelectedMessages()[0];
        byte[] request = req.getRequest();
        byte[] param = new byte[selectedIndex[1]-selectedIndex[0]];
        System.arraycopy(request, selectedIndex[0], param, 0, selectedIndex[1]-selectedIndex[0]);
        String selectString = new String(param);
        String action = e.getActionCommand();
        byte[] newRequest = do_SSTI(request, selectString, action, selectedIndex);
        req.setRequest(newRequest);
    }
    
    public byte[] do_SSTI(byte[] request, String selectedString, String action, int[] selectedIndex){
        switch(action){
            case "SSTI 1":
                selectedString =    "{{7*7}}";
                break;
            case "SSTI 2":
                selectedString =    "${7*7}";
                break;
            case "SSTI 3":
                selectedString =    "<%= 7*7 %>";
                break;
            case "SSTI 4":
                selectedString =    "${{7*7}}";
                break;
            case "SSTI 5":
                selectedString =    "#{7*7}";
                break;
            case "SSTI 6":
                selectedString =    "*{7*7}";
                break;
            default:
                break;
        }
        return Methods.do_modify_request(request, selectedIndex, selectedString);
    }
    
    

}