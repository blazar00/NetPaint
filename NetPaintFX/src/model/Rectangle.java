package model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends PaintObject implements Serializable{
	
	private Color color;
	private Point s;
	private Point e;

	public Rectangle(Color c, Point start, Point end) {
		color = c;
		s = start;
		e = end;
	}

	public void draw(GraphicsContext gc){
		javafx.scene.paint.Color c = ColorTypeConverter.Awt2Fx(color);
		gc.setFill(c);
		if(e.getX() > s.getX() && e.getY() > s.getY())
			gc.fillRect(s.getX(), s.getY(), e.getX() - s.getX(), e.getY() - s.getY());
		if(s.getX() > e.getX() && s.getY() > e.getY())
			gc.fillRect(e.getX(), e.getY(), s.getX() - e.getX(), s.getY() - e.getY());
		if(e.getX() > s.getX() && s.getY() > e.getY())
			gc.fillRect(s.getX(), e.getY(), e.getX() - s.getX(), s.getY() - e.getY());
		if(s.getX() > e.getX() && e.getY() > s.getY())
			gc.fillRect(e.getX(), s.getY(), s.getX() - e.getX(), e.getY() - s.getY());
	}
}
