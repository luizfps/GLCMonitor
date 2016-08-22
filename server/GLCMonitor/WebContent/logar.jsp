<%@page import="com.ufla.glcmonitor.jdbc.modelo.Usuario"%>
<%@page import="com.ufla.glcmonitor.jdbc.dao.UsuarioDao"%>
<%@page import="com.google.gson.Gson"%>
<%
	String usuarioJson = request.getParameter("usuario");
 	Gson gson = new Gson();
	Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
	

	Usuario usuarioEsperado = new UsuarioDao().busca(usuario.getLogin());
	
// 	System.out.println(usuario.getLogin()+"  "+usuario.getSenha());

// 	System.out.println(usuarioEsperado.getLogin()+"  "+usuarioEsperado.getSenha());

	if(usuario != null && usuario.getSenha() != null && usuarioEsperado != null &&
			usuario.getSenha().equals(usuarioEsperado.getSenha())) {
		out.print("Sucesso!");
	} else {
		out.print("Login ou senha incorreto!");
	}
	//out.print("λ"+gson.toJson(usuarioEsperado,Usuario.class));
%>
