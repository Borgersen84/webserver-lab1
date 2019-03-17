import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        int streamLength = 0;
        InputStream inputStream = null;

        boolean verbose = true;

        byte[] buffer = null;

        try {
            out = new PrintWriter(connect.getOutputStream());
            dataOut = new BufferedOutputStream(connect.getOutputStream());
            inputStream = connect.getInputStream();

            streamLength = inputStream.available();
            buffer = new byte[streamLength + 1000];
            inputStream.read(buffer, 0, streamLength);
            requestData = new String(buffer, 0, buffer.length);

            RequestParser req = new RequestParser(requestData, connect.getInetAddress());




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
