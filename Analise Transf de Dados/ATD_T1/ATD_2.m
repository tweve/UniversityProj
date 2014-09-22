% Igor Nelson Garrido da Cruz 2009111924
% Gonçalo Silva Pereira 2009111643

%2
%2.1
n = -54:50;                     %mostramos uma margem para os valores = 0

ind = find(n>= -40& n < 40);

xnn = zeros (size(n));

xnn (ind) = 1.5 *cos(0.025*pi*n(ind));
figure(1);

nn = -54:50;
n = nn(5:end);
n_2 = nn(3:end-2);

xn = xnn(5:end);
xn_1 = xnn (4:end-1);
xn_2 = xnn (3:end-2);
xn_3 = xnn (2:end-3);
xn_4 = xnn (1:end-4);

xn_2n = xnn (1:end).*2;
xn_2n_4 = xnn (1:end-4);


y1n = (0.4*xn_1) + (0.3*xn_3) + ((-0.4)*xn_4);
y2n = 2.4*xn_2n_4;
y3n = 2*xn_2.*xn_3;
y4n = n_2.*xn_3;

hold off;
plot (nn,xnn,'k');
hold on;
plot (n,y1n,'b:');
plot (n,y2n,'r:');
plot (n,y3n,'g:');
plot (n,y4n,'y:');

xlabel('t[s]');
ylabel('Preto-> x[n] cores -> Yn[n]');
title('As diferentes saídas dos sistemas perante a mesma entrada');

%2.2
figure (2);
ruido =rand(size(nn));
ruido = ruido * 0.4;
ruido = ruido - 0.2;

xrn  = xnn +ruido;
xrn_1 = xnn (4:end-1);
xrn_3 = xnn (2:end-3);
xrn_4 = xnn (1:end-4);

y1rn = (0.4*xrn_1) + (0.3*xrn_3) + ((-0.4)*xrn_4);
hold off;
plot (nn,xrn);
hold on;
plot (n,y1rn,'r');
xlabel('t[s]');
ylabel('Azul -> x[n] | Vermelho -> y1[n]');
title('Comparaçao x[n] -> y1[n] com ruído');

%2.3
    %Consultar Relatório
%2.4
    %Consultar Relatório
   
2.5
    n = -54:50;                     %mostramos uma margem para os valores = 0

ind = find(n>= -40& n < 40);

xnn = zeros (size(n));

xnn (ind) = 1.5 *cos(0.025*pi*n(ind));

nn = -54:50;
n = nn(5:end);

xn = xnn(5:end);
xn_1 = xnn (4:end-1);
%xn_2 = xnn (3:end-2);
xn_3 = xnn (2:end-3);
xn_4 = xnn (1:end-4);


y1n = (0.4*xn_1) + (0.3*xn_3) + ((-0.4)*xn_4);

figure (3);
hold off;
plot (nn,xnn,'k');
hold on;
plot (n,y1n,'r');

xlabel('t[s]');
ylabel('Preto-> x[n] Vermelho -> Y1[n]');
title('A Saída Y1[n]');


%2.6

y1=sym('0.4*charfcn[1](n)+0.3*charfcn[3](n)-0.4*charfcn[4](n)');
FuncaoTransf=ztrans(y1)

%2.7 - ????????????

dem = [1 0.4 0.3 -0.4];

for k=-10:1:10;
    
    dem = dem.*k;
    
    if(all(abs(roots(dem))<1)==1),
        
        display(k);
        
    end
        

end

    
