import java.net.Socket;

public class HttpServer implements Runnable {

    private Socket connect;

    public HttpServer(Socket connect) {
        this.connect = connect;
    }

    @Override
    public void run() {

    }
}
