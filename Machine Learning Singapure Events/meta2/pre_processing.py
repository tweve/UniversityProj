import sys
import re
from fuzzycomp import fuzzycomp
import matplotlib.pyplot as pl
from geopy.geocoders import GoogleV3
import numpy

f = open('matching_events.tsv')
rows = f.readlines()
f.close()

labels_data = rows[0]
infos_data = rows[1:]

labels = labels_data.split()
print labels


def extractTitleFeatures(A,B):
    
    # remove pontuation
    A = re.sub(r'[^\w\s]','',A)
    # remove pontuation
    B = re.sub(r'[^\w\s]','',B)
    
    # remove multiple spaces
    A = re.sub(' +',' ',A)
    B = re.sub(' +',' ',B)
    
    
    charactersA = numpy.zeros(26)
    for char in A:
        if char >= 'a' and char <= 'z':
            charactersA[ord(char)-97]+=1
    
    charactersB = numpy.zeros(26)
    for char in B:
        if char >= 'a' and char <= 'z':
            charactersB[ord(char)-97]+=1
    
    subtraction = numpy.absolute(charactersA-charactersB)
    distance = numpy.sum(subtraction)
    
    max_chars =  max(numpy.sum(charactersA),numpy.sum(charactersB))
    if (max_chars ==0):
        measure =0
    else:
        measure = distance/float(max_chars)
    
    return [measure, fuzzycomp.levenshtein_distance(A,B), fuzzycomp.jaccard_distance(A,B), fuzzycomp.jaro_distance(A,B)]
    
def extractVenueFeatures(A,B):
    
     # remove pontuation
    A = re.sub(r'[^\w\s]','',A)
     # remove pontuation
    B = re.sub(r'[^\w\s]','',B)
    
    # remove multiple spaces
    A = re.sub(' +',' ',A)
    B = re.sub(' +',' ',B)
    if A == '':
        A = '-';
    if B == '':
        B = '-';
		
    charactersA = numpy.zeros(26)
    for char in A:
        if char >= 'a' and char <= 'z':
            charactersA[ord(char)-97]+=1
    
    charactersB = numpy.zeros(26)
    for char in B:
        if char >= 'a' and char <= 'z':
            charactersB[ord(char)-97]+=1
    
    subtraction = numpy.absolute(charactersA-charactersB)
    distance = numpy.sum(subtraction)
    
    max_chars =  max(numpy.sum(charactersA),numpy.sum(charactersB))
    if (max_chars ==0):
        measure =0
    else:
        measure = distance/float(max_chars)
    
    return [measure, fuzzycomp.levenshtein_distance(A,B), fuzzycomp.jaccard_distance(A,B), fuzzycomp.jaro_distance(A,B)]
    
def extractStartingTimeFeatures(A,B):
    
     # remove pontuation
    A = re.sub(r'[^\w\s]','',A)
     # remove pontuation
    B = re.sub(r'[^\w\s]','',B)
    
    diff = int(A)-int(B)
    
    return diff;

def extractDistanceFeatures(lat1,lng1,lat2,lng2):
    a = numpy.array((float(lat1),float(lng1)))
    b = numpy.array((float(lat2),float(lng2)))
    dist = numpy.linalg.norm(a-b)
    return dist;

def extractCathegoryFeatures(A,B):
    matches=re.findall(r"\'(.+?)\'",A)
    a = " ".join(matches)
    
    matches=re.findall(r"\'(.+?)\'",B)
    b = " ".join(matches)

    a = a.replace("&amp", "")
    a = re.sub(r'[^\w\s]','',a)
    a = re.sub(' +',' ',a)
 
    b = b.replace("&amp", "")
    b = re.sub(r'[^\w\s]','',b)
    b = re.sub(' +',' ',b)

    a = a.split()
    b = b.split()
    
    if not a:
        a.append("-")
    if not b:
        b.append("-")
        
    
    min_leven = 99999;
    value_leven =0;
    
    for elem1 in a:
        for elem2 in b:
            if elem1 == "":
                elem1 = "-"
            if elem2 == "":
                elem2 = "-"
            
            lev = fuzzycomp.levenshtein_distance(elem1,elem2)
            if  lev < min_leven:
                value_leven = lev
                min_leven = value_leven
            
    return value_leven

def extractDescription(A,B):
    
    A = re.sub(r'[^\w\s]','',A)
    B = re.sub(r'[^\w\s]','',B)
    
    a = A.split(" ")
    b = B.split(" ")
    
    maxim = max(len(a),len(b))

    count = 0
    
    for elem1 in a:
        if elem1 in b:
            count+=1
            b.remove(elem1)
            a.remove(elem1)
 
    return count/float(maxim)
   
dataset =[]
for row in infos_data:
    
    # remove \n no fim da string
    row = row.strip();
    
    # lower case
    row = row.lower();
    
    # Divide data em tabulacoes
    infos = row.split('\t');
    
    features = (extractTitleFeatures(infos[4],infos[5])+
    extractVenueFeatures(infos[6],infos[7]))
    
    features.append(extractStartingTimeFeatures(infos[8],infos[9]));
    features.append(extractDistanceFeatures(infos[10],infos[12],infos[11],infos[13]));
    features.append(extractCathegoryFeatures(infos[16],infos[17]));
    features.append(extractDescription(infos[18],infos[19]));
    features.append(int(infos[20]));
 
    #print features

    dataset.append(features)
    
out = open('dataset.txt','w');
for pattern in dataset:
    
 
    for i in range(0,len(pattern)-1):
        out.write(str(pattern[i])+",\t")
    i+=1
    out.write(str(pattern[i])+"\n")
out.close();   
'''
print no_match
no_match2 = numpy.array(no_match)
no_match = no_match2-100000
pl.plot(no_match,no_match2,'+')
pl.plot(match,match,'o',)
pl.show()
'''
