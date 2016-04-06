package entities;
 
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
 
public class Camera {
  
	/*	
	 *  the idea of the camera is to allow the playermodel rotate in 360 but the camera is stuck on a 2D plane and panning to where the character is facing
	 * 	for example: character facing left face will pan the camera to the left only stopping at the character's back
	 *  if facing front or back from user perspective the camera centers on the playermodel
	 */
	
	//camera from player
	private float distanceFromPlayer = 50;
	//camera circle around player
	private float angleAroundPlayer = 0;
	
    private Vector3f position = new Vector3f(0,5,0);
    
    //camera angle looking at player
    private float pitch = 20;
    
    private float yaw ;
    private float roll;
     
    private Player player;
    
    public Camera(Player player){
    	this.player = player;
    }
     
    public void move(){
       calculateZoom();
       calculatePitch();
       calculateAngleAroundPlayer();
       float horizontalDistance = calculateHorizontalDistance();
       float verticalDistance = calculateVerticalDistance();
       calculateCameraPosition(horizontalDistance, verticalDistance);
       //this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
    
    private void calculateCameraPosition(float horizDistance, float verticDistance){
    	float theta = player.getRotY() + angleAroundPlayer;
    	float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
    	float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
    	position.x = player.getPosition().x - offsetX;
    	position.z = player.getPosition().z - offsetZ;
    	position.y = player.getPosition().y + verticDistance;
    }
    
    private float calculateHorizontalDistance(){
    	return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }
     
    private float calculateVerticalDistance(){
    	return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }
    
    private void calculateZoom(){
   // 	float zoomLevel = Mouse.getDWheel() * 0.1f;
   //	distanceFromPlayer -= zoomLevel;
    }
     
    private void calculatePitch(){
   // 	if(Mouse.isButtonDown(1)){
   //		float pitchChange = Mouse.getDY() * 0.1f;
   // 		pitch -= pitchChange;
//    	}
    }

    private void calculateAngleAroundPlayer(){
    	if(Mouse.isButtonDown(0)){
    		float angleChange = Mouse.getDX() * 0.3f;
    		angleAroundPlayer -= angleChange;
    	}
    }
 
}