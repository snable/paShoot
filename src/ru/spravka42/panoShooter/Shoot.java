package ru.spravka42.panoShooter;

public class Shoot {

	private float yaw;
	private float pitch;
	
	public Shoot(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Shoot(String value, String value2) {
		this(Float.parseFloat(value), Float.parseFloat(value2));
	}

	public String toString() {
		return new String("Yaw: "+Float.toString(this.yaw)+
				"; Pitch: "+Float.toString(this.pitch));
	}
        
        public float getYaw() {
            return yaw;
        }
        
        public float getPitch() {
            return pitch;
        }

}
