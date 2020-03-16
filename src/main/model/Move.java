package model;

public class Move {
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

    public enum Status {
        HIT, MISS, BOMBED
    }
}
