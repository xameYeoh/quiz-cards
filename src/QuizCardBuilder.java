import javax.swing.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class QuizCardBuilder {
	private JFrame frame;
	private ArrayList<QuizCard> cardList;
	private TextArea question;
	private TextArea answer;
	
	static JComponent centerComponentsInOuter(JComponent outerComponent) { //функция не взаимодействует с полями объектов класса, в котором
																		//находится, а получает ссылку на конкретный объект Компонента
		for (Component component : outerComponent.getComponents()) {
			JComponent jComponent = (JComponent) component;
			component = center(jComponent);
		}
		return outerComponent;
	}

	static JComponent center(JComponent component) {
		component.setAlignmentX(Component.CENTER_ALIGNMENT);
		return component;
	}

	QuizCardBuilder() {
		question = prepare(new TextArea());
		answer = prepare(new TextArea());

		cardList = new ArrayList<QuizCard>();
	}

	public static void main(String[] args) {
		new QuizCardBuilder().go();
	}

	private void go() {
		frame = setUp(frame);
		frame.setVisible(true);
	}

	private JFrame setUp(JFrame frame) {
		frame = new JFrame("Quiz Card Builder");

		JPanel panel = prepare(new JPanel());

		JMenuBar menu = prepare(new JMenuBar());

		frame.setJMenuBar(menu);
		frame.getContentPane().add(panel);
		frame.setSize(500, 600);
		return frame;
	}

	public JMenuBar prepare(JMenuBar menuBar) {
		JMenu menu = new JMenu("File");

		JMenuItem newItem = new JMenuItem("New");
		JMenuItem saveItem = new JMenuItem("Save");
		newItem.addActionListener(new NewMenuListener());
		saveItem.addActionListener(new SaveMenuListener());

		menu.add(newItem);
		menu.add(saveItem);

		menuBar.add(menu);

		return menuBar;
	}

	private JPanel prepare(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JScrollPane questionPane = question.getScrollPane();
		JScrollPane answerPane = answer.getScrollPane();

		JLabel questionLabel = new JLabel("Question");
		JLabel answerLabel = new JLabel("Answer");

		JButton buttonNext = prepare(new JButton());
		
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

		panel.add(questionLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(questionPane);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		panel.add(answerLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(answerPane);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));

		panel.add(buttonNext);

		panel = (JPanel) centerComponentsInOuter(panel);

		return panel;
	}



	private JButton prepare(JButton button) {
		button = new JButton("Next Card");
		button.addActionListener(new NextCardListener());

		return button;
	}

	private TextArea prepare(TextArea area) {
		Font bigFont = new Font("sanserif", Font.BOLD, 24);

		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(bigFont);

		return area;
	}

	private TextArea refreshTextArea(TextArea textArea) {
		textArea.setText("");
		return textArea;
	}

	private void saveCurrentCard() {
		QuizCard card = new QuizCard(question.getText(), answer.getText());
		cardList.add(card);
	}

	private void saveFile(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (QuizCard card : cardList) {
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
			}
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private class NextCardListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			saveCurrentCard();
			question = refreshTextArea(question);
			answer = refreshTextArea(answer);
			question.requestFocus();
		}
	}

	private class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cardList.clear();
			answer = refreshTextArea(answer);
			question = refreshTextArea(question);
		}
	}

	private class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			saveCurrentCard();

			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());

		}
	}

}
