// Classe para geracao de numeros aleatorios segundos varias distribui��es
// Apenas a distribuicao exponencial negativa esta definida

public class Aleatorio {
    
    static short chamadaNormalAF = 0;
    static short chamadaNormalAG = 0;
    static double X2AF = 0;
    static double X2AG = 0;
    
	// Gera um n�mero segundo uma distribui��o exponencial negativa de m�dia m
    static double exponencial (double m){
	return (-m*Math.log(RandomGenerator.rand(1)));                  // Seed 1
    }
    
    static double normalGamaAF (double m, double dp){
        
        if (chamadaNormalAF == 1){
            chamadaNormalAF = 0;
            return X2AF; 
        }
        else{
         

            double V1 = 2* RandomGenerator.rand(3) - 1;                           // Seed 3
            double V2 = 2* RandomGenerator.rand(3) - 1;
            double X1 = 0;
            double W = V1*V1 + V2*V2;

            while (W > 1){
                V1 = 2* RandomGenerator.rand(3) - 1;                           // Seed 3
                V2 = 2* RandomGenerator.rand(3) - 1;
                W = V1*V1 + V2*V2;
            }
            
            double Y1 = V1 * Math.pow(((-2*Math.log(W))/W),(1/2));
            double Y2 = V2 * Math.pow(((-2*Math.log(W))/W),(1/2));
            
            X1 = m + (Y1*dp);
            chamadaNormalAF = 1;
            X2AF = m + (Y2*dp);
            
            return X1;
        }
    }
    
      static double normalGamaAG (double m, double dp){
        
        if (chamadaNormalAG == 1){
            chamadaNormalAG = 0;
            return X2AG; 
        }
        else{
         

            double V1 = 2* RandomGenerator.rand(4) - 1;                           // Seed 4
            double V2 = 2* RandomGenerator.rand(4) - 1;
            double X1 = 0;
            double W = V1*V1 + V2*V2;

            while (W > 1){
                V1 = 2* RandomGenerator.rand(4) - 1;                              // Seed 4
                V2 = 2* RandomGenerator.rand(4) - 1;
                W = V1*V1 + V2*V2;
            }
            
            double Y1 = V1 * Math.pow(((-2*Math.log(W))/W),(1/2));
            double Y2 = V2 * Math.pow(((-2*Math.log(W))/W),(1/2));
            
            X1 = m + (Y1*dp);
            chamadaNormalAG = 1;
            X2AG = m + (Y2*dp);
            
            return X1;
        }
    }

}
