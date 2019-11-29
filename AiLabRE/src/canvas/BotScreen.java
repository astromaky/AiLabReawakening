package canvas;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

import botsystem.Bot;
import botsystem.TrusterInterface;
import utils.Vector2;

public class BotScreen extends Canvas {
	
	private static final int RAD = 30;
	private static final int SQR = 30;
	
	private Bot bot;
	
	public BotScreen(Bot b) {
		bot = b;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawOval((int)bot.getPos().getX() - RAD, (int)bot.getPos().getY() - RAD, RAD*2, RAD*2);
		g2d.drawLine((int)bot.getPos().getX() + (int)bot.getDir().getNormalized().getX() * RAD, (int)bot.getPos().getY() + (int)bot.getDir().getNormalized().getY() * RAD, (int)bot.getPos().getX() + (int)bot.getDir().getNormalized().getX() * -RAD, (int)bot.getPos().getY() + (int)bot.getDir().getNormalized().getY() * -RAD);
		for (TrusterInterface t : bot.getAllTrusters()) {
			Vector2 pos = t.getAbsolutePos();
			Vector2 ruler = Vector2.turnDeg(new Vector2(SQR, 0), Vector2.getAngle(new Vector2(1, 0),t.getDirection()));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, -30)), Vector2.add(pos ,Vector2.turnDeg(ruler, 30)));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, -30)),Vector2.add(pos ,Vector2.turnDeg(ruler, -150)));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, -150)),Vector2.add(pos ,Vector2.turnDeg(ruler, -150)));
			drawLine(g2d,Vector2.add(pos ,Vector2.turnDeg(ruler, 30)),Vector2.add(pos ,Vector2.turnDeg(ruler, 150)));
			drawLine(g2d, pos, bot.getPos());
		}
	}
	
	private void drawLine(Graphics2D g2d, Vector2 v1,Vector2 v2) {
		g2d.drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
	}

}
