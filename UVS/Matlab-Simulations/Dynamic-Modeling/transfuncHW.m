% Transfer Function of Dynamical System Homework
% H(s) = 4/(as^3 + bs^2 + cs + d)

a = 2;
b = 5;
c = 3;
d = 5;

num=4;
den=[a b c d];

tfsystm = tf(num, den);

stepplot(tfsystm)
