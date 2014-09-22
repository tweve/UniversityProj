function h = nmBitsSimbolo( C,P )
    h = 0;
    t = size(C);
    
    for i=1:t(2),
        if (C(1,i)~= 0)  %O o valor nao esta presente no alfabeto
                h = h -(C(1,i)/length(P(:)))*log2((C(1,i)/length(P(:))));  
        end
    end
    
end

