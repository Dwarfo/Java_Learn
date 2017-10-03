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
	
	private DefaultTableModel populateTable () {
		
		String line = null;
        int i=0;
        boolean columnsNotInitialized = true;
        DefaultTableModel dtm = new DefaultTableModel();
        
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
                	columnsNotInitialized = false;
                }
                Object[] dataObject = data.toArray();
                dtm.addRow(dataObject); 
                System.out.println(".................................");
                
            }
            
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return dtm;
		
	}

	public JTable create() throws IOException {
       
             
       JTable jTable2 = new JTable(populateTable());
       //jTable2.setModel(dtm);
        
       
		return jTable2;
	}


}

