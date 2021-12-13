public class Vector2i {

    int x;
    int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public void print()
    {
        System.out.println("( " + x + ", " + y + " )");
    }
}
