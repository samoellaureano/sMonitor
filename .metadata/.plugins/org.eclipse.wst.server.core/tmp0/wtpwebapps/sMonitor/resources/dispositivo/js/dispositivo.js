
sMONITOR.dispositivo = new Object();

$(document).ready(function(){
	loopBusca();
});

var loopBusca = function(){
	var valorBusca = $("#consultar").val();
	if(valorBusca == ""){
		sMONITOR.dispositivo.buscar();
		setTimeout(loopBusca,15000);
	}else{
		setTimeout(loopBusca,15000);
	}
	
	
}

sMONITOR.dispositivo.cadastrar = function(){
	$.ajax({
		type: "POST",
		url: "CadastraDispositivo",
		data: $("#cadastrarDispositivo").serialize(),
		success: function (msg){
			var cfg = {
					title: "Mensagem",
					height: 250,
					width: 400,
					modal: true,
					buttons: {
						"Ok": function(){
							$(this).dialog("close");
						}
					}							
			};
			$("#msg").html(msg.msg);
			$("#msg").dialog(cfg);
			$("#cadastrarDispositivo")[0].reset();
			sMONITOR.dispositivo.buscar();
		},
		error: function (rest){
			alert("Erro ao cadastrar um novo dispositivo");
		}
	});
};

sMONITOR.dispositivo.buscar = function(){
	var valorBusca = $("#consultar").val();
	sMONITOR.dispositivo.exibir(undefined, valorBusca);
};

sMONITOR.dispositivo.exibir = function(listaDeDispositivos, valorBusca){
	var html = "<div class='container'>" +
	"<table class='table table-dark table-striped'>"+
	"<tr>" +
	"<th>Descrição</th>" +
	"<th>Protocolo</th>"+
	"<th>IP/HostName</th>" +
	"<th>Porta</th>" +
	"<th>Status</th>" +
	"<th>Ações</th>" +
	"</tr>";

	//Se a listaDeContatos tiver indefinida, deve ser feita a busca no BD
	if(listaDeDispositivos == undefined){
		$.ajax({
			type:"POST",
			url:"ConsultaDispositivo",
			data:"valorBusca=" + valorBusca,

			success: function(listaDeDispositivos){
				sMONITOR.dispositivo.exibir(listaDeDispositivos);
			},

			//Em caso de erro na consulta, avisa o usuário.
			error: function(rest){
				alert("Erro ao consultar os dispositivos.");
			}
		});
	}else if(listaDeDispositivos != undefined && listaDeDispositivos.length > 0){
		for (var i=0; i<listaDeDispositivos.length; i++){
			var status;
			if(listaDeDispositivos[i].status == "OK"){
				status = ("<td style='color: green;'>" + listaDeDispositivos[i].status + "</td>");
			}else{
				status = ("<td style='color: red;'>" + listaDeDispositivos[i].status + "</td>");
			}
			html += "<tr>" +
			"<td>" + listaDeDispositivos[i].descritivo + "</td>" +
			"<td>" + listaDeDispositivos[i].protocolo + "</td>" +
			"<td>" + listaDeDispositivos[i].IPname + "</td>" +
			"<td>" + listaDeDispositivos[i].Porta + "</td>" +
			status +
			"<td>" +
			"<a class='link' onclick='sMONITOR.dispositivo.editar(" + listaDeDispositivos[i].id+")'>Editar</a>" +
			"<a class='link' onclick='sMONITOR.dispositivo.deletar(" + listaDeDispositivos[i].id+")'>Deletar</a>" +
			"</td>" +
			"</tr>"
		}
	}else if (listaDeDispositivos == ""){
		html += "<tr><td colspan = '5'>Nenhum registro encontrado</td></tr>";
	}

	html += "</table>" +
			"</div>";
	$("#resultadoDispositivos").html(html);
};

sMONITOR.dispositivo.muda = function(){
	if($("#btnMuda").val() == "CADASTRAR"){
		$("#cadastrarField").css({"display":"block"});
		$("#consultaField").css({"display":"none"});
		$("#btnMuda").val("VOLTAR");
	}else{
		$("#consultaField").css({"display":"block"});
		$("#cadastrarField").css({"display":"none"});
		$("#btnMuda").val("CADASTRAR");
	}
};

sMONITOR.dispositivo.protocolo = function(){
	if($("#protocol").val() == "Telnet"){
		$("#dport").css({"display":"block"});
	}else{
		$("#dport").css({"display":"none"});
	}
}

sMONITOR.dispositivo.deletar = function(id){
	$.ajax({
		type:"POST",
		url:"DeletaDispositivo",
		data:"id="+id,
		success: function(data){
			var cfg = {
					title: "Mensagem",
					height: 250,
					width: 400,
					modal: true,
					buttons: {
						"Ok": function(){
							$(this).dialog("close");
						}
					}							
			};
			$("#msg").html(data.msg);
			$("#msg").dialog(cfg);
			sMONITOR.dispositivo.buscar();
		},
		error: function(rest){
			alert("Erro ao deletar dispositivo.");
		}
	});
};

sMONITOR.dispositivo.editar = function(id){
	$.ajax({
		type:"POST",
		url:"ConsultaDispositivoPorId",
		data:"id="+id,
		success: function(dispositivo){
			$("#descricaoEdit").val(dispositivo.descritivo);
			$("#protocoloEdit").val(dispositivo.protocolo);
			$("#ipHostEdit").val(dispositivo.IPname);
			$("#idEdit").val(dispositivo.id);
			sMONITOR.dispositivo.exibirEdicao();
		},
		error: function(rest){
			alert("Erro ao encontrar o dispositivo a ser alterado.");
		}
	});
};

sMONITOR.dispositivo.exibirEdicao = function(){
	var cfg = {
			title: "Editar Dispositivo",
			height: 400,
			width: 550,
			modal: true,
			buttons: {
				"Salvar": function(){
					var dialog = this;
					var newConta = "descricao="+$("#descricaoEdit").val()+
					"&protocolo="+$("#protocoloEdit").val()+
					"&IPname="+$("#ipHostEdit").val()+
					"&id="+$("#idEdit").val();
					$.ajax({
						type:"POST",
						url:"EditaDispositivo",
						data:newConta,
						success: function (data){
							$(dialog).dialog("close");//Fecha o formulario de edição

							var cfg = {
									title: "Mensagem",
									height: 250,
									width: 400,
									modal: true,
									buttons: {
										"Ok": function(){
											$(this).dialog("close");//Fecha a responsta de edição
										}
									}							
							};
							$("#msg").html(data.msg);
							$("#msg").dialog(cfg);
							//Atualiza a tabela de contatos
							sMONITOR.dispositivo.buscar();
						},
						error: function(rest){
							alert("Erro ao editar o dispositivo.");
						}
					});
				}, "Cancelar": function(){
					$(this).dialog("close");
					sMONITOR.dispositivo.buscar();
				}
			},
			close: function(){
			}
	};
	$("#editarDispositivo").dialog(cfg);
};
