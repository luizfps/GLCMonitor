package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Util {
	
	public static void setFloatPreparedStatement(PreparedStatement stmt, 
			int  parameterIndex, Float value) throws SQLException {
		if(value == null) {
			stmt.setNull(parameterIndex, Types.FLOAT);
		} else {
			stmt.setFloat(parameterIndex, value);
		}
	}
	
	public static void setLongPreparedStatement(PreparedStatement stmt, 
			int  parameterIndex, Long value) throws SQLException {
		if(value == null) {
			stmt.setNull(parameterIndex, Types.BIGINT);
		} else {
			stmt.setLong(parameterIndex, value);
		}
	}
	
	public static void setIntegerPreparedStatement(PreparedStatement stmt, 
			int  parameterIndex, Integer value) throws SQLException {
		if(value == null) {
			stmt.setNull(parameterIndex, Types.INTEGER);
		} else {
			stmt.setInt(parameterIndex, value);
		}
	}
	
	public static void setDatePreparedStatement(PreparedStatement stmt, 
			int  parameterIndex, java.util.Date data) throws SQLException {
		if(data == null) {
			stmt.setDate(parameterIndex, null);
		} else {
			stmt.setDate(parameterIndex, new Date(data.getTime()));
		}
	}
	
	public static Long getResultSetValueLong(ResultSet rs, String columnLabel) 
			throws SQLException {
		Long value = rs.getLong(columnLabel);
		if(rs.wasNull()) {
			return null;
		}
		return value;
	}
	
	public static Integer getResultSetValueInteger(ResultSet rs, String columnLabel) 
			throws SQLException {
		Integer value = rs.getInt(columnLabel);
		if(rs.wasNull()) {
			return null;
		}
		return value;
	}
	
	public static Float getResultSetValueFloat(ResultSet rs, String columnLabel) 
			throws SQLException {
		Float value = rs.getFloat(columnLabel);
		if(rs.wasNull()) {
			return null;
		}
		return value;
	}

}
