% Igor Nelson Garrido da Cruz 2009111924
% Gon�alo Silva Pereira 2009111643

%Ex1

NGrupo = 3;

A1 = 2*mod(NGrupo,2);
A2 = 3*mod(1+NGrupo,2);
A3 = 5*mod(NGrupo,2);
A4 = 4*mod(1+NGrupo,2);

wa = mod (NGrupo,5) + 2;
wb = mod (NGrupo,7) + 7;
wc = mod (NGrupo,9) + 1;

ts = 0.1;
t = -pi:ts:pi;

%1
%1.1:
%x1t escrita na forma de somat�rio de cossenos:
x1t = cos(pi/2+(15*t)) - cos(pi/2+(5*t)) + 5/2*(cos(8*t)+1);

%1.2:
n = t/ts;
x1n= cos(pi/2+(1.5*n)) - cos(pi/2+(0.5*n)) + 5/2*(cos(0.8*n)+1);

%1.3:
%linha cont�nua x1(t)
t = linspace(-pi,pi,500);
xt = cos(pi/2+(15*t)) - cos(pi/2+(5*t)) + 5/2*(cos(8*t)+1);
plot (t,xt);
xlabel('t[s]');
ylabel('x(t)');
title('x[n] com ts = 0.1s e x(t) com 500 amostras')

%valores discretos

%ts = t(2)-t(1);
ts = 0.1;
t = -pi:ts:pi;
n = t/ts;
n = round(t(1)/ts):round(t(end)/ts);
x1n= cos(pi/2+(1.5*n)) - cos(pi/2+(0.5*n)) + 5/2*(cos(0.8*n)+1);
% display(n);
hold on;
plot(n*ts,x1n,'r*');

%1.4:

% C�lculo do valor exacto

syms taux;                                                                %vari�vel simb�lica
xx1t = cos(pi/2+(15*taux)) - cos(pi/2+(5*taux)) + 5/2*(cos(8*taux)+1);    %fun��o simb�lica
Energia = int (abs(xx1t)^2,-pi,pi)                                        %energia da fun��o, exacta
Energia = double(Energia);                                                %converte para valor

fprintf('Valor Aproximado: %f\n',Energia);


% C�lculo com recurso a fun��o trapz, (regra dos trap�zios)


ts2 = 2;
erro = 1;

startTime = tic();

while (erro >= 0.001 || erro < 0),
% 
%       solu��o antes de termos
%     criado a nossa implementa��o dos trapezios

%     ts2 = ts2/10;                         
%     t = -pi:ts2:pi;
%     xt = cos(pi/2+(15*t)) - cos(pi/2+(5*t)) + 5/2*(cos(8*t)+1);
%     
%     Estimativa = trapz(t,xt.^2);
%     erro = Energia-Estimativa;
%     
%     
    ts2 = ts2 + 2;
    
    h= (pi-(-pi)) / ts2;
    t = -pi:h:pi;

    xt = (cos(pi/2+(15*t)) - cos(pi/2+(5*t)) + 5/2*(cos(8*t)+1)).^2;
    Estimativa = trapezios(xt,ts2,h);  % criamos implementa�ao de regra dos trapezios
    
    erro = Energia-Estimativa;
    
end


elapsed = toc(startTime);
fprintf('Tempo de c�lculo da Regra dos Trapezios: %f\n',elapsed);
fprintf('Valor Aproximado: %f\n',Estimativa);
fprintf('Passo necess�rio: %f\n',h);
 
% C�lculo com recurso a Regra de Simpson

erro = 1;
ts =2;
startTime = tic();

while (erro >= 0.001 || erro < 0),
    
    ts = ts + 2;
   
    h= (pi-(-pi)) / ts;
    t = -pi:h:pi;

    xt = (cos(pi/2+(15*t)) - cos(pi/2+(5*t)) + 5/2*(cos(8*t)+1)).^2;
    Estimativa = simpson(xt,ts,h);  % criamos implementa�ao de regra simpson
    
    erro = Energia-Estimativa;

end
elapsed = toc(startTime);

fprintf('Tempo de c�lculo da Regra de Simpson: %f\n',elapsed);
fprintf('Valor Aproximado: %f\n',Estimativa);
fprintf('Passo necess�rio: %f\n',h);

%1.5
Energia_Discreta=0;

for i=1:length(x1n),
    Energia_Discreta =Energia_Discreta + abs(x1n(i))^2;
end

% Como o passo � de 0.1 em 0.1, e aplicamos o n = t/ts, temos de
% multiplicar por Ts para anular.

Energia_Discreta = Energia_Discreta * 0.1;
fprintf('Energia Calculada a partir de x1[n]: %f\n',Energia_Discreta);
