% Igor Nelson Garrido da Cruz
% Gon�alo Silva Pereira

% Ex 3

% 3.1
[image,map]= imread('lena.bmp');

% 3.2
figure(1);
imshow(image,map);

% 3.3
x_image = fftshift(fft2(image));

[Nl,Nc] = size(x_image);

if (mod(Nl,2)== 0),
    eixox = -Nl/2 : Nl/2 -1;
else
    eixox = fix(-Nl/2):fix(Nl/2);
end

if (mod(Nc,2)== 0),
    eixoy = -Nc/2 : Nc/2 -1;
else
    eixoy = fix(-Nc/2):fix(Nc/2);
end

figure(2)
mesh (eixox,eixoy,20*log10(abs(x_image)));

view([-37.5 40]);

% 3.4
mask = zeros(size(x_image));                            % Cria m�scara
filtro = menu ('Filtro','PassaBaixo','PassaAlto');      % Escolha do Filtro
fc = input('Frequ�ncia de Corte = ');                   % F�rmula do Filtro

cx = find (eixox == 0);
cy = find (eixoy == 0);

for l = 1:Nl,
    for c = 1:Nc,
        if ( (l-cx)^2 + (c-cy)^2 <= fc^2 ),             % Se l,c est� dentro da fc
            mask(l,c)=1;
        end
    end
end

coef = 1;

if(filtro == 2)                                         % Por defeito filtro = 1, quando se escolhe o 2...
    mask = ones(size(mask))-mask;
    coef = 10;
end

% 3.5
figure(3)
mesh(eixox,eixoy,20*log10(abs(mask)));                                         % magnitude do filtro
title('Magnitude do filtro');

% 3.6
x_image_filt = x_image.*mask;
mesh (eixox,eixoy,20*log10(abs(x_image_filt)+1));       % +1 : Forma f�cil de contornar o erro do log (0), e n�o influencia muito os resultados finais

% 3.7
image_filt = ifft2(ifftshift(x_image_filt));

% 3.8
figure(4)
imshow(real(image_filt)*coef,map);

% 3.9

    %Na pr�tica, ao aplicar um filtro passa alto os contornos da imagem s�o
    %destacados e ao aplicar um filtro passa baixo os limites s�o
    %atenuados.