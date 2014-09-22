//this is a convinient typedef to better read the code.
//it defines "byte" as a unsigned 8bit int.
typedef u_int8_t  byte;

//this struct represents each pixel.
//should be used to handle each pixel and maintain their correct colors.
typedef struct pixel_t {
	byte red;
	byte green;
	byte blue;
}pixel;


//this struct represents the ppm file header.
//it stores the file info and will be copied to the output file.
typedef struct header_t {
	char type[3];
	unsigned int width;
	unsigned int height;
	unsigned int depth;
}header;

//reads a line and extracts the type of ppm file.
//returns "P6" on success or "" on fail or other type of ppm file.
char* readType(FILE *fp);

//reads a line and extracts the unsigned int value of that line.
//returns 0 on fail and a value greater than 0 ond success.
//used to read width, height and depth from the ppm file.
unsigned int readNextHeaderValue(FILE *fp);

//reads file and extracts the header.
//returns a header struct on success or NULL on failure.
header* getImageHeader(FILE *fp);

//reads a complete row of the image based on the number of pixels given.
//user should give an initialized pixel array to be filled.
//returns number of pixels read on success and -1 on failure.
int getImageRow(int pixelNo, pixel* row, FILE *fp);

//inverts the pixels in the given row.
void invertRow(int pixelNo, pixel* row);

//writes the image header to file.
//returns 0 on success and -1 on failure.
int writeImageHeader(header* h, FILE* fp);

//writes a row of pixels to a file.
//returns 0 on success and -1 on failure
int writeRow(int pixelNo, pixel* row, FILE* fp);
