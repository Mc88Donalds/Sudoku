package com.julianve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static JFrame window;
    private static Container content;
    private static JLabel title;
    private static Sudoku sudoku;
    private static JLabel[][] grid;
    private static JButton generate;
    private static GenerateListener generateAL;

    public static void main(String[] args) {

        //Create GUI
        window = new JFrame("Sudoku generator");
        window.setSize(375, 450);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = window.getContentPane();
        window.setLocation(350, 350);
        content.setLayout(null);

        //Create Sudoku
        sudoku = new Sudoku();

        //Generate Button
        generate = new JButton("GENERATE");
        generate.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        generateAL = new GenerateListener();
        generate.addActionListener(generateAL);
        generate.setBounds(25, 350, 300, 50);
        content.add(generate);

        grid = new JLabel[3][3];
        for (int o = 0; o < grid.length; o++) {
            for (int i = 0; i < grid[0].length; i++) {
                grid[o][i] = new JLabel(" ");
            }
        }
        draw();
    }

    private static void draw() {

        for (int o = 0; o < grid.length; o++) {
            for (int i = 0; i < grid[0].length; i++) {
                String current = "<html><body>";
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        int cell = sudoku.cells[x + i * grid.length][y + o * grid.length];
                        if (cell != 0)
                            current += cell + " ";
                        else
                            current += "[] ";
                    }
                    current += "<br>";
                }
                current += "</html></body>";
                grid[i][o].setText(current);
                grid[i][o].setFont(new Font("TimesRoman", Font.PLAIN, 24));
                grid[i][o].setBounds(70 + 70 * i, 10 + 105 * o, 70, 100);
                content.add(grid[i][o]);
            }
        }

        System.out.println("Result:");
        String text = "";
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                text += sudoku.cells[x][y] + " ";
            }
            System.out.println(text);
            text = "";
        }
        window.setVisible(true);
    }

    private static class GenerateListener implements ActionListener {
        public GenerateListener() {

        }

        public void actionPerformed(ActionEvent e) {
            sudoku = new Sudoku();
            draw();
        }
    }
}