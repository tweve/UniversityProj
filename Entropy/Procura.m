function C= Procura( A,P)
% Exercicio numero 1:
contador=0;
for i=1:length(A), % percorre todos os valores do alfabeto
   for e = 1:length(P(:)),
       if P(e)==A(i)
           contador = contador+1;
       end
   end
   C(i)= contador;
   contador = 0;
end
end

