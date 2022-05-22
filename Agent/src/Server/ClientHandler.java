package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
    void handle(InputStream in, OutputStream out);
}
