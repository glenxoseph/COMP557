#version 400

uniform vec3 kd; 
uniform vec3 ks;

uniform vec3 viewDir;
uniform vec3 fillDir;
uniform vec3 keyDir;
uniform vec3 backDir; 
uniform float shininess; 
uniform vec3 fillColor; 
uniform vec3 keyColor; 
uniform vec3 backColor;

in vec3 normalForFP;
out vec4 fragColor;

// TODO: Objective 7, GLSL lighting

void main(void) {
vec3 diffuse = kd * fillColor * max(0, dot(normalForFP, fillDir))
             + kd * backColor * max(0, dot(normalForFP, backDir))
             + kd * keyColor * max(0, dot(normalForFP, keyDir));

vec3 specular = ks * fillColor* pow(max(0, dot(normalForFP, normalize(viewDir+fillDir))), shininess);
              + ks * backColor * pow(max(0, dot(normalForFP, normalize(viewDir+backDir))), shininess);
              + ks * keyColor * pow(max(0, dot(normalForFP, normalize(viewDir+keyDir))), shininess);
              
              fragColor = vec4(diffuse + specular, 1);
}