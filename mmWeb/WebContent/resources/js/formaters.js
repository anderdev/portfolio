/*Função Pai de Mascaras*/
function Mascara(o,f){
    v_obj=o;
    v_fun=f;
    setTimeout("execmascara()",1);
}
    
/*Função que Executa os objetos*/
function execmascara(){
    v_obj.value=v_fun(v_obj.value);
}
    
/*Função que Determina as expressões regulares dos objetos*/
function leech(v){
    v=v.replace(/o/gi,"0");
    v=v.replace(/i/gi,"1");
    v=v.replace(/z/gi,"2");
    v=v.replace(/e/gi,"3");
    v=v.replace(/a/gi,"4");
    v=v.replace(/s/gi,"5");
    v=v.replace(/t/gi,"7");
    return v;
}

/*Função que permite apenas numeros*/
function Integer(v){
    return v.replace(/\D/g,"");
}

/*Função que padroniza telefone (11) 4184-1241*/
function Telefone(v){
    v=v.replace(/\D/g,"");              
    v=v.replace(/^(\d\d)(\d)/g,"($1) $2"); 
    v=v.replace(/(\d{4})(\d)/,"$1-$2");    
    return v;
}

/*Função que padroniza telefone (11) 41841241*/
function TelefoneCall(v){
    v=v.replace(/\D/g,"");                 
    v=v.replace(/^(\d\d)(\d)/g,"($1) $2");    
    return v;
}

/*Função que padroniza CPF*/
function Cpf(v){
    v=v.replace(/\D/g,"");                    
    v=v.replace(/(\d{3})(\d)/,"$1.$2");       
    v=v.replace(/(\d{3})(\d)/,"$1.$2");       
                                             
    v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2"); 
    return v;
}

/*Função que padroniza CEP*/
function Cep(v){
    v=v.replace(/D/g,"");                
    v=v.replace(/^(\d{5})(\d)/,"$1-$2"); 
    return v;
}

/*Função que padroniza HORA*/
function Hora(v){
    v=v.replace(/D/g,"");                
    v=v.replace(/^(\d{5})(\d)/,"$1-$2"); 
    return v;
}

/*Função que padroniza CNPJ*/
function Cnpj(v){
    v=v.replace(/\D/g,"");                   
    v=v.replace(/^(\d{2})(\d)/,"$1.$2");     
    v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3"); 
    v=v.replace(/\.(\d{3})(\d)/,".$1/$2");           
    v=v.replace(/(\d{4})(\d)/,"$1-$2");              
    return v;
}

/*Função que padroniza o Site*/
function url(v){
    v=v.replace(/^http:\/\/?/,"");
    dominio=v;
    caminho="";
    if(v.indexOf("/")>-1)
        dominio=v.split("/")[0];
        caminho=v.replace(/[^\/]*/,"");
        dominio=dominio.replace(/[^\w\.\+-:@]/g,"");
        caminho=caminho.replace(/[^\w\d\+-@:\?&=%\(\)\.]/g,"");
        caminho=caminho.replace(/([\?&])=/,"$1");
    if(caminho!="")dominio=dominio.replace(/\.+$/,"");
        v="http://"+dominio+caminho;
    return v;
}

/*Função que padroniza DATA*/
function date(v){
    v=v.replace(/\D/g,""); 
    v=v.replace(/(\d{2})(\d)/,"$1/$2"); 
    v=v.replace(/(\d{2})(\d)/,"$1/$2"); 
    return v;
}

/*Função que padroniza DATA*/
function Hora(v){
    v=v.replace(/\D/g,""); 
    v=v.replace(/(\d{2})(\d)/,"$1:$2");  
    return v;
}

/*Função que padroniza valor monétario*/
function value(v){
    v=v.replace(/\D/g,""); //Remove tudo o que não é dígito
    v=v.replace(/^([0-9]{3}\.?){3}-[0-9]{2}$/,"$1.$2");
    //v=v.replace(/(\d{3})(\d)/g,"$1,$2")
    v=v.replace(/(\d)(\d{2})$/,"$1.$2"); //Coloca ponto antes dos 2 últimos digitos
    return v;
}

/*Função que padroniza Area*/
function areaCode(v){
    v=v.replace(/\D/g,""); 
    v=v.replace(/(\d)(\d{2})$/,"$1.$2"); 
    return v;
}


function onlyNumber(keybord){
	sep = 0;
	key = '';
	i = j = 0;
	len = len2 = 0;
	strCheck = '0123456789';
	aux = aux2 = '';
	
	if (document.all) // Internet Explorer
		whichCode = keybord.keyCode;
	else if(document.layers) // Nestcape
		whichCode = keybord.which;
	else
		whichCode = (window.Event) ? keybord.which : keybord.keyCode;
	
	if (whichCode == 13) return true; // Tecla Enter
	if (whichCode == 8)  return true; // Tecla Delete
	if (whichCode == 0)  return true; // Tecla Tab
	key = String.fromCharCode(whichCode); // Pegando o valor digitado
	if (strCheck.indexOf(key) == -1) {
		return false; // Valor inválido (não inteiro)
	}else{
		return true;
	}
}