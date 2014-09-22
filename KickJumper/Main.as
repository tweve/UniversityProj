package {
	
	// Imports
	import flash.display.*;
	import flash.events.*;
	import flash.net.URLRequest;
	import flash.net.URLLoader;
	import flash.system.*;
	import flash.media.Sound;
	import flash.media.SoundChannel;
	import flash.net.SharedObject;
	import flash.text.*;
	
	public class Main extends MovieClip{
		
		// Variáveis
		var musicMenu : Music = new Music("Sounds/0.mp3");					// carrega música chamada 0.mp3
		var soundMouseOver : Music = new Music("Sounds/menuButton.mp3");
		
		var musicOn : Boolean = true;										// variável que permite activar desativar musica de background
		var soundsOn :Boolean = true;										// variável que permite activar desativar sons
		
		private var _instance:Main;											// Referência para Main
		
		public function Main(){												// Mostra o menu
			//Constructor
			
			_instance = this;
			fscommand("allowscale","false");								// Desabilita Redimensão da janela
			this.stop();													// Pára a reproduçao automática de frames
			this.mostraMenu();												// Mostra o Menu
			musicMenu.unmute(0);											// Inicia a música do menu
		}
		
		public function mostraMenu(){
			//Mostra o Menu
			
			clearStage();																// Limpa o Stage
			
			var bck : Loader = new Loader();											
			bck.contentLoaderInfo.addEventListener(Event.INIT, loadToBackground);		// Chama loadToBackground()
			bck.load(new URLRequest("Backgrounds/menu_bck.png"));						// Carrega o Background
			
			// Cria os botoes necessarios ao Menu
			var buttons: Array= new Array();
			var i: uint;
			var buttonPlay: BotaoJogar = new BotaoJogar();
			var buttonInstructions: BotaoInstrucoes= new BotaoInstrucoes();
			var buttonRanking: BotaoRanking = new BotaoRanking();
			var buttonCredits: BotaoCreditos = new BotaoCreditos();
			var buttonExit: BotaoSair = new BotaoSair();
			var buttonSound: BotaoSom = new BotaoSom();
			var buttonMusic: BotaoMusica = new BotaoMusica();
			
			// Adiciona os botoes ao stage e coloca-os nas posiçoes correctas
			addChild(buttonPlay);			
			buttonPlay.x=170;
			buttonPlay.y=336;
			
			addChild(buttonInstructions);
			buttonInstructions.x=170;
			buttonInstructions.y=401;
			
			addChild(buttonRanking);
			buttonRanking.x=412;
			buttonRanking.y=336;
			
			addChild(buttonCredits);
			buttonCredits.x=412;
			buttonCredits.y=401;
			
			addChild(buttonExit);
			buttonExit.x=291;
			buttonExit.y=487;
			
			addChild(buttonSound);
			buttonSound.x=590;
			buttonSound.y=508;
			
			addChild(buttonMusic);
			buttonMusic.x=615;
			buttonMusic.y=469;
			
			var buttonMusicOff: BotaoMusicaOFF = new BotaoMusicaOFF();
			
			if (! soundsOn){												// se os sons nao estiverem activos
				var soundsButton: somOFFButton = new somOFFButton();
				soundsButton.x=590;
				soundsButton.y=508;
           		addChild(soundsButton);
				soundsButton.addEventListener( MouseEvent.MOUSE_UP, unmuteAllSounds );
				
				buttonMusicOff.x=615;
				buttonMusicOff.y=469;
           		addChild(buttonMusicOff);
				buttonMusicOff.addEventListener( MouseEvent.MOUSE_UP, unmute );
			}
			
			// se a música estiver parada adiciona o botao musica off
			if (! musicOn && soundsOn){
				buttonMusicOff.x=615;
				buttonMusicOff.y=469;
           		addChild(buttonMusicOff);
				buttonMusicOff.addEventListener( MouseEvent.MOUSE_UP, unmute );
			}
			
			
			// adiciona os botoes ao array buttons para facilitar a aplicação de transparencia alpha
			buttons.push(buttonPlay);
			buttons.push(buttonInstructions);
			buttons.push(buttonRanking);
			buttons.push(buttonCredits);
			buttons.push(buttonExit);
			
			// percorre buttons e aplica os listeners responsáveis pela transparencia
			for(i=0; i<5;i++){
				buttons[i].addEventListener(MouseEvent.MOUSE_OVER, function(e:MouseEvent){aalpha(e,0.5)});
				buttons[i].addEventListener(MouseEvent.MOUSE_OUT, function(e:MouseEvent){aalpha(e,1)});
			}
			
			// adiciona listeners reponsáveis pela reacao dos botoes
			buttonPlay.addEventListener(MouseEvent.MOUSE_UP, jjogar);
			buttonInstructions.addEventListener(MouseEvent.MOUSE_UP, iinst);
			buttonRanking.addEventListener(MouseEvent.MOUSE_UP, rrank);
			buttonCredits.addEventListener(MouseEvent.MOUSE_UP, ccred);
			buttonExit.addEventListener(MouseEvent.MOUSE_UP, ssair);
			
			// adiciona listeners para parar os sons
			buttonMusic.addEventListener(MouseEvent.MOUSE_UP, mute);
			buttonSound.addEventListener(MouseEvent.MOUSE_UP, muteAllSounds);
			
			//quando acabar a musica, volta a repetir
			musicMenu.getChannel().addEventListener(Event.SOUND_COMPLETE, function(e : int = 0){musicMenu.unmute(e)});
			
			// eventlistener que permite controlar o volume da musica de background com o scroll do rato
			this.addEventListener(MouseEvent.MOUSE_WHEEL, musicVolume);
		}

		public function loadToBackground(e:Event){   
			// Função que coloca a imagem carregada como background do stage (centrado)
			
      		var myImg : Loader = e.target.loader;
			myImg.x = 148; myImg.y = 50;
			addChild(myImg);
			setChildIndex(myImg,0);
		}
		
		function mute(e:Event){
			// Pára a reproduçao da música de fundo
			
			var buttonMusicOff: BotaoMusicaOFF = new BotaoMusicaOFF();
			
			buttonMusicOff.x=615;
			buttonMusicOff.y=469;
            addChild(buttonMusicOff);
			buttonMusicOff.addEventListener( MouseEvent.MOUSE_UP, unmute );
			
			musicMenu.savePosition();
			musicMenu.mute();
			musicOn = false;
		}

		function unmute(e:MouseEvent){
			// Reproduz a música de fundo
		
			if(e!=null){	
				removeChildAt(8);
				e.target.removeEventListener( MouseEvent.MOUSE_UP, mute );
			}
			
			musicMenu.unmute(musicMenu.getPosition());
			musicOn = true;
		}
		
		function muteAllSounds(e:Event){
			// Para a reproduçao de todos os sons
			
			if (musicOn){
				var buttonSoundOff: somOFFButton = new somOFFButton();
			
				buttonSoundOff.x=590;
				buttonSoundOff.y=508;
           		addChild(buttonSoundOff);
				
				var buttonMusicOff: BotaoMusicaOFF = new BotaoMusicaOFF();
				
				buttonMusicOff.x=615;
				buttonMusicOff.y=469;
           		addChild(buttonMusicOff);
				
				musicMenu.savePosition();
				
				musicMenu.mute();
				
				soundsOn = false;
				musicOn = false;
			
				buttonSoundOff.addEventListener( MouseEvent.MOUSE_UP, unmuteAllSounds );
			}
		}

		function unmuteAllSounds(e:MouseEvent){
			// permite que todos os sons sejam reproduzidos
			
			var buttonSoundOff: somOFFButton = new somOFFButton();
			
			buttonSoundOff.x=615;
			buttonSoundOff.y=469;
   			
			// retira botoes de Som parado
			removeChildAt(numChildren-1);			
			removeChildAt(numChildren-1);				
			
			musicMenu.unmute(musicMenu.getPosition());
			soundMouseOver.unmute(0);
			
			soundsOn = true;
			musicOn = true;
			
			buttonSoundOff.addEventListener( MouseEvent.MOUSE_UP, unmuteAllSounds );
		}

		function musicVolume(e:MouseEvent){
			//volume controlado pelo scroll do rato
			
			if (e.delta>0)
				musicMenu.volumeScroll(1);
			else
				musicMenu.volumeScroll(0);
		}
		
		public function jjogar(e:Event){
			// Botão Jogar pressionado
			
			musicMenu.mute();
			clearStage();
			var engine : GameEngine = new GameEngine(stage, this);
			
			addChild(engine);
		}
		
		public function aalpha(e:MouseEvent, alph:Number){
			// Funçao responsável por aplicar transparência alpha aos botoes
			
			if(alph==0.5 && soundsOn)
				soundMouseOver.unmute(0);
			
			e.target.alpha=alph;
		}
		
		public function iinst(e:Event){
			// Funçao que apresenta as instruçoes
			
			clearStage();
			
			var bck : Loader = new Loader();
			var back: BotaoVoltar = new BotaoVoltar();
			bck.contentLoaderInfo.addEventListener(Event.INIT, loadToBackground);
			bck.load(new URLRequest("Backgrounds/inst_bck.png"));
			
			addChild(back);
			back.x=291;
			back.y=487;
			
			back.addEventListener(MouseEvent.MOUSE_OVER, function(e:MouseEvent){aalpha(e,0.5)});		// transparencia do botao
			back.addEventListener(MouseEvent.MOUSE_OUT, function(e:MouseEvent){aalpha(e,1)});
			
			back.addEventListener(MouseEvent.MOUSE_UP, function() {mostraMenu()} );						// mostra menu quando pressionado
		}
		
		public function rrank(e:Event)			// Funçao que apresenta o ranking
		{
			clearStage();
			
			var bck : Loader = new Loader();
			var back: BotaoVoltar = new BotaoVoltar();
			bck.contentLoaderInfo.addEventListener(Event.INIT, loadToBackground);
			bck.load(new URLRequest("Backgrounds/rank_bck.png"));
			
			// Mostra pontuaçoes
			
			var msgFormat : TextFormat = new TextFormat();
			
			msgFormat.font = "Adobe Heiti Std";
			msgFormat.color = 0xF3800C;
			msgFormat.size = 30;
			msgFormat.bold = true;
			
			var score:SharedObject = SharedObject.getLocal("KJscores");
			
			if (score.data.highScore != null && score.data.highScoreName!= null)
			{
				trace("carrega1")
        		//highScore = score.data.highScore;
				var sc1:TextField = new TextField();
				trace(score.data.highScore);
				trace(score.data.highScoreName);
				
				sc1.text ="1 : " +score.data.highScore+ " pontos - " + score.data.highScoreName;
				sc1.width = 500;
				sc1.x = 290;
				sc1.y = 200;
				sc1.setTextFormat(msgFormat);
				addChild(sc1);
			}
			
			if (score.data.highScore1 != null && score.data.highScore1Name!= null)
			{
				trace("carrega2");
        		//highScore1 = score.data.highScore1;
				var sc2:TextField = new TextField();
				sc2.text ="2 : " +score.data.highScore1+" pontos - " + score.data.highScore1Name;
				sc2.width = 500;
				sc2.x = 290;
				sc2.y = 240;
				sc2.setTextFormat(msgFormat);
				addChild(sc2);
			}
			
			if (score.data.highScore2 != null && score.data.highScore2Name!= null)
			{
        		trace("carrega3");
				var sc3:TextField = new TextField();
				sc3.text ="3 : " +score.data.highScore2+" pontos - " + score.data.highScore2Name;
				sc3.width = 500;
				sc3.x = 290;
				sc3.y = 280;
				sc3.setTextFormat(msgFormat);
				addChild(sc3);
			}
			
			addChild(back);
			back.x=291;
			back.y=487;
			
			back.addEventListener(MouseEvent.MOUSE_OVER, function(e:MouseEvent){aalpha(e,0.5)});
			back.addEventListener(MouseEvent.MOUSE_OUT, function(e:MouseEvent){aalpha(e,1)});			// transparencia do botao
			back.addEventListener(MouseEvent.MOUSE_UP, function() {mostraMenu()} );						// mostra menu quando pressionado
			
		}
		
		public function ccred(e:Event)		// Função que apresenta os créditos
		{			
			clearStage();
			
			var bck : Loader = new Loader();
			var back: BotaoVoltar = new BotaoVoltar();
			bck.contentLoaderInfo.addEventListener(Event.INIT, loadToBackground);
			bck.load(new URLRequest("Backgrounds/cred_bck.png"));
			
			addChild(back);
			back.x=291;
			back.y=487;
			
			back.addEventListener(MouseEvent.MOUSE_OVER, function(e:MouseEvent){aalpha(e,0.5)});		// Alpha
			back.addEventListener(MouseEvent.MOUSE_OUT, function(e:MouseEvent){aalpha(e,1)});
			back.addEventListener(MouseEvent.MOUSE_UP, function() {mostraMenu()} );						// Menu
			
		}
		
		function clearStage()					// retira todos os Objectos do Stage
		{
			while (numChildren > 0)
				removeChildAt(0);
		}
		
		public function ssair(e:Event)			// Botao Sair Pressionado
		{
			fscommand("quit");
		}
		
	}
}