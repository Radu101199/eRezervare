import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AdaugaAngajatDialog extends JDialog {
	private JTextField usernameField;
	private JTextField cnpField;
	private JTextField passwordField;
	private JTextField functieField;
	private JButton adaugaButon;
	private AngajatOperatii angajatOperatii;

	public AdaugaAngajatDialog() {
		angajatOperatii = new AngajatOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Adauga Angajat");
		setSize(300, 200);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(5, 2));

		panel.add(new JLabel("Username:"));
		usernameField = new JTextField();
		panel.add(usernameField);

		panel.add(new JLabel("CNP:"));
		cnpField = new JTextField();
		panel.add(cnpField);

		panel.add(new JLabel("Parola:"));
		passwordField = new JTextField();
		panel.add(passwordField);

		panel.add(new JLabel("Functie:"));
		functieField = new JTextField();
		panel.add(functieField);

		adaugaButon = new JButton("Adauga");
		adaugaButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String cnp = cnpField.getText();
				String password = passwordField.getText();
				int functie = Integer.parseInt(functieField.getText());

				Angajat angajatNou = new Angajat(username, cnp, password, functie);

				boolean succes = angajatOperatii.adaugaAngajatBD(angajatNou);

				if (succes) {
					JOptionPane.showMessageDialog(null, "Angajat adaugat cu succes!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Adaugarea angajatului a esuat!");
				}
			}
		});
		panel.add(adaugaButon);
		add(panel);
	}
}
