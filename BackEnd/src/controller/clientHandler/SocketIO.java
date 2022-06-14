package controller.clientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class SocketIO implements IO{
    Scanner in;
    PrintWriter out;

    public SocketIO(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintWriter(out);
    }

    @Override
    public String readLine() {
        return in.nextLine();
    }

    @Override
    public void write(String text) {
        out.println(text);
        out.flush();
    }

    @Override
    public float readVal() {
        return in.nextFloat();
    }

    @Override
    public void write(float val) {
        out.println(val);
        out.flush();
    }

    @Override
    public boolean hasNext() {
        return in.hasNext();
    }

    @Override
    public void close() {
        in.close();
        out.close();
    }
}
