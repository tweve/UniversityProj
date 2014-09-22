package {
	
	import flash.display.*;
	import flash.events.*;
	
	public class Hero extends MovieClip{
		
		var mc : MovieClip; 					// animacao do heroi
		
		private var jumpSpeed : Number = 1;		// constante salto
		private var moveSpeed : Number = 0.25;	// constante velocidade de movimento
		
		// variaveis de estado
		private var dead : Boolean = false;				// morto			
		private var walkRight : Boolean = false;		// andar direita	 
		private var walkLeft : Boolean = false;			// andar esquerda
		private var jumpRight : Boolean = false;		// saltar direita
		private var jumpLeft : Boolean = false;			// saltar esquerda
		private var inAir : Boolean = false;    		// no ar
		private var direct : int = 1;           		// direccao que esta virado
		private var kickRight : Boolean = false;        // pontape direita
		private var kickLeft : Boolean = false;         // pontape esquerda
		private var crouchRight : Boolean = false;		// agachar direita
		private var crouchLeft : Boolean = false;		// agachar esquerda
		private var hitWallRight: Boolean = false;  	// colisao lateral direita
		private var hitWallLeft: Boolean = false;		// colisao lateral esquerda	
		
		private var dx : Number = 0.0;		// velocidade do heroi nos eixos			
		private var dy : Number = 0.0;
		
		// limites da animacao do heroi
		private var leftside : Number;		// limite lado esquerdo	
		private var rightside : Number;		// limite lado direito
		private var topside: Number;		// limite cima
		private var bottomside: Number;     // limite baixo
		
		private var startx;					// posicao inicial do heroi 
		private var starty;					// para reposicao em caso de morte
			
		var animstate:String = "standRight";	// Animations
		var animstep:Number = 1;
		var walkRightAnimation : Array = new Array(1,2,3,4,5);
		var walkLeftAnimation : Array = new Array(6,7,8,9,10);
		var kickLeftAnimation : Array = new Array(30,30,30,30,30);
		var kickRightAnimation : Array = new Array(29,29,29,29,29);
		var crouchLeftAnimation : Array = new Array(32,32,32,32,32);
		var crouchRightAnimation : Array = new Array(31,31,31,31,31);
		
		public function Hero()			// construtor
		{
			mc = new Boneco();			
			mc.stop();	//parar animacao na frame 1
			mc.x = 200;					// colocacao da personagem
			mc.y = 400;
			startx = mc.x;				// guardar posicao inicial para reposicao em caso de morte
			starty = mc.y;
			super.addChild(mc);
		}

		public function moveRight():void	// mover para direita
		{				
			this.walkRight = true;
		}
		
		public function stopMoveRight()		// pára de mover para direita
		{				
			this.walkRight = false;
		}
		
		public function moveLeft():void		// mover para esquerda
		{				
			this.walkLeft = true;
		}
		
		public function stopMoveLeft()		// pára de mover para esquerda
		{					
			this.walkLeft = false;
		}
		
		public function jump():void			// funcao para saltar
		{
			if (this.walkRight || this.direct==1){		// está virado para a direita
				this.jumpRight = true;					// salta para a direita
			}

			else if (this.walkLeft || this.direct==-1)  // está virado para a esquerda
				this.jumpLeft = true;					// salta para a esquerda
		}
		
		public function removeMC()			// Retira o MC do heroi
		{										
			super.removeChild(mc);
		}
		
		public function setSuper()			// Transforma heroi em super heroi
		{						
			var Cx, Cy : int;
			Cx = mc.x;
			Cy = mc.y;
			mc = new SuperBoneco();
			mc.stop();
			mc.x = Cx;
			mc.y = Cy;
			super.addChild (mc);
			jumpSpeed = 1.2;
			moveSpeed = 0.35;
		}
		
		public function setNormal(){		// Transforma super heroi em heroi
			var Cx, Cy : int;
			Cx = mc.x;
			Cy = mc.y;
			mc = new Boneco();
			mc.stop();
			mc.x = Cx;
			mc.y = Cy;
			super.addChild (mc);
			jumpSpeed = 1;
			moveSpeed = 0.25;
		}
		
		public function getMoveRight(){
			return this.walkRight;										// devolve o moveRight utilizado para movimentar em conjunto com class GameEngine
		}
		
		public function getMoveLeft(){
			return this.walkLeft;										// devolve o moveLeft utilizado para movimentar em conjunto com class GameEngine
		}
		
		public function setHitWallRight( stt : Boolean){				// método set para o atributo hitWallRight utilizado para detectar colisoes em conjunto com class GameEngine
			this.hitWallRight = stt;
		}
		
		public function setHitWallLeft( stt : Boolean){					// método set para o atributo hitWallLeft utilizado para detectar colisoes em conjunto com class GameEngine
			this.hitWallLeft = stt;
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
			return mc.y;
		}
		
		public function getX():Number{									// devolve a posiçao no eixo dos X da animacao do char
			return mc.x;
		}
		
		public function getWidth():Number{								// devolve a largura da animacao
			return mc.width;
		}
		
		public function getHeight():Number{								// devolve a altura da animacao
			return mc.height;
		}
		
		public function getAnimstate():String{							// devolve pos da animacao
			return this.animstate;
		}
		
		public function setAnimState(stt:String){						// coloca a animacao na pos stff
			this.animstate = stt;
		}
		
		public function getDirection(){									// devolve direccao
			return this.direct;
		}
		
		public function setDirection(d:int){							// coloca direccao
			this.direct = d;
		}
		
		public function getJumpLeft():Boolean{							// devolve estado de salto
			return this.jumpLeft;
		}
		
		public function getJumpRight():Boolean{							// devolve estado de salto
			return this.jumpRight;
		}
		
		public function setJumpLeft(stt :Boolean){						// atribui salto esquerda
			this.jumpLeft = stt;
		}
		
		public function setJumpRight(stt :Boolean){						// atribui salto direita
			this.jumpRight = stt;
		}
		
		public function getJumpSpeed():Number{							// devolve velocidade salto
			return this.jumpSpeed;
		}
		
		public function kick(){											// ordena pontapé
			
			if (this.direct == 1)
				this.kickRight = true;
			else
				this.kickLeft = true;
		}
		
		public function setKickLeft(b:Boolean){							// Ordena Pontapé esquerda
			this.kickLeft = b;
		}
		
		public function setKickRight(b:Boolean){						// Ordena pontapé direta
			this.kickRight = b;
		}
		
		public function getKickLeft(){									// Devolve pontapé esquerda
			return this.kickLeft;
		}
		
		public function getKickRight(){									// Devolve pontapé direita
			return this.kickRight;
		}
		
		public function crouch(){										// Set para baixar
			
			if (this.direct == 1)
				this.crouchRight = true;
			else
				this.crouchLeft = true;
		}
		
		public function setCrouchLeft(b:Boolean){						// Crouch esquerda
			this.crouchLeft = b;
		}
		
		public function setCrouchRight(b:Boolean){						// Crouch direita
			this.crouchRight = b;
		}
		
		public function getCrouchLeft(){								// Devolve estado de crouch Left
			return this.crouchLeft;
		}
		
		public function getCrouchRight(){								// Devolve Estado de crouch right
			return this.crouchRight;
		}
		
		public function setDead(b:Boolean){		// Define heroi como morto
			this.dead = b;
		}
		
		public function getDead():Boolean{		// Verifica se heroi esta morto
			return this.dead;
		}
	}
}
