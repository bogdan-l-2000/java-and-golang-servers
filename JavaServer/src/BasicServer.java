//import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BasicServer {
    private final Map<String, RequestRunner> routes;
    private final ServerSocket socket;
    private final Executor threadPool;
    private HttpHandler handler;

    public BasicServer(int port) throws IOException {
        routes = new HashMap<>();
        threadPool = Executors.newFixedThreadPool(50);
        socket = new ServerSocket(port);
    }

    public void addRoute(HttpMethod opCode, String route, RequestRunner requestRunner) {
        routes.put(opCode.name().concat(route), requestRunner);
    }

    public void start() throws IOException {
        handler = new HttpHandler(routes);
        while (true) {
            Socket clientConnection = socket.accept();
            handleConnection(clientConnection);
        }
    }

    private void handleConnection(Socket clientConnection) {
        Runnable httpRequestRunner = () -> {
            try {
                handler.handleConnection(clientConnection.getInputStream(), clientConnection.getOutputStream());
            } catch (IOException ignored) {
            }
        };
        threadPool.execute(httpRequestRunner);
    }
}
