import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 * A window that asks the user a multiple choice question.
 * 
 * @author Harish Ramachandran
 */
public class QuestionAsker
{
	JButton answerButton;
	JButton stopButton;
	JTextArea questionText;
	ArrayList<JCheckBox> answerChoices;
	JFrame frame;

	private Question question;

	/**
	 * Constructs a window that asks the user a multiple choice question.
	 * 
	 * @param question the question to be asked.
	 */
	public QuestionAsker(Question question)
	{
		if (question != null)
		{
			this.question = question;
			initialize();
		}
	}

	/**
	 * Returns the question associated with this window.
	 * 
	 * @return the question associated with this window
	 */
	public Question getQuestion()
	{
		return question;
	}

	/**
	 * Sets the question associated with this window. 
	 * 
	 * @param question the question associated with this window.
	 */
	public void setQuestion(Question question)
	{
		this.question = question;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		ArrayList<String> answerChoicesStrings = question.getAnswerChoices();
		answerChoices = new ArrayList<JCheckBox>();

		frame = new JFrame();
		frame.setTitle("Question");
		frame.setBounds(300, 300, 700, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);
		
		questionText = new JTextArea(question.getQuestion());
		questionText.setEditable(false);
		questionText.setLineWrap(true);
		questionText.setWrapStyleWord(true);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridwidth = 6;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().add(questionText, gbc_lblNewLabel);

		answerButton = new JButton("Answer");
		answerButton.setEnabled(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;

		stopButton = new JButton("Stop");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.gridx = 3;

		int i = 0;
		for (String answerChoiceString : answerChoicesStrings)
		{
			JCheckBox answerChoice = new JCheckBox(answerChoiceString);

			answerChoice.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (!answerChoice.isSelected()) answerChoice.setSelected(true);

					answerButton.setEnabled(true);
					for (JCheckBox answerChoice1 : answerChoices)
					{
						if (!answerChoice.equals(answerChoice1)) answerChoice1.setSelected(false);
					}
				}
			});

			GridBagConstraints gbc_answerChoice = new GridBagConstraints();
			gbc_answerChoice.anchor = GridBagConstraints.WEST;
			gbc_answerChoice.gridwidth = 2;
			gbc_answerChoice.insets = new Insets(0, 0, 5, 5);
			gbc_answerChoice.gridx = 0;
			gbc_answerChoice.gridy = 1 + i;
			frame.getContentPane().add(answerChoice, gbc_answerChoice);

			i++;

			answerChoices.add(answerChoice);
		}

		gbc_btnNewButton.gridy = i + 1;
		frame.getContentPane().add(answerButton, gbc_btnNewButton);

		gbc_btnStop.gridy = i + 1;
		frame.getContentPane().add(stopButton, gbc_btnStop);
	}
}
