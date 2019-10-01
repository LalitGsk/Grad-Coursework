%State space representation model Homework

time=[0 40];

y0=[1;1;1];

[t,y]=ode45(@ssdyn,time,y0);

plot(t,y)