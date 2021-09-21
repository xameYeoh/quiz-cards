import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class QuizCardPlayer {

	private JFrame frame;
	private TextArea display;
	private boolean isShowAnswer;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private JButton nextButton;
	private int currentCardIndex;

	QuizCardPlayer(){
		display = new TextArea();
		cardList = new ArrayList<QuizCard>();
	}
	
	public static void main(String[] args) {
		new QuizCardPlayer().go();
	}

	private void go() {
		frame = setUpFrame(new JFrame("Quiz Card Player"));

		frame.setVisible(true);
	}

	private JFrame setUpFrame(JFrame frame) {
		JPanel panel = prepare(new JPanel());
		JMenuBar menu = prepare(new JMenuBar());

		frame.setJMenuBar(menu);
		frame.getContentPane().add(panel);
		frame.setSize(500, 600);

		return frame;
	}

	private JPanel prepare(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane pane = prepare(display).getScrollPane();
		nextButton = prepare(new JButton());
		
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		panel.add(pane);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(nextButton);

		QuizCardBuilder.centerComponentsInOuter(panel);

		return panel;
	}

	private JButton prepare(JButton button) {
		button.setText("Show question");
		button.addActionListener(new NextCardListener());

		return button;
	}

	private TextArea prepare(TextArea display) {
		display.setLineWrap(true);
		display.setEditable(false);

		return display;
	}

	private JMenuBar prepare(JMenuBar menuBar) {
		JMenu menu = new JMenu("File");
		JMenuItem loadFile = new JMenuItem("Load file");

		loadFile.addActionListener(new LoadFileListener());
		menu.add(loadFile);
		menuBar.add(menu);

		return menuBar;
	}

	private void toggleNextCard() {
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
	}

	private void showAnswerToCurrentCart() {
		display.setText(currentCard.getAnswer());
		nextButton.setText("Show question");
	}

	private void showNextCard() {
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show answer");
	}
	
	private QuizCard makeCardFrom(String lineToParse) {
		String[] result = lineToParse.split("/");
		
		return new QuizCard(result[0], result[1]);
	}
	
	private void loadFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null) {
				cardList.add(makeCardFrom(line));
			}
			reader.close();
		}
		catch(FileNotFoundException ex) {
			display.setText("The file was not found!");
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	private class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (isShowAnswer) {
				showAnswerToCurrentCart();
				isShowAnswer = false;
			} else {
				if (currentCardIndex < cardList.size()) {
					toggleNextCard();
					showNextCard();
					isShowAnswer = true;
				} else
					display.setText("Out of cards!");
			}
		}
	}

	private class LoadFileListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(frame);
			loadFile(fileChooser.getSelectedFile());
		}
	}
}
