% Igor Nelson Garrido da Cruz
% Gon�alo Silva Pereira

% ATD Trabalho Pr�tico N� 4

% Ex 2
% 2.1

    [x, fs, bits] = wavread('escala.wav');
    wavplay(x,fs);
    figure(1)
    plot(abs(x));
    xlabel('t'), ylabel('Magnitude'); 
    title('Magnitude do Espectro do Sinal');
    
% 2.2

    tjanela = 46.44/1000;
    tsobrepos = 5.8/1000;

    length_janela = round(tjanela * fs);
    length_sobrep = round(tsobrepos * fs);
    janelaHamming = hamming(length_janela);

    w = 0:2*pi*fs/(100*length_janela):pi*fs;
    maiores_100 = find(w >= 2*pi*100);

    freq_fundamental = [];
    maximos = [];
    notas = [];


    for (i=1 : length_janela-length_sobrep : length(x)-length_janela)

        x_janela = fft((janelaHamming.*x(i : i+length_janela-1)), 100 * length_janela);
        maximo = max(abs(x_janela(maiores_100)));
        indice = find(abs(x_janela(maiores_100))==maximo, 1, 'first');
        freq_fundamental = [freq_fundamental round(w(maiores_100(indice))/(2*pi))];

         if(freq_fundamental(end) > 255  && freq_fundamental(end) < 267.5)
           notas = [notas  'D� '];
         elseif(freq_fundamental(end) > 267.5  && freq_fundamental(end) < 285.5 )
           notas = [notas  '#D� '];
         elseif(freq_fundamental(end) > 285.5  && freq_fundamental(end) < 305.5)
           notas = [notas  'R� '];
         elseif(freq_fundamental(end) > 285.5  && freq_fundamental(end) < 320)
           notas = [notas  '#R� '];
         elseif(freq_fundamental(end) > 320  && freq_fundamental(end) < 338)
           notas = [notas  'Mi '];
         elseif(freq_fundamental(end) > 338  && freq_fundamental(end) < 360)
           notas = [notas  'F� '];
         elseif(freq_fundamental(end) > 360 && freq_fundamental(end) < 380)
           notas = [notas  '#F� '];
         elseif(freq_fundamental(end) > 380 && freq_fundamental(end) < 400)
           notas = [notas  'Sol '];
         elseif(freq_fundamental(end) > 400  && freq_fundamental(end) < 430)
           notas = [notas  '#Sol '];  
         elseif(freq_fundamental(end) > 430 && freq_fundamental(end) < 450)
           notas = [notas  'L� '];
         elseif(freq_fundamental(end) > 450  && freq_fundamental(end) < 470)
           notas = [notas  '#L� '];  
         elseif(freq_fundamental(end) >470  && freq_fundamental(end) < 510)
           notas = [notas  'Si '];  
         end

     end

     notas
     
% 2.3

    [x, fs, bits] = wavread('piano.wav');
    wavplay(x,fs);
    figure(2)
    plot(abs(x));
    xlabel('t'), ylabel('Magnitude'); 
    title('Magnitude do Espectro do Sinal piano.wav');
    
    tjanela = 46.44/1000;
    tsobrepos = 5.8/1000;

    length_janela = round(tjanela * fs);
    length_sobrep = round(tsobrepos * fs);
    janelaHamming = hamming(length_janela);

    w = 0:2*pi*fs/(100*length_janela):pi*fs;
    maiores_100 = find(w >= 2*pi*100);

    freq_fundamental = [];
    maximos = [];
    notas = [];

% 2.4

    for (i=1 : length_janela-length_sobrep : length(x)-length_janela)

        x_janela = fft((janelaHamming.*x(i : i+length_janela-1)), 100 * length_janela);
        maximo = max(abs(x_janela(maiores_100)));
        indice = find(abs(x_janela(maiores_100))==maximo, 1, 'first');
        freq_fundamental = [freq_fundamental round(w(maiores_100(indice))/(2*pi))];

         if(freq_fundamental(end) > 255  && freq_fundamental(end) < 267.5)
           notas = [notas  'D� '];
         elseif(freq_fundamental(end) > 267.5  && freq_fundamental(end) < 285.5 )
           notas = [notas  '#D� '];
         elseif(freq_fundamental(end) > 285.5  && freq_fundamental(end) < 305.5)
           notas = [notas  'R� '];
         elseif(freq_fundamental(end) > 285.5  && freq_fundamental(end) < 320)
           notas = [notas  '#R� '];
         elseif(freq_fundamental(end) > 320  && freq_fundamental(end) < 338)
           notas = [notas  'Mi '];
         elseif(freq_fundamental(end) > 338  && freq_fundamental(end) < 360)
           notas = [notas  'F� '];
         elseif(freq_fundamental(end) > 360 && freq_fundamental(end) < 380)
           notas = [notas  '#F� '];
         elseif(freq_fundamental(end) > 380 && freq_fundamental(end) < 400)
           notas = [notas  'Sol '];
         elseif(freq_fundamental(end) > 400  && freq_fundamental(end) < 430)
           notas = [notas  '#Sol '];  
         elseif(freq_fundamental(end) > 430 && freq_fundamental(end) < 450)
           notas = [notas  'L� '];
         elseif(freq_fundamental(end) > 450  && freq_fundamental(end) < 470)
           notas = [notas  '#L� '];  
         elseif(freq_fundamental(end) >470  && freq_fundamental(end) < 510)
           notas = [notas  'Si '];  
         end

     end

     notas
    
    [x, fs, bits] = wavread('flauta.wav');
    wavplay(x,fs);
    figure(3)
    x = x(:,1);
    plot(abs(x));
    xlabel('t'), ylabel('Magnitude'); 
    title('Magnitude do Espectro do Sinal flauta.wav');
    
% 2.4

    
    tjanela = 20.44/1000;
    tsobrepos = 5.8/1000;

    length_janela = round(tjanela * fs);
    length_sobrep = round(tsobrepos * fs);
    janelaHamming = hamming(length_janela);

    w = 0:2*pi*fs/(100*length_janela):pi*fs;
    maiores_100 = find(w >= 2*pi*100);

    freq_fundamental = [];
    maximos = [];
    notas = [];

% 2.4

    for (i=1 : length_janela-length_sobrep : length(x)-length_janela)

        x_janela = fft((janelaHamming.*x(i : i+length_janela-1)), 100 * length_janela);
        maximo = max(abs(x_janela(maiores_100)));
        indice = find(abs(x_janela(maiores_100))==maximo, 1, 'first');
        freq_fundamental = [freq_fundamental round(w(maiores_100(indice))/(2*pi))];

         if(freq_fundamental(end) > 255  && freq_fundamental(end) < 267.5)
           notas = [notas  'D� '];
         elseif(freq_fundamental(end) > 267.5  && freq_fundamental(end) < 285.5 )
           notas = [notas  '#D� '];
         elseif(freq_fundamental(end) > 285.5  && freq_fundamental(end) < 305.5)
           notas = [notas  'R� '];
         elseif(freq_fundamental(end) > 285.5  && freq_fundamental(end) < 320)
           notas = [notas  '#R� '];
         elseif(freq_fundamental(end) > 320  && freq_fundamental(end) < 338)
           notas = [notas  'Mi '];
         elseif(freq_fundamental(end) > 338  && freq_fundamental(end) < 360)
           notas = [notas  'F� '];
         elseif(freq_fundamental(end) > 360 && freq_fundamental(end) < 380)
           notas = [notas  '#F� '];
         elseif(freq_fundamental(end) > 380 && freq_fundamental(end) < 400)
           notas = [notas  'Sol '];
         elseif(freq_fundamental(end) > 400  && freq_fundamental(end) < 430)
           notas = [notas  '#Sol '];  
         elseif(freq_fundamental(end) > 430 && freq_fundamental(end) < 450)
           notas = [notas  'L� '];
         elseif(freq_fundamental(end) > 450  && freq_fundamental(end) < 470)
           notas = [notas  '#L� '];  
         elseif(freq_fundamental(end) >470  && freq_fundamental(end) < 510)
           notas = [notas  'Si '];  
         end

     end

     notas
    
 % 2.5 
    % Relat�rio
    