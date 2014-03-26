import java.util.ArrayList;
import java.util.Vector;

public class GridHandler{
    private int minSize;
    private Grid grid;
    private ArrayList<ArrayList<Point>> subgraphs;
    GridHandler(Grid g, int minSize, ArrayList<ArrayList<Point>> subgraphs){
        this.subgraphs = subgraphs;
        this.minSize = minSize;
        grid = g;
    }
    public Grid getGrid(){
        return grid;
    }
    public Vector<Point> getFurthestPints(ArrayList<Point> subgrid){
        return null;
    }
    public void setTeleporters(ArrayList<ArrayList<Point>> subgrids){
        // TODO:Add teleporter setters 
    }
    ArrayList<ArrayList<Point>> QRTo2DArray(){
        // TODO: Implement
        return subgraphs;
    }


}
