import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class AdaugaCameraDialog extends JDialog {
	private JTextField nrField;
	private JTextField suplimentField;
	private JTextField dotariField;
	private JTextField tipIdField;
	private JButton adaugaButon;
	private CameraOperatii cameraOperatii;
	
	public AdaugaCameraDialog() {
		cameraOperatii = new CameraOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Adauga Camera");
		setSize(300, 200);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(5, 2));

		panel.add(new JLabel("Numarul Camerei:"));
		nrField = new JTextField();
		panel.add(nrField);

		panel.add(new JLabel("Supliment Pret:"));
		suplimentField = new JTextField();
		panel.add(suplimentField);

		panel.add(new JLabel("Dotari:"));
		dotariField = new JTextField();
		panel.add(dotariField);

		panel.add(new JLabel("Tip ID:"));
		tipIdField = new JTextField();
		panel.add(tipIdField);

		adaugaButon = new JButton("Adauga");
		adaugaButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nr = nrField.getText();
				int supliment = Integer.parseInt(suplimentField.getText());
				String dotari = dotariField.getText();
				int tipId = Integer.parseInt(tipIdField.getText());

				Camera cameraNoua = new Camera(nr, supliment, dotari, tipId);

				boolean success;
				try {
					success = cameraOperatii.adaugaCamera(cameraNoua);
					if (success) {
						JOptionPane.showMessageDialog(null, "Camera adaugata cu succes!");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Adaugarea camerei a esuat!");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(adaugaButon);
		add(panel);
		setLocationRelativeTo(null);
	}
}
