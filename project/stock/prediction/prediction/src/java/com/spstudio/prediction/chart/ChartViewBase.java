/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.prediction.chart;

import com.spstudio.stock.entity.DailyPrice;
import java.text.SimpleDateFormat;
import java.util.List;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author sp
 */
public class ChartViewBase {

    protected List<DailyPrice> list;
    protected double highBoundary = Double.MIN_VALUE;
    protected double lowBoundary = Double.MAX_VALUE;
    protected SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
    private LineChartModel lineModel;

    public LineChartModel getLineModel() {
        if (lineModel == null) {
            createLineModels();
        }
        return lineModel;
    }

    protected void createLineModels() {

        lineModel = initCategoryModel();
        lineModel.setExtender("skinChart");
        lineModel.setLegendPosition("ne");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("股票交易日期"));
        lineModel.setAnimate(true);

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("股票价格");
        yAxis.setMin(lowBoundary);
        yAxis.setMax(highBoundary);
    }

    private LineChartModel initCategoryModel() {

        LineChartModel model = new LineChartModel();

        ChartSeries actualValue = new ChartSeries();
        actualValue.setLabel("实际价格");

        ChartSeries predictValue = new ChartSeries();
        predictValue.setLabel("预测价格");

        fillSeriesData(actualValue, predictValue);

        model.addSeries(actualValue);
        model.addSeries(predictValue);

        return model;
    }

    protected void fillSeriesData(ChartSeries actualSeries, ChartSeries predictSeries) {

    }
}
