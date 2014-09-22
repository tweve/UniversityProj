package  {
	
	import flash.display.MovieClip;
	
	public class PlataformaGelo extends MovieClip {
		
		var identifier : uint;					// ID da plataforma, serve para saber em que plataforma estamos
		var group : String = "platform";		// Serve para saber que se trata de uma plataforma
		
		var leftside : int;						// limite esquerdo
		var rightside : int;					// limite direito
		var topside : int;						// limite cima
		var bottomside : int;					// limite baixo
		
		public function PlataformaGelo(n:uint) 
		{
			this.identifier = n;				// Atribui o valor recebido ao ID da plataforma
		}
		
	}
}
