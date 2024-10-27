package me.emil.game.object;

/**
 * Sets the path coordinates.
 */
public class PathPoint {

	private int xCord;
	private int yCord;

	/**
	 * Sets the x and y coordinates.
	 */
	public PathPoint(int xCord, int yCord) {
		this.xCord = xCord;
		this.yCord = yCord;
	}

	public int getxCord() {
		return xCord;
	}

	public void setxCord(int xCord) {
		this.xCord = xCord;
	}

	public int getyCord() {
		return yCord;
	}

	public void setyCord(int yCord) {
		this.yCord = yCord;
	}
}
