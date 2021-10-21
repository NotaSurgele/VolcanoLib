package com.volcano.game;

public class LayerData {

    public int[][] layer;
    public int posX;
    public int posY;

    public LayerData(int[][] layer) {
        this.layer = layer;
    }

    public void setLayerInformations(int[][] layer, int x, int y)
    {
        this.layer = layer;
        this.posX = x;
        this.posY = y;
    }

    public void setLayer(int[][] layer)
    {
        this.layer = layer;
    }

    public void setLayerCoordinate(int x, int y)
    {
        this.posX = x;
        this.posY = y;
    }

    public LayerData getLayerInformations()
    {
        return this;
    }
}
