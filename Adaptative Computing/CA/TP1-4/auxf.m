
finalT = []
for i= 1:size(treinoTargets,2)
   
   a = find((treinoTargets(:,i)==1),1 ) ;
   finalT = [finalT a]
   
end