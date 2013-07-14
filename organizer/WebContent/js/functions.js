function desabilitaCheck(){
	document.getElementById("ckCartao").style.display = "none";
	document.getElementById("comboCartao").style.display = "block";
}

function habilitaCheck(){
	document.getElementById("ckCartao").style.display = "block";
	document.getElementById("comboCartao").style.display = "none";
	document.getElementById("radioButton").checked = false;
}

function verificaCheckConsulta(tipo,tela){
	document.getElementById("divData").style.display = "none";
	document.getElementById("divDescricao").style.display = "none";
	document.getElementById("divGrupo").style.display = "none";
	document.getElementById("divSuperGrupo").style.display = "none";
	document.getElementById("divBotoes").style.display = "none";
	document.getElementById("divMaster").style.display = "none";
	document.getElementById("divCartao").style.display = "none";
	
	if(tipo=='data'){
		document.getElementById("divMaster").style.display = "block";
		document.getElementById("divData").style.display = "block";
		document.getElementById("divBotoes").style.display = "block";
	}
	if(tipo=='descricao'){
		document.getElementById("divMaster").style.display = "block";
		document.getElementById("divDescricao").style.display = "block";
		document.getElementById("divData").style.display = "block";
		document.getElementById("divBotoes").style.display = "block";
	}
	if(tipo=='grupo'){
		document.getElementById("divMaster").style.display = "block";
		document.getElementById("divGrupo").style.display = "block";
		document.getElementById("divData").style.display = "block";
		document.getElementById("divBotoes").style.display = "block";				
	}
	if(tipo=='superGrupo'){
		document.getElementById("divMaster").style.display = "block";
		document.getElementById("divSuperGrupo").style.display = "block";
		document.getElementById("divData").style.display = "block";
		document.getElementById("divBotoes").style.display = "block";
	}
	if(tipo=='cartao'){
		document.getElementById("divMaster").style.display = "block";
		document.getElementById("divCartao").style.display = "block";
		document.getElementById("divData").style.display = "block";
		document.getElementById("divBotoes").style.display = "block";
	}
}