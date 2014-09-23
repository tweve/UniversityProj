function [ data_norm ] = scalestd( data )
    
    tam = size(data.X);
    data_norm = data;
    
    for i=1:tam(1)
        media = sum(data.X(i,:))/tam(2);
        desvioPadrao = std(data.X(i,:));
        
        for j=1:1:tam(2)
            data_norm.X(i,j) = (data_norm.X(i,j)-media)/desvioPadrao;
        end
    end
    
end

