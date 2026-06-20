package net.poljw002.AndroidAssignment1;

public class Ball {
    public int x;
    public int y;
    public double[] velocity;
    public double decelerate = 1.05;
    public double threshhold = 0.25;

    public Ball(){
        velocity = new double[2];

    }
    public void accelerate(int[] input){
        if (input[0] == 0 & input[1] == 0){
            if (velocity[0] != 0 ){
                velocity[0] /= decelerate;
                if(velocity[0] <= threshhold){
                    velocity[0] = 0;
                }
            }
            if (velocity[1] != 0 ){
                velocity[1] /= decelerate;
                if(velocity[1] <= threshhold){
                    velocity[1] = 0;
                }
            }
        } else{
            velocity[0] += input[0]*10;
            velocity[1] += input[1]*10;
        }

    }
}
