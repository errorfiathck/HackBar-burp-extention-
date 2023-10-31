package burp;

import burp.BurpExtender;
import javax.swing.JMenu;

/**
 *
 * @author errorfiathck
 */
public class Decoder_Encoder_Menu extends JMenu {
    public BurpExtender myburp;
    
    Decoder_Encoder_Menu(BurpExtender burp){
        this.setText("Decoder/Encoder");
        this.myburp = burp;
        this.Create_Reverse_Shell_Menu();
    }
    
    public void Create_Reverse_Shell_Menu(){}
}
