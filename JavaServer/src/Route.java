import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Route {
    EXAMPLE(HttpMethod.GET,"/exampleRoute", ServerPath.HTML_DIRECTORY.getDirectory() + "exampleRoute.html");

    private final HttpMethod method;
    private final String path;
    private final String source;
    private String content;

    Route(HttpMethod method, String path, String source){
        this.method = method;
        this.path = path;
        this.source = source;
        this.content = "";
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getContent() throws IOException {
        String outputContent = "";

        try {
            FileReader fileReader = new FileReader(this.source);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[10];
            while (reader.read(buffer) != -1) {
                stringBuilder.append(new String(buffer));
                buffer = new char[10];
            }
            reader.close();

            outputContent = stringBuilder.toString();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            outputContent = "<html><p>File is currently unavailable.</p></html>";
        }
        this.content = outputContent;
        return this.content;
    }

}
