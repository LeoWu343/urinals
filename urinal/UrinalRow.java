package urinal;
import java.util.*;

public class UrinalRow {
    private ArrayList<Urinal> urinals;

    public UrinalRow() {
        urinals = new ArrayList<Urinal>();
    }

    public ArrayList<Urinal> getUrinalList() {
        return urinals;
    }

    public int numOfUrinals() {
        return urinals.size();
    }

    public void makeUrinal() {
        urinals.add(new Urinal(urinals.size()));
    }

    private void takeUrinal(int position) throws InvalidUrinalException {
        if(position >= urinals.size() || urinals.get(position) == null) {
            throw new InvalidUrinalException();
        }
        urinals.get(position).take();
    }

    public void takeUrinal() throws InvalidUrinalException {
        ArrayList<Urinal> taken = takenUrinals();
        ArrayList<Urinal> untaken = untakenUrinals();
        Urinal furthest = new Urinal(-1);
        int distance = 1;
        if(taken.size() == 0) {
            furthest = urinals.get(0);
            distance = 0;
        }
        else if(taken.size() == 1 && numOfUrinals() <= 2) {
            return;
        }
        else if(taken.size() == 1 && numOfUrinals() > 2) {
            furthest = urinals.get(urinals.size() - 1);
            distance = furthest.getDistance(urinals.get(0));
        }
        else {
            for(int i = 0; i < taken.size()-1; i++) {
                Urinal currT1 = taken.get(i);
                Urinal currT2 = taken.get(i+1);
                for(int j = 0; j < untaken.size(); j++) {
                    Urinal currU = untaken.get(j);
                    int dist = 0;
                    if(currU.getPosition() > currT1.getPosition() && currU.getPosition() < currT2.getPosition()) {
                        dist = currU.getDistance(currT2) * currU.getDistance(currT1);
                    }
                    boolean checkAdj = true;
                    for(int w = 0; w < taken.size()-1; w++) {
                        if(currU.getDistance(taken.get(w)) == 1 || currU.getDistance(taken.get(w+1)) == 1) {
                            checkAdj = false;
                        }
                    }
                    if(dist > distance && checkAdj) {
                        furthest = currU;
                        distance = dist;
                    }
                }
            }
        }
        if(furthest.getPosition() >= 0) {
            this.takeUrinal(furthest.getPosition());
        }
    }

    private ArrayList<Urinal> untakenUrinals() {
        ArrayList<Urinal> untakenUrinals = new ArrayList<Urinal>();
        for(int i = 0; i < urinals.size(); i++) {
            Urinal currUrinal = urinals.get(i);
            if(!currUrinal.isTaken()) {
                untakenUrinals.add(currUrinal);
            }
        }
        return untakenUrinals;
    }

    private ArrayList<Urinal> takenUrinals() {
        ArrayList<Urinal> takenUrinals = new ArrayList<Urinal>();
        for(int i = 0; i < urinals.size(); i++) {
            Urinal currUrinal = urinals.get(i);
            if(currUrinal.isTaken()) {
                takenUrinals.add(currUrinal);
            }
        }
        return takenUrinals;
    }

    public boolean isTaken(int position) throws InvalidUrinalException {
        if(position >= urinals.size() || urinals.get(position) == null) {
            throw new InvalidUrinalException();
        }
        return urinals.get(position).isTaken();
    }

    public String toString() {
        String theString = "[ ";
        for(Urinal e : urinals) {
            if(e.isTaken()) {
                theString = theString + "| ";
            }
            else {
                theString = theString + "' ";
            }
        }
        theString = theString + "]";
        return theString;
    }

    public static void main(String[] args) {
        UrinalRow herro = new UrinalRow();
        System.out.print("Please enter the number of urinals: ");
        Scanner userIn = new Scanner(System.in);
        int urinalNum = userIn.nextInt();
        for(int i = 0; i < urinalNum; i++) {
            herro.makeUrinal();
        }
        try {
            for(int i = 0; i < 10; i++) {
                herro.takeUrinal();
            }
            System.out.println(herro);
        } catch(InvalidUrinalException e) {
            System.err.println(e);
        }
    }
}
