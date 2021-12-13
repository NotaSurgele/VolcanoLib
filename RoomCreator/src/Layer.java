public class Layer {

    int[][] layer;

    public Layer() {
        this.layer = setLayer();
    }

    public int[][] setLayer()
    {
        int[][] layer = new int[50][50];

        for (int i = 0; i != 50; i++) {
            for (int j = 0; j != 50; j++) {
                layer[i][j] = 3;
            }
        }
        return layer;
    }

    public int[][] getLayer()
    {
        return this.layer;
    }

    public void setValue(int x, int y, int value)
    {
        this.layer[x][y] = value;
    }
}
