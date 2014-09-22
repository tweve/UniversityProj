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

% % EX3
% 
% escolha = menu('Ruido:','Aleatório','Definido em gama','Expressão');
% 
% if (escolha ==1 )
%     %aleatorio
%     
%     ruido = 0.2 * (rand(size(t))-0.5);
% elseif (escolha == 2)
%     
%     %entre gamas
%     mr_min = input('Frequencia minima\n\n  -> ');
%         mr_max = input('Frequencia maxima\n\n  -> ');       
%         Crm = 0.01 * abs (random('Normal',1,1,mr_max-mr_min+1,1));	
% 		Crm
%         Orm = pi * (random('Normal',1,1,mr_max-mr_min+1,1)-0.5);   
%         ruido = zeros(size(t)); 
%         for m=mr_min:mr_max
%             ruido = ruido + Crm (m - mr_min +1) * cos (m * 2 * pi /t0 * t + Orm(m - mr_min +1));
%         end
%         
%         
% elseif (escolha == 3)
%     ruido = input('x(t)=');
% end
% 
% %x_ruido = x+ruido;

%   2.1.4
m_max = 100;
[cm,tetam]= SerieFourier (t',x',t0,m_max);

figure(1);
subplot(2,1,1)
    plot(cm, '.');
title('Cm');

subplot(2,1,2)
    plot(tetam, '.');
title('Tetam');


%   2.1.5
figure (2);
plot(t, x, 'b');
hold on;
maximos = [0 1 3 5 10 50 100];

for i = 1:length(maximos),
    figure(2);
    plot(t, AproximacaoFourier(cm,tetam,maximos(i),t0,t), 'g');

end

%   2.1.6
figure(3);
cmcomplexos = CMComplexo(100,cm,tetam)
subplot(2,1,1)
    plot (-100:100,angle(cmcomplexos),'r');
    title('Representação em angulo');
subplot(2,1,2)
    plot(-100:100,abs(cmcomplexos),'r');
    title('Representação em norma');

    
%   2.2
%   Relatório


%   2.3
%   Relatório


%   2.4
syms m t;

xteste = cos(4*t)*sin(6*t);

xt = 3*sin(12*pi*t+(pi/4)).*cos(21*pi*t);

x2t= -2+4*cos(4*t+(pi/3)) -2*sin(10*t);

cm = int(xt*exp(-j*m*2*t),-pi/2,pi/2)* (1/ (2*pi/3));
cm2 = int(x2t*exp(-j*m*2*t),-pi/2,pi/2)* (1/ pi);


cm = subs(cm,m,-100:100);
cm2 = subs(cm,m,-100:100);
figure(4);
subplot(2,1,1)
    plot (-100:100,angle(cm),'r');
    title('Representação em angulo');
subplot(2,1,2)
    plot(-100:100,abs(cm),'r');
    title('Representação em norma');

figure(5);
subplot(2,1,1)
    plot (-100:100,angle(cm2),'r');
    title('Representação em angulo');
subplot(2,1,2)
    plot(-100:100,abs(cm2),'r');
    title('Representação em norma');





