import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        BasicServer myServer = new BasicServer(8081);
        Route routes[] = Route.values();
        for (Route route : routes) {
            myServer.addRoute(route.getMethod(), route.getPath(),
                    (request) -> new HttpResponse.ResponseBuilder()
                            .setStatusCode(200)
                            .addHeader("Content-Type", "text/html")
                            .setContent(route.getContent())
                            .build()
            );
        }

        myServer.start();
    }
}
