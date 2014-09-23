
% mapeia os dados no intervalo [-1 1]
treino = mapminmax(treino , -1,1);

inputs = treino;
targets = treinoTargets;

% Cria a rede neuronal
hiddenLayerSize = [15 15];
net = patternnet(hiddenLayerSize);

% Escolha da função de treino
net.trainFcn = 'traingdx';  % Levenberg-Marquardt

% Funcao de perfomance
net.performFcn = 'mse';  % Mean squared error

% Choose Plot Functions
net.plotFcns = {'plotperform','plottrainstate','ploterrhist', ...
  'plotregression', 'plotfit'};

% Não queremos divisão porque ela é feita manualmente
net.divideFcn = '';

% Treino da rede
net = train(net,inputs,targets);

% Testa a rede com o ficheiro de treino
outputs = net(inputs);



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
     tmp( find((outputs(:,i)== max (outputs(:,i))),1)) = 1;
     outputs(:,i) = tmp;
     

    if (treinoTargets(:,i) == pre_ict)
        if (tmp == pre_ict),
            positivos_verdadeiros = positivos_verdadeiros + 1;
        else
            falsos_negativos = falsos_negativos + 1;
        end
    end
     
     
     if (tmp ~= pre_ict), 
        if (treinoTargets(:,i) ~= pre_ict)
            negativos_verdadeiros = negativos_verdadeiros + 1;
        end
     end
     
     if(tmp == pre_ict)
         if (treinoTargets(:,i) ~= pre_ict)
             falsos_positivos = falsos_positivos +1 ;
         end
     end
     
     
     
     if ( find(outputs(:,i) == max (outputs(:,i)),1) == find(treinoTargets(:,i) == max (treinoTargets(:,i)),1)  )
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
   
   a = find((outputs(:,i)==1),1 ) ;
   finalTreino = [finalTreino a];
   
end


finalTreinoTargets = [];
for i= 1:size(treinoTargets,2)
   
   a = find((treinoTargets(:,i)==1),1 ) ;
   finalTreinoTargets = [finalTreinoTargets a];
   
end

figure (1)
hold on
plot(finalTreino,'g.')
plot(finalTreinoTargets,'ro')

figure(2)

plotconfusion(treinoTargets,outputs);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TESTE

positivos_verdadeiros = 0;
falsos_negativos = 0;
negativos_verdadeiros = 0;
falsos_positivos = 0;

certos = 0;
errados = 0;
total = 0;


teste = mapminmax(teste , -1,1);

outputs = net(teste);

for i= 1:size(outputs,2)
     tmp = zeros(4,1);
     tmp( find((outputs(:,i)== max (outputs(:,i))),1 )) = 1;
     outputs(:,i) = tmp;
     
      % pre ictal
     if (tmp == pre_ict),   %previu
        if (testeTargets(:,i) == pre_ict) %foi crise
            positivos_verdadeiros = positivos_verdadeiros +1;
        else   %nao foi crise
            negativos_verdadeiros = negativos_verdadeiros+1;  
        end
    else %nao previu
          if (testeTargets(:,i) == pre_ict) % foi crise
              falsos_positivos = falsos_positivos+1;
          else  %nao  foi crise
              falsos_negativos = falsos_negativos +1;
          end
     end
     
     if ( find(outputs(:,i) == max (outputs(:,i)),1) == find(testeTargets(:,i) == max (testeTargets(:,i)),1)  )
         certos = certos+1;
     else
         errados = errados+1;
     end
     total = total +1;

end

precisao_classificador_teste = certos/total
sensibilidade_teste = positivos_verdadeiros/(positivos_verdadeiros+falsos_negativos)
especificidade_teste = negativos_verdadeiros/(negativos_verdadeiros+falsos_positivos)

finalTeste = [];
for i= 1:size(outputs,2)
   
   a = find((outputs(:,i)==1),1 ) ;
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

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% VALIDACAO

positivos_verdadeiros = 0;
falsos_negativos = 0;
negativos_verdadeiros = 0;
falsos_positivos = 0;

certos = 0;
errados = 0;
total = 0;


validacao = mapminmax(validacao , -1,1);

outputs = net(validacao);

for i= 1:size(outputs,2)
    
     tmp = zeros(4,1);
     tmp( find((outputs(:,i)== max (outputs(:,i))),1 )) = 1;
     outputs(:,i) = tmp;
     
      % pre ictal
     if (tmp == pre_ict),   %previu
        if (validacaoTargets(:,i) == pre_ict) %foi crise
            positivos_verdadeiros = positivos_verdadeiros +1;
        else   %nao foi crise
            negativos_verdadeiros = negativos_verdadeiros+1;  
        end
     else %nao previu
          if (validacaoTargets(:,i) == pre_ict) % foi crise
              falsos_positivos = falsos_positivos+1;
          else  %nao  foi crise
              falsos_negativos = falsos_negativos +1;
          end
     end
     
     if ( find(outputs(:,i) == max (outputs(:,i)),1) == find(validacaoTargets(:,i) == max (validacaoTargets(:,i)),1)  )
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
   
   a = find((outputs(:,i)==1),1 ) ;
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


