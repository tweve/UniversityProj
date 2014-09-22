% Igor Nelson Garrido da Cruz 
% Goncalo Silva Pereira


%1.1
%   Relatório

%1.2.1
b = [0 0 0 0.3137 0 -0.1537];
a = [1 -2.3 1.74 -0.432 0 0];

zeros = roots(b);
polos = roots(a);

zplane (zeros,polos);

%1.2.3 
%   Recorrendo a iztrans
syms Z;
Gz = (0.3137*Z^(-3) -0.1537*Z^(-5)) / (1 - 2.3*Z^(-1) +1.74*Z^(-2) -0.432*Z^(-3));
hn=iztrans(Gz);
pretty (hn);
%   Recorrendo a residue
bb = [0 0.3137 0 -0.1537];
aa = [1 -2.3 1.74 -0.432];

[r,p,k] = residuez (bb,aa);

hn2=iztrans((r(1)*Z^(-k))/(1-p(1)*Z^(-1)));


%1.2.4
h1n = double(subs(hn,0:70));
figure (2);
stairs(0:70,h1n)
hold on;
title('Respostas a inpulso');
xlabel('n'),ylabel('h[n]');

h1n = impz(b,a,71);
plot (h1n,'o');
h2n = dimpulse(b,a,71);
plot(h2n,'+');

%1.2.5
%   Relatório

%1.2.6
figure(3);
Yz = (1/ ( 1-Z^(-1) )) * ((0.3137*Z^(-3) -0.1537*Z^(-5)) / (1 - 2.3*Z^(-1) +1.74*Z^(-2) -0.432*Z^(-3)));
y = iztrans(Yz);            % transformada inversa
y1n = double(subs(y,0:70));
stairs(0:70,y1n);
hold on;

y2n = dstep(b,a,71);
plot (y2n,'o')

title('Respostas do sistema');
xlabel('n'),ylabel('y[n]');

%1.2.7 / 1.2.8

syms z;
syms n;

Hz = ((0.3137*z^-3) -(0.1537*z^-5)) / (1 -(2.3*z^-1) +(1.74*z^-2) -(0.432*z^-3));

func = '5*(heaviside(n-4)-heaviside(n-10))';
X = ztrans(sym(func));

y = iztrans(Hz*X)


% 1.2.8
figure (4);

y1n = double(subs(y,0:70));
stairs(0:70,y1n);

hold on;

x = double(subs(func,0:70));

y2n = filter(b,a,x);
plot (y2n,'o');

y3n = dlsim(b,a,x);
plot (y2n,'+');

title('Respostas do Sistema');
xlabel('n'),ylabel('h[n]');


% 1.2.9

x = linspace(-pi,pi,500);
figure (5);
[H,W] = freqz(b,a,x);

subplot(2,1,1)
    plot(x,abs(H));
    title('Amplitude');
subplot(2,1,2)
    plot(x,unwrap(angle(H)));
    title('Angulo');
% quando nao aplicados paramentros de saída a função freqz representa em
% gráfico a resposta do sistema em magnitude e em fase entre 0 e pi já
% fazendo unwrap automaticamente


%1.2.10

%recorrendo a ddcgain
ganhoestacionario=ddcgain(b,a)
%recorrendo ao Teorema do valor final
func = Yz * (1-Z^-1)
TVF = limit (func,1);



