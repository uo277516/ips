package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import util.BaseDatos;
import util.DtoAssembler;

public class AtletaModel {

	public static String sql1 = "select * from atleta";
	public static String sql2 = "select * from atleta, inscripcion, competicion" + "		where "
			+ " 	atleta.dni = inscripcion.dni_a and " + "     inscripcion.id_c = competicion.id and "
			+ "		inscripcion.email=? and " + "     competicion.nombre=?";
	public static String sql3 = "select * from atleta where atleta.email=?";
	public static String COMPID_ATL = "select a.dni, a.nombre, a.sexo, a.f_nac, a.email"
			+ " from atleta as a, inscripcion as i" + " where a.dni = i.dni_a" + " and i.id_c = ?";
	
	public static String sqlFindByDni = "select * from atleta where dni=?";
	public static String sqlFindByEmail = "select * from atleta where email=?";


	public List<AtletaDto> getAtletas() throws SQLException {
		return getAllAtletas();
	}

	private List<AtletaDto> getAllAtletas() throws SQLException {
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
			System.out.println(atletaDto);
		}
		return listaAtletas;
	}

	public boolean atletaAlredyRegistred(String email, String cmpe) {
		boolean op = false;
		try {
			op = yaEstaRegistrado(email, cmpe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return op;
	}

	/*
	 * true si ya esta registrado, false si no (lo guay false)
	 */
	private boolean yaEstaRegistrado(String emailAtleta, String nombreCompe) throws SQLException {
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
		if (listaAtletas.size() > 0) { // si ya esta registrado en esa carrera
			System.out.println("Ya se ha registrado en esta competicion");
			return true;
		} else {
			System.out.println("no registrado, puede registrarse");
			return false;
		}
	}

	public boolean atletaEnBase(String email) {
		boolean op = false;
		try {
			op = atletaEnBaseP(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return op;
	}

	public boolean atletaEnBaseP(String email) throws SQLException {
		boolean op = false;
		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql3);
			pst.setString(1, email);
			rs = pst.executeQuery();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs == null) {
				op = false;
			} else {
				rs.close();
				op = true;
			}

			pst.close();
			c.close();
		}
		return op;
	}

	public List<AtletaDto> getAletasDeUnaCompeticion(int id) throws SQLException {
		List<AtletaDto> atletas = new ArrayList<AtletaDto>();

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(COMPID_ATL);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			atletas = DtoAssembler.toAtletaDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}

		return atletas;
	}

	public int yearsAtleta(String email) {
		int years = 0;
		try {
			years = yearsAtletaByEmail(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return years;
	}

	private int yearsAtletaByEmail(String email) throws SQLException {
		int years = 0;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql3);
			pst.setString(1, email);
			rs = pst.executeQuery();
			rs.next();
			String fecha = rs.getString("f_nac");
			int year = Integer.valueOf((fecha.split("/")[2]));
			int yearActual = LocalDate.now().getYear();
			years = yearActual - year;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			pst.close();
			c.close();
		}
		return years;
	}

	public AtletaDto findAtletaByDni(String dni) throws SQLException {
		AtletaDto a;

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sqlFindByDni);
			pst.setString(1, dni);
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

	public AtletaDto findAtletaByEmail(String email) throws SQLException {
		AtletaDto a;

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sqlFindByEmail);
			pst.setString(1, email);
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
=======
import java.util.ArrayList;
import java.util.List;

import util.BaseDatos;
import util.DtoAssembler;

public class AtletaModel {

	public static String sql1 = "select * from atleta";
	public static String sqlFindByDni = "select * from atleta where dni=?";
	public static String sqlFindByEmail = "select * from atleta where email=?";

	public List<AtletaDto> getAtletas() throws SQLException {
		return getAllAtletas();
	}

	private List<AtletaDto> getAllAtletas() throws SQLException {
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

//        for (AtletaDto atletaDto : listaAtletas) {
//			System.out.println(atletaDto.getDni() + " " + atletaDto.getF_nac()
//			);
//		}
		return listaAtletas;
	}

	public AtletaDto findAtletaByDni(String dni) throws SQLException {
		AtletaDto a;

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sqlFindByDni);
			pst.setString(1, dni);
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

	public AtletaDto findAtletaByEmail(String email) throws SQLException {
		AtletaDto a;

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sqlFindByEmail);
			pst.setString(1, email);
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

>>>>>>> branch 'UO264802' of https://github.com/uo277516/ips.git
}
