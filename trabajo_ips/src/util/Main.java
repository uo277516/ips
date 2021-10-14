package util;

import java.sql.SQLException;

import logica.InscripcionModel;

public class Main {

	public static void main(String[] args) throws SQLException
	{
		new InscripcionModel().getInscripciones();
	}
	
}
