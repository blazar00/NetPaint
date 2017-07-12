package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Picture extends PaintObject{
	
	private Image image;
	private Point s;
	private Point e;

	public Picture(Point start, Point end, String str) {
		image = new Image("file:images/" + str, false);
		s = start;
		e = end;
	}

	public void draw(GraphicsContext gc){
		if(e.getX() > s.getX() && e.getY() > s.getY())
			gc.drawImage(image, s.getX(), s.getY(), e.getX() - s.getX(), e.getY() - s.getY());
		if(s.getX() > e.getX() && s.getY() > e.getY())
			gc.drawImage(image, e.getX(), e.getY(), s.getX() - e.getX(), s.getY() - e.getY());
		if(e.getX() > s.getX() && s.getY() > e.getY())
			gc.drawImage(image, s.getX(), e.getY(), e.getX() - s.getX(), s.getY() - e.getY());
		if(s.getX() > e.getX() && e.getY() > s.getY())
			gc.drawImage(image, e.getX(), s.getY(), s.getX() - e.getX(), e.getY() - s.getY());
		
	}
}
