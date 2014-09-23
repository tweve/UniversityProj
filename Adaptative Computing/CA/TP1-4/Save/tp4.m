
load('500_1020.mat')
interictal = find(EpiTargets(1,:));
preictal=find(EpiTargets(2,:));
ictal=find(EpiTargets(3,:));
posictal=find(EpiTargets(4,:));

[rInter,cInter] = size(interictal);
[rPre,cPre] = size(preictal);
[rIctal,cIctal] = size(ictal);
[rPos,cPos] = size(posictal);

% treino = EpiInputs(interictal(1:floor(cInter*0.70)));
% validacao = EpiInputs(interictal(floor(cInter*0.70)+1:floor(cInter*0.85)));
% teste = EpiInputs(interictal(floor(cInter*0.85)+1:cInter));
% treinoTarget = EpiTargets(:,interictal(1:floor(cInter*0.70)));
% validacaoTarget = EpiTargets(:,interictal(floor(cInter*0.70)+1:floor(cInter*0.85)));
% testeTarget = EpiTargets(:,interictal(floor(cInter*0.85)+1:cInter));
% 
% disp('1º')
% size(treino)
% size(validacao)
% size(teste)
% 
% 
% treino = horzcat(treino,EpiInputs(preictal(1:floor(cPre*0.70))));
% validacao = horzcat(validacao,EpiInputs(preictal(floor(cPre*0.70)+1:floor(cPre*0.85))));
% teste = horzcat(teste,EpiInputs(preictal(floor(cPre*0.85)+1:cPre)));
% treinoTarget = horzcat(treinoTarget,EpiTargets(:,preictal(1:floor(cPre*0.70))));
% validacaoTarget = horzcat(validacaoTarget,EpiTargets(:,preictal(floor(cPre*0.70)+1:floor(cPre*0.85))));
% testeTarget = horzcat(testeTarget,EpiTargets(:,preictal(floor(cPre*0.85)+1:cPre)));
% 
% disp('2º')
% size(treino)
% size(validacao)
% size(teste)
% 
% 
% treino = horzcat(treino,EpiInputs(ictal(1:floor(cIctal*0.70))));
% validacao = horzcat(validacao,EpiInputs(ictal(floor(cIctal*0.70)+1:floor(cIctal*0.85))));
% teste = horzcat(teste,EpiInputs(ictal(floor(cIctal*0.85)+1:cIctal)));
% treinoTarget = horzcat(treinoTarget,EpiTargets(:,ictal(1:floor(cIctal*0.70))));
% validacaoTarget = horzcat(validacaoTarget,EpiTargets(:,ictal(floor(cIctal*0.70)+1:floor(cIctal*0.85))));
% testeTarget = horzcat(testeTarget,EpiTargets(:,ictal(floor(cIctal*0.85)+1:cIctal)));
% 
% disp('3º')
% size(treino)
% size(validacao)
% size(teste)
% 
% 
% 
% 
% treino = horzcat(treino,EpiInputs(posictal(1:floor(cPos*0.70))));
% validacao = horzcat(validacao,EpiInputs(posictal(floor(cPos*0.70)+1:floor(cPos*0.85))));
% teste = horzcat(teste,EpiInputs(posictal(floor(cPos*0.85)+1:cPos)));
% treinoTarget = horzcat(treinoTarget,EpiTargets(:,posictal(1:floor(cPos*0.70))));
% validacaoTarget = horzcat(validacaoTarget,EpiTargets(:,posictal(floor(cPos*0.70)+1:floor(cPos*0.85))));
% testeTarget = horzcat(testeTarget,EpiTargets(:,posictal(floor(cPos*0.85)+1:cPos)));
% 
% disp('4º')
% size(treino)
% size(validacao)
% size(teste)
% 
% randPermTreino = randperm(size(treinoTarget,2));
% randPermValidacao = randperm(size(validacaoTarget,2));
% randPermTeste = randperm(size(testeTarget,2));
% 
% treinoTarget = treinoTarget(:,randPermTreino);
% validacaoTarget = validacaoTarget(:,randPermValidacao);
% testeTarget = testeTarget(:,randPermTeste);
% 
% treino = treino(:,randPermTreino);
% validacao = validacao(:,randPermValidacao);
% teste = teste(:,randPermTeste);
% 
% save('treino.mat','treino')
% save('validacao.mat','validacao')
% save('teste.mat','teste')
% 
% save('treinoTarget.mat','treinoTarget')
% save('validacaoTarget.mat','validacaoTarget')
% save('testeTarget.mat','testeTarget')
% 



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



treino = [];
treinoTargets = [];
for i=1:totalCrises

    if(i < floor(totalCrises*0.7))
        treino = horzcat(treino,EpiInputs(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
        treinoTargets = horzcat(treinoTargets,EpiTargets(:,periodosPreIctal(i)-100:periodosPosIctal(i)));
    end
end

treino
treinoTargets


