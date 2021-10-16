package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
	private JTextArea txtInfo;
	private JTextField textFecha;
	private InscripcionModel ins;
	private AtletaModel atl;
	private CompeticionModel comp;
	private JButton btnAceptar;
	private JLabel lblCompeticiones;
	private JScrollPane scrollPane;
	private JTable table;

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
	 * @throws ParseException 
	 */
	public VentanaMostrarCarreras() {
		ins = new InscripcionModel();
		atl = new AtletaModel();
		comp = new CompeticionModel();
		setTitle("Selecci\u00F3n de Competici\u00F3n:");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 688, 503);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTxtInfo());
		contentPane.add(getTextFecha());
		contentPane.add(getBtnAceptar());
		contentPane.add(getLblCompeticiones());
		contentPane.add(getScrollPane());
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
			txtInfo.setText("Se muestra: \r\nNombre---fecha competici\u00F3n---tipo---distancia---cuota---fecha fin inscripci\u00F3n---numero de plazas");
			txtInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtInfo.setBounds(24, 83, 642, 54);
		}
		return txtInfo;
	}
	private JTextField getTextFecha() {
		if (textFecha == null) {
			textFecha = new JTextField();
			textFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
			textFecha.setEditable(false);
			textFecha.setBounds(312, 26, 86, 29);
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
					try {
						if (table.getSelectedRow() != -1) {
							pasarAInscripcion();
						}else {
							JOptionPane.showMessageDialog(null, "Error: Seleccione una carrera para registrarse");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnAceptar.setBackground(Color.GREEN);
			btnAceptar.setForeground(Color.WHITE);
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnAceptar.setBounds(547, 430, 119, 23);
		}
		return btnAceptar;
	}
	protected void pasarAInscripcion() throws SQLException {
		this.dispose();
		CompeticionDto competicion = crearCompeticion();
		VentanaInscripción vPal = new VentanaInscripción(this, competicion);
		vPal.setLocationRelativeTo(this);
		vPal.setVisible(true);
	}

	private CompeticionDto crearCompeticion() throws SQLException {
		int fila = table.getSelectedRow();
		String identificador = (String) table.getValueAt(fila,0);
		System.out.println(identificador);
		List<CompeticionDto> compe = comp.getCompeticionById(identificador);
		// TODO Auto-generated method stub
		return compe.get(0);
	}

	private JLabel getLblCompeticiones() {
		if (lblCompeticiones == null) {
			lblCompeticiones = new JLabel("Competiciones abiertas actualmente a d\u00EDa:");
			lblCompeticiones.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCompeticiones.setBounds(24, 32, 278, 13);
		}
		return lblCompeticiones;
	}
	private JScrollPane getScrollPane(){
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 148, 656, 271);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
			table.setFont(new Font("Tahoma", Font.PLAIN, 13));
			table.setSelectionBackground(Color.YELLOW);
			table.setBackground(Color.LIGHT_GRAY);
			DefaultTableModel modelo = new DefaultTableModel();
			table.setModel(modelo);
			modelo.addColumn("Iden");modelo.addColumn("Nombre");modelo.addColumn("Fecha Comp");modelo.addColumn("Tipo");modelo.addColumn("Distancia");modelo.addColumn("Cuota");modelo.addColumn("Fecha Fin Insc");modelo.addColumn("Plazas");
			List<CompeticionDto> competiciones = comp.getCompetcionesFechaLista(textFecha.getText());
			String[][] info = new String[competiciones.size()][8];
			//List<AtletaDto> atletas = getAtletas();
			//List<InscripcionDto> inscripciones = getInscripciones();
			
			for(int i = 0; i < competiciones.size(); i++) {
				info[i][0] = String.valueOf(competiciones.get(i).getId());
				info[i][1] = competiciones.get(i).getNombre();info[i][2] = competiciones.get(i).getF_comp();
				info[i][3] = competiciones.get(i).getTipo();info[i][4] = competiciones.get(i).getDistancia()+"km";
				info[i][5] = String.valueOf(competiciones.get(i).getCuota())+"\u20AC";info[i][6] = competiciones.get(i).getF_fin();
				info[i][7] = String.valueOf(competiciones.get(i).getNum_plazas());
				modelo.addRow(info[i]);
			}
		}
		
		return table;
	}
	
	
}
