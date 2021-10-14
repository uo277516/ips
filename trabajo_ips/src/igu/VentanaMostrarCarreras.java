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

public class VentanaMostrarCarreras extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIntroduzca;
	private JButton btnValidar;
	private JTextArea txtInfo;
	private JComboBox<CompeticionDto> cmboxCarreras;
	private JTextField textFecha;
	private InscripcionModel ins;
	private AtletaModel atl;
	private CompeticionModel comp;
	private JButton btnAceptar;

	/**
	 * Launch the application.
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
		contentPane.add(getTxtIntroduzca());
		contentPane.add(getBtnValidar());
		contentPane.add(getTxtInfo());
		contentPane.add(getCmboxCarreras());
		contentPane.add(getTextFecha());
		contentPane.add(getBtnAceptar());
	}

	private JTextField getTxtIntroduzca() {
		if (txtIntroduzca == null) {
			txtIntroduzca = new JTextField();
			txtIntroduzca.setText("Competiciones abiertas actualmente:");
			txtIntroduzca.setBounds(24, 21, 276, 20);
			txtIntroduzca.setColumns(10);
		}
		return txtIntroduzca;
	}

	private JButton getBtnValidar() {
		if (btnValidar == null) {
			btnValidar = new JButton("Validar");
			btnValidar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(soloNumeros(getTextFecha().getText())) {
						cmboxCarreras.setModel(new DefaultComboBoxModel<CompeticionDto>(comp.getCompetcionesFecha(textFecha.getText())));
					}
				}

				
			});
			btnValidar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnValidar.setForeground(Color.WHITE);
			btnValidar.setBackground(Color.GREEN);
			btnValidar.setBounds(406, 18, 89, 23);
		}
		return btnValidar;
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
			cmboxCarreras.setBounds(10, 137, 485, 22);
			cmboxCarreras.setModel(new DefaultComboBoxModel<CompeticionDto>(comp.getCompeticionesArray()));
		}
		return cmboxCarreras;
	}
	private JTextField getTextFecha() {
		if (textFecha == null) {
			textFecha = new JTextField();
			textFecha.setEditable(false);
			textFecha.setBounds(310, 21, 86, 20);
			textFecha.setColumns(10);
			textFecha.setText(cambiarFormatoFecha());
			cambiarFormatoFecha();
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
			btnAceptar = new JButton("Aceptar");
			btnAceptar.setBackground(Color.GREEN);
			btnAceptar.setForeground(Color.WHITE);
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnAceptar.setBounds(423, 275, 89, 23);
		}
		return btnAceptar;
	}
}
