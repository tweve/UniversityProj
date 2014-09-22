% Igor Nelson Garrido da Cruz 2009111924
% Gonçalo Silva Pereira 2009111643

tt = 0.5;
f = 800;
fs = 44100;
t = (0:1/fs: tt)';
freq = 11025;
pos = 1;
maximototal=0;

for f = 200:100:18000,
    y = sin (2 * pi * f * t);
    wavplay(y,fs,'async');
    
    y=wavrecord(0.5*fs+1,fs);

    for k=1:30
      % dividimos em 30 partes iguais
       subts=length(y)/30;
       suby=y((1+(k-1))*round(subts)):y(k*round(subts));
       maximototal=maximototal+max(abs(suby)); %maximototal+local
    end

    Amp(pos)=maximototal/30;
    pos=pos+1;
    maximototal = 0;
end
frequencia = 200:100:18000;
plot(frequencia,Amp);
