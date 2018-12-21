
public class Location {
	private int x;
	private int y;
	
	public Location (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if(o instanceof Location) {
			result = this.equeles(((Location) o).getX(), ((Location) o).getY());
		}
		
		return result;
	}
	
	public boolean equeles(int x, int y) {
		return this.x==x && this.y==y;
	}
	
	public boolean isNear(Location l) {
		return isNear(l.getX(), l.getY());
	}
	
	public boolean isNear(int x, int y) {
		return ((((this.x+1 == x) || (this.x-1 == x)) && this.y == y) ||
				(((this.y+1 == y) || (this.y-1 == y)) && this.x == x));
	}
}
