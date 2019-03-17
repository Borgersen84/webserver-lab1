import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpServer implements Runnable {

    private Socket connect;

    public HttpServer(Socket connect) {
        this.connect = connect;
    }

    @Override
    public void run() {

        PrintWriter out = null;

        BufferedOutputStream dataOut = null;

        String requestData = "";

        boolean verbose = true;

        byte[] buffer = null;
    }
}
