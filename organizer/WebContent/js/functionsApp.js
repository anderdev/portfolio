function executarURL( url ){
	location.replace(url);
}

function invalidaDivs(obj){
	if(obj=='locale'){
		document.getElementById("chamaPais").style.visibility = "hidden";
		document.getElementById("chamaEstado").style.visibility = "hidden";
		document.getElementById("chamaCidade").style.visibility = "hidden";	
	}else if (obj=='descricao'){
		document.getElementById("chamaDescricaoCredito").style.visibility = "hidden";
		document.getElementById("chamaDescricaoDebito").style.visibility = "hidden";
		document.getElementById("chamaDescricaoPagamento").style.visibility = "hidden";	
		document.getElementById("chamaDescricaoAgrupadores").style.visibility = "hidden";	
	}else if(obj=='configsGerais'){
		document.getElementById("chamaMoeda").style.visibility = "hidden";
		document.getElementById("chamaTipoFechamento").style.visibility = "hidden";
	}
	else{
		document.getElementById("chamaGravado").style.visibility = "hidden";
	}
}


function validaMenu(tipoAcesso){

	if (tipoAcesso == 'chamaGravado'){
		invalidaDivs('');
		document.getElementById("chamaGravado").style.visibility = "visible";		
	}
	if (tipoAcesso == 'chamaPais'){
		invalidaDivs('locale');
		document.getElementById("chamaPais").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaEstado'){
		invalidaDivs('locale');
		document.getElementById("chamaEstado").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaCidade'){
		invalidaDivs('locale');
		document.getElementById("chamaCidade").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaDescricaoCredito'){
		invalidaDivs('descricao');
		document.getElementById("chamaDescricaoCredito").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaDescricaoDebito'){
		invalidaDivs('descricao');
		document.getElementById("chamaDescricaoDebito").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaDescricaoPagamento'){
		invalidaDivs('descricao');
		document.getElementById("chamaDescricaoPagamento").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaDescricaoAgrupadores'){
		invalidaDivs('descricao');
		document.getElementById("chamaDescricaoAgrupadores").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaTipoFechamento'){
		invalidaDivs('configsGerais');
		document.getElementById("chamaTipoFechamento").style.visibility = "visible";		
	}
	
	if (tipoAcesso == 'chamaMoeda'){
		invalidaDivs('configsGerais');
		document.getElementById("chamaMoeda").style.visibility = "visible";		
	}
}
/*
function carregarEstadosPorPais(paisCodigo){
	executarURL('carregarEstadosPorPais.org?paisCodigo='+paisCodigo);
}

function carregarCidadesPorEstado(estadoCodigo,nome,email,dtnasc,cidadeCodigo,usuario){
	executarURL('carregarCidadesPorEstado.org?estadoCodigo='+estadoCodigo+'&nome='+nome+'&email='+email+'&dtnasc='+dtnasc+'&cidadeCodigo='+cidadeCodigo+'&usuario='+usuario);
}
*/


function emailCheck (emailStr) {
	var checkTLD=1;
	var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;
	var emailPat=/^(.+)@(.+)$/;
	var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
	var validChars="\[^\\s" + specialChars + "\]";
	var quotedUser="(\"[^\"]*\")";
	var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
	var atom=validChars + '+';
	var word="(" + atom + "|" + quotedUser + ")";
	var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
	var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");
	var matchArray=emailStr.match(emailPat);
	
	if (matchArray==null) {
		//alert("Email address seems incorrect (check @ and .'s)");
		return false;
	}
	var user=matchArray[1];
	var domain=matchArray[2];
	for (i=0; i<user.length; i++) {
		if (user.charCodeAt(i)>127) {
			//alert("Ths username contains invalid characters.");
			return false;
		}
	}
	for (i=0; i<domain.length; i++) {
		if (domain.charCodeAt(i)>127) {
			//alert("Ths domain name contains invalid characters.");
			return false;
		}
	}
	if (user.match(userPat)==null) {
		//alert("The username doesn't seem to be valid.");
		return false;
	}
	var IPArray=domain.match(ipDomainPat);
	if (IPArray!=null) {
		for (var i=1;i<=4;i++) {
			if (IPArray[i]>255) {
				//alert("Destination IP address is invalid!");
				return false;
			}
		}
		return true;
	}
	var atomPat=new RegExp("^" + atom + "$");
	var domArr=domain.split(".");
	var len=domArr.length;
	for (i=0;i<len;i++) {
		if (domArr[i].search(atomPat)==-1) {
			//alert("The domain name does not seem to be valid.");
			return false;
		}
	}
	if (checkTLD && domArr[domArr.length-1].length!=2 && 
		domArr[domArr.length-1].search(knownDomsPat)==-1) {
		//alert("The address must end in a well-known domain or two letter " + "country.");
		return false;
	}
	if (len<2) {
		//alert("This address is missing a hostname!");
		return false;
	}
	return true;
}

function validaFormContato(){
	d = document.frmContato;
	
     //validar nome
    if (d.nome.value == ""){
        alert("O campo " + d.nome.name + " deve ser preenchido!");
        d.nome.focus();
        return false;
    }   
    //validar email
    if (d.email.value == ""){
        alert("O campo " + d.email.name + " deve ser preenchido!");
        d.email.focus();
        return false;
    }
    //validar email(verificao de endereco eletrônico)
    if (!emailCheck (d.email.value)) {
        alert ("O campo " + d.email.name + " deve conter um endereço eletrônico válido!\n Ex.: fulano@fulano.com");
        d.email.focus();
        return false;
    } 
    //validar mensagem
    if (d.mensagem.value.length <= 10){
        alert("Sua mensagem deve contar mais que 10 caracteres!");
        d.mensagem.focus();
        return false;
    }
    document.frmContato.enviar.disabled = true;
	document.frmContato.limpar.disabled = true;
    return true;       
}

function selecionaAba(obj){	
	var totalAbas = document.getElementsByTagName("li").length;            
    for (var x=0;x<totalAbas;x++){
    	document.getElementsByTagName("li")[x].id = "x";
    } 
    if (obj!=null){    
    	obj.id = "current";
    }	
}


function desabilitaMensagemLocale(){
	document.getElementById("chamaGravadoLocale").style.visibility = "hidden";	
}

function desabilitaMensagemDescricao(){
	document.getElementById("chamaGravadoDescricao").style.visibility = "hidden";	
}

function desabilitaMensagemConfigsGerais(){
	document.getElementById("chamaGravadoConfigsGerais").style.visibility = "hidden";	
}

function editar(codigo,origem){
	if(origem=='pais'){
		executarURL('editarPais.org?codigo='+codigo);
	}
	if(origem=='estado'){
		executarURL('editarEstado.org?codigo='+codigo);
	}
	if(origem=='cidade'){
		executarURL('editarCidade.org?codigo='+codigo);
	}
	if(origem=='moeda'){
		executarURL('editarMoeda.org?codigo='+codigo);
	}
	if(origem=='tipoFechamento'){
		executarURL('editarTipoFechamento.org?codigo='+codigo);
	}
	if(origem=='descricaoCredito'){
		executarURL('editarDescricaoCredito.org?codigo='+codigo);
	}
	if(origem=='descricaoDebito'){
		executarURL('editarDescricaoDebito.org?codigo='+codigo);
	}
	if(origem=='descricaoPagamento'){
		executarURL('editarDescricaoPagamento.org?codigo='+codigo);
	}
	if(origem=='grupoDeDados'){
		executarURL('editarGrupoDeDados.org?codigo='+codigo);
	}
	if(origem=='credito' || origem=='listarCreditos'){
		executarURL('editarCredito.org?codigo='+codigo+'&origemTela='+origem);
	}
	if(origem=='debito' || origem=='listarDebitos'){
		executarURL('editarDebito.org?codigo='+codigo+'&origemTela='+origem);
	}
	if(origem=='pagamento' || origem=='listarPagamentos'){
		executarURL('editarPagamento.org?codigo='+codigo+'&origemTela='+origem);
	}
	if(origem=='fechamento' || origem=='listaFechamentos'){
		executarURL('editarFechamento.org?codigo='+codigo+'&origemTela='+origem);
	}
}
function excluir(codigo,origem){
	if(origem=='pais'){
		executarURL('excluirPais.org?codigo='+codigo);
	}
	if(origem=='estado'){
		executarURL('excluirEstado.org?codigo='+codigo);
	}
	if(origem=='cidade'){
		executarURL('excluirCidade.org?codigo='+codigo);
	}
	if(origem=='moeda'){
		executarURL('excluirMoeda.org?codigo='+codigo);
	}
	if(origem=='tipoFechamento'){
		executarURL('excluirTipoFechamento.org?codigo='+codigo);
	}
	if(origem=='descricaoCredito'){
		executarURL('excluirDescricaoCredito.org?codigo='+codigo);
	}
	if(origem=='descricaoDebito'){
		executarURL('excluirDescricaoDebito.org?codigo='+codigo);
	}
	if(origem=='descricaoPagamento'){
		executarURL('excluirDescricaoPagamento.org?codigo='+codigo);
	}
	if(origem=='grupoDeDados'){
		executarURL('excluirGrupoDeDados.org?codigo='+codigo);
	}
	if(origem=='credito' || origem=='listarCreditos'){
		executarURL('excluirCredito.org?codigo='+codigo+'&origemTela='+origem);
	}
	if(origem=='debito' || origem=='listarDebitos'){
		executarURL('excluirDebito.org?codigo='+codigo+'&origemTela='+origem);
	}
	if(origem=='pagamento' || origem=='listarPagamentos'){
		executarURL('excluirPagamento.org?codigo='+codigo+'&origemTela='+origem);
	}
	if(origem=='fechamento' || origem=='listarFechamentos'){
		executarURL('excluirFechamento.org?codigo='+codigo+'&origemTela='+origem);
	}
	
}

function verificaOrigem(origem){
	if (origem == 'moeda'){
		executarURL('manterConfigsGerais.org?tipoConfig=moeda');
	}
	
	if (origem == 'tipoFechamento'){
		executarURL('manterConfigsGerais.org?tipoConfig=tipoFechamento');
	}
	
	if (origem == 'descricaoCredito'){
		executarURL('manterDescricao.org?tipoDescricao=credito');
	}
	
	if (origem == 'descricaoDebito'){
		executarURL('manterDescricao.org?tipoDescricao=debito');
	}
	
	if (origem == 'descricaoPagamento'){
		executarURL('manterDescricao.org?tipoDescricao=pagamento');
	}
	
	if (origem == 'pais'){
		executarURL('manterLocale.org?tipoLocale=pais');
	}
	
	if (origem == 'estado'){
		executarURL('manterLocale.org?tipoLocale=estado');
	}
	
	if (origem == 'cidade'){
		executarURL('manterLocale.org?tipoLocale=cidade');
	}
}

var descricao = "";
var action ="";
var url="";
var telaOrigem="";

function habilitaCadastroDescricao(origem){
	if(origem =='credito'){
		descricao = "descricaoCredito";
		action ="salvarDescricaoCredito.org";
		url="manterCredito.org";
		telaOrigem="cadastroCredito";
	}else if(origem =='debito'){
		descricao = "descricaoDebito";
		action ="salvarDescricaoDebito.org";
		url="manterDebito.org";
		telaOrigem="cadastroDebito";
	}else if(origem =='pagamento'){
		descricao = "descricaoPagamento";
		action ="salvarDescricaoPagamento.org";
		url="manterPagamento.org";
		telaOrigem="cadastroPagamento";
	}
	document.getElementById("desabilitaAplicacaoDescricao").style.visibility = "visible";
}

function desabilitaCadastroDescricao(){
	document.getElementById("desabilitaAplicacaoDescricao").style.visibility = "hidden";
}

function habilitaConfirmaExclusao(codigoItem, origem){
	codigoExlusao = codigoItem;
	origemTela = origem;
	document.getElementById("desabilitaAplicacaoExclusao").style.visibility = "visible";
}

function desabilitaConfirmaExclusao(){
	document.getElementById("desabilitaAplicacaoExclusao").style.visibility = "hidden";
}

function desabilitaDatasContabilizadas(){
	document.getElementById("chamaFechamentoContabilizados").style.visibility = "hidden";
}

function somente_numero(campo){  
var digits="0123456789"  
var campo_temp   
	for (var i=0;i<campo.value.length;i++){  
    	campo_temp=campo.value.substring(i,i+1)   
        if (digits.indexOf(campo_temp)==-1){  
        	campo.value = campo.value.substring(0,i);  
        }  
    }  
} 

function numeros(e){
    var whichCode = (window.Event)? e.which : e.keyCode;
    if(whichCode>=48 && whichCode<=57 || whichCode==8 || whichCode==0 || whichCode==32) return true;
    return false;
}

function setaFocus(idCampo){
	document.getElementById(idCampo).focus();
} 
