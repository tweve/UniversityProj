% Igor Nelson Garrido da Cruz
% Gon�alo Silva Pereira

% ATD Trabalho Pr�tico N� 4

% Ex 3

% 3.1
load sinal.mat
figure(1)
plot(sumsin_freqbrk)
title('Sinal.mat')

% 3.2
[CA,CD] = DWT(sumsin_freqbrk,'Haar');
figure(2);
subplot(2,1,1)
plot(CA);
title('Aproxima�ao')
subplot(2,1,2)
plot(CD);
title('Detalhe')


% 3.3
sinalReconstruido = idwt(CA,CD,'Haar');
figure(3)
plot(sinalReconstruido);
title('Sinal Reconstru�do');


% 3.4
[C1,L1] = wavedec(sumsin_freqbrk,4,'db3');
figure(4);
subplot(2,1,1)
plot(C1);
title('4 Niveis - 3� Daubechies');
subplot(2,1,2)
plot(L1);

% 3.5
[C2,L2] = wavedec(sumsin_freqbrk,4,'sym2');
figure(5);
subplot(2,1,1)
plot(C2);
title('4 Niveis - 2� Symlet')
subplot(2,1,2) 
plot(L2);

% 3.6
reconstruidoDau = WAVEREC(C1,L1,'db3');
reconstruidoSym = WAVEREC(C2,L2,'sym2');

figure(6);
hold on
plot(reconstruidoDau,'r');
plot(reconstruidoSym,'g.');

title('Terceira wavelet de Daubechies - Vermelho');
title('Segunda wavelet de Symlet - Verde');

