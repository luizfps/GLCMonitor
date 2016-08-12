<%@page import="java.sql.SQLException"%>
<%@page import="com.ufla.glcmonitor.jdbc.modelo.Usuario"%>
<%@page import="com.ufla.glcmonitor.jdbc.dao.UsuarioDao"%>
<%@page import="com.google.gson.Gson"%>
<%
	String usuarioJson = request.getParameter("usuario");
	Gson gson = new Gson();
	UsuarioDao usuarioDao = new UsuarioDao();
	boolean sucesso = true;
	try {
		usuarioDao.adiciona(gson.fromJson(usuarioJson, Usuario.class));
	} catch(SQLException e) {
		out.println(e.getMessage());
		sucesso = false;
	}
	if(sucesso) {
		out.println("Sucesso!");
	}
%>