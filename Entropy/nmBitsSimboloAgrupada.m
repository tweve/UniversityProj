function h = nmBitsSimboloAgrupada( C,P )
    
    h = 0;
    for i=1:length(C(:)),
        if (C(i)~= 0)  %O o valor esta presente no alfabeto
                h = h -(C(i)/(length(P(:))/2))*log2((C(i)/(length(P(:))/2)));  
        end
    end
    
end
