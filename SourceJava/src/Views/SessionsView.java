package Views;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import Controller.Mediator;
import Model.LogsReader;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

public class SessionsView {

	public JFrame frame;
	private JCheckBox chckbxExportOnlyInt;
	private JTextPane textField;

	public Mediator med;
	private javax.swing.JComboBox<String> comboBox;

	/**
	 * Create the application.
	 */
	public SessionsView(Mediator mediator) {
		med = mediator;
		med.sessionsView = this;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1081, 844);
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

		JLabel lblFilterPar = new JLabel("Choose Sessions creation method");
		springLayout.putConstraint(SpringLayout.WEST, lblFilterPar, 0,
				SpringLayout.WEST, canvas);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFilterPar, -65,
				SpringLayout.NORTH, canvas);
		frame.getContentPane().add(lblFilterPar);

		JButton btnExport = new JButton("Export Sessions");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				med.exportSessions(chckbxExportOnlyInt.isSelected());
			}
		});
		frame.getContentPane().add(btnExport);

		comboBox = new JComboBox<String>();
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 20,
				SpringLayout.SOUTH, lblFilterPar);
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 45,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, comboBox, 92,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comboBox, -426,
				SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(comboBox);

		comboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] { LogsReader.byExpiration,  LogsReader.byReferer }));

		JButton btnGenerate = new JButton("Generate sessions");
		springLayout.putConstraint(SpringLayout.EAST, btnExport, 0,
				SpringLayout.EAST, btnGenerate);
		springLayout.putConstraint(SpringLayout.WEST, btnGenerate, 20,
				SpringLayout.WEST, frame.getContentPane());
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				med.generateSessions(comboBox.getSelectedItem().toString());
			}
		});

		springLayout.putConstraint(SpringLayout.SOUTH, btnGenerate, -96,
				SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnExport, 36,
				SpringLayout.SOUTH, btnGenerate);
		springLayout.putConstraint(SpringLayout.WEST, btnExport, 0,
				SpringLayout.WEST, btnGenerate);
		frame.getContentPane().add(btnGenerate);

		textField = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 35,
				SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, textField, 28,
				SpringLayout.EAST, btnGenerate);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, 0,
				SpringLayout.SOUTH, btnExport);
		springLayout.putConstraint(SpringLayout.EAST, textField, 810,
				SpringLayout.EAST, btnGenerate);
		frame.getContentPane().add(textField);

		chckbxExportOnlyInt = new JCheckBox("Export only int");
		chckbxExportOnlyInt.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxExportOnlyInt, 35,
				SpringLayout.SOUTH, canvas);
		springLayout.putConstraint(SpringLayout.WEST, chckbxExportOnlyInt, 0,
				SpringLayout.WEST, btnExport);
		frame.getContentPane().add(chckbxExportOnlyInt);

	}

	public void displayResult(String show) {
		textField.setText(show);
	}
}
