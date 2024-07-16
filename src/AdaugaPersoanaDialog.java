import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AdaugaPersoanaDialog extends JDialog {
	private JTextField numeField;
	private JTextField serieNrField;
	private JTextField cnpField;
	private JTextField stradaNrField;
	private JTextField telefonField;
	private JButton adaugaButon;
	private AngajatOperatii angajatOperatii;

	public AdaugaPersoanaDialog() {
		angajatOperatii = new AngajatOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Adauga Persoana");
		setSize(300, 300);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		

		JPanel panel = new JPanel(new GridLayout(6, 2));

		panel.add(new JLabel("Nume:"));
		numeField = new JTextField();
		panel.add(numeField);

		panel.add(new JLabel("SerieNr:"));
		serieNrField = new JTextField();
		panel.add(serieNrField);

		panel.add(new JLabel("CNP:"));
		cnpField = new JTextField();
		panel.add(cnpField);

		panel.add(new JLabel("StradaNr:"));
		stradaNrField = new JTextField();
		panel.add(stradaNrField);

		panel.add(new JLabel("Telefon:"));
		telefonField = new JTextField();
		panel.add(telefonField);

		adaugaButon = new JButton("Adauga");
		adaugaButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nume = numeField.getText();
				String serieNr = serieNrField.getText();
				String cnp = cnpField.getText();
				String stradaNr = stradaNrField.getText();
				String telefon = telefonField.getText();

				Persoana persoanaNoua = new Persoana(nume, serieNr, cnp, stradaNr, telefon);

				boolean succes = angajatOperatii.adaugaPersoanaBD(persoanaNoua);

				if (succes) {
					JOptionPane.showMessageDialog(null, "Persoana adaugata cu succes!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Adaugarea persoanei a esuat!");
				}
			}
		});
		panel.add(adaugaButon);
		add(panel);
        setLocationRelativeTo(null);
	}
}
