import java.io.*;
import java.util.Map;
import java.util.Optional;


public class HttpHandler {
    private final Map<String, RequestRunner> routes;

    public HttpHandler(final Map<String, RequestRunner> routes) {
        this.routes = routes;
    }

    public void handleConnection(final InputStream inputStream, final OutputStream outputStream) {
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        try {
            Optional<HttpRequest> request = HttpDecoder.decode(inputStream);
            request.ifPresentOrElse((r) -> handleRequest(r, bufferedWriter), () -> handleInvalidRequest(bufferedWriter));
            bufferedWriter.close();
            inputStream.close();
        } catch (IOException ignored) {

        }

    }

    private void handleRequest(final HttpRequest request, final BufferedWriter bufferedWriter) {
        final String routeKey = request.getHttpMethod().name().concat(request.getUri().getRawPath());

        try {
            if (routes.containsKey(routeKey)) {
                ResponseWriter.writeResponse(bufferedWriter, routes.get(routeKey).run(request));
            } else {
                ResponseWriter.writeResponse(bufferedWriter, new HttpResponse.ResponseBuilder().setStatusCode(404).setContent("Route Not Found...").build());
            }
        } catch (IOException ignored) {

        }

    }

    private void handleInvalidRequest(final BufferedWriter bufferedWriter) {
        HttpResponse notFoundResponse = new HttpResponse.ResponseBuilder().setStatusCode(400).setContent("Bad Request...").build();
        ResponseWriter.writeResponse(bufferedWriter, notFoundResponse);
    }
}
