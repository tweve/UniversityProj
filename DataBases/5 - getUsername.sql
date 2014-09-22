CREATE OR REPLACE FUNCTION getUsername(id in utilizador.id_user%type) return varchar is

username_x varchar(10);

begin

select username into username_x
from utilizador
where utilizador.id_user = id;

return username_x;

end;
/
