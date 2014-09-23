% Escolha do ficheiro de input
load('1244803_prop.mat')

% Localização das Crises
interictal = find(EpiTargets(1,:));
preictal=find(EpiTargets(2,:));
ictal=find(EpiTargets(3,:));
posictal=find(EpiTargets(4,:));

[rInter,cInter] = size(interictal);
[rPre,cPre] = size(preictal);
[rIctal,cIctal] = size(ictal);
[rPos,cPos] = size(posictal);

%Para saber o numero de crises
totalCrises = 0;
for i=2:cIctal-1
    if( ictal(i)+1 ~= ictal(i+1))
        totalCrises = totalCrises+1;
    end
    
end

%Por causa da ultima crise que nao e detectada com o algoritmo de cima
totalCrises = totalCrises +1;

totalCrises

% 70% das crises sao utilizadas para treino
numCrisesTreino = floor(0.7*totalCrises);

periodosPreIctal = [];

crises = 1;
mudou = 1;

% armazena os inicios dos pre-ictais das crises (começa em 2 porque o ficheiro do paciente começa em pre-ictal e nao ia ter um inter-ictal para ir buscar)
for i=2:cPre-1
    
    if(mudou == 1)
        periodosPreIctal = [periodosPreIctal preictal(i)];
    end
    
    mudou = 0;
    
    if( preictal(i)+1 ~= preictal(i+1))
        crises = crises+1;
        mudou = 1;
    end
    
end

periodosPreIctal


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Selecao de caracteristicas melhores para previsao do pre ictal
figure(1)
hold on;

for i=1:cPre-1
    instante =   EpiInputs(:,preictal(i));


    plot(instante);


end

  yL = get(gca,'YLim')
  line([5 5],yL,'Color','r');
  line([27 27],yL,'Color','r');
  line([49 49],yL,'Color','r');
  line([71 71],yL,'Color','r');
  line([93 93],yL,'Color','r');
  line([115 115],yL,'Color','r');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


 vec = [5 27 49 71 93 115];
 
periodosPosIctal = [];
crises = 1;

for i=2:cPos-1
    
    if( posictal(i)+1 ~= posictal(i+1))
        crises = crises+1;
        periodosPosIctal = [periodosPosIctal posictal(i)];
    end
    
end

periodosPosIctal = [periodosPosIctal posictal(i)];

periodosPosIctal

treino = [];
treinoTargets = [];
numCrisesTreino = floor(totalCrises*0.7);
for i=2:numCrisesTreino
    
    disp(i)
    treino = horzcat(treino,EpiInputs(vec,periodosPreIctal(i)-100:periodosPosIctal(i)));
    
    treinoTargets = horzcat(treinoTargets,EpiTargets(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    
end

%treino
%treinoTargets


validacao = [];
validacaoTargets = [];
numCrisesValidacao = floor(totalCrises*0.15);
for i=numCrisesTreino+1:numCrisesTreino+numCrisesValidacao
    
    disp(i)
    
    validacao = horzcat(validacao,EpiInputs(vec,periodosPreIctal(i)-30:periodosPosIctal(i)));
    validacaoTargets = horzcat(validacaoTargets,EpiTargets(:,periodosPreIctal(i)-30:periodosPosIctal(i)));
    
end

%validacao
%validacaoTargets


teste = [];
testeTargets = [];
numCrisesTeste = floor(totalCrises*0.15);
for i=numCrisesTreino+numCrisesValidacao+1:numCrisesTreino+numCrisesValidacao+numCrisesTeste
    
    disp(i)
    teste = horzcat(teste,EpiInputs(vec,periodosPreIctal(i)-100:periodosPosIctal(i)));
    testeTargets = horzcat(testeTargets,EpiTargets(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    
end

%teste
%testeTargets

%disp(numCrisesTreino)
%disp(numCrisesValidacao)
%disp(numCrisesTeste)


