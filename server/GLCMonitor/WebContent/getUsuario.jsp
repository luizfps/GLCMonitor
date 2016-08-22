<%@page import="com.ufla.glcmonitor.jdbc.modelo.Usuario"%>
<%@page import="com.ufla.glcmonitor.jdbc.dao.UsuarioDao"%>
<%@page import="com.google.gson.Gson"%>
<%
	
	out.println(new Gson().toJson(new UsuarioDao().busca(request.getParameter("login")),
			Usuario.class));
%>