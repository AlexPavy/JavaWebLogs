package Views;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Controller.Mediator;
import Model.LogsReader;
import javax.swing.JTextPane;

public class FiltersView {

	private JFrame frame;
	private JTextField textField;
	private JTextPane textPane;

	public Mediator med;
	private javax.swing.JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FiltersView window = new FiltersView(args[0]);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FiltersView(String entryFile) {
		med = new Mediator(this, entryFile);
		initialize();
	}

	public Mediator getMediator() {
		return med;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 497, 347);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		Canvas canvas = new Canvas();
		springLayout.putConstraint(SpringLayout.NORTH, canvas, 117,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, canvas, 55,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, canvas, 117,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, canvas, 149,
				SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(canvas);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField, 0,
				SpringLayout.WEST, canvas);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -17,
				SpringLayout.NORTH, canvas);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblFilterPar = new JLabel("Filter by");
		springLayout.putConstraint(SpringLayout.WEST, lblFilterPar, 0,
				SpringLayout.WEST, canvas);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFilterPar, -28,
				SpringLayout.NORTH, textField);
		frame.getContentPane().add(lblFilterPar);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SessionsView sessionsView = new SessionsView(med);
							sessionsView.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		frame.getContentPane().add(btnNext);

		comboBox = new JComboBox<String>();
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 70,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 28,
				SpringLayout.EAST, canvas);
		springLayout.putConstraint(SpringLayout.SOUTH, comboBox, 90,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comboBox, -32,
				SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(comboBox);

		comboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] { LogsReader.requestFileType,
						LogsReader.requestStatus, LogsReader.bot }));

		JButton btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				med.FilterLogs(comboBox.getSelectedItem().toString(),
						textField.getText());
			}
		});

		springLayout.putConstraint(SpringLayout.SOUTH, btnFilter, -96,
				SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnNext, 36,
				SpringLayout.SOUTH, btnFilter);
		springLayout.putConstraint(SpringLayout.WEST, btnNext, 0,
				SpringLayout.WEST, btnFilter);
		springLayout.putConstraint(SpringLayout.EAST, btnFilter, 0,
				SpringLayout.EAST, textField);
		frame.getContentPane().add(btnFilter);

		textPane = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, textPane, 64,
				SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, textPane, 72,
				SpringLayout.EAST, btnFilter);
		springLayout.putConstraint(SpringLayout.SOUTH, textPane, 0,
				SpringLayout.SOUTH, btnNext);
		springLayout.putConstraint(SpringLayout.EAST, textPane, 234,
				SpringLayout.EAST, btnFilter);
		frame.getContentPane().add(textPane);

	}

	public void displayResult(String show) {
		textPane.setText(show);
	}
}
