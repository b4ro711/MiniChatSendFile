/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author BARO
 */
import java.net.*;
import java.io.*;

public class Server implements Runnable { // Alt Enter - Implement All abstract medthods

    private ServerThread clients[] = new ServerThread[50];
    public ServerSocket server = null;
    private Thread thread = null;
    private int clientCount = 0;
    private String Useronline[] = new String[100];

    public Server(ServerSocket _server, int port) {
        server = _server;
        System.out.println("Đang mở port [" + port + "] vui lòng chờ  ...");
        //server = new ServerSocket(port);
        System.out.println("Server đang khởi động: " + server);
        start();

    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                System.out.println("Đang đợi phản hồi từ phía Client ...");
                addThread(server.accept());
            } catch (IOException ioe) {
                System.out.println("Server accept error: " + ioe);
                stop();
            }
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }

    }

    @SuppressWarnings("deprecation")
    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
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

    public synchronized void handle(int ID, String input) {
        Useronline[clientCount - 1] = input;
        int j = 0;
        int i = 0;
        if (clientCount != 1) {
            for (i = 0; i < clientCount; i++) {
                for (j = 0; j < clientCount; j++) {
                    if (i != j) {
                        clients[i].send(Useronline[j] + '\n');
                    }
                }
            }
        }
    }

    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        int i = 0;
        int j = 0;
        if (pos >= 0) {
            ServerThread toTerminate = clients[pos];
            System.out.println("Removing client thread " + ID + " at " + pos);
            if (pos < clientCount - 1) {
                for (i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            if (pos < clientCount) {
                for (i = pos + 1; i < clientCount + 1; i++) {
                    Useronline[i - 1] = Useronline[i];
                }
            }
            for (i = 0; i < clientCount; i++) {
                clients[i].send("Refesh" + '\n');
                for (j = 0; j < clientCount; j++) {
                    if (i != j) {
                        clients[i].send(Useronline[j] + '\n');
                    }
                }
            }
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                System.out.println("Error closing thread: " + ioe);
            }
            toTerminate.stop();
        }
    }

    private void addThread(Socket socket) {
        if (clientCount < clients.length) {  //System.out.println("Client accepted: " + socket);
            clients[clientCount] = new ServerThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                System.out.println("Error opening thread: " + ioe);
            }
        } else {
            System.out.println("Client refused: maximum " + clients.length + " reached.");
        }
    }

}
