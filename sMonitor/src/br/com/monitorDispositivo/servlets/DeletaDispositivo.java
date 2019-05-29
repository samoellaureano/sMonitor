package br.com.monitorDispositivo.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import br.com.monitorDispositivo.bd.conexao.Conexao;
import br.com.monitorDispositivo.jdbc.JDBCDispositivoDAO;

import com.google.gson.Gson;

public class DeletaDispositivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeletaDispositivo() {
        super();
    }
	
	public void process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		JDBCDispositivoDAO jdbcDispositivo = new JDBCDispositivoDAO(conexao);
		boolean retorno = jdbcDispositivo.deletar(id);
		conec.fecharConexao();
		
		//Para retornar uma mensangem para o usuario
		Map<String, String> msg = new HashMap<String, String>();
		if(retorno == true){
			msg.put("msg", "Dispositivo deletado com sucesso.");
		}else{
			msg.put("msg", "Não foi possível deletar o dispositivo.");
		}
		
		String json = new Gson().toJson(msg);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

}
