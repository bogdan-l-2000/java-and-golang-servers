import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        BasicServer myServer = new BasicServer(8081);
        myServer.addRoute(HttpMethod.GET, "/exampleRoute",
                (request) -> new HttpResponse.ResponseBuilder()
                        .setStatusCode(200)
                        .addHeader("Content-Type", "text/html")
                        .setContent("<html> <p> This is an example route... </p> </html>")
                        .build()
                );
        myServer.start();
    }
}
