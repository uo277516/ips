package igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.AtletaDto;
import logica.AtletaModel;
import logica.CompeticionDto;
import logica.InscripcionDto;
import logica.InscripcionModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextArea;

public class VentanaAtletaInscripcion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private CompeticionDto competition;
	private JLabel lblTituloInscripciones;
	private JLabel lblAtletas;
	private JLabel lblInscripciones;
	private JTextArea txtAreaAtletaInfo;
	private JTextArea txtrHey;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaAtletaInscripcion frame = new VentanaAtletaInscripcion();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @param comp 
	 */
	public VentanaAtletaInscripcion(CompeticionDto comp) {
		this.competition = comp;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblTituloInscripciones());
		contentPane.add(getLblAtletas());
		contentPane.add(getLblInscripciones());
		contentPane.add(getTxtAreaAtletaInfo());
		contentPane.add(getTxtrHey());
	}
	private JLabel getLblTituloInscripciones() {
		if (lblTituloInscripciones == null) {
			lblTituloInscripciones = new JLabel("Inscripciones para la " + this.competition.getNombre());
			lblTituloInscripciones.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
			lblTituloInscripciones.setBounds(23, 6, 403, 40);
		}
		return lblTituloInscripciones;
	}
	private JLabel getLblAtletas() {
		if (lblAtletas == null) {
			lblAtletas = new JLabel("Atletas");
			lblAtletas.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
			lblAtletas.setBounds(23, 54, 136, 33);
		}
		return lblAtletas;
	}
	private JLabel getLblInscripciones() {
		if (lblInscripciones == null) {
			lblInscripciones = new JLabel("Inscripciones");
			lblInscripciones.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
			lblInscripciones.setBounds(166, 54, 260, 33);
		}
		return lblInscripciones;
	}
	
	private List<AtletaDto> getAtletas() throws SQLException{
		AtletaModel am = new AtletaModel();
		return am.getAletasDeUnaCompeticion(this.competition.getId());
	}
	
	private List<InscripcionDto> getInscripciones() throws SQLException{
		InscripcionModel im = new InscripcionModel();
		return im.getInscripcionesDeUnaCompeticion(this.competition.getId());
	}
	private JTextArea getTxtAreaAtletaInfo() {
		if (txtAreaAtletaInfo == null) {
			txtAreaAtletaInfo = new JTextArea();
			txtAreaAtletaInfo.setText("hey");
			txtAreaAtletaInfo.setBounds(23, 99, 127, 156);
		}
		return txtAreaAtletaInfo;
	}
	private JTextArea getTxtrHey() {
		if (txtrHey == null) {
			txtrHey = new JTextArea();
			txtrHey.setText("hey");
			txtrHey.setBounds(166, 99, 260, 156);
		}
		return txtrHey;
	}
	
	private String getInfoAtletas() throws SQLException {
		List<AtletaDto> atletas = getAtletas();
		String str = "";
		for(AtletaDto a : atletas) {
			str += a.getDni() + " - " + a.getNombre() + "\n";
		}
		return str;
	}
}
