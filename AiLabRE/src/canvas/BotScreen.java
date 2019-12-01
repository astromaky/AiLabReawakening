package canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import botsystem.Bot;
import botsystem.TrusterInterface;
import utils.Vector2;

public class BotScreen extends Canvas {
	
	private static final int RAD = 10;
	private static final int SQR = 10;
	
	private Bot bot;
	
	public BotScreen(Bot b) {
		bot = b;
	}
	
	public static int i = 0;
	public void paint(Graphics g) {
		
		
		BufferedImage bi = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		g2d.setColor(Color.red);
		drawOval(g2d, new Vector2(500,500), 390);
		drawOval(g2d, bot.getPos(), RAD);
		g2d.setColor(Color.GREEN);
		drawOval(g2d, bot.getAbsoluteCenter(), 4);
		g2d.setColor(Color.blue);
		drawOval(g2d, bot.getAbsoluteCenterOfMass(), 4);
		g2d.setColor(Color.red);
		drawLine(g2d,new Vector2(0,900), new Vector2(1000,900));
		drawLine(g2d,Vector2.add( bot.getPos(), (new Vector2(bot.getAngle())).getNormalized().mult(-RAD)),Vector2.add( bot.getPos(), (new Vector2(bot.getAngle())).getNormalized().mult(RAD)) );
		for (TrusterInterface t : bot.getAllTrusters()) {
			Vector2 pos = t.getAbsolutePos();
			//Vector2 ruler = Vector2.turnDeg(new Vector2(0, SQR), Vector2.getAngle(new Vector2(0, 1),t.getDirection()));
			Vector2 ruler = t.getAbsoluteDirection().getNormalized().mult(SQR);
			drawLine(g2d,pos, Vector2.add(pos ,ruler));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, -30)), Vector2.add(pos ,Vector2.turnDeg(ruler, -30)));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, -30)), Vector2.add(pos ,Vector2.turnDeg(ruler, -30)));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, -30)),Vector2.add(pos ,Vector2.turnDeg(ruler, -150)));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, -150)),Vector2.add(pos ,Vector2.turnDeg(ruler, 150)));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, 30)),Vector2.add(pos ,Vector2.turnDeg(ruler, 150)));
			Vector2 top = Vector2.add(pos, Vector2.add(Vector2.turnDeg(ruler, -150), Vector2.turnDeg(ruler, 150)).mult(0.5));
			//Vector2 top = pos;
			drawLine(g2d,Vector2.add(Vector2.add(bot.getPos().mult(-1),top).getNormalized().mult(RAD), bot.getPos() ), top);
		}
		g.drawImage(bi, 0, 0, null);
	}
	
	private void drawLine(Graphics2D g2d, Vector2 v1,Vector2 v2) {
		g2d.drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
	}

	private void drawOval(Graphics2D g2d, Vector2 v1,double rad) {
		g2d.drawOval((int)(v1.getX()-rad) , (int)(v1.getY()-rad), (int)rad*2 , (int)rad*2);
	}
}
