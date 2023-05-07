/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author BARO
 */
public class Client implements Runnable {

    // Chú ý khai báo
    private Socket socket = null;
    private ClientThread client = null;
    private Thread thread = null;
    private Thread clientserverthread = null;
    /*Username , Port*/
    private MyInfo info;
    private DefaultListModel module = new DefaultListModel();
    /*Message*/
    private BufferedReader inFromUser = null;
    private DataOutputStream streamOut = null;
    // Chú ý khai báo

    public Client(String serverName, int serverPort, MyInfo _info) {
        try {
            /*Connect*/
            socket = new Socket(InetAddress.getByName(serverName), serverPort);
            info = _info;
            MiniChatClientDlg dialog = new MiniChatClientDlg(info, module, socket);
            start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {
        try {
            streamOut.writeBytes(info.Username + " " + info.Port + " " + socket.getInetAddress().getLocalHost().getHostAddress() + '\n');
            streamOut.flush();
            if (clientserverthread == null) {
                clientserverthread = new ClientServer(info);
                clientserverthread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            stop();
        }
    }

    public void handle(String msg) {
        if (msg.equals("Refesh")) {
            module.removeAllElements();
        } else if (msg != null) {
            if (!module.contains(msg)) {
                module.addElement(msg);
            }
        }
    }

    public void start() throws IOException {
        streamOut = new DataOutputStream(socket.getOutputStream());
        if (thread == null) {
            client = new ClientThread(this, socket);
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        try {
            if (thread != null) {
                thread.stop();
                thread = null;
            }
            if (inFromUser != null) {
                inFromUser.close();
            }
            if (streamOut != null) {
                streamOut.close();
            }
            if (socket != null) {
                socket.close();
            }
            client.close();
            client.stop();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
