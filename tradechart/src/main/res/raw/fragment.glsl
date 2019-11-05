precision highp float;

uniform vec2 uResolution;
uniform float uTime;

void main() {
    vec3 col = vec3(0.2, 0.1, 0.643) ;
    gl_FragColor = vec4(col, 1.0);
}