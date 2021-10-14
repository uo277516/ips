package util;

import java.sql.SQLException;

import logica.AtletaModel;
import logica.CompeticionModel;
import logica.InscripcionModel;

public class Main {

	public static void main(String[] args) throws SQLException
	{
		new CompeticionModel().getCompeticionesArray();
		new InscripcionModel().getInscripciones();
		new AtletaModel().getAtletas();
		System.out.println();
		new AtletaModel().atletaAlredyRegistred("natalia@email.com", "Cross de Tineo");
		
	}
	
}
