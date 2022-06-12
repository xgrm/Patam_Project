package model.utils.IO;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BackEndIO implements IO {
    PrintWriter out;
    Scanner in;

    public BackEndIO(InputStream in, OutputStream out) {
        this.out = new PrintWriter(out);
        this.in = new Scanner(in);

    }
    @Override
    public String readLine() {
        if(in.hasNext())
            return in.nextLine();
        else return "null";
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
