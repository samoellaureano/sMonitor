package br.com.monitorBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

public class bot {
	public void enviarMensagemTelegram(String message) {
		//Criação do objeto bot com as informações de acesso
		TelegramBot bot = TelegramBotAdapter.build("848852134:AAG_WcWDNhsfag3BmHugdrGuCTIPDkbUOXo");
		bot.execute(new SendMessage("746501439",message));
		
		/* ABAIXO O CODIGO QUE AGUARDA O ENVIO DE UMA MENSAGEM
		//Criação do objeto bot com as informações de acesso
		TelegramBot bot = TelegramBotAdapter.build("858854429:AAHcwWYGx3ReNSS27GABVYv19yfqLWXS-3c");

		//objeto responsável por receber as mensagens
		GetUpdatesResponse updatesResponse;
		//objeto responsável por gerenciar o envio de respostas
		SendResponse sendResponse;
		//objeto responsável por gerenciar o envio de ações do chat
		BaseResponse baseResponse;

		//controle de off-set, isto é, a partir deste ID será lido as mensagens pendentes na fila
		int m=0;

		//executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
		updatesResponse =  bot.execute(new GetUpdates().limit(1).offset(m));

		//lista de mensagens
		List<Update> updates = updatesResponse.updates();

		//análise de cada ação da mensagem
		for (Update update : updates) {
			System.out.println("Recebendo mensagem:"+ update.message().text());

			//envio de "Escrevendo" antes de enviar a resposta
			baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
			//verificação de ação de chat foi enviada com sucesso
			System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());

			sendResponse = bot.execute(new SendMessage(update.message().chat().id(),message));

			//verificação de mensagem enviada com sucesso
			System.out.println("Mensagem Enviada?" +sendResponse.isOk());

		}
		*/
	}
}
