package IO;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class TelnetIO implements IO{
    PrintWriter out;
    Scanner in;

    public TelnetIO(InputStream in, OutputStream out) {
        this.out = new PrintWriter(out);
        this.in = new Scanner(in);

    }

    @Override
    public String readLine() {
        return in.nextLine();
    }

    @Override
    public void write(String text) {
        out.print(text+"\r\n");
        out.flush();
    }

    @Override
    public float readVal() {
        if(in.hasNextFloat())
            return in.nextFloat();
        else try {
            throw new Exception("There is no Float");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(float val) {
        out.print(val+"\r\n");
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
