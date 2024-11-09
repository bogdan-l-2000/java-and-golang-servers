import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class HttpDecoder {

    public static Optional<HttpRequest> decode(final InputStream inputStream) {
        return readMessage(inputStream).flatMap(HttpDecoder::buildRequest);
    }

    private static Optional<ArrayList<String>> readMessage(final InputStream inputStream) {
        try {
            if (!(inputStream.available() > 0)) {
                System.out.println("Input stream is unavailable");
                return Optional.empty();
            }

            final char[] inBuffer = new char[inputStream.available()];
            final InputStreamReader inReader = new InputStreamReader(inputStream);
            final int read = inReader.read(inBuffer);
            System.out.println(read);

            ArrayList<String> message = new ArrayList<>();

            try (Scanner sc = new Scanner(new String(inBuffer))) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    message.add(line);
                }
            }
            System.out.println(message);

            return Optional.of(message);
        } catch (Exception ignored) {
            System.out.println("Error with reading message");
            return Optional.empty();
        }
    }

    private static Optional<HttpRequest> buildRequest(ArrayList<String> message) {
        if (message.isEmpty()) {
            return Optional.empty();
        }

        // HTTP request first line: <HttpMethod> <path> <protocol version>
        String firstLine = message.getFirst();
        String[] httpInfo = firstLine.split(" ");

        if (httpInfo.length != 3) {
            return Optional.empty(); // Invalid request header
        }

        String protocolVersion = httpInfo[2];
        if (!protocolVersion.equals("HTTP/1.1")) {
            return Optional.empty(); // HTTP 1.0 version not considered
        }

        try {
            HttpRequest.RequestBuilder requestBuilder = new HttpRequest.RequestBuilder();
            requestBuilder.setHttpMethod(HttpMethod.valueOf(httpInfo[0]));
            requestBuilder.setUri(new URI(httpInfo[1]));
            return Optional.of(addRequestHeaders(message, requestBuilder));
        } catch (URISyntaxException | IllegalArgumentException e) {
                return Optional.empty();
        }
    }

    private static HttpRequest addRequestHeaders(final List<String> message, final HttpRequest.RequestBuilder requestBuilder) {
        final Map<String, ArrayList<String>> requestHeaders = new HashMap<>();

        if (message.size() > 1) {
            for (int i = 1; i < message.size(); i++) {
                String header = message.get(i);
                int colonIndex = header.indexOf(':');

                if (! (colonIndex > 0 && header.length() > colonIndex + 1)) {
                    break;
                }

                // Split by the colon: header name and value
                String headerName = header.substring(0, colonIndex);
                String headerValue = header.substring(colonIndex + 1);

                // Add header name and value to map if value exists, else add an empty list
                requestHeaders.compute(headerName, (key, values) -> {
                    if (values != null) {
                        values.add(headerValue);
                    } else {
                        values = new ArrayList<>();
                    }
                    return values;
                });
            }
        }

        requestBuilder.setRequestHeaders(requestHeaders);
        return requestBuilder.build();
    }
}
