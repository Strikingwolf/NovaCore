package nova.core.render;

import nova.core.util.transform.Vector2d;
import nova.core.util.transform.Vector3d;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Calclavia, Kubuxu, inspired by ChickenBones
 */
public class Vertex {
	public final Vector3d vec;
	public final Vector2d uv;

	/**
	 * A RGC color value from 0 to 1.
	 */
	public final Vector3d color;

	public Vertex(Vector3d vert, Vector2d uv) {
		this.vec = vert;
		this.uv = uv;
		this.color = Vector3d.one;
	}

	public Vertex(double x, double y, double z, double u, double v) {
		this(new Vector3d(x, y, z), new Vector2d(u, v));
	}

	public String toString() {
		MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
		return "Vertex5[" + new BigDecimal(vec.x, cont) + ", " + new BigDecimal(vec.y, cont) + ", " + new BigDecimal(vec.z, cont) + "]" +
			"[" + new BigDecimal(uv.x, cont) + ", " + new BigDecimal(uv.y) + "]";
	}
}