package urinal;

public class Urinal {
    private int position;
    private boolean taken;

    public Urinal(int pos) {
        position = pos;
        taken = false;
    }

    public int getPosition() {
        return position;
    }

    protected void take() {
        taken = true;
    }

    public boolean isTaken() {
        return taken;
    }

    protected int getDistance(Urinal other) {
        return Math.abs(other.position - this.position);
    }

    public String toString() {
        if(taken) {
            return "Urinal " + position + " that is taken.";
        }
        return "Urinal " + position + " that is not taken.";
    }
}
