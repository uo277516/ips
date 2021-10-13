package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.BaseDatos;
import util.DtoAssembler;

public class CompeticionModel {

	public static String sql1 = "select * from competicion";

	private InscripcionModel im = new InscripcionModel();
	private AtletaModel am = new AtletaModel();

	public List<CompeticionDto> getCompeticiones() throws SQLException {
		return getAllCompeticiones();
	}

	public CompeticionDto[] getCompeticionesArray() {
		CompeticionDto[] articulos = null;
		try {
			articulos = getAllCompeticiones().toArray(new CompeticionDto[getAllCompeticiones().size()]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articulos;
	}

	private List<CompeticionDto> getAllCompeticiones() throws SQLException {
		List<CompeticionDto> listaCompeticiones = new ArrayList<CompeticionDto>();

		// Conexión a la base de datos
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = BaseDatos.getConnection();
			pst = c.prepareStatement(sql1);
			rs = pst.executeQuery();

			// Añadimos los pedidos a la lista
			listaCompeticiones = DtoAssembler.toCompeticionDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			rs.close();
			pst.close();
			c.close();
		}

		return listaCompeticiones;
	}

	public void listarClasificacion(int carreraId) throws SQLException {
		AtletaDto a;
		List<InscripcionDto> inscripciones = im.getInscripcionesPorTiempo(carreraId);
		for (InscripcionDto i : inscripciones) {
			a = am.findAtletaByDni(i.getDni_a());
			System.out.println("Nombre: " + a.getNombre() + "Sexo: " + a.getSexo() + "Tiempo: " + i.getHoras() != null
					? i.getHoras()
					: "---" + "h " + i.getMinutos() != null ? i.getMinutos() : "---" + " minutos");
		}

	}

}
