import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileRetriever {

    public static byte[] getFile(String fileRequested) throws IOException {
        File file = new File(fileRequested.substring(1));


        if (fileRequested.endsWith("/")) {
            fileRequested = "index.html";
            file = new File(fileRequested);
        }

        else if(!file.exists()) {
           fileRequested = "404.html";
           file = new File(fileRequested);
        }


        int fileLength = (int) file.length();


        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }


        return fileData;
    }


}
