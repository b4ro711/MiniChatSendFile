/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author BARO
 */
public class Message {
    String [] text = null;
    public String toUsername = null;
    public int toPort = 0;
    public String toIP = null;
    public Message(String msg)
    {
        if(msg!=null){
            text = msg.split(" ");
            toUsername = text[0];
            toPort = Integer.parseInt(text[1]);
            toIP = text[2];
        }
    }
}
