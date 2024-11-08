import java.io.BufferedWriter;
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
        } catch (Exception ignored) {
        }
    }

    private static ArrayList<String> buildHeaderStrings(final Map<String, ArrayList<String>> responseHeaders) {
        final ArrayList<String> responseHeadersList = new ArrayList<>();

        return responseHeadersList;
    }

    private static Optional<String> getResponseString(final Object content) {
        return Optional.empty();
    }
}
