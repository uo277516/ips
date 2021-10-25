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

public class InscripcionModel {

	public static String sql1 = "select * from inscripcion";
	public static String sql2 = "select * from inscripcion where inscripcion.email=?";
	public static String sql3 = "insert into inscripcion (dni_a, id_c, email, estado,cantidad_pagada,fecha,categoria) values (?,?,?,'Pre-inscrito',?,?,?)";
	public static String sql4 = "select * from atleta where atleta.email=?";
	public static String sql6 = "select * from inscripcion where inscripcion.email=? order by inscripcion.fecha asc";
	public static String sql5 = "select * from inscripcion where inscripcion.dni_a=? order by inscripcion.fecha asc";
	public static String COMPID_INS = "select * from inscripcion where id_c=?";

	public static String ATLETA_DNI = "select * from atleta where dni = ?";
	public static String INS_DNI_IDC = "select * from inscripcion where dni_a = ? and id_c = ?";
	public static String CAT_INS_DNI_ID = "select categoria from inscripcion where dni_a = ? and id_c = ?";
	public static String sql6Ins = "select * from inscripcion where dni_a=? and id_c =?";

	public static String sql7UpdateEstado = "update inscripcion set estado=? where dni_a=? and id_c=?";
	public static String sql7UpdateFecha = "update inscripcion set fecha=? where dni_a=? and id_c=?";
	public static String sql7UpdatePago = "update inscripcion set metodo_pago=? where dni_a=? and id_c=?";

	public static String sql_InscripcionesPorTiempo = "select  * from inscripcion i, atleta a where i.dni_a = a.dni and id_c = ? order by horas is null, minutos is null, horas, minutos asc";
	public static String sql_InscripcionesPorTiempoYCategoria = "select  * from inscripcion i, atleta a where i.dni_a = a.dni and id_c = ? order by horas is null, minutos is null, horas, minutos asc";
	public static String sql_InscripcionesPorTiempoYSexo = "select  * from inscripcion i, atleta a where i.dni_a = a.dni and a.sexo=? and id_c = ? order by horas is null, minutos is null, horas, minutos asc";

	public AtletaDto findAtletaEmail(String email) {
		AtletaDto a = null;
		try {
			a = findAtletaByEmail(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	private AtletaDto findAtletaByEmail(String email) throws SQLException {
		AtletaDto a = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql4);
			pst.setString(1, email);
			// System.out.println(pst);
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

	public List<InscripcionDto> getInscripciones() throws SQLException {
		return getAllInscripciones();
	}

	private List<InscripcionDto> getAllInscripciones() throws SQLException {
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
			System.out.println(atletaDto);
		}
		return listaInscrpcines;
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

		if (listaAtletas.size() > 0)
			return true;
		else
			return false;
	}

	public void agregarInscripcion(String email, int id, float precio, String fecha) {
		try {
			agregarParticipante(email, id, precio, fecha);
		} catch (SQLException e) {
			System.out.println("no se pudo añadir -- inscripcion model");
			e.printStackTrace();
		}
	}

	private void agregarParticipante(String email, int id, float f, String fecha) throws SQLException {
		AtletaDto a = findAtletaEmail(email);
		String dni = a.getDni();
		String cat = calcularCategoria(a.getF_nac());
		// Conexión a la base de datos
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
			pst.setString(6, cat);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {

			pst.close();
			c.close();
		}
	}

	private static int year1 = 18;
	private static int year2 = 35;
	private static String[] categories = { "A", "B", "C", "D", "E", "F", "G" };

	private String calcularCategoria(String fecha) {
		String[] fechaArray = fecha.split("/");
		int year = Integer.valueOf(fechaArray[2]);
		int yearActual = LocalDate.now().getYear();
		int cat = yearActual - year;
		if (cat >= year1 && cat < year2) {
			return "Senior";
		} else if (cat >= year2) {
			int a = year2;
			int b = year2 + 5;
			for (int i = 0; i < categories.length; i++) {
				if (cat >= a && cat < b) {
					return "Veterano " + categories[i];
				}
				a += 5;
				b += 5;
			}
		}
		return null;
	}

	public List<InscripcionDto> buscarInsByDni(String dni) {
		List<InscripcionDto> listaDni = null;
		try {
			listaDni = buscarInsByDniP(dni);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaDni;
	}

	public List<InscripcionDto> buscarInsByEmail(String email) {
		List<InscripcionDto> listaE = null;
		try {
			listaE = buscarInsByEmailP(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaE;
	}

	private List<InscripcionDto> buscarInsByDniP(String dni) throws SQLException {
		List<InscripcionDto> listaDni = new ArrayList<InscripcionDto>();

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql5);
			pst.setString(1, dni);
			rs = pst.executeQuery();

			// Añadimos los pedidos a la lista
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

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql6);
			pst.setString(1, email);
			rs = pst.executeQuery();

			// Añadimos los pedidos a la lista
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

	public List<InscripcionDto> getInscripcionesDeUnaCompeticion(int id) throws SQLException {
		List<InscripcionDto> inscripciones = new ArrayList<InscripcionDto>();

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(COMPID_INS);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			inscripciones = DtoAssembler.toInscripcionDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}
		return inscripciones;
	}

	public String getCategoriaByDniId(String dni, int id) {
		String a = "";
		try {
			a = getCategoria(dni, id);
		} catch (SQLException e) {
			System.out.println("no se pudo añadir -- inscripcion model");
			e.printStackTrace();
		}
		return a;
	}

	private String getCategoria(String dni, int id) throws SQLException {
		String a = "";
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(CAT_INS_DNI_ID);
			pst.setString(1, dni);
			pst.setInt(2, id);
			rs = pst.executeQuery();
			rs.next();

			a = rs.getString(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}
		return a;
	}

	public InscripcionDto findInsByDniId(String dni_a, int id_c) {
		InscripcionDto ins = null;
		try {
			ins = findInsByDniIdP(dni_a, id_c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins;
	}

	private InscripcionDto findInsByDniIdP(String dni_a, int id_c) throws SQLException {
		InscripcionDto a = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql6Ins);
			pst.setString(1, dni_a);
			pst.setInt(2, id_c);
			// System.out.println(pst);
			rs = pst.executeQuery();
			rs.next();

			a = DtoAssembler.toInscripcionDto(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}
		return a;
	}

	public void actualizarInscripcionEstado(String estado, String dni, int id) {
		try {
			actualizarEstado(estado, dni, id);
		} catch (SQLException e) {
			System.out.println("no se pudo actualizar");
			e.printStackTrace();
		}
	}

	private void actualizarEstado(String estado, String dni, int id) throws SQLException {
		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
//        ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql7UpdateEstado);
			pst.setString(1, estado);
			pst.setString(2, dni);
			pst.setInt(3, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			pst.close();
			c.close();
		}

	}

	public void actualizarInscripcionFecha(String fecha, String dni, int id) {
		try {
			actualizarFechaP(fecha, dni, id);
		} catch (SQLException e) {
			System.out.println("no se pudo actuliazar");
			e.printStackTrace();
		}
	}

	private void actualizarFechaP(String fecha, String dni, int id) throws SQLException {
		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
//        ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql7UpdateFecha);
			pst.setString(1, fecha);
			pst.setString(2, dni);
			pst.setInt(3, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			pst.close();
			c.close();
		}

	}

	public void cambiarMetodoPago(String string, String dni, int id) {
		try {
			cambiarMetodoPagoP(string, dni, id);
		} catch (SQLException e) {
			System.out.println("no se pudo actuliazar");
			e.printStackTrace();
		}
	}

	public void cambiarMetodoPagoP(String string, String dni, int id) throws SQLException {
		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
//        ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql7UpdatePago);
			pst.setString(1, string);
			pst.setString(2, dni);
			pst.setInt(3, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			pst.close();
			c.close();
		}

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
			pst.setInt(1, carreraId);
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
			pst.setInt(2, carreraId);
=======
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
>>>>>>> branch 'UO264802' of https://github.com/uo277516/ips.git
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
