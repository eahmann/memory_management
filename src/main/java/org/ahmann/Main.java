package org.ahmann;

public class Main extends IoClass {
    public static void main(String[] args) {
        App app = new App();

        output.println("MEMORY_MANAGEMENT IS RUNNING.........");

        output.println("Initial FSB list");
        app.showStatus();

        app.getMemory(45);
        app.getMemory(70);
        app.freeMemory(5, 10);
        app.getMemory(10);
        app.freeMemory(25, 50);
        app.getMemory(175);
        app.freeMemory(18, 150);
        app.getMemory(55);
        app.freeMemory(47, 100);
        app.getMemory(47);
    }
}
