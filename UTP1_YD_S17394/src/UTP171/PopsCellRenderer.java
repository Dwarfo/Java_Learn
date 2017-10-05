package UTP171;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class PopsCellRenderer extends DefaultTableCellRenderer{
		  
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if((Integer)value > 20000) {
				cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				cellComponent.setBackground(Color.RED);
				value = NumberFormat.getNumberInstance().format(value);
				//System.out.println((Integer)value);
		}
		else {
			cellComponent.setBackground(Color.WHITE);
			value = NumberFormat.getNumberInstance().format(value);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
		
}



