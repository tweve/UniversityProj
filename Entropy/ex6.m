
%ex 1
plot(InformacaoMutua([2 6 4 10 5 9 5 8 0 8],[6 8 9 7 2 4 9 9 4 9 1 4 8 0 1 2 2 6 3 2 0 7 4 9 5 4 8 5 2 7 8 0 7 4 8 5 7 4 3 2 2 7 3 5 2 7 4 9 9 6],[0:10],1))


[query, fs, nbits] = wavread('guitarsolo.wav');

[target, fs, nbits] = wavread('target01 - repeat.wav');
info = InformacaoMutua(query,target,-1:2/2^nbits:1,length(query)/4);
subplot(211);
bar(info);

[target, fs, nbits] = wavread('target02 - repeatNoise.wav');
info = InformacaoMutua(query,target,-1:2/2^nbits:1,length(query)/4);
subplot(212);
bar(info);
