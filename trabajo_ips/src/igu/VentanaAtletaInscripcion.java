package igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.SwingConstants;

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
	private Panel panel;
	private Panel pnlAtletaInsTitulo;
	private Panel pnlTxtArea;
	private JTable table;
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
	 * @throws SQLException 
	 */
	public VentanaAtletaInscripcion(CompeticionDto comp) throws SQLException {
		this.competition = comp;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLblTituloInscripciones(), BorderLayout.NORTH);
		contentPane.add(getPanel(), BorderLayout.CENTER);
	}
	private JLabel getLblTituloInscripciones() {
		if (lblTituloInscripciones == null) {
			lblTituloInscripciones = new JLabel("Inscripciones para la " + this.competition.getNombre());
			lblTituloInscripciones.setHorizontalTextPosition(SwingConstants.CENTER);
			lblTituloInscripciones.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		}
		return lblTituloInscripciones;
	}
	private JLabel getLblAtletas() {
		if (lblAtletas == null) {
			lblAtletas = new JLabel("Atletas");
			lblAtletas.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		}
		return lblAtletas;
	}
	private JLabel getLblInscripciones() {
		if (lblInscripciones == null) {
			lblInscripciones = new JLabel("Inscripciones");
			lblInscripciones.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
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
	
	private Panel getPanel() throws SQLException {
		if (panel == null) {
			panel = new Panel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPnlAtletaInsTitulo(), BorderLayout.NORTH);
			panel.add(getPanel_1_1(), BorderLayout.CENTER);
		}
		return panel;
	}
	private Panel getPnlAtletaInsTitulo() throws SQLException {
		if (pnlAtletaInsTitulo == null) {
			pnlAtletaInsTitulo = new Panel();
			pnlAtletaInsTitulo.setLayout(new GridLayout(0, 2, 0, 0));
			pnlAtletaInsTitulo.add(getLblAtletas());
			pnlAtletaInsTitulo.add(getLblInscripciones());
		}
		return pnlAtletaInsTitulo;
	}
	private Panel getPanel_1_1() throws SQLException {
		if (pnlTxtArea == null) {
			pnlTxtArea = new Panel();
			pnlTxtArea.setLayout(new BorderLayout(0, 0));
			pnlTxtArea.add(getTable(), BorderLayout.CENTER);
		}
		return pnlTxtArea;
	}
	private JTable getTable() throws SQLException {
		if (table == null) {
			table = new JTable();
			table.setSelectionBackground(new Color(106, 31, 109));
			table.setBackground(Color.LIGHT_GRAY);
			DefaultTableModel modelo = new DefaultTableModel();
			table.setModel(modelo);
			modelo.addColumn("Atleta");
			modelo.addColumn("Inscripción");
			String[][] info = new String[getAtletas().size()][2];
			List<AtletaDto> atletas = getAtletas();
			List<InscripcionDto> inscripciones = getInscripciones();
			
			for(int i = 0; i < atletas.size(); i++) {
				info[i][0] = atletas.get(i).getDni() + " - " 
						+ atletas.get(i).getNombre();
				info[i][1] = inscripciones.get(i).getCategoria() + " - " 
						+ inscripciones.get(i).getFecha() + " - " 
						+ inscripciones.get(i).getStatus();
				modelo.addRow(info[i]);
			}
		}
		return table;
	}
}
