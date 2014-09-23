
load('1900_1020.mat')
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
for i=1:cIctal-1
    if( ictal(i)+1 ~= ictal(i+1))
        totalCrises = totalCrises+1;
    end
    
end

%Por causa da ultima crise que nao e detectada com o algoritmo de cima
totalCrises = totalCrises +1;

totalCrises


numCrisesTreino = floor(0.7*totalCrises);



periodosPreIctal = [];

crises = 1;
mudou = 1;
for i=1:cPre-1
    
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





periodosPosIctal = [];
crises = 1;

for i=1:cPos-1
    
    if( posictal(i)+1 ~= posictal(i+1))
        crises = crises+1;
        periodosPosIctal = [periodosPosIctal posictal(i)];
    end
    
end

periodosPosIctal = [periodosPosIctal posictal(i)];

periodosPosIctal


disp('Treino')

treino = [];
treinoTargets = [];
numCrisesTreino = floor(totalCrises*0.7);
for i=2:numCrisesTreino+1
    
    disp(i)
    treino = horzcat(treino,EpiInputs(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    treinoTargets = horzcat(treinoTargets,EpiTargets(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    
end

%treino
%treinoTargets

disp('Validacao')


validacao = [];
validacaoTargets = [];
numCrisesValidacao = floor(totalCrises*0.15);
for i=numCrisesTreino+2:numCrisesTreino+numCrisesValidacao+1
    
    disp(i)
    
    validacao = horzcat(validacao,EpiInputs(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    validacaoTargets = horzcat(validacaoTargets,EpiTargets(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    
end

%validacao
%validacaoTargets

disp('Teste')


teste = [];
testeTargets = [];
numCrisesTeste = floor(totalCrises*0.15);
for i=numCrisesTreino+numCrisesValidacao+2:numCrisesTreino+numCrisesValidacao+numCrisesTeste+1
    
    disp(i)
    teste = horzcat(teste,EpiInputs(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    testeTargets = horzcat(testeTargets,EpiTargets(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    
end

%teste
%testeTargets

%disp(numCrisesTreino)
%disp(numCrisesValidacao)
%disp(numCrisesTeste)


