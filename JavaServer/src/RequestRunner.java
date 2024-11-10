import java.io.IOException;

public interface RequestRunner {
    HttpResponse run(HttpRequest request) throws IOException;
}
