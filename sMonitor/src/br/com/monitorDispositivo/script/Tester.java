package br.com.monitorDispositivo.script;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

import br.com.monitorDispositivo.bd.conexao.Conexao;

public class Tester extends TimerTask{

	@SuppressWarnings("unused")
	public void testaTelnet (int id, String descritivo, String IPname, int porta) throws InterruptedException, MessagingException{
		boolean alcance = false;
		int cont = 0;
		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		do{			
			try{
				TimerTask con  = new Tester();
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(con,1,3000);
				Socket s1=new Socket(IPname,porta);

				InputStream is=s1.getInputStream();
				DataInputStream dis = new DataInputStream(is);

				if(dis!=null){
					alcance = true;
				} else {
					cont++;
					Thread.sleep(2000);
				}
				dis.close();	        
				s1.close();
			}
			catch(Exception e){
				cont++;
			}
		}while((alcance == false)&&(cont < 3));
		System.out.println("Testa Telnet "+ descritivo + " - "+ cont);

		if(alcance == true){
			if(buscaStatus(id).equals("FALHA")){
				gravaStatus(id, "OK");
				gravaData(id, data+" "+hora);
				System.out.print("Enviou E-mail ONLINE");
				SendMail.enviarEmail("ONLINE", descritivo, IPname);
			}else if(buscaStatus(id).equals("")){
				gravaStatus(id, "OK");
				gravaData(id, data+" "+hora);
			}
		}else if(buscaStatus(id).equals("OK")){
			gravaStatus(id, "FALHA");
			System.out.print("Enviou E-mail OFFLINE");
			SendMail.enviarEmail("OFFLINE", descritivo, IPname);
		}else if(buscaStatus(id).equals("")){
			gravaStatus(id, "FALHA");
			gravaData(id, data+" "+hora);
		}
	}

	@SuppressWarnings("static-access")
	public void testaPing (int id, String descritivo, String IPname, int porta) throws InterruptedException, MessagingException{
		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

		int cont=0;
		boolean alcance;
		do{
			alcance = false;
			try{
				InetAddress address = InetAddress.getByName(IPname);
				alcance = address.isReachable(4000);
				if(alcance == false) {
					cont++;
					new Thread().sleep(1000);
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}while((alcance == false && cont < 3)||(alcance == true && cont > 4));
		System.out.println("Testa Ping " + descritivo + " - "+ cont);

		if(alcance == true){
			if((buscaStatus(id).equals("FALHA")) || (buscaStatus(id).equals(""))){
				gravaStatus(id, "OK");
				gravaData(id, data+" "+hora);
				System.out.print("Enviou E-mail ONLINE");
				SendMail.enviarEmail("ONLINE", descritivo, IPname);
			}else if(buscaStatus(id).equals("")){
				gravaStatus(id, "OK");
				gravaData(id, data+" "+hora);
			}
		}else{
			if((buscaStatus(id).equals("OK")) || (buscaStatus(id).equals(""))){
				gravaStatus(id, "FALHA");
				System.out.print("Enviou E-mail OFFLINE");
				SendMail.enviarEmail("OFFLINE", descritivo, IPname);

			}else if(buscaStatus(id).equals("")){
				gravaStatus(id, "FALHA");
				gravaData(id, data+" "+hora);
			}
		}
	}

	public void gravaStatus(int id, String cond){
		String comando = "UPDATE dispositivos SET status=?";
		comando += "WHERE id=" + id;
		PreparedStatement p;
		Conexao conec = new Conexao();
		try{
			Connection conexao = conec.abrirConexao();
			p = conexao.prepareStatement(comando);
			p.setString(1, cond);
			p.executeUpdate();
			conec.fecharConexao();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	private void gravaData(int id, String data) {
		String comando = "UPDATE dispositivos SET DataHoraFalha=?";
		comando += "WHERE id=" + id;
		PreparedStatement p;
		Conexao conec = new Conexao();
		try{
			Connection conexao = conec.abrirConexao();
			p = conexao.prepareStatement(comando);
			p.setString(1, data);
			p.executeUpdate();
			conec.fecharConexao();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public String buscaStatus(int id){
		String status = "";
		String comando = "SELECT status FROM dispositivos WHERE id=" + id;
		Conexao conec = new Conexao();
		try{
			Connection conexao = conec.abrirConexao();
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				status = rs.getString("status");
			}
			conec.fecharConexao();		
		} catch (SQLException e){
			e.printStackTrace();
		}
		return status;
	}

	public String buscaDataFalha(int id){
		String data = "";
		String comando = "SELECT DataHoraFalha FROM dispositivos WHERE id=" + id;
		Conexao conec = new Conexao();
		try{
			Connection conexao = conec.abrirConexao();
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				data = rs.getString("DataHoraFalha");
			}
			conec.fecharConexao();		
		} catch (SQLException e){
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
