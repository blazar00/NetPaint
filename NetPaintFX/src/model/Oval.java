package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Oval extends PaintObject{
	
	private Color color;
	private Point s;
	private Point e;

	public Oval(Color c, Point start, Point end) {
		color = c;
		s = start;
		e = end;
	}

	public void draw(GraphicsContext gc){
		gc.setFill(color);
		if(e.getX() > s.getX() && e.getY() > s.getY())
			gc.fillOval(s.getX(), s.getY(), e.getX() - s.getX(), e.getY() - s.getY());
		if(s.getX() > e.getX() && s.getY() > e.getY())
			gc.fillOval(e.getX(), e.getY(), s.getX() - e.getX(), s.getY() - e.getY());
		if(e.getX() > s.getX() && s.getY() > e.getY())
			gc.fillOval(s.getX(), e.getY(), e.getX() - s.getX(), s.getY() - e.getY());
		if(s.getX() > e.getX() && e.getY() > s.getY())
			gc.fillOval(e.getX(), s.getY(), s.getX() - e.getX(), e.getY() - s.getY());
		
	}
}
