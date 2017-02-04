package game.networking;


import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Daniel on 31/01/2017.
 */
public class Server {

    public Server(){
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("Usage: java Server <host> <port>");
            System.exit(0);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try{
            ServerSocket outSocket = new ServerSocket(port);
            Socket clientSocket = outSocket.accept();


            ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());

            while(true){
                Thread.sleep(1000);
                objOut.writeObject(new SampleObject());
                objOut.flush();
                System.out.println("Sent object");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());

        }

    }
}
