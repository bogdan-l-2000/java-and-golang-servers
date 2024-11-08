import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final URI uri;

    private final Map<String, ArrayList<String>> requestHeaders;

    private HttpRequest(HttpMethod opCode,
                        URI uri,
                        Map<String, ArrayList<String>> requestHeaders) {
        this.httpMethod = opCode;
        this.uri = uri;
        this.requestHeaders = requestHeaders;

    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, ArrayList<String>> getRequestHeaders() {
        return requestHeaders;
    }

    public static class RequestBuilder {
        private HttpMethod httpMethod;
        private URI uri;
        private Map<String, ArrayList<String>> requestHeaders;

        public RequestBuilder() {

        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        public void setUri(URI uri) {
            this.uri = uri;
        }

        public void setRequestHeaders(Map<String, ArrayList<String>> requestHeaders) {
            this.requestHeaders = requestHeaders;
        }

        public HttpRequest build() {
            return new HttpRequest(httpMethod, uri, requestHeaders);
        }
    }
}
