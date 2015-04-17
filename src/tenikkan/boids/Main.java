package tenikkan.boids;

public class Main
{
    public static void main(String args[]) 
    {
        World world = new World();
        
        Display display = new Display("Boids Demo", 1024, 768);
        
        for(int i = 0; i < 1000; i++) 
        {
            double x = Math.random() * 1024 / Display.scale;
            double y = Math.random() * 768 / Display.scale;
            double dx = Math.random() * 2 - 1;
            double dy = Math.random() * 2 - 1;
            int col = (int)(Math.random() * 0x1000000);
            
            Boid b = new Boid(
                    new Vec2(x, y), 
                    new Vec2(dx, dy), 
                    col);
            world.addBoid(b);
        }
        
        double tickTime = System.nanoTime();
        double skipTicks = 1000000000 / 60d;
        
        double timer = System.nanoTime();
        int frames = 0;
        
        while(true) 
        {
            int loops = 0;
            while(tickTime < System.nanoTime() && loops++<10) 
            {
                world.update();
                display.render(world);
                display.show();
                frames++;
                
                tickTime += skipTicks;
            }
            
            if(System.nanoTime() - timer >= 1000000000) 
            {
                timer = System.nanoTime();
                System.out.println(frames + " fps");
                frames = 0;
            }
        }
    }
}
