package br.com.monitorDispositivo.script;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import br.com.monitorDispositivo.bd.conexao.Conexao;
import br.com.monitorDispositivo.objetos.Dispositivo;

public class Inicio {
	public static void main(String[] args) throws InterruptedException, MessagingException {
		Conexao conec = new Conexao();

		while(true){
			System.out.println("Inicio");
			List<Dispositivo> listaDispositivos = new ArrayList<Dispositivo>();
			Tester tester = new Tester();
			listaDispositivos = buscarDispositivos(conec);
			
			for(Dispositivo dispositivo : listaDispositivos){
				int id = dispositivo.getId();
				String descritivo = dispositivo.getDescritivo();
				String IPname = dispositivo.getIPname();
				String protocolo = dispositivo.getProtocolo();
				int porta = dispositivo.getPorta();
				
				if(protocolo.equals("Telnet")){
					tester.testaTelnet(id, descritivo, IPname, porta);
				}else if(protocolo.equals("TCP/IP")){
					tester.testaPing(id, descritivo, IPname, porta);
				}
			}
			//Thread.sleep(15000);
		}
         
    }
    
    public static List<Dispositivo> buscarDispositivos(Conexao conec){
		String comando = "SELECT * FROM dispositivos ";
		List<Dispositivo> listDispositivo = new ArrayList<Dispositivo>();
		Dispositivo dispositivo = null;
		
		try{
	    	Connection conexao = conec.abrirConexao();
	    	
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()){
				dispositivo = new Dispositivo();
				
				int id = rs.getInt("id");
				String descritivo = rs.getString("descritivo");
				String IPname = rs.getString("IPname");
				String protocolo = rs.getString("protocolo");
				int porta = rs.getInt("porta");
				
				dispositivo.setId(id);
				dispositivo.setDescritivo(descritivo);
				dispositivo.setIPname(IPname);
				dispositivo.setProtocolo(protocolo);
				dispositivo.setPorta(porta);;
				
				listDispositivo.add(dispositivo);
			}
			conec.fecharConexao();
		} catch (Exception e){
			e.printStackTrace();
		}
		return listDispositivo;
	}

}

