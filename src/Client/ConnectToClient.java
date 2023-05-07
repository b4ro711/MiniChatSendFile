/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BARO
 */
public class ConnectToClient extends Thread{
     private Socket socket = null;
    private MyInfo info;
    private Message message;
    private MiniChatClientChatDlg clients[] = new MiniChatClientChatDlg[50];
    private int clientCount = 0;
    /*Messages*/
    private DataOutputStream streamOut = null;

    public ConnectToClient(MyInfo _info, Message _message) {
        info = _info;
        message = _message;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(InetAddress.getByName(message.toIP), message.toPort);
            streamOut = new DataOutputStream(socket.getOutputStream());
            streamOut.writeBytes(info.Username + " " + info.Port + " " + socket.getInetAddress().getHostAddress() + '\n');
            streamOut.flush();
            addThread();
        } catch (IOException ex) {
            Logger.getLogger(ConnectToClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addThread() {
        if (clientCount < clients.length) {
            clients[clientCount] = new MiniChatClientChatDlg(info, message, socket);
            clients[clientCount].open();
            clients[clientCount].start();
            clientCount++;
        }
    }
}
