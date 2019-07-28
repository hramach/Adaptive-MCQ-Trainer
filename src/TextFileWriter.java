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
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A window asking the user for a text file to be written to.
 * 
 * @author Harish Ramachandran
 */
public class TextFileWriter extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	JTextField textField;
	JButton okButton;
	JButton cancelButton;

	public static void main(String[] args)
	{
		try
		{
			TextFileWriter dialog = new TextFileWriter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creates the window asking for a text file to be written to.
	 */
	public TextFileWriter()
	{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		contentPanel.setLayout(gbl_contentPanel);

		JLabel lblNewLabel = new JLabel("Write question bank and answer histories to a .txt file.");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Question file name:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		contentPanel.add(textField, gbc_textField);
		textField.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setEnabled(false);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);

		textField.getDocument().addDocumentListener(new DocumentListener()
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
				if (textField.getText().equals(""))
				{
					okButton.setEnabled(false);
				}
				else
				{
					okButton.setEnabled(true);
				}
			}
		});

	}

}
