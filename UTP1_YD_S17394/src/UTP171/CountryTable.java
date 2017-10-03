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
	
	private void populate(DefaultTableModel dtm, Vector columnNames) {
		
		String line = null;
        boolean isColumnName = true; 
		try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                Vector data = new Vector();
                StringTokenizer st1 = new StringTokenizer(line, "    ");
                while (st1.hasMoreTokens()) {
                    String nextToken = st1.nextToken();
                    data.add(nextToken);
                    System.out.println(nextToken);
                }
                System.out.println(data);
                 
                	dtm.addRow(data);//add here 
                	System.out.println(".................................");
                
            }
            
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	public JTable create() throws IOException {
		
		
		//JTable jTable2 = new JTable();
        DefaultTableModel dtm = new DefaultTableModel();
        //populate(dtm, columnNames);
        String line = null;
        int i=0;
        boolean columnsNotInitialized = true;
		try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));

            while ((line = br.readLine()) != null) {
                Vector data = new Vector();
                StringTokenizer st1 = new StringTokenizer(line, "	");
                while (st1.hasMoreTokens()) {
                    String str = st1.nextToken();
                    data.add(str);
                    if(columnsNotInitialized) i++;
                    System.out.println(str);
                }
                System.out.println(data);
                if(columnsNotInitialized) {
                	for(int j=0;j<i;j++)dtm.addColumn(null);
                	columnsNotInitialized=false;
                }
                Object[] dataObject = data.toArray();
                dtm.addRow(dataObject); 
                //dtm.addColumn();
                System.out.println(".................................");
                
            }
            
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
		
       DefaultTableModel dtm2 = new DefaultTableModel();
       /*Vector row = new Vector();
       row.add("Enter data to column 1");
       row.add("Enter data to column 2");
       row.add("Enter data to column 3");*/
       
      dtm2.addColumn(new Object[] {"a","b","c","d"});
       System.out.println(dtm2.getColumnCount());
       System.out.println(dtm2.getRowCount());
       
       JTable jTable2 = new JTable(dtm);
       jTable2.setModel(dtm);
        
       //System.out.println((String)dtm.getValueAt(1,0));
		return jTable2;
	}


}

