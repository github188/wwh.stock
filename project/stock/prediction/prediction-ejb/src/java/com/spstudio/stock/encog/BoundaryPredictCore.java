/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.encog;

import com.spstudio.stock.entity.DailyPrice;
import com.spstudio.stock.entity.MarketIndex;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import org.encog.Encog;
import org.encog.ml.MLMethod;
import org.encog.ml.MLRegression;
import org.encog.ml.MLResettable;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.temporal.TemporalDataDescription;
import org.encog.ml.data.temporal.TemporalMLDataSet;
import org.encog.ml.data.temporal.TemporalPoint;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.factory.MLTrainFactory;
import org.encog.ml.train.MLTrain;
import org.encog.ml.train.strategy.RequiredImprovementStrategy;
import org.encog.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.encog.util.simple.EncogUtility;

/**
 *
 * @author sp
 */
/*
 Warning: this class not support multi-thread processing, since we use encog framework to imple prediction, which is singleton by design
 */
public class BoundaryPredictCore {

    private int INPUT_WINDOW_SIZE = 2;
    private final int PREDICT_WINDOW_SIZE = 1;
    private final int NODE_NUMBER = 100;
    private final double DESIRED_ERROR_LEVEL = 0.00001;
    private static NormalizedField stockMaxPriceNormalizedField;
    private static NormalizedField stockMinPriceNormalizedField;
    private static NormalizedField stockOpenPriceNormalizedField;
    private static NormalizedField stockClosePriceNormalizedField;
    private static NormalizedField stockTradeNumNormalizedField;
    private static NormalizedField stockTradeAmountNormalizedField;
    private static NormalizedField stockMarketPointNormalizedField; // Market Index

    private enum PredictionType {
        MAX_STOCK_PRICE, MIN_STOCK_PRICE
    };

    private static final Logger log = Logger.getLogger(BoundaryPredictCore.class.getName());

    public DailyPrice predict(List<DailyPrice> dpList, List<MarketIndex> miList) {

        INPUT_WINDOW_SIZE = dpList.size() - 2;

        // calculate normalized field
        calculateNormalizedField(dpList, miList);

        // initial training data set
        TemporalMLDataSet trainingData = createTraining(dpList, miList, PredictionType.MAX_STOCK_PRICE);
        MLRegression model = trainModel(trainingData, MLMethodFactory.TYPE_FEEDFORWARD,
                "?:B->SIGMOID->" + NODE_NUMBER + ":B->SIGMOID->?", MLTrainFactory.TYPE_RPROP, "");
        double predictedMaxPrice = doPredict(model, dpList, miList, PredictionType.MAX_STOCK_PRICE);

        trainingData = createTraining(dpList, miList, PredictionType.MIN_STOCK_PRICE);
        model = trainModel(trainingData, MLMethodFactory.TYPE_FEEDFORWARD,
                "?:B->SIGMOID->" + NODE_NUMBER + ":B->SIGMOID->?", MLTrainFactory.TYPE_RPROP, "");
        double predictedMinPrice = doPredict(model, dpList, miList, PredictionType.MIN_STOCK_PRICE);

        DailyPrice dp = new DailyPrice();
        dp.setPredictMaxPrice(BigDecimal.valueOf(predictedMaxPrice));
        dp.setPredictMinPrice(BigDecimal.valueOf(predictedMinPrice));

        Encog.getInstance().shutdown();

        return dp;
    }

    private void calculateNormalizedField(List<DailyPrice> dpList, List<MarketIndex> miList) {
        
        double maxPriceBoundaryHigh = Double.MIN_VALUE, maxPriceBoundaryLow = Double.MAX_VALUE;
        double minPriceBoundaryHigh = Double.MIN_VALUE, minPriceBoundaryLow = Double.MAX_VALUE;
        double openPriceBoundaryHigh = Double.MIN_VALUE, openPriceBoundaryLow = Double.MAX_VALUE;
        double closePriceBoundaryHigh = Double.MIN_VALUE, closePriceBoundaryLow = Double.MAX_VALUE;
        double tradeNumBoundaryHigh = Double.MIN_VALUE, tradeNumBoundaryLow = Double.MAX_VALUE;
        double tradeAmountBoundaryHigh = Double.MIN_VALUE, tradeAmountBoundaryLow = Double.MAX_VALUE;
        double marketPointBoundaryHigh = Double.MIN_VALUE, marketPointBoundaryLow = Double.MAX_VALUE;

        double max, min;
        for (DailyPrice dp : dpList) {
            // maxPrice
            max = dp.getMaxPrice().doubleValue();
            min = dp.getMaxPrice().doubleValue();
            if (max >= maxPriceBoundaryHigh) {
                maxPriceBoundaryHigh = max;
            } else {
                maxPriceBoundaryLow = min;
            }

            // minPrice
            max = dp.getMinPrice().doubleValue();
            min = dp.getMinPrice().doubleValue();
            if (max > minPriceBoundaryHigh) {
                minPriceBoundaryHigh = max;
            } else {
                minPriceBoundaryLow = min;
            }

            // open price of stock
            max = dp.getOpenPrice().doubleValue();
            min = dp.getOpenPrice().doubleValue();
            if (max > openPriceBoundaryHigh) {
                openPriceBoundaryHigh = max;
            } else {
                openPriceBoundaryLow = min;
            }

            // close price of stock
            max = dp.getClosePrice().doubleValue();
            min = dp.getClosePrice().doubleValue();
            if (max > closePriceBoundaryHigh) {
                closePriceBoundaryHigh = max;
            } else {
                closePriceBoundaryLow = min;
            }

            // trade number
            max = dp.getTradeNum().doubleValue();
            min = dp.getTradeNum().doubleValue();
            if (max > tradeNumBoundaryHigh) {
                tradeNumBoundaryHigh = max;
            } else {
                tradeNumBoundaryLow = min;
            }

            // trade amount
            max = dp.getTradeAmount().doubleValue();
            min = dp.getTradeAmount().doubleValue();
            if (max > tradeAmountBoundaryHigh) {
                tradeAmountBoundaryHigh = max;
            } else {
                tradeAmountBoundaryLow = min;
            }
        }

        for (MarketIndex mi : miList) {
            // market point
            max = mi.getPoint();
            min = mi.getPoint();
            if (max > marketPointBoundaryHigh) {
                marketPointBoundaryHigh = max;
            } else {
                marketPointBoundaryLow = min;
            }
        }
        stockMaxPriceNormalizedField = new NormalizedField(NormalizationAction.Normalize, "stockMaxPrice", maxPriceBoundaryHigh * 1.1, maxPriceBoundaryLow * 0.9, 1, 0);
        stockMinPriceNormalizedField = new NormalizedField(NormalizationAction.Normalize, "stockMinPrice", minPriceBoundaryHigh * 1.1, minPriceBoundaryLow * 0.9, 1, 0);
        stockOpenPriceNormalizedField = new NormalizedField(NormalizationAction.Normalize, "stockOpenPrice", openPriceBoundaryHigh * 1.1, openPriceBoundaryLow * 0.9, 1, 0);
        stockClosePriceNormalizedField = new NormalizedField(NormalizationAction.Normalize, "stockClosePrice", closePriceBoundaryHigh * 1.1, closePriceBoundaryLow * 0.9, 1, 0);
        stockTradeNumNormalizedField = new NormalizedField(NormalizationAction.Normalize, "stockTradeNum", tradeNumBoundaryHigh * 1.5, tradeNumBoundaryLow * 0.5, 1, 0);
        stockTradeAmountNormalizedField = new NormalizedField(NormalizationAction.Normalize, "stockTradeAmount", tradeAmountBoundaryHigh * 1.5, tradeAmountBoundaryLow * 0.5, 1, 0);
        stockMarketPointNormalizedField = new NormalizedField(NormalizationAction.Normalize, "stockMarketPoint", marketPointBoundaryHigh * 1.1, marketPointBoundaryLow * 0.9, 1, 0);
    }

    private TemporalMLDataSet createTraining(List<DailyPrice> dpList, List<MarketIndex> miList, PredictionType pt) {
        TemporalMLDataSet trainingData = initDataSet(pt);
        for (int i = 0; i < dpList.size(); i++) {
            TemporalPoint point = new TemporalPoint(trainingData.getDescriptions().size());
            point.setSequence(i + 1);
            switch (pt) {
                case MAX_STOCK_PRICE:
                    point.setData(new double[]{
                        stockMaxPriceNormalizedField.normalize(dpList.get(i).getMaxPrice().doubleValue()),
                        stockOpenPriceNormalizedField.normalize(dpList.get(i).getOpenPrice().doubleValue()),
                        stockClosePriceNormalizedField.normalize(dpList.get(i).getClosePrice().doubleValue()),
                        stockTradeNumNormalizedField.normalize(dpList.get(i).getTradeNum().doubleValue()),
                        stockTradeAmountNormalizedField.normalize(dpList.get(i).getTradeAmount().doubleValue()),
                        stockMarketPointNormalizedField.normalize(miList.get(i).getPoint())
                    });
                    break;
                case MIN_STOCK_PRICE:
                    point.setData(new double[]{
                        stockMinPriceNormalizedField.normalize(dpList.get(i).getMinPrice().doubleValue()),
                        stockOpenPriceNormalizedField.normalize(dpList.get(i).getOpenPrice().doubleValue()),
                        stockClosePriceNormalizedField.normalize(dpList.get(i).getClosePrice().doubleValue()),
                        stockTradeNumNormalizedField.normalize(dpList.get(i).getTradeNum().doubleValue()),
                        stockTradeAmountNormalizedField.normalize(dpList.get(i).getTradeAmount().doubleValue()),
                        stockMarketPointNormalizedField.normalize(miList.get(i).getPoint())
                    });
                    break;
            }
            trainingData.getPoints().add(point);
        }
        trainingData.generate();
        return trainingData;
    }

    private TemporalMLDataSet initDataSet(PredictionType pt) {
        TemporalMLDataSet dataSet = new TemporalMLDataSet(INPUT_WINDOW_SIZE, PREDICT_WINDOW_SIZE);

        switch (pt) {
            case MAX_STOCK_PRICE:
                TemporalDataDescription stockMaxPriceDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, true);
                dataSet.addDescription(stockMaxPriceDesc);
                break;
            case MIN_STOCK_PRICE:
                TemporalDataDescription stockMinPriceDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, true);
                dataSet.addDescription(stockMinPriceDesc);
                break;
        }
        
        TemporalDataDescription openPriceDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, false);
        dataSet.addDescription(openPriceDesc);
        TemporalDataDescription closePriceDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, false);
        dataSet.addDescription(closePriceDesc);
        TemporalDataDescription tradeNumDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, false);
        dataSet.addDescription(tradeNumDesc);
        TemporalDataDescription tradeAmountDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, false);
        dataSet.addDescription(tradeAmountDesc);
        TemporalDataDescription marketPoint = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, false);
        dataSet.addDescription(marketPoint);

        return dataSet;
    }

    private MLRegression trainModel(
            MLDataSet trainingData,
            String methodName,
            String methodArchitecture,
            String trainerName,
            String trainerArgs) {

        MLMethodFactory methodFactory = new MLMethodFactory();
        MLMethod method = methodFactory.create(methodName, methodArchitecture, trainingData.getInputSize(), trainingData.getIdealSize());

        MLTrainFactory trainFactory = new MLTrainFactory();
        MLTrain train = trainFactory.create(method, trainingData, trainerName, trainerArgs);
        if (method instanceof MLResettable && !(train instanceof ManhattanPropagation)) {
            train.addStrategy(new RequiredImprovementStrategy(500));
        }
        EncogUtility.trainToError(train, DESIRED_ERROR_LEVEL);
        return (MLRegression) train.getMethod();
    }

    private double doPredict(MLRegression model, List<DailyPrice> dpList, List<MarketIndex> miList, PredictionType pt) {
        TemporalMLDataSet trainingData = initDataSet(pt);

        for (int i = 0; i < dpList.size(); i++) {

            TemporalPoint point = new TemporalPoint(trainingData.getDescriptions().size());
            point.setSequence(i + 1);

            switch (pt) {
                case MAX_STOCK_PRICE:
                    point.setData(new double[]{
                        stockMaxPriceNormalizedField.normalize(dpList.get(i).getMaxPrice().doubleValue()),
                        stockOpenPriceNormalizedField.normalize(dpList.get(i).getOpenPrice().doubleValue()),
                        stockClosePriceNormalizedField.normalize(dpList.get(i).getClosePrice().doubleValue()),
                        stockTradeNumNormalizedField.normalize(dpList.get(i).getTradeNum().doubleValue()),
                        stockTradeAmountNormalizedField.normalize(dpList.get(i).getTradeAmount().doubleValue()),
                        stockMarketPointNormalizedField.normalize(miList.get(i).getPoint())
                    });
                    break;
                case MIN_STOCK_PRICE:
                    point.setData(new double[]{
                        stockMinPriceNormalizedField.normalize(dpList.get(i).getMinPrice().doubleValue()),
                        stockOpenPriceNormalizedField.normalize(dpList.get(i).getOpenPrice().doubleValue()),
                        stockClosePriceNormalizedField.normalize(dpList.get(i).getClosePrice().doubleValue()),
                        stockTradeNumNormalizedField.normalize(dpList.get(i).getTradeNum().doubleValue()),
                        stockTradeAmountNormalizedField.normalize(dpList.get(i).getTradeAmount().doubleValue()),
                        stockMarketPointNormalizedField.normalize(miList.get(i).getPoint())
                    });
                    break;
            }

            trainingData.getPoints().add(point);
        }

        MLData modelInput = trainingData.generateInputNeuralData(1);
        MLData modelOutput = model.compute(modelInput);

        double returnValue = 0;
        switch (pt) {
            case MAX_STOCK_PRICE:
                returnValue = stockMaxPriceNormalizedField.deNormalize(modelOutput.getData(0));
                break;
            case MIN_STOCK_PRICE:
                returnValue = stockMinPriceNormalizedField.deNormalize(modelOutput.getData(0));
                break;
        }

        // generate the time-boxed data
        trainingData.generate();
        return returnValue;
    }
}
