public class Path {
    private final Station v;
    private final Station w;
    private double duration;
    public Path(Station v, Station w, double roadSpeed)
    {
        this.v = v;
        this.w = w;
        double distance = Math.sqrt(Math.pow(v.coordinates.x - w.coordinates.x, 2) + Math.pow(v.coordinates.y - w.coordinates.y, 2));
        this.duration = distance / roadSpeed;
    }
    public Station from() {return v;}
    public Station to() {return w;}
    public double duration() {return duration; }
}
