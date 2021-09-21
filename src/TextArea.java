import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class TextArea extends JTextArea {
	private JScrollPane scroll = new JScrollPane(this);

	TextArea(int rows, int columns) {
		super(rows, columns);

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.setFont(new Font("sanserif", Font.BOLD, 24));
		this.setLineWrap(getLineWrap());
	}

	TextArea() {
		this(6, 20);
	}

	public JScrollPane getScrollPane() {
		return scroll;
	}
}