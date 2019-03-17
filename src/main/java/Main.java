import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    static final int PORT = 8080;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            HttpServer myServer = new HttpServer(serverSocket.accept());
            Thread thread = new Thread(myServer);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

