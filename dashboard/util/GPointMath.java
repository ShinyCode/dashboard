package dashboard.util;

import acm.graphics.GPoint;

/**
 * Provides methods to execute vector operations on GPoints, which are treated as
 * vectors with their tails at the origin.
 * 
 * @author Mark Sabini
 *
 */
public class GPointMath
{
	/**
	 * Returns the 2-norm of the specified vector.
	 * 
	 * @param p the endpoint of the vector
	 * @return the 2-norm of the specified vector
	 */
	public static double norm(GPoint p)
	{
		return Math.sqrt(normsq(p));
	}
	
	/**
	 * Returns the square of the 2-norm of the specified vector.
	 * 
	 * @param p the endpoint of the vector
	 * @return the square of the 2-norm of the specified vector
	 */
	public static double normsq(GPoint p)
	{
		return dot(p, p);
	}
	
	/**
	 * Returns the dot product of the two specified vectors.
	 * 
	 * @param p1 the endpoint of the first vector
	 * @param p2 the endpoint of the second vector
	 * @return the dot product of the two specified vectors
	 */
	public static double dot(GPoint p1, GPoint p2)
	{
		return p1.getX() * p2.getX() + p1.getY() * p2.getY();
	}
	
	/**
	 * Returns the cross product of the two specified vectors.
	 * 
	 * @param p1 the endpoint of the first vector
	 * @param p2 the endpoint of the second vector
	 * @return the cross product of the two specified vectors
	 */
	public static double cross(GPoint p1, GPoint p2)
	{
		return p1.getX() * p2.getY() - p1.getY() * p2.getX();
	}
	
	/**
	 * Returns the sum of the two specified vectors
	 * 
	 * @param p1 the endpoint of the first vector
	 * @param p2 the endpoint of the second vector
	 * @return the endpoint of the sum of the two specified vectors
	 */
	public static GPoint sum(GPoint p1, GPoint p2)
	{
		return new GPoint(p1.getX() + p2.getX(), p1.getY() + p2.getY());
	}
	
	/**
	 * Returns the difference of the two specified vectors
	 * 
	 * @param p1 the endpoint of the first vector
	 * @param p2 the endpoint of the second vector
	 * @return the endpoint of the difference of the two specified vectors
	 */
	public static GPoint diff(GPoint p1, GPoint p2)
	{
		return new GPoint(p1.getX() - p2.getX(), p1.getY() - p2.getY());
	}
}
