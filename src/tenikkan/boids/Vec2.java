package tenikkan.boids;

public class Vec2
{
    public double x, y;
    
    public Vec2(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }
    
    public double length() 
    {
        return Math.sqrt(x * x + y * y);
    }
    
    public Vec2 normalized() 
    {
        double len = length();
        if(len == 0) return new Vec2(0.0, 0.0);
        return new Vec2(x / len, y / len);
    }
}
