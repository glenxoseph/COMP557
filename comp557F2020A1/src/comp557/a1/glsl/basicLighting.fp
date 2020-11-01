#version 400

uniform vec3 kd; 
uniform vec3 ks;

in vec3 normalForFP;
out vec4 fragColor;

uniform vec3 viewDir;
uniform vec3 fillingD;
uniform vec3 keyD;
uniform vec3 backDir; 

uniform float shininess; 

uniform vec3 fillColor; 
uniform vec3 keyColor; 
uniform vec3 backColor;

// TODO: Objective 7, GLSL lighting

void main(void) {
vec3 spec = ks * (fillColor * pow(max(0, dot(normalForFP, normalize(viewDir + fillingD))), shininess) 
+ backColor * pow(max(0, dot(normalForFP, normalize(viewDir + backDir))), shininess) 
+ keyColor * pow(max(0, dot(normalForFP, normalize(viewDir + keyD))), shininess));

vec3 dif = kd * (backColor * max(0, dot(normalForFP, backDir)) + fillColor * max(0, dot(normalForFP, fillingD)) + keyColor * max(0, dot(normalForFP, keyD)));
              
fragColor = vec4(dif + spec, 1);
}