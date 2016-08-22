<%@page import="com.ufla.glcmonitor.jdbc.modelo.Sensor"%>
<%@page import="com.ufla.glcmonitor.jdbc.modelo.Usuario"%>
<%@page import="com.ufla.glcmonitor.jdbc.dao.SensorDao"%>
<%@page import="com.google.gson.Gson"%>
<%@page import= "java.util.ArrayList"%>
<%@page import= "java.util.List"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.ExclusionStrategy"%>
<%@page import="com.google.gson.FieldAttributes"%>
<%

String buscasensor  = request.getParameter("login");


SensorDao daosensor = new SensorDao();


List<Sensor> aux = daosensor.buscaPorUsuario(buscasensor);
 
 if(!aux.isEmpty())
 {
	 Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
		 
		 public boolean shouldSkipClass(Class<?> clazz){
			 return (clazz.equals(Usuario.class));
		 }
		 
		 public boolean shouldSkipField(FieldAttributes f){
			 return false;
		 }
	 }).serializeNulls().create();
	out.print(gson.toJson(aux, new TypeToken<List<Sensor>>(){}.getType() ));	
	System.out.println(new Gson().toJson(aux));
 }
 else
 {
	 out.print("sem registros");
 }
		
	

%>