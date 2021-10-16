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
public static String sql3 = "insert into inscripcion (dni_a, id_c, email, estado,cantidad_pagada,fecha) values (?,?,?,'Pre-inscrito',?,?)";
public static String sql4 = "select * from atleta where atleta.email=?";
public static String sql6 = "select * from inscripcion where inscripcion.email=? order by inscripcion.fecha asc";
public static String sql5 = "select * from inscripcion where inscripcion.dni_a=? order by inscripcion.fecha asc";




	public AtletaDto findAtletaEmail(String email)
	{
		AtletaDto a = null;
		try {
			a = findAtletaByEmail(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	

	private AtletaDto findAtletaByEmail(String email) throws SQLException
	{
		AtletaDto a = null;
		Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql4);
            pst.setString(1, email);
            //System.out.println(pst);
            rs = pst.executeQuery();
            rs.next();
            
            a = DtoAssembler.toAtletaDto(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
        return a;
	}
	
	public List<InscripcionDto> getInscripciones() throws SQLException
	{
		return getAllInscripciones();
	}
	
	
	private List<InscripcionDto> getAllInscripciones() throws SQLException
	{
		List<InscripcionDto> listaInscrpcines = new ArrayList<InscripcionDto>();

        // Conexi�n a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql1);
            rs = pst.executeQuery();

            // A�adimos los pedidos a la lista
            listaInscrpcines = DtoAssembler.toInscripcionDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

        for (InscripcionDto atletaDto : listaInscrpcines) {
			System.out.println(atletaDto
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

        // Conexi�n a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql2);
            pst.setString(1, email);
            rs = pst.executeQuery();

            // A�adimos los pedidos a la lista
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

	
	public void agregarInscripcion(String text, int id, float f, String fecha)
	{
		try {
			agregarParticipante(text, id, f,fecha);
		} catch (SQLException e) {
			System.out.println("no se pudo a�adir -- inscripcion model");
			e.printStackTrace();
		}
	}
	

	private void agregarParticipante(String email, int id, float f, String fecha) throws SQLException {
		AtletaDto a = findAtletaEmail(email);
		String dni = a.getDni();
		// Conexi�n a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
//        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql3);
            pst.setString(1, dni);
            pst.setInt(2, id);
            pst.setString(3, email);
            pst.setFloat(4, f);
            pst.setString(5, fecha);
             
            pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {


            pst.close();
            c.close();
        }
	}

	public List<InscripcionDto> buscarInsByDni(String dni) {
		List<InscripcionDto> listaDni = null;
		try {
			listaDni=buscarInsByDniP( dni);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaDni;
	}
	
	public List<InscripcionDto> buscarInsByEmail(String email) {
		List<InscripcionDto> listaE = null;
		try {
			listaE=buscarInsByEmailP( email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaE;
	}

	private List<InscripcionDto> buscarInsByDniP(String dni) throws SQLException {
		List<InscripcionDto> listaDni = new ArrayList<InscripcionDto>();

        // Conexi�n a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql5);
            pst.setString(1, dni);
            rs = pst.executeQuery();

            // A�adimos los pedidos a la lista
            listaDni = DtoAssembler.toInscripcionDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

        for (InscripcionDto i : listaDni) {
			System.out.println(i.getDni_a());
		}
        return listaDni;
	}


	public List<InscripcionDto> buscarInsByEmailP(String email) throws SQLException {
		List<InscripcionDto> listaE = new ArrayList<InscripcionDto>();

        // Conexi�n a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql6);
            pst.setString(1, email);
            rs = pst.executeQuery();

            // A�adimos los pedidos a la lista
            listaE = DtoAssembler.toInscripcionDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

        for (InscripcionDto i : listaE) {
			System.out.println(i.getEmail());
		}
        return listaE;
	}

}
