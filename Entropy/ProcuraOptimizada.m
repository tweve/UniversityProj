function F = ProcuraOptimizada( A,P )

tmnh = length(A);
F = zeros(3,tmnh^2);
pos = 1;

%Cria�ao do novo dicionario
for i = 1:tmnh,   %percorre alfabeto
    for j= 1:tmnh,

        F(1,pos)= A(i);
        F(2,pos)= A(j);
        pos = pos+1;
        
    end   
end
for e = 1:2:length(P(:))-1,
       posx = find(F(1,:)==P(e));  % Elementos da matriz com o primeiro elemento igual ao que pesquisamos
       posy = find(F(2,:)==P(e+1));

       
       final = intersect(posx,posy);
       F(3,final)= F(3,final)+1;        %incrementa
end
F = F(3,:);
end
