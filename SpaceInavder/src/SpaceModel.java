import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.*;

public class SpaceModel {
	private Image playerImg, enemyImg, pBulletImg, eBulletImg, BonusImg;
	private int GameScore; // 화면 상에 보여지는 점
	private int endScore; // 내부에서만인식되는 점수
	private int bulletCount;
	private boolean[] isBulletSignal = new boolean[8];
	private boolean[] isEnemyBulletSignal = new boolean[16]; // 적 총알도 하나당 하나의 신호
	private boolean isGameover;
	private GameObject player;
	private GameObject bonus;

	private ArrayList<BulletObject> arrayBullet = new ArrayList<BulletObject>();
	private ArrayList<EnemyObject> arrayEnemy = new ArrayList<EnemyObject>();
	private ArrayList<EnemyBulletObject> arrayEnemyBullet = new ArrayList<EnemyBulletObject>();

	Random r = new Random();

	public SpaceModel() { // 칸 마다 어떤 색으로 칠할 지 지정
		playerImg = new ImageIcon("images/player.png").getImage();
		enemyImg = new ImageIcon("images/enemy.png").getImage();
		pBulletImg = new ImageIcon("images/playerBullet.png").getImage();
		eBulletImg = new ImageIcon("images/enemyBullet.png").getImage();
		BonusImg = new ImageIcon("images/Bonus.png").getImage();
		isGameover = false;
	}

	public void allObjectInfo() {
		player = new PlayerObject(playerImg, 250, 400);

		for (int i = 0; i < 8; i++) {
			arrayBullet.add(new BulletObject(pBulletImg, player.posX, player.posY));
			if (i < 4) {
				arrayEnemy.add(new EnemyObject(enemyImg, (i * 10 + 5) * 10, 10));// 5,15,25,35
			} else {
				arrayEnemy.add(new EnemyObject(enemyImg, ((i - 3) * 10) * 10, 100));// 10,20,30,40
			}
			for (int j = 0; j < 2; j++)
				arrayEnemyBullet.add(new EnemyBulletObject(eBulletImg, arrayEnemy.get(i).posX, arrayEnemy.get(i).posY));
		}
		bonus = new BonusObject(BonusImg, 1000, 0);
	}

	// ----------------- PLAYER BULLET -------------------
	public void PlayerBulletShoot() { // Enemy 8호기가 존재하니 Bullet도 맞춰 8발
		for (int i = 0; i < 8; i++) {

			if (isBulletSignal[i] == false) {// 총알 위치 갱신
				arrayBullet.get(i).locationData(player.posX + 13, player.posY + 2);
			}

			if (isBulletSignal[i] == true) {// 총알 프린트 발사 이미지 출력
				arrayBullet.get(i).Up();
			}
			if (arrayBullet.get(i).posY <= 0) {// 벽 닿으면 처음 위치로
				isBulletSignal[i] = false;
			}

			for (int j = 0; j < 8; j++)// 적 격추시 적정보 삭제
				for (int k = 0; k < 38; k++)
					if (arrayBullet.get(i).posX == arrayEnemy.get(j).posX + k
							&& arrayBullet.get(i).posY <= arrayEnemy.get(j).posY
							&& arrayBullet.get(i).posY + 30 >= arrayEnemy.get(j).posY) {// 총알이 적에 닿았을 시
						arrayEnemy.get(j).deleted();

						for (int n = 0; n < 2; n++)
							arrayEnemyBullet.get(j + n * 8).deleted();
						isBulletSignal[i] = false;
						GameScore += 10;
						endScore += 10;
						gameEnd();
					}
		}
	}

	public void blletSignal() {
		isBulletSignal[bulletCount] = true;
		bulletCount++;
		if (bulletCount == 8) {
			bulletCount = 0;
		}
	}

	public boolean setGameover() {
		return isGameover;
	}

	public void gameEnd() {
		if (endScore >= 80)
			isGameover = true;
	}

	// ----------------- ENEMY -------------------
	int enemySpeedNum = 0;// max값 까지 올라가면 내려가기
	int enemySpeedMax = 1000;// 값이 되었을떄 아래로 내려가고 점점 값이 줄어든다.

	public void EnemyMove() { // Enemy 위 4개 ,아래 4개 위치 지정후 화면 출력

		int rnadomNum = r.nextInt(10);// 적이 램덤으로 좌우로 움직인다

		if (rnadomNum == 0) {
			for (int j = 0; j < 8; j++) {
				if (j < 4) {
					if (arrayEnemy.get(j).posX != j * 10)// 0,10,20,30 좌우 이동 범위
						arrayEnemy.get(j).Left();
				} else {
					if (arrayEnemy.get(j).posX != (j - 3) * 10 - 5)// 5,15,25,35
						arrayEnemy.get(j).Left();
				}
			}
		}
		if (rnadomNum == 1) {
			for (int j = 0; j < 8; j++) {
				if (j > 4) {
					if (arrayEnemy.get(j).posX != j * 10 + 100)// 10,20,30,40
						arrayEnemy.get(j).Right();
				} else {
					if (arrayEnemy.get(j).posX != (j - 3) * 10 + 500)// 15,25,35,45
						arrayEnemy.get(j).Right();
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			if (arrayEnemy.get(i).posY >= 500)
				isGameover=true;
		}
		enemySpeedNum += 20;
		if (enemySpeedNum >= enemySpeedMax) {// 내려오는 속도 점점 빨라지게 맥스값 감소
			for (int j = 0; j < 8; j++) {
				arrayEnemy.get(j).Down();
			}
			enemySpeedNum = 0;
			enemySpeedMax -= 50;
		}
	}

	// ----------------- ENEMY BULLET -------------------
	public void enemyBulletShoot() {// 적 램 덤 총알
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++)
				if (isEnemyBulletSignal[i + j * 8] == false) {// 적이 최대 2발씩 장착 , 기존과 역시 총알당 하나에 신호 매치 총알 16개 신호 16
					arrayEnemyBullet.get(i + j * 8).posX = arrayEnemy.get(i).posX + 15;
					arrayEnemyBullet.get(i + j * 8).posY = arrayEnemy.get(i).posY;
				}
		}

		for (int i = 0; i < 16; i++) {
			if (isEnemyBulletSignal[i] == true) {
				arrayEnemyBullet.get(i).Down();
			}
			if (arrayEnemyBullet.get(i).posY >= 500)
				isEnemyBulletSignal[i] = false;

			for (int j = 0; j < 40; j++)// 플레이어가 총알에 맞았을때
				if (player.posX + j == arrayEnemyBullet.get(i).posX && player.posY == arrayEnemyBullet.get(i).posY) {
					player.deleted();
					isGameover = true;
				}
		}
		int rSignal = r.nextInt(100);// 램덤 숫자를 받아 16이하면 적이 총알 발사 미살일 1,8 1호기 ,2,9 2호기
		if (rSignal < 16)
			isEnemyBulletSignal[rSignal] = true;
	}

	// ----------------- BONUS MOVE -------------------
	public void bonusMove() {
		bonus.Right();
		if (endScore == 30 || endScore == 61) {
			endScore++;
			bonus.posX = 0;

		}

		for (int i = 0; i < 8; i++) {
			for (int k = 0; k < 40; k++) {
				if (arrayBullet.get(i).posX == bonus.posX + k && arrayBullet.get(i).posY <= bonus.posY) {
					bonus.deleted();
					GameScore += 100;
				}
			}
			if (bonus.posX == 500)
				bonus.deleted();
		}
	}

	// ----------------- PLAYER MOVE -------------------
	public void playerLeft() {
		player.Left();
	}

	public void playerRight() {
		player.Right();
	}

	public void playerUp() {
		player.Up();
	}

	public void playerDown() {
		player.Down();
	}

	public GameObject playerInfo() {
		return player;
	}

	public ArrayList enemyInfo() {

		return arrayEnemy;
	}

	public ArrayList bulletInfo() {

		return arrayBullet;
	}

	public ArrayList ebulletInfo() {

		return arrayEnemyBullet;
	}

	public GameObject bonusInfo() {

		return bonus;
	}

	public int GameScore() {
		return GameScore;
	}

	public int endScore() {
		return endScore;
	}

}
