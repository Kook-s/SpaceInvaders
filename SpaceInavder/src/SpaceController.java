import java.awt.*;
import java.io.*;
import javax.swing.*;

public class SpaceController implements Runnable {
	private SpaceModel model;
	private SpaceView view;
	private int hightScore;
	private int gameScore;

	public SpaceController(SpaceModel model, SpaceView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void run() {
		callGame();
		model.allObjectInfo();

		while (!model.setGameover()) {
			GameTiming();
		
			gameScore = model.GameScore();
			model.EnemyMove();
			model.enemyBulletShoot();
			model.PlayerBulletShoot();
			model.bonusMove();
			
			view.upDate();
			view.GameScore(model.GameScore(), hightScore);
			view.bonusInfo(model.bonusInfo());
			view.playerInfo(model.playerInfo());
			view.enemyInfo(model.enemyInfo());
			view.bulletInfo(model.bulletInfo());
			view.ebulletInfo(model.ebulletInfo());
		}
		if (gameScore >= hightScore)
			saveGame();
		new yesORno(model.endScore());
	}

	public void GameTiming() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException ex) {
//			ex.printStackTrace();
			System.out.println("--- Space Invader Game Start ---");
		}
	}

	public void saveGame() {//// 쓰기
		try {
			BufferedWriter save = new BufferedWriter(new FileWriter("text.txt"));
			String inOut = Integer.toString(model.GameScore());
			save.write(inOut);
			save.close();

		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

	public void callGame() {// 읽기
		try {
			BufferedReader call = new BufferedReader(new FileReader("text.txt"));
			hightScore = Integer.parseInt(call.readLine());
			call.close();

		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

}

class yesORno {

	private JFrame jFrame;
	JTextField tf = new JTextField(10);
	int result;
	int endScore;
	String winlose;

	yesORno(int Score) {
		this.endScore = Score;
		initJFrame();
	}

	private void initJFrame() {
		jFrame = new JFrame();
		jFrame.setLayout(new BorderLayout());
		jFrame.setSize(720, 540);
		jFrame.setUndecorated(true);
		jFrame.setBackground(new Color(0, 0, 0, 0));
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);

		if (endScore <= 80) {
			winlose = "LOSE WIN";
		}
		if (endScore >= 80) {
			winlose = "WIN WIN";
		}
		result = JOptionPane.showConfirmDialog(jFrame.getContentPane(), "계속할 것입니까?", winlose,
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.CLOSED_OPTION)
			tf.setText("Just Closed without Selection");

		else if (result == JOptionPane.YES_OPTION) {
			tf.setText("Yes");
			new SpaceinvaderInfo(720, 540);

		} else {
			tf.setText("No");
//			saveGame();
			System.exit(0);
		}

	}

//	public void saveGame() {//// 쓰기
//		try {
//			BufferedWriter save = new BufferedWriter(new FileWriter("text.txt"));
//			String inOut = Integer.toString(0);
//			save.write(inOut);
//			save.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
