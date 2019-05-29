/*
 * Criação do objeto SENAI, que nos serve para controle e organização. Toda a nossa estrutura da aplicação fica abaixo dele. Ex:
 * SENAI.contato.METODOS_E_VARIAVEIS
 * SENAI.usuario.METODOS_E_VARIAVEIS
 * e assim por diante. Assim nossa aplicação fica estruturada, trabalhando sempre com
 * o objeto SENAI e ver se os objetos internos existem. Por exemplo, na página de contato,
 * ver se eu tenho acesso ao objeto SENAI.contato. Assim seguimos um padrão que facilita o desenvolvimento.
 */
sMONITOR = new Object();

/*
 * Rotina jQuery para carregarmos, dinamicamente, os conteúdos HTML do cadastro de contato
 * e da consulta dos contatos registrados, na página index.html, que será a página
 * principal de nossa aplicação.
*/
$(document).ready(function(){
	/*
	 * O método load carrega (insere) o conteúdo HTML da página informada, na div
	 * utilizada no seletor jQuery.
	 */
	$("#cadastroDispositivo").load("resources/dispositivo/cadastrarDispositivo.html");
	$("#listaDispositivo").load("resources/dispositivo/consultarDispositivo.html");
});