package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import logica.CompeticionModel;

public class VentanaClasificacion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<String> clasificacion;
	private DefaultListModel<String> model;
	private JScrollPane scrollLista;
	private JLabel label;
	private JComboBox<String> cbCategorias;

	CompeticionModel cm = new CompeticionModel();
	private int id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaClasificacion frame = new VentanaClasificacion(2113);
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
	public VentanaClasificacion(int id) {
		this.id = id;
		setTitle("Clasificación:");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 488);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getListaClasificacion());
		contentPane.add(getScroll());
		contentPane.add(getLblCompeticiones());
		contentPane.add(getCbCategorias());
	}

	private JLabel getLblCompeticiones() {
		if (label == null) {
			label = new JLabel("A continuación se muestra la clasificacion de "
					+ (cm.getCompeticionById(String.valueOf(id)).get(0).getNombre()));
			label.setFont(new Font("Lucida Grande", Font.BOLD, 14));
			label.setBounds(24, 6, 889, 186);
		}
		return label;
	}

	private JComboBox<String> getCbCategorias() {
		if (cbCategorias == null) {
			cbCategorias = new JComboBox<String>();
			cbCategorias.setBounds(24, 137, 471, 22);
			cbCategorias.setModel(new DefaultComboBoxModel<String>(cm.getCategorias()));
			cbCategorias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						model.removeAllElements();
						cm.getClasificacionPorSexo(id, (String) cbCategorias.getSelectedItem())
								.forEach(s -> model.addElement(s));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			});
		}
		return cbCategorias;
	}

	private JScrollPane getScroll() {
		if (scrollLista == null) {
			scrollLista = new JScrollPane();
			scrollLista.setBounds(10, 180, 656, 221);
			scrollLista.setViewportView(clasificacion);
		}
		return scrollLista;

	}

	private JList<String> getListaClasificacion() {
		if (clasificacion == null) {
			clasificacion = new JList<String>();
			model = new DefaultListModel<String>();
			try {
				cm.getClasificacion(id).forEach(s -> model.addElement(s));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		clasificacion.setModel(model);
		return clasificacion;
	}

}
