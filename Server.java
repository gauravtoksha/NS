import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    static String status;
    private ObjectInputStream objectinputStream = null;
    private EncryptHandler encryptHandler;
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            this.status="Server started";
 
            this.status="Waiting for a client ...";
 
            socket = server.accept();
            this.status="Client accepted";
 
            // takes input from the client socket
          
            objectinputStream=new ObjectInputStream(socket.getInputStream());
 
 
            // reads message from client until "Over" is sent
            
                try
                {              
                    encryptHandler=(EncryptHandler)objectinputStream.readObject();
                    System.out.println("Received at server:"+encryptHandler.getEncrypted());
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
                catch(ClassNotFoundException i){
                    System.out.println(i);
                }
            
            this.status="Closing connection";
 
            // close connection
            socket.close();
            objectinputStream.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    public EncryptHandler getOutput(){
        return this.encryptHandler;
    }
    public static void main(String[] args) {
        Server server=new Server(9000);
    }
}