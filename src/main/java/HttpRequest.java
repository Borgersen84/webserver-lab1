import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequest {

    private String fileRequested;
    private String requestType;
    private ArrayList<String> formData;
    private String contentLength;
    private String params;
    private String plainFileName;
    private InetAddress ip;

    public HttpRequest(String s, InetAddress ip) {
        this.fileRequested = gFileRequested(s);
        this.requestType = gRequestType(s);
        this.formData = gFormData(s);
        this.contentLength = gContentLength(s);
        this.params = gParams(s);
        this.ip = ip;
    }

    public String getFileRequested() {
        return fileRequested;
    }

    public String getRequestType() {
        return requestType;
    }

    public ArrayList<String> getFormData() {
        return formData;
    }

    public String getContentLength() {
        return contentLength;
    }

    public String getParams() {
        return params;
    }

    public String getPlainFileName() {
        return plainFileName;
    }

    public InetAddress getIp() {
        return ip;
    }

    public String[] getArr(String s){
        return s.split("\\r?\\n");
    }

    public String gFileRequested(String s){
        String fileRequested = null;
        if(getArr(s)[0].contains("/")){
            String[] stage1 = getArr(s)[0].split(" ");
            fileRequested = stage1[1];
        }
        return fileRequested;
    }

    public String gRequestType(String s) {
        String type;
        if (getArr(s)[0].toUpperCase().contains("GET")) {
            type = "GET";
        } else if (getArr(s)[0].toUpperCase().contains("HEAD")) {
            type = "HEAD";
        } else if (getArr(s)[0].toUpperCase().contains("POST")) {
            type = "POST";
        } else {
            type = "INVALID";
        }
        return type;
    }

    public static String gtForm(ArrayList<String> requestData) {
        String form = "";
        for (String s : requestData) {

            if (s.contains("Content-Type")) {
                String[] stage1 = s.split("Content-Type: ");
                form = stage1[1];
            }
        }

        return form;
    }

    public String gParams(String sk) {
        String params = "";
        boolean parseParams = false;
        String requestData[] = getArr(sk);

        for (String s : requestData) {
            if (s.contains("-----")) {
                break;
            }
            if (parseParams == true && !s.isEmpty()) {
                params += s;
            }
            if (s.length() == 0) {
                parseParams = true;
            }

        }
        if (!params.contains("=")) {
            params = "";
        }

        return params.trim();
    }

    public static ArrayList<String> gFileData() {

        return null;
    }

    public static List<String> gValidFileTypes() {
        return Stream.of(".html", ".css", ".js", ".svg", ".txt", ".json").collect(Collectors.toList());
    }

    public static boolean isKeepAlive(ArrayList<String> requestData) {
        boolean alive = false;
        for (String s : requestData) {
            if (s.contains("Connection: keep-alive")) {
                alive = true;
                break;
            }
        }
        return alive;
    }

    public static String gFileName(ArrayList<String> requestData) {
        String fileName = "-1";
        for (String s : requestData) {

            if (s.contains("filename")) {
                String[] stage1 = s.split("filename=\"");
                String[] stage2 = stage1[1].split("\"");
                fileName = stage2[0];
            }
        }

        return fileName;
    }

    public String gContentLength(String sk) {
        String contentLength = "-1";
        String requestData[] = getArr(sk);
        for (String s : requestData) {

            if (s.contains("Content-Length")) {
                String[] stage1 = s.split("Content-Length: ");
                contentLength = stage1[1];
            }
        }
        if (contentLength.contentEquals("-1")) {
            contentLength = "0";
        }

        return contentLength;
    }

    public ArrayList<String> gFormData(String sk) {
        ArrayList<String> formData = new ArrayList<String>();
        String fileName = "-1";
        int fileBegins = -1;
        int fileEnd = -1;
        ArrayList<String> requestData = new ArrayList<>(Arrays.asList(getArr(sk)));

        for (String s : requestData) {

            if (s.contains("filename")) {
                String[] stage1 = s.split("filename=\"");
                String[] stage2 = stage1[1].split("\"");
                fileName = stage2[0];
                fileBegins = requestData.indexOf(s) + 2;
            }

            if (s.contains("-----")) {
                fileEnd = requestData.indexOf(s) - 1;
            }
        }

        int counter = 0;
        if (fileBegins != -1 && fileEnd != -1) {

            for (String s : requestData) {
                if (counter >= fileBegins && counter <= fileEnd) {

                    formData.add(s);
                }

                counter++;
            }

        }
        this.plainFileName = fileName;

        return formData;

    }

    @Override
    public String toString() {
        String file = "";
        if (!this.formData.isEmpty() && !this.plainFileName.equals("-1")) {
            file = "FILE POSTED: " + this.plainFileName + ", ";
        }
        if (!this.params.equals("")) {
            file = "POST PARAMS: " + this.params + ", ";
        }

        Date d = new Date();

        return d.toString() + " TYPE: " + this.getRequestType() + ", URL: " + this.fileRequested + ", "
                + file + "CLIENT IP: " + this.ip;
    }









}
