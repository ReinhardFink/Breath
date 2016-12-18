package Breath;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class BreathPanel extends JPanel {

	private final int TIMER_WAIT_MILLIS = 50;
	private final String TIMER_LABEL_STRING = "Laufzeit";
	private final String TIMER_START_STRING = "3:00";
	private final String MESSAGE_WRONG_TIMEFORMAT = "Wrong Timeformat";
	private final String MESSAGE_HELP_TIMEFORMAT = "Use with format: mm:ss";

	private final String BREATHPERMINUTE_LABEL_STRING = "Züge/Minute";
	private final String BREATHPERMINUTE_START_STRING = "6,5";
	private final String MESSAGE_WRONG_NUMBERFORMAT = "Wrong Numberformat";
	private final String MESSAGE_HELP_NUMBERFORMAT = "Use with format: d,d";

	private final String START_BUTTON_STRING = "Start";
	private final String STOP_BUTTON_STRING = "Stop";

	private JTextField tf_time;
	private JTextField tf_breathPerMinute;
	private PaintCanvas paintCanvas;
	private Timer timer;
	private long stopTime;

	public BreathPanel() {
		this.setLayout(new BorderLayout());
		
		JPanel headerPanel = createHeaderpanel();
		this.add(headerPanel, BorderLayout.NORTH);
		
		paintCanvas = new PaintCanvas();

		this.add(paintCanvas, BorderLayout.CENTER);

		this.stopTime = 0;

		this.timer = new Timer(TIMER_WAIT_MILLIS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_time.setText(createRemainingTimeString());
				BreathPanel.this.repaint();
				if (System.currentTimeMillis() >= stopTime)
					timer.stop();
			}
		});
	}
	
	private JPanel createHeaderpanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(2, 3));

		JLabel lableTime = new JLabel(TIMER_LABEL_STRING);
		headerPanel.add(lableTime);

		JLabel lableBreathPerMinute = new JLabel(BREATHPERMINUTE_LABEL_STRING);
		headerPanel.add(lableBreathPerMinute);

		JButton start = createStartButton();
		headerPanel.add(start);

		tf_time = new JTextField(TIMER_START_STRING);
		headerPanel.add(tf_time);

		// JTextField breathPerMinute = new JTextField();
		tf_breathPerMinute = new JTextField(BREATHPERMINUTE_START_STRING);
		headerPanel.add(tf_breathPerMinute);

		JButton stop = createStopButton();
		headerPanel.add(stop);
		return headerPanel;
	}

	private JButton createStartButton() {
		JButton startButton = new JButton(START_BUTTON_STRING);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stopTime = System.currentTimeMillis()
							+ parseTime(tf_time.getText());
					paintCanvas
							.setBreathPerMinute(parseBreathPerMinute(tf_breathPerMinute
									.getText()));
					timer.start();
				} catch (InputMismatchException ie) {
					if (ie.getMessage().equals(MESSAGE_WRONG_TIMEFORMAT))
						tf_time.setText(MESSAGE_HELP_TIMEFORMAT);
					if (ie.getMessage().equals(MESSAGE_WRONG_NUMBERFORMAT))
						tf_breathPerMinute.setText(MESSAGE_HELP_NUMBERFORMAT);
				}
			}
		});
		return startButton;
	}

	private JButton createStopButton() {
		JButton stopButton = new JButton(STOP_BUTTON_STRING);
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.stop();
			}
		});
		return stopButton;
	}

	private long parseTime(String timeString) throws InputMismatchException {
		Scanner sc = new Scanner(timeString);
		sc.useDelimiter(":");
		int minutes = 0;
		int seconds = 0;
		try {
			minutes = sc.nextInt();
			if (sc.hasNextInt())
				seconds = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(MESSAGE_WRONG_TIMEFORMAT);
			sc.close();
			throw new InputMismatchException(MESSAGE_WRONG_TIMEFORMAT);
		}
		// System.out.println((minutes * 60 + seconds) * 1000);
		sc.close();
		return (minutes * 60 + seconds) * 1000;
	}

	private double parseBreathPerMinute(String breathPerMinuteString)
			throws InputMismatchException {
		Scanner sc = new Scanner(breathPerMinuteString);
		double breathPerMinute = 0;
		try {
			breathPerMinute = sc.nextDouble();
		} catch (InputMismatchException e) {
			System.out.println(MESSAGE_WRONG_NUMBERFORMAT);
			sc.close();
			throw new InputMismatchException(MESSAGE_WRONG_NUMBERFORMAT);
		}
		sc.close();
		return breathPerMinute;
	}

	private String createRemainingTimeString() {
		long remainingTime = stopTime - System.currentTimeMillis();
		StringBuffer currentTimeString = new StringBuffer();
		currentTimeString.append(new Long(remainingTime / (1000 * 60))
				.toString());
		currentTimeString.append(":");
		remainingTime = remainingTime % (1000 * 60);
		if (remainingTime / 1000 < 10)
			currentTimeString.append("0");
		currentTimeString.append(new Long(remainingTime / 1000).toString());
		currentTimeString.append(":");
		remainingTime = remainingTime % (1000);
		if (remainingTime <= 0)
			currentTimeString.append("00");
		else {
			if (remainingTime / 10 < 10)
				currentTimeString.append("0");
			currentTimeString.append(new Long(remainingTime / 10).toString());
		}
		return currentTimeString.toString();
	}
}
