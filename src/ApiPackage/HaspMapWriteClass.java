package ApiPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class HaspMapWriteClass {

	static Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();

	public static void HaspPutData(String TagIdValue, String OpenIdValue, String Response) throws Exception {

		// To generate uniqueId to generate for haspmap
		String uniqueID = UUID.randomUUID().toString();

		data.put(uniqueID, new Object[] { TagIdValue, OpenIdValue, Response });

		System.out.println("HaspMap Value has been put successfully" + "" + "=" + uniqueID);

	}

	public static void HaspMapExcelWrite() throws IOException {

		Properties Apiobj = new Properties();

		FileInputStream Apiobjfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");

		Apiobj.load(Apiobjfile);

		String WriteExcelFileName = Apiobj.getProperty("ResultExcelFileName");
		String ResultSheetName    = Apiobj.getProperty("ResultSheetName");
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(ResultSheetName);
		Set<String> keyset = data.keySet();

		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				}
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(
					new File(System.getProperty("user.dir") + "\\" + WriteExcelFileName));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
