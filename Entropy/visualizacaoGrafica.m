function visualizacaoGrafica( sinal,fs )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

subplot(2,1,1), plot(0:.5:2,-1,sinal);
title('Canal Esquerdo')
xlabel('Tempo (seg)');
ylabel('Amplitude [-1,1]')

subplot(2,1,2),plot(0:.5:2,-1,sinal);
title('Canal Direito')

xlabel('Tempo (seg)');
ylabel('Amplitude [-1,1]')


end

