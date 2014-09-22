function C = ProcuraAgrupada( A,P)
% Exercicio numero 5:
contador = 0;
pos = 1;
for i=1:length(A), % percorre todos os valores do alfabeto
    for j=1:length(A),           
            %A(i),A(j) <- NOVO ALFABETO
        for k = 1 :2:length(P(:)),         % Percorre fonte
            if (k < length (P(:)))     % protecçao para o caso de ser o ultimo elemento
                if P(k)== A(i) && P(k+1)==A(j)
                    contador = contador+1;
                end
            end
        end
        C(pos) = contador;
        contador = 0;
        pos = pos +1;
    end
end
end


