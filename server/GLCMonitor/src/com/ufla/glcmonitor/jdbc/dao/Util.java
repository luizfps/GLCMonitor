package com.ufla.glcmonitor.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdk.internal.org.objectweb.asm.Type;

public class Util {
	
	public static void setFloatPreparedStatement(PreparedStatement stmt, 
			int  parameterIndex, Float value) throws SQLException {
		if(value == null) {
			stmt.setNull(parameterIndex, Type.FLOAT);
		} else {
			stmt.setFloat(parameterIndex, value);
		}
	}
	
	public static void setLongPreparedStatement(PreparedStatement stmt, 
			int  parameterIndex, Long value) throws SQLException {
		if(value == null) {
			stmt.setNull(parameterIndex, Type.FLOAT);
		} else {
			stmt.setFloat(parameterIndex, value);
		}
	}
	
	public static void setIntegerPreparedStatement(PreparedStatement stmt, 
			int  parameterIndex, Integer value) throws SQLException {
		if(value == null) {
			stmt.setNull(parameterIndex, Type.FLOAT);
		} else {
			stmt.setFloat(parameterIndex, value);
		}
	}

}
