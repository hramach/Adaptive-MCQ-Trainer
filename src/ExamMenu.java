import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A menu with exam options.
 * 
 * @author Harish Ramachandran
 */
public class ExamMenu
{

	JFrame frame;
	JTextField numberTextField;
	JButton beginButton;
	JButton cancelButton;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ExamMenu window = new ExamMenu();
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
	 * Creates the exam menu.
	 * 
	 */
	public ExamMenu()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setTitle("Exam");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Number of questions");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		numberTextField = new JTextField();
		numberTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				changed();
			}

			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}

			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}

			public void changed()
			{
				beginButton.setEnabled(false);
				if (!numberTextField.getText().equals(""))
				{
					try
					{
						Integer.parseInt(numberTextField.getText());
						beginButton.setEnabled(true);
					}
					catch (NumberFormatException e)
					{
						// Do not enable the button in this case.
					}
				}
			}
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		frame.getContentPane().add(numberTextField, gbc_textField);
		numberTextField.setColumns(10);

		beginButton = new JButton("Begin");
		beginButton.setEnabled(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		gbc_btnNewButton.weightx = 0.5;
		frame.getContentPane().add(beginButton, gbc_btnNewButton);

		cancelButton = new JButton("Cancel");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 2;
		gbc_btnNewButton_1.weightx = 0.5;
		frame.getContentPane().add(cancelButton, gbc_btnNewButton_1);
	}

}
