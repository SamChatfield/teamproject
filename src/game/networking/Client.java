package game.networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Created by Daniel on 31/01/2017.
 */
public class Client {

    public Client(){
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("Usage: java Client <host> <port>");
            System.exit(0);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try{
            Socket outSocket = new Socket(host,port);

            ObjectInputStream objIn = new ObjectInputStream(outSocket.getInputStream());
            ObjectOutputStream objOut = new ObjectOutputStream(outSocket.getOutputStream());

            while(true){
                Object obj = objIn.readObject();
                System.out.println(obj == null);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());

        }

    }
}
