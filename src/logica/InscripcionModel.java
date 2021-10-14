package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import util.BaseDatos;
import util.DtoAssembler;

public class InscripcionModel {

	public static String sql1 = "select * from inscripcion";
	public static String sql2 = "select * from inscripcion where inscripcion.email=?";
	public static String sql_InscripcionesPorTiempo = "select  * from inscripcion i, atleta a where i.dni_a = a.dni \r\n"
			+ "order by horas is null, minutos is null, horas, minutos asc";
	public static String sql_AddInscripcion = "insert into inscripcion values(?,?,?,?,?,?,?,?,?)";
//	public static String sql_InscripcionesPorTiempoYCategoria = "select  * from inscripcion i, atleta a where i.dni_a = a.dni and ? order by horas is null, minutos is null, horas, minutos asc";
	public static String sql_InscripcionesPorTiempoYSexo = "select  * from inscripcion i, atleta a where i.dni_a = a.dni and a.sexo=? order by horas is null, minutos is null, horas, minutos asc";

	private AtletaModel am = new AtletaModel();

	public List<InscripcionDto> getInscripciones() throws SQLException {
		return getAllInscripciones();
	}

	private List<InscripcionDto> getAllInscripciones() throws SQLException {
		List<InscripcionDto> listaInscripciones = new ArrayList<InscripcionDto>();

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql1);
			rs = pst.executeQuery();

			// Añadimos los pedidos a la lista
			listaInscripciones = DtoAssembler.toInscripcionDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}

		for (InscripcionDto atletaDto : listaInscripciones) {
			System.out.println(atletaDto.getEmail());
		}
		return listaInscripciones;
	}

	public boolean isEmailRegistred(String email) {
		boolean op = false;
		try {
			op = existsThisEmail(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("algo fue mal");
			e.printStackTrace();
		}
		return op;
	}

	private boolean existsThisEmail(String email) throws SQLException {
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

			listaAtletas = DtoAssembler.toInscripcionDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}

		if (listaAtletas.size() > 0)
			return true;
		else
			return false;
	}

	public List<InscripcionDto> getInscripcionesPorTiempo(int carreraId) throws SQLException {
		List<InscripcionDto> listaInscripciones = new ArrayList<InscripcionDto>();

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql_InscripcionesPorTiempo);
			rs = pst.executeQuery();

			listaInscripciones = DtoAssembler.toInscripcionDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}

		return listaInscripciones;
	}

	public void addInscripcion(InscripcionDto i) throws SQLException {

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql_AddInscripcion);
			pst.setString(1, i.getDni_a());
			pst.setInt(2, i.getId_c());
			pst.setString(3, i.getCategoria());
			pst.setString(4, i.getEmail());
			pst.setString(5, i.getFecha());
			pst.setString(6, i.getMetodo_pago());
			pst.setFloat(7, i.getCantidad_pagada());
			pst.setInt(8, i.getHoras());
			pst.setInt(9, i.getMinutos());
			pst.setString(10, i.getEstado());
			rs = pst.executeQuery();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}

	}

	public void crearPreinscripcion(String email, int carreraId, String metodo) throws SQLException {
		InscripcionDto inscripcion = new InscripcionDto();
		inscripcion.setEmail(email);
		inscripcion.setDni_a(am.findAtletaByEmail(email).getDni());
		inscripcion.setId_c(carreraId);
		inscripcion.setFecha(LocalDateTime.now().toString());
		inscripcion.setEstado("Pre-inscrito");
		inscripcion.setCantidad_pagada(0);
	}

	public List<InscripcionDto> getInscripcionesPorTiempoYSexo(int carreraId, String sexo) throws SQLException {
		List<InscripcionDto> listaInscripciones = new ArrayList<InscripcionDto>();

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql_InscripcionesPorTiempoYSexo);
			pst.setString(1, sexo);
			rs = pst.executeQuery();

			listaInscripciones = DtoAssembler.toInscripcionDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null)
				rs.close();
			pst.close();
			c.close();
		}

		return listaInscripciones;
	}

}
