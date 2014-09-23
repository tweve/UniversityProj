percep = zeros(256,10);
target = zeros(10,10);

net =newp(percep,target);
W=rand(10,256);
b=rand(10,1);
net.IW{1,1}=W;
net.b{1,1}= b;

net.performParam.ratio = 0.5;   % learning rate
net.trainParam.epochs = 1000;   % maximum epochs
net.trainParam.show = 35;       % show
net.trainParam.goal = 1e-6;     % goal=objective
net.performFcn = 'sse';         % criterion


target(1,1)= 1;
target(2,2)= 1;
target(2,2)= 1;
target(3,3)= 1;
target(4,4)= 1;
target(5,5)= 1;
target(6,6)= 1;
target(7,7)= 1;
target(8,8)= 1;
target(9,9)= 1;
target(10,10)= 1;


for i = 1:length(filled_inx),
    
    net = train(net, P(:,i), target(:, i ));
end
