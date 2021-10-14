package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logica.AtletaModel;
import logica.CompeticionDto;
import logica.CompeticionModel;
import logica.InscripcionModel;
import javax.swing.JLabel;

public class VentanaMostrarCarreras extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtInfo;
	private JComboBox<CompeticionDto> cmboxCarreras;
	private JTextField textFecha;
	private InscripcionModel ins;
	private AtletaModel atl;
	private CompeticionModel comp;
	private JButton btnAceptar;
	private JLabel lblCompeticiones;

	/**
	 * Launch the application.eqwewq
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMostrarCarreras frame = new VentanaMostrarCarreras();
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
	public VentanaMostrarCarreras() {
		ins = new InscripcionModel();
		atl = new AtletaModel();
		comp = new CompeticionModel();
		setTitle("Selecci\u00F3n de Competici\u00F3n:");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 348);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTxtInfo());
		contentPane.add(getCmboxCarreras());
		contentPane.add(getTextFecha());
		contentPane.add(getBtnAceptar());
		contentPane.add(getLblCompeticiones());
	}
	
	/**
	 * Comprueba si la cadena esta formada por numeros
	 * @param dni
	 * @return
	 */
	private boolean soloNumeros(String fecha) {
		System.out.println(textFecha.getText());
		String numero="";
		int contador =0;
		String minumero="";
		String[] numeros= {"0","1","2","3","4","5","6","7","8","9"};
		for (int i=0;i<fecha.length();i++) {
			numero=fecha.substring(i,i+1);
			for (int j=0;j<numeros.length;j++) {
				if (numero.equals(numeros[j])) {
					minumero=minumero+numeros[j];
				}
			}
			if (numero.equals("/")) {
				contador++;
			}
		}
		if (contador==2 && minumero.length()==8) {
			return true;
		}else
			return false;
				
		
	}
	
	private JTextArea getTxtInfo() {
		if (txtInfo == null) {
			txtInfo = new JTextArea();
			txtInfo.setText("Se muestra: \r\nNombre---fecha competici\u00F3n---tipo---distancia---cuota---fecha fin inscripci\u00F3n\r\n---numero de plazas disponibles");
			txtInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
			txtInfo.setBounds(24, 72, 420, 54);
		}
		return txtInfo;
	}
	private JComboBox<CompeticionDto> getCmboxCarreras() {
		if (cmboxCarreras == null) {
			cmboxCarreras = new JComboBox<CompeticionDto>();
			cmboxCarreras.setBounds(24, 139, 485, 22);
			cmboxCarreras.setModel(new DefaultComboBoxModel<CompeticionDto>(comp.getCompetcionesFecha(cambiarFormatoFecha())));
		}
		return cmboxCarreras;
	}
	private JTextField getTextFecha() {
		if (textFecha == null) {
			textFecha = new JTextField();
			textFecha.setEditable(false);
			textFecha.setBounds(241, 22, 86, 20);
			textFecha.setColumns(10);
			textFecha.setText(cambiarFormatoFecha());
			//cambiarFormatoFecha();
		}
		return textFecha;
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
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Siguiente");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pasarAInscripcion();
				}
			});
			btnAceptar.setBackground(Color.GREEN);
			btnAceptar.setForeground(Color.WHITE);
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnAceptar.setBounds(393, 275, 119, 23);
		}
		return btnAceptar;
	}
	protected void pasarAInscripcion() {
		this.dispose();
		VentanaInscripción vPal = new VentanaInscripción(this, (CompeticionDto) cmboxCarreras.getSelectedItem());
		vPal.setLocationRelativeTo(this);
		vPal.setVisible(true);
	}

	private JLabel getLblCompeticiones() {
		if (lblCompeticiones == null) {
			lblCompeticiones = new JLabel("Competiciones abiertas actualmente a d\u00EDa:");
			lblCompeticiones.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblCompeticiones.setBounds(24, 28, 223, 13);
		}
		return lblCompeticiones;
	}
}
