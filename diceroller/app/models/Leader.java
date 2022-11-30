package models;

public class Leader implements Comparable  {
    private int rank;
    private String name;
    private int score;

    public Leader() {
        this.rank = 0;
        this.name = "";
        this.score = 0;
    }
    public Leader(String n, int s) {
        this.rank = 0;
        this.name = n;
        this.score = s;
    }
    public Leader(int r, String n, int s) {
        this.rank = r;
        this.name = n;
        this.score = s;
    }

    public void setResult(String n, int s) {
        this.name = n;
        this.score = s;
    }

    public void setRank(int r) {
        this.rank = r;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        int comparingScore = ((Leader)o).getScore();
        return comparingScore-this.score;
    }

}
