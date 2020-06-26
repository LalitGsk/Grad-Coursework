# News Classification

### Goal: 
Bayesian learning for classifying news text articles:  

### Data:  
http://www.cs.cmu.edu/afs/cs/project/theo-11/www/naivebayes.html (Newsgroup Data)  
  
### Requirement:
1.	Use half data as training data, and the other half as testing data.	
2.	Implement the Naive Bayes classifier	
3.	Summarize your implementations and results.

#### Size of the Dataset:  
A dataset containing 20,000 newsgroup messages drawn from the 20 newsgroups. The dataset contains 1000 documents from each of the 20 newsgroups.

#### Data Preview:

**List of Folders:**

['alt.atheism',  
 'comp.graphics',  
 'comp.os.ms-windows.misc',  
 'comp.sys.ibm.pc.hardware',  
 'comp.sys.mac.hardware',  
 'comp.windows.x',  
 'misc.forsale',  
 'rec.autos',  
 'rec.motorcycles',  
 'rec.sport.baseball',  
 'rec.sport.hockey',  
 'sci.crypt',  
 'sci.electronics',  
 'sci.med',  
 'sci.space',  
 'soc.religion.christian',  
 'talk.politics.guns',  
 'talk.politics.mideast',  
 'talk.politics.misc',  
 'talk.religion.misc']  

## Method:  
  
- The machine learning model in the code is trained using the naïve bayes classifier formula to perform the text classification on the data.
  
The piepline is implemented in three stages:   
### 1.	Preprocessing   
- In the preprocessing stage, the data is cleaned by the clean_text user defined function in the program. There are some characters that are necessary to either be removed or replaced with space character (so that the words can later be split as a list of words).
- The following list replace_list is considered.
- All the characters inside the replaced_list from the data, are replaced with space.
-	Replace_list = ["'",'!','/','\\','=',',',':', '<','>','?','.','"',')','(','|','-','#','*','+', '_']
- After replacing the special characters, all the uppercase letters are converted to lowercase ones. 

### 2.	Training
- In the training stage, the first 500 files of each class/newsgroup are selected and the file data is split with character space by preprocessing. 
- Here, the number of occurrences of each word in each class file is counted
- A dictionary is created to maintain the count of each word. 
- After the training of the data, there are around 170,000 different words in all classes (for 10,000 files).

### 3.	Testing
- In the testing stage, after the training stage the files are selected randomly from the remaining files, function get_file returns these files as the testing dataset.   
- And the similar approach is implemented with the testing data. The files are cleaned with clean_text function, and post preprocessing the probability of each word in these files are determined for all the classes/newsgroups
  
  For each newsgroup n:  
   {  Pn = 0     
    For each word in preprocessed_file:  
     {  
     Pn = Pn + log(P(word| n))   
     } }
                
- The function get_probability computes the probability for a given class/newsgroup. The predicted class would be the one has the highest probability Pn.  

### Results and Analysis:

- Concluding from the results summary table, the Naïve Bayes Classifier model best performs with using at least half the dataset (500 files each class) as training data in terms of accuracy. 
- Therefore the reason I think the value is given as 500 in the problem statement because in case of small datasets, each dataset must contain some good amount of data, having less data in the dataset means that the data wont be trained well enough and the that does not serve the model accuracy well.

<p align="center">
  
| Training Data size  | Accuracy |
| ------------- | ------------- |
| 1  | 20.6%  |
| 10  | 42.6%  |
| 50  | 63.3% |
| 150  | 75%  |
| 250  | 80.2%  |
| 350  | 82.7% |
| 500 | 84% |
| 750  | 84%  |
| 900  | 84%  |

</p>

### Classification Report for Training Size= 500: 

| |	precision |	recall	| f1-score |	support |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| alt.atheism |  0.19 | 0.97 | 0.32 |	10000 |
| comp.graphics	| 0.29    |  	0.75	   |   0.42   | 	10000 |
| comp.os.ms-windows.misc	 | 0.95     | 	0.25 |	0.39	| 10000 |
| comp.sys.ibm.pc.hardware    |  	0.37    |  	0.70 |	0.49 |	10000 |
| comp.sys.mac.hardware     |  	0.41      |	0.70	| 0.52 |	10000 |
| comp.windows.x      | 	0.84  |    	0.63 |	0.72	| 10000 |
| misc.forsale     | 	0.38      |	0.63 |	0.48 |	10000 |
| rec.autos   |  	0.42     | 	0.60	| 0.50	| 10000 |
| rec.motorcycles      | 	0.68   |   	0.57 |	0.62	| 10000 |
| rec.sport.baseball   |    	0.81  |    	0.52 |	0.63 |	10000 |
| rec.sport.hockey    |   	0.97      |	0.47 |	0.64	| 10000 |
| sci.crypt |	0.70  |    	0.43 |	0.53	| 10000 |
| sci.electronics  | 	0.74    |  	0.35	| 0.48 |	10000 |
| sci.med  | 	0.79  | 	0.32	| 0.45	| 10000 |
| sci.space |  	0.87     | 	0.28 |	0.43 |	10000 |
| soc.religion.christian  |  	0.85    |  	0.25 |	0.38 |	9960 |
| talk.politics.guns |   	0.58   |   	0.19	| 0.29 |	10000 |
| talk.politics.mideast | 	0.94    |  	0.14 |	0.24 |	10000 |
| talk.politics.misc   |	0.74     | 	0.08 |	0.14 |	10000 |
| talk.religion.misc  |	0.66  | 	0.03	| 0.06 |	10000 |
|  |  |	 | |	 |		
| accuracy	|		|  | 0.44   | 	199960 |
| macro avg   |    	0.66   | 	0.44   |	0.44	| 199960 |
| weighted avg  |	0.66 | 	0.44 | 0.44 |	199960 |

### About Confusion Matrix:
- A confusion matrix is a table that is often used to describe the performance of a classification model (or "classifier") on a set of test data for which the true values are known.
- The predicted classes are represented in the columns of the matrix, whereas the actual classes are in the rows of the matrix. We then have four cases for each newsgroup in the data:  

**1.	True positives (TP):** the cases for which the classifier predicted ‘correct class’ and the actual data was from same class.  
**2.	True negatives (TN):** the cases for which the classifier did not predicted class as ‘correct’ and the actual data was also not from same class.  
**3. False positives (FP):** the cases for which the classifier predicted class as ‘correct class’ but the actual data had a different correct class.  
**4. False negatives (FN):** the cases for which the classifier did not predicted class as ‘correct class’ and the actual data was rather a correct class.    

### Confusion Martix: 

| | | | | | | | | | | | | | | | | | | | |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 9711  |  3  |  0  |  9  | 40  | 13 |  72  |  0  | 35  |  0  |  0  |  9  |  0  |  2   |  0  | 19 |  4 |   0  |  1 |  82|
| 506 | 7486  |  0 | 612 | 277 | 227 | 670 | 11 |  36 |  0  |  0  |  9 | 120  | 28  |  18  |  0  |  0  |  0 |  0  |  0 |
| 501 | 1615 | 2482 | 2854 | 933 | 542 | 968 |   7 |  12 |   0 |   0 |  18 |  56 |   0 |   10 |   0 |   0 |   0 |   2 |   0 |
| 521 | 1150 |  38 | 7012 | 774  | 31 | 374  | 13  |  0  |  0  |  0  |  0  | 80  |  7  |   0  |  0  |  0  |  0  |  0  |  0 |
| 516 | 1189  |  6 | 951 | 7042  |  0 | 280  |  0  |  0  |  0  |  0  |  0  | 16  |  0   |  0  |  0  |  0  |  0  |  0 |   0 |
| 500 | 1989  | 24 | 559 | 311 | 6254 | 346 |  13  |  0  |  0  |  0  |  0  |  0  |  0   |  0  |  0  |  4  |  0  |  0  |  0 |
| 549 | 1109  |  6 | 933 | 783  |  5 | 6340  | 109  | 12  |  0  | 10  |  0 | 144  |  0   |  0  |  0  |  0  |  0  |  0  |  0 |
| 917 | 908   | 7 | 652 | 701   | 6 | 548 | 6046 | 132  | 0  |  0  |  0  | 72 |   7   |  0  |  0  |  4   | 0  |  0  |  0 |
| 1081 | 823  |  2 | 599 | 713  |  9 | 579 | 492 | 5700  |  0  |  0  |  0  |  0  |  0   |  0  |  0  |  0  |  0  |  2  |  0 |
| 1422 | 683  | 11 | 558 | 684  |  9 | 940 | 288 | 195 | 5183 |  13  |  0  |  0 |  14   |  0  |  0  |  0  |  0  |  0  |  0 |
| 1213 | 975  | 12 | 669 | 468  | 22 | 1044 | 305 | 167 | 381 | 4740  |  0  |  0  |  0   |  0  |  0  |  2  |  0  |  2  |  0 |
| 1774 | 1581  |  2 | 567 | 765 | 141 | 328 | 386  | 86  |  8   | 0 | 4326 |  32  |  0  |   0  |  0  |  2  |  0  |  2  |  0 |
| 584 | 1527  | 10 | 1323 | 1233 |  38 | 1064 | 602 |  44  | 11  |  0  |  9 | 3530   | 7  |  18  |  0  |  0  |  0  |  0   | 0 |
| 2213 | 1192  |  1 | 277 | 824  | 22 | 818 | 634 | 429 | 145 |   3 |  58 | 178 | 3191  |   6  |  0  |  4  |  3  |  2  |  0 |
| 1271 | 2296  |  1 | 379 | 672 |  82 | 821 | 753 | 201 |  83  |  2 | 114 | 328 | 137 | 2850  |  0  |  6  |  0  |  4  |  0 |
| 5664 | 447  |  0  | 81  | 97  |  6 | 153 | 584 | 147 | 127  | 28  |  6  | 36  | 95   |  9 | 2477  |  0  |  0  |  2  |  1 |
| 3639 | 168 |  18 | 385 | 138  |  0 | 293 | 1909 | 510  | 60  |  5 | 750  | 42 |  58   | 56  |  5  | 1915  |  0  | 39 |  10 |
| 5688 | 304  |  0 | 149 | 306  |  1 | 370 | 439 | 181 | 126  | 22 | 172  | 54 | 153   | 87 | 162 | 347 | 1382  | 56  |  1 | 
| 4459 | 205 |   0 | 243 | 245  |  0 | 364 | 1176 | 369 | 183  | 35 |  613 |  53 | 272  | 190  | 21 | 660  | 83 | 764  | 65 |
| 7538 |  71  |  0  | 98  | 32  |  2 | 247 | 506 | 126  | 64  | 18 | 131 |  30 |  82   | 18 | 236 | 337  |  1 | 157 | 306 |


References:  
- https://krakensystems.co/blog/2018/text-classification
- http://guidetodatamining.com/assets/guideChapters/DataMining-ch7.pdf
- http://www.cs.cmu.edu/~tom/10701_sp11/slides/GNB_1-25-2011.pdf                
