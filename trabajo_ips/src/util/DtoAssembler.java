package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.AtletaDto;
import logica.CompeticionDto;
import logica.InscripcionDto;

public class DtoAssembler {

	public static List<AtletaDto> toAtletaDtoList (ResultSet rs)
	{
		List<AtletaDto> lista = new ArrayList<AtletaDto>();
		try {
			while(rs.next())
			{
				lista.add(cogerDatosAtleta(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	private static AtletaDto cogerDatosAtleta(ResultSet rs) throws SQLException {
		AtletaDto a = new AtletaDto();
		a.setDni(rs.getString("dni"));
		a.setF_nac(rs.getString("f_nac"));
		a.setNombre(rs.getString("nombre"));
		a.setSexo(rs.getString("sexo"));
		return a;
	}
	
	
	
	public static List<CompeticionDto> toCompeticionDtoList (ResultSet rs)
	{
		List<CompeticionDto> lista = new ArrayList<CompeticionDto>();
		try {
			while(rs.next())
			{
				lista.add(cogerDatosCompeticion(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	private static CompeticionDto cogerDatosCompeticion(ResultSet rs) throws SQLException {
		CompeticionDto a = new CompeticionDto();
		a.setCuota(Float.parseFloat(rs.getString("cuota")));
		a.setDistancia(rs.getString("distancia"));
		a.setF_comp(rs.getString("f_comp"));
		a.setF_fin(rs.getString("f_fin"));
		a.setF_inicio(rs.getString("f_inicio"));
		a.setId(Integer.parseInt(rs.getString("id")));
		a.setNombre(rs.getString("nombre"));
		a.setNum_plazas(Integer.parseInt(rs.getString("num_plazas")));
		a.setTipo(rs.getString("tipo"));
		return a;
	}

	public static List<InscripcionDto> toInscripcionDtoList(ResultSet rs) {
		List<InscripcionDto> lista = new ArrayList<InscripcionDto>();
		try {
			while(rs.next())
			{
				lista.add(cogerDatosInscripcion(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	private static InscripcionDto cogerDatosInscripcion(ResultSet rs) throws SQLException {
		InscripcionDto i = new InscripcionDto();
		i.setCantidad_pagada(Float.parseFloat(rs.getString("cantidad_pagada")));
		i.setCategoria(rs.getString("categoria"));
		i.setDni_a(rs.getString("dni_a"));
		i.setEmail(rs.getString("email"));
		i.setFecha(rs.getString("fecha"));
		i.setHoras(Integer.parseInt(rs.getString("horas")));
		i.setId_c(rs.getString("id_c"));
		i.setMetodo_pago(rs.getString("metodo_pago"));
		i.setMinutos(Integer.parseInt(rs.getString("minutos")));
		return i;
	}
	
}
