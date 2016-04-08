package particles;

import org.lwjgl.util.vector.Vector3f;

public class Particle {

	private Vector3f position; 
	private Vector3f velocity; 
	private float gravityEffect; 
	private float lifeLength; 
	private float rotation; 
	private float scale; 
	
	private float elapsedTime = 0;

	public Particle(Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
	} 
	
	

}
