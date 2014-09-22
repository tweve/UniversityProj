
x = rand (20,1)-0.5;
valP = x(x>0)

disp(valP)
np = length(valP);
s = sprintf('Nº de Positivos = %d',np);
disp(s);