# -*- coding: utf-8 -*-
"""
Created on Wed Oct 23 11:20:24 2019

@author: lalit
"""

import os, copy, random, math
from sklearn.metrics import confusion_matrix, classification_report
path = os.getcwd()				#takes the current working directory as path 
path = path + "/20_newsgroups/" 	#adds the data folder to path in next line

#-----------------------------------------------------------------------------------------------------

def bagOfWords(training_split, file_name):
    folder_list = os.listdir(path)  #Create a list of all the folders
    total_dic = {}
    dictionary = {}
    print("---------- CREATING BAG OF WORDS ----------")
    for fo in folder_list:
        dic = {}
        folder_ = path + fo
        print("Reading words from " + fo)
        files = os.listdir(folder_)     #list of all files in the folder
        it = 0
        for fi in files:
            it = it + 1
            if it > training_split:     #stop when training split completes
                break
            add = folder_ + '/'+fi
            myfile = open(add,'r')      #open the file in read only
            data = clean_text(myfile.read())    #clean the data
            fields = data.split(' ')    #collect all the words from clean data
            for field in fields:
                if field == ' ' or field == '':
                    continue
                value = dic.get(field, 0)
                value_t = total_dic.get(field, 0)
                if value == 0:
                    dic[field] = 1
                else:
                    dic[field] = value + 1
                if value_t == 0:
                    total_dic[field] = 1
                else:
                    total_dic[field] = value_t + 1
            files.remove(fi)
        file_name[fo] = files
        dictionary[fo] = dic
    print("\nThe bag of words  has ", len(total_dic), "words" )
    return folder_list, dictionary

#-----------------------------------------------------------------------------------------------------

def clean_text(data):
    """
    input -> text from the files
    function -> converting everything to lower case, remove special characters and new lines
    output -> text containing only words from the file
    
    """
    data = data.replace('\n', ' ')  #remove new lines
    replace_l = ["'",'!','/','\\','=',',',':', '<','>','?','.','"',')','(','|','-','#','*','+', '_']      #list of characters to remove
    data = data.lower()     #Convert all the words to lower case
    for i in replace_l:
        data = data.replace(i,' ')  #replace words with blank character
    return data     #return clean data

#-----------------------------------------------------------------------------------------------------

def nbClassifier(folder_l):
	print("\nCalculating Accuracy for Naive Bayes on the Newsgroup data")
	data = 1
	iteration = 0
	success = 0
	y_pred = []
	y_true = []
	while (data):
		data = get_file()
		iteration = iteration + 1
		if data =='NULL':
			break
		data = clean_text(data)
		fields = data.split(' ')
		if '' in fields: fields.remove('')
		if ' ' in fields: fields.remove(' ')
		probabilities = []
		for c in folder_list:
			probabilities.append(get_probability(fields,dictionary[c]))
#            print("Predicted = " + folder_list[probabilities.index(max(probabilities))] + " Actual = " + group)
			y_pred.append(folder_list[probabilities.index(max(probabilities))])
			y_true.append(group)
		if group == folder_list[probabilities.index(max(probabilities))]:
			success = success + 1
	print('\nAccuracy = %.1f'% (float(success)/float(iteration - 1)*100))
	print("\nConfusion Martix : \n")
	print(confusion_matrix(y_true, y_pred))
	#print("\nClassification report: \n")
	#print(classification_report(y_true, y_pred))
    
#-----------------------------------------------------------------------------------------------------
def get_file():
    """
    function -> return data from the file according to training or testing grouo
    output -> data from file
    
    """
    global group
    while (len(folder_l)):
        r_fo = random.randint(0,len(folder_l)-1)
        folder_n = folder_l[r_fo]
        if len(file_name[folder_n])== 0:
            folder_l.remove(folder_n)
        else:
            r_fi = random.randint(0, len(file_name[folder_n])-1)
            fil = file_name[folder_n][r_fi]
            file_name[folder_n].remove(fil)
            group = folder_n
            data = open(path + folder_n + '/'+ fil,'r')
            return data.read()
    group = 'NULL'
    return 'NULL'

#-----------------------------------------------------------------------------------------------------
def get_probability(fields, dic):
    """
    function -> calculate probability for the instance with respect to all classes
    output -> probability
    
    """
    sum_ = sum(dic.values())
    p = 0.0
    for f in fields:
        value = dic.get(f, 0.0) + 0.0001
        p = p + math.log(float(value)/float(sum_))
    return p

#-----------------------------------------------------------------------------------------------------

if __name__ == "__main__":
	training_split = 500
	file_name ={}
	folder_list, dictionary = bagOfWords(training_split, file_name)
	folder_l = copy.deepcopy(folder_list)
	nbClassifier(folder_l)