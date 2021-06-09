package br.com.ssp.ematricula.test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.gson.Gson;

import br.com.ssp.ematricula.model.dao.MatriculaDAO;
import br.com.ssp.ematricula.model.domain.Matricula;

public class Test {
	public static void main(String[] args) {
		test5();
	}
	
	public static void test5() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
		System.out.println(LocalDateTime.now().format(pattern));
	}
	
	public static void test4() {
		Gson gson = new Gson();
		Matricula mat = new Matricula();
		mat.setCodigo("202157-direito penal e criminologia-m0");
		System.out.println(gson.toJson(MatriculaDAO.getInstancia().read(mat)));
	}
	
	public static void test1() {
		String date = "2000-03-06";
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int day = Integer.parseInt(date.substring(8,10));
		GregorianCalendar calendario = new GregorianCalendar(year,month-1,day);
		GregorianCalendar hoje = new GregorianCalendar();
		System.out.println("Data: " + year + "-" + month + "-" + day);
		System.out.println("Ano: " + calendario.get(Calendar.YEAR));
		System.out.println("Mês: " + (calendario.get(Calendar.MONTH)+1));
		System.out.println("Dia: " + calendario.get(Calendar.DAY_OF_MONTH));
		System.out.println("Semestre: " + (((calendario.get(Calendar.MONTH)) > 5) ? 2 : 1));
		System.out.println("\n\nHoje: " 
				+ hoje.get(Calendar.YEAR) + "-"
				+(hoje.get(Calendar.MONTH)+1) + "-"
				+ hoje.get(Calendar.DAY_OF_MONTH));
	}
	
	public static void test2() {
		String date = "2000-03-06";
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int day = Integer.parseInt(date.substring(8,10));
		GregorianCalendar calendario = new GregorianCalendar(year,month-1,day);
		GregorianCalendar hoje = new GregorianCalendar();
		System.out.println((hoje.get(Calendar.YEAR)) - (calendario.get(Calendar.YEAR)));
	}
	
	public static void test3() {
		String date = "2000-03-06";
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int day = Integer.parseInt(date.substring(8,10));
		GregorianCalendar calendario = new GregorianCalendar(year,month-1,day);
		SimpleDateFormat sdf = new SimpleDateFormat("y-MM-dd");
		System.out.println(sdf.format(calendario.getTime()));
	}
}
