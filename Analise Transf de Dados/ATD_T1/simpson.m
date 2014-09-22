% Igor Nelson Garrido da Cruz 2009111924
% Gonçalo Silva Pereira 2009111643

function [E] = simpson(f,n,h)

    E=0;
    
    for k=2:2:(n-1),
      E=E+(2*f(k));
    end
    
    for k=3:2:(n-2),
      E=E+(4*f(k));
    end
  
    E=(E+f(1)+f(n))*(h/3);

end
