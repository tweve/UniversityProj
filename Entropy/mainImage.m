Y = imread('C:\Users\Tweve\Desktop\Aulas\TI\image008.jpg');
Y(:,:,1)= Y(:,:,1)*2;
Y = min(Y,255);
mosaico(Y,11);