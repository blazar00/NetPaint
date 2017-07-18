package controller_view;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ColorTypeConverter;
import model.Line;
import model.Oval;
import model.PaintObject;
import model.Picture;
import model.Rectangle;

/**
 * A JPanel GUI for Netpaint that has all paint objects drawn on it. This file
 * also represents the controller as it controls how paint objects are drawn and
 * sends new paint objects to the server. All Client objects also listen to the
 * server to read the Vector of PaintObjects and repaint every time any client
 * adds a new one.
 * 
 * @author Braxton Lazar
 * 
 */
public class Client extends Application {

	private ColorPicker colorPicker;
	private Color white = Color.WHITE;
	private Color color = Color.BLUE;
	private Point start = null;
	private Point end;
	private Vector<PaintObject> allPaintObjects = new Vector<>();

	private ObjectOutputStream writer;
	private ObjectInputStream inputFromServer;
	private Socket socketServer;
	public static String host = "localhost";
	public GraphicsContext gc;

	private void makeConnection() {
		ListenForServerUpdates listener = new ListenForServerUpdates();
		Thread thread = new Thread(listener);
		thread.setDaemon(true);
		thread.start();
	}

	// Listen for the Server to read a modified Vector<PaintObject>
	// Because JavaFX is not Thread-safe. This must be started in a
	// new Thread as a Task from the javafx.concurrent Package.
	private class ListenForServerUpdates extends Task<Object> {

		@SuppressWarnings("unchecked")
		protected Object call() throws Exception {
			try {
				socketServer = new Socket(host, 4011);
				writer = new ObjectOutputStream(socketServer.getOutputStream()); // from starter/lecture code
				inputFromServer = new ObjectInputStream(socketServer.getInputStream());
				drawAllPaintObjects();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(true){
				allPaintObjects = (Vector<PaintObject>) inputFromServer.readObject();
				drawAllPaintObjects();
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane all = new BorderPane();
		Scene scene = new Scene(all, 800, 650);
		Canvas canvas = new Canvas(800, 550);
		gc = canvas.getGraphicsContext2D();
		GridPane gridPane = createGridPane(canvas, gc);
		gc.setFill(ColorTypeConverter.Awt2Fx(white));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		all.setCenter(canvas);
		all.setBottom(gridPane);

		primaryStage.setScene(scene);
		primaryStage.show();
		
		makeConnection();
	}

	private GridPane createGridPane(Canvas canvas, GraphicsContext gc) {
		RadioButton line = new RadioButton("Line");
		RadioButton rect = new RadioButton("Rectangle");
		RadioButton oval = new RadioButton("Oval");
		RadioButton pict = new RadioButton("Picture");
		ToggleGroup radioGroup = new ToggleGroup();

		colorPicker = new ColorPicker();
		colorPicker.setValue(ColorTypeConverter.Awt2Fx(color));
		colorPicker.setOnAction(event -> {
			color = ColorTypeConverter.Fx2Awt(colorPicker.getValue());
		});

		line.setToggleGroup(radioGroup);
		rect.setToggleGroup(radioGroup);
		oval.setToggleGroup(radioGroup);
		pict.setToggleGroup(radioGroup);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(40);
		gridPane.setVgap(40);
		gridPane.setAlignment(Pos.BOTTOM_CENTER);

		gridPane.add(line, 0, 1);
		gridPane.add(rect, 1, 1);
		gridPane.add(oval, 2, 1);
		gridPane.add(pict, 3, 1);
		gridPane.add(colorPicker, 4, 1);

		line.setOnAction(event -> {
			addHandlersToLine(canvas, gc);
		});

		rect.setOnAction(event -> {
			addHandlersToRect(canvas, gc);
		});

		oval.setOnAction(event -> {
			addHandlersToOval(canvas, gc);
		});

		pict.setOnAction(event -> {
			addHandlersToPict(canvas, gc);
		});

		return gridPane;
	}

	private void addHandlersToLine(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseClicked(event -> {
			if (start == null)
				start = new Point((int) event.getX(), (int) event.getY());
			else {
				try {
					writer.writeObject(new Line(color, start, end));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				start = null;
			}
		});

		canvas.setOnMouseMoved(event -> {
			if (start != null) {
				end = new Point((int) event.getX(), (int) event.getY());
				PaintObject pc = new Line(color, start, end);
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.setFill(ColorTypeConverter.Awt2Fx(white));
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				for (PaintObject po : allPaintObjects)
					po.draw(gc);
				pc.draw(gc);
			}
		});
	}

	private void addHandlersToRect(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseClicked(event -> {
			if (start == null)
				start = new Point((int) event.getX(), (int) event.getY());
			else {
				try {
					writer.writeObject(new Rectangle(color, start, end));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				start = null;
			}
		});

		canvas.setOnMouseMoved(event -> {
			if (start != null) {
				end = new Point((int) event.getX(), (int) event.getY());
				PaintObject pc = new Rectangle(color, start, end);
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.setFill(ColorTypeConverter.Awt2Fx(white));
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				for (PaintObject po : allPaintObjects)
					po.draw(gc);
				pc.draw(gc);
			}
		});
	}

	private void addHandlersToOval(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseClicked(event -> {
			if (start == null)
				start = new Point((int) event.getX(), (int) event.getY());
			else {
				try {
					writer.writeObject(new Oval(color, start, end));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				start = null;
			}
		});

		canvas.setOnMouseMoved(event -> {
			if (start != null) {
				end = new Point((int) event.getX(), (int) event.getY());
				PaintObject pc = new Oval(color, start, end);
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.setFill(ColorTypeConverter.Awt2Fx(white));
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				for (PaintObject po : allPaintObjects)
					po.draw(gc);
				pc.draw(gc);
			}
		});
	}

	private void addHandlersToPict(Canvas canvas, GraphicsContext gc) {
		canvas.setOnMouseClicked(event -> {
			if (start == null)
				start = new Point((int) event.getX(), (int) event.getY());
			else {
				try {
					writer.writeObject(new Picture(start, end, "doge.jpeg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				start = null;
			}
		});

		canvas.setOnMouseMoved(event -> {
			if (start != null) {
				end = new Point((int) event.getX(), (int) event.getY());
				PaintObject pc = new Picture(start, end, "doge.jpeg");
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.setFill(ColorTypeConverter.Awt2Fx(white));
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				for (PaintObject po : allPaintObjects)
					po.draw(gc);
				pc.draw(gc);
			}
		});
	}
	
	private void drawAllPaintObjects() {
	    for (PaintObject po : allPaintObjects)
	      po.draw(gc);
	}
}