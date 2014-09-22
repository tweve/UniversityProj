"""
iiaAgents.py

Created by Rui Lopes on 2012-02-18.
Copyright (c) 2012 University of Coimbra. All rights reserved.
"""

from pacman import Directions
from game import Agent
import random
import game
from util import *

class iiaPacmanAgent(Agent):	
  
  mem = [0]
  
  def __init__( self, index = 0 ):
    self.index = index
    self.keys = []
    self.trainingName = self.getTrainingName()
    print "Training name:", self.trainingName
    file = open(self.trainingName, 'w')
    file.close()  
  
  
  def getTrainingName(self):
    import os.path
    name = "training_"
    count = 1
    while os.path.isfile(name + str(count) + ".iia"):
	    count += 1
    return name + str(count) + ".iia"
  
  def saveTraining(self, currentStuff):
    import cPickle
    try:
	    file = open(self.trainingName, 'r')
	    oldStuff = cPickle.load(file)
	    file.close()
    except EOFError:
	    oldStuff = []
    file = open(self.trainingName, 'w')
#	  print "Now saving:", oldStuff + [currentStuff]
#	  print "Adding:", currentStuff[0]
    cPickle.dump(oldStuff + [currentStuff], file) 
    file.close()  
  
  def getAction(self, state):
    
    stateRepresentation = getStateRepresentation(state)
    print "State Representation:", stateRepresentation
    
    if (self.EN(state) and self.scaredEN(state)):
      actionRepresentation = getActionRepresentation(Directions.NORTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])
      return ('North')
    if (self.ES(state) and self.scaredES(state)):
      actionRepresentation = getActionRepresentation(Directions.SOUTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('South')
    if (self.EE(state) and self.scaredEE(state)):
      actionRepresentation = getActionRepresentation(Directions.EAST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('East')
    if (self.EO(state) and self.scaredEO(state)):
      actionRepresentation = getActionRepresentation(Directions.WEST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('West')
    
    
    if (self.LO(state) and self.CO(state) and not(self.EO(state))):
      actionRepresentation = getActionRepresentation(Directions.WEST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('West')
    if (self.LS(state) and self.CS(state) and not(self.ES(state))):
      actionRepresentation = getActionRepresentation(Directions.SOUTH)
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('South')
    if (self.LN(state) and self.CN(state) and not(self.EN(state))):
      actionRepresentation = getActionRepresentation(Directions.NORTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('North')    
    if (self.LE(state) and self.CE(state) and not(self.EE(state))):
      actionRepresentation = getActionRepresentation(Directions.EAST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('East')    
    
    
    
    if (self.EN(state) and self.LE(state) and not(self.EE(state))):
      actionRepresentation = getActionRepresentation(Directions.EAST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('East')
    if (self.EN(state) and self.LO(state) and not(self.EO(state))):
      actionRepresentation = getActionRepresentation(Directions.WEST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('West')
    if (self.EN(state) and self.LS(state) and not(self.ES(state))):
      actionRepresentation = getActionRepresentation(Directions.SOUTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('South')         

    if (self.ES(state) and self.LE(state) and not(self.EE(state))):
      actionRepresentation = getActionRepresentation(Directions.EAST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('East')
    if (self.ES(state) and self.LO(state) and not(self.EO(state))):
      actionRepresentation = getActionRepresentation(Directions.WEST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('West')
    if (self.ES(state) and self.LN(state) and not(self.EN(state))):
      actionRepresentation = getActionRepresentation(Directions.NORTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('North')      

    if (self.EE(state) and self.LN(state) and not(self.EN(state))):
      actionRepresentation = getActionRepresentation(Directions.NORTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('North')
    if (self.EE(state) and self.LS(state) and not(self.ES(state))):
      actionRepresentation = getActionRepresentation(Directions.SOUTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('South')
    if (self.EE(state) and self.LO(state) and not(self.EO(state))):
      actionRepresentation = getActionRepresentation(Directions.WEST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('West')
    
    if (self.EO(state) and self.LN(state) and not(self.EN(state))):
      actionRepresentation = getActionRepresentation(Directions.NORTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('North')
    if (self.EO(state) and self.LS(state) and not(self.ES(state))):
      actionRepresentation = getActionRepresentation(Directions.SOUTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('South')
    if (self.EO(state) and self.LE(state) and not(self.EE(state))):
      actionRepresentation = getActionRepresentation(Directions.EAST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      return ('East')    
    
    
    lista = state.getLegalPacmanActions()
    lista.remove('Stop')
    
    # se o tamanho da lista e 3 o pacman esta num corredor (stop+ 2 direcoes)
    if (len(lista)==2):
    #print lista
      if(self.mem[0]==1 and self.LN(state) and not(self.EN(state))):
	actionRepresentation = getActionRepresentation(Directions.NORTH)
	print "Action Representation:", actionRepresentation
	self.saveTraining([stateRepresentation, actionRepresentation])	
        return 'North'
      if(self.mem[0]==2 and self.LS(state) and not(self.ES(state))):  
	actionRepresentation = getActionRepresentation(Directions.SOUTH)
	print "Action Representation:", actionRepresentation
	self.saveTraining([stateRepresentation, actionRepresentation])	
        return 'South'    
      if(self.mem[0]==3 and self.LE(state) and not(self.EE(state))):  
	actionRepresentation = getActionRepresentation(Directions.EAST)
	print "Action Representation:", actionRepresentation
	self.saveTraining([stateRepresentation, actionRepresentation])	
        return 'East'  
      if(self.mem[0]==4 and self.LO(state) and not(self.EO(state))):   
	actionRepresentation = getActionRepresentation(Directions.WEST)
	print "Action Representation:", actionRepresentation
	self.saveTraining([stateRepresentation, actionRepresentation])	
        return 'West'


    if (len(lista) > 1):      
      if (self.mem[0] == 2 and self.LN(state)): lista.remove('North')
      if (self.mem[0] == 1 and self.LS(state)): lista.remove('South')
      if (self.mem[0] == 4 and self.LE(state)): lista.remove('East')
      if (self.mem[0] == 3 and self.LO(state)): lista.remove('West')
    
    dir = random.choice(lista);
      
    if dir == 'North':
      actionRepresentation = getActionRepresentation(Directions.NORTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      self.mem[0] =1
    if dir == 'South':
      actionRepresentation = getActionRepresentation(Directions.SOUTH)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      self.mem[0] =2     
    if dir == 'East':
      actionRepresentation = getActionRepresentation(Directions.EAST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      self.mem[0] =3    
    if dir == 'West':
      actionRepresentation = getActionRepresentation(Directions.WEST)
      print "Action Representation:", actionRepresentation
      self.saveTraining([stateRepresentation, actionRepresentation])      
      self.mem[0] =4    
          
    return dir
    
  # Sensores de Obstaculos
  # Sensores em + 1 posicao
  """"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
  
  def LN (self, state):
    paredes = state.getWalls()
    posicao = state.getPacmanPosition()
    if (paredes[posicao[0]][posicao[1]+1]):
      return False
    return True   
    
  def LS (self, state):
    paredes = state.getWalls()
    posicao = state.getPacmanPosition()
    if (paredes[posicao[0]][posicao[1]-1]):
      return False
    return True    

  def LE (self, state):
    paredes = state.getWalls()
    posicao = state.getPacmanPosition()
    if (paredes[posicao[0]+1][posicao[1]]):
      return False
    return True   
  
  def LO (self, state):
    paredes = state.getWalls()
    posicao = state.getPacmanPosition()
    if (paredes[posicao[0]-1][posicao[1]]):
      return False
    return True  
  
  
  """"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
  # Sensores de Comida
  # Sensores em + 1 posicao
  """"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""  
  
  def CN (self, state):
    capsulas = state.getCapsules()
    comida = state.getFood()
    posicao = state.getPacmanPosition()
    if (comida[posicao[0]][posicao[1]+1] or (posicao[0],posicao[1]+1) in capsulas):
      return True
    return False  
  
  def CS (self, state):
    capsulas = state.getCapsules()
    comida = state.getFood()
    posicao = state.getPacmanPosition()
    if (comida[posicao[0]][posicao[1]-1] or (posicao[0],posicao[1]-1) in capsulas):
      return True
    return False    
  
  def CE (self, state):
    capsulas = state.getCapsules()
    comida = state.getFood()
    posicao = state.getPacmanPosition()
    if (comida[posicao[0]+1][posicao[1]] or (posicao[0]+1,posicao[1]) in capsulas):
      return True
    return False       

  def CO (self, state):
    capsulas = state.getCapsules()
    comida = state.getFood()
    posicao = state.getPacmanPosition()
    if (comida[posicao[0]-1][posicao[1]] or (posicao[0]-1,posicao[1]) in capsulas):
      return True
    return False   
  
  """"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
    # Sensores de Inimigos
    # sensores em + 2 posicoes
  """"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""    
  
  def scaredEN (self, state):
    posicao = state.getPacmanPosition()
    posicao = (posicao[0],posicao[1]+1)
    paredes = state.getWalls();
    lista = state.getGhostPositions()
    
    indice = 0
   
    for i in range (len(lista)):
      # se o fantasma esta na posicao seguinte ou se esta 2 posicoes a frente e a posicao seguinte nao e uma parede
      if lista[i] == posicao or (lista[i] == (posicao[0],posicao[1]+2) and not paredes[int (posicao[0])][int(posicao[1])+1]) :
        indice = i;
        if(state.getGhostState(indice+1).scaredTimer > 0):
          return True

    return False
    
  def scaredES (self, state):
    posicao = state.getPacmanPosition()
    posicao = (posicao[0],posicao[1]-1)
    lista = state.getGhostPositions()
    paredes = state.getWalls();
    indice = 0
   
    for i in range (len(lista)):
      if lista[i] == posicao or (lista[i] == (posicao[0],posicao[1]-2) and not paredes[int (posicao[0])][int(posicao[1])-1]) :
        indice = i;
        if(state.getGhostState(indice+1).scaredTimer > 0):
          return True

    return False    

  def scaredEE (self, state):
    posicao = state.getPacmanPosition()
    posicao = (posicao[0]+1,posicao[1])
    lista = state.getGhostPositions()
    paredes = state.getWalls();
    
    
    indice = 0
   
    for i in range (len(lista)):
      if lista[i] == posicao or (lista[i] == (posicao[0]+2,posicao[1]) and not paredes[int (posicao[0])+1][int(posicao[1])]) :
        indice = i;
        if(state.getGhostState(indice+1).scaredTimer > 0):
          return True

    return False

  def scaredEO (self, state):
    posicao = state.getPacmanPosition()
    posicao = (posicao[0]-1,posicao[1])
    lista = state.getGhostPositions()
    paredes = state.getWalls();
    
    indice = 0
   
    for i in range (len(lista)):
      if lista[i] == posicao or (lista[i] == (posicao[0]-2,posicao[1]) and not paredes[int (posicao[0])-1][int(posicao[1])]) :
        indice = i;
        if(state.getGhostState(indice+1).scaredTimer > 0):
          return True

    return False  
  
  
  def EN (self, state):
    posicoesFantasmas = state.getGhostPositions()
    posicao = state.getPacmanPosition()
    paredes = state.getWalls();
    
    
    # se existem fantasmas a volta ou se existem fantasmas a duas casas de distancia e a casa a seguir nao e uma parede  
    if ((posicao[0],posicao[1]+1) in posicoesFantasmas or ((posicao[0],posicao[1]+2) in posicoesFantasmas and not paredes[posicao[0]][posicao[1]+1])):
      return True
    return False  
  
  def ES (self, state):
    posicoesFantasmas = state.getGhostPositions()
    posicao = state.getPacmanPosition()
    paredes = state.getWalls();
    
    # se existem fantasmas a volta ou se existem fantasmas a duas casas de distancia e a casa a seguir nao e uma parede  
    if ((posicao[0],posicao[1]-1) in posicoesFantasmas or ((posicao[0],posicao[1]-2) in posicoesFantasmas and not paredes[posicao[0]][posicao[1]-1])):
      return True
    return False    
  
  def EE (self, state):
    posicoesFantasmas = state.getGhostPositions()
    posicao = state.getPacmanPosition()
    paredes = state.getWalls();
        
    # se existem fantasmas a volta ou se existem fantasmas a duas casas de distancia e a casa a seguir nao e uma parede  
    if ((posicao[0]+1,posicao[1]) in posicoesFantasmas or ((posicao[0]+2,posicao[1]) in posicoesFantasmas and not paredes[posicao[0]+1][posicao[1]])):
      return True
    return False       

  def EO (self, state):
    posicoesFantasmas = state.getGhostPositions()
    posicao = state.getPacmanPosition()
    paredes = state.getWalls();
        
    # se existem fantasmas a volta ou se existem fantasmas a duas casas de distancia e a casa a seguir nao e uma parede  
    if ((posicao[0]-1,posicao[1]) in posicoesFantasmas or ((posicao[0]-2,posicao[1]) in posicoesFantasmas and not paredes[posicao[0]-1][posicao[1]])):
      return True
    return False
  
  