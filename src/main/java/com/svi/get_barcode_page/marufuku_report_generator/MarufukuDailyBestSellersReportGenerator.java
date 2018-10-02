package com.svi.get_barcode_page.marufuku_report_generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.svi.config.AppConfig;
import com.svi.objects.Invoice;
import com.svi.objects.Item;
import com.svi.objects.MenuSection;
import com.svi.objects.MenuSections;
import com.svi.objects.SalesReport;
/**
 * Hello world!
 *
 */
public class MarufukuDailyBestSellersReportGenerator {
	private static final String XML_EXTNSION = ".XML";
	private static final String DINNER_SHIFT = "Dinner";
	private static final String LUNCH_SHIFT = "Lunch";

	public static void main(String[] args) throws JAXBException {
		
		initializeConfig();
		List<Path> filePaths = new ArrayList<>();
		List<String> inputPaths = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		if ("Y".equalsIgnoreCase(AppConfig.IS_CURRENT_DATE.value())) {
			inputPaths.add(AppConfig.INPUT_PATH.value() + "/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		} else {
			inputPaths = getListOfInputPath();
		}
		
		for (String inputPath : inputPaths) {
			System.out.println("inputPath: " + inputPath);
			try (Stream<Path> stream = Files.walk(Paths.get(inputPath))) {
				filePaths.addAll(stream.filter(path -> Files.isRegularFile(path))
						.filter(path -> (path.getFileName().toString()).toUpperCase().contains(XML_EXTNSION))
						.collect(Collectors.toList()));
			} catch (NoSuchFileException e) {
				System.out.println("file not found: " + inputPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Map<String, List<Invoice>> dateShiftToInvoiceList = new HashMap<>();
		// parse xml and group into date and shift

		System.out.println("Parsing XML ...");
		filePaths.forEach(e -> {
			JAXBContext jaxbContext;
			try {
				System.out.println(e);
				jaxbContext = JAXBContext.newInstance(Invoice.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				Invoice invoice = (Invoice) jaxbUnmarshaller.unmarshal(e.toFile());
				String dateSplit[] = invoice.getInvoiceDate().split(" ");
				String date = dateSplit[0];
				String time = dateSplit[1];
				dateShiftToInvoiceList.computeIfAbsent(date, k -> new ArrayList<>())
				.add(invoice);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}

		});
		System.out.println("Generating Report ...");
		dateShiftToInvoiceList.forEach((date, invoiceList) -> {
			Map<String, Map <String, Map<String, Integer>>> shiftToGroupNameToItemWithQty = new HashMap<>();
			Map<String, Map<String,Integer>> groupNameToItemWithQty = new HashMap<>();
			shiftToGroupNameToItemWithQty.put(LUNCH_SHIFT, groupNameToItemWithQty);
			shiftToGroupNameToItemWithQty.put(DINNER_SHIFT, groupNameToItemWithQty);
			Map<String, Map<String, Double>> groupNameToItemWithPrice = new HashMap<>();
			SalesReport salesReport = new SalesReport();
			List<Item> groupTotals = new ArrayList<Item>();
			List<Item> itemInGroups = new ArrayList<Item>();
			MenuSections menuSections = new MenuSections();
			// MapUtil.groupNameToItemWithQty.clear();
			invoiceList.forEach(invoice -> {
				// MapUtil.computeGroupNamesQuantity(invoice);
				computeGroupNamesQuantity(invoice, shiftToGroupNameToItemWithQty);
				computeGroupNamesPrice(invoice, groupNameToItemWithPrice);
				salesReport.setTotalNoOfCustomers(
						salesReport.getTotalNoOfCustomers() + invoice.getTable().getNumberOfPax());

			
			});
			// MapUtil.groupNameToItemWithQty.forEach((groupName,itemToQtyMap)
			// -> {
			//Filling out GroupTotals and itemInGroups
			for(String shift : shiftToGroupNameToItemWithQty.keySet()){
				shiftToGroupNameToItemWithQty.get(shift).forEach((groupName, itemToQtyMap) -> {
					groupTotals
					.add(new Item("", groupName, itemToQtyMap.values().stream().mapToInt(Number::intValue).sum(),
							groupNameToItemWithPrice.get(groupName).values().stream().mapToDouble(Double::doubleValue).sum(), shift));
					menuSections.add(new MenuSection(groupName));
					itemToQtyMap.forEach((itm, qty) -> {
						itemInGroups.add(new Item(itm, groupName, qty,groupNameToItemWithPrice.get(groupName).get(itm), shift));
						menuSections.get(groupName).add(new Item(itm,groupName,qty,groupNameToItemWithPrice.get(groupName).get(itm)
								, shift));
					});			
				});	
			}
			
			String[] dateSplit = date.split("-");
			salesReport.setMenuSections(menuSections);
			LocalDate reportDate = null;
			try {
				reportDate = LocalDate.parse(dateSplit[0],formatter);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			salesReport.setDate(reportDate.getMonth() + " " +  reportDate.getDayOfMonth() + ", " + reportDate.getYear());
			salesReport.setPercentOfSD(
					((double) salesReport.getTotalNoOfSeniorDiscounts() / salesReport.getTotalNoOfCustomers()));
			salesReport.setGroupTotals(groupTotals);
			salesReport.setItemsByGroup(itemInGroups);
			System.out.println("date: " + date);
			System.out.println("customers: " + salesReport.getTotalNoOfCustomers());
			System.out.println("==============");

			try {
				printToPdfUsingJasper(salesReport, date, AppConfig.OUTPUT_PATH.value());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (JRException e1) {
				e1.printStackTrace();
			} catch (@SuppressWarnings("hiding") IOException e1) {
				e1.printStackTrace();
			}
		});

		System.out.println("DONE!");
	}

	private static List<String> getListOfInputPath() {
		List<String> inputPaths = new ArrayList<String>();
		List<String> years = getRange(AppConfig.YEAR.value(), 4);
		List<String> months = getRange(AppConfig.MONTH.value(), 2);
		List<String> days = getRange(AppConfig.DAY.value(), 2);

		for (String year : years) {
			for (String month : months) {
				for (String day : days) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append(AppConfig.INPUT_PATH.value());
					strBuilder.append("/");
					strBuilder.append(year);
					strBuilder.append("/");
					strBuilder.append(month);
					strBuilder.append("/");
					strBuilder.append(day);
					inputPaths.add(strBuilder.toString());
				}
			}
		}
		return inputPaths;
	}

	public static String getShift(String time) {
		String shift = LUNCH_SHIFT;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime localTime = LocalTime.parse(time, formatter);
		LocalTime frstShiftLocalTime = LocalTime.of(15, 0, 0);
		int timeComparison = localTime.compareTo(frstShiftLocalTime);
		if (timeComparison == 1) {
			shift = DINNER_SHIFT;
		}
		return shift;
	}

	private static void printToPdfUsingJasper(SalesReport salesReport, String date, String output)
			throws FileNotFoundException, JRException {
		List<SalesReport> beanCollection = new ArrayList<SalesReport>();
		beanCollection.add(salesReport);
		String destinationFile = output + "/" + date;
		new File(destinationFile).mkdirs();
		// JasperPrint demographicsJasper = JasperFillManager.fillReport(
		// new FileInputStream(new
		// File("resources/SALES_REPORT_BY_ITEM.jasper")), null,
		// new JRBeanCollectionDataSource(beanCollection));
		// JasperExportManager.exportReportToPdfFile(demographicsJasper,
		// destinationFile+"/SalesReportByItem.pdf");
		JasperPrint demographicsJasper2 = JasperFillManager.fillReport(
				new FileInputStream(new File("resources/DAILY_BEST_SELLERS_PER_SHIFT.jasper")), null,
				new JRBeanCollectionDataSource(beanCollection));
		JasperExportManager.exportReportToPdfFile(demographicsJasper2,
				destinationFile + "/Best Sellers per shift (Day) " + date.replaceAll("/", "-") + ".pdf");
	}

	public static void addTable(Document document, PdfWriter writer,
			Map<String, Map<String, Integer>> groupNameToItemWithQty) {
		// a table with three columns
		// table.setSkipFirstHeader(true);
		// we add the four remaining cells with addCell()

		List<PdfPTable> tables = new ArrayList<PdfPTable>();
		Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
		groupNameToItemWithQty.forEach((groupName, itemToQtyMap) -> {
			PdfPTable table = new PdfPTable(2);
			try {
				table.setWidths(new float[] { 3, 1 });
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// the cell object
			PdfPCell titleCell = new PdfPCell();
			PdfPCell cell = new PdfPCell();
			PdfPCell quantity = new PdfPCell();

			titleCell.setColspan(2);
			titleCell.setPhrase(new Phrase(groupName, tableFont));
			titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			titleCell.setBackgroundColor(WebColors.getRGBColor("#99ddff"));
			titleCell.setPadding(2);
			table.addCell(titleCell);

			cell.setPadding(2);

			quantity.setPadding(2);
			quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
			// Font font = FontFactory.getFont(FontFactory.COURIER, 11,
			// Font.BOLD, BaseColor.BLACK);
			BaseColor myColor = WebColors.getRGBColor("#b3ffff");
			cell = new PdfPCell();
			cell.setPhrase(new Phrase("Group Name", tableFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(myColor);
			// table.addCell(cell);
			cell.setPhrase(new Phrase("Item Name", tableFont));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Total Quantity", tableFont));
			table.addCell(cell);
			// table.setHeaderRows(1);
			// StringUtils.join(itemToQtyMap.keySet(),"\n");
			itemToQtyMap.forEach((itm, qty) -> {
				table.addCell(new Phrase(itm, tableFont));
				quantity.setPhrase(new Phrase(String.valueOf(qty), tableFont));
				table.addCell(quantity);
			});

			Font font = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
			table.addCell(new Phrase("TOTAL", font));
			;
			// table.addCell("");
			quantity.setPhrase(
					new Phrase(String.valueOf(itemToQtyMap.values().stream().mapToInt(Number::intValue).sum()), font));
			table.addCell(quantity);

			//			 try {
			//				document.add(table);
			//				document.add(new Paragraph(" "));
			//			} catch (DocumentException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
			tables.add(table);

		});
		try {
			fillToColumn(document, writer, tables);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void fillToColumn(Document document, PdfWriter writer , List<PdfPTable> tables)
			throws DocumentException, IOException {
		ColumnText ct = new ColumnText(writer.getDirectContent());
		for (PdfPTable pdfPTable : tables) {
			System.out.println("dadada");
			ct.addElement(pdfPTable);
			//			ct.addText(new Phrase("  "));
			int c = 0;
			int status = ColumnText.START_COLUMN;
			while (ColumnText.hasMoreText(status)) {
				if (c == 0) {
					ct.setSimpleColumn(36, 36, 290, 806);
				} else {
					ct.setSimpleColumn(305, 36, 559, 806);
				}
				status = ct.go();
				if (++c == 2) {
					document.newPage();
					c = 0;
				}
			}
		}

	}

	private static void initializeConfig() {
		try {
			AppConfig.setContext(new FileInputStream(new File("config/config.properties")));
		} catch (FileNotFoundException e) {
			System.out.println("ConfigFile Not Found");
			e.printStackTrace();
			System.exit(0);
		}

	}

	// public static Map<String,Map<String,Integer>> groupNameToItemWithQty =
	// new HashMap<>();;

	public static void computeGroupNamesQuantity(Invoice invoice,
			Map<String,Map<String, Map<String, Integer>>> shiftToGroupNameToItemWithQty) {
		String shift = getShift(invoice.getInvoiceDate().split(" ")[1]);
		Map<String, Map<String,Integer>> groupNameToItemWithQty = shiftToGroupNameToItemWithQty.get(shift);
		invoice.getSeats().getSeats().forEach(seat -> {
			seat.getOrdersObj().getOrderObj().forEach(order -> {
				String groupItem = order.getItemGroup();
				String itemName = order.getItemnames();
				int quantity = order.getItemQuantity();
				Map<String, Integer> itemToQuantityToBeInserted = new HashMap<String, Integer>();
				itemToQuantityToBeInserted.put(itemName, quantity);
				Map<String, Integer> itemToQuantityOfGroupName = groupNameToItemWithQty.get(groupItem);
				if (itemToQuantityOfGroupName == null) {
					groupNameToItemWithQty.put(groupItem, itemToQuantityToBeInserted);
				} else {
					Map<String, Integer> mx = Stream.of(itemToQuantityOfGroupName, itemToQuantityToBeInserted)
							.map(Map::entrySet).flatMap(Collection::stream)
							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
					groupNameToItemWithQty.put(groupItem, mx);
				}
			});
		});
	}

	public static void computeGroupNamesPrice(Invoice invoice,
			Map<String, Map<String, Double>> groupNameToItemWithPrice) {
		invoice.getSeats().getSeats().forEach(seat -> {
			seat.getOrdersObj().getOrderObj().forEach(order -> {
				String groupItem = order.getItemGroup();
				String itemName = order.getItemnames();
				double price = order.getItemTotalPrice();
				Map<String, Double> itemToPriceToBeInserted = new HashMap<String, Double>();
				itemToPriceToBeInserted.put(itemName, price);
				Map<String, Double> itemToPriceOfGroupName = groupNameToItemWithPrice.get(groupItem);
				if (itemToPriceOfGroupName == null) {
					groupNameToItemWithPrice.put(groupItem, itemToPriceToBeInserted);
				} else {
					Map<String, Double> mx = Stream.of(itemToPriceOfGroupName, itemToPriceToBeInserted)
							.map(Map::entrySet).flatMap(Collection::stream)
							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::sum));
					groupNameToItemWithPrice.put(groupItem, mx);
				}
			});
		});
	}

	private static List<String> getRange(String str, int noOfDecimal) {
		List<String> rangeList = new ArrayList<String>();
		String[] splitStr = str.split(",");
		System.out.println(str);
		for (String string : splitStr) {
			if (string.contains("-")) {
				String[] rangeSplit = string.split("-");
				Integer start = Integer.parseInt(rangeSplit[0].trim());
				Integer end = Integer.parseInt(rangeSplit[1].trim());
				for (int i = start; i <= end; i++) {
					rangeList.add(String.format("%0" + String.valueOf(noOfDecimal) + "d", i));
				}
			} else {
				rangeList.add(string.trim());
			}
		}
		return rangeList;
	}
}

