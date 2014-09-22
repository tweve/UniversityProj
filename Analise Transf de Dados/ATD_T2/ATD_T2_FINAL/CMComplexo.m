% Igor Nelson Garrido da Cruz 
% Goncalo Silva Pereira


function CM = CMComplexo(m_max,cm,tetam )

negativos = [];
positivos = [];

for m=0 : m_max,
    if (m == 0)
        c0 = cm(1)*(cos(tetam(1)) + j*sin(tetam(1)));
    else
        factor = cm(m+1)/2 * (cos(tetam(m+1)) + j*sin(tetam(m+1)));
        positivos = [positivos factor];
        factorsimetrico = cm(m+1)/2 * (cos(tetam(m+1)) - j*sin(tetam(m+1)));
        negativos = [factorsimetrico negativos];
    end
end
CM = [negativos c0 positivos];
end

