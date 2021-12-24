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
                layer[i][j] = -1;
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

    public void clear()
    {
        this.layer = null;

        this.layer = this.setLayer();
    }

    public void showLayer()
    {
        for (int i = 0; i != this.layer.length; i++) {
            for (int j = 0; j != this.layer[i].length; j++) {
                System.out.print(" " + this.layer[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("__________________________________________________\n");
    }
}
