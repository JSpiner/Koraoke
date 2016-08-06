%matplotlib inline

import librosa
import numpy as np
import matplotlib.pyplot as plt
import tensorflow as tf

def average(arr):
    return float(sum(arr, 0.0) / len(arr));

def getrow(id1, id2):
    filename = 'data/audio_'+str(id1)+'_'+str(id2)+'.wav'
    y, sr = librosa.load(filename)
    mfcc = librosa.feature.mfcc(y=y, sr=sr).tolist()
    data = [[0 for _ in range(20)] for _ in range(1)]
    for i in range(0,20):
        data[0][i] = (float(average(mfcc[i])))
    return np.array(data)

np.set_printoptions(threshold=np.inf)

x_data = ([[0. for _ in range(20)] for row in range(15)])
y_data = [[1, 0, 0],
          [1, 0, 0],
          [1, 0, 0],
          [1, 0, 0],
          [1, 0, 0],
          [0, 1, 0],
          [0, 1, 0],
          [0, 1, 0],
          [0, 1, 0],
          [0, 1, 0],
          [0, 0, 1],
          [0, 0, 1],
          [0, 0, 1],
          [0, 0, 1],
          [0, 0, 1]]


for i in range(1,6):
    for j in range(1, 4):
        filename = 'data/audio_'+str(j)+'_'+str(i)+'.wav'
        print filename
        y, sr = librosa.load(filename)
        mfcc = librosa.feature.mfcc(y=y, sr=sr).tolist()
        temp = [float(0) for _ in range(len(mfcc))]
        for k in range(0, 20):
            temp[k] = float(average(mfcc[k]))
        x_data[(j-1)*5+i-1] = temp

"""
    for i in range(1,20):
    filename = 'data/audio_'+str(1)+'_'+str(1)+'.wav'
    y, sr = librosa.load(filename)
    mfcc = librosa.feature.mfcc(y=y, sr=sr).tolist()
    print mfcc
    for j in range(1,15):
    x_data[i][j] = 1
    """


x_data = np.array(x_data)
y_data = np.array(y_data)

print x_data

X = tf.placeholder("float", [None, 20])
Y = tf.placeholder("float", [None, 3])

W = tf.Variable(tf.zeros([20, 3]))

hypothesis = tf.nn.softmax(tf.matmul(X, W))

learning_rate = 0.0001

cost = tf.reduce_mean(-tf.reduce_sum(Y * tf.log(hypothesis), reduction_indices=1))

optimizer = tf.train.GradientDescentOptimizer(learning_rate).minimize(cost)


init = tf.initialize_all_variables()

with tf.Session() as sess:
    sess.run(init)
    
    for step in xrange(2001):
        sess.run(optimizer, feed_dict={X: x_data, Y: y_data})
        if step % 200 == 0:
            print step, sess.run(cost, feed_dict={X: x_data, Y: y_data}), sess.run(W)



data1 = getrow(10,1)
    print data1
    a = sess.run(hypothesis, feed_dict={X: data1})
    print "a :", a, sess.run(tf.arg_max(a, 1))