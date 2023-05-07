/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BARO
 */
public class ClientServer extends Thread {

    private ServerSocket server = null;
    private Socket socket = null;
    private Thread thread = null;
    private MiniChatClientChatDlg clients[] = new MiniChatClientChatDlg[50];
    private int clientCount = 0;
    private MyInfo info;
    private Message message;
    private BufferedReader inFromClient = null;
    
     public ClientServer(MyInfo _info){
        try {
            info = _info;
            server = new ServerSocket(info.Port);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
     @Override
   public void run(){
       while (thread != null){
            try {
                socket = server.accept();
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                message = new Message(inFromClient.readLine());
                addThread();
            } catch (IOException ex) {
                Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
   }
    @Override
   public void start()  {
	   if (thread == null){
		   thread = new Thread(this);
	       thread.start();
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
    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            MiniChatClientChatDlg toTerminate = clients[pos];
            if (pos < clientCount - 1) {
                for (int i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            toTerminate.close();
        }
    }
    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }
}
