package buscaminas;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class prueba extends JFrame {
	private JButton b = new JButton();
	public prueba() {
		setSize(200, 300);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(b);
		b.addActionListener(new Listener());
		setVisible(true);
	}
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,((JButton)e.getSource()).getName());
		}
	}
	public static void main(String[] args) {
		// prueba();
		int num = 5;
		System.out.println(String.valueOf(num));

	}

}
