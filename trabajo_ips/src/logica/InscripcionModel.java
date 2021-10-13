package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.BaseDatos;
import util.DtoAssembler;

public class InscripcionModel 
{
	
public static String sql1 = "select * from inscripcion";
public static String sql2 = "select * from inscripcion where inscripcion.email=?";

	
	public List<InscripcionDto> getInscripciones() throws SQLException
	{
		return getAllInscripciones();
	}
	
	
	private List<InscripcionDto> getAllInscripciones() throws SQLException
	{
		List<InscripcionDto> listaInscrpcines = new ArrayList<InscripcionDto>();

        // Conexión a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql1);
            rs = pst.executeQuery();

            // Añadimos los pedidos a la lista
            listaInscrpcines = DtoAssembler.toInscripcionDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

        for (InscripcionDto atletaDto : listaInscrpcines) {
			System.out.println(atletaDto.getEmail()
			);
		}
        return listaInscrpcines;
	}
	
	
	public boolean isEmailRegistred(String email)
	{
		boolean op=false;
		try {
			op= existsThisEmail(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("algo fue mal");
			e.printStackTrace();
		}
		return op;
	}
	
	
	private boolean existsThisEmail(String email) throws SQLException
	{
		List<InscripcionDto> listaAtletas = new ArrayList<InscripcionDto>();

        // Conexión a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql2);
            pst.setString(1, email);
            rs = pst.executeQuery();

            // Añadimos los pedidos a la lista
            listaAtletas = DtoAssembler.toInscripcionDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
//        	if (rs==null) return false;
//        	else {
            rs.close();
            pst.close();
            c.close();
//        	}
        }
        
        if (listaAtletas.size()>0) return true;
        else return false;
	}

}
