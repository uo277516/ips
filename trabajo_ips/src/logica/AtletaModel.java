package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.BaseDatos;
import util.DtoAssembler;

public class AtletaModel 
{
	
	public static String sql1 = "select * from atleta";
	public static String sql2 = "select * from atleta, inscripcion, competicion"
			+ "		where "
			+ " 	atleta.dni = inscripcion.dni_a and "
			+ "     inscripcion.id_c = competicion.id and "
			+ "		inscripcion.email=? and "
			+ "     competicion.nombre=?";
	
	
	
	public List<AtletaDto> getAtletas() throws SQLException
	{
		return getAllAtletas();
	}
	
	
	
	
	private List<AtletaDto> getAllAtletas() throws SQLException
	{
		List<AtletaDto> listaAtletas = new ArrayList<AtletaDto>();

        // Conexión a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql1);
            rs = pst.executeQuery();

            // Añadimos los pedidos a la lista
            listaAtletas = DtoAssembler.toAtletaDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

        for (AtletaDto atletaDto : listaAtletas) {
			System.out.println(atletaDto
			);
		}
        return listaAtletas;
	}
	
	
	public boolean atletaAlredyRegistred(String email, String cmpe)
	{
		boolean op=false;
		try {
			op= yaEstaRegistrado(email, cmpe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return op;
	}
	
	/*
	 * true si ya esta registrado, false si no (lo guay false)
	 */
	private boolean yaEstaRegistrado(String emailAtleta, String nombreCompe) throws SQLException
	{
		List<AtletaDto> listaAtletas = new ArrayList<AtletaDto>();

        // Conexión a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql2);
            pst.setString(1, emailAtleta);
            pst.setString(2, nombreCompe);
            rs = pst.executeQuery();

            // Añadimos los pedidos a la lista
            listaAtletas = DtoAssembler.toAtletaDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

//        for (AtletaDto atletaDto : listaAtletas) {
//			System.out.println(atletaDto.getDni() + " " + atletaDto.getF_nac()
//			);
//		}
        if (listaAtletas.size()>0) { //si ya esta registrado en esa carrera
        	System.out.println("Ya se ha registrado en esta competicion");
        	return true;
        }
        else {
        	System.out.println("no registrado, puede registrarse");
        	return false;
        }
	}




	
}
