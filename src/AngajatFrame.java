import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AngajatFrame extends JFrame {
	
	@SuppressWarnings("unused")
	private Angajat angajat;
	private JTextField usernameField;
	private JTextField CNPField;
	private JPasswordField parolaField;
	private JTextField functieField;
	private JButton salveazaButon;
	private JButton gestioneazaAngajatiButon;
	private JButton gestioneazaCamereButon;
	private JButton adaugaPersoaneButon;
	private JButton gestioneazaRezervariButon;
	private JButton deconectareButon;
	private AngajatOperatii angajatOperatii;
	private int angajatId;

	public AngajatFrame(Angajat angajat) {
		this.angajat = angajat;
		angajatOperatii = new AngajatOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		this.angajatId = angajatOperatii.getAngajatId(angajat.getUsername(), angajat.getParola());
		setTitle("Angajat" + angajat.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(7, 2));

		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(angajat.getUsername());

		JLabel parolaLabel = new JLabel("Parola:");
		parolaField = new JPasswordField(angajat.getParola());

		JLabel CNPLabel = new JLabel("CNP:");
		CNPField = new JTextField(angajat.getCNP());
		CNPField.setEnabled(false);

		JLabel functieLabel = new JLabel("Functie:");
		int functieId = angajat.getFunctie();
		String functieNume = angajatOperatii.getFunctieNume(functieId);
		functieField = new JTextField(functieNume);
		functieField.setEnabled(false);

		String functieDenumire = angajatOperatii.getFunctieDefinitie(angajat.getFunctie());
		JLabel denumireLabel = new JLabel(functieDenumire);

		salveazaButon = new JButton("Salveaza");
		salveazaButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(parolaField.getPassword());

				boolean success = angajatOperatii.updateAngajat(angajatId, username, password);
				if (success) {
					JOptionPane.showMessageDialog(null, "Profil actualizat cu succes!");
				} else {
					JOptionPane.showMessageDialog(null, "Actualizarea profilului a esuat!");
				}
			}
		});

		deconectareButon = new JButton("Deconectare");
		deconectareButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angajatOperatii.inchideConexiune();
				dispose();
				AngajatLoginFrame loginFrame = new AngajatLoginFrame();
				loginFrame.setVisible(true);
			}
		});

		add(usernameLabel);
		add(usernameField);
		add(parolaLabel);
		add(parolaField);
		add(CNPLabel);
		add(CNPField);
		add(functieLabel);
		add(functieField);
		add(salveazaButon);
		add(deconectareButon);

		if (angajat.getFunctie() == 1) {

			gestioneazaAngajatiButon = new JButton("Gestioneaza Angajati");
			gestioneazaAngajatiButon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GestiuneAngajatiFrame gestiuneAngajatiFrame = new GestiuneAngajatiFrame();
					gestiuneAngajatiFrame.setVisible(true);
				}
			});

			gestioneazaCamereButon = new JButton("Gestioneaza Camere");
			gestioneazaCamereButon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GestiuneCamereFrame gestiuneCamereFrame = new GestiuneCamereFrame();
					gestiuneCamereFrame.setVisible(true);
				}
			});

			add(gestioneazaAngajatiButon);
			add(gestioneazaCamereButon);

		} else if (angajat.getFunctie() == 2) {

			adaugaPersoaneButon = new JButton("Adauga Persoane");
			adaugaPersoaneButon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AdaugaPersoanaDialog adaugaPersoanaDialog = new AdaugaPersoanaDialog();
					adaugaPersoanaDialog.setLocationRelativeTo(null);
					adaugaPersoanaDialog.setVisible(true);
				}
			});

			gestioneazaRezervariButon = new JButton("Gestioneaza Rezervari");
			gestioneazaRezervariButon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GestiuneRezervareFrame gestiuneRezervareFrame = new GestiuneRezervareFrame(angajatId);
					gestiuneRezervareFrame.setVisible(true);
				}
			});

			add(adaugaPersoaneButon);
			add(gestioneazaRezervariButon);

		} else {

		}

		add(denumireLabel);
		pack();
		setLocationRelativeTo(null);
	}
}
