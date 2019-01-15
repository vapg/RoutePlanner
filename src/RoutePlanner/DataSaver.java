package RoutePlanner;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class DataSaver {


    static String getXLSFileName(String description) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm");
        Date date = new Date();
        String fileName = "Results_Simulation_"+dateFormat.format(date);
        return fileName;

    }

    static void writeXLS(Logging logging, HashMap<String,String> configuracion, String description) {
        String fileName = "Results_Simulation_"+ MainExperiment.SIM_ACTUAL +".xlsx";


        XSSFWorkbook workbook = new XSSFWorkbook();
        String periodos = configuracion.get("Periodo");
        String numeroAgentes = configuracion.get("NumeroAgentes");

        Sheet configuracionSheet = workbook.createSheet("Configuration");
        Sheet resultSheet = workbook.createSheet("Results");

        Row row1 = configuracionSheet.createRow(0);
        row1.createCell(0).setCellValue("Periodo");
        row1.createCell(1).setCellValue(periodos);

        Row row2 = configuracionSheet.createRow(1);
        row2.createCell(0).setCellValue("Numero de Agentes");
        row2.createCell(1).setCellValue(numeroAgentes);

        Row rowResultHeader = resultSheet.createRow(0);
        rowResultHeader.createCell(0).setCellValue("ID TURISTA");
        rowResultHeader.createCell(1).setCellValue("Preferencia 1");
        rowResultHeader.createCell(2).setCellValue("Preferencia 2");
        rowResultHeader.createCell(3).setCellValue("Preferencia 3");
        rowResultHeader.createCell(4).setCellValue("Periodo");
        rowResultHeader.createCell(5).setCellValue("Presupuesto");
        rowResultHeader.createCell(6).setCellValue("Satisfaccion");
        rowResultHeader.createCell(7).setCellValue("Province");
        rowResultHeader.createCell(8).setCellValue("Activo");

        //for info of experiments
        for (int rowNum = 0; rowNum < logging.size(); ++rowNum) {
            int touristaID = logging.obtenerTourista(rowNum);
            int dia = logging.obtenerDia(rowNum);

            double presupuesto = logging.obtenerPresupuesto(rowNum);
            double satisfaccion = logging.obtenerSatisfaccion(rowNum);
            int provincia = logging.obtenerProvince(rowNum);
            boolean[] preferencias = logging.obtenerAtractivos(rowNum);
            boolean activo = logging.obtenerActivo(rowNum);
            double costo = logging.obtenerCosto(rowNum);

            Row rowResult = resultSheet.createRow(rowNum + 1);
            rowResult.createCell(0).setCellValue(touristaID);
            rowResult.createCell(1).setCellValue(preferencias[0]);
            rowResult.createCell(2).setCellValue(preferencias[1]);
            rowResult.createCell(3).setCellValue(preferencias[2]);

            rowResult.createCell(4).setCellValue(dia);
            rowResult.createCell(5).setCellValue(presupuesto);
            rowResult.createCell(6).setCellValue(satisfaccion);
            rowResult.createCell(7).setCellValue(ProvinceFactory.getNombre(provincia));
            rowResult.createCell(8).setCellValue(activo);
            rowResult.createCell(9).setCellValue(costo);
        }

        try {
            FileOutputStream out = new FileOutputStream(fileName);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

