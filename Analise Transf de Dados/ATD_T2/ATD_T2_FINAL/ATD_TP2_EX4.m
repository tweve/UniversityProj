% Igor Nelson Garrido da Cruz 
% Goncalo Silva Pereira


% Exercício 4

syms t m w;

sinal = 2*exp(-0.7*t) * sin(4*pi*t);

xt=sym('2*exp(-0.7*t)*(heaviside(t)-heaviside(t-6))/(2*pi)');
Xw=fourier(xt);
cm=int(2*exp(-0.7*t)*exp(-i*m*1*t),0,6)/(2*pi);

cm = int(sinal * exp(-i*w*t), t, 0,6);

for k=-11:11;
    
   c_Xw=double(subs(Xw,k)) 
   c=double(subs(cm,k)) 

end

% comparação gráfica

w=linspace(-11,11,500);

X=subs(Xw);

m=-11:11;

c=subs(cm);

subplot(211);

plot(w,abs(X),m*1,abs(c),'o');

xlabel('w e m*w0 [rad/s]');

title('Comparação entre |X(m*w0)/T0| e |cm| com m entre -11 e 11');

grid

subplot(212);

plot(w,unwrap(angle(X)),m*1,unwrap(angle(c)),'o');

xlabel('w e m*w0 [rad/s]');

ylabel('[rad]');

title('Comparação entre <X(m*w0)/T0 e <cm com m entre -11 e 11');

grid