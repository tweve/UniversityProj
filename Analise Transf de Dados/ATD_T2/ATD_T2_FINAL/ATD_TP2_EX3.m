% Igor Nelson Garrido da Cruz 
% Goncalo Silva Pereira


%   2.1.1
t0 = input('Introduza o período fundamental:');

%   2.1.2
t = linspace(0,t0,500);

%   2.1.3
escolha = menu('Escolha uma opção:','Onda Quadrada Periódia','Onda Dente Serra','Introduzir Expressão');

if (escolha ==1 )
    x = zeros (size(t));
    x(1:round(length(t)/2))= 1;
elseif (escolha == 2)
    x=t/t0;
elseif (escolha == 3)
      x = input('x(t)=');
end


m_max = 100;

% % EX3
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

escolha = menu('Ruido:','Aleatório','Definido em gama','Expressão');

if (escolha ==1 )
    %aleatorio
    
    ruido = 0.2 * (rand(size(t))-0.5);
elseif (escolha == 2)
    
    %entre gamas
    min = input('Minimo: ');
    max = input('Máximo: ');

    mr =min:max;
    cm = 0.2*rand(size(mr));
    tetamr = 2*pi*rand(size(mr))-pi;
    ruido = zeros(size(t));
        
    for m=min:max
        
        ruido = ruido + cm (m-min+1)* cos (m * 2 * pi /t0 * t + tetamr(m-min+1));
    
    end
    
        
        
elseif (escolha == 3)
    ruido = input('x(t)=');
end
%   3.1.2
x_ruido = x+ruido;

[cm,tetam]= SerieFourier (t',x',t0,m_max);
[cmR,tetamR]= SerieFourier (t',x_ruido',t0,m_max);


escolha = menu('Escolha o tipo de filtro:','Passa-Baixo','Passa-Alto','Passa-Banda','Rejeita-Banda');
if(escolha ==1) 
         max = input('Frequencia maxima: '); 
         min = 0;
         filtro = zeros(size(t));
         for i = min:max
            filtro = filtro + cmR (i - min +1) * cos(i * 2 * pi /t0 * t + tetamR(i - min +1));
         end
elseif(escolha == 2)
        min = input('Frequencia minima:'); 
        max = m_max;
        filtro = zeros(size(t));
        for i = min : max
           filtro = filtro + cmR (i - min +1) * cos (i * 2 * pi /t0 * t + tetamR(i - min +1));
        end
        
elseif(escolha == 3)
        min = input('Frequencia minima:'); 
        max = input('Frequencia maxima: ');
        filtro = zeros(size(t));
        
        for i = min:max
            filtro = filtro + cmR (i - min +1) * cos (i * 2 * pi /t0 * t + tetamR(i - min +1));
        end
elseif(escolha == 4)
        min = input('Frequencia minima:'); 
        max = input('Frequencia maxima: ');
        filtro = zeros(size(t));
        for i = 1:min
            filtro = filtro + cmR (i) * cos (i * 2 * pi /t0 * t + tetamR(i));
        end
        for i = max:m_max
            filtro = filtro + cmR (i - max +1) * cos (i * 2 * pi /t0 * t + tetamR(i - max +1));
        end 
end

plot(t,x);
hold on;

plot(t,x_ruido,'r');
plot(t,filtro,'g');


figure(2);
   
[cmF,tetamF] = SerieFourier(t', filtro',t0, m_max);
subplot(4,1,1), plot(cmR, '.');
title('cm antes do Filtro');
subplot(4,1,2), plot(tetamR, '.');
title('tetam antes do Filtro');
subplot(4,1,3), plot(cmF, '.');
title('cm depois do Filtro');
subplot(4,1,4), plot(tetamF, '.');
title('tetam depois do Filtro');

