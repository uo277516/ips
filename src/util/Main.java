package util;

import java.sql.SQLException;

<<<<<<< HEAD
import igu.VentanaInicial;

public class Main {

	public static void main(String[] args) throws SQLException
	{
//		new CompeticionModel().getCompeticionesArray();
//		new InscripcionModel().getInscripciones();
//		new AtletaModel().getAtletas();	
//		System.out.println();
//		new AtletaModel().atletaAlredyRegistred("natalia@email.com", "Cross de Tineo");
//		System.out.println(new InscripcionModel().findAtletaEmail("jose@email.com"));
		VentanaInicial frame = new VentanaInicial();
		frame.setVisible(true);
	}
	
=======
import logica.CompeticionModel;
import logica.InscripcionModel;

public class Main {

	public static void main(String[] args) throws SQLException
	{
		InscripcionModel im = new InscripcionModel();
		CompeticionModel cm = new CompeticionModel();
		cm.listarClasificacion(141415);
		cm.listarClasificacionPorCategoria(141415, "a.sexo = 'masculino'");
		im.getInscripciones();
		
		
	}
	 
>>>>>>> branch 'UO264802' of https://github.com/uo277516/ips.git
}
