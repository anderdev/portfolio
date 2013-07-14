/*Função Pai de Mascaras*/
function Mascara(o,f){
    v_obj=o
    v_fun=f
    setTimeout("execmascara()",1)
}
    
/*Função que Executa os objetos*/
function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}
    
/*Função que Determina as expressões regulares dos objetos*/
function leech(v){
    v=v.replace(/o/gi,"0")
    v=v.replace(/i/gi,"1")
    v=v.replace(/z/gi,"2")
    v=v.replace(/e/gi,"3")
    v=v.replace(/a/gi,"4")
    v=v.replace(/s/gi,"5")
    v=v.replace(/t/gi,"7")
    return v
}

/*Função que permite apenas numeros*/
function Integer(v){
    return v.replace(/\D/g,"")
}

/*Função que padroniza telefone (11) 4184-1241*/
function Telefone(v){
    v=v.replace(/\D/g,"")                 
    v=v.replace(/^(\d\d)(\d)/g,"($1) $2") 
    v=v.replace(/(\d{4})(\d)/,"$1-$2")    
    return v
}

/*Função que padroniza telefone (11) 41841241*/
function TelefoneCall(v){
    v=v.replace(/\D/g,"")                 
    v=v.replace(/^(\d\d)(\d)/g,"($1) $2")    
    return v
}

/*Função que padroniza CPF*/
function Cpf(v){
    v=v.replace(/\D/g,"")                    
    v=v.replace(/(\d{3})(\d)/,"$1.$2")       
    v=v.replace(/(\d{3})(\d)/,"$1.$2")       
                                             
    v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2") 
    return v
}

/*Função que padroniza CEP*/
function Cep(v){
    v=v.replace(/D/g,"")                
    v=v.replace(/^(\d{5})(\d)/,"$1-$2") 
    return v
}

/*Função que padroniza CNPJ*/
function Cnpj(v){
    v=v.replace(/\D/g,"")                   
    v=v.replace(/^(\d{2})(\d)/,"$1.$2")     
    v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3") 
    v=v.replace(/\.(\d{3})(\d)/,".$1/$2")           
    v=v.replace(/(\d{4})(\d)/,"$1-$2")              
    return v
}

/*Função que permite apenas numeros Romanos*/
function Romanos(v){
    v=v.toUpperCase()             
    v=v.replace(/[^IVXLCDM]/g,"") 
    
    while(v.replace(/^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$/,"")!="")
        v=v.replace(/.$/,"")
    return v
}

/*Função que padroniza o Site*/
function Site(v){
    v=v.replace(/^http:\/\/?/,"")
    dominio=v
    caminho=""
    if(v.indexOf("/")>-1)
        dominio=v.split("/")[0]
        caminho=v.replace(/[^\/]*/,"")
        dominio=dominio.replace(/[^\w\.\+-:@]/g,"")
        caminho=caminho.replace(/[^\w\d\+-@:\?&=%\(\)\.]/g,"")
        caminho=caminho.replace(/([\?&])=/,"$1")
    if(caminho!="")dominio=dominio.replace(/\.+$/,"")
        v="http://"+dominio+caminho
    return v
}

/*Função que padroniza DATA*/
function Data(v){
    v=v.replace(/\D/g,"") 
    v=v.replace(/(\d{2})(\d)/,"$1/$2") 
    v=v.replace(/(\d{2})(\d)/,"$1/$2") 
    return v
}

/*Função que padroniza DATA CARTÃO*/
function DataCartao(v){
    v=v.replace(/\D/g,"") 
    v=v.replace(/(\d{2})(\d)/,"$1/$2") 
    return v
}

/*Função que padroniza DATA*/
function Hora(v){
    v=v.replace(/\D/g,"") 
    v=v.replace(/(\d{2})(\d)/,"$1:$2")  
    return v
}

/*Função que padroniza valor monétario*/
function Valor(v){
    v=v.replace(/\D/g,"") //Remove tudo o que não é dígito
    v=v.replace(/^([0-9]{3}\.?){3}-[0-9]{2}$/,"$1.$2");
    //v=v.replace(/(\d{3})(\d)/g,"$1,$2")
    v=v.replace(/(\d)(\d{2})$/,"$1.$2") //Coloca ponto antes dos 2 últimos digitos
    return v
}

/*Função que padroniza Area*/
function Area(v){
    v=v.replace(/\D/g,"") 
    v=v.replace(/(\d)(\d{2})$/,"$1.$2") 
    return v
}

function formatar_moeda(campo, separador_milhar, separador_decimal, tecla, maxLength) {
	var sep = 0;
	var key = '';
	var i = j = 0;
	var len = len2 = 0;
	var strCheck = '0123456789';
	var aux = aux2 = '';
	
	if (document.all) // Internet Explorer
		var whichCode = tecla.keyCode;
	else if(document.layers) // Nestcape
		var whichCode = tecla.which;
	else
		var whichCode = (window.Event) ? tecla.which : tecla.keyCode;

	if (whichCode == 13) return true; // Tecla Enter
	if (whichCode == 8) return true; // Tecla Delete
	key = String.fromCharCode(whichCode); // Pegando o valor digitado
	if (strCheck.indexOf(key) == -1) return false; // Valor inválido (não inteiro)
	len = maxLength;
	for(i = 0; i < len; i++)
	if ((campo.value.charAt(i) != '0') && (campo.value.charAt(i) != separador_decimal)) break;
	aux = '';
	for(; i < len; i++)
	if (strCheck.indexOf(campo.value.charAt(i))!=-1) aux += campo.value.charAt(i);
	aux += key;
	len = aux.length;
	if (len == 0) campo.value = '';
	if (len == 1) campo.value = '0'+ separador_decimal + '0' + aux;
	if (len == 2) campo.value = '0'+ separador_decimal + aux;

	if (len > 2) {
		aux2 = '';

		for (j = 0, i = len - 3; i >= 0; i--) {
			if (j == 3) {
				aux2 += separador_milhar;
				j = 0;
			}
			aux2 += aux.charAt(i);
			j++;
		}

		campo.value = '';
		len2 = aux2.length;
		for (i = len2 - 1; i >= 0; i--)
		campo.value += aux2.charAt(i);
		campo.value += separador_decimal + aux. substr(len - 2, len);
	}

	return false;
}

function somente_numeros(tecla){
	var sep = 0;
	var key = '';
	var i = j = 0;
	var len = len2 = 0;
	var strCheck = '0123456789';
	var aux = aux2 = '';
	
	if (document.all) // Internet Explorer
		var whichCode = tecla.keyCode;
	else if(document.layers) // Nestcape
		var whichCode = tecla.which;
	else
		var whichCode = (window.Event) ? tecla.which : tecla.keyCode;

	if (whichCode == 13) return true; // Tecla Enter
	if (whichCode == 8)  return true; // Tecla Delete
	key = String.fromCharCode(whichCode); // Pegando o valor digitado
	if (strCheck.indexOf(key) == -1) {
		return false; // Valor inválido (não inteiro)
	}else{
		return true;
	}
}
