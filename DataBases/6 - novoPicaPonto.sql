CREATE OR REPLACE PROCEDURE picaPonto (user_id IN utilizador.id_user%type)
IS
	CURSOR dias is select * from dia_trabalho where id_user = user_id and to_char(hora_entrada,'dd/mm/yy') like to_char(sysdate,'dd/mm/yy') for update;
	dia dias%ROWTYPE;
	estado number := 0;
	
BEGIN
	
FOR dia IN dias
	LOOP
		if dia.hora_saida is null then
				UPDATE dia_trabalho set hora_saida = systimestamp WHERE current of dias;
				estado :=1;
				exit;
			end if;
	END LOOP;
	if estado = 0 then
		INSERT INTO dia_trabalho(id_dia,id_user,hora_entrada,data_dia) VALUES(DIA_ID_SEQ.NEXTVAL,user_id,systimestamp,sysdate);
	end if;
	
END;