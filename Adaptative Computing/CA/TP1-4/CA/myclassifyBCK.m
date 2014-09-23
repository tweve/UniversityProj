function Y = myclassify( P,filled_inx )

target = zeros(10 ,10);

for targ=0:10-1,
    target(targ+1,mod(targ,10)+1)= 1;
end

net=feedforwardnet(10);
    

    net.performParam.ratio = 0.5;   % learning rate
    net.trainParam.epochs = 1000;   % maximum epochs
    net.trainParam.show = 35;       % show
    net.trainParam.goal = 1e-6;     % goal=objective
    net.performFcn = 'sse';         % criterion


net = train(net, P, target);


for pos = 1:length(filled_inx),

    out  = sim(net,P(:,pos));
    
    found = 0;
    
    for i=1:10,
        if (out(i)==1)
            Y(pos) = i;
            found = 1;
        end
    end
    
    if (found ==0)
          Y(pos) = 1;
    end

   
end
    
end

