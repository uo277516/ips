package igu;

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
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class VentanaInscripción extends JFrame {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPedir;
	private TextField txtEmail;
	private Label lblEmail;
	private JButton btnValidar;
	private JLabel lblInfoJus;
	private InscripcionModel ins;
	private AtletaModel atl;
	private CompeticionModel comp;
	private CompeticionDto cSeleccionada;
	private VentanaMostrarCarreras vC;
	private JTextArea textArea;
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaInscripción frame = new VentanaInscripción();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @param competicionDto 
	 * @param ventanaMostrarCarreras 
	 */
	public VentanaInscripción(VentanaMostrarCarreras ventanaMostrarCarreras, CompeticionDto competicionDto) {
		ins = new InscripcionModel();
		atl=new AtletaModel();
		comp = new CompeticionModel();
		this.vC=ventanaMostrarCarreras;
		this.cSeleccionada=competicionDto;
		setTitle("Inscripci\u00F3n de carreras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 422);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblPedir());
		contentPane.add(getTxtEmail());
		contentPane.add(getLblEmail());
		contentPane.add(getBtnValidar());
		contentPane.add(getLblInfoJus());
		contentPane.add(getTextArea());
	}
	private JLabel getLblPedir() {
		if (lblPedir == null) {
			lblPedir = new JLabel("Para inscribirte en una nueva carrera, por favor introduzca su email:");
			lblPedir.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblPedir.setBounds(20, 28, 460, 29);
		}
		return lblPedir;
	}
	private TextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new TextField();
			txtEmail.setBounds(101, 72, 189, 21);
		}
		return txtEmail;
	}
	private Label getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new Label("E-mail:");
			lblEmail.setBounds(36, 72, 59, 21);
		}
		return lblEmail;
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
					if (txtEmail.getText().equals(""))
					{
						mostrarErrorVacio();
					}
					else if (!yaRegistradoEnlaCarrera())
					{
						textArea.setEnabled(true);
						lblInfoJus.setVisible(true);
						inscribirParticipante();
						textArea.setText(getInformacion());
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
			btnValidar.setBounds(20, 131, 106, 21);
		}
		return btnValidar;
	}
	
	protected void inscribirParticipante() {
		System.out.println(txtEmail.getText());
		System.out.println(cSeleccionada.getId());
		float n = 10.0f+cSeleccionada.getCuota();
		ins.agregarInscripcion(txtEmail.getText(),cSeleccionada.getId(),n,cambiarFormatoFecha());
		
		
	}

	protected boolean haySuficientesPlazas() {
		int plazas=cSeleccionada.getNum_plazas();
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
		//CompeticionDto c = (CompeticionDto) comboBox.getSelectedItem();
		String nombre = cSeleccionada.getNombre();
		return atl.atletaAlredyRegistred(email, nombre);
	}

	protected void mostrarErrorVacio() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "El campo email no puede estar vacío");
	}
	/*
	 * Como resultado de la inscripción, el atleta recibirá un justificante con su nombre, 
	 * la competición, categoría en la que participará (ver siguientes HUs),
	 *  fecha inscripción y cantidad que debe abonar en concepto de inscripción.
	 */
	private String getInformacion() {
		String s = "";
		float n = 10.0f+cSeleccionada.getCuota();
		AtletaDto nombre = ins.findAtletaEmail(txtEmail.getText()); 
		return s+="Nombre del atleta: " + nombre.getNombre() + "\n" +
			"Competición: " + cSeleccionada.getNombre() + "\n" +
			"Categoría: " + "esto lo hace tania:)" + "\n" +
			"Fecha de inscripción: " + cambiarFormatoFecha() + "\n" +
			"Cantidad a abonar: " + n  +" euros (cuota+gastos adicionales)";
	}
	
	private String cambiarFormatoFecha() {
		String fechaString = String.valueOf(LocalDate.now());
		String[] fechaPartida = fechaString.split("-");
		String result ="";
		for (int i = 0; i < fechaPartida.length; i++) {
			result="/"+fechaPartida[i]+result;
		}
		return result.substring(1);
		
	}
	private JLabel getLblInfoJus() {
		if (lblInfoJus == null) {
			lblInfoJus = new JLabel("A continuaci\u00F3n, se imprimir\u00E1 el siguiente justificante informante:");
			lblInfoJus.setVisible(false);
			lblInfoJus.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblInfoJus.setBounds(20, 172, 374, 21);
		}
		return lblInfoJus;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
			textArea.setBounds(20, 206, 404, 139);
		}
		return textArea;
	}
}
