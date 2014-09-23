function varargout = GUI(varargin)
    gui_Singleton = 1;
    gui_State = struct('gui_Name',       mfilename, ...
        'gui_Singleton',  gui_Singleton, ...
        'gui_OpeningFcn', @GUI_OpeningFcn, ...
        'gui_OutputFcn',  @GUI_OutputFcn, ...
        'gui_LayoutFcn',  [] , ...
        'gui_Callback',   []);
    if nargin && ischar(varargin{1})
        gui_State.gui_Callback = str2func(varargin{1});
    end

    if nargout
        [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
    else
        gui_mainfcn(gui_State, varargin{:});
    end
    % End initialization code - DO NOT EDIT
end

% --- Executes just before GUI is made visible.
function GUI_OpeningFcn(hObject, eventdata, handles, varargin)
    data_input = csvread('dataset.txt');
    data.X = data_input(:,1:end-1)';
    data.y = data_input(:,end)'+1;
    data.dim = size(data.X,1);
    data.num_data=size(data.X,2);
    data.name='Singapore Events';

    handles.norm = 0;
    handles.PCA = 0;
    handles.num_feat = 1;
    handles.classifier = 'bayes';
    handles.sampling = 'holdout';
    handles.spec = 0;
    handles.sens = 0;
    handles.fselection = 'kWallis';

    handles.data = data;
    handles.output = hObject;

    % Update handles structure
    guidata(hObject, handles);
end

% --- Outputs from this function are returned to the command line.
function varargout = GUI_OutputFcn(hObject, eventdata, handles)
    varargout{1} = handles.output;
end

function kvalue_CreateFcn(hObject, eventdata, handles)
    if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor','white');
    end
end

function k_folds_CreateFcn(hObject, eventdata, handles)
    if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor','white');
    end
end

function slider5_CreateFcn(hObject, eventdata, handles)
    if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor',[.9 .9 .9]);
    end
end

function svm_c_CreateFcn(hObject, eventdata, handles)
    if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor','white');
    end
end

function svm_g_CreateFcn(hObject, eventdata, handles)
    if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor','white');
    end
end

function nClass_CreateFcn(hObject, eventdata, handles)
    if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor','white');
    end
end

function edit1_CreateFcn(hObject, eventdata, handles)
    if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor','white');
    end
end

function slider2_CreateFcn(hObject, eventdata, handles)
    if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor',[.9 .9 .9]);
    end
end

function nfeatures_CreateFcn(hObject, eventdata, handles)
    if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
        set(hObject,'BackgroundColor',[.9 .9 .9]);
    end
end

% --- Executes on button press in normalization.
function normalization_Callback(hObject, eventdata, handles)
    do_normalization = get(hObject,'Value')
    if (do_normalization == 1)
        norm = 1;
    else
        norm = 0;
    end

    handles.norm = norm;
    handles.output = hObject;

    % Update handles structure
    guidata(hObject, handles);
end

% --- Executes when selected object is changed in pca_panel.
function pca_panel_SelectionChangeFcn(hObject, eventdata, handles)
    switch get(eventdata.NewValue,'Tag')
        case 'rb_raw'
            PCA = 0;
        case 'rb_pca'
            PCA = 1;
    end

    handles.PCA = PCA;
    handles.output = hObject;

    % Update handles structure
    guidata(hObject, handles);
end

function roc_curve_Callback(hObject, eventdata, handles)
    %ROC Curve
    figure;
    spec = handles.spec;
    sens = handles.sens;
    plot(1-spec,sens,'color',[rand(1) rand(1) rand(1)])
    xlabel('False Positives');
    ylabel('True Positives');
end

function classify_ButtonDownFcn(hObject, eventdata, handles)
    classifiersPannel_SelectionChangeFcn(hObject, eventdata, handles)
end

function nfeatures_Callback(hObject, eventdata, handles)
    data = handles.data;
    a = num2str(round(get(handles.nfeatures,'Value')*(data.dim-1))+1)
    set(handles.nfeat,'String',a)
    handles.num_feat= str2num(a);
    handles.output = hObject;
    guidata(hObject, handles);
end

function scree_test_Callback(hObject, eventdata, handles)
    normalization =handles.norm;
    PCA = handles.PCA;
    data = handles.data;

    if (normalization == 1)
        data = scalestd(data);
    end

    if (PCA == 1)
        modelo = pca(data.X);
        data_proj = linproj(data.X,modelo);
        data.X = data_proj;

        for i=1:length(modelo.eigval),
            info(i) = ((sum(modelo.eigval(1:i))/(sum(modelo.eigval(:))))*100);
        end

        figure;
        subplot(2,1,1);
        plot(info)
        title('Cumulated information')

        subplot(2,1,2);
        plot(modelo.eigval)
        title('Scree Test')
        hold on
        plot([0 12],[1 1],'r')
        hleg1 = legend('Eigval','Kaiser Test');
        set(hleg1,'Interpreter','none')
    else
        msgbox('Only available with PCA');
    end
end

function kruskawallis_Callback(hObject, eventdata, handles)
    normalization =handles.norm;
    PCA = handles.PCA;
    data = handles.data;

    if (normalization == 1)
        data = scalestd(data);
    end

    if (PCA == 1)
        modelo = pca(data.X);
        data_proj = linproj(data.X,modelo);
        data.X = data_proj;

        % Kruskall Wallis
        for i=1 : size(data.X(:,1),1)
            [a b c]=kruskalwallis(data.X(i,:),data.y,'off');
            aux(i,1) = i;
            aux(i,2) =  cell2mat(b(2,5));
        end
        aux = sortrows(aux,-2);

        data_kruskall = data;
        data_kruskall.X = data.X (aux(1:5,1),:);

        aux.eigval = modelo.eigval;

    else
        % Kruskall Wallis
        for i=1 : size(data.X(:,1),1)
            [a b c]=kruskalwallis(data.X(i,:),data.y,'off');
            aux(i,1) = i;
            aux(i,2) =  cell2mat(b(2,5));
        end
        aux = sortrows(aux,-2);

        data_kruskall = data;
        data_kruskall.X = data.X (aux(1:5,1),:);
        aux.eigval = aux(:,2);
    end

    for i=1:length(aux.eigval),
        info(i) = ((sum(aux.eigval(1:i))/(sum(aux.eigval(:))))*100);
    end

    figure;
    subplot(2,1,1);
    plot(info)
    title('Cumulated information')

    subplot(2,1,2);
    plot(aux.eigval)
    title('Scree Test')
    hold on
    plot([0 12],[1 1],'r')
    hleg1 = legend('Eigval','Kaiser Test');
    set(hleg1,'Interpreter','none')
end

function classify_Callback(hObject, eventdata, handles)

    normalization =handles.norm;
    PCA = handles.PCA;
    data = handles.data;
    num_feat= handles.num_feat;
    classifier = handles.classifier;
    sampling = handles.sampling;
    handles

    %% Normalization

    if normalization == 1
        data = scalestd(data);
    end

    %%PCA Krusskall ROC
    if (PCA == 1)
        modelo = pca(data.X);
        data_proj = linproj(data.X,modelo);
        data.X = data_proj;
        % Kruskall Wallis
        if(strcmp(handles.fselection, 'kWallis'))
            for i=1 : size(data.X(:,1),1)
                [a b c]=kruskalwallis(data.X(i,:),data.y,'off');
                aux(i,1) = i;
                aux(i,2) =  cell2mat(b(2,5));
            end
            aux = sortrows(aux,-2);
        else
            for i = 1:data.dim
                [FP,FN]=roc(data.X(i,:),data.y);
                spec = 1-FP;
                sens = 1-FN;
                aux(i,2)=trapz(sens(end:-1:1),spec(end:-1:1));
                aux(i,1) = i;
            end
            aux = sortrows(aux,-2);
        end

        data_kruskall = data;
        data_kruskall.X = data.X(aux(1:num_feat,1),:);

        aux.eigval = modelo.eigval;

    else
        if(strcmp(handles.fselection,'kWallis'))
            % Kruskall Wallis
            for i=1 : size(data.X(:,1),1)
                [a b c]=kruskalwallis(data.X(i,:),data.y,'off');
                aux(i,1) = i;
                aux(i,2) =  cell2mat(b(2,5));
            end
            aux = sortrows(aux,-2);
        else
            for i = 1:data.dim
                [FP,FN]=roc(data.X(i,:),data.y);
                spec = 1-FP;
                sens = 1-FN;
                aux(i,2)=trapz(sens(end:-1:1),spec(end:-1:1));
                aux(i,1) = i;
            end
            aux = sortrows(aux,-2);
        end
        data_kruskall = data;
        data_kruskall.X = data.X (aux(1:num_feat,1),:);
        aux.eigval = aux(:,2);
    end

    %% Classifiers
    switch classifier % Get Tag of selected object.
        case 'fisher'
            if (strcmp(sampling,'holdout'))

                choice = randsample(1:data_kruskall.num_data, data_kruskall.num_data);

                train.X(:,:)=data_kruskall.X(:,choice(1:round(data_kruskall.num_data*0.8)));
                train.y(:,:)=data_kruskall.y(:,choice(1:round(data_kruskall.num_data*0.8)));

                test.X(:,:)=data_kruskall.X(:,choice(round(data_kruskall.num_data*0.8)+1:end));
                test.y(:,:)=data_kruskall.y(:,choice(round(data_kruskall.num_data*0.8)+1:end));

                model = fldqp(train);
                ypred = linclass(test.X,model);
                error = cerror(ypred,test.y);

                % confusionmat
                cp = confusionmat(test.y,ypred);
                TP = cp(1,1);
                FP = cp(1,2);
                FN = cp(2,1);
                TN = cp(2,2);

                % ACC
                N = TN + FN;
                P = TP+FP;
                ACC =(TP+TN)/(P+N);
                set(handles.accuracy,'String',['Accuracy: ',num2str(ACC)]);

                %f1 score
                F1 = (2*TP)/(2*TP+FP+FN);
                set(handles.f1,'String',['F1 Score: ',num2str(F1)]);

                %Precision and Recall
                rec = TP/(TP+FN);
                prec = TP/P;
                set(handles.prec,'String',['Precision: ',num2str(prec)]);
                set(handles.recall,'String',['Recall: ',num2str(rec)]);

                %ROC Curve
                [FP,FN]=roc(ypred,test.y);
                spec = FP;
                sens = FN;
                auc = trapz(sens(1:1:end),spec(1:1:end));
                set(handles.auc,'String',['AUC: ',num2str(auc)]);

            elseif (strcmp(sampling,'cross_validation'))
                val_k = str2num(get(handles.k_folds, 'String'));
                folds = crossvalind('Kfold',data_kruskall.num_data,val_k)
                k = str2num(get(handles.kvalue, 'String'));

                accs = []
                f1s = []
                aucs = []
                precs = []
                recs = []
                for i_k = 1:val_k
                    clear train;
                    clear test;
                    indexes_train = find(folds ~= i_k);
                    indexes_test = find(folds == i_k);

                    train.X(:,:)=data_kruskall.X(:,indexes_train);
                    train.y(:,:)=data_kruskall.y(:,indexes_train);

                    test.X(:,:)=data_kruskall.X(:,indexes_test);
                    test.y(:,:)=data_kruskall.y(:,indexes_test);

                    model = fldqp(train);
                    ypred = linclass(test.X,model);
                    error = cerror(ypred,test.y);

                    cp = confusionmat(test.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);

                    [FP,FN]=roc(ypred,test.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));
                    
                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);

            elseif (strcmp(sampling,'bootstrap'))
                val_n = str2num(get(handles.nClass, 'String'));
                accs = []
                f1s = []
                aucs = []
                precs = []
                recs = []
                for i=0 : val_n
                    clear train;
                    clear choice;
                    choice = randsample(data_kruskall.num_data, data_kruskall.num_data,true);
                    train.X(:,:)=data_kruskall.X(:,choice(:));
                    train.y(:,:)=data_kruskall.y(:,choice(:));

                    model = fldqp(train);
                    ypred = linclass(data_kruskall.X,model);
                    error = cerror(ypred,data_kruskall.y);

                    cp = confusionmat(data_kruskall.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);

                    [FP,FN]=roc(ypred,data_kruskall.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));
                    
                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);
            end
            
        case 'bayes'
            if (strcmp(sampling,'holdout'))
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

                % confusionmat
                cp = confusionmat(test.y,ypred);
                TP = cp(1,1);
                FP = cp(1,2);
                FN = cp(2,1);
                TN = cp(2,2);

                % ACC
                N = TN + FN;
                P = TP+FP;
                ACC =(TP+TN)/(P+N);
                set(handles.accuracy,'String',['Accuracy: ',num2str(ACC)])

                %f1 score
                F1 = (2*TP)/(2*TP+FP+FN);
                set(handles.f1,'String',['F1 Score: ',num2str(F1)])

                %Precision and Recall
                rec = TP/(TP+FN);
                prec = TP/P;
                set(handles.prec,'String',['Precision: ',num2str(prec)]);
                set(handles.recall,'String',['Recall: ',num2str(rec)]);

                %ROC Curve
                [FP,FN]=roc(ypred,test.y);
                spec = FP;
                sens = FN;
                auc = trapz(sens(1:1:end),spec(1:1:end));
                set(handles.auc,'String',['AUC: ',num2str(auc)]);

            elseif (strcmp(sampling,'cross_validation'))
                val_k = str2num(get(handles.k_folds, 'String'));
                folds = crossvalind('Kfold',data_kruskall.num_data,val_k);
                k = str2num(get(handles.kvalue, 'String'));

                accs = []
                f1s = []
                aucs = []
                precs = []
                recs = []
                for i_k = 1:val_k
                    clear train;
                    clear test;
                    indexes_train = find(folds ~= i_k);
                    indexes_test = find(folds == i_k);

                    train.X(:,:)=data_kruskall.X(:,indexes_train);
                    train.y(:,:)=data_kruskall.y(:,indexes_train);

                    test.X(:,:)=data_kruskall.X(:,indexes_test);
                    test.y(:,:)=data_kruskall.y(:,indexes_test);

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

                    cp = confusionmat(test.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);

                    [FP,FN]=roc(ypred,test.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));
                    
                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);

            elseif (strcmp(sampling,'bootstrap'))
                val_n = str2num(get(handles.nClass, 'String'));
                accs = []
                f1s = []
                aucs = []
                precs = []
                recs = []
                for i=0 : val_n
                    clear train;
                    clear choice;
                    choice = randsample(data_kruskall.num_data, data_kruskall.num_data,true);
                    train.X(:,:)=data_kruskall.X(:,choice(:));
                    train.y(:,:)=data_kruskall.y(:,choice(:));

                    % Bayes
                    ix_w1 = find(train.y==1);
                    ix_w2 = find(train.y==2);

                    modelBayes.Pclass{1} = mlcgmm(train.X(:,ix_w1)); %estimate likelihood for no match
                    modelBayes.Pclass{2} = mlcgmm(train.X(:,ix_w2)); %estimate likelihood for match

                    P_w1 = length(ix_w1)/(length(ix_w1)+length(ix_w2)); %estimate prior
                    P_w2 = length(ix_w2)/(length(ix_w1)+length(ix_w2)); %estimate prior

                    modelBayes.Prior = [P_w1 P_w2];
                    modelBayes.fun = 'bayescls';

                    ypred = bayescls(data_kruskall.X(:,:),modelBayes);
                    error = cerror(ypred,data_kruskall.y);

                    cp = confusionmat(data_kruskall.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);

                    [FP,FN]=roc(ypred,data_kruskall.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));
                    
                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);
            end

        case 'knn'
            if (strcmp(sampling,'holdout'))
                choice = randsample(1:data_kruskall.num_data, data_kruskall.num_data);

                train.X(:,:)=data_kruskall.X(:,choice(1:round(data_kruskall.num_data*0.8)));
                train.y(:,:)=data_kruskall.y(:,choice(1:round(data_kruskall.num_data*0.8)));

                test.X(:,:)=data_kruskall.X(:,choice(round(data_kruskall.num_data*0.8)+1:end));
                test.y(:,:)=data_kruskall.y(:,choice(round(data_kruskall.num_data*0.8)+1:end));

                k = str2num(get(handles.kvalue, 'String'));
                model = knnrule(train,k);
                ypred = knnclass(test.X,model);
                error = cerror(ypred,test.y);

                % confusionmat
                cp = confusionmat(test.y,ypred);
                TP = cp(1,1);
                FP = cp(1,2);
                FN = cp(2,1);
                TN = cp(2,2);

                % ACC
                N = TN + FN;
                P = TP+FP;
                ACC =(TP+TN)/(P+N);
                set(handles.accuracy,'String',['Accuracy: ',num2str(ACC)])

                %f1 score
                F1 = (2*TP)/(2*TP+FP+FN);
                set(handles.f1,'String',['F1 Score: ',num2str(F1)])

                %Precision and Recall
                rec = TP/(TP+FN);
                prec = TP/P;
                set(handles.prec,'String',['Precision: ',num2str(prec)]);
                set(handles.recall,'String',['Recall: ',num2str(rec)]);

                %ROC Curve
                [FP,FN]=roc(ypred,test.y);
                spec = FP;
                sens = FN;
                auc = trapz(sens(1:1:end),spec(1:1:end));
                set(handles.auc,'String',['AUC: ',num2str(auc)]);

            elseif (strcmp(sampling,'cross_validation'))
                val_k = str2num(get(handles.k_folds, 'String'));
                folds = crossvalind('Kfold',data_kruskall.num_data,val_k)
                k = str2num(get(handles.kvalue, 'String'));

                accs = []
                f1s = []
                aucs = []
                precs = []
                recs = []
                for i_k = 1:val_k
                    clear train;
                    clear test;
                    indexes_train = find(folds ~= i_k);
                    indexes_test = find(folds == i_k);

                    train.X(:,:)=data_kruskall.X(:,indexes_train);
                    train.y(:,:)=data_kruskall.y(:,indexes_train);

                    test.X(:,:)=data_kruskall.X(:,indexes_test);
                    test.y(:,:)=data_kruskall.y(:,indexes_test);

                    model = knnrule(train,k);
                    ypred = knnclass(test.X,model);
                    error = cerror(ypred,test.y);

                    cp = confusionmat(test.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);

                    [FP,FN]=roc(ypred,test.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));
                    
                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);
                
            elseif (strcmp(sampling,'bootstrap'))
                val_n = str2num(get(handles.nClass, 'String'));
                accs = []
                f1s = []
                aucs = []
                precs = []
                recs = []
                for i=0 : val_n
                    clear train;
                    clear choice;
                    choice = randsample(data_kruskall.num_data, data_kruskall.num_data,true);
                    train.X(:,:)=data_kruskall.X(:,choice(:));
                    train.y(:,:)=data_kruskall.y(:,choice(:));

                    k = str2num(get(handles.kvalue, 'String'));
                    model = knnrule(train,k);
                    ypred = knnclass(data_kruskall.X,model);
                    error = cerror(ypred,data_kruskall.y);

                    cp = confusionmat(data_kruskall.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);

                    [FP,FN]=roc(ypred,data_kruskall.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));
                    
                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);
            end

        case 'svm'
            if (strcmp(sampling,'holdout'))
                choice = randsample(1:data_kruskall.num_data, data_kruskall.num_data);

                train.X(:,:)=data_kruskall.X(:,choice(1:round(data_kruskall.num_data*0.8)));
                train.y(:,:)=data_kruskall.y(:,choice(1:round(data_kruskall.num_data*0.8)));

                test.X(:,:)=data_kruskall.X(:,choice(round(data_kruskall.num_data*0.8)+1:end));
                test.y(:,:)=data_kruskall.y(:,choice(round(data_kruskall.num_data*0.8)+1:end));

                c = str2num(get(handles.svm_c, 'String'));
                g = str2num(get(handles.svm_g, 'String'));

                svmStruct = svmtrain(train.X',train.y','kernel_function','rbf','boxconstraint',c,'rbf_sigma',sqrt(1/(2*g)),'method','smo','kktviolationlevel',1,'showplot',false);
                ypred = svmclassify( svmStruct, test.X');
                error=cerror(ypred,test.y);

                % confusionmat
                cp = confusionmat(test.y,ypred);
                TP = cp(1,1);
                FP = cp(1,2);
                FN = cp(2,1);
                TN = cp(2,2);

                % ACC
                N = TN + FN;
                P = TP+FP;
                ACC =(TP+TN)/(P+N);
                set(handles.accuracy,'String',['Accuracy: ',num2str(ACC)])

                %f1 score
                F1 = (2*TP)/(2*TP+FP+FN);
                set(handles.f1,'String',['F1 Score: ',num2str(F1)])

                %Precision and Recall
                rec = TP/(TP+FN);
                prec = TP/P;
                set(handles.prec,'String',['Precision: ',num2str(prec)]);
                set(handles.recall,'String',['Recall: ',num2str(rec)]);

                %ROC Curve
                [FP,FN]=roc(ypred,test.y);
                spec = FP;
                sens = FN;
                auc = trapz(sens(1:1:end),spec(1:1:end));
                set(handles.auc,'String',['AUC: ',num2str(auc)]);

            elseif (strcmp(sampling,'cross_validation'))
                disp('svm cross val')
                val_k = str2num(get(handles.k_folds, 'String'));
                c = str2num(get(handles.svm_c, 'String'));
                g = str2num(get(handles.svm_g, 'String'));

                folds = crossvalind('Kfold',data_kruskall.num_data,val_k)
                k = str2num(get(handles.kvalue, 'String'));

                accs = []
                f1s = []
                aucs = []
                precs = []
                recs = []
                for i_k = 1:val_k
                    clear train;
                    clear test;
                    indexes_train = find(folds ~= i_k);
                    indexes_test = find(folds == i_k);

                    train.X(:,:)=data_kruskall.X(:,indexes_train);
                    train.y(:,:)=data_kruskall.y(:,indexes_train);

                    test.X(:,:)=data_kruskall.X(:,indexes_test);
                    test.y(:,:)=data_kruskall.y(:,indexes_test);

                    svmStruct = svmtrain(train.X',train.y','kernel_function','rbf','boxconstraint',c,'rbf_sigma',sqrt(1/(2*g)),'method','smo','kktviolationlevel',1,'showplot',false);
                    ypred = svmclassify( svmStruct, test.X');
                    error=cerror(ypred,test.y);

                    cp = confusionmat(test.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);

                    [FP,FN]=roc(ypred,test.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));
                    
                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);

            elseif (strcmp(sampling,'bootstrap'))
                val_n = str2num(get(handles.nClass, 'String'));
                accs = []
                f1s = []
                aucs = []
                recs = []
                precs = []
                for i=0 : val_n
                    clear train;
                    clear choice;
                    choice = randsample(data_kruskall.num_data, data_kruskall.num_data,true);
                    train.X(:,:)=data_kruskall.X(:,choice(:));
                    train.y(:,:)=data_kruskall.y(:,choice(:));

                    c = str2num(get(handles.svm_c, 'String'));
                    g = str2num(get(handles.svm_g, 'String'));

                    svmStruct = svmtrain(train.X',train.y','kernel_function','rbf','boxconstraint',c,'rbf_sigma',sqrt(1/(2*g)),'method','smo','kktviolationlevel',1,'showplot',false);
                    ypred = svmclassify( svmStruct, data_kruskall.X');
                    error = cerror(ypred,data_kruskall.y);

                    cp = confusionmat(data_kruskall.y,ypred);
                    TP = cp(1,1);
                    FP = cp(1,2);
                    FN = cp(2,1);
                    TN = cp(2,2);

                    % ACC
                    N = TN + FN;
                    P = TP+FP;
                    ACC =(TP+TN)/(P+N);
                    rec = TP/(TP+FN);
                    prec = TP/P;
                    F1 = (2*TP)/(2*TP+FP+FN);
                    
                    [FP,FN]=roc(ypred,data_kruskall.y);
                    spec = FP;
                    sens = FN;
                    auc = trapz(sens(1:1:end),spec(1:1:end));

                    accs = [accs ACC]
                    f1s = [f1s F1]
                    aucs = [aucs auc]
                    precs = [precs prec]
                    recs = [recs rec]
                end
                set(handles.accuracy,'String',['Accuracy: ',num2str(mean(accs))]);
                set(handles.f1,'String',['F1 Score: ',num2str(mean(f1s))]);
                set(handles.auc,'String',['AUC: ',num2str(mean(aucs))]);
                set(handles.prec,'String',['Precision: ',num2str(mean(precs))]);
                set(handles.recall,'String',['Recall: ',num2str(mean(recs))]);
            end
    end

    %% Results
    handles.spec = spec;
    handles.sens = sens;

    handles.output = hObject;
    guidata(hObject, handles);
end

function data_sampling_SelectionChangeFcn(hObject, eventdata, handles)
    switch get(eventdata.NewValue,'Tag') % Get Tag of selected object.
        case 'hold'
            handles.sampling = 'holdout';
        case 'cross'
            handles.sampling = 'cross_validation';
        case 'boots'
            handles.sampling = 'bootstrap';
    end
    handles.output = hObject;
    % Update handles structure
    guidata(hObject, handles);
end

function classifiersPannel_SelectionChangeFcn(hObject, eventdata, handles)
    switch get(eventdata.NewValue,'Tag') % Get Tag of selected object.
        case 'rbFisher'
            handles.classifier = 'fisher';
        case 'rbBayes'
            handles.classifier = 'bayes';
        case 'rbKNN'
            handles.classifier = 'knn';
        case 'rbSVM'
            handles.classifier = 'svm';
    end
    handles.output = hObject;
    % Update handles structure
    guidata(hObject, handles);
end

function rocBtn_Callback(hObject, eventdata, handles)
    data = handles.data;
    figure(1);
    hold on;
    
    for i = 1:data.dim
        [FP,FN]=roc(data.X(i,:),data.y);
        spec = 1-FP;
        sens = 1-FN;
        area(i,1)=trapz(sens(end:-1:1),spec(end:-1:1));
        area(i,2)=i;
    end

    area=sortrows(area,-1);
    
    for i = 1:data.dim
        [FP,FN]=roc(data.X(area(i,2),:),data.y);
        spec = 1-FP;
        sens = 1-FN;
        plot(1-spec,sens,'color',[rand(1) rand(1) rand(1)])
        
        leg{i} = num2str(area(i,1));
    end
    xlabel('False Positives');
    ylabel('True Positives');
    title('Features ROC Curves');
    legend(leg)
end

% --- Executes when selected object is changed in fSelect.
function fSelect_SelectionChangeFcn(hObject, eventdata, handles)
    switch get(eventdata.NewValue,'Tag') % Get Tag of selected object.
        case 'rocCrv'
            handles.fselection = 'rocCrv';
        case 'kWallis'
            handles.fselection = 'kWallis';
    end
    handles.output = hObject;
    % Update handles structure
    guidata(hObject, handles);
end

function k_folds_Callback(hObject, eventdata, handles)
% hObject    handle to k_folds (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of k_folds as text
%        str2double(get(hObject,'String')) returns contents of k_folds as a double
end

function nClass_Callback(hObject, eventdata, handles)
% hObject    handle to nClass (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of nClass as text
%        str2double(get(hObject,'String')) returns contents of nClass as a double
end

function kvalue_Callback(hObject, eventdata, handles)
% hObject    handle to kvalue (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of kvalue as text
%        str2double(get(hObject,'String')) returns contents of kvalue as a double
end

function svm_c_Callback(hObject, eventdata, handles)
% hObject    handle to svm_c (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of svm_c as text
%        str2double(get(hObject,'String')) returns contents of svm_c as a double
end

function svm_g_Callback(hObject, eventdata, handles)
% hObject    handle to svm_g (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of svm_g as text
%        str2double(get(hObject,'String')) returns contents of svm_g as a double
end