package johd.andr.opengles2harjtyo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Random;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private Triangle mTriangle;
    private float previousX;
    private float previousY;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:

                // Make the triangle to change color here
                float r = 0.5f;
                float g = 0.5f;
                float b = 0.5f;
                float c = 0.5f;

                float dx = x - previousX;
                float dy = y - previousY;

                // Only change color if under mid line
                if (y > getHeight() / 2) {
                    Random rand = new Random();
                    for (int i = 0; i < 4; i++){
                        float random = rand.nextFloat();

                        if (i==1) { r=random;
                        }
                        else if (i==2){ g= random;
                        }
                        else if (i ==3){
                            b=random;
                        }

                        else {
                            c = random;
                        }
                    }

                    float color[] = { r, g, b, c };
                    mTriangle = renderer.getTriangle();
                    mTriangle.setColor(color);
                    break;
                }


            case MotionEvent.ACTION_MOVE:

                dx = x - previousX;
                dy = y - previousY;
                // Reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }
                // Reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }
                renderer.setAngle(
                        renderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
                break;


        }

        previousX = x;
        previousY = y;
        return true;
    }

}
