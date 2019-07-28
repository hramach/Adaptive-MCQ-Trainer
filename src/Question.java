import java.util.*;

/**
 * A multiple-choice question with the corresponding answer choices, correct
 * answer, answer explanation, and tags.
 * 
 * @author Harish Ramachandran
 * @version 1.0 2018-04-05
 */
public class Question implements Comparable<Question>
{
	private ArrayList<String> tags;
	private ArrayList<String> answerChoicesList;
	private String question;
	private String answerChoicesString;
	private char answer;
	private String answerExplanation;

	/**
	 * Constructs a question with the corresponding answer choices, correct answer,
	 * answer explanation, and tags.
	 * 
	 * @param question
	 *           the question text
	 * @param answerChoices
	 *           the answer choices for the question<br>
	 *           precondition: <code>answerChoices<code> must be in the format "A:
	 *           text; B: text; C: text:" up to 26 answer choices
	 * @param answer
	 *           the letter of the correct answer choice for the question
	 * @param answerExplanation
	 *           a brief explanation of why the answer choice is correct
	 * @param tags
	 *           the tags associated with this question<br>
	 *           precondition: <code>tags<code> must be in the format "tag1, tag 2,
	 *           tag 3" and so on
	 */
	public Question(String question, String answerChoices, char answer, String answerExplanation, String tags)
	{
		editQuestion(question);

		this.answerChoicesList = new ArrayList<String>();
		answerChoicesString = answerChoices;
		editAnswerChoices(answerChoices);

		editAnswer(answer);
		editAnswerExplanation(answerExplanation);

		this.tags = new ArrayList<String>();
		addTags(tags);
	}

	/**
	 * Adds a tag to this question.
	 * 
	 * @param tag
	 *           the tag to be added
	 */
	public void addTag(String tag)
	{
		tags.add(tag);
	}

	/**
	 * Adds a series of tags to this question.
	 * 
	 * @param tagsString
	 *           the list of tags to be added<br>
	 *           precondition: <code>tagsString<code> must be in the format "tag1,
	 *           tag 2, tag 3" and so on
	 */
	private void addTags(String tagsString)
	{
		int commaIndex = tagsString.indexOf(',');
		while (commaIndex != -1)
		{
			tags.add(tagsString.substring(0, commaIndex).trim());

			tagsString = tagsString.substring(commaIndex + 1);
			commaIndex = tagsString.indexOf(',');
		}

		if (tagsString.length() != 0)
		{
			tags.add(tagsString.trim());
		}

		Collections.sort(tags);
	}

	/**
	 * Sets the correct answer to this question.
	 * 
	 * @param answer
	 *           a letter indicating the correct answer choice
	 */
	public void editAnswer(char answer)
	{
		this.answer = (answer + "").toUpperCase().charAt(0);
	}

	/**
	 * Sets the answer choices for this question.
	 * 
	 * @param answerChoices
	 *           the list of answer choices for this question<br>
	 *           precondition: <code>answerChoices<code> must be in the format "A:
	 *           text; B: text; C: text:" up to 26 answer choices
	 */
	public void editAnswerChoices(String answerChoices)
	{
		answerChoicesList.clear();

		int semiColonIndex = answerChoices.indexOf(';');
		while (semiColonIndex != -1)
		{
			answerChoicesList.add(answerChoices.substring(0, semiColonIndex).trim());

			answerChoices = answerChoices.substring(semiColonIndex + 1);
			semiColonIndex = answerChoices.indexOf(';');
		}
	}

	/**
	 * Sets the answer explanation for this question.
	 * 
	 * @param answerExplanation
	 *           a brief explanation as to why the correct answer to this question
	 *           is correct
	 */
	public void editAnswerExplanation(String answerExplanation)
	{
		if (answerExplanation == null)
			this.answerExplanation = "";
		else
			this.answerExplanation = answerExplanation;
	}

	/**
	 * Sets the question text for this question.
	 * 
	 * @param question the question text for this question.
	 */
	public void editQuestion(String question)
	{
		this.question = question;
	}

	/**
	 * Sets the list of tags for this question.
	 * 
	 * @param tags the list of tags to be added
	 */
	public void editTags(ArrayList<String> tags)
	{
		this.tags = tags;
		if (tags == null)
		{
			tags = new ArrayList<String>();
		}
	}

	/**
	 * Sets the list of tags for this question.
	 * 
	 * @param tagsString<br>
	 *           precondition: <code>tagsString<code> must be in the format "tag1,
	 *           tag 2, tag 3" and so on
	 */
	public void editTags(String tagsString)
	{
		tags.clear();

		addTags(tagsString);
	}

	/**
	 * Returns to letter indicating the correct answer to this question.
	 * 
	 * @return a letter indicating the correct answer to this question.
	 */
	public char getAnswer()
	{
		return answer;
	} // end of method getAnswer()

	/**
	 * Returns the answer choices for this question.
	 * 
	 * @return the answer choices for this question.
	 */
	public ArrayList<String> getAnswerChoices()
	{
		return answerChoicesList;
	} // end of method getAnswerChoices()

	/**
	 * Returns the answer choices for this question in the form of a string.
	 * 
	 * @return the answer choices for this question.
	 */
	public String getAnswerChoicesString()
	{
		return answerChoicesString;
	} // end of method getAnswerChoices()

	/**
	 * Returns the answer explanation for this question.
	 * 
	 * @return the answer explanation for this question.
	 */
	public String getAnswerExplanation()
	{
		return answerExplanation;
	} // end of method getAnswerExplanation()

	/**
	 * Returns the question text for this question.
	 * 
	 * @return the question text for this question.
	 */
	public String getQuestion()
	{
		return question;
	} // end of method getQuestion()

	/**
	 * Returns the tags associated with this question.
	 * 
	 * @return the tags associated with this question.
	 */
	public ArrayList<String> getTags()
	{
		return tags;
	} // end of method getTags()

	/**
	 * Returns a string representation of this question.
	 * 
	 * @return a string representation of this question
	 */
	public String toString()
	{
		String stringRepresentation = "";

		String questionString = this.getQuestion();
		if (questionString.length() > 16)
		{
			stringRepresentation = stringRepresentation.concat(questionString.substring(0, 16) + "...");
		}
		else
		{
			stringRepresentation = stringRepresentation.concat(questionString);
			int numberOfSpaces = 19 - questionString.length();
			for (int i = 0; i < numberOfSpaces; i++)
			{
				stringRepresentation = stringRepresentation.concat(" ");
			}
		}
		stringRepresentation = stringRepresentation.concat(" | ");

		if (answerChoicesString.length() > 16)
		{
			stringRepresentation = stringRepresentation.concat(answerChoicesString.substring(0, 16) + "...");
		}
		else
		{
			stringRepresentation = stringRepresentation.concat(answerChoicesString);
			int numberOfSpaces = 19 - answerChoicesString.length();
			for (int i = 0; i < numberOfSpaces; i++)
			{
				stringRepresentation = stringRepresentation.concat(" ");
			}
		}
		stringRepresentation = stringRepresentation.concat(" | ");

		stringRepresentation = stringRepresentation.concat("Ans: " + this.getAnswer() + " | ");

		String explanation = this.getAnswerExplanation();
		if (explanation.length() > 16)
		{
			stringRepresentation = stringRepresentation.concat(explanation.substring(0, 16) + "...");
		}
		else
		{
			stringRepresentation = stringRepresentation.concat(explanation);
			int numberOfSpaces = 19 - explanation.length();
			for (int i = 0; i < numberOfSpaces; i++)
			{
				stringRepresentation = stringRepresentation.concat(" ");
			}
		}
		stringRepresentation = stringRepresentation.concat(" | ");

		ArrayList<String> tags = this.getTags();
		String tagsString = "";
		Iterator<String> it = tags.iterator();

		while (it.hasNext())
		{
			String tag = it.next();
			if (tag.trim().length() > 0)
			{
				tagsString = tagsString.concat(tag + ", ");
			}
			else
			{
				it.remove();
			}
		}

		if (tagsString.length() > 16)
		{
			stringRepresentation = stringRepresentation.concat(tagsString.substring(0, 16) + "...");
		}
		else
		{
			stringRepresentation = stringRepresentation.concat(tagsString);
			int numberOfSpaces = 19 - tagsString.length();
			for (int i = 0; i < numberOfSpaces; i++)
			{
				stringRepresentation = stringRepresentation.concat(" ");
			}
		}
		return stringRepresentation;

	}

	public int compareTo(Question question)
	{
		// Checks whether the questions have tags. If so, compares the questions based
		// on their first (alphabetical) tags.
		if (this.tags.size() != 0 && question.getTags().size() != 0)
		{
			int tagComparison = this.tags.get(0).compareToIgnoreCase(question.getTags().get(0));
			if (tagComparison != 0)
			{
				return tagComparison;
			}
		}
		else if (this.tags.size() != 0)
		{
			return 1;
		}
		else if (question.getTags().size() != 0)
		{
			return -1;
		}

		// If the questions have the same first tag or no tags, compares the questions
		// lexicographically based on their question texts. The comparison of fields
		// beyond question text is meaningless, so the method returns zero if the
		// question texts are the same.
		if (this.question.compareTo(question.question) > 0)
			return 1;
		else if (this.question.compareTo(question.question) == 0)
			return 0;
		else
			return -1;
	}
}
