% Igor Nelson Garrido da Cruz
% Gonçalo Silva Pereira

function [x] = criaSinal(cm, tetam,m_max, t0, t)
w0 = 2*pi/t0;
x = zeros(size(t));

for m=0: m_max
       x= x+cm(m+1)*cos(m*w0*t + tetam(m+1));
end