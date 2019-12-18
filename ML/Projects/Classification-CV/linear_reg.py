# -*- coding: utf-8 -*-
"""
Created on Mon Sep  9 03:20:31 2019

@author: lalit
"""

from sklearn.datasets import load_iris
import numpy as np
#------------------------------------------------------------------------------------------------
def lsquare_regression(X_train, Y_train, X_validation, Y_validation):
    
    """ Computing beta_hat = (X.T X)^-1 (X.T Y) 
        Where X : Training Feature Data Set
              Y : Training Label (Target) Data Set
    """
    op1 = np.dot(X_train.T, X_train)			# X_train.T : transpose of matrix
    op1_inverse = np.linalg.inv(op1)
    op2 = X_train.T@Y_train 					# @: matrix multiplication
    beta_hat = op1_inverse@op2
    
    """ Getting the Predictions for the Validation Set using beta estimates"""
    pred_val = np.matmul(X_validation, beta_hat)
    pred_val = abs(np.rint(pred_val)).astype(int)
    print("\n DataSet Label values -", Y_validation)
    print("Predicted Label values - ", pred_val)
    """ Calculating Accuracy of the trained Model """
    count = 0
    for i in range(len(pred_val)):
        if(pred_val[i] == Y_validation[i]):
            count = count+1
    accuracy = (count/len(pred_val))*100
    print('Accuracy = ', accuracy)
    return accuracy
#-----------------------------------------------------------------------------------------------

def cross_val(k, iris_X, iris_Y):
    np.random.seed(56)
	#Random Permutation for shuffling the data set
    indices = np.random.permutation(len(iris_X))    
    n = len(iris_X)
    len_k = n // k
    accuracy_set = []

    """
    Splitting the Data Into Training and Validation data sets in iteration and perform k-fold CV    
    """
    for i in range(0,k):
        start = i * len_k
        end = ((i + 1) * len_k)
        iris_X_valid  = iris_X[indices[start:end]]
        iris_Y_valid  = iris_Y[indices[start:end]]
        iris_X_train = iris_X[indices[[x for x in indices if x not in indices[start:end]]]]
        iris_Y_train = iris_Y[indices[[x for x in indices if x not in indices[start:end]]]]
        accuracy = lsquare_regression(iris_X_train, iris_Y_train, iris_X_valid, iris_Y_valid)
        accuracy_set.append(accuracy)
    avg_accuracy = sum(accuracy_set)/len(accuracy_set)
    print("\n The Average Accuracy for k=", k, " fold Cross Validation :", avg_accuracy)
#------------------------------------------------------------------------------------------------

iris = load_iris()
k_fold = 3
cross_val(k_fold, iris.data, iris.target)