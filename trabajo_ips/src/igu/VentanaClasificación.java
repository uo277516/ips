package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import logica.CompeticionModel;

public class VentanaClasificación extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JList<String> clasificacion;

	CompeticionModel cm = new CompeticionModel();
	private VentanaInicial vi;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaClasificación frame = new VentanaClasificación();
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
	public VentanaClasificación(VentanaInicial vi) {
		this.vi=vi;
		setTitle("Pago por transferencia:");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 488);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextArea());
		contentPane.add(getListaClasificacion());
	}

	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
			textArea.setText(getJustificante(10));
			textArea.setBounds(27, 21, 634, 58);
		}
		return textArea;
	}

	private String getJustificante(int precio) {
		return "La cuenta para realizar la transferencia será X.\n" + "La cantidad a abonar será de " + precio + "€\n"
				+ "Su inscripción ha pasado a pendiente de pago, dispone de 48 horas para abonar la cantidad indicada.";
	}

	private JList<String> getListaClasificacion() {
		if (clasificacion == null) {
			clasificacion = new JList<String>();
			DefaultListModel model = new DefaultListModel<>();
			clasificacion.setModel(model);
			try {
				model.addAll(cm.getClasificacion(2113));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return clasificacion;
	}

}
