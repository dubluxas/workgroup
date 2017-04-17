package petrol_station;

import java.awt.*;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.*;

/**
 * Enhanced version of standard Swing JSlider widget
 *
 * @author Ian T. Nabney
 * @version 13/03/2005
 */

public class Slider extends JComponent {

	private static final long serialVersionUID = 6511929653284188062L;

	private static final Integer[] VALUES = { 1, 2, 4 };
	private static final Hashtable<Integer, JLabel> LABELS = new Hashtable<>();
	private String labelString;
	private JLabel label;
	private JSlider slider;
	private int divider;
	private boolean isInteger;

	public Slider(String text, double min, double max, double value, int divider, boolean isInteger) {

		
		this.isInteger = isInteger;
		this.divider = divider;
		labelString = new String(text);

		if (isInteger) {

			for (int i = 0; i < VALUES.length; ++i) {
				LABELS.put(i, new JLabel(VALUES[i].toString()));
			}
			slider = new JSlider(0, VALUES.length - 1, 0);
			slider.setLabelTable(LABELS);
			label = new JLabel(text + getValue());

		} else {

			int minvalue = (int) (min * divider);
			int maxvalue = (int) (max * divider);
			int startvalue = (int) (value * divider);

			slider = new JSlider(minvalue, maxvalue, startvalue);
			label = new JLabel((text + checkNumber(value)));
		}

		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.addChangeListener(new SliderListener());

		this.setDoubleBuffered(true);
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.NORTH);
		this.add(slider, BorderLayout.SOUTH);
		this.setBorder(BorderFactory.createEtchedBorder());

	}

	private Number checkNumber(Number number) {

		if (number.doubleValue() < 1) {
			number = number.doubleValue();
			return number;
		}

		number = number.intValue();
		return number;

	}

	public void setMajorTickSpacing(int spacing) {
		slider.setMajorTickSpacing(spacing);
		repaint();
	}

	public Number getValue() {

		if (isInteger) {
			return VALUES[slider.getValue()];
		}

		double number = (double) slider.getValue() / divider;
		return checkNumber(number);

	}

	public Component getSlider() {
		return slider;
	}

	private class SliderListener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			if (!slider.getValueIsAdjusting()) {

				// value1 = (slider.getValue() / divider);
				if (isInteger) {
					label.setText(labelString + getValue());
				} else {

					double number = (slider.getValue());
					label.setText(labelString + (checkNumber((number / divider))));

				}

			}
			// System.out.println(checkNumber(getValue() / divider));
			System.out.println((getValue()));
			System.out.println("======================");
		}
	}
}
