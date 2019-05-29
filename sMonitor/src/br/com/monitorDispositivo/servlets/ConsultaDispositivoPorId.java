package br.com.monitorDispositivo.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;

import br.com.monitorDispositivo.bd.conexao.Conexao;
import br.com.monitorDispositivo.jdbc.JDBCDispositivoDAO;
import br.com.monitorDispositivo.objetos.Dispositivo;

import com.google.gson.Gson;

public class ConsultaDispositivoPorId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ConsultaDispositivoPorId() {
        super();
    }
    
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Dispositivo dispositivo = new Dispositivo();
    	
    	Conexao conec = new Conexao();
    	Connection conexao = conec.abrirConexao();
    	    	
    	JDBCDispositivoDAO jdbcDispositivo = new JDBCDispositivoDAO(conexao);
    	dispositivo = jdbcDispositivo.buscarPorId(Integer.parseInt(request.getParameter("id").toString()));
    	conec.fecharConexao();
    	
    	//Para retornar um objeto para o usuário
    	String json = new Gson().toJson(dispositivo);
    	try{
    		response.setContentType("application/json");
    		response.setCharacterEncoding("UTF-8");
    		response.getWriter().write(json);
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

}
