package UTP171;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;

public class CountryTable {

	private String fileName; 
	public CountryTable(String countriesFileName) {
		this.fileName = countriesFileName;
	}
	
	private Class[] getTableClasses() {
		
		int i = 0;
		String line = null;
		boolean secondLine = true;
		Vector data = new Vector();
		try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));

            while ((line = br.readLine()) != null) {
                data = new Vector();
                StringTokenizer st1 = new StringTokenizer(line, "	");
                while (st1.hasMoreTokens()) {
                    String str = st1.nextToken();
                    data.add(str);
                    if(secondLine) i++;
                }
                if(secondLine) {
                	secondLine = false;
                }
                else
                	break; 
                System.out.println(".................................");   
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		System.out.println(data);
		
		Class[] classes = new Class[i];
		Object[] rows2 = data.toArray();
		String[] rows = new String[i];
		System.out.println(rows2[0]);
		
		/*for(int j=0;j<i;j++) {
			if(checkNumber(rows[j])) {
            	rows2[j] = (Integer)rows2[j];
            }
			else
				rows2[j] = rows2[j].toString();
			classes[j] = rows[j].getClass();	
		}*/
		
		return classes;
		
	}
	
	private DefaultTableModel populateTable (/*Class[] columnClass*/) {
		
		String line = null;
        int i=0;
       /* final Class[] columnClass = getTableClasses() new Class[] {
    		    Integer.class, String.class, Double.class, Boolean.class
    		};*/
        
        boolean columnsNotInitialized = true;
        DefaultTableModel dtm = new DefaultTableModel() {
        	/*@Override
        	public Class<?> getColumnClass(int columnIndex)
        	{
        	    return columnClass[columnIndex];
        	}*/
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
                    System.out.println(str);
                }
                System.out.println(data);
                if(columnsNotInitialized) {
                	//for(int j=0;j<i;j++)dtm.addColumn(null);
                	dtm.setColumnCount(i);
                	dtm.setColumnIdentifiers(data);
                	columnsNotInitialized = false;
                }
                else
                	dtm.addRow(data); 
                System.out.println(".................................");   
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
		    System.out.println("Is number!");
		    return true;
	}
	

	
	public JTable create() throws IOException {
       
             
   // JTable jTable2 = new JTable(/*populateTable(getTableClasses())*/);
    JTable jTable2 = new JTable(populateTable());
    //jTable2.setModel(dtm);
       
        
       
	return jTable2;
	}


}

