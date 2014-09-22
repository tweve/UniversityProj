function Indetifica()
    
    [query, fs, nbits] = wavread('guitarsolo.wav');

    [target00, fs, nbits] = wavread('Song01.wav');
    inf_mutua_query_target0 = InformacaoMutua(query,target00,-1:2/2^nbits:1,length(query)/2);
    
    disp ('A calcular entropia conjunta, aguarde...');
    
    [target01, fs, nbits] = wavread('Song02.wav');
    inf_mutua_query_target1 = InformacaoMutua(query,target01,-1:2/2^nbits:1,length(query)/2);

    disp ('A calcular entropia conjunta, aguarde...');

    [target02, fs, nbits] = wavread('Song03.wav');
    inf_mutua_query_target2 = InformacaoMutua(query,target02,-1:2/2^nbits:1,length(query)/2);

    disp ('A calcular entropia conjunta, aguarde...');

    [target03, fs, nbits] = wavread('Song04.wav');
    inf_mutua_query_target3 = InformacaoMutua(query,target03,-1:2/2^nbits:1,length(query)/2);

    disp ('A calcular entropia conjunta, aguarde...');

    [target04, fs, nbits] = wavread('Song05.wav');
    inf_mutua_query_target4 = InformacaoMutua(query,target04,-1:2/2^nbits:1,length(query)/2);

    disp ('A calcular entropia conjunta, aguarde...');

    [target05, fs, nbits] = wavread('Song06.wav');
    inf_mutua_query_target5 = InformacaoMutua(query,target05,-1:2/2^nbits:1,length(query)/2);

    disp ('A calcular entropia conjunta, aguarde...');

    [target06, fs, nbits] = wavread('Song07.wav');
    inf_mutua_query_target6 = InformacaoMutua(query,target06,-1:2/2^nbits:1,length(query)/2);

    disp ('A calcular entropia conjunta, aguarde...');

    
    maximo(1) = max(inf_mutua_query_target0);
    nomes(1,:) = 'Song01';
    maximo(2) = max(inf_mutua_query_target1);
    nomes(2,:) = 'Song02';
    maximo(3) = max(inf_mutua_query_target2);
    nomes(3,:) = 'Song03';
    maximo(4) = max(inf_mutua_query_target3);
    nomes(4,:) = 'Song04';
    maximo(5) = max(inf_mutua_query_target4);
    nomes(5,:) = 'Song05';
    maximo(6) = max(inf_mutua_query_target5);
    nomes(6,:) = 'Song06';
    maximo(7) = max(inf_mutua_query_target6);
    nomes(7,:) = 'Song07';
    
    [max2, ind]=sort(maximo,'descend');
    
    nomes(ind,:)
    display(max2);

end

