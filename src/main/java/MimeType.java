public class MimeType {
    private String suffix;
    private String mime;


    public MimeType(String suffix) {

        if (suffix.contains("html")) {
            this.mime = "text/html";
        } else if (suffix.contains("css")) {
            this.mime = "text/css";
        } else if (suffix.contains("js") && !suffix.contains("json")) {
            this.mime = "text/javascript";
        } else if (suffix.contains("pdf")) {
            this.mime = "application/pdf";
        } else if (suffix.contains("jpg") || suffix.contains("jpeg")) {
            this.mime = "image/jpeg";
        } else if (suffix.contains("json")) {
            this.mime = "application/json";
        } else {
            this.mime = "";
        }
    }

    public String getMime() {
        return this.mime;
    }

}
