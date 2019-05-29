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
import br.com.monitorDispositivo.objetos.Dispositivo;

import com.google.gson.Gson;

public class EditaDispositivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditaDispositivo() {
        super();
    }
    
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try{
    		Dispositivo dispositivo = new Dispositivo();
    		dispositivo.setId(Integer.parseInt(request.getParameter("id")));
    		dispositivo.setDescritivo(request.getParameter("descricao"));
    		dispositivo.setProtocolo(request.getParameter("protocolo"));
    		dispositivo.setIPname(request.getParameter("IPname"));
    		
    		//Chama o método que atualiza no BD o registro referente ao contato editado
    		Conexao conec = new Conexao();
    		Connection conexao = conec.abrirConexao();
    		JDBCDispositivoDAO jdbcDispositivo = new JDBCDispositivoDAO(conexao);
    		boolean retorno = jdbcDispositivo.atualizar(dispositivo);
    		conec.fecharConexao();
    		
    		//Para retornar uma mensagem para o usuario
    		Map<String, String> msg = new HashMap<String, String>();
    		if(retorno){
    			msg.put("msg", "Dispositivo editado com sucesso");
    		}else{
    			msg.put("msg", "Não foi possivel editar o dispositivo");
    		}
    		
    		response.setContentType("application/json");
    		response.setCharacterEncoding("UTF-8");
    		response.getWriter().write(new Gson().toJson(msg));
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
