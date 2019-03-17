import java.net.InetAddress;
import java.util.ArrayList;

public class RequestParser {

    private String fileRequested;
    private String requestType;
    private ArrayList<String> formData;
    private String contentLength;
    private String params;
    private String plainFileName;
    private InetAddress ip;

    public RequestParser(String s, InetAddress ip) {
        this.fileRequested = gFileRequested(s);
        this.requestType = gRequestType(s);
        this.formData = formData;
        this.contentLength = contentLength;
        this.params = params;
        this.plainFileName = plainFileName;
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







}
