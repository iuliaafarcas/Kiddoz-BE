package com.kiddoz.recommendation.service;

import jakarta.annotation.PostConstruct;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AIService {
    // load the model
    Logger logger = LoggerFactory.getLogger(AIService.class);
    private String modelPath;
    private MultiLayerNetwork model;
    private INDArray features;

    @Value("${model.path}")
    private String path;

    @PostConstruct
    public void init() {
        try {
            modelPath = path;
            loadModel();
            logger.info("Model loaded successfully.");
        } catch (Exception ex) {
            logger.error("Failed to load model in AIService PostConstruct", ex);
            // Do not rethrow — allow Spring to continue
        }
    }



    public void loadModel() throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        ClassPathResource modelResource = new ClassPathResource(modelPath);
        this.model = KerasModelImport.importKerasSequentialModelAndWeights(modelResource.getInputStream());
    }

    public void createSample(List<Integer> questionResults) {
        int numRows = 1; // Number of rows in the matrix
        int numCols = 15; // Number of columns in the matrix

        if (questionResults.size() != numCols) {
            throw new IllegalArgumentException("Input size does not match the required matrix dimensions");
        }

        // Create the features matrix
        INDArray features = Nd4j.ones(numRows, numCols);
        for (int i = 0; i < numCols; i++) {
            features.putScalar(new int[]{0, i}, questionResults.get(i));
        }

        this.features = features;
    }


    public INDArray getPrediction() {
        return model.output(features);
    }
}
