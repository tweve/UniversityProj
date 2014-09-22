package{
	
	import flash.display.*;
	import flash.text.*;
	import flash.utils.Timer;
	import flash.events.TimerEvent;
	
	public class Text extends MovieClip {
		
		var txt : TextField = new TextField();				// Campo de texto informativo						 
		var txtFormat : TextFormat = new TextFormat();		// Formatação do texto
		var timer : Timer = new Timer(500,2);				// Timer para retirar o texto
	
		var s : MovieClip;													// Ponteiro para o MovieClip pai
		
		public function Text(txt : String, stg:MovieClip){ 					// Construtor da class Text
			
			this.s = stg;													// Atribuição da referencia
			this.txt.text = txt;											// Atribuição do texto
			
			txtFormat.font = "Arial";		// Declaração da Formatação									
			txtFormat.color = 0xFFFFFF;
			txtFormat.size = 15;
			txtFormat.bold = true;
			
			this.txt.setTextFormat(txtFormat);								// Atribuição da formatação ao texto
			
			addChild(this.txt);												// Adiciona o texto ao Stage
			
			timer.addEventListener(TimerEvent.TIMER, function() {removeText()});	// removeText é chamada quando ocorre o fim do tempo
			timer.start();													// Começa o timer	
		}
		
		public function removeText(){ 		// Remove o texto do MovieClip principal
			
			s.removeChild(this);														// Remove
			
			timer.stop();																// Pára o timer
			timer.removeEventListener(TimerEvent.TIMER, function() {removeText()}); 	// Remove o timer
		}
	}
}
