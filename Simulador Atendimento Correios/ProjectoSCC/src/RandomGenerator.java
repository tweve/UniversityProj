/* Prime modulus multiplicative linear congruential pseudo random number generator

   Code copied from book "Simulation Modeling and Analysis, second edition,
   Averill M. Law and W. David Kelton, McGraw-Hill, 1991"
   
   Z[i] = (630360016 * Z[i-1]) (mod(pow(2,31) - 1)), based on Marse and
   Roberts' portable FORTRAN random-number generator UNIRAN. Multiple
   (100) streams are supported, with seeds spaced 100,000 apart.
   Throughout, input argument "stream" must be an int giving the
   desired stream number.
   
   Usage: (three functions)
   
   1. To obtain the next U(0,1) random number from stream "stream,"
      execute
         u = MarseRoberts.rand( stream )
      where rand is a double function. The double variable u will
      contain the next random number.
      
      Note: there is an equivalent function rand64() which generates the same
      value using Java's 64 bit arithmetic. This was added by me.
      
   2. To set the seed for stream "stream" to desired value zset,
      execute
         MarseRoberts.randst( zset, stream );
      where zset must be an integer to the desired seed, 
      a number between 1 and 2147483646 (inclusive).
      Default seeds for all 100 streams are given in the code.
      
   3. To get the current (most recently used) integer in the sequence 
      being generated for stream "stream" into the int variable zget, 
      execute
         zget = MarseRoberts.randgt( stream );
      where randgt is a int function. 
      
   Validation:
   
   To get an idea whether this port works correctly, rand() and
   rand64() were called 1000 times for each of the 100 streams. 
   The two seed tables zrng and zrng64 were then compared using 
   compareSeeds(). They contained identical values.

   Performance:
   
   For each of the 100 streams, 100 random numbers were generated. This
   was timed, once using rand64() and then using rand(). The results are as follows:
   
   Metrowerks Java (Metrowerks CodeWarrior Academic Pro 11 for Mac OS):
   
   rand64()       rand()         speed up by using rand64 (time(rand) / time(rand64))
   ---------      --------       ----------------------------------------------------
   1604.4 ms      210.4 ms       0.131
   
   Netscape Navigator 3.01 for Macintosh:
   
   rand64()       rand()
   --------       ------         ----------------------------------------------------
   289.1 ms       428.1          1.481

   (Average of 7 runs on a PowerMacintosh 6100, 66 MHZ and second level cache):
*/

public class RandomGenerator {

  // Default seeds for all 100 streams
  //
  private static int zrng[] = { 
    1973272912,  281629770,   20006270, 1280689831, 2096730329, 1933576050, 
     913566091,  246780520, 1363774876,  604901985, 1511192140, 1259851944, 
     824064364,  150493284,  242708531,   75253171, 1964472944, 1202299975, 
     233217322, 1911216000,  726370533,  403498145,  993232223, 1103205531, 
     762430696, 1922803170, 1385516923,   76271663,  413682397,  726466604, 
     336157058, 1432650381, 1120463904,  595778810,  877722890, 1046574445, 
      68911991, 2088367019,  748545416,  622401386, 2122378830,  640690903, 
    1774806513, 2132545692, 2079249579,   78130110,  852776735, 1187867272, 
    1351423507, 1645973084, 1997049139,  922510944, 2045512870,  898585771, 
     243649545, 1004818771,  773686062,  403188473,  372279877, 1901633463, 
     498067494, 2087759558,  493157915,  597104727, 1530940798, 1814496276, 
     536444882, 1663153658,  855503735,   67784357, 1432404475,  619691088, 
     119025595,  880802310,  176192644, 1116780070,  277854671, 1366580350, 
    1142483975, 2026948561, 1053920743,  786262391, 1792203830, 1494667770, 
    1923011392, 1433700034, 1244184613, 1147297105,  539712780, 1545929719, 
     190641742, 1645390429,  264907697,  620389253, 1502074852,  927711160, 
     364849192, 2049576050,  638580085,  547070247 
  };
  
  // This table was used to validate the generator
  //
  /*
  private static int zrng64[] = { 
    1973272912,  281629770,   20006270, 1280689831, 2096730329, 1933576050, 
     913566091,  246780520, 1363774876,  604901985, 1511192140, 1259851944, 
     824064364,  150493284,  242708531,   75253171, 1964472944, 1202299975, 
     233217322, 1911216000,  726370533,  403498145,  993232223, 1103205531, 
     762430696, 1922803170, 1385516923,   76271663,  413682397,  726466604, 
     336157058, 1432650381, 1120463904,  595778810,  877722890, 1046574445, 
      68911991, 2088367019,  748545416,  622401386, 2122378830,  640690903, 
    1774806513, 2132545692, 2079249579,   78130110,  852776735, 1187867272, 
    1351423507, 1645973084, 1997049139,  922510944, 2045512870,  898585771, 
     243649545, 1004818771,  773686062,  403188473,  372279877, 1901633463, 
     498067494, 2087759558,  493157915,  597104727, 1530940798, 1814496276, 
     536444882, 1663153658,  855503735,   67784357, 1432404475,  619691088, 
     119025595,  880802310,  176192644, 1116780070,  277854671, 1366580350, 
    1142483975, 2026948561, 1053920743,  786262391, 1792203830, 1494667770, 
    1923011392, 1433700034, 1244184613, 1147297105,  539712780, 1545929719, 
     190641742, 1645390429,  264907697,  620389253, 1502074852,  927711160, 
     364849192, 2049576050,  638580085,  547070247 
  }; 
  */

  // The magic constants
  //
  private static final int MODLUS  = 2147483647;
  private static final int MULT1   = 24112;
  private static final int MULT2   = 26143;
  
  
  // Generate the next random number using 64 bit arithmetic.
  // (A Java 'long' is 64 bits long.)
  //
  public static double rand64( int stream ) {
    
    // This version was used to validate the generator (using the zrng64 table)
    /*
    long zi = ((long)630360016 * (long)zrng64[stream]) % (long)MODLUS;
    zrng64[stream] = (int) zi;
    */

    long zi = ((long)630360016 * (long)zrng[stream]) % (long)MODLUS;
    zrng[stream] = (int) zi;
        
    // return (double)zi / (double)MODLUS;
    return (double)((zi >> 7 | 1) + 1) / 16777216.0;
  }
  
  // Compare the two seed tables; returns true if the tables are identical.
  // This function was used to validate the generator.
  //
  /*
  public static boolean compareSeeds() {
    for (int i=0; i<100; i++) {
      if (zrng[i] != zrng64[i]) return false;
    }
    return true;
  }
  */
  
  // Generate the next random number.
  //
  public static double rand( int stream ) {
    int zi, lowprd, hi31;
    
    zi = zrng[stream];
    lowprd = (zi & 65535) * MULT1;
    hi31 = (zi >> 16) * MULT1 + (lowprd >> 16);
    zi = ((lowprd & 65535) - MODLUS) + ((hi31 & 32767) << 16) + (hi31 >> 15);
    if (zi < 0) zi += MODLUS;
    lowprd = (zi & 65535) * MULT2;
    hi31 = (zi >> 16) * MULT2 + (lowprd >> 16);
    zi = ((lowprd & 65535) - MODLUS) + ((hi31 & 32767) << 16) + (hi31 >> 15);
    if (zi < 0) zi += MODLUS;
    zrng[stream] = zi;
    
    return (double)((zi >> 7 | 1) + 1) / 16777216.0;
  }
  
  
  // Set the current zrng for stream "stream" to zset.
  //
  public static void randst( int zset, int stream ) {
    zrng[stream] = zset;
  }
  
  
  // Return the current zrng for stream "stream".
  //
  public static int randgt( int stream ) {
    return zrng[stream];
  }
}
