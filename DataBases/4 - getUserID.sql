CREATE OR REPLACE FUNCTION getUserID (usernamein IN utilizador.username%type) RETURN number is

id number;

BEGIN  

Select id_user into id
from utilizador 
where utilizador.username = usernamein
group by id_user;
	
Return id;  

END; 
