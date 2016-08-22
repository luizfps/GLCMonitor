<%@page import="com.ufla.glcmonitor.jdbc.modelo.RegistroDeTemperatura"%>
<%@page import="com.ufla.glcmonitor.jdbc.dao.RegistroDeTemperaturaDao"%>
<%@page import="java.sql.SQLException"%>
<%@page import= "java.util.ArrayList"%>
<%@page import= "java.text.SimpleDateFormat"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>


<%
	
	String codigo_sensor= request.getParameter("codigo_sensor");
	String dataInicial=request.getParameter("data_inicial");
	String dataFinal=request.getParameter("data_final");

	System.out.println(codigo_sensor);
	System.out.println(dataInicial);
	System.out.println(dataFinal);
	

	
	//contém todos os dados que satisfazer as condições de intervalo de datas
		ArrayList<RegistroDeTemperatura> Limitedregister = new ArrayList<>();
		
	RegistroDeTemperaturaDao recovery = new RegistroDeTemperaturaDao();
	Limitedregister.addAll( recovery.buscaIntervalo(Long.parseLong(codigo_sensor), dataInicial, dataFinal+" 23:59:59"));
	
	if(!Limitedregister.isEmpty())
	{
		
		//System.out.println(new Gson().toJson(Limitedregister));
		out.println(new Gson().toJson(Limitedregister));
	}
	else{
		out.println("sem registros");
	}
	
%>