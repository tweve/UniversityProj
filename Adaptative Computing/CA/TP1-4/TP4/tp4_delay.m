% Solve an Autoregression Problem with External Input with a NARX Neural Network
% Script generated by NTSTOOL
% Created Mon Dec 03 22:09:49 GMT 2012
%
% This script assumes these variables are defined:
%
%   treino - input time series.
%   treinoTargets - feedback time series.

inputSeries = tonndata(treino,true,false);
targetSeries = tonndata(treinoTargets,true,false);

% Create a Nonlinear Autoregressive Network with External Input
inputDelays = 1:5;
feedbackDelays = 1:5;
hiddenLayerSize = 10;
net = narxnet(inputDelays,feedbackDelays,hiddenLayerSize);

% Choose Input and Feedback Pre/Post-Processing Functions
% Settings for feedback input are automatically applied to feedback output
% For a list of all processing functions type: help nnprocess
% Customize input parameters at: net.inputs{i}.processParam
% Customize output parameters at: net.outputs{i}.processParam
net.inputs{1}.processFcns = {'removeconstantrows','mapminmax'};
net.inputs{2}.processFcns = {'removeconstantrows','mapminmax'};

% Prepare the Data for Training and Simulation
% The function PREPARETS prepares timeseries data for a particular network,
% shifting time by the minimum amount to fill input states and layer states.
% Using PREPARETS allows you to keep your original time series data unchanged, while
% easily customizing it for networks with differing numbers of delays, with
% open loop or closed loop feedback modes.
[inputs,inputStates,layerStates,targets] = preparets(net,inputSeries,{},targetSeries);

% Setup Division of Data for Training, Validation, Testing
% The function DIVIDERAND randomly assigns target values to training,
% validation and test sets during training.
% For a list of all data division functions type: help nndivide
% The property DIVIDEMODE set to TIMESTEP means that targets are divided
% into training, validation and test sets according to timesteps.
% For a list of data division modes type: help nntype_data_division_mode
net.divideFcn = '';  % Divide up every value

% Choose a Training Function
% For a list of all training functions type: help nntrain
% Customize training parameters at: net.trainParam
net.trainFcn = 'traingdx';  % Levenberg-Marquardt

% Choose a Performance Function
% For a list of all performance functions type: help nnperformance
% Customize performance parameters at: net.performParam
net.performFcn = 'mse';  % Mean squared error

% Choose Plot Functions
% For a list of all plot functions type: help nnplot
% Customize plot parameters at: net.plotParam
net.plotFcns = {'plotperform','plottrainstate','plotresponse', ...
  'ploterrcorr', 'plotinerrcorr'};

% Train the Network
[net,tr] = train(net,inputs,targets,inputStates,layerStates);

% Test the Network
outputs = net(inputs,inputStates,layerStates);

errors = gsubtract(targets,outputs);
performance = perform(net,targets,outputs)

% Recalculate Training, Validation and Test Performance
trainTargets = gmultiply(targets,tr.trainMask);
valTargets = gmultiply(targets,tr.valMask);
testTargets = gmultiply(targets,tr.testMask);
trainPerformance = perform(net,trainTargets,outputs)
valPerformance = perform(net,valTargets,outputs)
testPerformance = perform(net,testTargets,outputs)

% View the Network
view(net)

% Plots
% Uncomment these lines to enable various plots.
%figure, plotperform(tr)
%figure, plottrainstate(tr)
%figure, plotregression(targets,outputs)
%figure, plotresponse(targets,outputs)
%figure, ploterrcorr(errors)
%figure, plotinerrcorr(inputs,errors)

% Closed Loop Network
% Use this network to do multi-step prediction.
% The function CLOSELOOP replaces the feedback input with a direct
% connection from the outout layer.
netc = closeloop(net);
netc.name = [net.name ' - Closed Loop'];
view(netc)
[xc,xic,aic,tc] = preparets(netc,inputSeries,{},targetSeries);
yc = netc(xc,xic,aic);
closedLoopPerformance = perform(netc,tc,yc)

% Early Prediction Network
% For some applications it helps to get the prediction a timestep early.
% The original network returns predicted y(t+1) at the same time it is given y(t+1).
% For some applications such as decision making, it would help to have predicted
% y(t+1) once y(t) is available, but before the actual y(t+1) occurs.
% The network can be made to return its output a timestep early by removing one delay
% so that its minimal tap delay is now 0 instead of 1.  The new network returns the
% same outputs as the original network, but outputs are shifted left one timestep.
nets = removedelay(net);
nets.name = [net.name ' - Predict One Step Ahead'];
view(nets)
[xs,xis,ais,ts] = preparets(nets,inputSeries,{},targetSeries);
ys = nets(xs,xis,ais);
earlyPredictPerformance = perform(nets,ts,ys)








%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TREINO
certos = 0;
errados = 0;
total = 0;

positivos_verdadeiros = 0;
falsos_negativos = 0;
negativos_verdadeiros = 0;
falsos_positivos = 0;

pre_ict = zeros(4,1);
pre_ict(2,1)= 1;


% Compara o output da rede com o desejado
for i= 1:size(outputs,2)
     tmp = zeros(4,1);
     tmp( find((outputs{1,i}== max (outputs{1,i})),1)) = 1;
    outputs{1,i} = tmp;
     
     % pre ictal
     if (tmp == pre_ict),   %previu
        if (treinoTargets(:,i) == pre_ict) %foi crise
            positivos_verdadeiros = positivos_verdadeiros +1;
        else   %nao foi crise
            falsos_positivos = falsos_positivos+1;  
        end
     else %nao previu
          if (treinoTargets(:,i) == pre_ict) % foi crise
              falsos_negativos = falsos_negativos+1;
          else  %nao  foi crise
              negativos_verdadeiros = negativos_verdadeiros +1;
          end
     end
     
     if ( find(outputs{1,i} == max (outputs{1,i}),1) == find(treinoTargets(:,i) == max (treinoTargets(:,i)),1)  )
         certos = certos+1;
     else
         errados = errados+1;
     end
     total = total +1;
     
end

precisao_classificador_treino = certos/total
sensibilidade_treino = positivos_verdadeiros/(positivos_verdadeiros+falsos_negativos)
especificidade_treino = negativos_verdadeiros/(negativos_verdadeiros+falsos_positivos)


finalTreino = [];
for i= 1:size(outputs,2)
   
   a = find((outputs{1,i}==1),1 ) ;
   finalTreino = [finalTreino a];
   
end


finalTreinoTargets = [];
for i= 1:size(treinoTargets,2)
   
   a = find((treinoTargets(:,i)==1),1 ) ;
   finalTreinoTargets = [finalTreinoTargets a];
   
end

figure (1)
hold on
plot(finalTreino,'g')
plot(finalTreinoTargets,'r')













inputSeries = tonndata(teste,true,false);
targetSeries = tonndata(testeTargets,true,false);

[inputs,inputStates,layerStates,targets] = preparets(net,inputSeries,{},targetSeries);

% Setup Division of Data for Training, Validation, Testing
% The function DIVIDERAND randomly assigns target values to training,
% validation and test sets during training.
% For a list of all data division functions type: help nndivide

% The property DIVIDEMODE set to TIMESTEP means that targets are divided
% into training, validation and test sets according to timesteps.




% Test the Network
outputs = net(inputs,inputStates,layerStates);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TESTE
certos = 0;
errados = 0;
total = 0;

positivos_verdadeiros = 0;
falsos_negativos = 0;
negativos_verdadeiros = 0;
falsos_positivos = 0;

pre_ict = zeros(4,1);
pre_ict(2,1)= 1;


% Compara o output da rede com o desejado
for i= 1:size(outputs,2)
     tmp = zeros(4,1);
     tmp( find((outputs{1,i}== max (outputs{1,i})),1)) = 1;
    outputs{1,i} = tmp;
     
     % pre ictal
     if (tmp == pre_ict),   %previu
        if (testeTargets(:,i) == pre_ict) %foi crise
            positivos_verdadeiros = positivos_verdadeiros +1;
        else   %nao foi crise
            falsos_positivos = falsos_positivos+1;  
        end
     else %nao previu
          if (testeTargets(:,i) == pre_ict) % foi crise
              falsos_negativos = falsos_negativos+1;
          else  %nao  foi crise
              negativos_verdadeiros = negativos_verdadeiros +1;
          end
     end
     
     if ( find(outputs{1,i} == max (outputs{1,i}),1) == find(testeTargets(:,i) == max (testeTargets(:,i)),1)  )
         certos = certos+1;
     else
         errados = errados+1;
     end
     total = total +1;
     
end

precisao_classificador_teste = certos/total
sensibilidade_teste= positivos_verdadeiros/(positivos_verdadeiros+falsos_negativos)
especificidade_teste = negativos_verdadeiros/(negativos_verdadeiros+falsos_positivos)


finalTeste = [];
for i= 1:size(outputs,2)
   
   a = find((outputs{1,i}==1),1 ) ;
   finalTeste = [finalTeste a];
   
end


finalTesteTargets = [];
for i= 1:size(testeTargets,2)
   
   a = find((testeTargets(:,i)==1),1 ) ;
   finalTesteTargets = [finalTesteTargets a];
   
end

figure (2)
hold on
plot(finalTeste,'g')
plot(finalTesteTargets,'r')








inputSeries = tonndata(validacao,true,false);
targetSeries = tonndata(validacaoTargets,true,false);

[inputs,inputStates,layerStates,targets] = preparets(net,inputSeries,{},targetSeries);

% Setup Division of Data for Training, Validation, Testing
% The function DIVIDERAND randomly assigns target values to training,
% validation and test sets during training.
% For a list of all data division functions type: help nndivide

% The property DIVIDEMODE set to TIMESTEP means that targets are divided
% into training, validation and test sets according to timesteps.




% Test the Network
outputs = net(inputs,inputStates,layerStates);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TESTE
certos = 0;
errados = 0;
total = 0;

positivos_verdadeiros = 0;
falsos_negativos = 0;
negativos_verdadeiros = 0;
falsos_positivos = 0;

pre_ict = zeros(4,1);
pre_ict(2,1)= 1;


% Compara o output da rede com o desejado
for i= 1:size(outputs,2)
     tmp = zeros(4,1);
     tmp( find((outputs{1,i}== max (outputs{1,i})),1)) = 1;
    outputs{1,i} = tmp;
     
     % pre ictal
     if (tmp == pre_ict),   %previu
        if (validacaoTargets(:,i) == pre_ict) %foi crise
            positivos_verdadeiros = positivos_verdadeiros +1;
        else   %nao foi crise
            falsos_positivos = falsos_positivos+1;  
        end
     else %nao previu
          if (validacaoTargets(:,i) == pre_ict) % foi crise
              falsos_negativos = falsos_negativos+1;
          else  %nao  foi crise
              negativos_verdadeiros = negativos_verdadeiros +1;
          end
     end
     
     if ( find(outputs{1,i} == max (outputs{1,i}),1) == find(validacaoTargets(:,i) == max (validacaoTargets(:,i)),1)  )
         certos = certos+1;
     else
         errados = errados+1;
     end
     total = total +1;
     
end

precisao_classificador_validacao = certos/total
sensibilidade_validacao = positivos_verdadeiros/(positivos_verdadeiros+falsos_negativos)
especificidade_validacao = negativos_verdadeiros/(negativos_verdadeiros+falsos_positivos)


finalVal = [];
for i= 1:size(outputs,2)
   
   a = find((outputs{1,i}==1),1 ) ;
   finalVal = [finalVal a];
   
end


finalValTargets = [];
for i= 1:size(validacaoTargets,2)
   
   a = find((validacaoTargets(:,i)==1),1 ) ;
   finalValTargets = [finalValTargets a];
   
end

figure (3)
hold on
plot(finalVal,'g')
plot(finalValTargets,'r')



