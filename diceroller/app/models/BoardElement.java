package models;

public class BoardElement {

    private boolean fixed;

    private int element;


    public BoardElement (int x) {
        element = x;
    }

    public BoardElement() {
        element = 0;
    }

    void setElement (int x) {
        element = x;
    }

    public int boardElementValue() {
        return element;
    }
    public void holdBoardElement () {
        fixed = true;
    }

    boolean isOnHold() {
        return fixed;
    }


}
