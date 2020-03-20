package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;

public class Move implements Saveable {
    private int xc;
    private int yc;
    private Status status;

    public Move(int x, int y, Status s) {
        this.xc = x;
        this.yc = y;
        status = s;
    }

    public int getXCoordinate() {
        return xc;
    }

    public int getYCoordinate() {
        return yc;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(xc);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(yc);
        printWriter.print(Reader.DELIMITER);
        if (status == Status.MISS) {
            printWriter.print(0);
        } else {
            printWriter.print(1);
        }
        printWriter.print(Reader.DELIMITER);
    }

    public enum Status {
        HIT, MISS, BOMBED
    }
}
