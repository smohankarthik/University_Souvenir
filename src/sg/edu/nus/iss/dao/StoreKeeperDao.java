package sg.edu.nus.iss.dao;

import java.io.IOException;
import java.util.ArrayList;
import static sg.edu.nus.iss.utils.StoreConstants.STOREKEEPER_PATH;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.StoreKeeper;

/**
 * 
 * @author Sanskar Deepak
 *
 */
public class StoreKeeperDao extends BaseDao{
	private ArrayList<StoreKeeper> storeKeeperList;
	
	public  StoreKeeperDao() {

		storeKeeperList = new ArrayList<StoreKeeper>();
	}
	
	public ArrayList<StoreKeeper> createListFromFile() throws IOException{
		ArrayList<String> fileList = new ArrayList<String>();
		fileList = super.readFromFile(STOREKEEPER_PATH);
		
		if(fileList!=null)
		{
			for(String line: fileList)
			{
				String storeData[] = line.split(",");
				try {
					storeKeeperList.add(new StoreKeeper(storeData[0],storeData[1]));
				} catch (BadValueException e) {
					e.printStackTrace();
				}
			}
		}
		return storeKeeperList;
	
	}
	
	public void writeToFile(ArrayList<StoreKeeper> storeList) throws IOException
	{
		ArrayList<StringBuffer> writeCategory = new ArrayList<>();
		for(StoreKeeper store : storeList)
		{
			StringBuffer catLine = new StringBuffer();
			catLine.append(store.getStoreKeeperName());
			catLine.append(",");
			catLine.append(store.getPassword());
			writeCategory.add(catLine);
		}
		super.writeToFile(writeCategory, STOREKEEPER_PATH);
	}
	
}

