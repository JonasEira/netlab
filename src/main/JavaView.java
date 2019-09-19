package main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class JavaView extends JFrame {
	/**
	 *  Window for data presentation
	 */
	private static final long serialVersionUID = 1L;

	private DataInterface dataInterface;
	
	public JavaView() {
		int rows = 8;
		int cols = 18;
		BorderLayout bl = new BorderLayout();
		JPanel pan = new JPanel();
		JPanel pan2 = new JPanel();
		pan.setLayout(bl);
		JTextArea jta = new JTextArea(rows, cols);
		JTextField jtf = new JTextField(cols);
		JButton getData = new JButton("Apply");
		getData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				String town = jtf.getText();
				System.out.println(town);
				try {
					dataInterface.datarequest(town);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JTextField town = new JTextField(cols/2);
		town.setEnabled(false);
		pan.add(jta, BorderLayout.CENTER);
		pan2.add(town);
		pan2.add(jtf);
		pan2.add(getData);
		pan.add(pan2, BorderLayout.SOUTH);
		this.add(pan);
		int width = 500;
		int height = 200;
		this.setSize(width, height);
		this.setVisible(true);
	}
	public void addDataInterface(DataInterface d) {
		dataInterface = d;
	}
}
