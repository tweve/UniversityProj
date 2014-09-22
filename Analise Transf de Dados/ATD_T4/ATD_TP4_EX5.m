% Igor Nelson Garrido da Cruz
% Gonçalo Silva Pereira

% ATD Trabalho Prático Nº 4

% Ex 5
% 5.1
figure(1);
[X,map] = imread('lenna.jpg');
colormap(gray(256));
title('Original')
image(X);

% 5.2
figure(2)
colormap(gray(256));
image(dct2(X));
title('Transformada Discreta de Co-seno')

%5.3
figure(3)
colormap(gray(256));
fun = @dct2;
blocos = blkproc(X,[8 8], fun);
image(blocos)
title('DCT - blocos de 8x8')

% % 5.4
figure(4);
colormap(gray(256));

dctblocos = dctmtx(8);
mask = zeros(8);

mask(1,1)=1;
img = blkproc(blocos,[8 8],'P1*(x.*P2)*P3',dctblocos.',mask,dctblocos);
image(img)
title('1 coeficiente')

% ex_5_4
% 1 coeficiente


%%%%%
figure(5);
colormap(gray(256));

mask(1,1)=1;mask(1,2)=1;mask(2,1)=1;mask(2,2)=1;mask(3,1)=1;

img = blkproc(blocos,[8 8],'P1*(x.*P2)*P3',dctblocos.',mask,dctblocos);
image(img)
title('5 coeficientes')

%%%%%
figure(6);
colormap(gray(256));

mask(1,1:4)=1;
mask(2,1:3)=1;
mask(3,1:2)=1;
mask(4,1:1)=1;

img = blkproc(blocos,[8 8],'P1*(x.*P2)*P3',dctblocos.',mask,dctblocos);
image(img)
title('10 coeficientes')

%%%%%
figure(7);
colormap(gray(256));

mask(1,1:6)=1;
mask(2,1:5)=1;
mask(3,1:4)=1;
mask(4,1:3)=1;
mask(5,1:2)=1;
mask(6,1:1)=1;

img = blkproc(blocos,[8 8],'P1*(x.*P2)*P3',dctblocos.',mask,dctblocos);
image(img)
title('20 coeficientes')

%%%%%
figure(7);
colormap(gray(256));

mask = ones(8)

img = blkproc(blocos,[8 8],'P1*(x.*P2)*P3',dctblocos.',mask,dctblocos);
image(img)
title('Todos os coeficientes')
