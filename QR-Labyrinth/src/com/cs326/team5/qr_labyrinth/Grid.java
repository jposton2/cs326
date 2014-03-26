import java.util.ArrayList;
public class Grid{
    private ArrayList<ArrayList<PointData>> grid;
    Point start;
    Grid(Point start, ArrayList<ArrayList<PointData>> grid){
        this.grid = grid;
        this.start = start;
    }
    ArrayList<ArrayList<PointData>> getGrid(){
        return grid;
    }

    Point getStart(){
        return start;
    }
}
