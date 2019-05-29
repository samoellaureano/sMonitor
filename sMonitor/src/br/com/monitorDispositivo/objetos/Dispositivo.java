package br.com.monitorDispositivo.objetos;

import java.io.Serializable;

public class Dispositivo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String descritivo;
	private String protocolo;
	private String status;
	private String IPname;
	private int Porta;
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getDescritivo(){
		return this.descritivo;
	}
	
	public void setDescritivo(String descritivo){
		this.descritivo = descritivo;
	}
	
	public String getProtocolo(){
		return this.protocolo;
	}
	
	public void setProtocolo(String protocolo){
		this.protocolo = protocolo;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getIPname(){
		return this.IPname;
	}
	
	public void setIPname(String IPname){
		this.IPname = IPname;
	}
	
	public int getPorta(){
		return this.Porta;
	}
	
	public void setPorta(int Porta){
		this.Porta = Porta;
	}

}
