package Breath;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaintCanvas extends JPanel {

	private final int BORDER_X_PERCENT = 5;
	private final int BORDER_Y_PERCENT = 5;

	private final int OVAL_WIDTH = 20;

	private Dimension panelSize;
	private double[] sin;
	private int pixelPerSchwingung;

	private long startTime;
	private double breathPerMinute;

	public PaintCanvas() {
		this.panelSize = new Dimension();
		this.sin = null;
		this.pixelPerSchwingung = 0;
		this.startTime = 0;
		this.breathPerMinute = 0;
	}


	public void setBreathPerMinute(double breathPerMinute) {
		this.breathPerMinute = breathPerMinute;
		this.startTime = System.currentTimeMillis();
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// System.out.println("Size: " + this.getSize());
		if (!this.getSize().equals(panelSize))
			calcSinus(this.getSize());
		int startX = panelSize.width * BORDER_X_PERCENT / 100;
		int startY = panelSize.height * (100 - BORDER_Y_PERCENT) / 100;
		int stretch = panelSize.height * (100 - 2 * BORDER_Y_PERCENT) / 2 / 100;
		int timeOffset = (int) ((System.currentTimeMillis() - startTime)
				* pixelPerSchwingung * breathPerMinute / (60 * 1000));
		for (int i = 0; i < sin.length - 1; i++) {
			g2.drawLine(startX + i,
					(int) (startY - (stretch + sin[(i + timeOffset)
							% sin.length]
							* stretch)), startX + i + 1,
					(int) (startY - (stretch + sin[(i + 1 + timeOffset)
							% sin.length]
							* stretch)));
		}
		int height = (int) (stretch + sin[(sin.length / 2 + timeOffset)
				% sin.length]
				* stretch);
		g2.fillOval(startX + sin.length / 2 - OVAL_WIDTH / 2, startY - height,
				OVAL_WIDTH, height);
	}

	private void calcSinus(Dimension panelSize) {
		this.panelSize = panelSize;
		this.pixelPerSchwingung = panelSize.width
				* (100 - 2 * BORDER_X_PERCENT) / 100 / 2;
		this.sin = new double[panelSize.width * (100 - 2 * BORDER_X_PERCENT)
				/ 100];
		double dx = 4 * Math.PI
				/ (panelSize.width * (100 - 2 * BORDER_X_PERCENT) / 100);
		for (int i = 0; i < sin.length; i++) {
			sin[i] = Math.sin(i * dx);
		}
	}
}
