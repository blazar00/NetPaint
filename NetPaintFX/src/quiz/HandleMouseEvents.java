package quiz;

import java.awt.Point;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
  * Code demo to show how to handle mouse clicks on a Canvas.
  * I added an alert for any Mouse click, but this is not needed
  * in this quiz or the project except possibly for debugging.
  *
  * @author Rick Mercer
 */
public class HandleMouseEvents extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane all = new BorderPane();
    Canvas canvas = new Canvas(400, 400);
    addHandlersTo(canvas);
    
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    // Layout GUI
    all.setCenter(canvas);
    all.setTop(new Label("top"));
    all.setBottom(new Label("bottom"));
    Scene scene = new Scene(all, 400, 460);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void addHandlersTo(Canvas canvas) {
    canvas.setOnMouseClicked(event -> {
      // Using java.awt.Point because we used it in NetPaint 1
      Point aPoint = new Point((int) event.getX(), (int) event.getY());
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText("You just clicked the mouse on the canvas");
      alert.setContentText(aPoint.toString());
      alert.showAndWait();
    });

    canvas.setOnMouseMoved(event -> {
      Point aPoint = new Point((int) event.getX(), (int) event.getY());
      System.out.println("Mouse moved: " + aPoint);
    });

  }
}