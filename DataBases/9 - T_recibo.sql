CREATE OR REPLACE 
TRIGGER T_Recibo 
AFTER 
UPDATE OF HORA_SAIDA ON DIA_TRABALHO
FOR EACH ROW
DECLARE
	sal float;
	
BEGIN 
	
	select salario_base into sal from funcionario where id_user = :new.id_user;
	insert into recibo (id_recibo,id_user,data_recibo,quantia,horas_trab) values ( RECIBO_ID_SEQ.nextval,:new.id_user,sysdate,to_number (to_char(:new.hora_saida,'hh')-to_char(:new.hora_entrada,'hh')) *sal ,to_number (to_char(:new.hora_saida,'hh')-to_char(:new.hora_entrada,'hh')));
END; 
