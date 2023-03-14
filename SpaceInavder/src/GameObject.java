import java.awt.*;

abstract class GameObject { // Player,Enemy,Bullet,EnemtBullet 상속
	protected int posX;
	protected int posY;
	protected Image image;

	public GameObject(Image image, int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.image = image;
	}

	public void Left() {// 공통으로 사용하는 상하좌우, 삭제 ,위치 갱신
	}

	public void Right() {
	}

	public void Up() {
	}

	public void Down() {
	}

	public void deleted() {
	}// 필드에서 지우기

	public void locationupData() {
	}// 위치 업데이트
}




