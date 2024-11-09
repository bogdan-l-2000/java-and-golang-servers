import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class ResponseWriter {
    public static void writeResponse(final BufferedWriter outputStream, final HttpResponse response) {
        try {
            final int statusCode = response.getStatusCode();
            final String statusCodeMeaning = HttpStatusCode.getStatusFromCode(statusCode).getDescription();
            final ArrayList<String> responseHeaders = buildHeaderStrings(response.getResponseHeaders());

            outputStream.write("HTTP/1.1 " + statusCode + " " + statusCodeMeaning + "\r\n");

            for (String header : responseHeaders) {
                outputStream.write(header);
            }

            final Optional<String> contentString = response.getContent().flatMap(ResponseWriter::getResponseString);
            if (contentString.isPresent()) {
                final String encodedString = new String(contentString.get().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                outputStream.write("Content-Length: " + encodedString.getBytes().length + "\r\n");
                outputStream.write("\r\n");
                outputStream.write(encodedString);
            } else {
                outputStream.write("\r\n");
            }
        } catch (Exception ignored) {
        }
    }

    private static ArrayList<String> buildHeaderStrings(final Map<String, ArrayList<String>> responseHeaders) {
        final ArrayList<String> responseHeadersList = new ArrayList<>();

        responseHeaders.forEach((name, values) -> {
            final StringBuilder combinedValues = new StringBuilder();
            values.forEach(combinedValues::append);
            combinedValues.append(";");

            responseHeadersList.add(name + ": " + combinedValues + "\r\n");
        });

        return responseHeadersList;
    }

    private static Optional<String> getResponseString(final Object content) {
        if (content instanceof String) {
            try {
                return Optional.of(content.toString());
            } catch (Exception ignored) {
            }
        }
        return Optional.empty();
    }
}
