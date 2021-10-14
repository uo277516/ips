package util;

import java.sql.SQLException;

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
	 
}
