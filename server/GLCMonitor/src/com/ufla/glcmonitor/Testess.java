package com.ufla.glcmonitor;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ufla.glcmonitor.jdbc.dao.RegistroDeTemperaturaDao;

public class Testess {

	@SuppressWarnings("null")
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		RegistroDeTemperaturaDao aux = new RegistroDeTemperaturaDao();
		
		for(int i=0;i<aux.busca((long) 1).size();i++)
		{
			System.out.println(aux.busca((long) 1).get(i).getMomento());
		}
		
		
	
		
	}

}
