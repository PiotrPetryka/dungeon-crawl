package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    GameMap map = new MapLoader().loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Button pickUpButton = new Button("Pick up");

    ArrayList<Label> itemLabels = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        // TODO: sprawdź poprawność refresh kiedy podnosi z ziemi
        refresh();

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(pickUpButton, 2, 400);


        ui.add(new Label("-----------"), 0, 1);
        ui.add(new Label("Items: "), 0, 2);

        pickUpButton.setFocusTraversable(false);
        pickUpButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(map.getPlayer().getCell().getItem() != null){
                    map.getPlayer().addItemToEq(map.getPlayer().getCell().getItem());
                    System.out.println(map.getPlayer().getEquipment().get(0).getTileName());
//                    map.getPlayer().getCell()
                }else{
                    System.out.println("There is no item.");
                }
            }
        });
        for (int i = 0; i<itemLabels.size(); i++) {
            ui.add(itemLabels.get(i), 3, i + 2);
        }


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP -> map.getPlayer().move(0, -1);
            case DOWN -> map.getPlayer().move(0, 1);
            case LEFT -> map.getPlayer().move(-1, 0);
            case RIGHT -> map.getPlayer().move(1, 0);
        }
        moveMonsters();
        refresh();
    }

    private void checkForWall(int x, int y) {
        if (map.getPlayer().getCell().getType() == CellType.WALL){
            map.getPlayer().move(x, y);
        }
    }

    private String getRandomDirection(){
        List<String> directions = Arrays.asList("UP", "DOWN", "LEFT", "RIGHT");
        Random random = new Random();
        return directions.get(random.nextInt(directions.size()));
    }

    private void moveMonsters(){
        String direction = getRandomDirection();
        switch (direction) {
            case "UP" -> map.getActor().move(0, -1);
            case "DOWN" -> map.getActor().move(0, 1);
            case "LEFT" -> map.getActor().move(-1, 0);
            case "RIGHT" -> map.getActor().move(1, 0);
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                Tiles.drawTile(context, cell, x, y);
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        for (Item item : map.getPlayer().getEquipment()) {
            itemLabels.add(new Label(item.getName()));
        }
    }
}
