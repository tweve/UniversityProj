"""
iiaAgents.py

Created by Rui Lopes on 2012-02-18.
Copyright (c) 2012 University of Coimbra. All rights reserved.
"""

from pacman import Directions, SCARED_TIME
from game import Agent
import random
import game
import util

class iiaPacmanAgent(Agent):	
  def getAction(self, state):
    """The agent receives a GameState (defined in pacman.py).
     Simple random choice agent. """
    return random.choice( state.getLegalPacmanActions() )

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
  
def defaultstrategy(agent,state):
  	print state.getLegalActions(agent.index)
	return random.choice( state.getLegalActions(agent.index))
  
