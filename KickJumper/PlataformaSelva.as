package  {
	
	import flash.display.MovieClip;
	
	public class PlataformaSelva extends MovieClip {
		
		var identifier : uint;						// ID da plataforma, serve para saber em que plataforma estamos
		var group : String = "platform";			// Serve para saber que se trata de uma plataforma
		
		// Declaração de limites
		var leftside : int;
		var rightside : int;
		var topside : int;
		var bottomside : int;
		
		public function PlataformaSelva(n:uint)
		{
			this.identifier=n;						// Atribui o valor recebido ao ID da plataforma
		}
		
	}
}
