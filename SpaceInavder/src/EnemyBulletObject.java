import java.awt.Image;
import java.util.ArrayList;

class EnemyBulletObject extends GameObject {
	EnemyBulletObject(Image image, int posX, int posY) {
		super(image, posX, posY);
	}

	public void deleted() {
		posX = 1000;
		posY = 0;
	}

	public void Down() {
		if(posY<=500&&posX<=500)
		posY += 10;
	}

//	public void locationData(ArrayList<EnemyObject> arrayEnemy, ArrayList<EnemyBulletObject> arrayEnemyBullet, int i) {
//		for (int j = 0; j < 2; j++) {
//			arrayEnemyBullet.get(i + j * 8).posX = arrayEnemy.get(i).posX + 15;
//			arrayEnemyBullet.get(i + j * 8).posY = arrayEnemy.get(i).posY;
//		}
//	}
}