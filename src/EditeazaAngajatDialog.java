import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class EditeazaAngajatDialog extends JDialog {
	private JTextField usernameField;
	private JTextField CNPField;
	private JTextField functieField;
	private JPasswordField parolaField;
	private AngajatOperatii angajatOperatii;

	public EditeazaAngajatDialog(Angajat angajat) {

		angajatOperatii = new AngajatOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Editeaza Angajat");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5, 2));

		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(angajat.getUsername());
		JLabel CNPLabel = new JLabel("CNP:");
		CNPField = new JTextField(angajat.getCNP());
		JLabel parolaLabel = new JLabel("Parola:");
		parolaField = new JPasswordField(angajat.getParola());
		JLabel functieLabel = new JLabel("Functie:");
		functieField = new JTextField(angajat.getFunctie());

		JButton salveazaButton = new JButton("Salveaza");
		salveazaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String CNP = CNPField.getText();
				String parola = new String(parolaField.getPassword());
				int functie = Integer.parseInt(functieField.getText());

				angajat.setUsername(username);
				angajat.setCNP(CNP);
				angajat.setParola(parola);
				angajat.setFunctie(functie);

				boolean succes = angajatOperatii.updateAngajatManager(angajat);
				if (succes) {
					JOptionPane.showMessageDialog(null, "Angajat editat cu succes!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Editarea angajatului a esuat!");
				}
			}
		});

		add(usernameLabel);
		add(usernameField);
		add(CNPLabel);
		add(CNPField);
		add(parolaLabel);
		add(parolaField);
		add(functieLabel);
		add(functieField);
		add(salveazaButton);

		pack();
		setLocationRelativeTo(null);
	}
}
