package com.ufla.glcmonitor.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.ufla.glcmonitor.jdbc.modelo.LimitesDeTemperatura;

public class Util {

	/**
	 * Classe apenas fornece métodos estáticos.
	 */
	private Util() {

	}

	public static void setFloatPreparedStatement(PreparedStatement stmt, int parameterIndex,
			Float value) throws SQLException {
		if (value == null) {
			stmt.setNull(parameterIndex, Types.FLOAT);
		} else {
			stmt.setFloat(parameterIndex, value);
		}
	}

	public static void setLongPreparedStatement(PreparedStatement stmt, int parameterIndex,
			Long value) throws SQLException {
		if (value == null) {
			stmt.setNull(parameterIndex, Types.BIGINT);
		} else {
			stmt.setLong(parameterIndex, value);
		}
	}

	public static void setIntegerPreparedStatement(PreparedStatement stmt, int parameterIndex,
			Integer value) throws SQLException {
		if (value == null) {
			stmt.setNull(parameterIndex, Types.INTEGER);
		} else {
			stmt.setInt(parameterIndex, value);
		}
	}

	public static void setDatePreparedStatement(PreparedStatement stmt, int parameterIndex,
			java.util.Date data) throws SQLException {
		if (data == null) {
			stmt.setTimestamp(parameterIndex, null);
		} else {
			stmt.setTimestamp(parameterIndex, new java.sql.Timestamp(data.getTime()));
		}
	}

	public static void setLimitesDeTemperaturaPreparedStatement(PreparedStatement stmt,
			int parameterIndexBegin, LimitesDeTemperatura limitesDeTemperatura)
			throws SQLException {
		if (limitesDeTemperatura == null) {
			stmt.setNull(parameterIndexBegin, Types.FLOAT);
			stmt.setNull(parameterIndexBegin + 1, Types.FLOAT);
		} else {
			setFloatPreparedStatement(stmt, parameterIndexBegin,
					limitesDeTemperatura.getTemperaturaMinima());
			setFloatPreparedStatement(stmt, parameterIndexBegin + 1,
					limitesDeTemperatura.getTemperaturaMinima());
		}
	}

	public static Long getResultSetValueLong(ResultSet rs, String columnLabel) throws SQLException {
		Long value = rs.getLong(columnLabel);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}

	public static Integer getResultSetValueInteger(ResultSet rs, String columnLabel)
			throws SQLException {
		Integer value = rs.getInt(columnLabel);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}

	public static Float getResultSetValueFloat(ResultSet rs, String columnLabel)
			throws SQLException {
		Float value = rs.getFloat(columnLabel);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}

	public static java.util.Date sqlDateToUtilDate(java.sql.Timestamp data) {
		if (data == null) {
			return null;
		}
		return new java.util.Date(data.getTime());
	}

}
