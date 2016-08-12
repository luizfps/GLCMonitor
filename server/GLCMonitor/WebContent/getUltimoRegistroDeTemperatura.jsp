<%@page import="com.ufla.glcmonitor.jdbc.modelo.RegistroDeTemperatura"%>
<%@page import="com.ufla.glcmonitor.jdbc.dao.RegistroDeTemperaturaDao"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.google.gson.Gson"%>
<%
	out.println(new Gson().toJson(new RegistroDeTemperaturaDao()
			.getUltimoRegistroDeTemperatura(Long.parseLong(request
					.getParameter("sensorCodigo"))), 
			RegistroDeTemperatura.class));
%>