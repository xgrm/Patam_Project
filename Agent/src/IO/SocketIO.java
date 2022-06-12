package IO;

import java.io.*;
import java.util.Scanner;

public class SocketIO implements IO{

    PrintWriter out;

    Scanner in;

    public SocketIO(InputStream in, OutputStream out) {
        this.out = new PrintWriter(out);
        this.in = new Scanner(in);
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
    out.close();
    in.close();
    }
}
