% ATD - Trabalho Pr�tico N� 3
% Igor Nelson Garrido da Cruz
% Gon�alo Silva Pereira

% Ex. 2
% 2.1
[x,fs] = wavread('saxriff.wav');
wavplay(x,fs);                      %fs = 22500Hz
display(length(x));
t = 0 : 1/fs: (length(x)-1)/fs;     %length(x)= 55125

plot(t,x);

x = fftshift(fft(x,2^16));          %N = 66536

N = 2^16;

ws = 2*pi*fs;

if mod(N,2)== 0,
    w = linspace(-ws/2,ws/2-ws/N,N);
else
    w = linspace(-ws/2+ws/N/2,ws/2-ws/N/2,N);
end

figure(2);

plot(w,abs(x));

% 2.2

x_abs_max = max (abs(x));               % maximo do sinal original

ind_max = find(abs(x)== x_abs_max);     % indice do m�ximo do sinal original

f_x_abs_max = w(ind_max(2))/2/pi;       % Hz, Segundo �ndice dos m�ximos, no sinal representado em frequ�ncias

2.3

% No dom�nio complexo a aleatoriedade � diferente, por isso geramos no real
% e transformamos em complexo

fr1 = 8500;
fr2 = 9500;

ind_pos = find (w>= 2*pi*fr1  & w<= 2*pi*fr2);
ind_neg = find (w>= -2*pi*fr2 & w<= -2*pi*fr1);

Xr_abs = 0.1 * x_abs_max * rand(size(ind_pos));     % Gera valores absolutos aleat�rios.
Xr_angle = 2*pi*rand(size(ind_pos))-pi;             % Gera valores de angulos aleat�rios
Xr_pos = Xr_abs.*(cos(Xr_angle)+1j*sin(Xr_angle));   % Partindo dos valores absolutos e angulos gera o ru�do final
Xr_neg = conj (Xr_pos);                             % Conjugado

Xr = x;

Xr (ind_pos)= x(ind_pos) + Xr_pos';
Xr (ind_neg)= x(ind_neg) + fliplr(Xr_neg');        % Inverter

figure(3)
plot(w,abs(Xr));

% 2.4 - Obter o Sinal com ru�do no dom�nio temporal

Xr = real (ifft(ifftshift(Xr)));
wavplay(Xr,fs);

% � poss�vel ouvir o ru�do inserido nas altas frequ�ncias a afectar a
% qualidade do som, no entanto esse ru�do n�o est� nas frequencias base do
% sinal orginal, pelo que poder� ser retirado e obtido novamento o sinal
% "limpo"

%2.5

fc = 8000;                              % Freq de corte

%   fc -> [0] - [fs/2] <-
%   wn -> [0] - [1]    <-
%
wn = 1/(fs/2/fc);
[b,a]= butter(6, wn)                    % Coeficientes numerador ,denominador             

figure(4);

freqz(b,a);

x_filtrado = filter(b,a,Xr);
wavplay(x_filtrado);

x_filtrado = fftshift(fft(x_filtrado));

figure(5);
plot(w,abs(x_filtrado));

% 2.6

% No dom�nio complexo a aleatoriedade � diferente, por isso geramos no real
% e transformamos em complexo

fr1 = 2000;
fr2 = 3000;

ind_pos = find (w>= 2*pi*fr1  & w<= 2*pi*fr2);
ind_neg = find (w>= -2*pi*fr2 & w<= -2*pi*fr1);

Xr_abs = 0.1 * x_abs_max * rand(size(ind_pos));     % Gera valores absolutos aleat�rios.
Xr_angle = 2*pi*rand(size(ind_pos))-pi;             % Gera valores de angulos aleat�rios
Xr_pos = Xr_abs.*(cos(Xr_angle)+1j*sin(Xr_angle));   % Partindo dos valores absolutos e angulos gera o ru�do final
Xr_neg = conj (Xr_pos);                             % Conjugado

Xr = x;

Xr (ind_pos)= x(ind_pos) + Xr_pos';
Xr (ind_neg)= x(ind_neg) + fliplr(Xr_neg');        % Inverter

figure(6)
plot(w,abs(Xr));

% 2.4 - Obter o Sinal com ru�do no dom�nio temporal

Xr = real (ifft(ifftshift(Xr)));
wavplay(Xr,fs);

% � poss�vel ouvir o ru�do inserido nas altas frequ�ncias a afectar a
% qualidade do som, no entanto esse ru�do n�o est� nas frequencias base do
% sinal orginal, pelo que poder� ser retirado e obtido novamento o sinal
% "limpo"

%2.5

fc = 2000;                              % Freq de corte

%   fc -> [0] - [fs/2] <-
%   wn -> [0] - [1]    <-
%
wn = 1/(fs/2/fc);
[b,a]= butter(6, wn)                    % Coeficientes numerador ,denominador             

figure(7);

freqz(b,a);

x_filtrado = filter(b,a,Xr);
wavplay(x_filtrado);

x_filtrado = fftshift(fft(x_filtrado));

figure(8);
plot(w,abs(x_filtrado));

figure(9)
zplane(roots(a),roots(b));
