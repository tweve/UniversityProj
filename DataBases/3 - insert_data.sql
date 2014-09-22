insert into utilizador (id_user,nome,username,password) values (1,'Super Homem','super','jj');
insert into utilizador (id_user,nome,username,password) values (2,'Master Yoda','yoda','pw');
insert into utilizador (id_user,nome,username,password) values (3,'Funcionário Malvado','func','qwe');
insert into utilizador (id_user,nome,username,password) values (4,'Raining Blood','Slayer','sl');


insert into funcionario (id_user,horas_trab,salario_base) values (1,50,1556.50);
insert into funcionario (id_user,horas_trab,salario_base) values (3,50,1000.50);

insert into administrador (id_user,cargo,especializacao) values (4,'Trash Metal','SLAYERRRRRRR');
insert into administrador (id_user,cargo,especializacao) values (2,'Jedi','Teh F0rce');

insert into requisicao values(1,4,'studio',sysdate,sysdate+1);

insert into mensagem values (1,3,'MENSAGEM DE TESTE','TEST',1);
insert into mensagem values (2,1,'I AM the super man','TESTmsg',1);

insert into le values (1,1);
insert into le values (2,1);
insert into le values (3,1);
insert into le values (4,1);
insert into le values (1,2);

commit;