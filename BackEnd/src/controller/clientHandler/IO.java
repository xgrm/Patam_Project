package controller.clientHandler;

import view.SerializableCommand;

public interface IO {
    public String readLine();

    public SerializableCommand readCommand();
    public void write(String text);
    public void write(SerializableCommand command);
    public float readVal();
    public void write(float val);
    public boolean hasNext();
    public void close();
}
