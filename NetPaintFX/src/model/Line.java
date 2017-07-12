package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends PaintObject{

	private Color color;
	private Point s;
	private Point e;
	
	public Line(Color c, Point start, Point end) {
		color = c;
		s = start;
		e = end;
	}
	
	public void draw(GraphicsContext gc){
		gc.setStroke(color);
		gc.strokeLine(s.getX(), s.getY(), e.getX(), e.getY());
		
	}

}
