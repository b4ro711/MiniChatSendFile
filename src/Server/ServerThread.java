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

public class ServerThread extends Thread {

    private Server server = null;
    private Socket socket = null;
    private int ID = -1;
    private BufferedReader inFromClient = null;
    private DataOutputStream streamOut = null;

    public ServerThread(Server _server, Socket _socket) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
    }

    @SuppressWarnings("deprecation")
    public void send(String msg) {
        try {
            streamOut.writeBytes(msg);
            streamOut.flush();
        } catch (IOException ioe) {
            System.out.println(ID + " ERROR sending: " + ioe.getMessage());
            server.remove(ID);
            stop();
        }
    }

    public int getID() {
        return ID;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        System.out.println("Server Thread " + ID + " running.");
        String disc = null;
        while (true) {
            try {
                disc = inFromClient.readLine();
                if (!disc.equals("Disconnect")) {
                    server.handle(ID, disc);
                } else {
                    server.remove(ID);
                }
            } catch (IOException ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();
            }
        }
    }

    public void open() throws IOException {
        inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (inFromClient != null) {
            inFromClient.close();
        }
        if (streamOut != null) {
            streamOut.close();
        }
    }
}
