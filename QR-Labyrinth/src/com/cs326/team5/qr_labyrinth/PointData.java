public class PointData{
    private boolean black;
    Point destination;
    PointData(Point p, boolean b){
        destination = p;
        black = b;
    }
    public boolean isBlack(){
        return black;
    }
    public Point getDestination(){
        return destination;
    }

 }
