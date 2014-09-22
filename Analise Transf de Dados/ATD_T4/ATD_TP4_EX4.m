% Igor Nelson Garrido da Cruz
% Gon�alo Silva Pereira

% ATD Trabalho Pr�tico N� 4

% Ex 4
% 4.1
[x,map]=imread('lenna.jpg','jpeg');
[C,S] = WAVEDEC2(x,2,'Haar');
figure(1)
subplot(2,1,1)
plot(C)
title('C')
subplot(2,1,2)
plot(S)
title('S')

% 4.2
x_r = WAVEREC2(C,S,'Haar');
figure(2)
IMSHOW(x_r,map);
title('Reconstru��o')
