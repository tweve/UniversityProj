create or replace trigger rem_utilizador
before
delete on utilizador
for each row
begin

	delete from le
	where id_user = :old.id_user;
	delete from le where  id_mensagem in ( select id_mensagem from mensagem where id_user = :old.id_user ) ;
	delete from mensagem
	where id_user = :old.id_user;
	
	
	delete from dia_trabalho
	where id_user = :old.id_user;
	
	delete from recibo
	where id_user = :old.id_user;
	
	delete from requisicao
	where id_user = :Old.id_user;
	
	delete from ferias
	where id_user = :Old.id_user;
	
	delete from administrador
	where id_user = :old.id_user;
	
	delete from funcionario
	where id_user = :old.id_user;
	

end;
