package sg.edu.nus.iss.gui;

import javax.swing.JPanel;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -8820162333877980019L;
	private StoreApplication manager;
	private MemberPanel memberPanel;
	private ProductPanel productPanel;

	/**
	 * Create the panel.
	 */
	public MainPanel(StoreApplication manager) {
		this.manager = manager;
		setLayout(new GridLayout(1, 0, 80, 0));
		
		JPanel panelLeft = new JPanel();
		add(panelLeft);
		panelLeft.setLayout(new GridLayout(0, 1, 0, 80));
		
		JButton btnMakeATransaction = new JButton("Make a Transaction");
		btnMakeATransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelLeft.add(btnMakeATransaction);
		
		JButton btnMemberRegistration = new JButton("Member Registration");
		btnMemberRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberPanel = new MemberPanel(manager);
				removeAll();
				setLayout(new BorderLayout());
				memberPanel.setVisible(true);
				add("Center", memberPanel);
				revalidate();
				repaint();
			}
		});
		panelLeft.add(btnMemberRegistration);
		
		JPanel panelRight = new JPanel();
		add(panelRight);
		panelRight.setLayout(new GridLayout(0, 1, 0, 40));

		
		JButton btnCategory = new JButton("Category");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panelRight.add(btnCategory);
		
		JButton productBtn = new JButton("Product");
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				productPanel = new ProductPanel(manager);
				removeAll();
				setLayout(new BorderLayout());
				productPanel.setVisible(true);
				add("Center", productPanel);
				revalidate();
				repaint();
			}
		});
		panelRight.add(productBtn);
		
		JButton vendorBtn = new JButton("Vendor");
		vendorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelRight.add(vendorBtn);
		
		JButton reportBtn = new JButton("Report");
		reportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelRight.add(reportBtn);
		
		JButton btnDiscount = new JButton("Discount");
		btnDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelRight.add(btnDiscount);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		panelRight.add(btnExit);

	}

	public void refresh() {
		// TODO Auto-generated method stub
		memberPanel.refresh();
		
	}

}