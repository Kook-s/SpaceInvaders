import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class SpaceinvaderInfo {
	private SpaceController controller;
	private SpaceModel model;
	private SpaceView view;
	private JFrame jFrame;
	private int width;
	private int height;
	private boolean upP = false;
	private boolean downP = false;
	private boolean leftP = false;
	private boolean rightP = false;
	private boolean spaceP = false;

	SpaceinvaderInfo(int width, int height) {
		this.width = width;
		this.height = height;
		initMVC();
		initJFrame();
		new Thread(controller).start();
	}

	private void initMVC() {
		view = new SpaceView(width, height);
		model = new SpaceModel();
		controller = new SpaceController(model, view);
	}

	private void initJFrame() {
		jFrame = new JFrame();
		jFrame.add(view);
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setTitle("SpaveInvader Gmae");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {

				case KeyEvent.VK_LEFT:
					leftP = true;
					break;

				case KeyEvent.VK_RIGHT:
					rightP = true;
					break;

				case KeyEvent.VK_UP:
					upP = true;
					break;

				case KeyEvent.VK_DOWN:
					downP = true;
					break;
				case KeyEvent.VK_SPACE:
					if (spaceP == false)
						model.blletSignal();
					spaceP = true;
					break;
				}
				if (upP) {
					model.playerUp();
				}
				if (downP) {
					model.playerDown();
				}
				if (leftP) {
					model.playerLeft();
				}
				if (rightP) {
					model.playerRight();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					upP = false;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downP = false;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftP = false;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightP = false;
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					spaceP = false;
				}
			}
		});
		jFrame.setVisible(true);
	}
}

public class Spaceinvader {
	private JFrame jFrame;
	private JButton startBtn;
	private JLabel startScreen;

	public Spaceinvader() {
		ImageIcon startImage = new ImageIcon("images/logo.png");
		Image startImg = startImage.getImage().getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH);
		startImage = new ImageIcon(startImg);
		startBtn = new JButton("START GAME");
		startScreen = new JLabel(startImage);
		initJFrame();
	}

	private void initJFrame() {
		jFrame = new JFrame();
		jFrame.setLayout(new BorderLayout());
		jFrame.add(startBtn, BorderLayout.SOUTH);
		jFrame.add(startScreen, BorderLayout.CENTER);
		jFrame.getContentPane().setBackground(Color.black);
		jFrame.setResizable(false);
		jFrame.setSize(720, 520);
		jFrame.setTitle("SpaveInvader Gmae");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);

		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SpaceinvaderInfo(720, 520);
				jFrame.setVisible(false);
			}
		});
	}

	public static void main(String[] argv) {
		new Spaceinvader();
	}
}