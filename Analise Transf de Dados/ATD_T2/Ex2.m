%2.1.1
T0 = input('Valor de T0:');

%2.1.2
t = linspace(0,T0,500);

%2.1.3
escolha = menu ('Escolha um sinal:','Onda quadrada periodica.','Onda dente de serra.','Introduzir expressão.');

if (escolha == 1),
    % onda quadrada
    amplitude = 1;
    xt = amplitude*square(2*pi*t);
    plot(t,xt);
    axis([0 T0 -1.5 1.5]);
    % x(t) = '2*sin(3*t)';
elseif(escolha == 2),
    % dente de serra
    xt = t - floor(t);
    plot(xt);
else (escolha == 3),
    xt = input('x(t)=');
end

% x = eval(xt);

m_max = input('Valor de m_max:');

[CM,Tetam] = SerieFourier(t',xt',T0,m_max);

plot(CM,Tetam);