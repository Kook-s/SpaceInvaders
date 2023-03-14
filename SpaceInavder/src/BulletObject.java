import java.awt.Image;

class BulletObject extends GameObject {
	BulletObject(Image image, int posX, int posY) {
		super(image, posX, posY);
	}

	public void Up() {
		if (posY >= 0)
			posY -= 10;
	}

	public void locationData(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
}