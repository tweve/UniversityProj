create or replace procedure reqSala(user_id IN utilizador.id_user%type, nome_sala_in in requisicao.nome_sala%type, HORA_INICIO IN VARCHAR, HORA_FIM IN VARCHAR) is 

quantia number; 

BEGIN 

	select count(*) into quantia
	from requisicao 
	where nome_sala = nome_sala_in; 
	
	if quantia = 0 then 
	
		INSERT INTO requisicao VALUES(REQUISICAO_ID_SEQ.NEXTVAL,user_id,nome_sala_in, to_date(HORA_INICIO,'hh24:mi'), to_date(HORA_FIM,'hh24:mi'));
	end if;
end;
/