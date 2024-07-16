import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class InregistrareFrame extends JFrame {
	
	private JLabel titluLabel;
	private JTextField usernameField, CNPField;
	private JPasswordField parolaField;
	private JComboBox<String> functieComboBox;
	private JButton inregistrareButon;
	private AngajatOperatii angajatOperatii;

	public InregistrareFrame() {
		angajatOperatii = new AngajatOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Angajat Inregistrare");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		titluLabel = new JLabel("Inregistrare");
		titluLabel.setFont(new Font("Arial", Font.BOLD, 30));
		titluLabel.setForeground(Color.BLUE);
		titluLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel inregistrarePanel = new JPanel();
		inregistrarePanel.setLayout(new GridLayout(5, 2));

		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField();

		JLabel CNPLabel = new JLabel("CNP:");
		CNPField = new JTextField();

		JLabel parolaLabel = new JLabel("Parola:");
		parolaField = new JPasswordField();

		JLabel functieLabel = new JLabel("Functie:");
		String[] functii = { "Manager", "Camerista", "Receptioner", "Bucatar", "Ospatar" };
		functieComboBox = new JComboBox<>(functii);

		inregistrareButon = new JButton("Register");
		inregistrareButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String CNP = CNPField.getText();
				String parola = new String(parolaField.getPassword());
				String selectedFunction = (String) functieComboBox.getSelectedItem();

				register(username,CNP , parola, selectedFunction);
			}
		});

		inregistrarePanel.add(usernameLabel);
		inregistrarePanel.add(usernameField);
		inregistrarePanel.add(CNPLabel);
		inregistrarePanel.add(CNPField);
		inregistrarePanel.add(parolaLabel);
		inregistrarePanel.add(parolaField);
		inregistrarePanel.add(functieLabel);
		inregistrarePanel.add(functieComboBox);
		inregistrarePanel.add(inregistrareButon);

		add(titluLabel, BorderLayout.NORTH);
		add(inregistrarePanel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null); 
	}

	private void register(String username,String CNP,  String password, String function) {
		boolean success = angajatOperatii.inregistrare(username, CNP,  password, function);
		if (success) {
			JOptionPane.showMessageDialog(null, "Registration successful");
		} else {
			JOptionPane.showMessageDialog(null, "Registration failed");
		}
	}
}
