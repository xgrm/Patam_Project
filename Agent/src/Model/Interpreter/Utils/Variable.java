package Model.Interpreter.Utils;

import Model.AgentModel;

public class Variable {
    String name;
    volatile float value;
    String bindTo;
    AgentModel model;

    public Variable(String name, Float value, String bindTo, AgentModel model) {
        this.name = name;
        this.value = value;
        if(bindTo!=null)
            this.bindTo = "set " + bindTo.substring(1,bindTo.length()-1);
        this.model = model;
    }
    public Variable(String name, Float value){
        this(name,value,null,null);
    }
    public void setValue(Float value) {
        this.value = value;
        if(this.bindTo != null){
            model.sendToFG(bindTo,value);
        }
    }
    public void setValue(Float value,boolean fromBind) {
        this.value = value;
    }
    public void setBindTo(String bindTo) {
        this.bindTo = bindTo;
    }
    public void setModel(AgentModel model) {
        this.model = model;
    }
    public float getValue() {
        return value;
    }
}
