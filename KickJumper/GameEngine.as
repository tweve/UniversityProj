package {
	
	// Imports
	import flash.display.*;
	import flash.display.Stage;
	import flash.utils.*;
	import flash.events.*;
	import flash.display.Loader;
	import flash.net.URLRequest;
	import flash.net.URLLoader;
	import flash.system.*;
	import flash.media.Sound;
	import flash.ui.Keyboard;
	import flash.utils.getTimer;
	import flash.sampler.NewObjectSample;
	import flash.text.TextFormat;
	import flash.utils.Timer;
	import flash.text.*;
	import flashx.textLayout.accessibility.TextAccImpl;
	import flash.net.SharedObject;

	public class GameEngine extends MovieClip
	{
		// Stage
		private var STG : Stage;								// referência para o stage principal
		
		// Chars
		private var hero : Hero;								// variável do tipo heroi que representa o char controlado pelo jogador
		private var enemies:Array= new Array();					// Array que conterá os enimigos
		
		//HUD
		private var hud : HUD;									// variável que contém o HUD (mostrará as vidas)
		
		// Constantes
		static const gravity :Number = .004;					// constante de gravidade
		static const edgeDistance:Number = 300;					// contante de distancia entre o heroi e os limites do stage para fazer scrolling
		static const edgeDistancey:Number = 200;
		
		// Objectos do stage
		private var fixedObjects:Array = new Array();					// objectos fixos no mapa (que o heroi pode embater)			
		private var bullets:Array = new Array();						// Array de disparos inimigos
		private var bonusList:Array = new Array();						// outros objectos (power ups etc)
		var gameMask :Sprite = new Sprite();							// mascara de jogo (limita o tamanho do jogo visivel)
		var buttonBackToMenu: SetaAvancar = new SetaAvancar();			// botao que retorna ao menu principal
		var time : Timer= new Timer(1000.0,0);							// tempo de jogo
		var timeText : TextField=new TextField;							// texto escrito na stage, do tempo
		var textFormat : TextFormat = new TextFormat();					// formataçao do texto
		var insert:TextField = new TextField();							// recebe a introduçao do nome do jogador
		var loadText :TextField = new TextField();						// texto que aparece durante a espera do carregamento dos objectos para o jogo
		
		var limite :LimiteLateral = new LimiteLateral();				// limite lateral, bloqueia a passagem do heroi para a esquerda
		var bottomObstacle : MovieClip;									// limite inferior, referencia para o obstaculo inferior
		
		//musica tocada durante o nivel
		var musicLevel: Music= new Music("Sounds/1.mp3");				// musica tocada durante o nivel
		var jumpSound: Music=new Music ("Sounds/jumpYa.mp3");			// som de salto
		var dieHero: Music= new Music("Sounds/dieHero.mp3");			// som da morte do heroi
		var dieEnemy: Music= new Music("Sounds/dieRobot.mp3");			// som da morte do robot
		var laserGun: Music = new Music("Sounds/laserGun.mp3");			// som disparos
		var lifeUp : Music = new Music("Sounds/lifeUp.mp3");			// som vida up
		var scoreUp : Music = new Music("Sounds/scoreUp.mp3");			// som score
		var powerUp : Music = new Music("Sounds/powerUp.mp3");			// som power
		var laserGunSound : Music = new Music("Sounds/laserGun.mp3");		// som das pistolas

		var musicOn : Boolean = true;									// variável que permite activar desativar musica do nivel
		var soundsOn :Boolean = true;									// variavel que permite activar ou desactivar todos os sons
		var buttonSound: SimpleButton;
		var buttonMusic: SimpleButton;
		
		var deadTimer:Timer;											// tempo de regeneraçao
		
		var probHealth:Number;											// probabilidade de obter health
		var probScore:Number;											// probabilidade de obter BonusScore
		var probSuper:Number;											// probabilidade de obter SuperMode
		var superMode: Boolean=false;									// variavel que verifica se o heroi esta em modo super 
		
		var txt:Text;													// variavel que mostra mensagens durante o jogo
		var userName: String;											// nome do jogador
		
		
		// Variáveis de Jogo
		private var playerObjects:Array;								// inventário de objectos obtidos pelo utilizador
		private var gameMode:String = "start";							// estado do jogo
		private var gameScore:int;										// pontuação
		private var playerLives:int;									// vidas
		private var lastTime:Number = 0;								// tempo decorrido desde o ultimo frame
		private var level:uint = 1;										// numero 
		private var previousPlatform:uint = 0;							// plataforma anterior
		private var platform:uint;										// plataforma actual
		
		// Variáveis auxiliares
		private var main:Main;											// ponteiro que da acesso
		private var oldPos:int;
		private var oldPos2:int;

		function GameEngine(stg: Stage, mainPTR: Main){
			
			STG = stg;									// atribuiçao do stage
			hud = new HUD(STG);							// cria HUD
			hero = new Hero();							// criação de herói
			
			STG.focus = STG;							// atribui focus ao stage
			
			playerObjects = new Array();				// cria array de objectos
			gameScore = 0;								// inicia pontuacao a zero
			gameMode = "username";						// estado de jogo - a pedir nome do jogador
			playerLives = 2;							// inicia o jogo com 2 vidas

			// botoes de controlo do som
			buttonSound = new BotaoSom();					
			buttonMusic = new BotaoMusica();
			
			main = mainPTR;								// e atribuido a variavel main, o ponteiro para a main do jogo, de modo a podermos usar funçoes dessa classe
			
			// ao clicarmos na seta branca, o jogo volta para o menu principal
			STG.addChild(buttonBackToMenu);
			buttonBackToMenu.x=5;
			buttonBackToMenu.y=530;
			buttonBackToMenu.addEventListener(MouseEvent.MOUSE_UP, function(){backToMenu()});		// listener da seta
				
			createWorld();								// inicia o nivel
						
			STG.addChild(buttonSound);
			buttonSound.x=725;
			buttonSound.y=71;
			STG.addChild(buttonMusic);
			buttonMusic.x=750;
			buttonMusic.y=20;
			
			// inicia a mascara, de modo a que nao seja visivel obejctos fora do gameplay
			gameMask.graphics.beginFill(0x00FF00);
			gameMask.graphics.drawRect(0, 0, 800, 600);
			gameMask.x = 0;
			gameMask.y = 0;
			STG.addChild(gameMask);
			this.mask = gameMask;
			
			STG.addEventListener(Event.ENTER_FRAME, gameLoop);				// listener a cada frame passada do jogo, chama o metodo gameLoop
			STG.addEventListener(KeyboardEvent.KEY_DOWN, keyDownFunc);		// listeners para quando é pressionada ou largada um tecla
			STG.addEventListener(KeyboardEvent.KEY_UP, keyUpFunc);
			
			buttonMusic.addEventListener(MouseEvent.MOUSE_UP, mute);		// listener para o icone da musica, silencia/activa a musica de fundo do nivel
			buttonSound.addEventListener(MouseEvent.MOUSE_UP, muteAllSounds);	// listener para o icone de som, silencia/activa todos os sons do nivel			
			
			musicLevel.getChannel().addEventListener(Event.SOUND_COMPLETE, function(e : int = 0){musicLevel.unmute(e)});		//quando acabar a musica, volta a repetir
			
			STG.addEventListener(MouseEvent.MOUSE_WHEEL, musicVolume);		// listener para o scroll do rato, baixa/aumenta o volume, consoante o scroll para baixo/cima
			
			insertName();													//chama o metodo insertName onde é pedido o nome do jogador
		}
				
		public function insertName()
		{
			var nameFormat: TextFormat=new TextFormat();			// variavel que contem o formato dos caracteres inseridos pelo jogado ao escrever o seu nome
			
			// apresenta a imagem "NOME" no ecra 
			var bck : Loader = new Loader();
			bck.contentLoaderInfo.addEventListener(Event.INIT, loadToTop);
			bck.load(new URLRequest("Backgrounds/userName.png"));
			bck.x = 220;
			bck.y = 250;
			
			// formataçao da variavel
			nameFormat.font = "Arial";
			nameFormat.color = 0xFFFFFF;
			nameFormat.size = 30;
			nameFormat.bold = true;
			
			// atribuiçao da formataçao e inicializaçao da variavel de captura de texto
			insert.type = TextFieldType.INPUT;
			insert.textColor =  0xFFFFFF;
			insert.border = true;
			insert.borderColor=0xFFFFFF;
			insert.height=40;
			insert.width=150;
			insert.defaultTextFormat = nameFormat;
			insert.x = 300;
			insert.y = 350;
			
			insert.addEventListener(Event.CHANGE, captureName);				// sempre que é inserido/modificado um caracter, é guardado na variavel insertName
			insert.addEventListener(KeyboardEvent.KEY_DOWN, startGame)		// quando pressionada uma tecla
			
			STG.focus = this.insert;										// faz focus ao insert, de modo ao jogador poder escrever mal apareça o rectangulo, sem ter que clicar dentro dele
			
			// adiciona o insert ao stage na posiçao 0
			STG.addChild(insert)
			STG.setChildIndex(insert,0);
		
		
		}
		
		// guarda os caracteres inseridos na variavel userName
		public function captureName(e:Event)
		{
			userName = insert.text;
		}

		public function startGame(e :KeyboardEvent)
		{
			// caso o jogador pressione a tecla ENTER quando lhe é pedido nome, o jogo é iniciado
			if (e.keyCode == Keyboard.ENTER){
				STG.removeChildAt(STG.numChildren-3);
				
				timePlay();				// é iniciado o tempo do jogo
				
				// remove os listeners e a caixa de texto criados para a introduçao do nome do jogador
				insert.removeEventListener(TextEvent.TEXT_INPUT, captureName);
				insert.removeEventListener(KeyboardEvent.KEY_DOWN, startGame);
				STG.removeChild(insert);
				
				gameMode = "play";		// jogo passa a estar em modo PLAY
				STG.focus = this;		// é feito focus ao stage
				STG.stageFocusRect = false;		// remove os rectangulos amarelos que aparecem ecra ao executar o focus
			}
		}
		
		//cria nivel
		public function createWorld()
		{
			if (level == 1){
				
				// estas probabilidades vao sofrendo alteraçoes ao avançar de nivel
				probHealth = 0.01;							// probabilidade de obter mais uma vida
				probScore=0.1;								// probabilidade de obter mais 100 pontos de bonus
				probSuper=0.01;								// probabilidade de ficar num modo mais forte (maior velocidade e altura no salto)
		
				// reproduz a musica para o nivel, caso nao esteja silenciada
				if (musicOn)
					musicPlay(level);
				
				// cria espinhos
				var spikes : MovieClip = new Spikes();
				spikes.y = 605;
				spikes.x = -3;
				spikes.topside = spikes.y;
				addChild(spikes);
				
				// Cria 100 plataformas e dispoe-as no moviclip de fundo
				var i : uint = 0;
				for (i=0;i<100;i++){
					
					var plataformaTemp : MovieClip = new PlataformaSelva(i+1);		// cria plataforma correspondente ao nivel, cada uma com o seu ID
					// a primeira plataforma é a unica fixa
					if (i == 0){
						plataformaTemp.x = 150;
						plataformaTemp.y = 500;
					}
					// as restantes sao criadas aleatoreamente (tanto em posiçao como em largura)
					else {
						plataformaTemp.x = fixedObjects[i-1].rightside + randomNumber(40,120);
						plataformaTemp.y = fixedObjects[i-1].topside + randomNumber(-100,80);
						plataformaTemp.scaleX = Math.random()+0.5;
					}
					
					// protecçao, caso as plataformas estejam a ser criadas abaixo dos picos, aumenta a altura onde vao ser colocadas
					if (plataformaTemp.y+plataformaTemp.height > spikes.topside)
						plataformaTemp.y = spikes.topside- plataformaTemp.height-50;
					
					// actualiza os limites das plataformas, serao mais tarde usados para verificar as colisoes
					plataformaTemp.leftside = plataformaTemp.x;
					plataformaTemp.rightside = plataformaTemp.x + plataformaTemp.width;
					plataformaTemp.topside = plataformaTemp.y;
					plataformaTemp.bottomside = plataformaTemp.y+plataformaTemp.height;
					
					// calcula a probabilidade de estar um inimigo numa plataforma (essa probabilidade vai aumentado consoante os niveis)
					var rand:Number = Math.random();
					if (rand < 0.2 && plataformaTemp.leftside>450){					// nas primeiras 2 ou 3 plataformas (dependendo da largura), nao aparecem inimigos
						var enemy :Enemy = new Enemy();
						enemy.setX(plataformaTemp.rightside-enemy.width);
						enemy.setY(plataformaTemp.topside-50);
						enemies.push(enemy);
						this.addChild(enemy);
					}
					
					// cria a plataforma no movieClip e adiciona-a ao Array fixedObjects
					this.addChild(plataformaTemp);
					fixedObjects.push(plataformaTemp);
				}
				
				// cria limite invisivel para colidir quando o heroi embate na esquerda
				limite.x = -46;
				limite.leftside = limite.x;
				limite.rightside = limite.x + limite.width;
				limite.topside = limite.y;
				limite.bottomside = limite.y+limite.height;
				fixedObjects.push(limite);
			
				// declaramos os espinhos como sendo o nosso limite inferior
				bottomObstacle = spikes;
			
				// actualiza os limites  dos spikes
				spikes.leftside = spikes.x;
				spikes.rightside = spikes.x + spikes.width;
				spikes.topside = spikes.y;
				spikes.bottomside = spikes.y+spikes.height;
				
				// Carrega a imagem de fundo
				var bck : Loader = new Loader();
				bck.contentLoaderInfo.addEventListener(Event.INIT, loadToBackground);
				
				//// entra num estado de espera (loading), enquanto carrega os objectos para o jogo
				loadText.text =  "Loading...";
				loadText.width=300;
				loadText.x = 540;
				loadText.y = 500;
				
				textFormat.font = "Arial";
				textFormat.color = 0xFFFFFF;
				textFormat.size = 50;
				textFormat.bold = true;
				
				loadText.setTextFormat(textFormat)
				
				addChild(loadText);
				gameMode = "awaiting";
				////
				
				bck.load(new URLRequest("Backgrounds/1.png"));
				
				
			}
			else if (level == 2){
				
				probHealth = 0.01;								// probabilidade de obter health
				probScore=0.1;									// probabilidade de obter BonusScore
				probSuper=0.005;								// probabilidade de ficar num modo mais forte (maior velocidade e altura no salto)
				
				// reproduz a musica para o nivel
				if (musicOn)
					musicPlay(level);
				
				// cria lava
				spikes = new lavaSprite();
				spikes.y = 575;
				spikes.x = -3;
				spikes.topside = spikes.y;
				addChild(spikes);
				
				//Cria 100 plataformas e dispoe-as no moviclip de fundo
				i = 0;
				for (i=0;i<100;i++){
					
					plataformaTemp = new PlataformaLava(i+1);
					if (i == 0){
						plataformaTemp.x = 150;
						plataformaTemp.y = 500;
					}
					else {
						plataformaTemp.x = fixedObjects[i-1].rightside + randomNumber(50,120);
						plataformaTemp.y = fixedObjects[i-1].topside + randomNumber(-100,10);
						plataformaTemp.scaleX = Math.random()+0.5;
					}
					
					if (plataformaTemp.y+plataformaTemp.height > spikes.topside)
						plataformaTemp.y = spikes.topside- plataformaTemp.height;
					
					plataformaTemp.leftside = plataformaTemp.x;
					plataformaTemp.rightside = plataformaTemp.x + plataformaTemp.width;
					plataformaTemp.topside = plataformaTemp.y;
					plataformaTemp.bottomside = plataformaTemp.y+plataformaTemp.height;
					
					
					rand= Math.random();
					if (rand < 0.3 && plataformaTemp.leftside>300){					// nas primeiras 1 ou 2 plataformas (dependendo da largura), nao aparecem inimigos
						enemy = new Enemy();
						enemy.setX(plataformaTemp.rightside-enemy.width);
						enemy.setY(plataformaTemp.topside-50);
						enemies.push(enemy);
						this.addChild(enemy);
					}
					
					this.addChild(plataformaTemp);
					fixedObjects.push(plataformaTemp);
					
				}
				
				// cria limite invisivel para colidir quando o heroi embate na esquerda
				limite.x = -46;
				limite.leftside = limite.x;
				limite.rightside = limite.x + limite.width;
				limite.topside = limite.y;
				limite.bottomside = limite.y+limite.height;
				fixedObjects.push(limite);
			
				// declaramos os a lava como sendo o nosso limite inferior
				bottomObstacle = spikes;
			
				// actualiza os limites  dos spikes
				spikes.leftside = spikes.x;
				spikes.rightside = spikes.x + spikes.width;
				spikes.topside = spikes.y;
				spikes.bottomside = spikes.y+spikes.height;
				
				// Carrega a imagem de fundo
				bck = new Loader();
				bck.contentLoaderInfo.addEventListener(Event.INIT, loadToBackground);
				
				/////
				loadText.text =  "Loading...";
				loadText.width=300;
				loadText.x = 540;
				loadText.y = 500;
				
				textFormat.font = "Arial";
				textFormat.color = 0xFFFFFF;
				textFormat.size = 50;
				textFormat.bold = true;
				
				loadText.setTextFormat(textFormat)
				
				addChild(loadText);
				
				gameMode = "awaiting";
				////
				
				bck.load(new URLRequest("Backgrounds/2.png"));
			}
			else if (level == 3){
				
				probHealth = 0.01;								// probabilidade de obter health
				probScore=0.1;									// probabilidade de obter BonusScore
				probSuper=0.005;
				
				// reproduz a musica para o nivel
				if (musicOn)
					musicPlay(level);
				
				//cria picos de gelo
				spikes = new iceSpikes();
				spikes.y = 570;
				spikes.x = -3;
				spikes.topside = spikes.y;
				addChild(spikes);
				
				//Cria 100 plataformas e dispoe-as no moviclip de fundo
				i = 0;
				for (i=0;i<100;i++){
					
					plataformaTemp = new PlataformaGelo(i+1);
					if (i == 0){
						plataformaTemp.x = 150;
						plataformaTemp.y = 500;
					}
					else {
						plataformaTemp.x = fixedObjects[i-1].rightside + randomNumber(50,140);
						plataformaTemp.y = fixedObjects[i-1].topside + randomNumber(-110,10);						
						plataformaTemp.scaleX = Math.random()+0.5;					
					}
					
					if (plataformaTemp.y+plataformaTemp.height > spikes.topside)
						plataformaTemp.y = spikes.topside- plataformaTemp.height;
					
					plataformaTemp.leftside = plataformaTemp.x;
					plataformaTemp.rightside = plataformaTemp.x + plataformaTemp.width;
					plataformaTemp.topside = plataformaTemp.y;
					plataformaTemp.bottomside = plataformaTemp.y+plataformaTemp.height;
					
					rand= Math.random();
					if (rand < 0.4 && plataformaTemp.leftside>150){					// na primeira plataformas nao aparecem inimigos
						enemy = new Enemy();
						enemy.setX(plataformaTemp.rightside-enemy.width);
						enemy.setY(plataformaTemp.topside-50);
						enemies.push(enemy);
						this.addChild(enemy);
					}
					
					this.addChild(plataformaTemp);
					fixedObjects.push(plataformaTemp);
				}
				
				// cria limite invisivel para colidir quando o heroi embate na esquerda
				limite.x = -46;
				limite.leftside = limite.x;
				limite.rightside = limite.x + limite.width;
				limite.topside = limite.y;
				limite.bottomside = limite.y+limite.height;
				fixedObjects.push(limite);
			
				// declaramos os espinhos como sendo o nosso limite inferior
				bottomObstacle = spikes;
			
				// actualiza os limites  dos spikes
				spikes.leftside = spikes.x;
				spikes.rightside = spikes.x + spikes.width;
				spikes.topside = spikes.y;
				spikes.bottomside = spikes.y+spikes.height;
				
				// Carrega a imagem de fundo
				bck = new Loader();
				bck.contentLoaderInfo.addEventListener(Event.INIT, loadToBackground);
				
				////
				loadText.text =  "Loading...";
				loadText.width=300;
				loadText.x = 540;
				loadText.y = 500;

				textFormat.font = "Arial";
				textFormat.color = 0xFFFFFF;
				textFormat.size = 50;
				textFormat.bold = true;
				
				loadText.setTextFormat(textFormat);
				
				addChild(loadText);
				gameMode = "awaiting";
				////
				
				bck.load(new URLRequest("Backgrounds/3.png"));
			}
			
			// o heroi inicia cada nivel sempre no mesmo sitio
			hero.setX(200);
			hero.setY(400);
			addChild(hero);
		}
		
		// apaga o nivel, removendo todos os objectos e desligando o som
		public function destroyWorld(){
			
			musicLevel.mute();
			
			removeChild(hero);							// apaga o heroi
		
			for (var i : uint =  0;i<100;i++)			// remove as plataformas e objectos limitadores
				removeChild(fixedObjects[i]);
			fixedObjects = new Array();
			
			
			for (i = 0;i<enemies.length;i++)			// remove os inimigos 
				removeChild(enemies[i]);
			enemies = new Array();
			
			for (i = 0;i<bullets.length;i++)			// remove as balas dos inimigos 
				removeChild(bullets[i]);
		
			bullets = new Array();
			
			for (i = 0;i<bonusList.length;i++)			// remove os bonus do nivel 
				removeChild(bonusList[i]);
		
			bonusList = new Array();
			
			previousPlatform = 0;
			
			removeChild(bottomObstacle);				// remove limite inferior (espinhos, lava, gelo)
			
			this.x = 0;
			this.y = 0;
			
			STG.removeChildAt(0); 						// remove imagem fundo
		}
		
		// carrega objectos para o fundo do jogo (posiçao 0)
		public function loadToBackground(e:Event)
		{     
			removeChild(loadText);
      		var myImg : Loader = e.target.loader;
			STG.addChild(myImg);
			
			STG.setChildIndex(myImg,0);
			
			// apos carregar o fundo, caso esteja no estado de espera, passa a esta em passagem de nivel (que consequentemente da inicio a um nivel)
			if (gameMode == "awaiting")
				gameMode = "lvlup";
		}
		
		// carrega objectos para a "frente" do jogo (posiçao numChildren-1)
		public function loadToTop(e:Event)
		{     
      		var myImg : Loader = e.target.loader;
			STG.addChild(myImg);
			
			STG.setChildIndex(myImg,STG.numChildren-1);
			oldPos = STG.getChildIndex(buttonBackToMenu);
			STG.setChildIndex(buttonBackToMenu , STG.numChildren-1);
		}
		
		// faz subir o nivel, destruindo o anterior e criando o seguinte (novo)
		public function showLVLUP(){
			
			var bck : Loader = new Loader();
			bck.contentLoaderInfo.addEventListener(Event.INIT, loadToTop);
			
			
			if (level < 4){				
				bck.load(new URLRequest("Backgrounds/lvlup.png"));
				timeStop();
				gameMode = "lvlup";
			}
			// caso o jogador passe o ultimo nivel, termina o jogo
			else{
				destroyWorld();
				
				bck.load(new URLRequest("Backgrounds/fim.png"));
				timeStop();
				
				gameScore-=time.currentCount;
				var scoreText : TextField = new TextField();
				scoreText.text = userName+" : "+ gameScore+" Pontos";		// e apresentada a pontuaçao final
				
				scoreText.width=600;
				
				scoreText.setTextFormat(textFormat);
				scoreText.x = 200;
				scoreText.y = 400;
				STG.addChild(scoreText);
				
				STG.removeEventListener(Event.ENTER_FRAME,gameLoop);
				
				gameMode = "terminated";
				saveScore();
			}
			
			return;
		}
		
		// gera um numero random entre 2 fornecidos
		function randomNumber(low:Number = 0,high:Number = 1):Number{
			return Math.floor(Math.random()*(1+high-low))+low;
		}
		
		// esta funçao é executada sempre que passa uma frame do jogo, portanto tudo o que esta constantemente a 
		//		ser actualizado (como pontuaçoes, vidas, etc...) passa por aqui
		public function gameLoop(event:Event) 
		{
			//se o jogo está no inicio, obtemos o tempo actual
			// tempo passado = tempo actual - ultimo tempo
			// ultimo tempo = ultimo tempo + tempo passado
			if (lastTime == 0) lastTime = getTimer();
			var timeDiff:int = getTimer()-lastTime;		
			lastTime += timeDiff;
			
			//caso o jogador volte para a plataforma anterior, nao conta mais score
			if (platform > previousPlatform){
				addScore(level* 10);
				previousPlatform = platform;
			}
			
			// quando chegamos as 60 plataformas, sobe de nivel
			if (platform > 60){
				platform=1;
				level++;
				showLVLUP();
				if(gameMode != "terminated"){
					destroyWorld();
					createWorld();
				}
			}
			
			// tempo apresenado durante o game play, tempo desde que o jogador começou o primeiro nivel
			timeText.text =  String(time.currentCount);
			timeText.width=300;
			timeText.x = 370;
			timeText.y = 25;
			
			textFormat.font = "Arial";
			textFormat.color = 0xFFFFFF;
			textFormat.size = 50;
			textFormat.bold = true;
			
			timeText.setTextFormat(textFormat)
			
			STG.addChild(timeText);
			
			// actualiza a pontuaçao e as vidas do heori
			hud.update(playerLives,gameScore);
		
			// esta tudo em movimento, heroi, inimigos, colisoes, bonus
			if (gameMode == "play"){
				moveCharacter(hero,timeDiff);
				moveEnemies(timeDiff);
				checkCollisions();
				scrollStage();
				autoScroll();
				moveBullets();
				moveBonus();
			}
		}
		
		// cria o movimento de deslocaçao das balas
		function moveBullets()
		{
			if (bullets.length > 0){
				for (var i:int=0; i < bullets.length;i++){
					bullets[i].x-= 10;
				}
			}
		}
		
		// cria o movimento dos objectos de bonus
		function moveBonus(){
			if (bonusList.length > 0){
				for (var i:int=0; i < bonusList.length;i++){
					if(bonusList[i].y < bottomObstacle.bottomside)		// bonus caem pelo ecra abaixo até embaterem no limite inferior
						bonusList[i].y+= 7;
					else{											// quando bate, sao removidos
						removeChild(bonusList[i]);
						bonusList.splice(i,1);
					}
				}
			}
		}
		
		// funçao responsavel pelo movimento das personagens de jogo
		function moveCharacter(char: Object, timeDiff:Number){
			if (timeDiff < 1) 							// se não passou tempo a nova posiçao vai ser igual à actual
				return;
			
			var verticalChange:Number = char.getdy()*timeDiff + timeDiff*gravity;
			var horizontalChange:Number = 0;
			var newAnimState:String = "stand";
			
			//nao deixa que o heroi salte mais que o valor 
			if (verticalChange > 15.0) 
				verticalChange = 15.0;
			
			// velocidade dos eixos do Y fica igual
			char.setdy (char.getdy()+timeDiff*gravity);
			
			// caso o jogador pressione a tecla para a esquerda, anda para a esquerda
			if (char.getMoveLeft()){
				horizontalChange = -char.getWalkSpeed()*timeDiff;
				newAnimState = "walkLeft";
				char.setDirection(-1);
			}
			// caso o jogador pressione a tecla para a direita, anda para a direita
			else if (char.getMoveRight()) {
				horizontalChange = char.getWalkSpeed()*timeDiff;
				newAnimState = "walkRight";
				char.setDirection(1);
			}
			// caso o jogador pressione a tecla para cima, salta para a esquerda se ainda nao estiver no ar
			if (char.getJumpLeft() && !char.getInAir()){
				newAnimState = "jumpLeft";
				if (soundsOn)			// reproduz  o som do salto
					jumpSound.unmute(0);
				char.setJumpLeft(false);
				char.setdy(-char.getJumpSpeed());
				verticalChange = -char.getJumpSpeed();
			}
			// caso o jogador pressione a tecla para cima, salta para a direita se ainda nao estiver no ar
			if (char.getJumpRight() && !char.getInAir()){
				newAnimState = "jumpRight";
				if (soundsOn)
					jumpSound.unmute(0);
				char.setJumpRight(false);
				char.setdy(-char.getJumpSpeed());
				verticalChange = -char.getJumpSpeed();
			}
			// caso o jogador pressione a tecla A no ar, o heroi da um pontape para a esquerda
			if (char.getKickLeft() && char.getInAir()){
				newAnimState = "kickLeft";
			}
			// caso o jogador pressione a tecla A no ar, o heroi da um pontape para a direita
			else if (char.getKickRight()&&char.getInAir()){
				newAnimState = "kickRight";
			}
			// caso o jogador pressione a tecla para baixo, o heroi agacha-se para a esquerda
			if (char.getCrouchLeft()){
				newAnimState = "crouchLeft";
			}
			// caso o jogador pressione a tecla para baixo, o heroi agacha-se para a direita
			else if (char.getCrouchRight()){
				newAnimState = "crouchRight";
			}
			
			char.setHitWallRight(false);
			char.setHitWallLeft(false);
			char.setInAir(true);
				
			// nova posiçao do heroi	
			var newY : Number = char.getY() + verticalChange;		
			
			// vai verificar se o heroi esta em contacto com alguma plataforma
			for(var i:int=0;i<fixedObjects.length;i++) {
				
					// se, em termos de largura, esta a coincidir com uma plataforma
					if ((char.getX()+((char.getWidth()*(3/4))) > fixedObjects[i].leftside) &&((char.getX()+(char.getWidth()*(1/4))) < fixedObjects[i].rightside))
					{	// se, em termos de altura, esta a coincidir com uma plataforma
						if ((newY+char.getHeight() >= fixedObjects[i].topside) && (newY+char.getHeight()< fixedObjects[i].bottomside))
						{	// se nao for o limite lateral esquerdo, a nova altura é a altura em cima da plataforma
							if (fixedObjects[i]!= limite)
								newY = fixedObjects[i].topside-char.getHeight();
							
							// assim sabemos em que plataforma estamos
							if (char == hero && fixedObjects[i].group == "platform")
								platform = fixedObjects[i].identifier;
							
							char.setdy(0);			// quando pousa, poe a altura a zero
							char.setInAir(false);
						
							break;
						}
					}
			}
			
			
			// encontra uma nova posiçao horizontal
			var newX:Number = char.getX() + horizontalChange;
			
					if ((char.getX() < limite.rightside)){
						if (char == hero){		// so precisamos de verificar para o heroi visto que o jogo consiste apenas em plataformas o limite esquerdo apenas conta para o heroi
							newX = limite.rightside+5;
							
							newAnimState = "walkRight";
							char.setHitWallLeft(true);
							char.setInAir(true);
						}
					}
			
			char.setX(newX);
			char.setY(newY);

			char.setAnimState(newAnimState);		// novo movimento
			
			// anda para direita
			if (char.getAnimstate() == "walkRight") {
				if (char.animstep >= char.walkRightAnimation.length)
					char.animstep = 0;
				
				char.mc.gotoAndStop(char.walkRightAnimation[char.animstep]);
				char.animstep ++;
			}
			// anda para a esquerda
			else if (char.getAnimstate() == "walkLeft") {
				if (char.animstep >= char.walkLeftAnimation.length)
					char.animstep = 0;
				
				char.mc.gotoAndStop(char.walkLeftAnimation[char.animstep]);
				char.animstep ++;
			}
			// salta para a esquerda
			else if (char.getAnimstate() == "jumpLeft")
				char.mc.gotoAndStop(25);
			// salta para a direita
			else if (char.getAnimstate() == "jumpRight")
				char.mc.gotoAndStop(21);
			// salta no mesmo sitio virado para a direita
			else if (char.getAnimstate() == "stand" && char.getDirection() == 1 && !char.getInAir())
				char.mc.gotoAndStop(1);
			// salta no mesmo sitio virado para a esquerda
			else if (char.getAnimstate() == "stand" && char.getDirection() == -1 && !char.getInAir())
				char.mc.gotoAndStop(6);
			// no ar, manda pontape para esquerda
			else if (char.getAnimstate() == "kickLeft" ){
				if (char.animstep >= char.kickLeftAnimation.length)
					char.animstep = 0;
			
			char.mc.gotoAndStop(char.kickLeftAnimation[char.animstep]);
			char.animstep ++;
			}
			// no ar, manda pontape para direita
			else if (char.getAnimstate() == "kickRight" ){
				if (char.animstep >= char.kickRightAnimation.length)
					char.animstep = 0;
								
			char.mc.gotoAndStop(char.kickRightAnimation[char.animstep]);
			char.animstep ++;
			}
			// agacha para esquerda
			else if (char.getAnimstate() == "crouchLeft" ){
				if (char.animstep >= char.crouchLeftAnimation.length)
					char.animstep = 0;
				
			char.mc.gotoAndStop(char.crouchLeftAnimation[char.animstep]);
			char.animstep ++;
			}
			// agacha para direita
			else if (char.getAnimstate() == "crouchRight" ){
				if (char.animstep >= char.crouchRightAnimation.length)
					char.animstep = 0;
				
			char.mc.gotoAndStop(char.crouchRightAnimation[char.animstep]);
			char.animstep ++;
			}
		}
		
		// cria o movimento dos robots inimigos
		public function moveEnemies(timeDiff:int) 
		{
			for(var i:int=0;i<enemies.length;i++) {
				moveCharacter(enemies[i],timeDiff);			// faz os inimigos disparar
				
				//formula da distancia entre 2 pontos para o enimigo so disparar quando esta a uma distancia razoavel do heroi
				if ((Math.abs(hero.getX() - enemies[i].getX()))< 300){		
		
					if (enemies[i].animstep >= enemies[i].shooting.length){
						var bullet:Bullet = new Bullet();
						bullet.x = enemies[i].getX()-15;
						bullet.y = enemies[i].getY()+27;
						
						addChild(bullet);
						
						if(soundsOn)
							laserGunSound.unmute(0);
						
						bullets.push(bullet);
						enemies[i].animstep = 0;
					}
					enemies[i].mc.gotoAndStop(enemies[i].shooting[enemies[i].animstep]);
					enemies[i].animstep ++;
				}
			}
		}
		
		// funçao responsavel pela verificaçao das colisoes
		public function hitTest(ObjA:MovieClip, ObjB:MovieClip): Boolean{
			if ((ObjA.x+((ObjA.width*(3/4))) > ObjB.x) &&((ObjA.x+(ObjA.width*(1/4))) < (ObjB.x+ObjB.width*0.6))){	// colisao horizontal
				if ( ( (ObjA.y+(ObjA.height*0.7) ) >= ObjB.y) && (( ObjA.y +(ObjA.height*0.3)  ) < (ObjB.y + ObjB.height) ) )	// colisao vertical
					return true;
			}
			return false;
		}
		
		// caso haja colisao, esta funçao irá aplicar  as acçoes dependendo do que colide
		public function checkCollisions() {
			// caso o heroi embata no limite inferior (espinhos, lava, gelo)
			if (hero.getY() > bottomObstacle.topside)
				heroDie();	// morre
			
			// caso o heroi seja atingido por uma bala inimiga
			for(var i:int=0;i<bullets.length;i++)
				if (hitTest(hero.mc, bullets[i]))
					heroDie();
			
			// caso o heroi apanhe um objecto de bonus (vida, +pontos, modo super)
			for(i=0;i<bonusList.length;i++)
				if (hitTest(hero.mc, bonusList[i])){
					if (bonusList[i].getType() == "health"){
						playerLives++;
						txt =  new Text("1 vida", this);
						txt.x = (bonusList[i].x);
						txt.y = (bonusList[i].y);
						addChild(txt);
						removeChild(bonusList[i]);
						bonusList.splice(i,1);
						
						if (soundsOn)
							lifeUp.unmute(0);
					}
					else if (bonusList[i].getType() == "euro"){
						addScore(100);
						txt =  new Text("+100", this);
						txt.x = (bonusList[i].x);
						txt.y = (bonusList[i].y);
						addChild(txt);
						removeChild(bonusList[i]);
						bonusList.splice(i,1);
						
						if (soundsOn)
							scoreUp.unmute(0);
					}
					else if (bonusList[i].getType() == "super" && superMode==false){
						txt =  new Text("Super!", this);
						txt.x = (bonusList[i].x);
						txt.y = (bonusList[i].y);
						addChild(txt);
						hero.removeMC();
						hero.setSuper();
						removeChild(bonusList[i]);
						bonusList.splice(i,1);
						superMode=true;
						
						if (soundsOn)
							powerUp.unmute(0);
					}
				}
			
			// se o heroi embater nos inimigos
			for(i=0;i<enemies.length;i++) {
				if (hitTest(hero.mc,enemies[i].mc)) {
					if (hero.getKickLeft() || hero.getKickRight()){  // caso esteja com o pontapé activo
						txt =  new Text("+200", this);
						txt.x = (enemies[i].mc.x);
						txt.y = (enemies[i].mc.y);
						addChild(txt);
						
						removeChild(enemies[i]);
						enemies.splice(i,1);
						if (soundsOn)
							dieEnemy.unmute(0);
						addScore(200);
						
					}
					else	// se nao, morre
						heroDie();
				}
			}
		}
		
		// funçao responsavel pela morte do heroi
		public function heroDie(){
			if (!hero.getDead()){
				if (playerLives == 0){	// caso perca todas as vidas, volta ao menu
					gameScore-=time.currentCount;
					backToMenu();
			}
			else{		// perde uma vida (se tiver em modo super, volta a normal)
				txt =  new Text("-1 vida", this);
				txt.x = (hero.mc.x);
				txt.y = (hero.mc.y);
				addChild(txt);
						
				playerLives--;
				
				gameMode = "dead";
				
				if (soundsOn)
					dieHero.unmute(0);
				
				musicLevel.savePosition();		// guarda a posiçao em que ia a musica, para qd voltar a fazer o load, nao começar de novo
				
				// apresenta a gota de sangue no ecra
				var bck : Loader = new Loader();
				bck.contentLoaderInfo.addEventListener(Event.INIT, loadToTop);
				bck.load(new URLRequest("Backgrounds/blood.png"));
				bck.scaleX=1.5;
				bck.scaleY=1.5;
				bck.x =200;
				bck.y =200;
				
				//determina o tempo que o heroi demora a regenerar
				deadTimer = new Timer(1000,2);
				deadTimer.addEventListener(TimerEvent.TIMER, function() {restartLvl()});
				deadTimer.start();
				hero.setDead(true);
				
				// volta o estado a normal, casa seja super
				if(superMode){
					hero.removeMC();
					hero.setNormal();
					superMode=false;
				}
			}
			}
		}
		
		// faz um restart ao nivel, usado quando o heroi morre
		public function restartLvl(){
			deadTimer.removeEventListener(TimerEvent.TIMER, function() {restartLvl()});
			deadTimer.stop();
			if (gameMode == "dead"){
				destroyWorld();
				createWorld();
				hero.setDead(false);
				
				STG.removeChildAt(STG.numChildren-3);
				
				gameMode = "play";
			}
		}
		
		// faz com que o movieClip esteja sempre em movimento, plataformas sempre a chegar
		public function autoScroll(){
			if (gameMode == "play"){
				var levelSpeed : uint = this.level;
				this.x -= levelSpeed;
				
				bottomObstacle.x += levelSpeed;
				
				limite.x += levelSpeed;
				limite.leftside = limite.x;
				limite.rightside = limite.x + limite.width;
				limite.topside = limite.y;
				limite.bottomside = limite.y+limite.height;
			}			
		}
		
		
		public function scrollStage() {
			
			if (gameMode == "play"){   										// Se estamos a jgr
					
				var stagePositionx: Number = this.x + hero.mc.x;				// Obtemos a posicao
				var stagePositiony: Number = this.y + hero.mc.y;
				
				
				var rightEdge: Number = STG.stageWidth-edgeDistance;
				var leftEdge:Number = edgeDistance;
				var bottomEdge:Number=STG.stageHeight - edgeDistancey;
				var topEdge:Number=edgeDistancey;
				
	
				
				if (stagePositionx > rightEdge) {								// Se ultrapassamos o limite direito
					
					this.x -= (stagePositionx-rightEdge);
					if (this.x < -(this.width-STG.stageWidth))
						this.x = -(this.width-STG.stageWidth);
					
						
					bottomObstacle.x +=  stagePositionx-rightEdge;
						
					// actualizamos a posicao do limite da esquerda
					limite.x += stagePositionx-rightEdge;
					limite.leftside = limite.x;
					limite.rightside = limite.x + limite.width;
					limite.topside = limite.y;
					limite.bottomside = limite.y+limite.height;
				
				}
				 if (stagePositiony < topEdge){									// Se ultrapassamos o limite superior
					this.y-= stagePositiony - topEdge;		
				 }
				 
			}
				
				if (stagePositiony > bottomEdge){								// Se ultrapassamos o limite inferior
					this.y-= stagePositiony - bottomEdge;
					
						
				 }
			
		}
		
		// funcao responsável por adicionar pontuacao
		public function bonus(e:TimerEvent){
			
			var rand: Number = Math.random();
			var bonusTemp:MovieClip

			
			if (rand < probHealth){
				bonusTemp = new Hearth();
				bonusTemp.x = hero.mc.x+200;
				bonusTemp.y = hero.mc.y-400;
				addChild(bonusTemp);
				bonusList.push(bonusTemp);
			}
			else if (rand > probHealth && rand <probScore+probHealth){
				bonusTemp = new Euro();
				bonusTemp.x = hero.mc.x+200;
				bonusTemp.y = hero.mc.y-400;
				addChild(bonusTemp);
				bonusList.push(bonusTemp);
			}
			else if (rand > probScore+probHealth && rand <probScore+probHealth+probSuper && superMode==false){
				bonusTemp = new Super();
				bonusTemp.x = hero.mc.x+200;
				bonusTemp.y = hero.mc.y-400;
				addChild(bonusTemp);
				bonusList.push(bonusTemp);
			}
		}
		
		// adiciona pontos ao Score do jogador
		public function addScore(numPoints:int)
		{
			gameScore += numPoints;
		}
		
		// reproduz a musica de fundo, consoante o nivel em que estiver o jogador
		function musicPlay(i:uint)
		{	
			if(i==1){
				musicLevel.unmute(musicLevel.getPosition());
				musicLevel.setVolume(musicLevel.getVolume());		// caso tenha sido mudado, poe o volume como estava
			}
			else if(i==2){
				musicLevel=new Music("Sounds/2.mp3");
				musicLevel.unmute(musicLevel.getPosition());
				musicLevel.setVolume(musicLevel.getVolume());
			}
			else if(i==3){
				musicLevel=new Music("Sounds/3.mp3");
				musicLevel.unmute(musicLevel.getPosition());
				musicLevel.setVolume(musicLevel.getVolume());
			}
		}
		
		// Pára a reproduçao da música de fundo
		function mute(e:Event){
				oldPos = STG.getChildIndex(buttonMusic);
				STG.removeChild(buttonMusic);
				
				// activa botao musica OFF
				buttonMusic = new BotaoMusicaOFF();
				buttonMusic.x=750;
				buttonMusic.y=20;
				STG.addChild(buttonMusic);
				STG.setChildIndex(buttonMusic, oldPos);
				
				buttonMusic.removeEventListener(MouseEvent.MOUSE_UP, mute);
				buttonMusic.addEventListener( MouseEvent.MOUSE_UP, unmute );
				
				musicLevel.savePosition();
				musicLevel.mute();
				musicOn = false;
				STG.focus = this;
				STG.stageFocusRect = false;			
		}

		// Reproduz a música de fundo
		function unmute(e:MouseEvent){
				oldPos = STG.getChildIndex(buttonMusic);
				STG.removeChild(buttonMusic);
				
				// activa o botao musica ON
				buttonMusic = new BotaoMusica();
				buttonMusic.x=750;
				buttonMusic.y=20;
				STG.addChild(buttonMusic);
				STG.setChildIndex(buttonMusic, oldPos);
				
				buttonMusic.removeEventListener(MouseEvent.MOUSE_UP, unmute);
				buttonMusic.addEventListener( MouseEvent.MOUSE_UP, mute );
				musicLevel.unmute(musicLevel.getPosition());
				musicLevel.setVolume(musicLevel.getVolume());
				musicOn = true;
				
				STG.focus = this;
				STG.stageFocusRect = false;
		}
		
		// Para a reproduçao de todos os sons do jogo
		function muteAllSounds(e:Event){
			if (musicOn){		// se estiver activo o botao musica OFF, é necessario desactiva-lo em primeiro, para podermos activar todos os sons OFF
				oldPos = STG.getChildIndex(buttonMusic);
				oldPos2 = STG.getChildIndex(buttonSound);
				STG.removeChild(buttonMusic);
				STG.removeChild(buttonSound);
				
				buttonSound = new somOFFButton();
				buttonSound.x=725;
				buttonSound.y=71;
           		
				buttonMusic = new BotaoMusicaOFF();
				buttonMusic.x=750;
				buttonMusic.y=20;
				
				STG.addChild(buttonSound);
				STG.addChild(buttonMusic);
				STG.setChildIndex(buttonMusic, oldPos);
				STG.setChildIndex(buttonSound, oldPos2);
				
				musicLevel.mute();
				
				soundsOn = false;
				musicOn = false;
			
				buttonMusic.removeEventListener( MouseEvent.MOUSE_UP, mute );
				buttonSound.removeEventListener( MouseEvent.MOUSE_UP, muteAllSounds );
				
				buttonSound.addEventListener( MouseEvent.MOUSE_UP, unmuteAllSounds );
				
				STG.focus = this;
				STG.stageFocusRect = false;
			}
		}

		// permite que todos os sons sejam reproduzidos
		function unmuteAllSounds(e:MouseEvent){
			oldPos = STG.getChildIndex(buttonMusic);
			oldPos2 = STG.getChildIndex(buttonSound);
			
			STG.removeChild(buttonSound);
			STG.removeChild(buttonMusic);
			
			buttonSound = new BotaoSom();
			buttonMusic = new BotaoMusica();
			
			buttonMusic.x=750;
			buttonMusic.y=20;
			STG.addChild(buttonMusic);
			
			buttonSound.x=725;
			buttonSound.y=71;
   			
			STG.addChild(buttonSound);
			STG.addChild(buttonMusic);
			
			STG.setChildIndex(buttonMusic, oldPos);
			STG.setChildIndex(buttonSound, oldPos2);
			
			musicLevel.unmute(musicLevel.getPosition());
			musicLevel.setVolume(musicLevel.getVolume());
			
			soundsOn = true;
			musicOn = true;
			
			buttonMusic.removeEventListener( MouseEvent.MOUSE_UP, unmute );
			buttonSound.removeEventListener( MouseEvent.MOUSE_UP, unmuteAllSounds );
			
			buttonSound.addEventListener( MouseEvent.MOUSE_UP, muteAllSounds );
			buttonMusic.addEventListener (MouseEvent.MOUSE_UP, mute)
			
			STG.focus = this;
			STG.stageFocusRect = false;
		}

		//volume controlado pelo scroll do rato
		function musicVolume(e:MouseEvent)
		{
			if (e.delta>0)
				musicLevel.volumeScroll(1);
			else
				musicLevel.volumeScroll(0);
		}
		
		// inicia o contador de tempo do jogo
		function timePlay(){
			time.start();
			time.addEventListener(TimerEvent.TIMER, bonus);
		}
		
		// para o contador, por exemplo enquanto passa de nivel ou fazemos pausa
		function timeStop():void {
			timeText.text=String(time.currentCount);
			time.stop();
		}
		
		// actualiza os scores no menu ranking, apenas sao visiveis as 3 primeiras melhores posiçoes
		function saveScore(){
			var highScore :uint = 0;
			var highScore1 :uint = 0;
			var highScore2 :uint = 0;
			
			var score:SharedObject = SharedObject.getLocal("KJscores");		// grava um cookie no computador, com os rankings de quem jogou
			
			if (score.data.highScore != null){
        		highScore = score.data.highScore;
			}
			if (score.data.highScore1 != null){
        		highScore1 = score.data.highScore1;
			}
			if (score.data.highScore2 != null){
        		highScore2 = score.data.highScore2;
			}
			
			if (gameScore > highScore){
				score.data.highScore = gameScore;
				score.data.highScoreName = userName;
			}
			else if (gameScore > highScore1){
				score.data.highScore1 = gameScore;
				score.data.highScore1Name = userName;
			}
			else if (gameScore > highScore2){
				score.data.highScore2 = gameScore;
				score.data.highScore2Name = userName;
			}
			score.flush(0);
		}
		
		// volta para o menu principal, quando perde todas as vidas, quando se clica na seta branca, ou quando o jogo é totalmente passado
		function backToMenu()
		{	
			muteAllSounds(null);
			cleanUp();
			if(gameMode != "terminated")
				destroyWorld();
			clearStage();

			saveScore();
			
			if (main.musicOn)
				main.musicMenu.unmute(0);
			main.mostraMenu();
		}
		
		// limpa o stage
		function clearStage(){
			if (gameMode == "username")
				STG.removeChildAt(0);
			
			while (STG.numChildren > 1){
				STG.removeChildAt(STG.numChildren-1);
			}
			//Nao removemos o primeiro elemento pois necessitamos dele para o aparecer o menu
		}
		
		// quando é pressionada uma tecla
		function keyDownFunc(event:KeyboardEvent):void{
			if (event.keyCode == Keyboard.ESCAPE && gameMode == "play"){		//faz pausa no jogo
				var bck : Loader = new Loader();
				bck.contentLoaderInfo.addEventListener(Event.INIT, loadToTop);
				bck.load(new URLRequest("Backgrounds/pause.png"));		
				
				timeStop();
				gameMode = "pause";
				return;
			}
			else if  (event.keyCode == Keyboard.ESCAPE && gameMode=="pause"){		// volta da pausa para o game play
				STG.removeChildAt(STG.numChildren-3);
				STG.setChildIndex(buttonBackToMenu, oldPos);		//volta a meter a seta de retorcesso na posicao em que estava para não haver conflitos com os botoes do som
				
				timePlay();
				gameMode = "play";
			}
			
			// verifica qual a tecla pressionada, e toma as devidas acçoes
			switch (event.keyCode)
			{
				case Keyboard.LEFT:{
					hero.moveLeft();
					break;
				}	
				case Keyboard.RIGHT:{
					hero.moveRight();
					break;
				}
				case Keyboard.DOWN:{
					hero.crouch();
					break;
				}
				case Keyboard.UP:{
					hero.jump();
					break;
				}
				case Keyboard.A:{
					hero.kick();
					break;
				}
			}
		}
		
		// quando é largada uma tecla
		function keyUpFunc(event:KeyboardEvent):void
		{
			// verifica qual a tecla largada, e toma as devidas acçoes
			switch (event.keyCode)
			{
				case Keyboard.LEFT:{
					hero.stopMoveLeft();
					break;
				}
				case Keyboard.RIGHT:{
					hero.stopMoveRight();
					break;
				}
				case Keyboard.UP:{
					hero.setJumpLeft(false);
					hero.setJumpRight(false);
					break;
				}
				case Keyboard.DOWN:{
					hero.setCrouchLeft(false);
					hero.setCrouchRight(false);
					break;
				}
				case Keyboard.A:{
					hero.setKickLeft(false);
					hero.setKickRight(false);
					break;
					
				}
				case Keyboard.ENTER:{
					if (gameMode=="lvlup"){
						timePlay();
						gameMode = "play";
						STG.removeChildAt(STG.numChildren-3);
						STG.setChildIndex(buttonBackToMenu, oldPos);		//volta a meter a seta de retorcesso na posicao em que estava para não haver conflitos com os botoes do som
				
					}
					break;
				}
			}
		}
		
		// limpa os listeners
		public function cleanUp() {
			STG.removeEventListener(Event.ENTER_FRAME,gameLoop);
			STG.removeEventListener(KeyboardEvent.KEY_DOWN,keyDownFunc);
			STG.removeEventListener(KeyboardEvent.KEY_UP,keyUpFunc);
		}
	}
}
