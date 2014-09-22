% Igor Nelson Garrido da Cruz 
% Goncalo Silva Pereira


function [Cm,tetam]=SerieFourier(t,x,T0,m_max)
% t e x devem ser vectores coluna
A=zeros(length(t),2*m_max+2);
for k=0:m_max
A(:,k+1)=cos(2*pi/T0*t*k);
A(:,m_max+1+k+1)=-sin(2*pi/T0*t*k);
end
coef=pinv(A)*x;
a=coef(1:m_max+1);
b=coef(m_max+2:2*m_max+2);
[nl,nc]=size(a);
for lin=1:nl,
for col=1:nc,
if abs(a(lin,col))<0.001 & abs(b(lin,col))<0.001,
a(lin,col)=0; b(lin,col)=0;
end
end
end
Cm=abs(a+b*j); % ? Cm=(a.^2+b.^2).^0.5
tetam=angle(a+b*j); % ? tetam=atan(b./a)
end