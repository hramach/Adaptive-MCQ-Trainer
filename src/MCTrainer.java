import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

/**
 * A adapative training application for Multiple Choice Questions.
 *  
 * @author Harish Ramachandran 
 * @version 1.0 2018-04-05
 */
public class MCTrainer
{
	private static Question sampleQuestion = new Question("Sample Question", "A: option A; B: option B;", 'B',
			"This is a sample question.", "sample, test");

	private final int ASCII_CAPITAL_A = 65;

	private int correctionFrequency;
	private int currentNumberOfQuestionsToAsk;
	private int currentNumberOfCorrectQuestions;
	private int currentNumberOfQuestionsAsked;
	private QuestionAsker currentQuestion;
	private String fileName;
	private ArrayList<Pair<Question, Boolean>> currentAnsweredQuestions = new ArrayList<Pair<Question, Boolean>>();
	private ArrayList<Question> questionBank = new ArrayList<Question>();

	// Stores the number of correct answers and the number of total answers for
	// questions of each tag in the question bank. String: tag; integer array:
	// [correct, total]
	private static HashMap<String, int[]> answerHistories = new HashMap<String, int[]>();

	private MainMenu mainMenu;
	private ExamMenu examMenu;
	private ProgressMenu progressMenu;
	private TrainingMenu trainingMenu;
	private ManageQuestionsMenu manageQuestionsMenu;

	/**
	 * Constructs a multiple choice training application.
	 */
	public MCTrainer()
	{
		initialize();
	}

	public static void main(String[] argument)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new MCTrainer();
			}
		});
	}

	
	/* private utility methods */
	/*
	 * Asks the user a question.
	 */
	private void askQuestion(boolean isExam)
	{
		Random generator = new Random();
		updateAnswerHistories();

		Pair<Double, String>[] cumulativeWeights = generateWeights();
		double cumulativeWeight = cumulativeWeights[cumulativeWeights.length - 1].getL().doubleValue();

		String tag = binarySearchCDF(cumulativeWeights, cumulativeWeight * generator.nextDouble());

		ArrayList<Question> taggedQuestions = determineTaggedQuestions(questionBank, tag);
		Question selectedQuestion = taggedQuestions.get(generator.nextInt(taggedQuestions.size()));

		JFrame pastQuestionFrame = currentQuestion.frame;
		currentQuestion.setQuestion(selectedQuestion);
		currentQuestion.frame.setVisible(true);

		if (pastQuestionFrame != null) pastQuestionFrame.dispose();

		currentQuestion.answerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean found = false;
				int i = 0;
				ArrayList<JCheckBox> answerChoices = currentQuestion.answerChoices;

				while (!found && i < answerChoices.size())
				{
					found = answerChoices.get(i).isSelected();
					i++;
				}

				boolean correct = (char) (ASCII_CAPITAL_A + i - 1) == currentQuestion.getQuestion().getAnswer();

				int[] overallRatio = answerHistories.get("Overall");
				overallRatio[1]++;
				if (correct) overallRatio[0]++;
				updateAnswerHistories(selectedQuestion.getTags(), correct);

				currentAnsweredQuestions.add(new Pair<Question, Boolean>(selectedQuestion, new Boolean(correct)));

				currentNumberOfQuestionsAsked++;

				if (isExam)
					continueExam();
				else
					continueTraining();
			}
		});

		currentQuestion.stopButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isExam)
					stopExam();
				else
					stopTraining();
			}
		});
	}

	/*
	 * Selects a question from the question bank.
	 */
	private String binarySearchCDF(Pair<Double, String>[] cumulativeWeights, double random)
	{
		int first = 0;
		int last = cumulativeWeights.length - 1;
		int middle = (first + last) / 2;
		boolean found = false;

		while (first <= last && !found)
		{
			middle = (first + last) / 2;
			double middleValue = cumulativeWeights[middle].getL();

			if (middleValue == random)
			{
				found = true;
			}
			else if (middleValue < random)
			{
				first = middle + 1;
			}
			else
			{
				last = middle - 1;
			}
		}

		// By the definition of a cumulative distribution function, for a random value
		// greater than one tag's cumulative weight but less than the next
		// tag's cumulative weight, the tag with the lower cumulative weight should be
		// returned as the search result even if it is not the weight closer to the
		// random value.
		if (random > cumulativeWeights[middle].getL() && middle > 0) middle -= 1;

		return cumulativeWeights[middle].getR();
	}

	/*
	 * Determines whether to continue the exam.
	 */
	private void continueExam()
	{
		if (currentAnsweredQuestions.size() == currentNumberOfQuestionsToAsk)
		{
			stopExam();
		}
		else
			askQuestion(true);
	}

	/*
	 * Determines whether to continue the training.
	 */
	private void continueTraining()
	{
		if (currentAnsweredQuestions.size() == correctionFrequency) makeCorrections();
		askQuestion(false);
	}

	/*
	 * Creates the menus for the user interface.
	 */
	private void createMenus()
	{
		// Main menu
		mainMenu = new MainMenu();
		mainMenu.textField.setText(fileName);
		mainMenu.frame.setVisible(true);
		mainMenu.examButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				examMenu.frame.setVisible(true);
				mainMenu.frame.setVisible(false);
			}
		});

		// Main menu buttons
		mainMenu.progressButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				progressMenu = new ProgressMenu(answerHistories);
				progressMenu.okButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						mainMenu.frame.setVisible(true);
						progressMenu.dispose();
					}
				});
				progressMenu.setVisible(true);
				mainMenu.frame.setVisible(false);
			}
		});
		mainMenu.trainingButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				trainingMenu.frame.setVisible(true);
				mainMenu.frame.setVisible(false);
			}
		});
		mainMenu.exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				TextFileWriter textFileWriter = new TextFileWriter();
				textFileWriter.setVisible(true);
				textFileWriter.okButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						boolean questionsWritten = false;
						boolean answerHistoriesWritten = false;

						String writingFileName = textFileWriter.textField.getText();
						if (writingFileName != null)
						{
							questionsWritten = QuestionFileIO.writeQuestions(questionBank, writingFileName);
							int dotIndex = writingFileName.indexOf(".");
							if (dotIndex != -1)
							{
								String fileNameNoExtension = writingFileName.substring(0, dotIndex);
								String answerHistoriesFileName = fileNameNoExtension + "_answerHistories.txt";
								answerHistoriesWritten = QuestionFileIO.writeAnswerHistories(answerHistories,
										answerHistoriesFileName);
							}
						}

						TerminationDialog terminationDialog = new TerminationDialog(questionsWritten, answerHistoriesWritten);
						terminationDialog.setVisible(true);

						textFileWriter.setVisible(false);

						terminationDialog.okButton.addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								terminationDialog.dispose();
								System.exit(0);
							}
						});

						mainMenu.frame.setVisible(false);
					}
				});
				textFileWriter.cancelButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						TerminationDialog terminationDialog = new TerminationDialog(false, false);
						terminationDialog.setVisible(true);

						textFileWriter.setVisible(false);

						terminationDialog.okButton.addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								terminationDialog.dispose();
								System.exit(0);
							}
						});
					}
				});
			}
		});
		mainMenu.manageButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manageQuestionsMenu.frame.dispose();
				manageQuestionsMenu = new ManageQuestionsMenu(questionBank);
				manageQuestionsMenu.backButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						mainMenu.frame.setVisible(true);
						manageQuestionsMenu.frame.setVisible(false);
						updateAnswerHistories();
					}
				});
				manageQuestionsMenu.frame.setVisible(true);
				mainMenu.frame.setVisible(false);
			}
		});

		// Training menu
		trainingMenu = new TrainingMenu();

		// Training menu buttons
		trainingMenu.cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainMenu.frame.setVisible(true);
				trainingMenu.frame.setVisible(false);
			}
		});
		trainingMenu.beginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				trainingMenu.frame.setVisible(false);
				train();
			}
		});

		// Exam menu
		examMenu = new ExamMenu();

		// Exam menu buttons
		examMenu.cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainMenu.frame.setVisible(true);
				examMenu.frame.setVisible(false);
			}
		});
		examMenu.beginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startExam();
			}
		});

		// Progress Menu
		progressMenu = new ProgressMenu(answerHistories);
		progressMenu.okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				mainMenu.frame.setVisible(true);
				progressMenu.dispose();
			}
		});

		manageQuestionsMenu = new ManageQuestionsMenu(questionBank);
		manageQuestionsMenu.backButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainMenu.frame.setVisible(true);
				manageQuestionsMenu.frame.setVisible(false);
				updateAnswerHistories();
			}
		});
	}

	/*
	 * Determines any Questions tagged with the provided tag in the Question list provided.
	 */
	private ArrayList<Question> determineTaggedQuestions(ArrayList<Question> questionBank, String tag)
	{
		ArrayList<Question> taggedQuestions = new ArrayList<>();

		for (Question question : questionBank)
		{
			ArrayList<String> tagList = question.getTags();
			Iterator<String> it = tagList.iterator();
			boolean found = false;

			while (it.hasNext() && !found)
			{
				found = it.next().equalsIgnoreCase(tag);
			}

			if (found)
			{
				taggedQuestions.add(question);
			}
		}

		return taggedQuestions;
	}

	/*
	 * Generates probabilities for each tag.
	 */
	private Pair<Double, String>[] generateWeights()
	{
		// For "Overall" tag.
		int numberOfTags = answerHistories.keySet().size() - 1;

		int totalCorrectAnswers = 0;

		for (String tag : answerHistories.keySet())
		{
			if (!tag.equals("Overall"))
			{
				totalCorrectAnswers += answerHistories.get(tag)[0];
			}
		}

		@SuppressWarnings("unchecked")
		Pair<Double, String>[] cumulativeWeights = new Pair[numberOfTags];
		int i = 0;

		double cumulativeWeight = 0;

		for (String tag : answerHistories.keySet())
		{
			if (!tag.equals("Overall"))
			{
				double weight;
				if (totalCorrectAnswers == 0)
				{
					weight = 1.5;
				}
				else
				{
					int numberOfCorrectAnswers = answerHistories.get(tag)[0];
					weight = 2 - ((double) numberOfCorrectAnswers) / totalCorrectAnswers;
				}
				cumulativeWeight += weight;
				cumulativeWeights[i] = new Pair<Double, String>(new Double(cumulativeWeight), tag);
				i++;
			}
		}

		return cumulativeWeights;
	}

	/*
	 * Initializes the application.
	 */
	private void initialize()
	{
		TextFileOpener textFileOpener = new TextFileOpener();
		textFileOpener.setVisible(true);

		textFileOpener.okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fileName = textFileOpener.textField.getText();

				Pair<ArrayList<Question>, HashMap<String, int[]>> initializationPair = QuestionFileIO
						.readQuestions(fileName);

				if (initializationPair != null)
				{
					questionBank = initializationPair.getL();
					answerHistories = initializationPair.getR();

					int dotIndex = fileName.indexOf(".");
					if (dotIndex != -1)
					{
						String fileNameNoExtension = fileName.substring(0, dotIndex);
						String answerHistoriesFileName = fileNameNoExtension + "_answerHistories.txt";
						QuestionFileIO.readAnswerHistories(answerHistories, answerHistoriesFileName);
					}

					if (!answerHistories.containsKey("Overall"))
					{
						int[] overallRatio = { 0, 0 };
						answerHistories.put("Overall", overallRatio);
					}
				}
				else
				{
					fileName = "";
					questionBank.add(sampleQuestion);
				}

				updateAnswerHistories();
				createMenus();
				textFileOpener.setVisible(false);
			}
		});

		textFileOpener.cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				createMenus();
				textFileOpener.setVisible(false);
			}
		});
	}

	/*
	 * Makes corrections to the user's answers to the questions.
	 */
	private void makeCorrections()
	{
		CorrectionsDialog corrections = new CorrectionsDialog();

		for (Pair<Question, Boolean> uncorrectedPair : currentAnsweredQuestions)
		{
			Question uncorrectedQuestion = uncorrectedPair.getL();

			String correction = uncorrectedQuestion.getQuestion() + "\n";

			if (uncorrectedPair.getR())
			{
				correction = correction + "Correct.\n";
				currentNumberOfCorrectQuestions++;
			}
			else
				correction = correction + "Incorrect.\n";

			int correctChoiceNumber = (int) uncorrectedQuestion.getAnswer() - ASCII_CAPITAL_A;

			correction = correction + "Correct Answer: " + uncorrectedQuestion.getAnswerChoices().get(correctChoiceNumber)
					+ "\n";

			correction = correction + "Explanation: " + uncorrectedQuestion.getAnswerExplanation() + "\n\n";

			corrections.textArea.append(correction);
		}

		corrections.textArea.append("Overall: " + currentNumberOfCorrectQuestions + "/" + currentNumberOfQuestionsAsked);

		corrections.setVisible(true);

		corrections.okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				corrections.dispose();
			}
		});

		currentAnsweredQuestions.clear();
	}

	/*
	 * Begins an examination.
	 */
	private void startExam()
	{
		// Number format checks have already been done by examMenu.
		currentNumberOfQuestionsToAsk = Integer.parseInt(examMenu.numberTextField.getText());
		// int time = Integer.parseInt(examMenu.timeTextField.getText());

		currentQuestion = new QuestionAsker(null);

		currentNumberOfQuestionsAsked = 0;
		currentNumberOfCorrectQuestions = 0;
		examMenu.frame.setVisible(false);
		askQuestion(true);
	}

	/*
	 * Stops the examination.
	 */
	private void stopExam()
	{
		currentQuestion.frame.dispose();
		makeCorrections();
		examMenu.frame.setVisible(false);
		mainMenu.frame.setVisible(true);
	}

	/*
	 * Stops the training.
	 */
	private void stopTraining()
	{
		currentQuestion.frame.dispose();
		// Indicates to the makeCorrections() method that it must correct the remaining
		// questions.
		correctionFrequency = currentAnsweredQuestions.size();
		makeCorrections();
		trainingMenu.frame.setVisible(false);
		mainMenu.frame.setVisible(true);
	}

	/*
	 * Begins a training session.
	 */
	private void train()
	{
		// Extra catch block implemented to deduce user selection, rather than
		// declaring each check box package-private.
		correctionFrequency = -1; // Indicates that the user wants corrections after the session.
		if (trainingMenu.everyQuestionCheckBox.isSelected())
		{
			correctionFrequency = 1;
		}
		else
		{
			try
			{
				correctionFrequency = Integer.parseInt(trainingMenu.frequencyTextField.getText());
			}
			catch (NumberFormatException exception)
			{
				// Leave correctionFrequency = -1 in this case.
			}
		}

		currentQuestion = new QuestionAsker(null);

		currentNumberOfQuestionsAsked = 0;
		currentNumberOfCorrectQuestions = 0;
		askQuestion(false);
	}

	/*
	 * Cleanses the answer history data of erroneous tags.
	 */
	private void updateAnswerHistories()
	{
		HashMap<String, int[]> newAnswerHistories = new HashMap<String, int[]>();

		for (Question question : questionBank)
		{
			ArrayList<String> tags = question.getTags();

			for (String tag : tags)
			{
				if (!newAnswerHistories.containsKey(tag) && tag.trim().length() > 0)
				{
					if (answerHistories.containsKey(tag))
					{
						newAnswerHistories.put(tag, answerHistories.get(tag));
					}
					if (!answerHistories.containsKey(tag))
					{
						int[] ratio = { 0, 0 };
						newAnswerHistories.put(tag, ratio);
					}
				}
			}
		}

		if (!newAnswerHistories.containsKey("Overall"))
		{
			if (answerHistories.containsKey("Overall"))
			{
				newAnswerHistories.put("Overall", answerHistories.get("Overall"));
			}
			else
			{
				int[] overallRatio = { 0, 0 };
				newAnswerHistories.put("Overall", overallRatio);
			}
		}

		answerHistories = newAnswerHistories;
	}

	/*
	 * Cleanses the answer history data of erroneous tags.
	 */
	private static void updateAnswerHistories(ArrayList<String> questionTags, boolean correct)
	{
		for (String questionTag : questionTags)
		{
			Iterator<String> it = answerHistories.keySet().iterator();
			boolean found = false;

			while (it.hasNext() && !found)
			{
				String pastTag = it.next();
				if (questionTag.equalsIgnoreCase(pastTag))
				{
					answerHistories.get(pastTag)[1]++;
					if (correct)
					{
						answerHistories.get(pastTag)[0]++;
					}
					found = true;
				}
			}
		}
	}
}
