import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

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

            HttpRequest req = new HttpRequest(requestData, connect.getInetAddress());

            if (verbose) {
                System.out.println();
                System.out.println("CONTENT-LENGTH: " + req.getContentLength());
                System.out.println("POST-PARAMS: " + req.getParams());
                System.out.println("FORMDATA: " + req.getFormData());
                System.out.println("REQUEST: " + req.getRequestType());
                System.out.println("URL: " + req.getFileRequested());
                System.out.println();
            }

            if (req.getFileRequested().contains("import")) {

                JSONObject jsonObj = new JSONObject();
                if (!req.getParams().contains("name") || !req.getParams().contains("location")) {

                    out.println("HTTP/1.1 400: Bad Request");
                    out.println("Server: Java HTTP Server from Borgersen");
                    out.println("Date: " + new Date());

                } else {
                    String[] stage1 = req.getParams().split("=");
                    String[] stage2 = stage1[1].split("&");

                    try {

                        jsonObj.put("name", stage2[0]);
                        jsonObj.put("location", stage1[2]);

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    byte[] fileData = jsonObj.toString().getBytes(StandardCharsets.UTF_8);
                    int fileLength = fileData.length;

                    byte[] bytes = fileData;
                    File file = new File("demo.json");

                    try {

                        OutputStream os = new FileOutputStream(file);
                        os.write(bytes);
                        System.out.println("Write bytes to file.");

                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // send HTTP Headers
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Java HTTP Server from Borgersen");
                    out.println("Date: " + new Date());
                    out.println("Content-type: " + "application/json");
                    out.println("Content-length: " + fileLength);
                    out.println(); // blank line between headers and content, very important !
                    out.flush(); // flush character output stream buffer

                    dataOut.write(fileData, 0, fileLength);
                    dataOut.flush();

                }
            }

            if (req.getRequestType() != "INVALID") {

                byte[] fileData = FileRetriever.getFile(req.getFileRequested());
                int fileLength = fileData.length;
                // send HTTP Headers
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Java HTTP Server from Borgersen");
                out.println("Date: " + new Date());
                out.println("Content-type: " + new MimeType(req.getFileRequested()).getMime());
                out.println("Content-length: " + fileLength);
                out.println(); // blank line between headers and content, very important !
                out.flush(); // flush character output stream buffer

                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
                if (dataOut != null) {
                    dataOut.close();
                }
                connect.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

        }

    }
}








