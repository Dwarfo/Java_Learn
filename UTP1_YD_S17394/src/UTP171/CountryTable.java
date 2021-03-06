package UTP171;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sun.prism.paint.Color;
import com.sun.xml.internal.ws.api.Component;

import javax.swing.JTable;

public class CountryTable {

	private String fileName; 
	public CountryTable(String countriesFileName) {
		this.fileName = countriesFileName;
	}
	
	private Class[] getTableClasses() {
		
		int i = 0;
		Class[] classes = new Class[0];
		
		try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));
            String line = br.readLine();
            StringTokenizer st1 = new StringTokenizer(line, "	");
            while (st1.hasMoreTokens()) {
                String str = st1.nextToken();
                i++;                
            }
            
            classes = new Class[i];
            //System.out.println(classes.length);
            i = 0;
            line = br.readLine();
            st1 = new StringTokenizer(line, "	");
            
            while (st1.hasMoreTokens()) {
            	String str = st1.nextToken();
            	if(checkNumber(str)) {
            		classes[i] = Integer.class;
            		//System.out.println("Became Integer");
                }
            	else {
            		classes[i] = String.class;
            		//System.out.println("Became String");
            	}
            	i++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return classes;
		
	}
	
	private DefaultTableModel populateTable (Class[] columnClass) {
		
		String line = null;
        int i=0;
        
        boolean columnsNotInitialized = true;
        DefaultTableModel dtm = new DefaultTableModel() {
        	@Override
        	public Class<?> getColumnClass(int columnIndex)
        	{
        	    return columnClass[columnIndex];
        	}
        };
        
		try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));

            while ((line = br.readLine()) != null) {
                Vector data = new Vector();
                StringTokenizer st1 = new StringTokenizer(line, "	");
                while (st1.hasMoreTokens()) {
                    String str = st1.nextToken();
                    if(checkNumber(str)) {
                    	data.add(Integer.parseInt(str));
                    }
                    else {
                    	data.add(str);
                    }
                    if(columnsNotInitialized) i++;
                   // System.out.println(str);
                }
                //System.out.println(data);
                if(columnsNotInitialized) {
                	//for(int j=0;j<i;j++)dtm.addColumn(null);
                	dtm.setColumnCount(i);
                	dtm.setColumnIdentifiers(data);
                	columnsNotInitialized = false;
                }
                else
                	dtm.addRow(data); 
               //System.out.println(".................................");   
            }
            
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return dtm;
		
	}

	private boolean checkNumber(String s) {

		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    //System.out.println("Is number!");
		    return true;
	}
	
	private JTable colorCell(JTable table) {
		
		int columns = table.getColumnCount();
		int j=0;
		
		while(j<columns) {
			if(table.getColumnName(j).equals("Population"))break; 
			//System.out.println(table.getColumnName(j));
			j++;
		}
		//System.out.println(table.getColumnName(j));
		System.out.println(j);
		table.getColumnModel().getColumn(j).setCellRenderer(new PopsCellRenderer());
		
		return table;
	}
	
	
	public JTable create() throws IOException {
       
    System.out.println(populateTable(getTableClasses()).getColumnCount());        
    JTable jTable2 = new JTable(populateTable(getTableClasses()));
    jTable2 = colorCell(jTable2);
       
	return jTable2;
	}


}

