package IO;

import view.SerializableCommand;

import java.io.*;

public class SocketIO implements IO{
    ObjectInputStream in;
    ObjectOutputStream out;


    public SocketIO(InputStream in, OutputStream out) {
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
            this.in = new ObjectInputStream(new DataInputStream(in));
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    @Override
    public String readLine() {
        return null;
    }

    @Override
    public SerializableCommand readCommand() {
        SerializableCommand command = null;
        try {
            command = (SerializableCommand) in.readObject();
        } catch (IOException e) {
            System.out.println("back not good");
        }
        catch (ClassNotFoundException e) {
            System.out.println("back not good");
        }
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
        try {
            return in.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(float val) {

    }

    @Override
    public boolean hasNext() {
        try {
            return in.available()>0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
