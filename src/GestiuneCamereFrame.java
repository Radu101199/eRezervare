import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("serial")
public class GestiuneCamereFrame extends JFrame {

	private JTable camereTable;
	private DefaultTableModel tableModel;
	private JButton editButon;
	private JButton stergeButon;
	private JButton adaugaButon;
	private CameraOperatii cameraOperatii;

	public GestiuneCamereFrame() {
		cameraOperatii = new CameraOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Camera");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Numar");
		tableModel.addColumn("Supliment Pret");
		tableModel.addColumn("Dotari");
		tableModel.addColumn("Tip");

		camereTable = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(camereTable);

		JPanel butonPanel = new JPanel();
		butonPanel.setLayout(new FlowLayout());

		editButon = new JButton("Edit");
		editButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				int selectedRow = camereTable.getSelectedRow();
				if (selectedRow != -1) {
					String nr = (String) tableModel.getValueAt(selectedRow, 0);
					deschideEditCameraDialog(nr);
				} else {
					JOptionPane.showMessageDialog(null, "Nicio camera selectat");
				}
			}
		});

		stergeButon = new JButton("Sterge");
		stergeButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = camereTable.getSelectedRow();
				if (selectedRow != -1) {
					String value = (String) tableModel.getValueAt(selectedRow, 0);
					int nr = Integer.parseInt(value);
					int option = JOptionPane.showConfirmDialog(null, "Esti sigur ca vrei sa stergi aceasta camera?",
							"Confirmation", JOptionPane.YES_NO_OPTION);

					if (option == JOptionPane.YES_OPTION) {
						boolean succes;
						try {
							succes = cameraOperatii.stergeCamera(nr);
							if (succes) {
								JOptionPane.showMessageDialog(null, "Camera stearsa!");
								incarcaCamere();
							}
							else {
								JOptionPane.showMessageDialog(null, "Stergere esuata!");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Niciun angajat selectat!");
				}
			}
		});

		adaugaButon = new JButton("Adauga Camera");
		adaugaButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				deschideAdaugaCameraDialog();
			}
		});

		butonPanel.add(editButon);
		butonPanel.add(stergeButon);
		butonPanel.add(adaugaButon);

		add(scrollPane, BorderLayout.CENTER);
		add(butonPanel, BorderLayout.SOUTH);

		incarcaCamere();

		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private void incarcaCamere() {

		tableModel.setRowCount(0);
		List<Camera> camere = cameraOperatii.getCamere();
		for (Camera camera : camere) {
			Object[] row = {camera.getNr(), camera.getSupliment(), camera.getDotari(), cameraOperatii.getCameraTipNume(camera.getTipId())};
			tableModel.addRow(row);
		}
	}

	private void deschideEditCameraDialog(String nr) {

		Camera camera = cameraOperatii.getCameraByNr(nr);
		if (camera != null) {
			EditCameraDialog editDialog = new EditCameraDialog(camera);
			editDialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Camera nu a fost gasita!");
		}
	}

	private void deschideAdaugaCameraDialog() {

		AdaugaCameraDialog adaugaCameraDialog = new AdaugaCameraDialog();
		adaugaCameraDialog.setVisible(true);
	}
}


