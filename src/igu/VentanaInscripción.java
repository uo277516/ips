package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.AtletaModel;
import logica.CompeticionDto;
import logica.CompeticionModel;
import logica.InscripcionModel;

public class VentanaInscripción extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPedir;
	private TextField txtEmail;
	private Label lblEmail;
	private JLabel lblCarrera;
	private JButton btnValidar;
	private TextField txtJustificante;
	private JComboBox<CompeticionDto> comboBox;
	private JLabel lblInfoJus;
	private InscripcionModel ins;
	private AtletaModel atl;
	private CompeticionModel comp;

	private InscripcionModel im;
	private JButton btnPagar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInscripción frame = new VentanaInscripción();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaInscripción() {
		ins = new InscripcionModel();
		atl = new AtletaModel();
		comp = new CompeticionModel();
		setTitle("Inscripci\u00F3n de carreras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 453);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblPedir());
		contentPane.add(getTxtEmail());
		contentPane.add(getLblEmail());
		contentPane.add(getLblCarrera());
		contentPane.add(getBtnValidar());
		contentPane.add(getTxtJustificante());
		contentPane.add(getComboBox());
		contentPane.add(getLblInfoJus());
		contentPane.add(getBtnPagar());
	}

	private JLabel getLblPedir() {
		if (lblPedir == null) {
			lblPedir = new JLabel("Para inscribirte en una nueva carrera, por favor rellene los siguientes campos:");
			lblPedir.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblPedir.setBounds(10, 28, 460, 29);
		}
		return lblPedir;
	}

	private TextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new TextField();
			txtEmail.setBounds(70, 72, 189, 21);
		}
		return txtEmail;
	}

	private Label getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new Label("E-mail:");
			lblEmail.setBounds(20, 72, 59, 21);
		}
		return lblEmail;
	}

	private JLabel getLblCarrera() {
		if (lblCarrera == null) {
			lblCarrera = new JLabel("Carrera en la que desea inscribirse:");
			lblCarrera.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblCarrera.setBounds(20, 111, 206, 13);
		}
		return lblCarrera;
	}

	protected boolean comprobarCampos() {
		// TODO Auto-generated method stub
		return false;
	}

	private JButton getBtnValidar() {
		if (btnValidar == null) {
			btnValidar = new JButton("Validar");
			btnValidar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (camposCorrectos()) {
						txtJustificante.setText("ok");
						txtJustificante.setEnabled(true);
						lblInfoJus.setVisible(true);
						btnPagar.setVisible(true);
						try {
							im.crearPreinscripcion(txtEmail.getText(),
									((CompeticionDto) comboBox.getModel().getSelectedItem()).getId(), "efectivo");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						if (txtEmail.getText() == "")
							mostrarErrorVacio();
						else
							mostrarErrorNoRegis();
					}
				}
			});
			btnValidar.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnValidar.setBackground(SystemColor.activeCaption);
			btnValidar.setBounds(20, 156, 106, 21);
		}
		return btnValidar;
	}

	protected void mostrarErrorVacio() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "El campo email no puede estar vacío");
	}

	protected void mostrarErrorNoRegis() {
		JOptionPane.showMessageDialog(this, "Tu email todavía no ha sido registrado");
	}

	protected boolean camposCorrectos() {
		String email = txtEmail.getText();
		return ins.isEmailRegistred(email);
	}

	private TextField getTxtJustificante() {
		if (txtJustificante == null) {
			txtJustificante = new TextField();
			txtJustificante.setForeground(Color.BLACK);
			txtJustificante.setBackground(Color.WHITE);
			txtJustificante.setEnabled(false);
			txtJustificante.setEditable(false);
			txtJustificante.setBounds(20, 242, 352, 136);
		}
		return txtJustificante;
	}

	private JComboBox<CompeticionDto> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<CompeticionDto>();
			comboBox.setBounds(254, 108, 264, 21);
			comboBox.setModel(new DefaultComboBoxModel<CompeticionDto>(comp.getCompeticionesArray()));
		}
		return comboBox;
	}

	private JLabel getLblInfoJus() {
		if (lblInfoJus == null) {
			lblInfoJus = new JLabel("A continuaci\u00F3n, se imprimir\u00E1 el siguiente justificante informante:");
			lblInfoJus.setVisible(false);
			lblInfoJus.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblInfoJus.setBounds(20, 202, 374, 21);
		}
		return lblInfoJus;
	}

	private JButton getBtnPagar() {
		if (btnPagar == null) {
			btnPagar = new JButton("Pagar");
			btnPagar.setVisible(false);
			btnPagar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showOptionDialog(null, "Elige el método de pago", "Metodo de pago",
							getDefaultCloseOperation(), getDefaultCloseOperation(), null, null, null);
				}
			});
			btnPagar.setBounds(503, 355, 89, 23);
		}
		return btnPagar;
	}
}
