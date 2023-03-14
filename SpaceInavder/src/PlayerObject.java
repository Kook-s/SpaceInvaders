import java.awt.Image;

public class PlayerObject extends GameObject {
	
	PlayerObject(Image image, int posX, int posY) {
		super(image, posX, posY);
	}

	public void Left() {
		if (posX >= 0)
			posX -= 10;
	}

	public void Right() {
		if (posX <= 500)
			posX += 10;
	}

	public void Up() {
		if (posY >= 0)
			posY -= 10;
	}

	public void Down() {
		if (posY <= 500)
			posY += 10;
	}

	public void deleted() {
		posX = 1000;
		posY = 0;
	}
}