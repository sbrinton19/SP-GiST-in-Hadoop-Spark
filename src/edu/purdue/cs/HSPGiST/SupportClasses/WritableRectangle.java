package edu.purdue.cs.HSPGiST.SupportClasses;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

import org.apache.hadoop.io.WritableComparable;

/**
 * Implementation of WritableRectangles
 * @author Stefan Brinton
 *
 */
public class WritableRectangle implements WritableComparable<WritableRectangle>, Copyable<WritableRectangle>{
	private double x;		
	private double y;
	private double h;
	private double w;
	/**
	 * Default Constructor
	 */
	public WritableRectangle() {}

	/**
	 * Setups a rectangle with the given parameters
	 * @param x The lower left corner of the rectangle's x value
	 * @param y The lower left corner of the rectangle's y value
	 * @param h The height of the rectangle
	 * @param w The width of the rectangle
	 */
	public WritableRectangle(double x, double y, double h, double w){
		this.y = y;
		this.h = h;
		this.x = x;
		this.w = w;
		if(h < 0){
			this.y = this.y + h;
			this.h = -h;
		}
		if(w < 0){
			this.x = this.x + w;
			this.w = -w;
		}
	}
	
	public void readFields(DataInput in) throws IOException {
		x = in.readDouble();
		y = in.readDouble();
		h = in.readDouble();
		w = in.readDouble();
	}
	
	public void write(DataOutput out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(h);
		out.writeDouble(w);
	}
	
	/**
	 * Public getter methods 
	 */
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getH(){
		return h;
	}
	
	public double getW(){
		return w;
	}
	
	/**
	 * Public Setter methods
	 */
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setH(double h){
		this.h = h;
	}
	
	public void setW(double w){
		this.w = w;
	}
	
	/**
	 * Check if a point lies within a rectangle's bounds
	 * @param p The point to check
	 * @return True if a point is within a rectangle
	 */
	public boolean contains(WritablePoint p){
		if(p == null)
			return false;
		return x < p.getX() && p.getX() < x+w && y < p.getY() && p.getY() < y+h;
	}
	
	/**
	 * Checks if a given rectangle is bounded by another
	 * Does not return true for equivalent rectangles
	 * @param r The rectangle that may be a subspace of this one
	 * @return True if r is contained within this one (may share a common lower left corner)
	 */
	public boolean contains(WritableRectangle r){
		if(r == null)
			return false;
		if(x <= r.x && y<=r.y && h > r.h && w > r.w)
			return true;
		return false;
	}
	
	public int hashCode() {
		return Objects.hash(x, y, h, w); 
	}
	
	public boolean equals(Object o){
		if(!(o instanceof WritableRectangle))
			return false;
		WritableRectangle other = (WritableRectangle) o;
		return this.x == other.x && this.y == other.y && this.h == other.h && this.w == other.w;
	}
	
	public int compareTo(WritableRectangle o){
		double x1 = this.x;
		double x2 = o.x;
		return (x1<x2 ? -1 : (x1==x2 ? 0 : 1));
	}
	
	public String toString() {
		return "X: " + Double.toString(x) + " Y: " + Double.toString(y) + " H: " + Double.toString(h)+ " W: " + Double.toString(w);
	}

	@Override
	public WritableRectangle copy() {
		return new WritableRectangle(x,y,h,w);
	}
}

