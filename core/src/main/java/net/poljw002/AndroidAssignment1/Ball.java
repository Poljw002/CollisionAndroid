package net.poljw002.AndroidAssignment1;

public class Ball {
    public int x;
    public int y;
    public double[] velocity;
    public double decelerate = 1.01;
    public int threshhold = 5;

    public Ball(){
        velocity = new double[2];

    }
    public void accelerate(int[] input){
        //if both inputs are 0, it will decelerate the ball until both velocities are 0
        if (input[0] == 0 & input[1] == 0){
            if (velocity[0] != 0 ){
                velocity[0] /= decelerate;
                if(Math.abs(velocity[0]) <= threshhold){
                    velocity[0] = 0;
                }
            }
            if (velocity[1] != 0 ){
                velocity[1] /= decelerate;
                if(Math.abs(velocity[1]) <= threshhold){
                    velocity[1] = 0;
                }
            }
        } else{
            //increases the velocities by either 10 or -10
            velocity[0] += input[0]*10;
            velocity[1] += input[1]*10;
        }

    }
    public void move(){
        x += (int) Math.floor(velocity[0]);
        y += (int) Math.floor(velocity[1]);
    }
}
