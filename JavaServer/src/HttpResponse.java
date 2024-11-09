import java.util.*;

public class HttpResponse {
    private final Map<String, ArrayList<String>> responseHeaders;
    private final int statusCode;

    private final Optional<Object> content;

    private HttpResponse(final Map<String, ArrayList<String>> responseHeaders, final int statusCode, final Optional<Object> content) {
        this.responseHeaders = responseHeaders;
        this.statusCode = statusCode;
        this.content = content;
    }

    public Map<String, ArrayList<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Optional<Object> getContent() {
        return content;
    }

    public static class ResponseBuilder {
        private final Map<String, ArrayList<String>> responseHeaders;
        private int statusCode;

        private Optional<Object> content;

        public ResponseBuilder() {
            responseHeaders = new HashMap<>();

            content = Optional.empty();
        }

        public ResponseBuilder setStatusCode(final int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseBuilder addHeader(final String name, final String value) {
            responseHeaders.put(name, new ArrayList<>(List.of(value)));
            return this;
        }

        public ResponseBuilder setContent(final Object content) {
            if (content != null) {
                this.content = Optional.of(content);
            }
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(responseHeaders, statusCode, content);
        }
    }
}
