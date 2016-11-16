/**
 * CODE OBTAINED FROM AN ONLINE RESOURCE
 * MODIFIED FOR USE WITH CURRENT ASG
 */
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SocketServerMonitor {

    //Server socket
    private ServerSocket _serverSocket = null;

    //Client socket
    private Socket _clientSocket = null;


    private static SocketServerMonitor _singletonInstance = null;

    /**
     * Hidden constructor prevents instantiation of this type
     */
    private SocketServerMonitor() {
    }

    /**
     * Creates, if the singleton instance hasn't been created before and returns
     * the single instance of SocketServerMonitor
     *
     * @return Singleton Instance of SocketServerMonitor
     */
    public static SocketServerMonitor getSingleSocketServerMonitor() {
        if (_singletonInstance == null) {
            _singletonInstance = new SocketServerMonitor();
        }
        return _singletonInstance;
    }

    /**
     * Starts monitoring the server socket. Blocks until a client connects on
     * this socket
     *
     * @param serverEndPoint Address to which the server socket is bound
     */
    public void start(SocketAddress serverEndPoint) {
        if (_serverSocket != null) {
            stop();
        } else {
            try {
                _serverSocket = new ServerSocket();
                _serverSocket.bind(serverEndPoint);
                System.out.println("Listening for connections at " + serverEndPoint.toString());
                _clientSocket = _serverSocket.accept();
            } catch (IOException ex) {
                System.out.println("Error: Unable to Start Server Socket!\n\t" + ex);
            }
        }
    }

    /**
     * Stop monitoring. Closes server and client sockets
     */
    public void stop() {
        if (_serverSocket != null) {
            try {
                System.out.println("Stopping Server Socket Monitor");
                _serverSocket.close();
                _clientSocket.close();
                _serverSocket = null;
            } catch (IOException ex) {
                System.out.println("Error: Unable to stop server socket monitor! \n" + ex);
            }
        }
    }

    private Document acceptXMLFromClient() {
        Document xmlDoc = null;
        try {
            BufferedReader clientReader = new BufferedReader(
                    new InputStreamReader(_clientSocket.getInputStream()));
            // block until client sends us a message
            String clientMessage = clientReader.readLine();
            // read xml message
            System.out.println(clientMessage);
            InputSource is = new InputSource(new StringReader(clientMessage));
            xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            System.out.println("Error: Unable to read XML from client!\n\t" + ex);
        }
        return xmlDoc;
    }

    /**
     * Waits on client messages. Attempts to read client messages as a XML
     * document and sends a receipt notice to the client when a message is
     * received. Attempts to read the Command tag from the client and queues up
     * the client command for execution
     */
    private void listenToClient() {
        assert _clientSocket != null : "Invalid Client Socket!";
        // open a writer to client socket
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(_clientSocket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Error: Unable to write to client socket!\n\t" + ex);
        }
        while (true) {
            // Read xml from client
            Document xmlDoc = acceptXMLFromClient();

            DOMBuilder builder = new DOMBuilder();
            org.jdom2.Document myDoc = builder.build(xmlDoc);
            try {
                FileWriter writer = new FileWriter("src//serialization//output.xml");
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.output(myDoc, writer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            stop();
            System.exit(0);
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        // Make sure command line arguments are valid
  
        try {
            InetSocketAddress test = new InetSocketAddress(12345);
            getSingleSocketServerMonitor().start(new InetSocketAddress("192.168.1.150", 12345));
            getSingleSocketServerMonitor().listenToClient();
        } catch (NumberFormatException ex) {
            System.out.println(ex);
        }
    }
}
