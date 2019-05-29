package br.com.monitorDispositivo.bd.conexao;

import java.sql.Connection;

public class Conexao {
	
	/*
	 * Declaração do atributo conexao, do tipo Connection, que receberá as informações
	 * para conexão do aplicativo com o Banco de Dados (BD) via Driver JDBC.
	 */
	private Connection conexao;
	//Método que abre a conexão com o BD
	
	public Connection abrirConexao(){
		try{
			//Instrução que identifica o tipo de driver utilizado para a conexão com o BD.
			Class.forName("org.gjt.mm.mysql.Driver");
			//Note o endereçamento feito do servidor de BD e do driver
			conexao = java.sql.DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/monitor", "root", "");
		}catch (Exception e){
			e.printStackTrace();
		}
		return this.conexao;
	}
	//Método que fecha a conexão com o BD
	public void fecharConexao(){
		try{
			conexao.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
