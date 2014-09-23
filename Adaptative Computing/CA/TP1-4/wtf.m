
figure(1)
hold on;

for i=1:2
    vector =  sort( EpiInputs(:,i),'descend');


    plot(EpiInputs(:,i));

    yL = get(gca,'YLim')
    line([4 4],yL,'Color','r');


end