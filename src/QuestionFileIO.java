import java.io.*;
import java.util.*;

/**
 * Handles the file input and output for the multiple choice question training
 * application.
 * 
 * @author harishramachandran
 *
 */
public class QuestionFileIO
{
	/**
	 * Reads questions from the file with the file name indicated.
	 * 
	 * @param fileName
	 *           the file name of the file to be read from
	 * @return a pair consisting of the question list and the answer histories
	 *         associated with each tag in the question list
	 */
	public static Pair<ArrayList<Question>, HashMap<String, int[]>> readQuestions(String fileName)
	{
		ArrayList<Question> questionBank = new ArrayList<Question>();
		HashMap<String, int[]> answerHistories = new HashMap<String, int[]>();

		try
		{
			BufferedReader file = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = file.readLine()) != null)
			{
				String question = line.trim();
				String answerChoices = "";

				while (!(line = file.readLine()).contains("Answer:"))
				{
					// Ignores tab space
					answerChoices = answerChoices + " " + line.substring(1).trim() + ";";
				}
				System.out.println(line);

				char answer = line.substring(7).charAt(1);

				String answerExplanation = file.readLine().substring(12).trim();

				String tags = file.readLine().substring(5).trim();

				questionBank.add(new Question(question.trim(), answerChoices.trim(), answer, answerExplanation, tags));

				// Get the tags list of the question that was just added.
				ArrayList<String> tagsList = questionBank.get(questionBank.size() - 1).getTags();

				// Check if each tag already exists in the question bank.
				for (String tag : tagsList)
				{
					Iterator<String> it = answerHistories.keySet().iterator();
					boolean found = false;

					while (it.hasNext() && !found)
					{
						String pastTag = it.next();
						if (tag.equalsIgnoreCase(pastTag))
						{
							found = true;
						}
					}

					// If the tag does not exist already, add the tag to the answer histories map
					// and initialize its correct/total values.
					if (!found)
					{
						int[] answerHistory = { 0, 0 };
						answerHistories.put(tag, answerHistory);
					}
				}
				file.readLine();
			}

			file.close();

			return new Pair<ArrayList<Question>, HashMap<String, int[]>>(questionBank, answerHistories);
		}
		catch (IOException e)
		{
			return null;
		}
	}

	/**
	 * Reads answer histories from the file with the file name provided.
	 * 
	 * @param answerHistories
	 *           the answer histories for each tag
	 * @param fileName
	 *           the file name of the file to be read from
	 */
	public static void readAnswerHistories(HashMap<String, int[]> answerHistories, String fileName)
	{
		answerHistories.put("Overall", new int[2]);

		try
		{
			BufferedReader file = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = file.readLine()) != null)
			{
				line = line.trim();
				int colonIndex = line.indexOf(":");

				if (colonIndex != -1)
				{
					String tag = line.substring(0, colonIndex).trim();

					if (answerHistories.containsKey(tag))
					{
						if (line.length() > colonIndex + 1)
						{
							line = line.substring(colonIndex + 1).trim();

							int dashIndex = line.indexOf("/");
							if (dashIndex != -1)
							{
								try
								{
									int numberOfCorrectAnswers = Integer.parseInt(line.substring(0, dashIndex));
									if (line.length() > dashIndex + 1)
									{
										try
										{
											int numberOfTotalAnswers = Integer.parseInt(line.substring(dashIndex + 1).trim());
											int[] answerRatio = { numberOfCorrectAnswers, numberOfTotalAnswers };
											answerHistories.replace(tag, answerRatio);
										}
										catch (NumberFormatException e)
										{
											// Don't add the answer history in this case.
										}
									}
								}
								catch (NumberFormatException e)
								{
									// Don't add the answer history in this case.
								}
							}
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			// Don't add the answer history in this case.
		}
	}

	/**
	 * Writes the answer histories provided to a text file with the file name
	 * provided.
	 * 
	 * @param answerHistories
	 *           the answer histories to be written
	 * @param fileName
	 *           the name of the file to be written to
	 * @return a boolean indicating whether the method was successful in writing
	 */
	public static boolean writeAnswerHistories(HashMap<String, int[]> answerHistories, String fileName)
	{
		try
		{
			Writer writer = new FileWriter(fileName);
			Set<String> tagList = answerHistories.keySet();

			for (String tag : tagList)
			{
				writer.write(tag + ": ");
				writer.write(answerHistories.get(tag)[0] + "/" + answerHistories.get(tag)[1] + "\n");
			}

			writer.close();

			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}

	/**
	 * Writes the question bank provided to a text file with the file name provided.
	 * 
	 * @param questionBank
	 *           the question bank to be written.
	 * @param fileName
	 *           the name of the file to be written to
	 * @return a boolean indicating whether the method was successful in writing
	 */
	public static boolean writeQuestions(ArrayList<Question> questionBank, String fileName)
	{
		try
		{
			Writer writer = new FileWriter(fileName);
			for (Question question : questionBank)
			{
				writer.write(question.getQuestion() + "\n");

				ArrayList<String> answerChoices = question.getAnswerChoices();

				for (String answerChoice : answerChoices)
				{
					writer.write("\t" + answerChoice + "\n");
				}

				writer.write("Answer: " + question.getAnswer() + "\n");
				writer.write("Explanation: " + question.getAnswerExplanation() + "\n");

				ArrayList<String> tags = question.getTags();
				writer.write("Tags: ");

				for (String tag : tags)
				{
					writer.write(tag + ", ");
				}
				writer.write("\n\n");
			}

			writer.close();
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}
}
