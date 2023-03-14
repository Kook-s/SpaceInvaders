import java.awt.Image;

class EnemyObject extends GameObject {
	EnemyObject(Image image, int posX, int posY) {
		super(image, posX, posY);
	}

	public void Left() {
		if (posX >= 0&&posX<=500)
			posX -= 5;
	}

	public void Right() {
		if (posX <= 500)
			posX += 5;
	}

	public void Down() {
		if (posY <= 500&&posX<=500)
			posY += 20;
	}

	public void deleted() {
		posX = 1000;
		posY = 0;
	}
}