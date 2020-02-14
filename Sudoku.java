package com.julianve;

import java.util.ArrayList;
import java.util.Collections;

public class Sudoku {
    public int[][] cells;
    private int solutions;

    public Sudoku() {
        cells = new int[9][9];
        generate(cells);
        unsolve();
    }

    //is the grid done?
    public boolean done(int[][] grid) {
        //iterate over all cells
        for (int[] row : grid
        ) {
            for (int cell : row
            ) {
                if (cell == 0)
                    return false;
            }

        }
        //if no cell equals 0 its done
        return true;
    }

    //how many solutions are there?
    public boolean solutions(int[][] grid) {
        //iterate over all cells
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {

                //skip all done cells
                while (col < grid[0].length && grid[row][col] != 0)
                    col++;
                if (col >= grid[0].length)
                    break;

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
                    int x = 0, y = 0;
                    if (col < 3 && row < 3) {
                    } else if (col < 3 && row < 6) {
                        y = 3;
                    } else if (col < 3 && row < 9) {
                        y = 6;
                    } else if (col < 6 && row < 3) {
                        x = 3;
                    } else if (col < 6 && row < 6) {
                        x = 3;
                        y = 3;
                    } else if (col < 6 && row < 9) {
                        x = 3;
                        y = 6;
                    } else if (col < 9 && row < 3) {
                        x = 6;
                    } else if (col < 9 && row < 6) {
                        x = 6;
                        y = 3;
                    } else if (col < 9 && row < 9) {
                        x = 6;
                        y = 6;
                    }

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
        return false;
    }

    public boolean generate(int[][] grid) {
        //iterate over all cells
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                //skip all done cells
                while (col < grid[0].length && grid[row][col] != 0)
                    col++;
                if (col >= grid[0].length)
                    break;

                //randomise values
                ArrayList<Integer> numbers = new ArrayList();
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

                    //check the 3x3 your in
                    int x = 0, y = 0;
                    if (col < 3 && row < 3) {
                    } else if (col < 3 && row < 6) {
                        y = 3;
                    } else if (col < 3 && row < 9) {
                        y = 6;
                    } else if (col < 6 && row < 3) {
                        x = 3;
                    } else if (col < 6 && row < 6) {
                        x = 3;
                        y = 3;
                    } else if (col < 6 && row < 9) {
                        x = 3;
                        y = 6;
                    } else if (col < 9 && row < 3) {
                        x = 6;
                    } else if (col < 9 && row < 6) {
                        x = 6;
                        y = 3;
                    } else if (col < 9 && row < 9) {
                        x = 6;
                        y = 6;
                    }

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
                    grid[row][col] = value;
                    /*
                    System.out.println("---------");
                    String r = "";
                    for(int i=0;i<grid.length;i++) {
                        r = "";
                        for (int o = 0; o < grid[0].length; o++)
                            r += grid[i][o];
                        System.out.println(r);
                    }
                    */
                    if (done(grid)) {
                        System.out.println("Grid Done!");
                        cells = grid;
                        return true;
                    } else if (generate(grid)) {
                        return true;
                    }
                }
                grid[row][col] = 0;
                return false;
            }
        }
        return false;
    }

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

        //undo
        if (solutions != 1) {
            cells[x][y] = value;
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
