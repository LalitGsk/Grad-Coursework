%Parameter definition for our UGV model
rL=0.052959;    %Radius of left wheel [m]
rR=0.052959;    %Radius of right wheel [m]
b=0.5842;       %Effective width of UGV [m]
bDR=b;       %Value of b used for Dead Reckoning

Vmax=0.44;      %Max translational speed [m/s]
wmax=Vmax/rR;   %Max angular speed [rad/s]

%Encoder parameters
TsampleEncoder=0.1;
eTick=900;
TsampleEncoderDR=TsampleEncoder;
TauEncoderDR=0.1;
Tsample = 1/10;

%%%%%%%%%%%%%%%%%%% control parameter %%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1:speed, 2:angle
KP1=10; %20;    % 1 is velocity controller
KP2=10;%10;  %7 ->   % 2 is angle controller
KI1=.5;%10;%0.3; %0.001; %0.001;%10;
KI2=0.001; %0.001; %0.001;%1; % 3 ->
KD1=0; %3; %0; %3; %0;%3;
KD2=1.1;% 3;%  %.5; %0; %0.5; %0;%0.5; %0.5 ->
Tsample = 1/10; %1/100; %1/10;   %sampling rate -> 0.01
Tmodel=Tsample;
Tau1 = 0.01;     %time constant of filter 1
Tau2 = 0.1;       %time constant of filter 2

%%%%%%%%%%%%%%%%%%%%%%% Way Points %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%X_array  = [5 5 0]; 
%Y_array  = [-3 3 0]; 
X_array  = [3 6 6 3 0 0]; 
Y_array  = [5 0 13 8 13 0];

%%%%%%%%%%%%%%%%%%% guidance parameter %%%%%%%%%%%%%%%%%%%%%%%%%%%
rp1 =  0.3; % 1 -> [m] proximity circle to start slowing down
rp2 =  0.5;  % [m] radius of wayPoint proximity circle to switch to the next wayPoint
Vcom = 3*Vmax/4; % [m/s] used when constant speed is commanded