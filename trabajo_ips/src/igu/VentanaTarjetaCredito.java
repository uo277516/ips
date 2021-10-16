package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logica.AtletaDto;
import logica.CompeticionDto;

public class VentanaTarjetaCredito extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JLabel lblNumero;
	private JTextField txtNumero;
	private JLabel lblFecha;
	private JLabel lblCvc;
	private JTextField txtFecha;
	private JTextField txtCvc;
	private JButton btnValidar;
	private JLabel lblFormato;
	private JLabel lblInfo;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaTarjetaCredito frame = new VentanaTarjetaCredito();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VentanaTarjetaCredito(VentanaInscripción vi, CompeticionDto cDto, AtletaDto aDto) {
		setTitle("Pago con tarjeta de cr\u00E9dito:");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 488);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextArea());
		contentPane.add(getLblNumero());
		contentPane.add(getTxtNumero());
		contentPane.add(getLblFecha());
		contentPane.add(getLblCvc());
		contentPane.add(getTxtFecha());
		contentPane.add(getTxtCvc());
		contentPane.add(getBtnValidar());
		contentPane.add(getLblFormato());
		contentPane.add(getLblInfo());
	}

	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
			textArea.setText("Para realizar el pago mediante tarjeta de cr\u00E9dito necesita registrar los datos de esta. \r\nUna vez validados, se emitir\u00E1 un justificante de pago realizado y pasar\u00E1 a estado inscrito en la\r\ncompeteci\u00F3n.");
			textArea.setBounds(27, 21, 634, 58);
		}
		return textArea;
	}
	private JLabel getLblNumero() {
		if (lblNumero == null) {
			lblNumero = new JLabel("N\u00FAmero Tarjeta:");
			lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNumero.setBounds(27, 120, 114, 23);
		}
		return lblNumero;
	}
	private JTextField getTxtNumero() {
		if (txtNumero == null) {
			txtNumero = new JTextField();
			txtNumero.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!compruebaSoloNumeros(txtNumero.getText())) {
							mostrarMensajeSoloNumerosTarjeta();
							txtNumero.setText("");
					}
				}

				
			});
			txtNumero.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtNumero.setBounds(183, 117, 418, 31);
			txtNumero.setColumns(10);
		}
		return txtNumero;
	}
	private void mostrarMensajeSoloNumerosTarjeta() {
		JOptionPane.showMessageDialog(this, "Formato introducido incorrecto: Solo números porfavor");
	}
	
	private boolean compruebaSoloNumeros(String text) {
		String numero="";
		String minumero="";
		int tamañotext = text.length();
		String[] numeros= {"0","1","2","3","4","5","6","7","8","9"};
		for (int i=0;i<text.length();i++) {
			numero=text.substring(i,i+1);
			for (int j=0;j<numeros.length;j++) {
				if (numero.equals(numeros[j])) {
					minumero=minumero+numeros[j];
				}
			}
		}
		if (minumero.length()==tamañotext) {
			return true;
		}else
			return false;
	}
	
	/**
	 * Comprueba si la cadena esta formada por numeros
	 * @param dni
	 * @return
	 */
	private boolean soloNumerosFecha(String fecha) {
		String numero="";
		int contador =0;
		String minumero="";
		String[] posiciones = new String[fecha.length()];
		String[] numeros= {"0","1","2","3","4","5","6","7","8","9"};
		for (int i=0;i<fecha.length();i++) {
			numero=fecha.substring(i,i+1);
			for (int j=0;j<numeros.length;j++) {
				if (numero.equals(numeros[j])) {
					minumero=minumero+numeros[j];
				}
			}
			if (numero.equals("/")) {
				posiciones[i] = "/";
				contador++;
			}
		}
		if (contador==2 && minumero.length()==8) {
			if (posiciones[2] != null && posiciones[5]!=null) {
				return true;
			}else
				return false;
		}else
			return false;
				
		
	}
	
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha de Caducidad:");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFecha.setBounds(27, 181, 131, 23);
		}
		return lblFecha;
	}
	private JLabel getLblCvc() {
		if (lblCvc == null) {
			lblCvc = new JLabel("CVC:");
			lblCvc.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCvc.setBounds(27, 251, 67, 23);
		}
		return lblCvc;
	}
	private JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!soloNumerosFecha(txtFecha.getText())) {
						mostrarMensajeErrorFecha();
						txtFecha.setText("");
					}
				}

				
			});
			txtFecha.setBounds(183, 179, 173, 31);
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}
	
	private void mostrarMensajeErrorFecha() {
		JOptionPane.showMessageDialog(this, "Formato introducido incorrecto");
	}
	private JTextField getTxtCvc() {
		if (txtCvc == null) {
			txtCvc = new JTextField();
			txtCvc.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!soloNumeros3(txtCvc.getText())) {
						mostrarMensajeErrorCVC();
						txtCvc.setText("");
					}
				}

				

				
			});
			txtCvc.setBounds(104, 249, 173, 31);
			txtCvc.setColumns(10);
		}
		return txtCvc;
	}
	
	private void mostrarMensajeErrorCVC() {
		JOptionPane.showMessageDialog(this, "Formato introducido incorrecto");
	}
	
	private boolean soloNumeros3(String fecha) {
		String numero="";
		String minumero="";
		String[] numeros= {"0","1","2","3","4","5","6","7","8","9"};
		for (int i=0;i<fecha.length();i++) {
			numero=fecha.substring(i,i+1);
			for (int j=0;j<numeros.length;j++) {
				if (numero.equals(numeros[j])) {
					minumero=minumero+numeros[j];
				}
			}
		}
		if  (minumero.length()==3) {
			return true;
		}else
			return false;
	}
	
	
	private JButton getBtnValidar() {
		if (btnValidar == null) {
			btnValidar = new JButton("Validar");
			btnValidar.setForeground(Color.WHITE);
			btnValidar.setBackground(Color.GREEN);
			btnValidar.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnValidar.setBounds(512, 303, 89, 31);
		}
		return btnValidar;
	}
	private JLabel getLblFormato() {
		if (lblFormato == null) {
			lblFormato = new JLabel("dd/MM/aaaa");
			lblFormato.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblFormato.setBounds(391, 181, 95, 23);
		}
		return lblFormato;
	}
	private JLabel getLblInfo() {
		if (lblInfo == null) {
			lblInfo = new JLabel("C\u00F3digo de 3 d\u00EDgitos que aparece en la parte trasera de la tarjeta.");
			lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblInfo.setBounds(287, 251, 374, 23);
			contentPane.add(lblInfo);
		}
		return lblInfo;
	}
}
