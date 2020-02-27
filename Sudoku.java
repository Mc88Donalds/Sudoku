package com.julianve;

import java.util.ArrayList;
import java.util.Collections;

public class Sudoku {
    public int[][] cells;
    private int solutions;

    public Sudoku() {
        cells = new int[9][9];
        generate(cells, 0, 0);
        unsolve();
    }

    //is the grid done?
    public boolean done(int[][] grid) {
        //iterate over all cells
        for (int i = 0; i < grid.length; i++) {
            for (int o = 0; o < grid[0].length; o++) {
                if (grid[i][o] == 0)
                    return false;
            }
        }
        //if no cell equals 0 its done
        return true;
    }

    //how many solutions are there?
    public boolean solutions(int[][] grid) {
        //iterate over all cells
        for (int row = 0; ; row++) {
            int col = 0;
            //skip all done cells
            while (col < grid[0].length && grid[row][col] != 0)
                col++;
            if (col >= grid[0].length)
                continue;

            //iterate over all values
            for (int value = 1; value <= grid.length; value++) {
                //check if this value has been used in this row
                boolean used = false;
                for (int i = 0; i < grid.length; i++) {
                    if (grid[row][i] == value) {
                        used = true;
                        break;
                    }
                }
                if (used)
                    continue;
                //check if this value has been used in this column
                for (int i = 0; i < grid.length; i++) {
                    if (grid[i][col] == value) {
                        used = true;
                        break;
                    }
                }
                if (used)
                    continue;
                //check the 3x3 your in
                int x = (col / 3) * 3;
                int y = (row / 3) * 3;

                for (int i = 0; i < 3; i++) {
                    for (int o = 0; o < 3; o++) {
                        if (grid[i + y][o + x] == value) {
                            used = true;
                            break;
                        }
                    }
                }
                if (used)
                    continue;
                //set the value
                grid[row][col] = value;
                if (done(grid)) {
                    //Solution found
                    solutions += 1;
                    if (solutions > 1)
                        return false;
                } else {
                    if (solutions(grid))
                        return true;
                }
            }
            //set value back to 0
            grid[row][col] = 0;
            return false;
        }
    }

    //generate a new Cell
    public boolean generate(int[][] grid, int row, int col) {
        //randomise values
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            numbers.add(i);
        Collections.shuffle(numbers);

        //iterate over all randomised values
        for (int numi = 0; numi < grid.length; numi++) {
            int value = numbers.get(numi);

            //check if this value has been used in this row
            boolean used = false;
            for (int i = 0; i < grid.length; i++) {
                if (grid[row][i] == value) {
                    used = true;
                    break;
                }
            }
            if (used)
                continue;

            //check if this value has been used in this column
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][col] == value) {
                    used = true;
                    break;
                }
            }
            if (used)
                continue;

            //find the block
            int x = (col / 3) * 3;
            int y = (row / 3) * 3;

            //check the block
            for (int i = 0; i < 3; i++) {
                for (int o = 0; o < 3; o++) {
                    if (grid[i + y][o + x] == value) {
                        used = true;
                        break;
                    }
                }
            }
            if (used)
                continue;
            //set value
            grid[row][col] = value;

            col++;
            if (col == 9) {
                col = 0;
                row++;
            }

            //solved grid
            if (done(grid)) {
                System.out.println("Grid Done!");
                cells = grid;
                return true;
            }
            //next step
            else if (generate(grid, row, col)) {
                return true;
            }
        }
        grid[row][col] = 0;
        return false;
    }

    //remove cells
    public void unsolve() {
        //random position
        int x = (int) (Math.random() * 9);
        int y = (int) (Math.random() * 9);
        while (cells[x][y] == 0) {
            x = (int) (Math.random() * 9);
            y = (int) (Math.random() * 9);
        }

        //backup value
        int value = cells[x][y];

        //delete value
        cells[x][y] = 0;

        //copy grid
        int[][] copy = new int[9][9];
        System.arraycopy(cells, 0, copy, 0, copy.length);

        //find solution count
        solutions = 0;
        solutions(copy);

        if (solutions != 1) {
            //undo deletion
            cells[x][y] = value;

            //check whole grid
            for (x = 0; x < cells.length; x++) {
                for (y = 0; y < cells[0].length; y++) {
                    while (y < 9 && cells[x][y] == 0) {
                        y++;
                    }
                    if (y == 9)
                        break;
                    value = cells[x][y];

                    //delete value
                    cells[x][y] = 0;

                    //copy grid
                    System.arraycopy(cells, 0, copy, 0, copy.length);
                    //find solution count
                    solutions = 0;
                    solutions(copy);
                    if (solutions != 1) {
                        //undo
                        cells[x][y] = value;
                    }
                }
                x++;
            }
            return;
        }
        unsolve();
    }
}
