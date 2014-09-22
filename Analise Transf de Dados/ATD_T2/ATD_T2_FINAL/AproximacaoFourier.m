% Igor Nelson Garrido da Cruz 
% Goncalo Silva Pereira


function [ x ] = AproximacaoFourier( cm,tetam,maximo,t0,t )

frequenciafundamental = 2*pi / t0;
x = zeros (size(t));

for m=0 : maximo
    x = x + cm(m+1)*cos(m*frequenciafundamental*t + tetam(m+1));
    
end

