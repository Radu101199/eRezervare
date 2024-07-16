import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class GestiuneRezervareFrame extends JFrame {

	private JTextField dataOraPlecareField, dataOraSosireField, dataCheckinField, dataCheckoutField;
	private JButton rezervareButon;
	private JButton cautaButon;
	private JButton arataTotButon;
	private JButton stergeButton;
	private JTable rezervariTabel;
	private DefaultTableModel tableModel;
	private JComboBox<String> cnpComboBox;
	private JComboBox<String> camereComboBox;
	private RezervareOperatii rezervareOperatii;
	@SuppressWarnings("unused")
	private int angajatId;
	public String persoanaCNP;
	public String cameraId;

	public GestiuneRezervareFrame(int angajatId) {

		rezervareOperatii = new RezervareOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		this.angajatId = angajatId;
		setTitle("Gestiune Rezervari");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 4));

		JLabel persoanaCNPLabel = new JLabel("Persoana CNP:");
		cnpComboBox = new JComboBox<>();
		JLabel cameraIdLabel = new JLabel("Camera ID:");
		camereComboBox = new JComboBox<>();
		JLabel dataOraPlecareLabel = new JLabel("Data Ora Plecare (yyyy-MM-dd HH:mm):");
		dataOraPlecareField = new JTextField();
		JLabel dataOraSosireLabel = new JLabel("Data Ora Sosire (yyyy-MM-dd HH:mm):");
		dataOraSosireField = new JTextField();
		JLabel dataCheckinLabel = new JLabel("Data Checkin (yyyy-MM-dd HH:mm):");
		dataCheckinField = new JTextField();
		JLabel dataCheckoutLabel = new JLabel("Data Checkout (yyyy-MM-dd HH:mm):");
		dataCheckoutField = new JTextField();

		rezervareButon = new JButton("Rezerva");
		rezervareButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String turistCNP = cnpComboBox.getSelectedItem().toString();
				int cameraId = Integer.parseInt(camereComboBox.getSelectedItem().toString());
				LocalDateTime dataOraPlecare = LocalDateTime.parse(dataOraPlecareField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime dataOraSosire = LocalDateTime.parse(dataOraSosireField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime dataCheckin = LocalDateTime.parse(dataCheckinField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime dataCheckout = LocalDateTime.parse(dataCheckoutField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				boolean succes = rezervareOperatii.rezervaCamera(turistCNP, cameraId, dataOraPlecare, dataOraSosire, dataCheckin, dataCheckout, LocalDateTime.now(), angajatId);

				if (succes) {
					JOptionPane.showMessageDialog(null, "Reservare reusita!");
					incarcaRezervari();
				} else {
					JOptionPane.showMessageDialog(null, "Reservare esuata!");
				}
			}
		});

		inputPanel.add(persoanaCNPLabel);
		inputPanel.add(cnpComboBox);
		inputPanel.add(cameraIdLabel);
		inputPanel.add(camereComboBox);
		inputPanel.add(dataOraPlecareLabel);
		inputPanel.add(dataOraPlecareField);
		inputPanel.add(dataOraSosireLabel);
		inputPanel.add(dataOraSosireField);
		inputPanel.add(dataCheckinLabel);
		inputPanel.add(dataCheckinField);
		inputPanel.add(dataCheckoutLabel);
		inputPanel.add(dataCheckoutField);

		JPanel butonPanel = new JPanel();
		butonPanel.setLayout(new FlowLayout());

		cautaButon = new JButton("Cauta dupa CNP");
		cautaButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String persoanaCNP = cnpComboBox.getSelectedItem().toString();
				cautaRezervari(persoanaCNP);
			}
		});

		arataTotButon = new JButton("Arata tot");
		arataTotButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				incarcaRezervari();
			}
		});

		stergeButton = new JButton("Sterge");
		stergeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = rezervariTabel.getSelectedRow();
				if (row != -1) {
					int reservareId = (int) tableModel.getValueAt(row, 0);
					boolean succes = rezervareOperatii.stergeRezervare(reservareId);
					if (succes) {
						JOptionPane.showMessageDialog(null, "Rezervare stearsa");
						incarcaRezervari();
					} else {
						JOptionPane.showMessageDialog(null, "Stergere esuata");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nicio rezervare selectata");
				}
			}
		});

		butonPanel.add(rezervareButon);
		butonPanel.add(cautaButon);
		butonPanel.add(arataTotButon);
		butonPanel.add(stergeButton);

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Numar Rezervare");
		tableModel.addColumn("Turist CNP");
		tableModel.addColumn("Camera ID");
		tableModel.addColumn("Data Ora Plecare");
		tableModel.addColumn("Data Ora Sosire");
		tableModel.addColumn("Data Checkin");
		tableModel.addColumn("Data Checkout");
		tableModel.addColumn("Data Rezervare");
		tableModel.addColumn("Angajat ID");

		rezervariTabel = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(rezervariTabel);

		add(inputPanel, BorderLayout.NORTH);
		add(butonPanel, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.SOUTH);

		incarcaRezervari();

		incarcaCnpComboBox();
		incarcaCameraComboBox();

		cnpComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				persoanaCNP = cnpComboBox.getSelectedItem().toString();
			}
		});

		camereComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cameraId = camereComboBox.getSelectedItem().toString();
			}
		});

		pack();
		setLocationRelativeTo(null);
	}



	private void cautaRezervari(String persoanaCNP) {
		ResultSet resultSet = rezervareOperatii.cautaRezervare(persoanaCNP);
		if (resultSet != null) {
			try {
				tableModel.setRowCount(0); 
				while (resultSet.next()) {
					int nrRezervare = resultSet.getInt("NrInregistrare");
					int cameraId = resultSet.getInt("CameraID");
					String dataOraPlecare = resultSet.getString("DataOraPlecare");
					String dataOraSosire = resultSet.getString("DataOraSosire");
					String dataCheckin = resultSet.getString("DataCheckin");
					String dataCheckout = resultSet.getString("DataCheckout");
					String dataOraInregistrare = resultSet.getString("DataOraRezervare");
					int angajatId = resultSet.getInt("AngajatID");

					Object[] rowData = { nrRezervare, persoanaCNP, cameraId, dataOraPlecare, dataOraSosire, dataCheckin, dataCheckout, dataOraInregistrare, angajatId };
					tableModel.addRow(rowData);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cautare esuata!");
		}
	}

	private void incarcaRezervari() {
		ResultSet resultSet = rezervareOperatii.getRezervari();
		if (resultSet != null) {
			try {
				tableModel.setRowCount(0); // Clear the table
				while (resultSet.next()) {
					int nrRezervare = resultSet.getInt("NrInregistrare");
					int personId = resultSet.getInt("PersonID");
					int cameraId = resultSet.getInt("CameraID");
					String dataOraPlecare = resultSet.getString("DataOraPlecare");
					String dataOraSosire = resultSet.getString("DataOraSosire");
					String dataCheckin = resultSet.getString("DataCheckin");
					String dataCheckout = resultSet.getString("DataCheckout");
					String dataRezervare = resultSet.getString("DataOraRezervare");
					int angajatId = resultSet.getInt("AngajatID");

					String persoanaCNP = rezervareOperatii.getTuristCNP(personId);

					Object[] rowData = {
							nrRezervare,
							persoanaCNP,
							cameraId,
							dataOraPlecare,
							dataOraSosire,
							dataCheckin,
							dataCheckout,
							dataRezervare,
							angajatId
					};
					tableModel.addRow(rowData);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Incarcare esuata!");
		}
	}

	private void incarcaCnpComboBox() {
		cnpComboBox.removeAllItems();
		ResultSet resultSet = rezervareOperatii.getPersoane(); 
		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					String persoanaCNP = resultSet.getString("CNP"); 
					cnpComboBox.addItem(persoanaCNP); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Esuare a incarcarii dupa CNP!");
		}
	}

	private void incarcaCameraComboBox() {
		camereComboBox.removeAllItems(); 
		ResultSet resultSet = rezervareOperatii.getCamere();
		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					int cameraId = resultSet.getInt("Id"); 
					camereComboBox.addItem(Integer.toString(cameraId)); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Esuare a incarcarii camerelor!");
		}
	}

}
