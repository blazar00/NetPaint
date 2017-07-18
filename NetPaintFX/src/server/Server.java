package server;

/** 
 * This class is a multi-threaded server that allows manu clients to connect.
 * The clients are expected to write PaintObjects to this server.
 * The clients are also expected to read the Vector<PaintObject> object
 * when they connect and everytime any Client draws a new PaintObject.
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import java.net.Socket;
import java.util.Vector;

import model.PaintObject;

public class Server {
  public static int PORT_NUMBER = 4011;
  private Vector<PaintObject> allPaintObjects;
  private Vector<ObjectOutputStream> allClientsOutputStreams;

  public static void main(String args[]) {
    new Server();
  }

  // Start up this server to listen on port PORT_NUMBER
  public Server() {
    allPaintObjects = new Vector<PaintObject>();
    allClientsOutputStreams = new Vector<ObjectOutputStream>();
    ServerSocket myServerSocket = null; // client request source

    try {
      myServerSocket = new ServerSocket(PORT_NUMBER);
      while (true) {
        // Wait for a new Client
        Socket oneClient = myServerSocket.accept();
        // Get the Client's input and output stream to read from and write to the Server
        ObjectInputStream inputFromClient = new ObjectInputStream(oneClient.getInputStream());
        ObjectOutputStream outputToClient = new ObjectOutputStream(oneClient.getOutputStream());
        // Give this new Client the list of all PaintObject
        outputToClient.writeObject(this.allPaintObjects);
        // Add this new client to the list of clients so everyone can be
        // written the list of paint objects every time a client adds one.
        allClientsOutputStreams.add(outputToClient);

        // Start the loop that reads any Client's writeObject in the background in a 
        // different Thread so this program can also wait for new Client connections.
        LoopToReadClientsInput looper = new LoopToReadClientsInput(inputFromClient);
        Thread t = new Thread(looper);
        t.setDaemon(true);
        t.start();
      }

    } catch (IOException e) {
      System.out.println("IOxception In Server while accepting new clients");
    }
  }

  public class LoopToReadClientsInput implements Runnable {

    ObjectInputStream reader;

    public LoopToReadClientsInput(ObjectInputStream inputFromClient) {
      reader = inputFromClient;
    }

    @Override
    public void run() {
      PaintObject aPaintObject = null;
      try {
        while (true) {
          aPaintObject = (PaintObject) reader.readObject();
          allPaintObjects.add(aPaintObject);
          tellEveryone();
        }
      } catch (ClassNotFoundException e) {
        System.out.println("Server.67: Class not found");
      } catch (IOException e) {
        System.out.println("Client closed");
      }
    }

    public void tellEveryone() {
      for (ObjectOutputStream client : allClientsOutputStreams) {
        try {
          // The serialized object will not change when a new PaintObject
          // is added to the Vector unless you reset the ObjectOutputStream. 
          client.reset(); // Need this to avoid sending the same serialized object
          client.writeObject(allPaintObjects);
        } catch (IOException e) {
          System.out.println("A Client was closed");
        }
      }
    }
  } // end class LoopToReadClientsInputToServerInNewThread

} // end class ThreadedServer