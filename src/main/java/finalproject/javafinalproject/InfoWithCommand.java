package finalproject.javafinalproject;


import java.io.Serializable;

public class InfoWithCommand implements Serializable {
    String command;
    String information;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }
}
