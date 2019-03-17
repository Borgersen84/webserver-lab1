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
        this.fileRequested = fileRequested;
        this.requestType = requestType;
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
}
