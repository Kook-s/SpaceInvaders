import java.awt.Image;

class BonusObject extends GameObject {
	BonusObject(Image image, int posX, int posY) {
		super(image, posX, posY);
	}

	public void Right() {
		if (posX <= 500)
			posX += 5;
	}

	public void deleted() {
		posX = 1000;
		posY = 0;
	}
}