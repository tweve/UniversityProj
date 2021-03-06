function Y = myclassify( Perc,filled_inx )

%load('PerfectArial');
load('ManualPerfect');
load('treino350.mat');

opcao = menu ('Reconhecimento de Caracteres','Mem�ria Assiciativa + Classificador','Classificador');


if (opcao == 1)
    
    % Mem�ria Associativa
    disp entra;
    [x,y]= size(treino);

    W = 0;
    
        for pos = 0:10:y-1
            W =W +( ManualPerfect(:,1:10) * pinv(treino(:,pos+1:pos+10)));
        end     

    Perc = W* Perc;

    [x,y] = size(Perc);
    
end




% Classificador

target = zeros(10,10);

for targ=0:10-1,
    target(mod(targ,10)+1,targ+1)= 1;
end

    net = newp( treino(:,1:10), target, 'logsig','learnp');
    
    W=rand(10,256);
    b=rand(10,1);
    net.IW{1,1}=W;
    net.b{1,1}= b;

    net.performParam.ratio = 0.5;   % learning rate
    net.trainParam.epochs = 1000;   % maximum epochs
    net.trainParam.show = 35;       % show
    net.trainParam.goal = 1e-6;     % goal=objective
    net.performFcn = 'sse';         % criterion

    [x,y]= size(treino);
    
    for pos = 0:10:y-1
        net = train(net, treino(:,pos+1:pos+10), target);
    end
        

for pos = 1:length(filled_inx),
    out  = sim(net,Perc(:,pos));
    [a,v] = (max(out));
    Y(pos) = v;
end

    
end

