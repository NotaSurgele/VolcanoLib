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
        this.maze[h - 1][w - 1] = 3;
        this.maze[h - 1][w - 2] = 3;
        this.maze[h - 1][w - 3] = 3;
    }

    private int checkCellsAround(int line, int cell)
    {
        int possibility = 0;

        if (line + 2 <= this.height - 1 && this.maze[line + 2][cell] == -1) {
            possibility += 1;
            this.possibility.add(new Vector2(line + 2, cell));
        }
        if (line - 2 >= 0 && this.maze[line - 2][cell] == -1) {
            possibility += 1;
            this.possibility.add(new Vector2(line - 2, cell));
        }
        if (cell + 2 <= this.width - 1 && this.maze[line][cell + 2] == -1) {
            possibility += 1;
            this.possibility.add(new Vector2(line, cell + 2));
        }
        if (cell - 2 >= 0 && this.maze[line][cell - 2] == -1) {
            possibility += 1;
            this.possibility.add(new Vector2(line, cell - 2));
        }
        return possibility;
    }

    private void addCoordToStack(int line, int cell)
    {
        this.coordTrack.add(new Vector2(line, cell));
    }

    private int random(int choice)
    {
        int random = 0;

        random = MathUtils.random(1, choice) - 1;
        return random;
    }

    private void setPath(int line, int cell, int oldLine, int oldCell)
    {
        if (line > oldLine)
            this.maze[oldLine + 1][cell] = 1;
        if (line < oldLine)
            this.maze[oldLine - 1][cell] = 1;
        if (cell > oldCell)
            this.maze[oldLine][oldCell + 1] = 1;
        if (cell < oldCell)
            this.maze[oldLine][oldCell - 1] = 1;
        this.maze[line][cell] = 1;
    }

    private int complexity(int choice, int random)
    {
        int t = MathUtils.random(1, 6);

        if (t % 2 == 0)
            random = MathUtils.random(1, choice) - 1;
        return random;
    }

    public void setMaze(int[][]toSet)
    {
        this.maze = toSet;
        this.width = toSet[0].length;
        this.height = toSet.length;
    }

    public boolean algorithm()
    {
        int line = 2;
        int cell = 2;
        int current = 0;

        for (int walls = this.walls; walls != 0; walls--) {
            int stackSize = this.coordTrack.size();
            current = (current <= stackSize) ? stackSize: current;
            int choice = this.checkCellsAround(line, cell);
            while (walls > 1 && choice == 0) {
                current -= 1;
                if (current < 0) return true;
                line = (int)this.coordTrack.get(current).x;
                cell = (int)this.coordTrack.get(current).y;
                choice = this.checkCellsAround(line, cell);
            }
            int r = this.random(choice);
            r = this.complexity(choice, r);
            this.addCoordToStack(line, cell);
            int oldLine = line;
            int oldCell = cell;
            line = (int)this.possibility.get(r).x;
            cell = (int)this.possibility.get(r).y;
            this.setPath(line, cell, oldLine, oldCell);
            this.possibility.clear();
            current += 1;
        }
        return true;
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

        //this.displayMaze(this.maze, this.width, this.height);

    }
}
