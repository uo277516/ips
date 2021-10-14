package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.AtletaDto;
import logica.AtletaModel;
import logica.CompeticionDto;
import logica.CompeticionModel;
import logica.InscripcionModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextField;
import java.awt.Label;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JComboBox;

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
		atl=new AtletaModel();
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
					if (txtEmail.getText()=="")
					{
						System.out.println("q cojones");
						mostrarErrorVacio();
					}
					else if (!yaRegistradoEnlaCarrera())
					{
						txtJustificante.setText("ok");
						txtJustificante.setEnabled(true);
						lblInfoJus.setVisible(true);
						inscribirParticipante();
					} else if (yaRegistradoEnlaCarrera())
					{
						mostrarErrorYaRegistrado();
					}
					else if (!haySuficientesPlazas())
					{
						mostrarErrorPlazas();
					}
							
				}
			});
			btnValidar.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnValidar.setBackground(SystemColor.activeCaption);
			btnValidar.setBounds(20, 156, 106, 21);
		}
		return btnValidar;
	}
	
	protected void inscribirParticipante() {
		CompeticionDto c = (CompeticionDto) comboBox.getSelectedItem();
		System.out.println(txtEmail.getText());
		System.out.println(c.getId());

		ins.agregarInscripcion(txtEmail.getText(),c.getId());
	}

	protected boolean haySuficientesPlazas() {
		CompeticionDto c = (CompeticionDto) comboBox.getSelectedItem();
		int plazas=c.getNum_plazas();
		if (plazas>0) return true;
		else return false;
	}

	protected void mostrarErrorPlazas() {
		JOptionPane.showMessageDialog(this, "No hay plazas disponibles,lo sentimos");
		
	}

	protected void mostrarErrorYaRegistrado() {
		JOptionPane.showMessageDialog(this, "Ya te has inscrito en esta carrera");
		
	}

	protected boolean yaRegistradoEnlaCarrera() {
		String email=txtEmail.getText();
		CompeticionDto c = (CompeticionDto) comboBox.getSelectedItem();
		String nombre = c.getNombre();
		return atl.atletaAlredyRegistred(email, nombre);
	}

	protected void mostrarErrorVacio() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "El campo email no puede estar vacío");
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
}
