package{
	
	import flash.display.*;
	import flash.text.*;
	
	public class HUD extends MovieClip {
		
		var score : TextField = new TextField();	// texto pontuacao
		var lives : TextField = new TextField();	// texto vidas
	
		var scoreFormat : TextFormat = new TextFormat();	// Formatacao de texto
		
		public function HUD(stg : Stage)
		{		
			score.x = 2;			// posicao do texto com pontuacao e vidas
			score.y = 15;
			lives.x = 2;
			lives.y = 50;
			score.text = "0";		// valores pontuacao e vidas por defeito
			lives.text = "2";
			
			scoreFormat.font = "Arial";		// Construcao da Formatacao
			scoreFormat.color = 0xFFFFFF;
			scoreFormat.size = 15;
			scoreFormat.bold = true;
			
			score.setTextFormat(scoreFormat); 	// campo pontuacao e vidas
			lives.setTextFormat(scoreFormat);   // de acordo com a formatacao
			
			addChild(this.lives);		// Adicoes ao Stage
			addChild(this.score);
			stg.addChild(this);
		}
		
		public function update(lives: uint, score: uint) 	// Actualizar pontos e vidas
		{ 
			this.score.text = score.toString(10);		// pontuacao
			this.score.setTextFormat(scoreFormat);
			this.lives.text = lives.toString(10); 		// vidas
			this.lives.setTextFormat(scoreFormat);
		}
		
	}
}
