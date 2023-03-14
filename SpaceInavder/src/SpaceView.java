import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class SpaceView extends JPanel {

	private static final double GAME_SCORE_AREA_RATIO = 0.75;
	private ArrayList<Image> imgInfo = new ArrayList<Image>();
	private int width;
	private int height;
	private int gameWidth;
	private int sideWidth;

	private int playPosX, playPosY;

	private int enemyPosX[] = new int[8];
	private int enemyPosY[] = new int[8];

	private int bulletPosX[] = new int[8];
	private int bulletPosY[] = new int[8];

	private int ebulletPosX[] = new int[16];
	private int ebulletPosY[] = new int[16];

	private Image bonusImg;
	private int bonusPosX;
	private long GameScore;
	private long highrScore;

	private Image logo = new ImageIcon("images/logo.png").getImage();

	public SpaceView(int width, int height) {
		initDimensions(width, height);
		this.setPreferredSize(new Dimension(this.width, this.height));
	}

	private void initDimensions(int width, int height) { // 프레임 창 생성
		this.width = width;
		this.height = height;
		this.gameWidth = (int) Math.floor(width * GAME_SCORE_AREA_RATIO);
		this.sideWidth = width - this.gameWidth - 1;
	}

	public void playerInfo(GameObject a) {
		imgInfo.add(a.image);
		playPosX = a.posX;
		playPosY = a.posY;
	}

	public void enemyInfo(ArrayList<GameObject> a) {
		for (int i = 0; i < 8; i++) {
			imgInfo.add(a.get(i).image);
			enemyPosX[i] = a.get(i).posX;
			enemyPosY[i] = a.get(i).posY;
		}
	}

	public void bulletInfo(ArrayList<GameObject> a) {
		for (int i = 0; i < 8; i++) {
			imgInfo.add(a.get(i).image);
			bulletPosX[i] = a.get(i).posX;
			bulletPosY[i] = a.get(i).posY;
		}
	}

	public void ebulletInfo(ArrayList<GameObject> a) {
		for (int i = 0; i < 16; i++) {
			imgInfo.add(a.get(i).image);
			ebulletPosX[i] = a.get(i).posX;
			ebulletPosY[i] = a.get(i).posY;
		}
	}

	public void bonusInfo(GameObject a) {
		bonusImg = a.image;
		bonusPosX = a.posX;
	}

	public void GameScore(int GameScore, int a) {
		this.GameScore = GameScore;
		highrScore = a;
	}

	public void objectView(Graphics2D g2d) {

		for (int i = 0; i < 16; i++)
			g2d.drawImage(imgInfo.get(i + 17), ebulletPosX[i], ebulletPosY[i], this);
		for (int i = 0; i < 8; i++) {
			g2d.drawImage(imgInfo.get(i + 1), enemyPosX[i], enemyPosY[i], this);
			g2d.drawImage(imgInfo.get(i + 9), bulletPosX[i], bulletPosY[i], this);
			// player bullet
		} // 17,18,19,20,21,22,23,24 ,25,26,27,28,29,30,31,32

		g2d.drawImage(imgInfo.get(0), playPosX, playPosY, this);// playerImg
		g2d.drawImage(bonusImg, bonusPosX, 20, this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		drawGameGrid(g2d);
		fillSidePanel(g2d);
		drawScore(g2d);
		objectView(g2d);
	}

	private void fillSidePanel(Graphics2D g2d) { // 사이드 색
		g2d.setPaint(new Color(0, 8, 52)); // 군청색
		g2d.fillRect(width - sideWidth, 0, sideWidth, height);
	}

	private void drawScore(Graphics2D g2d) { // 점수판 색
		g2d.setColor(new Color(255, 233, 0));
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

		g2d.drawString("SCORE:", this.width - this.sideWidth + 25, metrics.getHeight() * 5);
		String scoreString = Long.toString(this.GameScore);
		g2d.drawString(scoreString, this.width - this.sideWidth + 85, metrics.getHeight() * 5);

		g2d.drawString("TOP SCORE:", this.width - this.sideWidth + 25, metrics.getHeight() * 6);
		String scoreStrin = Long.toString(highrScore);
		g2d.drawString(scoreStrin, this.width - this.sideWidth + 125, metrics.getHeight() * 6);

		g2d.drawString("by.kooks", this.width - this.sideWidth + 100, metrics.getHeight() * 28);
		g2d.drawImage(logo, this.width - this.sideWidth + 15, metrics.getHeight() * 15, this);
	}

	private void drawGameGrid(Graphics2D g2d) {
		g2d.setPaint(Color.BLACK);// 그리드 바탕 검정 색
		g2d.fillRect(0, 0, gameWidth, height);
	}

	public void upDate() {
		repaint();
	}
}
