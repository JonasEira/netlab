package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JavaView extends JFrame implements DataInterface {
	/**
	 * Window for data presentation
	 */
	private static final long serialVersionUID = 1L;

	private JavaModel dataModel;

	private JEditorPane jta;

	public JavaView(JavaModel jvm) {
		dataModel = jvm;
		int cols = 18;
		BorderLayout bl = new BorderLayout();
		JPanel pan = new JPanel();
		JPanel pan2 = new JPanel();
		pan.setLayout(bl);
		jta = new JEditorPane();
		jta.setContentType("text/xml");
		JTextField jtf = new JTextField(cols);
		JButton getData = new JButton("Apply");
		getData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String town = jtf.getText();
				dataModel.dataRequest(town);
			}
		});

		pan.add(jta, BorderLayout.CENTER);
		pan2.add(jtf);
		pan2.add(getData);
		pan.add(pan2, BorderLayout.SOUTH);
		this.add(pan);
		int width = 800;
		int height = 600;
		this.setSize(width, height);
		this.setVisible(true);
	}

	public void addModel(JavaModel jm) {
		dataModel = jm;
	}

	@Override
	public void updatedData() {
		jta.setText(dataModel.getReply());
	}
}
