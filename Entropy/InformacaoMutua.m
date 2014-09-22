function inf = InformacaoMutua( query,target,A,passo )
    
    inf =0;
    tmnh = length(query);  %entropia da target
    ocorrenciasquery = Procura(A,query);
    probabilidadequery = ocorrenciasquery./tmnh;
    querylength = length(query);
    pos = 1;
    
    
    for i = 1:passo:length(target)-length(query)+1,
        window = target(i:+i+tmnh-1); %entropia da janela
        ocorrenciaswindow = Procura(A,window);
        probabilidadewindow = ocorrenciaswindow./tmnh;
       
        % probabilidades conjuntas:
        probabilidadequery_window = zeros(length(A),length(A));
        
        for i=1:tmnh
            posx = find( A == query(i) );
            posy = find( A == window(i) );
            probabilidadequery_window(posx,posy) = probabilidadequery_window(posx,posy)+1/querylength; 
        end
        
        total=0;
        for x=1:length(A),
            for y=1:length(A),
                if probabilidadequery(x)~=0 && probabilidadewindow(y)~=0 && probabilidadequery_window(x,y)~=0
                    total=total+( probabilidadequery_window(x,y)*log2( ( probabilidadequery_window(x,y) ) / (probabilidadequery(x)*probabilidadewindow(y) ) ) );
                end
            end
        end
        
        inf(pos)=total;
        pos=pos+1;
    end
end
