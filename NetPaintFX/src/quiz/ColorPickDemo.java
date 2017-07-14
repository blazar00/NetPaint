package quiz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
  * Code demo to show how to use a ColorPicker
  *
  * @author Rick Mercer
 */
public class ColorPickDemo extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private ColorPicker colorPicker;

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane all = new BorderPane();

    colorPicker = new ColorPicker();
    colorPicker.setValue(Color.BLUE);
    colorPicker.setOnAction(event -> {
      Color color = colorPicker.getValue();
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setHeaderText("You chose a color");
      alert.setContentText(color.toString());
      alert.showAndWait();
    });
    // Layout GUI
    all.setCenter(colorPicker);
    all.setTop(new Label("top"));
    all.setBottom(new Label("bottom"));
    Scene scene = new Scene(all, 200, 200);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

}