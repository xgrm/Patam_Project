package model.Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
    public void handle(InputStream in, OutputStream out);
    public  void close();
}
