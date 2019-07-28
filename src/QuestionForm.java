import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 * A form allowing the user to fill out question information.
 * 
 * @author Harish Ramachandran
 */
public class QuestionForm extends JDialog
{
	private String questionString;
	private char answerChar;
	private String answerChoicesString;

	boolean questionReady = false;
	boolean answerReady = false;
	boolean answerChoicesReady = false;

	JButton saveButton;
	JButton cancelButton;

	private JTextArea answerChoicesText;
	private JTextArea questionText;
	private JTextArea explanationText;

	private final JPanel contentPanel = new JPanel();
	private JTextField tagsText;
	private JTextField answerTextField;

	/**
	 * Constructs a question form.
	 */
	public QuestionForm(ArrayList<Question> questionBank)
	{
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Question Properties");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0 };
		contentPanel.setLayout(gbl_contentPanel);

		JLabel lblNewLabel = new JLabel("Question: ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);

		questionText = new JTextArea();
		questionText.setLineWrap(true);
		questionText.setWrapStyleWord(true);
		questionText.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				changed();
			}

			public void changed()
			{
				if (questionText.getText().equals(""))
				{
					questionReady = false;
				}
				else
				{
					questionString = questionText.getText();
					questionReady = true;
				}

				checkReadiness();
			}
		});
		GridBagConstraints gbc_questionText = new GridBagConstraints();
		gbc_questionText.insets = new Insets(0, 0, 5, 0);
		gbc_questionText.fill = GridBagConstraints.BOTH;
		gbc_questionText.gridx = 0;
		gbc_questionText.gridy = 1;
		contentPanel.add(questionText, gbc_questionText);

		JLabel lblNewLabel_1 = new JLabel("Answer (A, B, C, ...):");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		answerTextField = new JTextField();
		answerTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				changed();
			}

			public void changed()
			{
				if (answerTextField.getText().equals(""))
				{
					answerReady = false;
				}
				else
				{
					char answer = answerTextField.getText().toUpperCase().charAt(0);
					if (answer >= 65 && answer <= 90)
					{
						answerChar = answer;
						answerReady = true;
					}
					else
					{
						answerReady = false;
					}
				}

				checkReadiness();
			}
		});
		GridBagConstraints gbc_answerTextField = new GridBagConstraints();
		gbc_answerTextField.insets = new Insets(0, 0, 5, 0);
		gbc_answerTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_answerTextField.gridx = 0;
		gbc_answerTextField.gridy = 3;
		contentPanel.add(answerTextField, gbc_answerTextField);
		answerTextField.setColumns(10);

		JLabel lblAnswerChoices = new JLabel("Answer Choices (A: optionA; B: optionB; ...):");
		GridBagConstraints gbc_lblAnswerChoices = new GridBagConstraints();
		gbc_lblAnswerChoices.anchor = GridBagConstraints.WEST;
		gbc_lblAnswerChoices.insets = new Insets(0, 0, 5, 0);
		gbc_lblAnswerChoices.gridx = 0;
		gbc_lblAnswerChoices.gridy = 4;
		contentPanel.add(lblAnswerChoices, gbc_lblAnswerChoices);

		answerChoicesText = new JTextArea();
		answerChoicesText.setLineWrap(true);
		answerChoicesText.setWrapStyleWord(true);
		answerChoicesText.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				changed();
			}

			public void changed()
			{
				if (answerChoicesText.getText().equals(""))
				{
					answerChoicesReady = false;
				}
				else
				{
					String answerChoices = answerChoicesText.getText().trim();
					if (answerChoices.charAt(answerChoices.length() - 1) != ';')
					{
						answerChoices = answerChoices + ";";
					}
					answerChoicesString = answerChoices;
					answerChoicesReady = true;
				}

				checkReadiness();
			}
		});
		GridBagConstraints gbc_answerChoicesText = new GridBagConstraints();
		gbc_answerChoicesText.insets = new Insets(0, 0, 5, 0);
		gbc_answerChoicesText.fill = GridBagConstraints.BOTH;
		gbc_answerChoicesText.gridx = 0;
		gbc_answerChoicesText.gridy = 5;
		contentPanel.add(answerChoicesText, gbc_answerChoicesText);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		contentPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_2 = new JLabel("Explanation:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 7;
		contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		explanationText = new JTextArea();
		explanationText.setLineWrap(true);
		explanationText.setWrapStyleWord(true);
		GridBagConstraints gbc_explanationText = new GridBagConstraints();
		gbc_explanationText.insets = new Insets(0, 0, 5, 0);
		gbc_explanationText.fill = GridBagConstraints.BOTH;
		gbc_explanationText.gridx = 0;
		gbc_explanationText.gridy = 8;
		contentPanel.add(explanationText, gbc_explanationText);

		JLabel lblNewLabel_3 = new JLabel("Tags (tag1, tag2, ...):");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 9;
		contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		tagsText = new JTextField();
		GridBagConstraints gbc_tagsText = new GridBagConstraints();
		gbc_tagsText.fill = GridBagConstraints.HORIZONTAL;
		gbc_tagsText.gridx = 0;
		gbc_tagsText.gridy = 10;
		contentPanel.add(tagsText, gbc_tagsText);
		tagsText.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String tags = tagsText.getText();
				if (tags == null)
				{
					tags = "";
				}

				questionBank
						.add(new Question(questionString, answerChoicesString, answerChar, explanationText.getText(), tags));
				dispose();
			}
		});
		saveButton.setActionCommand("Save");
		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);

		cancelButton = new JButton("Cancel");
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	public QuestionForm(Question presetQuestion)
	{
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Question Properties");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0 };
		contentPanel.setLayout(gbl_contentPanel);

		JLabel lblNewLabel = new JLabel("Question: ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);

		questionText = new JTextArea();
		questionText.setLineWrap(true);
		questionText.setWrapStyleWord(true);
		questionText.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				changed();
			}

			public void changed()
			{
				if (questionText.getText().equals(""))
				{
					questionReady = false;
				}
				else
				{
					questionString = questionText.getText();
					questionReady = true;
				}

				checkReadiness();
			}
		});
		GridBagConstraints gbc_questionText = new GridBagConstraints();
		gbc_questionText.insets = new Insets(0, 0, 5, 0);
		gbc_questionText.fill = GridBagConstraints.BOTH;
		gbc_questionText.gridx = 0;
		gbc_questionText.gridy = 1;
		contentPanel.add(questionText, gbc_questionText);

		JLabel lblNewLabel_1 = new JLabel("Answer (A, B, C, ...):");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		answerTextField = new JTextField();
		answerTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				changed();
			}

			public void changed()
			{
				if (answerTextField.getText().equals(""))
				{
					answerReady = false;
				}
				else
				{
					char answer = answerTextField.getText().toUpperCase().charAt(0);
					if (answer >= 65 && answer <= 90)
					{
						answerChar = answer;
						answerReady = true;
					}
					else
					{
						answerReady = false;
					}
				}

				checkReadiness();
			}
		});
		GridBagConstraints gbc_answerTextField = new GridBagConstraints();
		gbc_answerTextField.insets = new Insets(0, 0, 5, 0);
		gbc_answerTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_answerTextField.gridx = 0;
		gbc_answerTextField.gridy = 3;
		contentPanel.add(answerTextField, gbc_answerTextField);
		answerTextField.setColumns(10);

		JLabel lblAnswerChoices = new JLabel("Answer Choices (A: optionA; B: optionB; ...):");
		GridBagConstraints gbc_lblAnswerChoices = new GridBagConstraints();
		gbc_lblAnswerChoices.anchor = GridBagConstraints.WEST;
		gbc_lblAnswerChoices.insets = new Insets(0, 0, 5, 0);
		gbc_lblAnswerChoices.gridx = 0;
		gbc_lblAnswerChoices.gridy = 4;
		contentPanel.add(lblAnswerChoices, gbc_lblAnswerChoices);

		answerChoicesText = new JTextArea();
		answerChoicesText.setLineWrap(true);
		answerChoicesText.setWrapStyleWord(true);
		answerChoicesText.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				changed();
			}

			public void changed()
			{
				if (answerChoicesText.getText().equals(""))
				{
					answerChoicesReady = false;
				}
				else
				{
					String answerChoices = answerChoicesText.getText().trim();
					if (answerChoices.charAt(answerChoices.length() - 1) != ';')
					{
						answerChoices = answerChoices + ";";
					}
					answerChoicesString = answerChoices;
					answerChoicesReady = true;
				}

				checkReadiness();
			}
		});
		GridBagConstraints gbc_answerChoicesText = new GridBagConstraints();
		gbc_answerChoicesText.insets = new Insets(0, 0, 5, 0);
		gbc_answerChoicesText.fill = GridBagConstraints.BOTH;
		gbc_answerChoicesText.gridx = 0;
		gbc_answerChoicesText.gridy = 5;
		contentPanel.add(answerChoicesText, gbc_answerChoicesText);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		contentPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_2 = new JLabel("Explanation:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 7;
		contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		explanationText = new JTextArea();
		explanationText.setLineWrap(true);
		explanationText.setWrapStyleWord(true);
		GridBagConstraints gbc_explanationText = new GridBagConstraints();
		gbc_explanationText.insets = new Insets(0, 0, 5, 0);
		gbc_explanationText.fill = GridBagConstraints.BOTH;
		gbc_explanationText.gridx = 0;
		gbc_explanationText.gridy = 8;
		contentPanel.add(explanationText, gbc_explanationText);

		JLabel lblNewLabel_3 = new JLabel("Tags (tag1, tag2, ...):");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 9;
		contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		tagsText = new JTextField();
		GridBagConstraints gbc_tagsText = new GridBagConstraints();
		gbc_tagsText.fill = GridBagConstraints.HORIZONTAL;
		gbc_tagsText.gridx = 0;
		gbc_tagsText.gridy = 10;
		contentPanel.add(tagsText, gbc_tagsText);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String tags = tagsText.getText();
				if (tags == null)
				{
					tags = "";
				}

				presetQuestion.editQuestion(questionString);
				presetQuestion.editAnswerChoices(answerChoicesString);
				presetQuestion.editAnswer(answerChar);
				presetQuestion.editAnswerExplanation(explanationText.getText());
				presetQuestion.editTags(tags);
				dispose();
			}
		});
		saveButton.setActionCommand("Save");
		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		questionText.setText(presetQuestion.getQuestion());
		answerTextField.setText("" + presetQuestion.getAnswer());
		answerChoicesText.setText(presetQuestion.getAnswerChoicesString());
		explanationText.setText(presetQuestion.getAnswerExplanation());

		String tagsString = "";
		ArrayList<String> tagList = presetQuestion.getTags();

		for (String tag : tagList)
		{
			if (tag.trim().length() > 0) tagsString = tagsString + tag + ", ";
		}

		tagsText.setText(tagsString);
	}

	private void checkReadiness()
	{
		saveButton.setEnabled(questionReady && answerChoicesReady && answerReady);
	}

}
