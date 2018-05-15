import java.net.*;
import java.io.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private ObjectInputStream objectinputStream = null;
    private ObjectOutputStream objectoutputStream = null;
    static String status;
    // constructor to put ip address and port
    public Client(String address, int port,EncryptHandler encryptHandler)
    {
        // establish a connection
        try
        {
            status="Connecting....";
            socket = new Socket(address, port);
            status="Connected";
            
            // takes input from terminal
           // objectinputStream= new ObjectInputStream(System.in);
 
            // sends output to the socket
            objectoutputStream=new ObjectOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
 
        try
            {
                System.out.println("value at in client object:"+encryptHandler.getEncrypted());
                objectoutputStream.writeObject(encryptHandler);
                status="Sending encrypted message..";
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        
 
        // close the connection
        try
        {
            status="Closing connection..";
            objectoutputStream.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}