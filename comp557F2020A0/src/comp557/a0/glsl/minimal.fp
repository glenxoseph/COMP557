#version 400

in vec3 colorForFP;

out vec4 fragColor;

void main(void) {
   fragColor = vec4(colorForFP,1);
}