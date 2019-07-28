import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * A menu allowing the user to select training options.
 * 
 * @author Harish Ramachandran.
 */
public class TrainingMenu
{
	JButton beginButton;
	JButton cancelButton;
	JCheckBox everyQuestionCheckBox;
	JFrame frame;
	JTextField frequencyTextField;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					TrainingMenu window = new TrainingMenu();
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
	 * Constructs a training menu.
	 */
	public TrainingMenu()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setTitle("Training");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);

		// ***left panel*** //
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.weightx = 1.0;
		gbc_panel.weighty = 1.0;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_2 = new JLabel("Corrections");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		everyQuestionCheckBox = new JCheckBox("After every question");
		GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_2.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxNewCheckBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_2.gridx = 1;
		gbc_chckbxNewCheckBox_2.gridy = 1;
		panel.add(everyQuestionCheckBox, gbc_chckbxNewCheckBox_2);

		frequencyTextField = new JTextField();
		frequencyTextField.setEnabled(false);
		frequencyTextField.getDocument().addDocumentListener(new DocumentListener()
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
				if (frequencyTextField.getText().equals(""))
				{
					beginButton.setEnabled(false);
				}
				else
				{
					try
					{
						Integer.parseInt(frequencyTextField.getText());
						beginButton.setEnabled(true);
					}
					catch (NumberFormatException e)
					{
					}
				}
			}
		});
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.EAST;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 2;
		panel.add(frequencyTextField, gbc_textField_1);
		frequencyTextField.setColumns(10);

		JCheckBox frequencyCheckBox = new JCheckBox("After every ___ questions");
		GridBagConstraints gbc_chckbxAfterEvery = new GridBagConstraints();
		gbc_chckbxAfterEvery.anchor = GridBagConstraints.WEST;
		gbc_chckbxAfterEvery.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxAfterEvery.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxAfterEvery.gridx = 1;
		gbc_chckbxAfterEvery.gridy = 2;
		panel.add(frequencyCheckBox, gbc_chckbxAfterEvery);

		JCheckBox afterSessionCheckBox = new JCheckBox("After session");
		afterSessionCheckBox.setSelected(true);
		GridBagConstraints gbc_chckbxAfterSession = new GridBagConstraints();
		gbc_chckbxAfterSession.anchor = GridBagConstraints.WEST;
		gbc_chckbxAfterSession.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxAfterSession.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxAfterSession.gridx = 1;
		gbc_chckbxAfterSession.gridy = 3;
		panel.add(afterSessionCheckBox, gbc_chckbxAfterSession);

		beginButton = new JButton("Begin");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		panel.add(beginButton, gbc_btnNewButton);

		cancelButton = new JButton("Cancel");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 4;
		panel.add(cancelButton, gbc_btnNewButton_1);

		everyQuestionCheckBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!everyQuestionCheckBox.isSelected()) everyQuestionCheckBox.setSelected(true);
				frequencyCheckBox.setSelected(false);
				afterSessionCheckBox.setSelected(false);
				frequencyTextField.setText("");
				frequencyTextField.setEnabled(false);

				beginButton.setEnabled(true);
			}
		});
		frequencyCheckBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!frequencyCheckBox.isSelected()) frequencyCheckBox.setSelected(true);
				everyQuestionCheckBox.setSelected(false);
				afterSessionCheckBox.setSelected(false);
				frequencyTextField.setEnabled(true);

				beginButton.setEnabled(false);
			}
		});
		afterSessionCheckBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!afterSessionCheckBox.isSelected()) afterSessionCheckBox.setSelected(true);
				everyQuestionCheckBox.setSelected(false);
				frequencyCheckBox.setSelected(false);
				frequencyTextField.setText("");
				frequencyTextField.setEnabled(false);

				beginButton.setEnabled(true);
			}
		});
	}
}
