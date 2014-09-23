clear all
clc
close all

data_input = csvread('dataset.txt');

data.X = data_input(:,9:9)';
data.y = data_input(:,end)'+1;
data.dim = size(data.X,1);
data.num_data=size(data.X,2);
data.name='Singapore Title Events';

% data = scalestd(data);

% modelo = pca(data.X);
% data_proj = linproj(data.X,modelo);
% data.X = data_proj;

% Kruskall Wallis
for i=1 : size(data.X(:,1),1)
    [a b c]=kruskalwallis(data.X(i,:),data.y,'off');
    aux(i,1) = i; 
    aux(i,2) =  cell2mat(b(2,5));
    close all;
end
aux = sortrows(aux,-2);

data_kruskall = data;
data_kruskall.X = data.X (aux(1:1,1),:);


% Kaiser test - discarta as features com eigvals menores que 1
% Threshold - fazer gráfico de informacao acumulada e ver a olho a
% quantidade desejada 
% Scree Test - Discartar quando o grafico tende para zero

% for i=1:length(modelo.eigval),
%     info(i) = ((sum(modelo.eigval(1:i))/(sum(modelo.eigval(:))))*100);
% end
% figure(1);
% 
% subplot(3,1,1);
% plot(info)
% title('Cumulated information')
% 
% subplot(3,1,2);
% plot(modelo.eigval)
% title('Scree Test')

% Data Sampling
% 50 treino 50 teste
choice = randsample(1:data_kruskall.num_data, data_kruskall.num_data);

train.X(:,:)=data_kruskall.X(:,choice(1:round(data_kruskall.num_data*0.8)));
train.y(:,:)=data_kruskall.y(:,choice(1:round(data_kruskall.num_data*0.8)));

test.X(:,:)=data_kruskall.X(:,choice(round(data_kruskall.num_data*0.8)+1:end));
test.y(:,:)=data_kruskall.y(:,choice(round(data_kruskall.num_data*0.8)+1:end));

% Bayes
ix_w1 = find(train.y==1);
ix_w2 = find(train.y==2);

modelBayes.Pclass{1} = mlcgmm(train.X(:,ix_w1)); %estimate likelihood for no match
modelBayes.Pclass{2} = mlcgmm(train.X(:,ix_w2)); %estimate likelihood for match

P_w1 = length(ix_w1)/(length(ix_w1)+length(ix_w2)); %estimate prior
P_w2 = length(ix_w2)/(length(ix_w1)+length(ix_w2)); %estimate prior

modelBayes.Prior = [P_w1 P_w2];
modelBayes.fun = 'bayescls';

ypred = bayescls(test.X(:,:),modelBayes);
error = cerror(ypred,test.y);


%% K NN
% model = knnrule(train,2);
% ypred = knnclass(test.X,model);
% error = cerror(ypred,test.y);

%% Fisher
% model = fldqp(train);
% ypred = linclass(test.X,model);
% error = cerror(ypred,test.y);

%% SVM
% nRuns=3;
% 
% c=-5:1;
% C=2.^c;
% 
% gg=-5:1;
% G=2.^gg;
% 
% mederror=zeros(numel(C),numel(G));
% 
% for ii=1:nRuns
%     ii
% for j = 1:numel(C)
%     for jj = 1:numel(G)
%     
%         rand_choice=randi(2,data_kruskall.num_data,1)-1;
%         g=0;
%         h=0;
%         for i=1:data_kruskall.num_data
%             if rand_choice(i) == 0
%                 g=g+1;
%                 train.X (:,g)= data_kruskall.X(:,i);
%                 train.y(1,g) = data_kruskall.y(i);
%             else
%                 h=h+1;
%                 test.X (:,h)= data_kruskall.X(:,i);
%                 test.y(1,h) = data_kruskall.y(i);
%             end
%         end
% 
%         test.dim=size(test.X,1);
%         test.num_data=size(test.X,2);
%         test.name='Firms dataset test';
% 
%         train.dim=size(train.X,1);
%         train.num_data=size(train.X,2);
%         train.name='Firms dataset train';
% 
%         % for j = 1:12
% 
%         model = smo(train,struct('ker','rbf','C',C(j),'arg', sqrt(1/(2*G(jj)))));
% %         model = smo(train,struct('ker','rbf','C',C(j)));
%         
%         ypred = svmclass( test.X, model );
%         clear model
%         error(j,jj) = cerror(ypred,test.y);
% %        ppatterns(train); psvm(model,struct('background',1));
%     end
%     
% end
% 
% % figure()
% % contourf(gg,c,error)
% mederror =mederror+error;
% end
% mederror = mederror/nRuns;
% 
% figure()
% contourf(gg,c,mederror)
% 
% [x,y]=find(mederror == min(min(mederror)));
% 
% 
%  model = smo(train,struct('ker','rbf','C',1,'arg', sqrt(1/(2*1))));
%  ypred = svmclass( test.X, model );
%  error=cerror(ypred,test.y);
% 
% % psvm(model,struct('background',1));
%  
% 
% 
% 
% 
% 

sprintf('Error rate=%.2f%%',error *100)

%ROC Curve
figure(2);
[FP,FN]=roc(ypred,test.y);
spec = FP;
sens = FN;
plot(1-spec,sens,'color',[rand(1) rand(1) rand(1)])
auc = trapz(sens(1:1:end),spec(1:1:end))



