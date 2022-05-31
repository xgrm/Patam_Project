package IO;

import java.io.*;

public class TestIO implements IO{

    PrintWriter out;

    BufferedReader in;

    public TestIO(InputStream in,OutputStream out) {
        this.out = new PrintWriter(out);
        this.in = new BufferedReader( new InputStreamReader(in));
    }

    @Override
    public String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(String text) {
        out.println(text);
        out.flush();
    }

    @Override
    public float readVal() {
        return 0;
    }

    @Override
    public void write(float val) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void close() {
        out.close();
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
