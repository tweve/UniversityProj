package  {
	
	// Imports
	import flash.display.MovieClip;
	
	public class Enemy extends MovieClip {
		
		private const jumpSpeed:Number = 1;
		private const moveSpeed :Number = 0.25;
		
		var shoot : Array = new Array(1,2,3,4,5);
		var mc :MovieClip = new EnemyMC();	
		
		// variáveis de estado do herói
		private var walkRight = false;
		private var walkLeft = false;
		private var rollRight = false;
		private var rollLeft = false;
		private var jumpRight = false;
		private var jumpLeft = false;
		private var descending = false;
		private var inAir :Boolean = false;
		private var direct :int = 1;
		private var kickRight = false;
		private var kickLeft = false;
		private var crouchRight = false;
		private var crouchLeft = false;
		private var shootLeft:Boolean = true;
		
		// colisoes laterais
		private var hitWallRight: Boolean = false;
		private var hitWallLeft: Boolean = false;
		
		// velocidade do enemy nos eixos
		private var dx : Number= 0.0;					
		private var dy : Number= 0.0;
		
		// Animações
	
		var animstate:String = "standLeft";
		var animstep:Number = 1;
		var shooting : Array = new Array(1,1,1,1,1,2,2,2,2,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,5);
		
		public function Enemy(){
			// Constructor
			
			mc = new EnemyMC();
			mc.x = 200;
			mc.y = 300;
			mc.stop();
			super.addChild(mc);
		}
		
		private function getShootLeft(){								// devolve ShootLeft, (disparos)
			return this.shootLeft;
		}
		
		public function getMoveRight(){
			return this.walkRight;										// devolve o moveRight utilizado para movimentar em conjunto com class GameEngine
		}
		
		public function getMoveLeft(){
			return this.walkLeft;										// devolve o moveLeft utilizado para movimentar em conjunto com class GameEngine
		}
		
		public function getdy ():Number{								// devolve velocidade no eixo yy
			return dy;
		}
		
		public function getdx ():Number{								// devolve velocidade no eixo xx
			return dy;
		}
		
		public function setdx (dX:Number){								// devolve velocidade no eixo yy
			this.dx = dX;
		}
		
		public function setdy (dY:Number){								// devolve velocidade no eixo xx
			this.dy = dY;
		}
		
		public function setInAir( stt : Boolean){						// set para inAir, utilizado em conjunto com GameEngine para saber se está no ar
			this.inAir = stt;
		}
		
		public function getInAir():Boolean{								// get inAir
			return this.inAir
		}
		
		public function getWalkSpeed():Number{							// devolve a velocidade de movimento do char
			return this.moveSpeed;
		}
		
		public function setX(X:Number){									// coloca a animacao na pos x do eixo dos xx
			this.mc.x= X;
		}
		
		public function setY(Y:Number){									// coloca a animacao na pos Y do eixo dos yy
			this.mc.y = Y;
		}
		
		public function getY():Number{									// devolve a posiçao no eixo dos Y da animacao do char
			return this.mc.y;
		}
		
		public function getX():Number{									// devolve a posiçao no eixo dos X da animacao do char
			return this.mc.x;
		}
		
		public function getJumpLeft():Boolean{							// devolve jumpLeft
			return this.jumpLeft;
		}
		
		public function getJumpRight():Boolean{							// devolve jumpRight
			return this.jumpRight;
		}
		
		public function setHitWallRight( stt : Boolean){				// método set para o atributo hitWallRight utilizado para detectar colisoes em conjunto com class GameEngine
			this.hitWallRight = stt;
		}
		
		public function setHitWallLeft( stt : Boolean){					// método set para o atributo hitWallLeft utilizado para detectar colisoes em conjunto com class GameEngine
			this.hitWallLeft = stt;
		}
		
		public function getWidth():Number{								// devolve a largura da animacao
			return this.width;
		}
		
		public function getHeight():Number{								// devolve a altura da animacao
			return this.height;
		}
		
		public function getAnimstate():String{							// devolve estado da animacao
			return this.animstate;
		}
		
		public function setAnimState(stt:String){						// atribui estado da animacao
			this.animstate = stt;
		}
		
		public function getDirection(){									// devolve direccao
			return this.direct;
		}
		
		public function setDirection(d:int){							// atribui direccao
			this.direct = d;
		}
		
		public function kick(){											// dá pontapé, conforme direccao
			if (this.direct == 1)
				this.kickRight = true;
			else
				this.kickLeft = true;
		}
		
		public function getKickLeft(){									// devolve pontapé esquerda
			return this.kickLeft;
		}
		
		public function getKickRight(){									// devolve pontapé direita
			return this.kickRight;
		}
		
		public function crouch(){										// Baixa , conforme a posição
			
			if (this.direct == 1)
				this.crouchRight = true;
			else
				this.crouchLeft = true;
		}
		
		public function setCrouchLeft(b:Boolean){						// baixa esquerda
			this.crouchLeft = b;
		}
		
		public function setCrouchRight(b:Boolean){						// baia direita
			this.crouchRight = b;
		}
		
		public function getCrouchLeft(){								// devolve baixa esquerda
			return this.crouchLeft;
		}
		
		public function getCrouchRight(){								// devolve baixa direita
			return this.crouchRight;
		}
	}
}
