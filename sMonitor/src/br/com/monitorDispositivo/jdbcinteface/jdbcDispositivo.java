package br.com.monitorDispositivo.jdbcinteface;

import java.util.List;

import br.com.monitorDispositivo.objetos.Dispositivo;

public interface jdbcDispositivo {
	
	/*
	 * Assinatura do método inserir contendo como parâmetro o objeto
	 * contato, que possuirá as informações a serem inseridas no BD,
	 * obrigando a classe JDBCContatoDAO a implementar esse método.
	 */
	public boolean inserir(Dispositivo dispositivo);
	
	/*
	 * Assinatura do m�todo buscarPorNome contendo como par�metro
	 * a String nome, e retornando um List de objetos do tipo Contato,
	 * obrigando a classe JDBCContatoDAO a implementar esse m�todo.
	 */
	public List<Dispositivo> buscarPorDesc(String desc);
	
	/*
	 * Assinatura do método deletar contendo como parâmetro
	 * o int id, que  possuirá o id do contato a ser excluido,
	 * obrigado a classe JDBCContatoDAO a implementar esse método.
	 */
	public boolean deletar(int id);
	
	/*
	 * Assinatura do método buscarPorId contendo como parametro
	 * o int id, que  pussuirá o ido do contato a ser alterado,
	 * e retorna o contato encontrado  em forma de objeto.
	 */
	public Dispositivo buscarPorId(int id);
	
	/*
	 * Assinatura do método atualizar contendo como parâmentro o objeto
	 * contato, que possuirá as informações a serem atualizadas no BD,
	 * obrigando a classe JDBCContatoDAO a implementar esse método.
	 */
	public boolean atualizar(Dispositivo dispositivo);
}
