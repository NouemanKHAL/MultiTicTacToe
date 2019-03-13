package Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nouemankhal
 */
public class Connection {

    static private Socket socket;
    static private ObjectInputStream input;
    static private ObjectOutputStream output;
    static private boolean isConnected = false;

    public Connection() {

    }

    public Connection(String ipAddress, int port) throws IOException {
        socket = new Socket(ipAddress, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
        input = new ObjectInputStream(socket.getInputStream());
        isConnected = true;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static boolean isConnected() throws IOException {
        return socket.isConnected();
    }

    public static ObjectInputStream getInput() {
        return input;
    }

    public static ObjectOutputStream getOutput() {
        return output;
    }

}
