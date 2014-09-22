package {
	
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.media.Sound;
	import flash.media.SoundTransform;
	import flash.media.SoundChannel;
	import flash.events.MouseEvent;
	import flash.events.Event;
	import flashx.textLayout.formats.Float;
	
	public class Music
	{
		// Variáveis
		public var MP3Request:URLRequest;
		private var musicTransform:SoundTransform;
		private var musicChannel:SoundChannel;
		private var music:Sound;									// Som
		private var musicPosition:int=0;							// Posição do Som
		private var vol:Number = 1.0;

		public function Music(filename:String)						// Constructor
		{
			MP3Request = new URLRequest(filename);					// Requisito do som
			
			music = new Sound();
			music.load(MP3Request);									// Carrega o Som
			musicChannel = new SoundChannel();
			musicTransform = new SoundTransform(0.5,0);
			musicPosition = 0;										// Posição = 0
		}

		public function mute()										// Pára a reprodução do Som
		{
			musicChannel.stop();
		}
		
		public function unmute(position:int)						// Retoma a reprodução do Som
		{
			musicChannel = music.play(position);			
		}
		
		public function volumeScroll(pos:uint)
		{
			// Transforma o volume do Som, incrementando ou decrementando
			var vol: Number = musicTransform.volume;
			if(pos==1){
				if(vol>=1.0)
					vol=1.0;
				else
					vol+=0.05;
			}
			else{
				if(vol<=0.0)
					vol=0.0;
				else
					vol-=0.05;
			}
			musicTransform.volume=vol;
			musicChannel.soundTransform=musicTransform;
			this.vol = vol;
		}
		
		public function savePosition():void				// Guarda a Posição de reprodução
		{
			musicPosition=this.musicChannel.position;
		}
				
		public function getChannel():SoundChannel		// Devolve os Canais do Som
		{
			return this.musicChannel;
		}
		
		public function getPosition():int				// Devolve a Posição Actual do SOm
		{
			return this.musicPosition;
		}
		
		public function getVolume()						// Retorna o volume do som
		{
			return this.vol;
		}
		
		public function setVolume(vol:Number)			// Transfoma o volume do som
		{
			musicTransform.volume=vol;
			musicChannel.soundTransform=musicTransform;
			this.vol = vol;
		}
		
	}
}