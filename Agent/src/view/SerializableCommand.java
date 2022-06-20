package view;

import java.io.Serializable;
import java.util.HashMap;

public class SerializableCommand implements Serializable {

    int id;

    String commandName;

    HashMap<String, Float> dataMap;
    String data;

    Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public SerializableCommand() {
    }
    public SerializableCommand(String commandName,String data) {
        this.commandName = commandName;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getCommandName() {
        return commandName;
    }

    public HashMap<String, Float> getDataMap() {
        return dataMap;
    }

    public String getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setDataMap(HashMap<String, Float> dataMap) {
        this.dataMap = dataMap;
    }

    public void setData(String data) {
        this.data = data;
    }
}

