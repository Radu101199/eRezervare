import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AngajatLoginFrame extends JFrame {

	private JLabel titluLabel;
	private JTextField usernameField;
	private JPasswordField parolaField;
	private JButton loginButon;
	private JButton inregistrareButon;
	private AngajatOperatii angajatOperatii;

	public AngajatLoginFrame() {

		angajatOperatii = new AngajatOperatii("jdbc:mysql://localhost:3306/mydatabase", "username", "password");

		setTitle("Hotel Staff");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(3, 2));

		titluLabel = new JLabel("Hotel Management");
		titluLabel.setFont(new Font("Arial", Font.BOLD, 30));
		titluLabel.setForeground(Color.BLUE);
		titluLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField();

		JLabel parolaLabel = new JLabel("Parola:");
		parolaField = new JPasswordField();

		JPanel butonPanel = new JPanel();
		butonPanel.setLayout(new FlowLayout());

		loginButon = new JButton("Login");
		loginButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(parolaField.getPassword());
				login(username, password);
			}
		});

		inregistrareButon = new JButton("Inregistrare");
		inregistrareButon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inregistrareFrame();
			}
		});

		butonPanel.add(loginButon);
		butonPanel.add(inregistrareButon);

		loginPanel.add(usernameLabel);
		loginPanel.add(usernameField);
		loginPanel.add(parolaLabel);
		loginPanel.add(parolaField);

		add(titluLabel, BorderLayout.NORTH);
		add(loginPanel, BorderLayout.CENTER);
		add(butonPanel, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
	}

	private void login(String username, String parola) {
		Angajat angajat = angajatOperatii.login(username, parola);
		if (angajat != null) {
			JOptionPane.showMessageDialog(null, "Logare reusita!");

			AngajatFrame angajatFrame = new AngajatFrame(angajat);
			angajatFrame.setVisible(true);
			dispose(); 
		} else {
			JOptionPane.showMessageDialog(null, "Logare esuata!");
		}
	}

	private void inregistrareFrame() {
		InregistrareFrame inregistrareFrame = new InregistrareFrame();
		inregistrareFrame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AngajatLoginFrame loginFrame = new AngajatLoginFrame();
				loginFrame.setVisible(true);
			}
		});
	}

}
