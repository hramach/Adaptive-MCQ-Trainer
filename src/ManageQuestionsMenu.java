import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

/**
 * A menu allowing the question to manage questions.
 * 
 * @author Harish Ramachandran
 *
 */
public class ManageQuestionsMenu
{
	JButton addButton;
	JButton backButton;
	JButton deleteButton;
	JButton editButton;
	JButton sortButton;

	private ArrayList<Question> questionBank;
	JFrame frame;
	private JTextField textField;
	private JList list;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ManageQuestionsMenu window = new ManageQuestionsMenu(
							QuestionFileIO.readQuestions("questions.txt").getL());
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructs a menu allowing the user to manage questions.
	 */
	public ManageQuestionsMenu(ArrayList<Question> questionBank)
	{
		this.questionBank = questionBank;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 10.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		gbc_scrollPane.weightx = 2.0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		list = new JList(questionBank.toArray());
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		gbc_panel.weightx = 1.0;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0 };
		panel.setLayout(gbl_panel);

		JLabel lblSearch = new JLabel("Search");
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.insets = new Insets(0, 0, 5, 0);
		gbc_lblSearch.gridx = 0;
		gbc_lblSearch.gridy = 0;
		panel.add(lblSearch, gbc_lblSearch);

		textField = new JTextField();
		textField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String query = textField.getText();
				ArrayList<Question> searchResults = new ArrayList<Question>();

				for (Question question : questionBank)
				{
					if (question.getQuestion().contains(query) || question.getAnswerChoices().contains(query)
							|| question.getAnswerExplanation().contains(query))
					{
						searchResults.add(question);
					}
					else
					{
						ArrayList<String> currentTags = question.getTags();
						Iterator<String> tagIterator = currentTags.iterator();
						boolean found = false;
						while (tagIterator.hasNext() && !found)
						{
							String tag = tagIterator.next();
							if (tag.contains(query))
							{
								searchResults.add(question);
								found = true;
							} // end of if
						} // end of while
					} // end of else
				} // end of while

				list = new JList(searchResults.toArray());
				list.setSelectedIndex(0);
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(list);
				textField.setEnabled(false);
				addButton.setEnabled(false);
				deleteButton.setEnabled(false);
				editButton.setEnabled(false);
				sortButton.setEnabled(false);
				frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				QuestionForm questionForm = new QuestionForm(questionBank);
				questionForm.addWindowListener(new WindowListener()
				{

					@Override
					public void windowOpened(WindowEvent e)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosing(WindowEvent e)
					{

					}

					@Override
					public void windowClosed(WindowEvent e)
					{
						list = new JList(questionBank.toArray());
						list.setSelectedIndex(0);
						list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						scrollPane.setViewportView(list);
						frame.setVisible(true);
					}

					@Override
					public void windowIconified(WindowEvent e)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeiconified(WindowEvent e)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void windowActivated(WindowEvent e)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeactivated(WindowEvent e)
					{
						// TODO Auto-generated method stub

					}
				});
				questionForm.setVisible(true);
				frame.setVisible(false);
			}
		});
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.insets = new Insets(0, 0, 5, 0);
		gbc_addButton.gridx = 0;
		gbc_addButton.gridy = 2;
		panel.add(addButton, gbc_addButton);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Object selectedValue = list.getSelectedValue();
				if (selectedValue != null && questionBank.size() > 1)
				{
					questionBank.remove((Question) selectedValue);
					list = new JList(questionBank.toArray());
					list.setSelectedIndex(0);
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane.setViewportView(list);
				}
			}
		});
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.insets = new Insets(0, 0, 5, 0);
		gbc_deleteButton.gridx = 0;
		gbc_deleteButton.gridy = 3;
		panel.add(deleteButton, gbc_deleteButton);

		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Question question = (Question) list.getSelectedValue();

				if (question != null)
				{
					QuestionForm questionForm = new QuestionForm(question);
					questionForm.addWindowListener(new WindowListener()
					{

						@Override
						public void windowOpened(WindowEvent e)
						{
							// TODO Auto-generated method stub

						}

						@Override
						public void windowClosing(WindowEvent e)
						{

						}

						@Override
						public void windowClosed(WindowEvent e)
						{
							list = new JList(questionBank.toArray());
							list.setSelectedIndex(0);
							list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							scrollPane.setViewportView(list);
							frame.setVisible(true);
						}

						@Override
						public void windowIconified(WindowEvent e)
						{
							// TODO Auto-generated method stub

						}

						@Override
						public void windowDeiconified(WindowEvent e)
						{
							// TODO Auto-generated method stub

						}

						@Override
						public void windowActivated(WindowEvent e)
						{
							// TODO Auto-generated method stub

						}

						@Override
						public void windowDeactivated(WindowEvent e)
						{
							// TODO Auto-generated method stub

						}
					});
					
					questionForm.setVisible(true);
					frame.setVisible(false);
				}
			}
		});
		GridBagConstraints gbc_editButton = new GridBagConstraints();
		gbc_editButton.insets = new Insets(0, 0, 5, 0);
		gbc_editButton.gridx = 0;
		gbc_editButton.gridy = 4;
		panel.add(editButton, gbc_editButton);

		sortButton = new JButton("Sort");
		sortButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Collections.sort(questionBank);
				list = new JList(questionBank.toArray());
				list.setSelectedIndex(0);
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(list);
				frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_sortButton = new GridBagConstraints();
		gbc_sortButton.insets = new Insets(0, 0, 5, 0);
		gbc_sortButton.gridx = 0;
		gbc_sortButton.gridy = 5;
		panel.add(sortButton, gbc_sortButton);

		backButton = new JButton("Back");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 6;
		panel.add(backButton, gbc_backButton);
	}
}
