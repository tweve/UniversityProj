% Igor Nelson Garrido da Cruz
% Gon�alo Silva Pereira

% ATD Trabalho Pr�tico N� 4

% Ex 1
% 1.1

    [x, fs, bits] = wavread('saxriff.wav');
    wavplay(x,fs);
    figure(1)
    plot(abs(x));
    xlabel('t'), ylabel('Magnitude'); 
    title('Magnitude do Espectro do Sinal');
    
% 1.2
    N = length(x);
    tjanela = 46.44/1000;
    tsobrepos = 5.8/1000;
    length_janela = round(tjanela * fs);
    length_sobrep = round(tsobrepos * fs);
    N1 = length_janela;
    f = linspace (-fs/2, (fs/2)-(fs/N1), N1);

    freq_fundamental = [];
    magnitude=[];
    indices_maiores_100 = find(f >= 2*pi*100);
    
    
    for (i = 1:length_janela- length_sobrep: length(x)- length_janela),

        x_janela = fftshift (fft(x(i:i+length_janela-1).* hamming(N1), N1));
        maximo = max(abs(x_janela(indices_maiores_100)));
        magnitude= [magnitude maximo];
        ind = find(abs(x_janela(indices_maiores_100))== maximo, 1, 'last');
        freq_fundamental = [freq_fundamental f(indices_maiores_100(ind))/(2*pi)];
        
    end

    figure(2);
    plot(freq_fundamental );
    xlabel('Tempo');
    ylabel('Frequ�ncias');
    title('Frequencias Fundamentais');
    
    
    % 1.4
    Njanela = 46.44/1000/(1/fs);
    sinal_reconstruido=[];
    
  for (i = 1:length(freq_fundamental)),

        t = 0.04064 * (i-1) : 1/fs : 0.04064*i -1/fs;
        sinal_reconstruido=[sinal_reconstruido (magnitude(i)*sin(2*pi*freq_fundamental(i)*t))];

    end
    figure(3);
    plot(sinal_reconstruido)
    title('Sinal Reconstruido');
    wavwrite(sinal_reconstruido', fs, bits, 'reconstruido.wav');
    [x1, fs1, bits1] = wavread('reconstruido.wav');
    wavplay(x1,fs1);

    

% 1.3
mediana = 5;
X = abs(x);
sinal = X;
for (i=1 :length(x)-mediana),
    valor_mediana = median(X(i : (i + mediana-1)));

    sinal(i:mediana) = valor_mediana;
    i = i+mediana;
end
figure(4)
plot(sinal);
title('Mediana do Sinal');
wavplay(sinal,fs);
freq_fundamental = [];
magnitude = [];

  for (i = 1:length_janela- length_sobrep: length(sinal)- length_janela),

        x_janela = fftshift (fft(sinal(i:i+length_janela-1).* hamming(N1), N1));
        maximo = max(abs(x_janela(indices_maiores_100)));
        magnitude= [magnitude maximo];
        ind = find(abs(x_janela(indices_maiores_100))== maximo, 1, 'last');
        freq_fundamental = [freq_fundamental f(indices_maiores_100(ind))/(2*pi)];
        
    end

    figure(5);
    plot(freq_fundamental );
    xlabel('Tempo');
    ylabel('Frequ�ncias');
    title('Frequencias Fundamentais Mediana 5');
    
        % 1.4
    Njanela = 46.44/1000/(1/fs);
    sinal_reconstruido=[];
    
  for (i = 1:length(freq_fundamental)),

        t = 0.04064 * (i-1) : 1/fs : 0.04064*i -1/fs;
        sinal_reconstruido=[sinal_reconstruido (magnitude(i)*sin(2*pi*freq_fundamental(i)*t))];

    end
    figure(6);
    plot(sinal_reconstruido)
    title('Sinal Reconstruido Mediana 5');
    wavwrite(sinal_reconstruido', fs, bits, 'reconstruidoMediana5.wav');
    [x1, fs1, bits1] = wavread('reconstruidoMediana5.wav');
    wavplay(x1,fs1);

    
    
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
mediana = 7;
X = abs(x);
sinal = X;
for (i=1 :length(x)-mediana),
    valor_mediana = median(X(i : (i + mediana-1)));

    sinal(i:mediana) = valor_mediana;
    i = i+mediana;
end
figure(7)
plot(sinal);
title('Mediana do Sinal');
wavplay(sinal,fs);
freq_fundamental = [];
magnitude = [];

  for (i = 1:length_janela- length_sobrep: length(sinal)- length_janela),

        x_janela = fftshift (fft(sinal(i:i+length_janela-1).* hamming(N1), N1));
        maximo = max(abs(x_janela(indices_maiores_100)));
        magnitude= [magnitude maximo];
        ind = find(abs(x_janela(indices_maiores_100))== maximo, 1, 'last');
        freq_fundamental = [freq_fundamental f(indices_maiores_100(ind))/(2*pi)];
        
    end

    figure(8);
    plot(freq_fundamental );
    xlabel('Tempo');
    ylabel('Frequ�ncias');
    title('Frequencias Fundamentais Mediana 7');
    
    % 1.4
    Njanela = 46.44/1000/(1/fs);
    sinal_reconstruido=[];
    
  for (i = 1:length(freq_fundamental)),

        t = 0.04064 * (i-1) : 1/fs : 0.04064*i -1/fs;
        sinal_reconstruido=[sinal_reconstruido (magnitude(i)*sin(2*pi*freq_fundamental(i)*t))];

    end
    figure(9);
    plot(sinal_reconstruido)
    title('Sinal Reconstruido Mediana 7');
    wavwrite(sinal_reconstruido', fs, bits, 'reconstruidoMediana7.wav');
    [x1, fs1, bits1] = wavread('reconstruidoMediana7.wav');
    wavplay(x1,fs1);

    
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
mediana = 9;
X = abs(x);
sinal = X;
for (i=1 :length(x)-mediana),
    valor_mediana = median(X(i : (i + mediana-1)));

    sinal(i:mediana) = valor_mediana;
    i = i+mediana;
end
figure(10)
plot(sinal);
title('Mediana do Sinal');
wavplay(sinal,fs);
freq_fundamental = [];
magnitude = [];

  for (i = 1:length_janela- length_sobrep: length(sinal)- length_janela),

        x_janela = fftshift (fft(sinal(i:i+length_janela-1).* hamming(N1), N1));
        maximo = max(abs(x_janela(indices_maiores_100)));
        magnitude= [magnitude maximo];
        ind = find(abs(x_janela(indices_maiores_100))== maximo, 1, 'last');
        freq_fundamental = [freq_fundamental f(indices_maiores_100(ind))/(2*pi)];
        
    end

    figure(11);
    plot(freq_fundamental );
    xlabel('Tempo');
    ylabel('Frequ�ncias');
    title('Frequencias Fundamentais Mediana 9');
    
    % 1.4
    Njanela = 46.44/1000/(1/fs);
    sinal_reconstruido=[];
    
  for (i = 1:length(freq_fundamental)),

        t = 0.04064 * (i-1) : 1/fs : 0.04064*i -1/fs;
        sinal_reconstruido=[sinal_reconstruido (magnitude(i)*sin(2*pi*freq_fundamental(i)*t))];

    end
    figure(12);
    plot(sinal_reconstruido)
    title('Sinal Reconstruido Mediana 9');
    wavwrite(sinal_reconstruido', fs, bits, 'reconstruidoMediana9.wav');
    [x1, fs1, bits1] = wavread('reconstruidoMediana9.wav');
    wavplay(x1,fs1);
    
% 1.5
    % Os sinais Sintetizados perdem muita qualidade, consegue-se perceber alguns tra�os do sinal original, por�m a qualidade � muito m�.
    