"""
Created by John DeNero et al.
Copyright (c) 2009 University of Berkley. All rights reserved.
http://inst.eecs.berkeley.edu/~cs188/sp09/pacman.html
"""
from game import Agent, Directions
from random import choice
from util import getActionRepresentation, getStateRepresentation
import random

class KeyboardAgent(Agent):
  """
  An agent controlled by the keyboard.
  """
  # NOTE: Arrow keys also work.
  WEST_KEY  = 'a' 
  EAST_KEY  = 'd' 
  NORTH_KEY = 'w' 
  SOUTH_KEY = 's'

  def __init__( self, index = 0 ):
	
	self.lastMove = Directions.STOP
	self.index = index
	self.keys = []
	
  def getAction( self, state):
	from graphicsUtils import keys_waiting
	from graphicsUtils import keys_pressed
	keys = keys_waiting() + keys_pressed()
	if keys != []:
	  self.keys = keys
	
	legal = state.getLegalActions(self.index)
	move = self.getMove(legal)
	
	if move == Directions.STOP:
	  # Try to move in the same direction as before
	  if self.lastMove in legal:
		move = self.lastMove
		
	if move not in legal:
	  move = random.choice(legal)
	  
	self.lastMove = move
	return move

  def getMove(self, legal):
	move = Directions.STOP
	if   (self.WEST_KEY in self.keys or 'Left' in self.keys) and Directions.WEST in legal:  move = Directions.WEST
	if   (self.EAST_KEY in self.keys or 'Right' in self.keys) and Directions.EAST in legal: move = Directions.EAST
	if   (self.NORTH_KEY in self.keys or 'Up' in self.keys) and Directions.NORTH in legal:   move = Directions.NORTH
	if   (self.SOUTH_KEY in self.keys or 'Down' in self.keys) and Directions.SOUTH in legal: move = Directions.SOUTH
	return move
  
class KeyboardAgent2(KeyboardAgent):
  """
  A second agent controlled by the keyboard.
  """
  # NOTE: Arrow keys also work.
  WEST_KEY  = 'j' 
  EAST_KEY  = "l" 
  NORTH_KEY = 'i' 
  SOUTH_KEY = 'k'
  
  def getMove(self, legal):
	move = Directions.STOP
	if   (self.WEST_KEY in self.keys) and Directions.WEST in legal:  move = Directions.WEST
	if   (self.EAST_KEY in self.keys) and Directions.EAST in legal: move = Directions.EAST
	if   (self.NORTH_KEY in self.keys) and Directions.NORTH in legal:   move = Directions.NORTH
	if   (self.SOUTH_KEY in self.keys) and Directions.SOUTH in legal: move = Directions.SOUTH
	return move
  

class LearningKeyboardAgent(Agent):
  """
  """
  # NOTE: Arrow keys also work.
  WEST_KEY  = 'a' 
  EAST_KEY  = 'd' 
  NORTH_KEY = 'w' 
  SOUTH_KEY = 's'
  STOP_KEY = 'space'

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
	
  def getAction( self, state):
	from graphicsUtils import wait_for_keys
	legal = state.getLegalActions(self.index)
	stateRepresentation = getStateRepresentation(state)
	print "State Representation:", stateRepresentation
	move = None
	while move == None:
		self.keys = wait_for_keys()
		move = self.getMove(legal)
		if move == None:
			print "Illegal move. Try again"
	
	actionRepresentation = getActionRepresentation(move)
	print "Action Representation:", actionRepresentation
	self.saveTraining([stateRepresentation, actionRepresentation])
	return move

  def getMove(self, legal):
	move = None
	if(self.WEST_KEY in self.keys or 'Left' in self.keys) and Directions.WEST in legal:  
		move = Directions.WEST
	elif(self.EAST_KEY in self.keys or 'Right' in self.keys) and Directions.EAST in legal: 
		move = Directions.EAST
	elif(self.NORTH_KEY in self.keys or 'Up' in self.keys) and Directions.NORTH in legal:
		move = Directions.NORTH
	elif(self.SOUTH_KEY in self.keys or 'Down' in self.keys) and Directions.SOUTH in legal:
		move = Directions.SOUTH
	elif(self.STOP_KEY in self.keys) and Directions.STOP in legal:
		move = Directions.STOP
	return move
