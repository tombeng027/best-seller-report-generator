package com.svi.best_seller_report_generator;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

public class BarChartCustomizer implements JRChartCustomizer {

	public void customize(JFreeChart chart, JRChart jasperChart){
		CategoryPlot categoryPlot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
		renderer.setMaximumBarWidth(.10);  //Set maximum width of barchart to 30 percent
//		renderer.setItemMargin(0.05f);
//		categoryPlot.getDomainAxis().setCategoryMargin(-0.03f);
	}
}
