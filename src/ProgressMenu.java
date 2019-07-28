import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

/**
 * A menu allowing the user to view his/her progress.
 * 
 * @author Harish Ramachandran
 */
public class ProgressMenu extends JDialog
{
	JButton okButton;

	JLabel accuracyLabel;
	JLabel answeredLabel;
	JLabel correctLabel;

	private final JPanel contentPanel = new JPanel();

	public static void main(String[] args)
	{
		try
		{
			ProgressMenu dialog = new ProgressMenu(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Constructs the progress menu.
	 */
	public ProgressMenu(HashMap<String, int[]> answerHistories)
	{
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		int[] overallRatio = answerHistories.get("Overall");
		Object[] tagList = answerHistories.keySet().toArray();

		setTitle("Progress");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();

		contentPanel.setLayout(gbl_contentPanel);

		JLabel lblNewLabel = new JLabel("Tag:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.weightx = 0.25;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Answered:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		answeredLabel = new JLabel("");
		if (overallRatio != null)
			answeredLabel.setText("" + overallRatio[1]);
		else if (tagList.length > 0)
		{
			answeredLabel.setText("" + answerHistories.get(tagList[0])[1]);
		}

		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 1;
		contentPanel.add(answeredLabel, gbc_lblNewLabel_4);

		JLabel lblNewLabel_2 = new JLabel("Correct:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		correctLabel = new JLabel("");
		if (overallRatio != null)
			correctLabel.setText("" + overallRatio[0]);
		else if (tagList.length > 0)
		{
			correctLabel.setText("" + answerHistories.get(tagList[0])[0]);
		}

		GridBagConstraints gbc_correctLabel = new GridBagConstraints();
		gbc_correctLabel.insets = new Insets(0, 0, 5, 0);
		gbc_correctLabel.gridx = 1;
		gbc_correctLabel.gridy = 2;
		contentPanel.add(correctLabel, gbc_correctLabel);

		JLabel lblNewLabel_3 = new JLabel("Accuracy:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JLabel accuracyLabel = new JLabel("");
		if (overallRatio != null)
			accuracyLabel.setText("" + (Math.round((double) overallRatio[0] / overallRatio[1] * 100)) + "%");
		else if (tagList.length > 0)
		{
			accuracyLabel.setText(
					"" + (Math.round((double) answerHistories.get(tagList[0])[0] / answerHistories.get(tagList[0])[1] * 100))
							+ "%");
		}

		GridBagConstraints gbc_accuracyLabel = new GridBagConstraints();
		gbc_accuracyLabel.gridx = 1;
		gbc_accuracyLabel.gridy = 3;
		contentPanel.add(accuracyLabel, gbc_accuracyLabel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weightx = 0.5;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		contentPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JComboBox comboBox = new JComboBox();
		for (Object tag : tagList)
		{
			comboBox.addItem(tag);
		}

		if (overallRatio != null)
		{
			comboBox.setSelectedItem("Overall");
		}
		else if (tagList.length > 0)
		{
			comboBox.setSelectedItem(tagList[0]);
		}

		comboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String selectedTag = (String) comboBox.getSelectedItem();
				int[] selectedRatio = answerHistories.get(selectedTag);

				correctLabel.setText("" + selectedRatio[0]);
				answeredLabel.setText("" + selectedRatio[1]);
				accuracyLabel.setText("" + (Math.round((double) selectedRatio[0] / selectedRatio[1] * 100)) + "%");
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);

	}

}
