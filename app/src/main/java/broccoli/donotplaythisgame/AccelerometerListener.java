package broccoli.donotplaythisgame;

/**
 * Created by ZenMatt on 10/20/2014.
 */
public interface AccelerometerListener {
    public void onAccelerationChanged(float x, float y, float z);
    public void onShake(float force);
}
