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
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

/**
 * A dialog displayed when the user terminates the application.
 * 
 * @author Harish Ramachandran
 *
 */
public class TerminationDialog extends JDialog
{
	JButton okButton;
	
	private final JPanel contentPanel = new JPanel();

	public static void main(String[] args)
	{
		try
		{
			TerminationDialog dialog = new TerminationDialog(true, true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Constructs the termination dialog.
	 */
	public TerminationDialog(boolean questionsWritten, boolean answerHistoriesWritten)
	{
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setDividerSize(0);
			splitPane.setEnabled(false);
			splitPane.setResizeWeight(0.5);
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			GridBagConstraints gbc_splitPane = new GridBagConstraints();
			gbc_splitPane.fill = GridBagConstraints.BOTH;
			gbc_splitPane.gridx = 0;
			gbc_splitPane.gridy = 0;
			contentPanel.add(splitPane, gbc_splitPane);
			{
				JLabel lblNewLabel_1 = new JLabel("Goodbye.");
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
				splitPane.setRightComponent(lblNewLabel_1);
			}
			{
				JSplitPane splitPane_1 = new JSplitPane();
				splitPane_1.setEnabled(false);
				splitPane_1.setDividerSize(0);
				splitPane_1.setResizeWeight(0.5);
				splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
				splitPane.setLeftComponent(splitPane_1);
				{
					String message;
					if(questionsWritten) message = "Questionbank written.";
					else message = "Oops! Questionbank could not be written.";
					JLabel lblNewLabel = new JLabel(message);
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					splitPane_1.setLeftComponent(lblNewLabel);
				}
				{
					String message;
					if(questionsWritten) message = "Answer histories written.";
					else message = "Oops! Answer histories could not be written.";
					JLabel lblNewLabel_2 = new JLabel(message);
					lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
					splitPane_1.setRightComponent(lblNewLabel_2);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
