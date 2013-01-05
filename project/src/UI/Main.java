package UI;

public class Main {

    public static void main(String[] args) {      
        GUI gui;
        TextUI tui;
        if (args[0].equalsIgnoreCase("-gui")) {
            gui = new GUI();
            gui.run();
        } else {
            tui = new TextUI();
            tui.start();
        }
    }
}