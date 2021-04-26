package org.ahmann;

public class Main extends IoClass {
    public static void main(String[] args) {
        App app = new App();

        output.println("GET_MEMORY IS RUNNING.........");
        output.println("Initial FSB list");
        app.showStatus();

        app.getMemory(45);
        app.getMemory(70);
        output.println("FREE_MEMORY IS RUNNING.........");
        app.freeMemory(25, 10);
        output.println("GET_MEMORY IS RUNNING.........");
        app.getMemory(10);
        output.println("FREE_MEMORY IS RUNNING.........");
        app.freeMemory(25, 50);


    }
}
