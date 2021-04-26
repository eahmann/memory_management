package org.ahmann;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IoClass {
    public static PrintStream output = null;
    public final InputStream input;
    public final Scanner scanner;

    public IoClass() {
        input = System.in;
        output = System.out;
        scanner = new Scanner(input);
    }
}
