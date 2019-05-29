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

public class CadastraDispositivo extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    public CadastraDispositivo() {
        super();
    }
	
	private void process(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		/*
		 * Instanciar o objeto contato para a classe Contato pois é nesta que 
		 * armazenaremos os valores dos campos do formulario contato que posteriormente
		 * serão gravados no banco de dados.
		 */
		Dispositivo dispositivo = new Dispositivo();
		
		try{
			/*
			 * Recebendo os valores dos campos do formulário e os armazenando
			 * em seus respectivos atributos do objeto contato.
			 */
			
			dispositivo.setDescritivo(request.getParameter("txtdesc"));
			dispositivo.setProtocolo(request.getParameter("txtprotocol"));
			dispositivo.setIPname(request.getParameter("txtIpHost"));
			dispositivo.setPorta(Integer.parseInt(request.getParameter("txtPorta")));
			
			/*
			 * Instanciando a classe Conexão ao objeto conec visando estabelecer
			 * a conexão com o banco de dados agenda.
			 */
			Conexao conec = new Conexao();
			//Abrindo a conexão com o BD
			Connection conexao = conec.abrirConexao();
			
			JDBCDispositivoDAO jdbcContato = new JDBCDispositivoDAO(conexao);
			
			boolean retorno = jdbcContato.inserir(dispositivo);
			
			//Fechando a conexão com o BD
			conec.fecharConexao();
			
			//Criando a mensagem para o usuário
			Map<String, String> msg = new HashMap<String, String>();
			if(retorno){
				msg.put("msg", "Contato cadastrado com sucesso.");
			}else{
				msg.put("msg", "Não foi possivel cadastrar o contato.");
			}
			
			//Retorna a resposta para o usuário a partir do Json
			String json = new Gson().toJson(msg);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}

}
