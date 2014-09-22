% Igor Nelson Garrido da Cruz 
% Goncalo Silva Pereira


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
cmcomplexos = CMComplexo(100,cm,tetam);
subplot(2,1,1)
    plot (-100:100,unwrap(angle(cmcomplexos)),'r');
    title('Representa��o em angulo');
subplot(2,1,2)
    plot(-100:100,abs(cmcomplexos),'r');
    title('Representa��o em norma');

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
syms m t;

xt = 1+2*sin(12*pi*t+(pi/4)).*cos(21*pi*t);
x2t= -2+4*cos(4*t+(pi/3)) -2*sin(10*t);

t0= 2*pi/3;
cm = int(xt*exp(-j*m*(2*pi/t0)*t),-t0/2,t0/2) * (1/t0);

t0 = pi;
cm2 = int(x2t*exp(-j*m*(2*pi/t0)*t),-t0/2,t0/2) *(1/t0);

figure(4);
valores   = double(subs(cm,m,-100:100))
   
subplot(2,1,1)
    plot(-100:100,unwrap(angle(valores)),'g');
subplot(2,1,2)
    plot(-100:100,abs(valores),'g');
    
figure(5);
valores   = double(subs(cm2,m,-100:100))
   
subplot(2,1,1)
    plot(-100:100,abs(valores));
subplot(2,1,2)
    plot(-100:100,unwrap(angle(valores)));