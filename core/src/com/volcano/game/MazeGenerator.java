package com.volcano.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MazeGenerator {

    int width;
    int height;
    int[][] maze;
    int walls = 0;
    ArrayList<Vector2> coordTrack;
    ArrayList<Vector2> possibility;

    public MazeGenerator(int w, int h) {
        this.width = w;
        this.height = h;
        this.maze = new int[h][w];
        this.filledMaze(this.maze, this.width, this.height);
        this.coordTrack = new ArrayList<Vector2>();
        this.possibility = new ArrayList<Vector2>();
        this.algorithm();
    }

    public void filledMaze(int[][] maze, int w, int h)
    {
        for (int l=0; l != h; l++) {
            for (int c=0; c != w; c++) {
                if (l % 2 == 0 && c % 2 == 0) {
                    this.maze[l][c] = 1;
                } else {
                    this.maze[l][c] = 0;
                    this.walls += 1;
                }
            }
        }
        this.maze[h - 1][w - 1] = 1;
        this.maze[h - 1][w - 2] = 1;
    }

    private int random(int choice)
    {
        if (choice > 0)
            return MathUtils.random(1, choice) - 1;
        else return 0;
    }

    private int trackCoordinate(int line, int cell, int current)
    {
        this.coordTrack.add(current, new Vector2(line, cell));
        return this.coordTrack.size() - 1;
    }

    private int checkCellsAround(int line, int cell)
    {
        if (line + 2 < this.height - 1 && this.maze[line + 2][cell] == 1)
            this.possibility.add(new Vector2(line + 2, cell));

        if (line - 2 > 0 && this.maze[line - 2][cell] == 0)
            this.possibility.add(new Vector2(line - 2, cell));

        if (cell + 2 < this.width - 1 && this.maze[line][cell + 2] == 1)
            this.possibility.add(new Vector2(line, cell + 2));

        if (cell - 2 > 0 && this.maze[line][cell - 2] == 0)
            this.possibility.add(new Vector2(line, cell - 2));

        return this.possibility.size();
    }


    public void destroyWall(int current, int newLine, int newCell)
    {
        int oldLine = (int)this.coordTrack.get(current).x;
        int oldCell = (int)this.coordTrack.get(current).y;

        if (newLine > oldLine) this.maze[oldLine + 1][oldCell] = 1;
        if (newLine < oldLine) this.maze[oldLine - 1][oldCell] = 1;
        if (newCell > oldCell) this.maze[oldLine][oldCell + 1] = 1;
        if (newCell < oldCell) this.maze[oldLine][oldCell - 1] = 1;
    }

    public void algorithm()
    {
        int line = 0;
        int cell = 0;
        int current = 0;

        for (int walls = this.walls; walls != 0; walls--) {
            current = (current <= this.coordTrack.size()) ? current: this.coordTrack.size() - 1;
            int possibility = this.checkCellsAround(line, cell);
            while (possibility == 0) {
                current--;
                line = (int)this.coordTrack.get(current).x;
                cell = (int)this.coordTrack.get(current).y;
                possibility = this.checkCellsAround(line, cell);
            }
            int r = this.random(possibility);
            this.trackCoordinate(line, cell, current);
            line = (int)this.possibility.get(r).x;
            cell = (int)this.possibility.get(r).y;
            this.destroyWall(current, line, cell);
            current++;
        }
    }

    public void displayMaze(int[][] maze, int w, int h) {
        for (int line = 0; line != h;  line++) {
            for (int cell = 0; cell != w; cell++) {
                System.out.print(maze[line][cell]);
            }
            System.out.println();
        }
        System.out.println("_________________________________________________________________________________");
    }

    public void update()
    {
        this.displayMaze(this.maze, this.width, this.height);
    }
}
