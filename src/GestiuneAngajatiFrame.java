import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@SuppressWarnings("serial")
public class GestiuneAngajatiFrame extends JFrame {
	private JTable angajatTabel;
	private DefaultTableModel tableModel;
	private JButton editButon;
	private JButton stergeButon;
	private JButton adaugaButon;
	private AngajatOperatii angajatOperatii;

	public GestiuneAngajatiFrame() {
		angajatOperatii = new AngajatOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Angajati");
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Angajat CNP");
		tableModel.addColumn("Nume");
		tableModel.addColumn("Functie");

		angajatTabel = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(angajatTabel);

		JPanel butonPanel = new JPanel();
		butonPanel.setLayout(new FlowLayout());

		editButon = new JButton("Editare");
		editButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				int selectedRow = angajatTabel.getSelectedRow();

				if (selectedRow != -1) {
					String angajatCNP = (String) tableModel.getValueAt(selectedRow, 0);

					deschideEditeazaAngajatDialog(angajatCNP);
				} else {
					JOptionPane.showMessageDialog(null, "Niciun angajat selectat!");
				}
			}
		});

		stergeButon = new JButton("Sterge");
		stergeButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = angajatTabel.getSelectedRow();

				if (selectedRow != -1) {
					String angajatCNP = (String) tableModel.getValueAt(selectedRow, 0);

					int option = JOptionPane.showConfirmDialog(null, "Esti sigur ca vrei sa stergi acest angajat?",
							"Confirmation", JOptionPane.YES_NO_OPTION);

					if (option == JOptionPane.YES_OPTION) {
						boolean succes = angajatOperatii.stergeAngajat(angajatCNP);
						if (succes) {
							JOptionPane.showMessageDialog(null, "Angajat sters!");
							incarcaAngajati();
						} else {
							JOptionPane.showMessageDialog(null, "Stergere esuata!");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Niciun angajat selectat!");
				}
			}
		});
		adaugaButon = new JButton("Adauga angajat");
		adaugaButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				deschideAdaugaAngajatDialog();
			}
		});

		butonPanel.add(editButon);
		butonPanel.add(stergeButon);
		butonPanel.add(adaugaButon);

		add(scrollPane, BorderLayout.CENTER);
		add(butonPanel, BorderLayout.SOUTH);

		incarcaAngajati();
	}

	private void incarcaAngajati() {

		tableModel.setRowCount(0);
		List<Angajat> angajati = angajatOperatii.getListAngajati();

		for (Angajat angajat : angajati) {
			Object[] row = {angajat.getCNP(), angajat.getUsername(), angajatOperatii.getAngajatFunctieDenumire(angajat.getFunctie())};
			tableModel.addRow(row);
		}
	}

	private void deschideEditeazaAngajatDialog(String CNP) {

		Angajat angajat = angajatOperatii.getAngajatCNP(CNP);

		if (angajat != null) {
			EditeazaAngajatDialog editDialog = new EditeazaAngajatDialog(angajat);
			editDialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Angajatul nu a fost gasit!");
		}
	}

	private void deschideAdaugaAngajatDialog() {
		AdaugaAngajatDialog adaugaAngajatDialog = new AdaugaAngajatDialog();
		adaugaAngajatDialog.setVisible(true);
	}

}