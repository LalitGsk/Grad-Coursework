# Inverted Pendulum Self Balancer using Q-Learning

### Reinforcement Learning
Reinforcement Learning is a Learning Process where a learning agent learns, overtime, to behave optimally in a certain environment by interacting continuously in the environment to maximize the rewards for each action and state. The agent during its learning phase, experiences different situations in its environment. These are called states. 
The agent while being in that state may choose from a set of allowable actions which may fetch different rewards (or penalties). 

### Q-Learning
Q-learning is an off-policy reinforcement learning algorithm that seeks to find the best action to take given the current state. The reason it's considered as an off-policy algorithm is because the q-learning function learns from actions that are outside the current policy, like taking random actions, and therefore a policy isn't needed.

#### Goal – Implement the Q-Learning algorithm on a pole balancing task.

The Q-Learning function is implemented by using the below equation:

Qt(S, a) = (1-α)*Qt-1(S, a) + α*( r(S, a) + γ*( maxbQt-1(S’, b) ))
Such that
Qt = Computed Q-value of the current state t
Qt-1 = Computed Q-value of the old/previous state t-1
r(S, a) (>0 and <=1) = Reinforcement/Reward Function for a state S and action a 

α = Learning rate
γ = Discount Factor
a = Current action
S = Current state
S’ = Next state
b = Set of all actions that contains best action to be picked with best Q-value.

The plots of the learning.dat file from the Q-learning function for different α and γ values are as follows,

![](images/poleBalance_15.jpg)

![](images/poleBalance_25.jpg)

### References: 
  - https://www.geeksforgeeks.org/q-learning-in-python/
  - https://towardsdatascience.com/simple-reinforcement-learning-q-learning-fcddc4b6fe56
