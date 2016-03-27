package sg.edu.nus.iss.gui;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import sg.edu.nus.iss.utils.ConfirmDialog;
import sg.edu.nus.iss.store.Category;
import sg.edu.nus.iss.store.Vendor;

/*
 * Author: Sanskar Deepak
 */

public class VendorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StoreApplication manager;
	private JScrollPane scroller;
	private JTable table;
	private static final String[] COLUMN_NAMES = {"Vendor Name", "Description"};
	private AbstractTableModel vendorTableModel;
	private ArrayList<Vendor> vendors ;
	private String action_source;
	private JComboBox<String> categoryBox;
	private Border raisedetched;
	private Border loweredetched; 
	private VendorPanel vp;
	private JTextField vNameT;
	private JTextField vDescT ;

	public VendorPanel(StoreApplication manager) {
		this.manager = manager;
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 
		vp = this;
		setLayout (new BorderLayout());
		vendors = manager.getVendors();
		add(createButtonPanel(), BorderLayout.EAST);
		add(showVendorListPanel(), BorderLayout.CENTER);
		add(createAddVendorPanel(), BorderLayout.SOUTH);

	}

	//*****************To create add Vendor Panel**********************

	private JPanel createAddVendorPanel () { 

		JPanel panel = new JPanel(new GridLayout(1, 0, 10, 0));

		JLabel label = new JLabel("Add Vendor: ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12 ));
		
		JLabel categoryLabel = new JLabel("Select Category");
		categoryBox = new JComboBox<String>();
		for(Category c:manager.getCategories()){
			categoryBox.addItem(c.getCategoryName());
		}
		JLabel vName = new JLabel("Vendor Name: ");
		vNameT = new JTextField();
		JLabel vDesc = new JLabel("Description: ");
		vDescT = new JTextField();

		panel.add(label);
		panel.add(vName);
		panel.add(vNameT);
		panel.add(vDesc);
		panel.add(vDescT);

		JButton addBtn = new JButton ("Add");
		addBtn.addActionListener (new ActionListener () { 
			public void actionPerformed (ActionEvent e) {
				action_source  =((JButton)e.getSource()).getText();
				if (vNameT.getText().length()!=0 && vDescT.getText().length()!=0){
							if (!(manager.addVendor(vNameT.getText(), vDescT.getText()))){
							JOptionPane.showMessageDialog(manager.getMainWindow(),
									"Category Code Already Exists !",
									"Duplicate Category Code",
									JOptionPane.ERROR_MESSAGE); 
						} else {
							refresh();
						}
				} else {
					JOptionPane.showMessageDialog(manager.getMainWindow(),
							"Fields cannot be empty !",
							"Empty Fields",
							JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		});


		panel.add(addBtn);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel2.add(categoryBox);
		JButton addVendToCatBtn = new JButton("Add Vend. to Cat.");
		panel2.add(addVendToCatBtn);
		
		panel.add(panel2);
		
		addVendToCatBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action_source  =((JButton)e.getSource()).getText();
				if(table.getSelectedRow()!=-1){
							String vendorName = (String)table.getValueAt(table.getSelectedRow(), 0);
							String vendorDescription = (String)table.getValueAt(table.getSelectedRow(), 1);
							//System.out.println("inside add vend to cat button "+vendorName);
							//System.out.println("inside add vend to cat button "+vendorDescription);
							//System.out.println(getCategory(getSelectedCategory()));
							if(!(manager.addVendor(vendorName, vendorDescription, getCategory(getSelectedCategory())))){
								JOptionPane.showMessageDialog(manager.getMainWindow(),
										"Vendor already exists in the category!",
										"Duplicate Vendor",
										JOptionPane.ERROR_MESSAGE); 
							} else {
								AddVendorToCategoryDialog d = new AddVendorToCategoryDialog(manager, vp);
								d.setModal(true);
								d.setLocationRelativeTo(manager.getMainWindow());
								d.pack();
								d.setVisible (true);
							}
				}else {
					JOptionPane.showMessageDialog( manager.getMainWindow(),
							"Please select a row to add!",
							"Select a Row",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		panel.setBorder(BorderFactory.createTitledBorder(
				loweredetched, "New Category Pane")); 

		return panel;
	}

	//*****************To Show all Buttons **********************	

	private JPanel createButtonPanel(){ 
		JPanel p = new JPanel(new GridLayout(0,1,0,10));
		JPanel panel = new JPanel(new BorderLayout());
		JButton backBtn = new JButton("Back");

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action_source = (((JButton)e.getSource()).getText());
				refresh();
			}
		});

		JButton removeBtn = new JButton ("Remove");
		removeBtn.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				String s =null;
				action_source  =(((JButton)e.getSource()).getText());
				if(table.getSelectedRow()!=-1){ //If at least one row is selected

					for(int i = 0 ; i<table.getColumnCount() ; i++)
					{
						if(table.getColumnName(i).equalsIgnoreCase("Vendor Name")){
							s = (String)table.getValueAt(table.getSelectedRow(), i);
							showConfirmDialog(s);
						}
					}
				}  else {
					JOptionPane.showMessageDialog( manager.getMainWindow(),
							"Please select a row to remove!",
							"Select a Row",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		p.add(backBtn);
		p.add(removeBtn);

		panel.add(p, "North");
		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;

	}

	//*****************To Show Vendor List Panel**********************	

	private JPanel showVendorListPanel() {  
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Vendors : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		table = new JTable();	
		table.setModel(getTableModel());
		vendorTableModel.fireTableDataChanged();
		scroller = new JScrollPane(table); //scroller automatically puts the table header at the top
		table.setFillsViewportHeight(true); // true : table uses the entire height of the container, even if the table doesn't have enough rows to use the whole vertical space. 

		panel.add(label, "North");
		panel.add(scroller, "Center");

		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;

	}

	//**********************Set Table Mode******************************	

	public TableModel getTableModel() {
		if (vendorTableModel != null) 
			return vendorTableModel;
		else {
			vendorTableModel = new AbstractTableModel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public String getColumnName(int column) {
					return COLUMN_NAMES[column];
				}

				@Override
				public int getRowCount() {
					return vendors.size();
				}

				@Override
				public int getColumnCount() {
					return COLUMN_NAMES.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Vendor vendor = vendors.get(rowIndex);
					switch (columnIndex) {
					case 0: return vendor.getVendorName();
					case 1: return vendor.getDescription();
					default: return null;
					}
				}
			};

			return vendorTableModel;
		}
	}


	//******************Reflect the changes done on the screen by the buttons******************

	public void refresh(){   
		if(action_source.equalsIgnoreCase("Add")){
			int rowIndex = vendors.size()-1;
			vendorTableModel.fireTableRowsInserted(rowIndex, rowIndex);
			vNameT.setText(null);
			vDescT.setText(null);
		}
		else if(action_source.equalsIgnoreCase("Remove")){
			vendorTableModel.fireTableRowsDeleted(table.getSelectedRow(), table.getSelectedRow());
		}

		else if(action_source.equalsIgnoreCase("Back")){
			removeAll();
			add("Center",manager.createMainPanel());
			revalidate();
			repaint();
		} 

	}


	// ************Show the confirm dialog on removing and performs the remove functionality*********

	public void showConfirmDialog(String s) { 
		String title = "Remove Vendor";
		String msg = "Do you really want to remove Vendor " + s + " ?";
		ConfirmDialog d = new ConfirmDialog (manager.getMainWindow(), title, msg) {

			private static final long serialVersionUID = 1L;

			protected boolean performOkAction () {
				manager.removeVendor(s);
				refresh();
				return true;
			}
		};
		d.setModal(true);
		d.setLocationRelativeTo(this);
		d.pack();
		d.setVisible (true);
	}
	
	public String getSelectedCategory() {
		return (String)categoryBox.getSelectedItem();
	}
	
	public Category getCategory(String categoryName) {
		for(Category c :manager.getCategories()) {
			if (c.getCategoryName().equals(categoryName)) {
				return c;
			}
		}
		return null;
		
	}

}
