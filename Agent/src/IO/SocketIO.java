package IO;

import view.SerializableCommand;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Scanner;

public class SocketIO implements IO{
    ObjectInputStream in;
    ObjectOutputStream out;
    Scanner scanner;

    public SocketIO(InputStream in, OutputStream out) {
        this.scanner = new Scanner(in);
        try {
            this.in = new ObjectInputStream(in);
            this.out = new ObjectOutputStream(out);
        } catch (IOException e) {throw new RuntimeException(e);}

    }
    public SocketIO(OutputStream out){
        try {
            this.out = new ObjectOutputStream(new DataOutputStream(out));
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void setInPutStream(InputStream in){
        try {
            this.scanner = new Scanner(in);
            this.in = new ObjectInputStream(new DataInputStream(in));
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public SerializableCommand readCommand() {
        SerializableCommand command = null;
        try {
            command = (SerializableCommand) in.readObject();
        } catch (IOException e) {
            return null;
        }
        catch (ClassNotFoundException e) {throw new RuntimeException(e);}

        return command;
    }

    @Override
    public void write(String text) {

    }

    @Override
    public void write(SerializableCommand command) {
        try {
            synchronized (this) {
                out.writeObject(command);
                out.flush();
            }
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    @Override
    public float readVal() {
        return scanner.nextFloat();
    }

    @Override
    public void write(float val) {

    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
