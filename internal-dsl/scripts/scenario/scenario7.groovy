sensor "button" onPin 9
actuator "led" pin 12
keyboard "k" values "good","bad"

state "write" means led becomes k
initial write

from write to write

export "keyboardControlsLed"
