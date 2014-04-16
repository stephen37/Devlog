package gestionnaire.gui;

import gestionnaire.Gestionnaire;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import personnages.Personnage;

@SuppressWarnings("serial")
public class PersonnagesUI extends JDialog {
	Personnage personnage;
	JTable list_personnages;
	DefaultTableModel tmodel;
	JPanel content_pane;
	Gestionnaire gestionnaire;

	public PersonnagesUI(JFrame owner, Gestionnaire gestionnaire) {
		super(owner);
		init();
		initListPersonnages();
		initPersonnagesIntoTable();
		gestionnaire = this.gestionnaire;
		pack();

	}

	private void init() {
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);

		content_pane = new JPanel();
		content_pane
				.setLayout(new BoxLayout(content_pane, BoxLayout.PAGE_AXIS));

		this.setContentPane(content_pane);
		this.setVisible(true);
	}

	private void initListPersonnages() {
		/* Initializing a JList for series */
		String[] colName = { "Nom", "Race", "Points vie", "Vitesse", "Force" };
		tmodel = new DefaultTableModel() {

		};
		// /!\ Have to set the column Identifiers... or maybe with a
		// DefaultColumnTableModel would work!
		tmodel.setColumnIdentifiers(colName);

		list_personnages = new JTable(tmodel) {
			/* Making only the column Vu? editable! */
			public boolean isCellEditable(int row, int col) {
				switch (col) {
				case 3:
					return true;
				default:
					return false;
				}
			}

			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}
		};

		/* auto sorting when clicking on headers */
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(
				tmodel);
		list_personnages.setRowSorter(sorter);

		/* Adding a Listener for Jtable modifications (Column Vu?) */
		TableModelListener jtable_listener = new TableModelListener() {
			/*
			 * TODO: this is crappy since it is called everytime the Jtable is
			 * changed. Even when initializing it, it is fired for non editable
			 * cells. Find a way of adding a Listener on specifics Cells
			 */
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 3) {
					int row = e.getFirstRow();
					int column = e.getColumn();
					DefaultTableModel model = (DefaultTableModel) e.getSource();
				}
			}
		};

		list_personnages.getModel().addTableModelListener(jtable_listener);

		/* a Scroll pane */
		JScrollPane scrollPane = new JScrollPane(list_personnages);
		// ist_episodes.setFillsViewportHeight(true);
		content_pane.add(scrollPane);

	}

	private void initPersonnagesIntoTable() {
		tmodel.setRowCount(0); // Clearing the table model
		for (Personnage perso : gestionnaire.getPersonnages()) {
			Object[] test = { perso, perso.getNom(), perso.getVie(),
					perso.getForce(), perso.getVitesse() };
			tmodel.addRow(test);
		}

	}

}
