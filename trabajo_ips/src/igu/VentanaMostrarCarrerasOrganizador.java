package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import logica.CompeticionDto;
import logica.CompeticionModel;
import javax.swing.JLabel;

public class VentanaMostrarCarrerasOrganizador extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<CompeticionDto> cmboxCarreras;
	private CompeticionModel comp;
	private JButton btnAceptar;
	private JLabel lblCompeticiones;
	private JTextArea txtInfoCarrera;
	private VentanaInicial vi;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaMostrarCarrerasOrganizador frame = new VentanaMostrarCarrerasOrganizador();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	/**
	 * Create the frame.
	 * @param ventanaInicial 
	 */
	public VentanaMostrarCarrerasOrganizador(VentanaInicial ventanaInicial) {
		this.vi=ventanaInicial;
		comp = new CompeticionModel();
		setTitle("Selecci\u00F3n de Competici\u00F3n:");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 348);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getCmboxCarreras());
		contentPane.add(getBtnAceptar());
		contentPane.add(getLblCompeticiones());
		contentPane.add(getTxtInfoCarrera());
	}
	private JComboBox<CompeticionDto> getCmboxCarreras() {
		if (cmboxCarreras == null) {
			cmboxCarreras = new JComboBox<CompeticionDto>();
			cmboxCarreras.setBounds(24, 137, 471, 22);
			cmboxCarreras.setModel(new DefaultComboBoxModel<CompeticionDto>(comp.getCompeticionesArray()));
		}
		return cmboxCarreras;
	}
	
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.setOpaque(true);
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						mostrarVentanaAtletaInscripcion();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnAceptar.setBackground(Color.GREEN);
			btnAceptar.setForeground(Color.BLACK);
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnAceptar.setBounds(423, 275, 89, 23);
		}
		return btnAceptar;
	}
	
	private void mostrarVentanaAtletaInscripcion() throws SQLException {
		VentanaAtletaInscripcion vai = new VentanaAtletaInscripcion((CompeticionDto) getCmboxCarreras().getSelectedItem());
		this.dispose();
		vai.setVisible(true); 
	}
	private JLabel getLblCompeticiones() {
		if (lblCompeticiones == null) {
			lblCompeticiones = new JLabel("Competiciones");
			lblCompeticiones.setFont(new Font("Lucida Grande", Font.BOLD, 25));
			lblCompeticiones.setBounds(24, 6, 389, 46);
		}
		return lblCompeticiones;
	}
	private JTextArea getTxtInfoCarrera() {
		if (txtInfoCarrera == null) {
			txtInfoCarrera = new JTextArea();
			txtInfoCarrera.setFocusable(false);
			txtInfoCarrera.setEditable(false);
			txtInfoCarrera.setText("Se muestra: Nombre - Fecha de la competici??n - Tipo - Distancia - Cuota \n - Fecha de fin de inscripci??n");
			txtInfoCarrera.setBounds(24, 76, 471, 49);
		}
		return txtInfoCarrera;
	}
}
