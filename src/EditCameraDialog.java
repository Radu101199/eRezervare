import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class EditCameraDialog extends JDialog {

	private JTextField suplimentPretField;
	private JTextField dotariField;
	private JTextField tipIdField;
	private CameraOperatii cameraOperatii;

	public EditCameraDialog(Camera room) {
		cameraOperatii = new CameraOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
		setTitle("Editeaza Camera");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(4, 2));

		JLabel suplimentPretLabel = new JLabel("Supliment pret:");
		suplimentPretField = new JTextField(String.valueOf(room.getSupliment()));
		JLabel dotariLabel = new JLabel("Dotari:");
		dotariField = new JTextField(room.getDotari());
		JLabel tipIdLabel = new JLabel("Tip ID:");
		tipIdField = new JTextField(String.valueOf(room.getTipId()));

		JButton salveazaButton = new JButton("Salveaza");
		salveazaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int suplimentPret = Integer.parseInt(suplimentPretField.getText());
				String dotari = dotariField.getText();
				int tipId = Integer.parseInt(tipIdField.getText());

				room.setSupliment(suplimentPret);
				room.setDotari(dotari);
				room.setTipId(tipId);

				try {
					boolean succes = cameraOperatii.editeazaCamera(room);
					if(succes) {
						JOptionPane.showMessageDialog(null, "Camera actualizata cu succes!");
						dispose();}
					else {
						JOptionPane.showMessageDialog(null, "Esec in actualizarea camerei!");
					}
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Esec in actualizarea camerei!");
				}
			}
		});

		add(suplimentPretLabel);
		add(suplimentPretField);
		add(dotariLabel);
		add(dotariField);
		add(tipIdLabel);
		add(tipIdField);
		add(salveazaButton);

		pack();
		setLocationRelativeTo(null);
	}
}
