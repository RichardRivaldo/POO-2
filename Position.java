public class Position {
    protected Integer x;
    protected Integer y;

    public Position(){
        this.x = 0;
        this.y = 0;
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(Position pos){
        this.x = pos.x;
        this.y = pos.y;
    }

    public int getXCoordinate(){
        return this.x;
    }

    public int getYCoordinate(){
        return this.y;
    }

    public void setXCoordinate(int x){
        this.x = x;
    }

    public void setYCoordinate(int y){
        this.y = y;
    }
}
