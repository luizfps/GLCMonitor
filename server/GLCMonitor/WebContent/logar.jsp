<%@page import="com.ufla.glcmonitor.jdbc.modelo.Usuario"%>
<%@page import="com.ufla.glcmonitor.jdbc.dao.UsuarioDao"%>
<%@page import="com.google.gson.Gson"%>
<%
	String usuarioJson = request.getParameter("usuario");
	Gson gson = new Gson();
	Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
	UsuarioDao usuarioDao = new UsuarioDao();
	usuarioDao.adiciona(gson.fromJson(usuarioJson, Usuario.class));
	out.println("Sucesso!");
%>