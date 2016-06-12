package com.tjgs.robotevolution.graphics;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Tyler Johnson on 4/29/2016.
 *
 */
public class Shader {

    private static final String TAG = Shader.class.getSimpleName();

    //default vertex shader
    private final String defaultVertexShader =
            "attribute vec4 vPosition;" +
                    "attribute vec2 texCoord;" +
                    "attribute vec4 vertColor;" +
                    "varying vec4 vColor;" +
                    "varying vec2 vTexCoord;" +
                    "uniform mat4 uVPMatrix;" +
                    "void main() {" +
                    "  gl_Position = uVPMatrix * vPosition;" +
                    "  vColor = vertColor;" +
                    "  vTexCoord = texCoord;" +
                    "}";

    //default fragment shader
    private final String defaultFragmentShader =
            "precision highp float;" +
                    "uniform sampler2D texture;" +
                    "varying vec4 vColor;" +
                    "varying vec2 vTexCoord;" +
                    "void main() {" +
                    "  gl_FragColor = vColor * texture2D(texture, vTexCoord);" +
                    "}";

    //location of source files TODO: needs to be switched to resource id
    private String sourceLocation;

    //handle for the openGL shader
    private int shaderHandle;

    public Shader(String source){
        //TODO: implement custom shader loading
        throw new RuntimeException("Constructor not implemented yet");
    }

    /**
     * Creates a new default shader
     */
    public Shader(){
        //generate shaders
        int vertShader = loadShader(GLES20.GL_VERTEX_SHADER, defaultVertexShader);
        int fragShader = loadShader(GLES20.GL_FRAGMENT_SHADER, defaultFragmentShader);

        //create a shader program
        shaderHandle = GLES20.glCreateProgram();

        //combine and link shaders
        GLES20.glAttachShader(shaderHandle, vertShader);
        GLES20.glAttachShader(shaderHandle, fragShader);
        GLES20.glLinkProgram(shaderHandle);
    }

    /**
     * @param name uniform to find
     * @return uniform location handle
     */
    public int getUniformLocation(String name){
        return GLES20.glGetUniformLocation(shaderHandle, name);
    }

    /**
     * @param attrib attribute to find
     * @return attribute location handle
     */
    public int getAttribLocation(String attrib){
        return GLES20.glGetAttribLocation(shaderHandle, attrib);
    }


    public void setUniformVariable(int handle, float x){
        GLES20.glUniform1f(handle, x);
    }

    public void setUniformVariable(int handle, float x, float y){
        GLES20.glUniform2f(handle, x, y);
    }

    public void setUniformVariable(int handle, float x, float y, float z){
        GLES20.glUniform3f(handle, x, y, z);
    }

    public void setUniformVariable(int handle, float x, float y, float z, float w){
        GLES20.glUniform4f(handle, x, y, z, w);
    }

    public void setUniformVariable(int handle, float[] mat){
        GLES20.glUniformMatrix4fv(handle, 1, false, mat, 0);
    }

    /**
     * Starts shader program
     */
    public void useProgram(){
        GLES20.glUseProgram(shaderHandle);
    }

    /**
     * Stops shader
     */
    public void quitProgram(){
        GLES20.glUseProgram(0);
    }

    /**
     * Loads and compiles a shader given the type and source
     * @param type shader type (GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER)
     * @param shaderCode shader source code
     * @return handle to shader
     */
    protected static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] params = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, params, 0);

        if(params[0] == GLES20.GL_FALSE){
            Log.d(TAG, "Shader compilation failed: " + params[0] + "\n" + shaderCode);
            throw new RuntimeException("Shader failed to compile");
        }else{
            Log.d(TAG, "Shader compilation win");
        }

        return shader;
    }

}
