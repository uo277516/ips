package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaInicial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicial frame = new VentanaInicial();
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
	public VentanaInicial() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 363);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPregunat = new JLabel("\u00BFDesea entrar como atleta o como organizador?");
		lblPregunat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPregunat.setBounds(109, 89, 302, 70);
		contentPane.add(lblPregunat);
		
		JButton btnAtleta = new JButton("Atleta");
		btnAtleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elegirAsAtleta();
			}
		});
		btnAtleta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAtleta.setBounds(80, 210, 131, 35);
		contentPane.add(btnAtleta);
		
		JButton btnOrganizador = new JButton("Organizador");
		btnOrganizador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elegirAsOrganizador();
			}
		});
		btnOrganizador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOrganizador.setBounds(303, 210, 122, 35);
		contentPane.add(btnOrganizador);
		
		JLabel llblBienvenido = new JLabel("\u00A1Bienvenido!");
		llblBienvenido.setFont(new Font("Tahoma", Font.PLAIN, 16));
		llblBienvenido.setBounds(218, 50, 131, 29);
		contentPane.add(llblBienvenido);
	}

	protected void elegirAsOrganizador() {
		int seleccion = JOptionPane.showOptionDialog(
				   this,
				   "¿Desea conocer el estado de las diferentes inscripciones o las clasificiones?", 
				   "Inicio como organizador",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,    // null para icono por defecto.
				   new Object[] { "Inscripciones", "Clasificaciones"},   // null para YES, NO y CANCEL
				   "opcion 1");

				if (seleccion != -1)
				   System.out.println("seleccionada opcion " + (seleccion + 1));	
				if (seleccion==0) //organizador
					{
						mostrarVentanaInscripciones();  //tania
					}
				else if (seleccion==1)
					{
						mostrarVentanaCalificaciones(); //moises
					}}
	

	private void mostrarVentanaCalificaciones() {
		this.dispose();
		//CompeticionDto competicion = crearCompeticion();
		VentanaClasificación vPal = new VentanaClasificación(this);
		vPal.setLocationRelativeTo(this);
		vPal.setVisible(true);
	}

	private void mostrarVentanaInscripciones() {
		this.dispose();
		//CompeticionDto competicion = crearCompeticion();
		VentanaMostrarCarrerasOrganizador vPal = new VentanaMostrarCarrerasOrganizador(this);
		vPal.setLocationRelativeTo(this);
		vPal.setVisible(true);
	}

	protected void elegirAsAtleta() {
		int seleccion = JOptionPane.showOptionDialog(
				   this,
				   "¿Desea inscribirse o conocer el estado de sus inscripciones?", 
				   "Inicio como atleta",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,    // null para icono por defecto.
				   new Object[] { "Inscribirme", "Conocer mi estado"},   // null para YES, NO y CANCEL
				   "opcion 1");

				if (seleccion != -1)
				   System.out.println("seleccionada opcion " + (seleccion + 1));	
				if (seleccion==0) //tarjeta de credito
					{
						mostrarVentanaCarreras();
					}
				else if (seleccion==1)
					{
						mostrarVentanaConocerEstado();
					}}

	private void mostrarVentanaConocerEstado() {
		this.dispose();
		//CompeticionDto competicion = crearCompeticion();
		VentanaInscripcionesAtleta vPal = new VentanaInscripcionesAtleta(this);
		vPal.setLocationRelativeTo(this);
		vPal.setVisible(true);
	}

	private void mostrarVentanaCarreras() {
		this.dispose();
		//CompeticionDto competicion = crearCompeticion();
		VentanaMostrarCarreras vPal = new VentanaMostrarCarreras(this);
		vPal.setLocationRelativeTo(this);
		vPal.setVisible(true);
		
	}
}

