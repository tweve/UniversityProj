%   2.1.1
t0 = input('Introduza o per�odo fundamental:');

%   2.1.2
t = linspace(0,t0,500);

%   2.1.3
escolha = menu('Escolha uma op��o:','Onda Quadrada Peri�dia','Onda Dente Serra','Introduzir Express�o');

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

escolha = menu('Ruido:','Aleat�rio','Definido em gama','Express�o');

if (escolha ==1 )
    %aleatorio
    
    ruido = 0.2 * (rand(size(t))-0.5);
elseif (escolha == 2)
    
    %entre gamas
    min = input('Minimo: ');
    max = input('M�ximo: ');

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


% 
% figure(1);
% 
% plot(t,x);
% hold on;
% plot(t,x_ruido,'r');
% plot(t,filtro,'g');
% 
% figure(2);
%    
% [cmF,tetamF] = SerieFourier(t', filtro',t0, m_max);
% subplot(4,1,1), plot(cmR, '.');
% title('cm antes do Filtro');
% subplot(4,1,2), plot(tetamR, '.');
% title('tetam antes do Filtro');
% subplot(4,1,3), plot(cmF, '.');
% title('cm depois do Filtro');
% subplot(4,1,4), plot(tetamF, '.');
% title('tetam depois do Filtro');


%%%%%%%%%%%%%%%%%%%%%%
% 
% %   2.1.4
% [cm,tetam]= SerieFourier (t',x',t0,m_max);
% 
% figure(1);
% subplot(2,1,1)
%     plot(cm, '.');
% title('Cm');
% 
% subplot(2,1,2)
%     plot(tetam, '.');
% title('Tetam');
% 
% 
% %   2.1.5
% figure (2);
% plot(t, x, 'b');
% hold on;
% maximos = [0 1 3 5 10 50 100];
% 
% for i = 1:length(maximos),
%     figure(2);
%     plot(t, AproximacaoFourier(cm,tetam,maximos(i),t0,t), 'g');
% 
% end
% 
% %   2.1.6
% figure(3);
% cmcomplexos = CMComplexo(100,cm,tetam);
% subplot(2,1,1)
%     plot (-100:100,angle(cmcomplexos),'r');
%     title('Representa��o em angulo');
% subplot(2,1,2)
%     plot(-100:100,abs(cmcomplexos),'r');
%     title('Representa��o em norma');
% 
%     
% %   2.2
% %   Relat�rio
% 
% 
% %   2.3
% %   Relat�rio
% 
% 
% %   2.4
% syms m t;
% 
% xt = 3*sin(12*pi*t+(pi/4)).*cos(21*pi*t);
% x2t= -2+4*cos(4*t+(pi/3)) -2*sin(10*t);
% 
% t0= 2*pi/3;
% cm = int(xt*exp(-j*m*(2*pi/t0)*t),-pi/2,pi/2)* (1/t0);
% 
% t0 = pi;
% cm2 = int(x2t*exp(-j*m*(2*pi/t0)*t),-pi/2,pi/2)*(1/t0);
% 
% c3 = double(subs(cm,m,3))
% c11 = double(subs(cm,m,11))
% 
% c0 = double(subs(cm2,m,0))
% c2 = double(subs(cm2,m,2))
% c5 = double(subs(cm2,m,5))
% 
% 
% 
% 
% 
