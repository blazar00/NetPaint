package model;


import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;

public class Line extends PaintObject implements Serializable{

	private Color color;
	private Point s;
	private Point e;
	
	public Line(Color c, Point start, Point end) {
		color = c;
		s = start;
		e = end;
	}
	
	public void draw(GraphicsContext gc){
		javafx.scene.paint.Color c = ColorTypeConverter.Awt2Fx(color);
		gc.setStroke(c);
		gc.strokeLine(s.getX(), s.getY(), e.getX(), e.getY());
		
	}

}
