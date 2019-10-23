precision highp float;

uniform vec2 uResolution;
uniform float uTime;
uniform startPosition;
uniform finishPosition;

void main() {
    vec3 col = vec3(0.2, 0.1, 0.643)  / rz;
    gl_FragColor = vec4(col, 1.0);
}