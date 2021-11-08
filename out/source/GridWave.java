import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GridWave extends PApplet {

Wave wave1;
Wave wave2;
Wave wave3;
public void setup() {
    
    background(255);

    wave1 = new Wave(30, 0, PI/30);
    wave2 = new Wave(20, PI/2, PI/40);
    wave3 = new Wave(10, PI/4, PI/50);
}

public void draw() {
    background(255);
    wave1.display();
    wave2.display();
    wave3.display();
}

class Wave {
    Seg[] segs;
    int gridsize;
    int count = 0;
    float angle;
    float angle_delta;
    public Wave(int gs, float sa, float da)
    {
        gridsize = gs + 1;
        angle = sa;
        angle_delta = da;
        segs = new Seg[gridsize*gridsize];
        for (int i = 1; i < gridsize; i++) {
            for (int j = 1; j < gridsize; j++){
                PVector vec = new PVector(1.5f*width*i/(gridsize), 1.5f*height*j/(gridsize));
                Seg seg = new Seg(vec, angle);
                // seg.display();
                segs[count] = seg;
                angle += angle_delta;
                count += 1;
            }
            angle = 0;
        }
    } 

    public void display(){
        translate(width/2, -height/2);
        rotate(radians(45));
        for (int i = 0; i < count; i++){
            segs[i].move();
            segs[i].display();
        }   
    }
}

class Seg {
    PVector loc;
    float length = width/(60);
    float angle; 
    float sw = 1;
    public Seg(float x, float y, float a){
        loc = new PVector(x, y, 0);
        angle = a;
    }
    public Seg(PVector loc0, float a){
        loc = loc0;
        angle = a;
    }
    public Seg(float x, float y, float z, float a){
        loc = new PVector(x, y, z);
        angle = a;
    }

    public void display(){
        pushMatrix();
        translate(loc.x, loc.y);
        rotate(angle);
        float smooth = (abs(angle - PI/2) / (PI/2));
        fill(smooth * 255 - 50);
        noStroke();
        ellipse(0, 0, (1.0f/(smooth+1))*10, (1.0f/(smooth+1))*10);
        stroke(0);
        line(-length, -length, length, length);
        popMatrix();
    }
    public void move(){
        angle += radians(2);
        if (angle > PI){
            angle -= PI;
        }
        // loc = loc.add(new PVector(1, 1, 0));
    }
}
  public void settings() {  size(512, 512); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GridWave" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
