package br.com.monitorDispositivo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import br.com.monitorDispositivo.jdbcinteface.jdbcDispositivo;
import br.com.monitorDispositivo.objetos.Dispositivo;

public class JDBCDispositivoDAO implements jdbcDispositivo {
	
	//Declaração da variavel conexao para estabelecer a conexão com o banco de dados
	private Connection conexao;
	
	//Estabelece a conexão com o banco de dados para inserir os dados
	public JDBCDispositivoDAO(Connection conexao){
		this.conexao = conexao;
	}
	
	public boolean inserir(Dispositivo dispositivo){
		//Armazena na variavel comendo a instrução de inserção de dados
		String comando = "INSERT INTO dispositivos (descritivo, protocolo, status, IPname, porta) VALUES (?,?,?,?,?)";
		
		/*
		 * Declara o objeto p para receber os valores atribuidos aos campos e
		 * executar a inserção dos dados
		 */
		PreparedStatement p;
		try{
			//Armazena no objeto p a instrução sql de inserção dos dados
			p = this.conexao.prepareStatement(comando);
			
			/*
			 * Captura os valores dos campos nome, endereco, telefone e nascimento e os posiciona
			 * respectivamente nos espaços com ? do comando contido no objeto p
			 */
			p.setString(1, dispositivo.getDescritivo());
			p.setString(2, dispositivo.getProtocolo());
			p.setString(3, "");
			p.setString(4, dispositivo.getIPname());
			p.setInt(5, dispositivo.getPorta());
			
			//Executa a instrução de inserção de dados no BD
			p.execute();
		}catch (SQLException e){
			e.printStackTrace();
			//Retorna falso caso algum problema tenha ocorrido.
			return false;
		}
		//Retorna verdadeiro se tudo correu bem.
		return true;		
	}
	
	public List<Dispositivo> buscarPorDesc(String desc){
		String comando = "SELECT * FROM dispositivos ";
		
		if(!desc.equals("") && !desc.equals("*")){
			comando += "WHERE descritivo LIKE '%" + desc + "%'";
		}else if(!desc.equals("*")){
			comando += "WHERE status LIKE 'falha'";
		}
		
		List<Dispositivo> listDispositivo = new ArrayList<Dispositivo>();
		Dispositivo dispositivo = null;
		
		try{
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()){
				dispositivo = new Dispositivo();
				int id = rs.getInt("id");
				String descritivo = rs.getString("descritivo");
				String protocolo = rs.getString("protocolo");
				String status = rs.getString("status");
				String IPname = rs.getString("IPname");
				if(rs.getString("porta")!=null) {
					dispositivo.setPorta(Integer.parseInt(rs.getString("porta")));
				}else {
					dispositivo.setPorta(0);
				}
								
				dispositivo.setId(id);
				dispositivo.setDescritivo(descritivo);
				dispositivo.setProtocolo(protocolo);
				dispositivo.setStatus(status);
				dispositivo.setIPname(IPname);
				
				
				listDispositivo.add(dispositivo);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return listDispositivo;
	}
	
	public boolean deletar(int id){
		String comando = "DELETE FROM dispositivos WHERE id = " + id;
		Statement p;
		try{
			p = this.conexao.createStatement();
			p.execute(comando);
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Dispositivo buscarPorId(int id){
		String comando = "SELECT * FROM dispositivos WHERE id = " + id;
		Dispositivo dispositivo = new Dispositivo();
		try{
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				int idDispositivo = rs.getInt("id");
				String descritivo = rs.getString("descritivo");
				String protocolo = rs.getString("protocolo");
				String IPname = rs.getString("IPname");
				
				dispositivo.setId(idDispositivo);
				dispositivo.setDescritivo(descritivo);
				dispositivo.setProtocolo(protocolo);
				dispositivo.setIPname(IPname);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return dispositivo;
	}
	
	public boolean atualizar(Dispositivo dispositivo){
		String comando = "UPDATE dispositivos SET descritivo=?, protocolo=?, IPname=? ";
		comando += "WHERE id=" + dispositivo.getId();
		PreparedStatement p;
		try{
			p = this.conexao.prepareStatement(comando);
			p.setString(1, dispositivo.getDescritivo());
			p.setString(2, dispositivo.getProtocolo());
			p.setString(3, dispositivo.getIPname());
			p.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
}
