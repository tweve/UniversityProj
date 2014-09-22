% Igor Nelson Garrido da Cruz 2009111924
% Gonçalo Silva Pereira 2009111643

function [E] = trapezios(f,n,h)

    E=0;

    for k=2:n-1,
    
        E=E+(2*f(k));
    end

    E=(E+ f(1)+ f(n))*h/2;

end
