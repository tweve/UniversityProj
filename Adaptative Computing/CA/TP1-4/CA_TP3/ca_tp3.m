function [] = ca_tp3( )

    option = menu ('REDES NEURONAIS NA MODELIZAÇÃO DE SISTEMAS DINÂMICOS','Sistema Linear','Sistema Não Linear');

    
    load('input.mat');
    load('output.mat');

    inputSeries = tonndata(input.Data,false,false);
    targetSeries = tonndata(output.Data,false,false);
        
    inputDelays = [1,2];
    feedbackDelays = [1 2];
        
        
    % LINEAR SYSTEM
    if option == 1
       
        hiddenLayerSize = [];
        feedbackMode = 'open';
        trainFcn = 'traingdx';
      
        net = narxnet(inputDelays,feedbackDelays,hiddenLayerSize,feedbackMode,trainFcn);
        
        %--------- Para o learnwh
        %net = narxnet(inputDelays,feedbackDelays,hiddenLayerSize,feedbackMode);
        %net.trainFcn = 'trainb';
        %net.adaptFcn = 'adaptwb';
        %net.inputWeights{1,1}.learnFcn = 'learnwh';
        %net.inputWeights{1,2}.learnFcn = 'learnwh';
        %net.biases{1}.learnFcn = 'learnwh';
        % --------
        
        view(net);

        net.divideFcn = '';
        [p,Pi,Ai,t] = preparets(net,inputSeries, {}, targetSeries);


        net = train(net, p,t,Pi);

        yp = sim(net,p,Pi);
        e = cell2mat(yp)-cell2mat(t);
        plot(e)

        
        return;
        
    end

    
    
    % NON LINEAR SYSTEM
    hiddenLayerSize = 2;
    net = narxnet(inputDelays,feedbackDelays,hiddenLayerSize);
    
    view(net);
    
    net.divideFcn = '';
    
    [p,Pi,Ai,t] = preparets(net,inputSeries, {}, targetSeries);
    
    net = train(net, p,t,Pi);
    
    yp = sim(net,p,Pi);
    e = cell2mat(yp)-cell2mat(t);
    plot(e)

end

