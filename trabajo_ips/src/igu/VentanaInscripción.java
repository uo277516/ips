package igu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import logica.AtletaDto;
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
	private JButton btnValidar;
	private JLabel lblInfoJus;
	private InscripcionModel ins;
	private AtletaModel atl;
	@SuppressWarnings("unused")
	private CompeticionModel comp;
	private CompeticionDto cSeleccionada;
	@SuppressWarnings("unused")
	private VentanaMostrarCarreras vC;
	private JTextArea textArea;
	private AtletaDto atleta;
	private JButton btnSiguiente;

	
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
		contentPane.add(getBtnSiguiente());
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
					else if (!registradoAtletaEnBase())
					{
						mostrarErrorDatosNoRegistrados();
					}
				    else if(esMenor()) {
				    	mostrarErrorMenor();
				    }
					else if (!yaRegistradoEnlaCarrera())
					{
						textArea.setEnabled(true);
						lblInfoJus.setVisible(true);
						btnSiguiente.setEnabled(true);
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
	
	protected void mostrarErrorDatosNoRegistrados() {
		JOptionPane.showMessageDialog(this, "Sus datos todavía no han sido registrados");
		
	}
	private boolean registradoAtletaEnBase() {
		if (atl.atletaEnBase(txtEmail.getText())) return false;
		else return true;
	}
	
	protected void mostrarErrorMenor() {
		JOptionPane.showMessageDialog(this, "Usted es menor de edad, por lo tanto no puede participar en la competiciÃ³n");
		
	}
	
	private boolean esMenor() {
		int years = atl.yearsAtleta(txtEmail.getText());
		if(years>=18) {
			return false;
		}else {
			return true;
		}
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
		atleta = ins.findAtletaEmail(txtEmail.getText()); 
		return s+="Nombre del atleta: " + atleta.getNombre() + "\n" +
			"Competición: " + cSeleccionada.getNombre() + "\n" +
			"Categoría: " + ins.getCategoriaByDniId(atleta.getDni(), cSeleccionada.getId()) + "\n" +
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
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.setEnabled(false);
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int seleccion = JOptionPane.showOptionDialog(
							   null,
							   "Método de pago", 
							   "Seleccione una opción para pagar su inscripción",
							   JOptionPane.YES_NO_CANCEL_OPTION,
							   JOptionPane.QUESTION_MESSAGE,
							   null,    // null para icono por defecto.
							   new Object[] { "Tarjeta de crédito", "Tranferencia"},   // null para YES, NO y CANCEL
							   "opcion 1");

							if (seleccion != -1)
							   System.out.println("seleccionada opcion " + (seleccion + 1));	
							if (seleccion==0) //tarjeta de credito
								{
									mostrarVentanaTarjetaDeCredito();
								}
							else if (seleccion==1)
								{
									mostrarVentanaTransferencia();
								}}
			});
			btnSiguiente.setBackground(Color.GREEN);
			btnSiguiente.setForeground(Color.WHITE);
			btnSiguiente.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnSiguiente.setBounds(488, 351, 106, 23);
		}
		return btnSiguiente;
	}
	protected void mostrarVentanaTransferencia() {
		// TODO Auto-generated method stub
		
	}
	protected void mostrarVentanaTarjetaDeCredito() {
		this.dispose();
		//CompeticionDto competicion = crearCompeticion();
		VentanaTarjetaCredito vPal = new VentanaTarjetaCredito(this, cSeleccionada, atleta);
		vPal.setLocationRelativeTo(this);
		vPal.setVisible(true);
	}
}
