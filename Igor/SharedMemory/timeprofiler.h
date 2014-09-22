//returns current time stamp with microsecond precision.
double getCurrentTimeMicro();

//returns current time stamp with milisecond precision.
double getCurrentTimeMili();

//calculates and prints the time elapsed between start and stop.
//unit is a string with the unit name to be used in the output.
void printTimeElapsed(double start, double stop, char* unit);
