package lm;

import java.io.IOException;

/**
 * Represents a Test
 */
public class Test {

    public static void main(String[] args) throws IOException {
        Matrix matrix = new Matrix("raspberry", 6969);
        matrix.clear();
//        matrix.createFont("/root/projects/InputMono/InputMono-Medium.ttf", 16);
        matrix.printString("FUck", 16, 16);
        matrix.swap();
//        matrix.switchScreen("clock");
//        matrix.switchScreen("digital_clock");
//        matrix.switchScreen("mesmerizing");
//        matrix.pause();
//        matrix.unpause();
        matrix.close();
    }
}
