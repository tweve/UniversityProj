function [np,nn] = contaPN (x)
    N = length(x);
    np = 0;
    nn = 0;
    
    for i = 1:N
        if x(i)>0
            np=np+1;
        elseif x(i)<0
            nn = nn+1;
        end
    end