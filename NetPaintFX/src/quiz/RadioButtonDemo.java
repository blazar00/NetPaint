package quiz;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
  * Code demo to show how to use a ColorPicker
  *
  * @author Rick Mercer
 */
public class RadioButtonDemo extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private ColorPicker colorPicker;

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane all = new BorderPane();

    GridPane gridPane = createGridPane();

    // Layout GUI
    all.setCenter(gridPane);
    all.setTop(new Label("top"));
    all.setBottom(new Label("bottom"));
    Scene scene = new Scene(all, 200, 200);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private GridPane createGridPane() {
    RadioButton one = new RadioButton("One");
    RadioButton two = new RadioButton("Two");
    RadioButton three = new RadioButton("Three");
    ToggleGroup radioGroup = new ToggleGroup();

    one.setToggleGroup(radioGroup);
    two.setToggleGroup(radioGroup);
    three.setToggleGroup(radioGroup);

    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setAlignment(Pos.CENTER);

    gridPane.add(one, 0, 0);
    gridPane.add(two, 0, 1);
    gridPane.add(three, 0, 2);

    one.setOnAction(event -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setHeaderText("You just clicked RadioButton one");
      alert.showAndWait();
    });

    two.setOnAction(event -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setHeaderText("You just clicked RadioButton two");
      alert.showAndWait();
    });

    three.setOnAction(event -> {
      System.out.println("You chose RadioButton Three");
    });

    return gridPane;
  }

}