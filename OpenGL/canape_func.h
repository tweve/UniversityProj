GLuint *vbo;
GLuint *vinx;

void InitMesh() {
  unsigned int i;

  vbo = (GLuint *)malloc(OBJECTS_COUNT * sizeof(GLuint));
  vinx = (GLuint *)malloc(OBJECTS_COUNT * sizeof(GLuint));

  glGenBuffers(OBJECTS_COUNT, vbo);

  for (i=0; i<OBJECTS_COUNT; i++) {	
	  glBindBuffer(GL_ARRAY_BUFFER, vbo[i]);
	  glBufferData(GL_ARRAY_BUFFER, sizeof (struct vertex_struct) * vertex_count[i], &vertices[vertex_offset_table[i]], GL_STATIC_DRAW);
	  glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  glGenBuffers(OBJECTS_COUNT, vinx);
  for (i=0; i<OBJECTS_COUNT; i++) {	
	  glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vinx[i]);
	  glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof (indexes[0]) * faces_count[i] * 3, &indexes[indices_offset_table[i]], GL_STATIC_DRAW);
	  glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
  }
}

#define BUFFER_OFFSET(x)((char *)NULL+(x))

void DrawMesh(unsigned int index, int apply_transformations) {

	if (apply_transformations) {
	  glPushMatrix();
		glMultMatrixf(transformations[index]);
	}

	glBindBuffer(GL_ARRAY_BUFFER, vbo[index]);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vinx[index]);

	glEnableClientState(GL_VERTEX_ARRAY);
	glVertexPointer(3, GL_FLOAT, sizeof (struct vertex_struct), BUFFER_OFFSET(0));

	glEnableClientState(GL_NORMAL_ARRAY);
	glNormalPointer(GL_FLOAT, sizeof (struct vertex_struct), BUFFER_OFFSET(3 * sizeof (float)));

	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	glTexCoordPointer(2, GL_FLOAT, sizeof (struct vertex_struct), BUFFER_OFFSET(6 * sizeof (float)));

	glDrawElements(GL_TRIANGLES, faces_count[index] * 3, INX_TYPE, BUFFER_OFFSET(0));

	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	glDisableClientState(GL_NORMAL_ARRAY);
	glDisableClientState(GL_VERTEX_ARRAY);

	if (apply_transformations) {
		glPopMatrix();
	}
}

void DrawAllMeshes()
{
	unsigned int i;

	for (i=0; i<OBJECTS_COUNT; i++) {
		DrawMesh(i, 1);
	}
}

