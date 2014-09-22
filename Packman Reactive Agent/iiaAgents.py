"""
iiaAgents.py

Created by Rui Lopes on 2012-02-18.
Copyright (c) 2012 University of Coimbra. All rights reserved.
"""

from pacman import Directions
from game import Agent
import random
import game
import util

class iiaPacmanAgent(Agent):	
  
  mem = [0]
  
      
  def getAction(self, state):
    
    if (self.EN(state) and self.scaredEN(state)):
      return ('North')
    if (self.ES(state) and self.scaredES(state)):
      return ('South')
    if (self.EE(state) and self.scaredEE(state)):
      return ('East')
    if (self.EO(state) and self.scaredEO(state)):
      return ('West')
    
    
    if (self.LO(state) and self.CO(state) and not(self.EO(state))):
      return ('West')
    if (self.LS(state) and self.CS(state) and not(self.ES(state))):
      return ('South')
    if (self.LN(state) and self.CN(state) and not(self.EN(state))):
      return ('North')    
    if (self.LE(state) and self.CE(state) and not(self.EE(state))):
      return ('East')    
    
    
    
    if (self.EN(state) and self.LE(state) and not(self.EE(state))):
      return ('East')
    if (self.EN(state) and self.LO(state) and not(self.EO(state))):
      return ('West')
    if (self.EN(state) and self.LS(state) and not(self.ES(state))):
      return ('South')         

    if (self.ES(state) and self.LE(state) and not(self.EE(state))):
      return ('East')
    if (self.ES(state) and self.LO(state) and not(self.EO(state))):
      return ('West')
    if (self.ES(state) and self.LN(state) and not(self.EN(state))):
      return ('North')      

    if (self.EE(state) and self.LN(state) and not(self.EN(state))):
      return ('North')
    if (self.EE(state) and self.LS(state) and not(self.ES(state))):
      return ('South')
    if (self.EE(state) and self.LO(state) and not(self.EO(state))):
      return ('West')
    
    if (self.EO(state) and self.LN(state) and not(self.EN(state))):
      return ('North')
    if (self.EO(state) and self.LS(state) and not(self.ES(state))):
      return ('South')
    if (self.EO(state) and self.LE(state) and not(self.EE(state))):
      return ('East')    
    
    
    lista = state.getLegalPacmanActions()
    lista.remove('Stop')
    
    # se o tamanho da lista e 3 o pacman esta num corredor (stop+ 2 direcoes)
    if (len(lista)==2):
    #print lista
      if(self.mem[0]==1 and self.LN(state) and not(self.EN(state))):    
        return 'North'
      if(self.mem[0]==2 and self.LS(state) and not(self.ES(state))):    
        return 'South'    
      if(self.mem[0]==3 and self.LE(state) and not(self.EE(state))):    
        return 'East'  
      if(self.mem[0]==4 and self.LO(state) and not(self.EO(state))):    
        return 'West'

    """if (len(lista) > 1):      
      if (self.mem[0] == 2 and 'North' in lista): lista.remove('North')
      if (self.mem[0] == 1 and 'South' in lista): lista.remove('South')
      if (self.mem[0] == 4 and 'East' in lista): lista.remove('East')
      if (self.mem[0] == 3 and 'West' in lista): lista.remove('West')"""

    if (len(lista) > 1):      
      if (self.mem[0] == 2 and self.LN(state)): lista.remove('North')
      if (self.mem[0] == 1 and self.LS(state)): lista.remove('South')
      if (self.mem[0] == 4 and self.LE(state)): lista.remove('East')
      if (self.mem[0] == 3 and self.LO(state)): lista.remove('West')
    
    dir = random.choice(lista);
      
    if dir == 'North':
      self.mem[0] =1
    if dir == 'South':
      self.mem[0] =2     
    if dir == 'East':
      self.mem[0] =3    
    if dir == 'West':
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
 
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

class iiaGhostAgent(Agent):	 
  
  """Uses a strategy pattern to allow usage of different ghost behaviors in the game. 
  The strategy must receive an agent and a GameState as the arguments.
  To set the strategy through command line use:
  >>>pacman.py -g iiaGhostAgent --ghostArgs fnStrategy='fun1[;fun*]'
  You may add new arguments as long as you provide a proper constructor. """
  
  def __init__(self, index, fnStrategy='defaultstrategy'):
    self.index=index
    strategies = fnStrategy.split(';')
    try:
      self.strategy = util.lookup(strategies[index%len(strategies)], globals())
    except:
      print "Function "+strategies[index%len(strategies)]+" not defined!"
      print "Loading defaultstrategy..."
      self.strategy = defaultstrategy
 
  def getAction(self, state):
    """The agent receives a GameState (defined in pacman.py).
     Simple random ghost agent."""
    return self.strategy( self, state )
  
  
  # sensores para os fantasmas detectarem o pacman
  
  def PN (self, state, indice):
    posPacman = state.getPacmanPosition()
    posicao = state.getGhostPosition(indice)
    paredes = state.getWalls();
    # se o pacman esta +1 Norte ou +2 Norte e a casa em +1 Norte nao e uma parede  
    if ((posicao[0],posicao[1]+1) == posPacman or ((posicao[0],posicao[1]+2) == posPacman and not paredes[int(posicao[0])][int(posicao[1])+1])):
      return True
    return False
  
  def PS (self, state, indice):
    posPacman = state.getPacmanPosition()
    posicao = state.getGhostPosition(indice)
    paredes = state.getWalls();
    
    # se o pacman esta -1 Sul ou -2 Sul e a casa em -1 Sul nao e uma parede  
    if ((posicao[0],posicao[1]-1) == posPacman or ((posicao[0],posicao[1]-2) == posPacman and not paredes[int (posicao[0])][int(posicao[1])-1])):
      return True
    return False  
  
  def PE (self, state, indice):
    posPacman = state.getPacmanPosition()
    posicao = state.getGhostPosition(indice)
    paredes = state.getWalls();
    
    # se o pacman esta +1 Este ou +2 Este e a casa em +1 Este nao e uma parede  
    if ((posicao[0]+1,posicao[1]) == posPacman or ((posicao[0]+2,posicao[1]) == posPacman and not paredes[int(posicao[0])+1][int(posicao[1])])):
      return True
    return False  
  
  def PO (self, state, indice):
    posPacman = state.getPacmanPosition()
    posicao = state.getGhostPosition(indice)
    paredes = state.getWalls();
    
    # se o pacman esta -1 Oeste ou -2 Oeste e a casa em -1 Oeste nao e uma parede  
    if ((posicao[0]-1,posicao[1]) == posPacman or ((posicao[0]-2,posicao[1]) == posPacman and not paredes[int(posicao[0])-1][int(posicao[1])])):
      return True
    return False  
  
  # Sensores para verificar se existe comida a volta dos fantasmas
  def Comida (self, direction, state, indice):
      posicao = state.getGhostPosition(indice)
      comida = state.getFood()
      
      if (direction == "North" and comida[int(posicao[0])][int(posicao[1])+1]):
            return True
      if (direction == "South" and comida[int(posicao[0])][int(posicao[1])-1]):
            return True
      if (direction == "East" and comida[int(posicao[0])+1][int(posicao[1])]):
            return True                
      if (direction == "West" and comida[int(posicao[0])-1][int(posicao[1])]):
            return True                      
      return False    
  
  
def s1(agent, state):
  
  # Destemido - vai contra o pacman
  
  scared = state.getGhostState(agent.index).scaredTimer
  
  if (agent.PN(state, agent.index) and 'North' in state.getLegalActions(agent.index) and scared == 0):
    return ('North')  
  
  if (agent.PS(state, agent.index) and 'South' in state.getLegalActions(agent.index) and scared == 0):
    return ('South')   

  if (agent.PE(state, agent.index) and 'East' in state.getLegalActions(agent.index) and scared == 0):
    return ('East')   
    
  if (agent.PO(state, agent.index) and 'West' in state.getLegalActions(agent.index) and scared == 0):
    return ('West')       
  
  # Pacman a Norte
  if (agent.PN(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')  
  
  if (agent.PN(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West')   

  if (agent.PN(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   
  
  # Pacman a Sul
  if (agent.PS(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')  
   
  if (agent.PS(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West')   

  if (agent.PS(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')        
 
  # Pacman a Este 
  if (agent.PE(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')  
    
  if (agent.PE(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   

  if (agent.PE(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West') 
  
  # Pacman a Oeste  
  if (agent.PO(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')  
      
  if (agent.PO(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   

  if (agent.PO(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')  
 
  return random.choice( state.getLegalActions(agent.index));

  
def s2(agent, state):
  
  # Medroso - foge do pacman independentemente de estar scared ou nao
  
  scared = state.getGhostState(agent.index).scaredTimer
  
  # Pacman a Norte
  if (agent.PN(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')  
  
  if (agent.PN(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West')   

  if (agent.PN(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   
  
  # Pacman a Sul
  if (agent.PS(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')  
   
  if (agent.PS(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West')   

  if (agent.PS(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')        
 
  # Pacman a Este 
  if (agent.PE(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')  
    
  if (agent.PE(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   

  if (agent.PE(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West') 
  
  # Pacman a Oeste
  if (agent.PO(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')  
      
  if (agent.PO(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   

  if (agent.PO(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East') 
 
  return random.choice( state.getLegalActions(agent.index));

def s3(agent, state):

  # Fantasma Ganancioso
  # Fantasma foge do Pacman quando esta amedrontado
  # Caso contrario Da preferencia aos caminhos que ainda teem comida

  # Pacman a Norte
  if (agent.PN(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')  
  
  if (agent.PN(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West')   

  if (agent.PN(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   
  
  # Pacman a Sul
  if (agent.PS(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')  
   
  if (agent.PS(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West')   

  if (agent.PS(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')        
 
  # Pacman a Este 
  if (agent.PE(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')  
    
  if (agent.PE(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   

  if (agent.PE(state, agent.index) and 'West' in state.getLegalActions(agent.index)):
    return ('West') 
  
  # Pacman a Oeste
  if (agent.PO(state, agent.index) and 'North' in state.getLegalActions(agent.index)):
    return ('North')  

  if (agent.PO(state, agent.index) and 'South' in state.getLegalActions(agent.index)):
    return ('South')   

  if (agent.PO(state, agent.index) and 'East' in state.getLegalActions(agent.index)):
    return ('East')
	
  lista = state.getLegalActions(agent.index)
  for i in range(len(lista)):
    if (agent.Comida(lista[i], state, agent.index)):
      return lista[i]
  return random.choice( state.getLegalActions(agent.index))


def defaultstrategy(agent,state):
  return random.choice( state.getLegalActions(agent.index))

  
