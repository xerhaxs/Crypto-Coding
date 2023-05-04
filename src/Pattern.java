import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pattern {

    private String repeat;
    private int distance;
    private ArrayList<Integer> factors;

    public Pattern(String rpt, int dist, ArrayList<Integer> factors){
        this.repeat = rpt;
        this.distance = dist;
        this.factors = factors;
    }

    public void setFactors(ArrayList<Integer> factors){
        this.factors = factors;
    }

    public String getRepeat() {
        return repeat;
    }

    public int getDistance() {
        return distance;
    }

    public ArrayList<Integer> getFactors() {
        return factors;
    }
}
