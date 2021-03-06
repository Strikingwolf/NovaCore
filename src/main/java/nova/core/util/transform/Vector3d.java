package nova.core.util.transform;

/**
 * A double implementation of Vector3. Vector3 is an immutable quantity that holds an x, y and z value.
 * @author Calclavia
 */
@SuppressWarnings("rawtypes")
public class Vector3d extends Vector3<Vector3d> {
	public static final Vector3d zero = new Vector3d(0, 0, 0);
	public static final Vector3d one = new Vector3d(1, 1, 1);
	public static final Vector3d xAxis = new Vector3d(1, 0, 0);
	public static final Vector3d yAxis = new Vector3d(0, 1, 0);
	public static final Vector3d zAxis = new Vector3d(0, 0, 1);

	public final double x, y, z;

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public Vector3d add(Vector3 other) {
		return new Vector3d(x + other.xd(), y + other.yd(), z + other.zd());
	}

	@Override
	public Vector3d add(double other) {
		return new Vector3d(x + other, y + other, z + other);
	}

	@Override
	public Vector3d multiply(Vector3 other) {
		return new Vector3d(x * other.xd(), y * other.yd(), z * other.zd());
	}

	@Override
	public Vector3d multiply(double other) {
		return new Vector3d(x * other, y * other, z * other);
	}

	@Override
	public Vector3d reciprocal() {
		return new Vector3d(1 / x, 1 / y, 1 / z);
	}

	@Override
	public Vector3d cross(Vector3 other) {
		return new Vector3d(y * other.zd() - z * other.yd(), z * other.xd() - x * other.zd(), x * other.yd() - y * other.xd());
	}

	public Vector3d perpendicular() {
		if (z == 0) {
			return zCross();
		}

		return xCross();
	}

	public Vector3d xCross() {
		return new Vector3d(0, z, -y);
	}

	public Vector3d zCross() {
		return new Vector3d(-y, x, 0);
	}

	@Override
	public Vector3d max(Vector3 other) {
		return new Vector3d(Math.max(xd(), other.xd()), Math.max(yd(), other.yd()), Math.max(zd(), other.zd()));
	}

	@Override
	public Vector3d min(Vector3 other) {
		return new Vector3d(Math.min(xd(), other.xd()), Math.min(yd(), other.yd()), Math.min(zd(), other.zd()));
	}

	@Override
	public int hashCode() {
		long x = Double.doubleToLongBits(this.x);
		long y = Double.doubleToLongBits(this.y);
		long z = Double.doubleToLongBits(this.z);
		long hash = (x ^ (x >>> 32));
		hash = 31 * hash + y ^ (y >>> 32);
		hash = 31 * hash + z ^ (z >>> 32);
		return (int) hash;
	}

	@Override
	public int xi() {
		return (int) x;
	}

	@Override
	public int yi() {
		return (int) y;
	}

	@Override
	public int zi() {
		return (int) z;
	}

	@Override
	public double xd() {
		return x;
	}

	@Override
	public double yd() {
		return y;
	}

	@Override
	public double zd() {
		return z;
	}

	public Vector3i toInt() {
		return new Vector3i(xi(), yi(), zi());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector3) {
			Vector3 v = ((Vector3) obj);
			return xd() == v.xd() && yd() == v.yd() && zd() == v.zd();
		}
		return this == obj;
	}

	@Override
	public String toString() {
		return "[Vector3d] " + x + ", " + y + ", " + z;
	}
}
