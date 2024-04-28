package marupeke.part3;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import marupeke.part2.MarupekeGrid;
import marupeke.part2.MarupekeTile;
import marupeke.part2.TooManyMarkedSquares;

import java.util.Collection;
import java.util.Scanner;


public class GridFX extends Application {




    @Override
    public void start(Stage primaryStage) throws TooManyMarkedSquares {


        System.out.println("Hello! Before we start let's understand some rules, \n" +
                "To win the game, you must keep have no triplets within the puzzle in any direction. \n" +
                "Left click to mark X, right click to mark O, and middle click to unmark");

        Scanner gameSize = new Scanner(System.in);
        System.out.println("Enter the size: \n" +
                "Note that it must be within sizes of 4 to 10, anything less or more than that will result in the game not starting.");

        int gameCheck = gameSize.nextInt();




        if(gameCheck >= 4 && gameCheck <= 10) {
            GridPane maruGrid = new GridPane();

            MarupekeGrid maruGame = MarupekeGrid.buildGameGrid(gameCheck);

            for (int i = 0; i < maruGame.getSize(); i++) {
                for (int j = 0; j < maruGame.getSize(); j++) {


                    if (maruGame.get(i, j).getMark().equals(MarupekeTile.Mark.BLANK)) {

                        Button buttn = new Button("_");

                        buttn.setStyle("-fx-border-color: black;");
                        buttn.setFont(Font.font(50));
                        buttn.setTextFill(Color.web("#000000", 0.8));

                        buttn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                if (event.getButton() == MouseButton.PRIMARY) {

                                    buttn.setText(MarupekeTile.Mark.CROSS.toString());


                                }
                                if (event.getButton() == MouseButton.SECONDARY) {

                                    buttn.setText(MarupekeTile.Mark.NOUGHT.toString());

                                }
                                if (event.getButton() == MouseButton.MIDDLE) {
                                    buttn.setText(MarupekeTile.Mark.BLANK.toString());
                                }
                            }

                        });


                        maruGrid.add(buttn, i, j);
                    }
                    if (maruGame.get(i, j).getMark().equals(MarupekeTile.Mark.SOLID)) {
                        Button buttSol = new Button("#");



                        buttSol.setFont(Font.font(50));
                        buttSol.setTextFill(Color.web("#ff0000", 0.8));
                        buttSol.disarm();

                        maruGrid.add(buttSol, i, j);
                    }
                    if (maruGame.get(i, j).getMark().equals(MarupekeTile.Mark.CROSS)) {
                        Button buttX = new Button("X");


                        buttX.setFont(Font.font(50));
                        buttX.setTextFill(Color.web("#ff0000", 0.8));
                        buttX.disarm();
                        maruGrid.add(buttX, i, j);
                    }
                    if (maruGame.get(i, j).getMark().equals(MarupekeTile.Mark.NOUGHT)) {
                         Button buttO = new Button("o");


                        buttO.setFont(Font.font(50));
                        buttO.setTextFill(Color.web("#ff0000", 0.8));
                        buttO.disarm();

                        maruGrid.add(buttO, i, j);
                    }
                }
            }

            System.out.println(maruGame);





            maruGrid.setAlignment(Pos.CENTER);

            Scene scene = new Scene(maruGrid, 10000, 10000);

            primaryStage.setTitle("Marupeke!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        else
        {
            System.out.println("Invalid size.");

        }
    }

    public static void main(String[] args)
    {

        if(Integer.parseInt(args[0]) < 4 || Integer.parseInt(args[0]) > 10)
        {
            System.err.println("Invalid Size");
            System.exit(-1);
        }


        GridFX.launch();


    }

}


