function ydot=ssdyn(t,y)
a=2;
b=8;
c=3;
d=5;

A=[0 1 0; 0 0 1; -d/a -c/a -b/a];
B=[0;0;4/a];

u=1;

ydot=A*y+B*u;