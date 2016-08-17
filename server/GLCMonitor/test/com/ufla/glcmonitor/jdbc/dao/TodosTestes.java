package com.ufla.glcmonitor.jdbc.dao;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ufla.glcmonitor.jdbc.persistance.ConnectionConfiguration;

@RunWith(Suite.class)

@SuiteClasses({ TestesDao.class, GsonTestes.class })


public class TodosTestes {
	
	@BeforeClass
	public static void setBDURL() {
		ConnectionConfiguration.URL = ConnectionConfiguration.URL_TESTES;
	}

}
