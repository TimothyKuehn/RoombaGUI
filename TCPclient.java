package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * A TCP client that listens for incoming messages in a separate thread.
 *
 */
public class TCPclient {
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public TCPclient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void sendChar(char message) {
        out.write(message);
        out.flush();
    }

    public void startListening() {
        new Thread(() -> {
            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null) {
                    Main.processString(inputLine);
                    Main.updateGUI();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
