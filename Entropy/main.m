%   >TEORIA DA INFORMAÇÃO
%   ////////////////////////////////////////////////

%   Carlos Manuel Fernades Dos Santos   - 2009108991  
%   Igor Nelson Garrido da Cruz         - 2009111924

%   ------------------------------------------------

disp('Numero médio de bits:  ');
P = imread('kid.bmp');
m = Procura(0:255,P);
t = sprintf('Normal kid.bmp : %.2f bits/pixel ',nmBitsSimbolo(m,P));
disp(t);
d = m./length(P(:));
f = hufflen(m).*d;
t = sprintf('Huffman kid.bmp : %.2f bits/pixel ', sum(f) );
disp(t);
m = ProcuraOptimizada(0:255,P);
agrup = nmBitsSimboloAgrupada(m,P);
t = sprintf('Agrupado kid.bmp : %.2f bits/2pixel = %.2f bits/pixel\n ',agrup,agrup/2);
disp (t);

P = imread('homer.bmp');
m = Procura(0:255,P);
t = sprintf('homer.bmp : %.2f ',nmBitsSimbolo(Procura(0:255,P),P));
disp(t);
d = m./length(P(:));
f = hufflen(m).*d;
t = sprintf('Huffman homer.bmp : %.2f ',sum(f));
disp(t);
m = ProcuraOptimizada(0:255,P);
agrup = nmBitsSimboloAgrupada(m,P);
t = sprintf('Agrupado homer.bmp : %.2f bits/2pixel = %.2f bits/pixel\n ',agrup,agrup/2);
disp (t);

P = imread('homerbin.bmp');
m = Procura(0:255,P);
t = sprintf('homerbin.bmp : %.2f ',nmBitsSimbolo(m,P));
disp(t);
d = m./length(P(:));
f = hufflen(m).*d;
t = sprintf('Huffman homerbin.bmp : %.2f ',sum(f));
disp(t);
m = ProcuraOptimizada(0:255,P);
agrup = nmBitsSimboloAgrupada(m,P);
t = sprintf('Agrupado homerbin.bmp : %.2f bits/2pixel = %.2f bits/pixel\n ',agrup,agrup/2);
disp (t);

[Y, fs, nbits] = wavread('guitarsolo.wav');
m =Procura(-1:2/2^nbits:1,Y);
t = sprintf('guitarsolo.wav : %.2f ',nmBitsSimbolo(m,Y));
disp(t);
d = m./length(Y(:));
f = hufflen(m).*d;
t = sprintf('Huffman guitarsolo.wav : %.2f ',sum(f));
disp(t);
m = ProcuraOptimizada(-1:2/2^nbits:1,Y);
agrup = nmBitsSimboloAgrupada(m,Y);
t = sprintf('Agrupado guitarsolo.wav : %.2f bits/2pixel = %.2f bits/pixel\n ',agrup,agrup/2);
disp (t);


fic = fopen('english.txt');
P = fscanf(fic,'%c');
fclose(fic);
P = double(P);
m =Procura([65:90 97:122],P);
t = sprintf('english.txt : %.2f ',nmBitsSimbolo(m,P));
disp(t);
d = m./length(P(:));
f = hufflen(m).*d;
t = sprintf('Huffman english.txt : %.2f ',sum(f));
disp(t);
m = ProcuraOptimizada([65:90 97:122],P);
agrup = nmBitsSimboloAgrupada(m,P);
t = sprintf('Agrupado english.txt : %.2f bits/2pixel = %.2f bits/pixel\n ',agrup,agrup/2);
disp (t);
