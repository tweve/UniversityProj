% 
% % Exerc�cio 4

syms m t w;
A = 2;
a = 0.7;
xt = sym('2*exp(-0.7*t)*t*(heaviside(t)-heaviside(t-6))/(6)');
Xw=fourier(xt);
cm=int(2*exp(-0.7*t)*exp(-i*m*1*t),0,2*pi)/(2*pi);

for k=-5:5;
   c_Xw=double(subs(Xw,k*1)) 
   c=double(subs(cm,k)) 
end

% compara��o gr�fica
w=linspace(-25,25,500);
X=subs(Xw);
m=-20:20;
c=subs(cm);
subplot(211);
plot(w,abs(X),m*1,abs(c),'o');
xlabel('w e m*w0 [rad/s]');
title('Compara��o entre |X(m*w0)/T0| e |cm| com m entre -20 e 20');
grid
subplot(212);
plot(w,unwrap(angle(X)),m*1,unwrap(angle(c)),'o');
xlabel('w e m*w0 [rad/s]');
ylabel('[rad]');
title('Compara��o entre <X(m*w0)/T0 e <cm com m entre -20 e 20');
grid